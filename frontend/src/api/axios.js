import axios from "axios";

import {
    getAccessToken,
    getRefreshToken,
    setAuthData,
    clearAuthData,
} from "../utils/authStorage";

const api = axios.create({

    baseURL: import.meta.env.VITE_API_BASE_URL,

    headers: {
        "Content-Type": "application/json",
    },

});

api.interceptors.request.use(
    (config) => {

        const token = getAccessToken();

        if (token) {

            config.headers.Authorization =
                `Bearer ${token}`;

        }

        return config;
    },

    (error) => {
        return Promise.reject(error);
    }
);

let isRefreshing = false;

let refreshSubscribers = [];

const subscribeToRefresh = (callback) => {

    refreshSubscribers.push(callback);

};

const notifyRefreshSubscribers = (token) => {

    refreshSubscribers.forEach(
        (callback) => callback(token)
    );

    refreshSubscribers = [];

};

api.interceptors.response.use(

    (response) => response,

    async (error) => {

        const originalRequest = error.config;

        if (
            error.response?.status !== 401 ||
            originalRequest?._retry
        ) {
            return Promise.reject(error);
        }

        originalRequest._retry = true;

        const refreshToken =
            getRefreshToken();

        if (!refreshToken) {

            clearAuthData();

            window.location.href = "/login";

            return Promise.reject(error);
        }

        if (isRefreshing) {

            return new Promise((resolve, reject) => {

                subscribeToRefresh((token) => {

                    if (!token) {

                        reject(error);
                        return;

                    }

                    originalRequest.headers.Authorization =
                        `Bearer ${token}`;

                    resolve(
                        api(originalRequest)
                    );

                });

            });

        }

        isRefreshing = true;

        try {

            const response =
                await axios.post(
                    `${import.meta.env.VITE_API_BASE_URL}/auth/refresh`,
                    {
                        refreshToken,
                    }
                );

            const newAccessToken =
                response.data.data.accessToken;

            const currentUser =
                JSON.parse(
                    localStorage.getItem(
                        "nexus_user"
                    )
                );

            setAuthData({

                accessToken: newAccessToken,

                refreshToken,

                user: currentUser,

            });

            notifyRefreshSubscribers(
                newAccessToken
            );

            originalRequest.headers.Authorization =
                `Bearer ${newAccessToken}`;

            return api(originalRequest);

        } catch (refreshError) {

            clearAuthData();

            window.location.href = "/login";

            return Promise.reject(
                refreshError
            );

        } finally {

            isRefreshing = false;

        }

    }
);

export default api;
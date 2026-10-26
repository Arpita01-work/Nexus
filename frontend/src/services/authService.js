import axios from "../api/axios";

const BASE_URL = "/auth";

export const login = async (credentials) => {
    const response = await axios.post(
        `${BASE_URL}/login`,
        credentials
    );

    return response.data.data;
};

export const refreshAccessToken = async (refreshToken) => {
    const response = await axios.post(
        `${BASE_URL}/refresh`,
        {
            refreshToken,
        }
    );

    return response.data.data;
};

export const logout = async (refreshToken) => {
    await axios.post(
        `${BASE_URL}/logout`,
        {
            refreshToken,
        }
    );
};
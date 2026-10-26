import axios from "../api/axios";

const BASE_URL = "/users";

export const getUsers = async () => {
    const response = await axios.get(BASE_URL);

    return response.data.data;
};

export const getUser = async (id) => {
    const response = await axios.get(
        `${BASE_URL}/${id}`
    );

    return response.data.data;
};

export const createUser = async (user) => {
    const response = await axios.post(
        BASE_URL,
        user
    );

    return response.data.data;
};

export const updateUser = async (id, user) => {
    const response = await axios.put(
        `${BASE_URL}/${id}`,
        user
    );

    return response.data.data;
};

export const updateUserStatus = async (id, active) => {
    const response = await axios.patch(
        `${BASE_URL}/${id}/status`,
        null,
        {
            params: {
                active,
            },
        }
    );

    return response.data;
};

export const deleteUser = async (id) => {
    return axios.delete(
        `${BASE_URL}/${id}`
    );
};
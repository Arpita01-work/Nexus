import axios from "../api/axios";

const BASE_URL = "/organizations";

export const getOrganizations = async () => {
    const response = await axios.get(BASE_URL);
    return response.data.data;
};

export const createOrganization = async (organization) => {
    const response = await axios.post(BASE_URL, organization);
    return response.data.data;
};

export const updateOrganization = async (id, organization) => {
    const response = await axios.put(`${BASE_URL}/${id}`, organization);
    return response.data.data;
};

export const deleteOrganization = async (id) => {
    return axios.delete(`${BASE_URL}/${id}`);
};
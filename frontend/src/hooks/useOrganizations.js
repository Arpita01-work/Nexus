import { useCallback, useEffect, useState } from "react";
import toast from "react-hot-toast";

import {
    getOrganizations,
    createOrganization,
    updateOrganization,
} from "../services/organizationService";

import { deleteOrganization } from "../services/organizationService";

export default function useOrganizations() {

    const [organizations, setOrganizations] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    

    const loadOrganizations = useCallback(async () => {

        try {

            setLoading(true);

            const data = await getOrganizations();

            setOrganizations(data);
            setError("");

        } catch (error) {

            console.error(error);

            setError("Failed to load organizations");

            toast.error("Failed to load organizations");

        } finally {

            setLoading(false);

        }

    }, []);

    useEffect(() => {

        (async() => {
            await loadOrganizations();
        })();

    }, []);

    const saveOrganization = async (
        organization,
        selectedOrganization
    ) => {

        try {

            if (selectedOrganization) {

                await updateOrganization(
                    selectedOrganization.id,
                    organization
                );

                toast.success("Organization updated");

            } else {

                await createOrganization(organization);

                toast.success("Organization created");

            }

            await loadOrganizations();

            return true;

        } catch (error) {

            console.error(error);

            toast.error("Operation failed");

            return false;

        }

    };

    const removeOrganization = async (id) => {

        try {

            await deleteOrganization(id);

            toast.success("Organization deleted");

            await loadOrganizations();

            return true;

        } catch (error) {

            console.error(error);

            toast.error("Unable to delete organization");

            return false;

        }

    };

    return {

        organizations,

        loading,

        error,

        saveOrganization,

        loadOrganizations,

        removeOrganization,
    };

}
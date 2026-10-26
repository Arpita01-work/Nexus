import {
    useCallback,
    useEffect,
    useState,
} from "react";

import toast from "react-hot-toast";

import {
    getUsers,
    createUser,
    updateUser,
    updateUserStatus,
    deleteUser,
} from "../services/userService";

export default function useUsers() {

    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    const loadUsers = useCallback(async () => {

        try {
            setLoading(true);
            const data = await getUsers();
            setUsers(data);
            setError("");
        } catch (error) {
            console.error(error);
            setError("Failed to load users");
            toast.error("Failed to load users");
        } finally {
            setLoading(false);
        }
    }, []);

    useEffect(() => {
        (async () => {
            await loadUsers();
        })();
    }, [loadUsers]);

    const saveUser = async (user, selectedUser) => {

        try {

            if (selectedUser) {

                await updateUser(
                    selectedUser.id,
                    user
                );

                toast.success(
                    "User updated successfully"
                );

            } else {

                await createUser(user);

                toast.success(
                    "User created successfully"
                );
            }

            await loadUsers();

            return true;

        } catch (error) {

            console.error(error);

            toast.error(
                "User operation failed"
            );

            return false;
        }
    };

    const changeUserStatus = async (
        id,
        active
    ) => {

        try {

            await updateUserStatus(
                id,
                active
            );

            toast.success(
                active
                    ? "User activated successfully"
                    : "User deactivated successfully"
            );

            await loadUsers();

            return true;

        } catch (error) {

            console.error(error);

            toast.error(
                "Unable to update user status"
            );

            return false;
        }
    };

    const removeUser = async (id) => {

        try {

            await deleteUser(id);

            toast.success(
                "User deleted successfully"
            );

            await loadUsers();

            return true;

        } catch (error) {

            console.error(error);

            toast.error(
                "Unable to delete user"
            );

            return false;
        }
    };

    return {
        users,
        loading,
        error,
        saveUser,
        changeUserStatus,
        removeUser,
        loadUsers,
    };
}
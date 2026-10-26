import { useState } from "react";

import {
    Alert,
    Box,
    Button,
    CircularProgress,
    Paper,
    Typography,
} from "@mui/material";

import AddIcon from "@mui/icons-material/PersonAdd";

import UserTable
    from "../../components/user/UserTable";

import UserDialog
    from "../../components/user/UserDialog";

import UserDetailsDialog
    from "../../components/user/UserDetailsDialog";

import DeleteUserDialog
    from "../../components/user/DeleteUserDialog";

import useUsers
    from "../../hooks/useUsers";

import useOrganizations
    from "../../hooks/useOrganizations";

export default function UserList() {

    const {
        users,
        loading,
        error,
        saveUser,
        removeUser,
    } = useUsers();

    const {
        organizations,
    } = useOrganizations();

    const [
        dialogOpen,
        setDialogOpen,
    ] = useState(false);

    const [
        selectedUser,
        setSelectedUser,
    ] = useState(null);

    const [
        detailsDialogOpen,
        setDetailsDialogOpen,
    ] = useState(false);

    const [
        selectedUserDetails,
        setSelectedUserDetails,
    ] = useState(null);

    const [
        deleteDialogOpen,
        setDeleteDialogOpen,
    ] = useState(false);

    const [
        userToDelete,
        setUserToDelete,
    ] = useState(null);

    const handleSave = async (
        formData
    ) => {

        const success =
            await saveUser(
                formData,
                selectedUser
            );

        if (success) {

            setDialogOpen(false);
            setSelectedUser(null);

        }
    };

    const handleView = (user) => {

        console.log(
            "VIEW USER:",
            user
        );

        setSelectedUserDetails(user);
        setDetailsDialogOpen(true);
    };

    const handleDelete = async () => {

        if (!userToDelete) {
            return;
        }

        const success =
            await removeUser(
                userToDelete.id
            );

        if (success) {

            setDeleteDialogOpen(false);
            setUserToDelete(null);

        }
    };

    return (

        <Paper
            sx={{
                p: 3,
            }}
        >

            {/* Header */}

            <Box
                sx={{
                    display: "flex",
                    justifyContent:
                        "space-between",
                    alignItems: "center",
                    mb: 3,
                }}
            >

                <Box>

                    <Typography
                        variant="h4"
                        fontWeight={600}
                    >
                        Users
                    </Typography>

                    <Typography
                        variant="body2"
                        color="text.secondary"
                        sx={{ mt: 0.5 }}
                    >
                        Manage users across
                        organizations
                    </Typography>

                </Box>

                <Button
                    variant="contained"
                    startIcon={
                        <AddIcon />
                    }
                    onClick={() => {

                        setSelectedUser(
                            null
                        );

                        setDialogOpen(
                            true
                        );

                    }}
                >
                    Add User
                </Button>

            </Box>

            {/* Count */}

            <Box
                sx={{
                    mb: 2,
                    display: "flex",
                    gap: 1,
                }}
            >

                <Typography
                    variant="body2"
                    color="text.secondary"
                >
                    Total Users:
                </Typography>

                <Typography
                    variant="body2"
                    fontWeight={700}
                >
                    {users.length}
                </Typography>

            </Box>

            {/* Error */}

            {error && (

                <Alert
                    severity="error"
                    sx={{ mb: 2 }}
                >
                    {error}
                </Alert>

            )}

            {/* Table */}

            {loading ? (

                <Box
                    sx={{
                        textAlign:
                            "center",
                        mt: 5,
                    }}
                >

                    <CircularProgress />

                </Box>

            ) : (

                <UserTable
                    users={users}
                    loading={loading}
                    onView={handleView}
                    onEdit={(user) => {

                        setSelectedUser(
                            user
                        );

                        setDialogOpen(
                            true
                        );

                    }}
                    onDelete={(user) => {

                        setUserToDelete(
                            user
                        );

                        setDeleteDialogOpen(
                            true
                        );

                    }}
                />

            )}

            {/* Create / Edit */}

            <UserDialog
                open={dialogOpen}
                user={selectedUser}
                organizations={
                    organizations
                }
                onClose={() => {

                    setDialogOpen(
                        false
                    );

                    setSelectedUser(
                        null
                    );

                }}
                onSubmit={
                    handleSave
                }
            />

            {/* User Details */}

            <UserDetailsDialog
                open={
                    detailsDialogOpen
                }
                user={
                    selectedUserDetails
                }
                onClose={() => {

                    setDetailsDialogOpen(
                        false
                    );

                    setSelectedUserDetails(
                        null
                    );

                }}
            />

            {/* Delete */}

            <DeleteUserDialog
                open={
                    deleteDialogOpen
                }
                user={userToDelete}
                onClose={() => {

                    setDeleteDialogOpen(
                        false
                    );

                    setUserToDelete(
                        null
                    );

                }}
                onConfirm={
                    handleDelete
                }
            />

        </Paper>
    );
}
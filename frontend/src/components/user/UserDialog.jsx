import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Button,
} from "@mui/material";

import UserForm from "../../pages/users/UserForm";

export default function UserDialog({
    open,
    onClose,
    user,
    organizations,
    onSubmit,
}) {

    return (
        <Dialog
            open={open}
            onClose={onClose}
            fullWidth
            maxWidth="md"
        >

            <DialogTitle>
                {user
                    ? "Edit User"
                    : "Add User"}
            </DialogTitle>

            <DialogContent dividers>

                <UserForm
                    user={user}
                    organizations={organizations}
                    onSubmit={onSubmit}
                />

            </DialogContent>

            <DialogActions>

                <Button onClick={onClose}>
                    Cancel
                </Button>

            </DialogActions>

        </Dialog>
    );
}
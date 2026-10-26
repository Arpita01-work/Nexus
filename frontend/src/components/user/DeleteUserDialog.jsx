import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Button,
    Typography,
} from "@mui/material";

export default function DeleteUserDialog({
    open,
    user,
    onClose,
    onConfirm,
}) {

    const fullName = user
        ? `${user.firstName} ${user.lastName}`
        : "";

    return (
        <Dialog
            open={open}
            onClose={onClose}
            maxWidth="xs"
            fullWidth
        >

            <DialogTitle>
                Delete User
            </DialogTitle>

            <DialogContent>

                <Typography>
                    Are you sure you want to
                    delete{" "}
                    <strong>{fullName}</strong>?
                </Typography>

                <Typography
                    variant="body2"
                    color="text.secondary"
                    sx={{ mt: 1 }}
                >
                    This action cannot be undone.
                </Typography>

            </DialogContent>

            <DialogActions>

                <Button onClick={onClose}>
                    Cancel
                </Button>

                <Button
                    variant="contained"
                    color="error"
                    onClick={onConfirm}
                >
                    Delete
                </Button>

            </DialogActions>

        </Dialog>
    );
}
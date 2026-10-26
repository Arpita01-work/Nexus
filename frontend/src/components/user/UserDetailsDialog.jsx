import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Button,
    Box,
    Typography,
    Divider,
    Chip,
    Grid,
} from "@mui/material";

import PersonIcon from "@mui/icons-material/Person";
import BusinessIcon from "@mui/icons-material/Business";
import EmailIcon from "@mui/icons-material/Email";
import PhoneIcon from "@mui/icons-material/Phone";
import WorkIcon from "@mui/icons-material/Work";
import VerifiedIcon from "@mui/icons-material/Verified";

export default function UserDetailsDialog({
    open,
    user,
    onClose,
}) {
    if (!user) {
        return null;
    }

    const fullName =
        `${user.firstName || ""} ${user.lastName || ""}`.trim();

    return (
        <Dialog
            open={open}
            onClose={onClose}
            fullWidth
            maxWidth="sm"
        >
            <DialogTitle>
                <Box
                    sx={{
                        display: "flex",
                        alignItems: "center",
                        gap: 1.5,
                    }}
                >
                    <PersonIcon color="primary" />

                    <Box>
                        <Typography
                            variant="h6"
                            fontWeight={600}
                        >
                            User Details
                        </Typography>

                        <Typography
                            variant="body2"
                            color="text.secondary"
                        >
                            View user information
                        </Typography>
                    </Box>
                </Box>
            </DialogTitle>

            <Divider />

            <DialogContent>
                <Box
                    sx={{
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "space-between",
                        mb: 3,
                    }}
                >
                    <Box>
                        <Typography
                            variant="h5"
                            fontWeight={600}
                        >
                            {fullName || "Unnamed User"}
                        </Typography>

                        <Typography
                            variant="body2"
                            color="text.secondary"
                            sx={{ mt: 0.5 }}
                        >
                            {user.email}
                        </Typography>
                    </Box>

                    <Chip
                        label={
                            user.active
                                ? "Active"
                                : "Inactive"
                        }
                        color={
                            user.active
                                ? "success"
                                : "default"
                        }
                    />
                </Box>

                <Grid container spacing={2}>

                    <Grid size={{ xs: 12, sm: 6 }}>
                        <InfoItem
                            icon={<BusinessIcon />}
                            label="Organization"
                            value={
                                user.organizationName ||
                                "Not assigned"
                            }
                        />
                    </Grid>

                    <Grid size={{ xs: 12, sm: 6 }}>
                        <InfoItem
                            icon={<WorkIcon />}
                            label="Job Title"
                            value={
                                user.jobTitle ||
                                "Not specified"
                            }
                        />
                    </Grid>

                    <Grid size={{ xs: 12, sm: 6 }}>
                        <InfoItem
                            icon={<EmailIcon />}
                            label="Email"
                            value={user.email}
                        />
                    </Grid>

                    <Grid size={{ xs: 12, sm: 6 }}>
                        <InfoItem
                            icon={<PhoneIcon />}
                            label="Phone"
                            value={
                                user.phone ||
                                "Not specified"
                            }
                        />
                    </Grid>

                    <Grid size={{ xs: 12, sm: 6 }}>
                        <InfoItem
                            icon={<VerifiedIcon />}
                            label="Email Verification"
                            value={
                                user.emailVerified
                                    ? "Verified"
                                    : "Not Verified"
                            }
                        />
                    </Grid>

                    <Grid size={{ xs: 12, sm: 6 }}>
                        <InfoItem
                            icon={<PersonIcon />}
                            label="Account Status"
                            value={
                                user.enabled
                                    ? "Enabled"
                                    : "Disabled"
                            }
                        />
                    </Grid>

                </Grid>
            </DialogContent>

            <DialogActions>
                <Button onClick={onClose}>
                    Close
                </Button>
            </DialogActions>
        </Dialog>
    );
}

function InfoItem({
    icon,
    label,
    value,
}) {
    return (
        <Box
            sx={{
                display: "flex",
                alignItems: "flex-start",
                gap: 1.5,
                p: 1.5,
                border: "1px solid",
                borderColor: "divider",
                borderRadius: 2,
            }}
        >
            <Box
                sx={{
                    color: "primary.main",
                    display: "flex",
                }}
            >
                {icon}
            </Box>

            <Box>
                <Typography
                    variant="caption"
                    color="text.secondary"
                >
                    {label}
                </Typography>

                <Typography
                    variant="body2"
                    fontWeight={500}
                >
                    {value}
                </Typography>
            </Box>
        </Box>
    );
}
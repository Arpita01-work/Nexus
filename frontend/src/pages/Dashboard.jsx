import {
    Box,
    Grid,
    Paper,
    Typography,
    Button,
} from "@mui/material";

import BusinessIcon
    from "@mui/icons-material/Business";

import GroupIcon
    from "@mui/icons-material/Group";

import PersonIcon
    from "@mui/icons-material/Person";

import AddBusinessIcon
    from "@mui/icons-material/AddBusiness";

import PeopleAltIcon
    from "@mui/icons-material/PeopleAlt";

import { useNavigate } from "react-router-dom";

import StatCard
    from "../components/common/StatCard";

import useOrganizations
    from "../hooks/useOrganizations";

import { useAuth }
    from "../context/useAuth";

export default function Dashboard() {

    const navigate = useNavigate();

    const {
        organizations,
        loading,
    } = useOrganizations();

    const {
        user,
    } = useAuth();

    const totalOrganizations =
        organizations.length;

    return (

        <Box>

            {/* Header */}

            <Box
                sx={{
                    display: "flex",
                    justifyContent:
                        "space-between",
                    alignItems: "center",
                    mb: 4,
                }}
            >

                <Box>

                    <Typography
                        variant="h4"
                        fontWeight={700}
                    >
                        Dashboard
                    </Typography>

                    <Typography
                        color="text.secondary"
                        sx={{ mt: 0.5 }}
                    >
                        Welcome back,{" "}
                        {user?.firstName || "Admin"}.
                        Here's what's happening
                        in Nexus.
                    </Typography>

                </Box>

                <Button
                    variant="contained"
                    startIcon={
                        <AddBusinessIcon />
                    }
                    onClick={() =>
                        navigate(
                            "/organizations"
                        )
                    }
                >
                    Manage Organizations
                </Button>

            </Box>

            {/* Statistics */}

            <Grid
                container
                spacing={3}
            >

                <Grid
                    size={{
                        xs: 12,
                        sm: 6,
                        md: 4,
                    }}
                >

                    <StatCard
                        title="Organizations"
                        value={
                            loading
                                ? "..."
                                : totalOrganizations
                        }
                        icon={
                            <BusinessIcon />
                        }
                        description="Registered organizations"
                    />

                </Grid>

                <Grid
                    size={{
                        xs: 12,
                        sm: 6,
                        md: 4,
                    }}
                >

                    <StatCard
                        title="Users"
                        value="—"
                        icon={
                            <GroupIcon />
                        }
                        description="Coming with User Management"
                    />

                </Grid>

                <Grid
                    size={{
                        xs: 12,
                        sm: 6,
                        md: 4,
                    }}
                >

                    <StatCard
                        title="Active Users"
                        value="—"
                        icon={
                            <PersonIcon />
                        }
                        description="Coming with User Management"
                    />

                </Grid>

            </Grid>

            {/* Quick Actions */}

            <Paper
                elevation={1}
                sx={{
                    mt: 4,
                    p: 3,
                    borderRadius: 3,
                }}
            >

                <Typography
                    variant="h6"
                    fontWeight={600}
                    mb={2}
                >
                    Quick Actions
                </Typography>

                <Box
                    sx={{
                        display: "flex",
                        gap: 2,
                        flexWrap: "wrap",
                    }}
                >

                    <Button
                        variant="outlined"
                        startIcon={
                            <AddBusinessIcon />
                        }
                        onClick={() =>
                            navigate(
                                "/organizations"
                            )
                        }
                    >
                        Organizations
                    </Button>

                    <Button
                        variant="outlined"
                        startIcon={
                            <PeopleAltIcon />
                        }
                        onClick={() =>
                            navigate("/users")
                        }
                    >
                        Users
                    </Button>

                </Box>

            </Paper>

            {/* Recent Organizations */}

            <Paper
                elevation={1}
                sx={{
                    mt: 4,
                    p: 3,
                    borderRadius: 3,
                }}
            >

                <Box
                    sx={{
                        display: "flex",
                        justifyContent:
                            "space-between",
                        alignItems: "center",
                        mb: 2,
                    }}
                >

                    <Box>

                        <Typography
                            variant="h6"
                            fontWeight={600}
                        >
                            Organizations
                        </Typography>

                        <Typography
                            variant="body2"
                            color="text.secondary"
                        >
                            Organizations currently
                            registered in Nexus
                        </Typography>

                    </Box>

                    <Button
                        onClick={() =>
                            navigate(
                                "/organizations"
                            )
                        }
                    >
                        View All
                    </Button>

                </Box>

                {organizations
                    .slice(0, 5)
                    .map((organization) => (

                        <Box
                            key={
                                organization.id
                            }
                            sx={{
                                display: "flex",
                                justifyContent:
                                    "space-between",
                                alignItems: "center",
                                py: 1.5,
                                borderBottom:
                                    "1px solid #e2e8f0",
                            }}
                        >

                            <Box>

                                <Typography
                                    fontWeight={600}
                                >
                                    {
                                        organization.name
                                    }
                                </Typography>

                                <Typography
                                    variant="body2"
                                    color="text.secondary"
                                >
                                    {
                                        organization.code
                                    }
                                </Typography>

                            </Box>

                            <Typography
                                variant="body2"
                                color="text.secondary"
                            >
                                {
                                    organization.email ||
                                    "No email"
                                }
                            </Typography>

                        </Box>

                    ))}

                {organizations.length === 0 &&
                    !loading && (

                        <Typography
                            color="text.secondary"
                            sx={{ py: 3 }}
                        >
                            No organizations have
                            been registered yet.
                        </Typography>

                    )}

            </Paper>

        </Box>
    );
}
import {
    Button,
    Box,
    Typography,
} from "@mui/material";

import toast from "react-hot-toast";

import { useNavigate } from "react-router-dom";

import { useAuth } from "../../context/useAuth";

export default function Navbar() {

    const {
        user,
        logout,
    } = useAuth();

    const navigate =
        useNavigate();

    const handleLogout = async () => {

        try {

            await logout();

            toast.success(
                "Logged out successfully"
            );

            navigate("/login");

        } catch (error) {

            console.error(error);

            toast.error(
                "Logout failed"
            );

        }

    };

    return (

        <Box
            sx={{
                height: "70px",
                background: "white",
                display: "flex",
                justifyContent:
                    "space-between",
                alignItems: "center",
                px: 3,
                boxShadow:
                    "0 2px 5px rgba(0,0,0,0.1)",
            }}
        >

            <Typography
                variant="h6"
                fontWeight={600}
            >
                Nexus Dashboard
            </Typography>

            <Box
                sx={{
                    display: "flex",
                    alignItems: "center",
                    gap: 2,
                }}
            >

                <Box>
                    <Typography
                        fontWeight={600}
                    >
                        {user?.firstName}{" "}
                        {user?.lastName}
                    </Typography>

                    <Typography
                        variant="caption"
                        color="text.secondary"
                    >
                        {user?.email}
                    </Typography>
                </Box>

                <Button
                    variant="outlined"
                    color="error"
                    onClick={handleLogout}
                >
                    Logout
                </Button>

            </Box>

        </Box>

    );

}
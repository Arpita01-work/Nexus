import {
  Box,
  Paper,
  Typography,
} from "@mui/material";

import { useAuth } from "../context/useAuth";

export default function EmployeeProfile() {
  const { user } = useAuth();

  return (
    <Box
      sx={{
        minHeight: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        background: "#f1f5f9",
        p: 2,
      }}
    >
      <Paper
        elevation={3}
        sx={{
          width: "100%",
          maxWidth: 600,
          p: 4,
          borderRadius: 3,
        }}
      >
        <Typography variant="h4" fontWeight={700} mb={2}>
          My Profile
        </Typography>

        <Typography color="text.secondary" mb={3}>
          Welcome to Nexus. You are signed in as an employee.
        </Typography>

        <Typography>
          <strong>Email:</strong>{" "}
          {user?.email || "Not available"}
        </Typography>

        <Typography mt={1}>
          <strong>Role:</strong>{" "}
          {user?.role || "Not available"}
        </Typography>
      </Paper>
    </Box>
  );
}
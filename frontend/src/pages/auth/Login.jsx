import { useState } from "react";
import {
  Box,
  Button,
  Paper,
  TextField,
  Typography,
} from "@mui/material";
import toast from "react-hot-toast";
import { Navigate, useNavigate } from "react-router-dom";
import { useAuth } from "../../context/useAuth";

export default function Login() {
  const navigate = useNavigate();
  const { login, loading, isAuthenticated, user } = useAuth();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  // If already logged in, redirect according to the user's role.
  if (isAuthenticated) {
    if (user?.role === "ORGANIZATION_ADMIN") {
      return <Navigate to="/" replace />;
    }

    if (user?.role === "EMPLOYEE") {
      return <Navigate to="/profile" replace />;
    }
  }

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const data = await login(email.trim(), password);

      const role = data?.user?.role;

      toast.success("Login successful");

      if (role === "ORGANIZATION_ADMIN") {
        navigate("/", { replace: true });
      } else if (role === "EMPLOYEE") {
        navigate("/profile", { replace: true });
      } else {
        toast.error(
          "Your account role is missing or not recognized. Please contact your administrator."
        );
      }
    } catch (error) {
      console.error("Login failed:", error);

      toast.error(
        error.response?.data?.message ||
          "Invalid email or password."
      );
    }
  };

  return (
    <Box
      sx={{
        minHeight: "100vh",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        background: "#f1f5f9",
        px: 2,
      }}
    >
      <Paper
        elevation={4}
        sx={{
          width: "100%",
          maxWidth: 400,
          p: 4,
          borderRadius: 3,
        }}
      >
        <Typography variant="h4" fontWeight={700} mb={1}>
          Nexus
        </Typography>

        <Typography color="text.secondary" mb={3}>
          Sign in to your account
        </Typography>

        <Box component="form" onSubmit={handleSubmit}>
          <TextField
            label="Email"
            type="email"
            fullWidth
            margin="normal"
            autoComplete="username"
            value={email}
            onChange={(event) => setEmail(event.target.value)}
            required
          />

          <TextField
            label="Password"
            type="password"
            fullWidth
            margin="normal"
            autoComplete="current-password"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
            required
          />

          <Button
            type="submit"
            variant="contained"
            fullWidth
            size="large"
            sx={{ mt: 3 }}
            disabled={loading}
          >
            {loading ? "Signing in..." : "Sign In"}
          </Button>
        </Box>
      </Paper>
    </Box>
  );
}
import { Navigate, useLocation } from "react-router-dom";
import { useAuth } from "../context/useAuth";

export default function ProtectedRoute({
  children,
  allowedRoles,
}) {
  const { isAuthenticated, user } = useAuth();
  const location = useLocation();

  // Not logged in: redirect to login.
  if (!isAuthenticated) {
    return (
      <Navigate
        to="/login"
        state={{ from: location }}
        replace
      />
    );
  }

  const role = user?.role;

  // The user is authenticated, but their role is not available.
  if (!role) {
    return (
      <div>
        Unable to determine your account role. Please sign out
        and sign in again.
      </div>
    );
  }

  // Restrict this route to the permitted roles.
  if (allowedRoles && !allowedRoles.includes(role)) {
    if (role === "EMPLOYEE") {
      return <Navigate to="/profile" replace />;
    }

    if (role === "ORGANIZATION_ADMIN") {
      return <Navigate to="/" replace />;
    }

    return <Navigate to="/login" replace />;
  }

  return children;
}
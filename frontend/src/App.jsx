import { BrowserRouter, Routes, Route } from "react-router-dom";

import Layout from "./components/layout/Layout";
import Dashboard from "./pages/Dashboard";
import OrganizationList from "./pages/organizations/OrganizationList";
import UserList from "./pages/users/UserList";
import Login from "./pages/auth/Login";
import ProtectedRoute from "./routes/ProtectedRoute";
import AuthProvider from "./context/AuthProvider";
import ActivateAccount from "./pages/auth/ActivateAccount";
import EmployeeProfile from "./pages/EmployeeProfile";

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Routes>
          {/* Public routes */}
          <Route path="/login" element={<Login />} />
          <Route path="/activate" element={<ActivateAccount />} />

          {/* Organization-admin-only routes */}
          <Route
            path="/"
            element={
              <ProtectedRoute
                allowedRoles={["ORGANIZATION_ADMIN"]}
              >
                <Layout />
              </ProtectedRoute>
            }
          >
            <Route index element={<Dashboard />} />

            <Route
              path="organizations"
              element={<OrganizationList />}
            />

            <Route
              path="users"
              element={<UserList />}
            />
          </Route>
          <Route
            path="/profile"
            element={
              <ProtectedRoute allowedRoles={["EMPLOYEE"]}>
                <EmployeeProfile />
              </ProtectedRoute>
            }
          />
          {/* Employee route will be added when Profile.jsx exists */}
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
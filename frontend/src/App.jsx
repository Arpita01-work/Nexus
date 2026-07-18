import { BrowserRouter, Routes, Route } from "react-router-dom";

import Layout from "./components/layout/Layout";
import Dashboard from "./pages/Dashboard";
import OrganizationList from "./pages/organizations/OrganizationList";
import UserList from "./pages/users/UserList";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
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
      </Routes>
    </BrowserRouter>
  );
}

export default App; 
import { NavLink } from "react-router-dom";
import DashboardIcon from "@mui/icons-material/Dashboard";
import BusinessIcon from "@mui/icons-material/Business";
import GroupIcon from "@mui/icons-material/Group";

const menuItems = [
  {
    text: "Dashboard",
    icon: <DashboardIcon />,
    path: "/",
  },
  {
    text: "Organizations",
    icon: <BusinessIcon />,
    path: "/organizations",
  },
  {
    text: "Users",
    icon: <GroupIcon />,
    path: "/users",
  },
];

export default function Sidebar() {
  return (
    <div
      style={{
        width: "250px",
        background: "#1e293b",
        color: "white",
        minHeight: "100vh",
        padding: "20px",
      }}
    >
      <h2 style={{ marginBottom: "40px" }}>Nexus</h2>

      {menuItems.map((item) => (
        <NavLink
          key={item.text}
          to={item.path}
          style={({ isActive }) => ({
            display: "flex",
            alignItems: "center",
            gap: "12px",
            padding: "12px",
            marginBottom: "10px",
            textDecoration: "none",
            color: "white",
            borderRadius: "8px",
            background: isActive ? "#334155" : "transparent",
          })}
        >
          {item.icon}
          {item.text}
        </NavLink>
      ))}
    </div>
  );
}
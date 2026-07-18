import { Outlet } from "react-router-dom";
import Sidebar from "./Sidebar";
import Navbar from "./Navbar";

export default function Layout() {
  return (
    <div
      style={{
        display: "flex",
      }}
    >
      <Sidebar />

      <div
        style={{
          flex: 1,
          background: "#f8fafc",
          minHeight: "100vh",
        }}
      >
        <Navbar />

        <div
          style={{
            padding: "30px",
          }}
        >
          <Outlet />
        </div>
      </div>
    </div>
  );
}
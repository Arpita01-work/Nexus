import { useState } from "react";

import { login as loginRequest, logout as logoutRequest } from "../services/authService";

import {
  getUser,
  getAccessToken,
  getRefreshToken,
  setAuthData,
  clearAuthData,
} from "../utils/authStorage";

import { AuthContext } from "./AuthContext";

export default function AuthProvider({ children }) {
  const [user, setUser] = useState(getUser());

  const [loading, setLoading] = useState(false);

  const isAuthenticated = Boolean(getAccessToken());

  const login = async (email, password) => {
    setLoading(true);

    try {
      const data = await loginRequest({
        email,
        password,
      });

      setAuthData({
        accessToken: data.accessToken,

        refreshToken: data.refreshToken,

        user: data.user,
      });

      setUser(data.user);

      return data;
    } finally {
      setLoading(false);
    }
  };

  const logout = async () => {
    const refreshToken = getRefreshToken();

    try {
      if (refreshToken) {
        await logoutRequest(refreshToken);
      }
    } finally {
      clearAuthData();

      setUser(null);
    }
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        loading,
        isAuthenticated,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

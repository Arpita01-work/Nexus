
import { useState } from "react";
import { useSearchParams, Link } from "react-router-dom";

const API_BASE_URL = "http://localhost:8080";

function ActivateAccount() {
  const [searchParams] = useSearchParams();
  const token = searchParams.get("token");

  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  async function handleSubmit(event) {
    event.preventDefault();

    setMessage("");
    setError("");

    if (!token) {
      setError("Activation token is missing. Please use the link in your email.");
      return;
    }

    if (newPassword !== confirmPassword) {
      setError("Passwords do not match.");
      return;
    }

    setLoading(true);

    try {
      const response = await fetch(
        `${API_BASE_URL}/api/v1/auth/activate`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            token,
            newPassword,
            confirmPassword,
          }),
        }
      );

      const result = await response.json().catch(() => null);

      if (!response.ok) {
        throw new Error(
          result?.message || "Account activation failed. Please try again."
        );
      }

      setMessage(
        result?.message || "Account activated successfully. You can now log in."
      );
      setNewPassword("");
      setConfirmPassword("");
    } catch (err) {
      setError(
        err.message || "Unable to connect to the server. Please try again."
      );
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50 px-4">
      <div className="w-full max-w-md rounded-xl bg-white p-8 shadow-md">
        <h1 className="mb-2 text-2xl font-bold text-gray-900">
          Activate Your Nexus Account
        </h1>

        <p className="mb-6 text-sm text-gray-600">
          Set a password to activate your account and get started.
        </p>

        {!token && (
          <p className="mb-4 rounded-md bg-red-50 p-3 text-sm text-red-700">
            The activation token is missing from the URL.
            Please open the link from your activation email.
          </p>
        )}

        {error && (
          <p role="alert" className="mb-4 rounded-md bg-red-50 p-3 text-sm text-red-700">
            {error}
          </p>
        )}

        {message && (
          <div role="status" className="mb-4 rounded-md bg-green-50 p-3 text-sm text-green-700">
            {message}
            <p className="mt-2">
              <Link to="/login" className="font-semibold underline">
                Go to login
              </Link>
            </p>
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label
              htmlFor="newPassword"
              className="mb-1 block text-sm font-medium text-gray-700"
            >
              New password
            </label>
            <input
              id="newPassword"
              type="password"
              autoComplete="new-password"
              value={newPassword}
              onChange={(event) => setNewPassword(event.target.value)}
              required
              minLength={8}
              maxLength={72}
              disabled={loading || !token || Boolean(message)}
              className="w-full rounded-md border border-gray-300 px-3 py-2 outline-none focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500"
              placeholder="Enter your new password"
            />
          </div>

          <div>
            <label
              htmlFor="confirmPassword"
              className="mb-1 block text-sm font-medium text-gray-700"
            >
              Confirm password
            </label>
            <input
              id="confirmPassword"
              type="password"
              autoComplete="new-password"
              value={confirmPassword}
              onChange={(event) => setConfirmPassword(event.target.value)}
              required
              minLength={8}
              maxLength={72}
              disabled={loading || !token || Boolean(message)}
              className="w-full rounded-md border border-gray-300 px-3 py-2 outline-none focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500"
              placeholder="Confirm your new password"
            />
          </div>

          <button
            type="submit"
            disabled={loading || !token || Boolean(message)}
            className="w-full rounded-md bg-indigo-600 px-4 py-2.5 font-semibold text-white hover:bg-indigo-700 disabled:cursor-not-allowed disabled:opacity-60"
          >
            {loading ? "Activating account..." : "Set Password & Activate"}
          </button>
        </form>

        <p className="mt-6 text-center text-sm text-gray-600">
          Already activated?{" "}
          <Link to="/login" className="font-semibold text-indigo-600 hover:underline">
            Log in
          </Link>
        </p>
      </div>
    </div>
  );
}

export default ActivateAccount;

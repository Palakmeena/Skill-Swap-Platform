"use client";

import { useEffect, useState } from 'react';
import ProtectedRoute from '../../components/ProtectedRoute';
import { apiCall } from '../../lib/clerk-auth';

const ProfilePage = () => {
  const [user, setUser] = useState<any>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    apiCall('/api/profile', { method: 'GET' })
      .then(setUser)
      .catch((err) => setError(err.message));
  }, []);

  // Add form and update logic here if needed

  return (
    <ProtectedRoute>
      <div className="max-w-2xl mx-auto mt-10 p-8 bg-white rounded shadow">
        <h1 className="text-3xl font-bold mb-4">Profile</h1>
        {error && <div className="text-red-500 mb-4">{error}</div>}
        {user ? (
          <div>
            <p className="mb-2"><b>Name:</b> {user.name}</p>
            <p className="mb-2"><b>Email:</b> {user.email}</p>
            {/* Add more user info here */}
          </div>
        ) : (
          <div>Loading...</div>
        )}
      </div>
    </ProtectedRoute>
  );
};

export default ProfilePage; 
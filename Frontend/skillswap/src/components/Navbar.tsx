// components/Navbar.tsx
"use client";

import Link from 'next/link';
import { useEffect, useState } from 'react';
import { getToken, removeToken } from '../lib/clerk-auth';

const Navbar = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    setIsAuthenticated(!!getToken());
  }, []);

  const handleLogout = () => {
    removeToken();
    setIsAuthenticated(false);
    window.location.href = '/login';
  };

  return (
    <nav className="flex items-center justify-between px-6 py-4 bg-white shadow-md">
      <Link href="/" className="text-2xl font-bold text-blue-600">SkillSwap</Link>
      <div className="flex gap-4 items-center">
        <Link href="/browse" className="hover:text-blue-600">Browse</Link>
        {isAuthenticated ? (
          <>
            <Link href="/dashboard" className="hover:text-blue-600">Dashboard</Link>
            <Link href="/profile" className="hover:text-blue-600">Profile</Link>
            <button onClick={handleLogout} className="ml-2 px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600">Logout</button>
          </>
        ) : (
          <>
            <Link href="/login" className="hover:text-blue-600">Login</Link>
            <Link href="/signup" className="hover:text-blue-600">Sign Up</Link>
          </>
        )}
      </div>
    </nav>
  );
};

export default Navbar;

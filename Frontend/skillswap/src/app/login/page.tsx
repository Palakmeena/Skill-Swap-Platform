// src/app/login/page.tsx

'use client';

import React from 'react';
import Link from 'next/link';

export default function LoginPage() {
  return (
    <div className="min-h-screen bg-black text-white flex flex-col items-center justify-center">
      {/* Header */}
      <div className="absolute top-4 left-6 text-lg font-mono">Skill Swap Platform</div>
      <div className="absolute top-4 right-6">
        <Link href="/" className="border px-4 py-1 rounded-full hover:bg-white hover:text-black transition">
          Home
        </Link>
      </div>

      {/* Login Form */}
      <div className="w-full max-w-sm bg-[#121212] border border-white p-6 rounded-lg shadow-md">
        <form className="flex flex-col gap-4">
          <div>
            <label className="block mb-1">Email</label>
            <input
              type="email"
              className="w-full px-4 py-2 rounded-md bg-black border border-white text-white focus:outline-none focus:ring-2 focus:ring-white"
              placeholder="you@example.com"
            />
          </div>

          <div>
            <label className="block mb-1">Password</label>
            <input
              type="password"
              className="w-full px-4 py-2 rounded-md bg-black border border-white text-white focus:outline-none focus:ring-2 focus:ring-white"
              placeholder="••••••••"
            />
          </div>

          <button
            type="submit"
            className="w-fit self-center px-6 py-2 mt-2 border border-white rounded-full hover:bg-white hover:text-black transition"
          >
            Login
          </button>
        </form>

        <div className="mt-4 text-center">
          <Link href="/forgot-password" className="text-blue-400 text-sm hover:underline">
            Forgot username/password
          </Link>
        </div>
      </div>
    </div>
  );
}

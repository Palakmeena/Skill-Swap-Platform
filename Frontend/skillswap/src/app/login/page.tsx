"use client";
import { useState } from "react";
import { useRouter } from "next/navigation";
import { login } from "../../lib/clerk-auth";

export default function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const router = useRouter();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError("");
    try {
      await login(email, password);
      router.push("/dashboard");
    } catch (err: any) {
      setError(err.message || "Login failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-[#F5F5F5] to-[#87CEEB]">
      <form onSubmit={handleSubmit} className="bg-white p-8 rounded-lg shadow-md w-full max-w-md">
        <h2 className="text-3xl font-bold mb-6 text-center text-[#A020F0]">Login to SkillSwap</h2>
        {error && <div className="mb-4 text-red-500 text-center">{error}</div>}
        <label className="block mb-2 font-semibold text-[#1F2937]">Email</label>
        <input type="email" value={email} onChange={e => setEmail(e.target.value)} required className="w-full mb-4 px-4 py-2 border border-[#87CEEB] rounded focus:outline-none focus:ring-2 focus:ring-[#20B2AA]" />
        <label className="block mb-2 font-semibold text-[#1F2937]">Password</label>
        <input type="password" value={password} onChange={e => setPassword(e.target.value)} required className="w-full mb-6 px-4 py-2 border border-[#87CEEB] rounded focus:outline-none focus:ring-2 focus:ring-[#20B2AA]" />
        <button type="submit" disabled={loading} className="w-full bg-[#20B2AA] hover:bg-[#FF7F50] text-white font-bold py-2 px-4 rounded transition-colors duration-200">
          {loading ? "Logging In..." : "Login"}
        </button>
        <p className="mt-4 text-center text-[#1F2937]">Don't have an account? <a href="/signup" className="text-[#A020F0] hover:underline">Sign Up</a></p>
      </form>
    </div>
  );
} 
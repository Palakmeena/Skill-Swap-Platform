"use client";
import { useState, useEffect } from "react";

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080';

const mockProfiles = [
  {
    name: "Marc Demo",
    profilePhotoUrl: "https://randomuser.me/api/portraits/men/32.jpg",
    skillsOffered: ["JavaScript", "Python"],
    skillsWanted: ["Animation", "Graphic designer"],
    rating: 3.8,
  },
  {
    name: "Michell",
    profilePhotoUrl: "https://randomuser.me/api/portraits/women/44.jpg",
    skillsOffered: ["JavaScript", "Python"],
    skillsWanted: ["Animation", "Graphic designer"],
    rating: 2.5,
  },
  {
    name: "Joe Wills",
    profilePhotoUrl: "https://randomuser.me/api/portraits/men/65.jpg",
    skillsOffered: ["JavaScript", "Python"],
    skillsWanted: ["Animation", "Graphic designer"],
    rating: 4.0,
  },
];

export default function BrowsePage() {
  const [profiles, setProfiles] = useState<any[]>([]);
  const [search, setSearch] = useState("");
  const [skillFilter, setSkillFilter] = useState("");
  const [toast, setToast] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function fetchProfiles() {
      setLoading(true);
      setError("");
      try {
        const res = await fetch(`${API_BASE_URL}/api/profile/public`);
        if (!res.ok) throw new Error("Failed to fetch profiles");
        const data = await res.json();
        setProfiles(data);
      } catch (err: any) {
        setError("Could not load profiles from backend. Showing mock data.");
        setProfiles(mockProfiles);
      } finally {
        setLoading(false);
      }
    }
    fetchProfiles();
  }, []);

  const allSkills = Array.from(
    new Set(
      profiles.flatMap((p) => p.skillsOffered || [])
    )
  );

  const filteredProfiles = profiles.filter((p) => {
    const matchesSearch = (p.name || "").toLowerCase().includes(search.toLowerCase());
    const matchesSkill = skillFilter
      ? (p.skillsOffered || []).includes(skillFilter)
      : true;
    return matchesSearch && matchesSkill;
  });

  const handleRequest = (name: string) => {
    setToast(`Swap request sent to ${name}!`);
    setTimeout(() => setToast(""), 2000);
  };

  return (
    <div className="min-h-screen bg-[#F5F5F5] flex flex-col items-center py-6 px-2">
      <h2 className="text-2xl md:text-3xl font-bold mb-6 text-[#20B2AA] font-mono">Browse & Swap Skills</h2>
      <div className="w-full max-w-3xl bg-white rounded-2xl shadow-xl p-0 md:p-2 border border-[#87CEEB]">
        {/* Header */}
        <div className="flex items-center justify-between border-b border-[#87CEEB] px-6 py-4 bg-gradient-to-r from-[#20B2AA] to-[#87CEEB] rounded-t-2xl">
          <div className="flex items-center gap-4">
            <span className="text-2xl font-extrabold font-mono text-white drop-shadow">SkillSwap</span>
            <span className="ml-4 text-lg font-mono text-white/80 border-b-2 border-white pb-1">Swap Request</span>
          </div>
          <img src="https://randomuser.me/api/portraits/men/1.jpg" alt="Profile" className="w-10 h-10 rounded-full border-2 border-white shadow" />
        </div>
        {/* Filters/Search */}
        <div className="flex flex-col md:flex-row items-center gap-3 px-6 py-4 border-b border-[#87CEEB] bg-[#F5F5F5]">
          <select
            className="bg-white border-2 border-[#20B2AA] rounded-lg px-4 py-2 font-mono text-[#20B2AA] focus:outline-none focus:ring-2 focus:ring-[#20B2AA] transition"
            value={skillFilter}
            onChange={(e) => setSkillFilter(e.target.value)}
          >
            <option value="">All Skills</option>
            {allSkills.map((skill) => (
              <option key={skill} value={skill}>{skill}</option>
            ))}
          </select>
          <input
            type="text"
            placeholder="Search by name..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            className="flex-1 bg-white border-2 border-[#20B2AA] rounded-lg px-4 py-2 font-mono text-[#1F2937] placeholder-[#87CEEB] focus:outline-none focus:ring-2 focus:ring-[#20B2AA] transition"
          />
        </div>
        {/* Profile Cards */}
        <div className="flex flex-col gap-6 px-4 py-6">
          {loading && <div className="text-center text-[#20B2AA] font-mono">Loading profiles...</div>}
          {error && <div className="text-center text-[#FF7F50] font-mono mb-2">{error}</div>}
          {!loading && filteredProfiles.length === 0 && (
            <div className="text-center text-[#20B2AA] font-mono">No profiles found.</div>
          )}
          {filteredProfiles.map((profile, idx) => (
            <div
              key={idx}
              className="flex flex-col md:flex-row items-center md:items-start bg-gradient-to-br from-white via-[#F5F5F5] to-[#87CEEB] border border-[#87CEEB] rounded-xl p-4 gap-4 shadow-md hover:shadow-2xl transition-all duration-200"
            >
              <div className="flex-shrink-0 flex flex-col items-center">
                <div className="w-20 h-20 rounded-full border-4 border-[#FF7F50] overflow-hidden flex items-center justify-center bg-gray-200 shadow">
                  <img
                    src={profile.profilePhotoUrl || "https://ui-avatars.com/api/?name=" + encodeURIComponent(profile.name || "U")}
                    alt="Profile Photo"
                    className="w-full h-full object-cover"
                    onError={(e) => (e.currentTarget.src = '/favicon.ico')}
                  />
                </div>
                <span className="mt-2 text-xs font-mono text-[#20B2AA]">Profile Photo</span>
              </div>
              <div className="flex-1 flex flex-col gap-2">
                <span className="text-lg font-bold font-mono text-[#1F2937]">{profile.name}</span>
                <div className="flex flex-wrap gap-2 items-center">
                  <span className="text-xs font-mono text-[#20B2AA]">Skills Offered:</span>
                  {(profile.skillsOffered || []).map((skill: string, i: number) => (
                    <span key={i} className="bg-[#20B2AA]/10 border border-[#20B2AA] rounded-full px-3 py-1 text-xs font-mono text-[#20B2AA]">
                      {skill}
                    </span>
                  ))}
                </div>
                <div className="flex flex-wrap gap-2 items-center">
                  <span className="text-xs font-mono text-[#FF7F50]">Skill wanted:</span>
                  {(profile.skillsWanted || []).map((skill: string, i: number) => (
                    <span key={i} className="bg-[#FF7F50]/10 border border-[#FF7F50] rounded-full px-3 py-1 text-xs font-mono text-[#FF7F50]">
                      {skill}
                    </span>
                  ))}
                </div>
                <div className="flex items-center gap-2 mt-2">
                  <span className="text-xs font-mono text-[#1F2937]/60">rating</span>
                  <span className="text-xs font-mono text-[#20B2AA] font-bold">{profile.rating ? profile.rating + "/5" : "-"}</span>
                </div>
              </div>
              <button
                className="bg-gradient-to-r from-[#20B2AA] to-[#FF7F50] border-2 border-[#20B2AA] text-white font-mono px-6 py-2 rounded-lg hover:from-[#FF7F50] hover:to-[#20B2AA] hover:scale-105 transition-all mt-4 md:mt-0 shadow"
                onClick={() => handleRequest(profile.name)}
              >
                Request
              </button>
            </div>
          ))}
        </div>
      </div>
      {/* Toast */}
      {toast && (
        <div className="fixed bottom-6 left-1/2 -translate-x-1/2 bg-gradient-to-r from-[#20B2AA] to-[#FF7F50] text-white px-6 py-3 rounded-full shadow-lg font-mono text-lg animate-bounce z-50">
          {toast}
        </div>
      )}
    </div>
  );
} 
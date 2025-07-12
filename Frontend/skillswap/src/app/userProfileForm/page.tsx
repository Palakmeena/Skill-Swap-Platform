'use client';

import { useState } from 'react';

export default function ProfileForm() {
  const [formData, setFormData] = useState({
    name: '',
    location: '',
    availability: '',
    isPublic: true,
    skillsOffered: [],
    skillsWanted: [],
  });

  const [profilePhoto, setProfilePhoto] = useState<File | null>(null);
  const [offeredSkill, setOfferedSkill] = useState('');
  const [wantedSkill, setWantedSkill] = useState('');

  const handleChange = (e: any) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value,
    }));
  };

  const addSkill = (type: 'offered' | 'wanted') => {
    if ((type === 'offered' && offeredSkill.trim()) || (type === 'wanted' && wantedSkill.trim())) {
      setFormData((prev) => ({
        ...prev,
        [type === 'offered' ? 'skillsOffered' : 'skillsWanted']: [
          ...(prev[type === 'offered' ? 'skillsOffered' : 'skillsWanted'] || []),
          type === 'offered' ? offeredSkill : wantedSkill,
        ],
      }));
      if (type === 'offered') setOfferedSkill('');
      else setWantedSkill('');
    }
  };

  const removeSkill = (type: 'offered' | 'wanted', index: number) => {
    const field = type === 'offered' ? 'skillsOffered' : 'skillsWanted';
    setFormData((prev) => ({
      ...prev,
      [field]: prev[field].filter((_, i) => i !== index),
    }));
  };

  const handleSubmit = async (e: any) => {
    e.preventDefault();
    // TODO: Upload photo separately
    const res = await fetch('/api/users', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(formData),
    });

    const result = await res.json();
    console.log('Saved:', result);
  };

  return (
    <div className="max-w-3xl mx-auto p-8 bg-[var(--color-background)] text-[var(--color-text)]">
      <h1 className="text-2xl mb-6 font-bold">Edit Profile</h1>
      <form onSubmit={handleSubmit} className="space-y-4">

        {/* Name & Location */}
        <input type="text" name="name" placeholder="Name" className="w-full border p-2" onChange={handleChange} />
        <input type="text" name="location" placeholder="Location" className="w-full border p-2" onChange={handleChange} />

        {/* Skills Offered */}
        <div>
          <label className="block mb-1">Skills Offered</label>
          <div className="flex gap-2 mb-2">
            <input type="text" value={offeredSkill} onChange={(e) => setOfferedSkill(e.target.value)} className="flex-1 border p-2" />
            <button type="button" onClick={() => addSkill('offered')} className="bg-[var(--color-main)] px-4 py-1 rounded text-white">Add</button>
          </div>
          <div className="flex flex-wrap gap-2">
            {formData.skillsOffered.map((skill, idx) => (
              <span key={idx} className="bg-gray-300 px-2 py-1 rounded-full text-sm flex items-center gap-1">
                {skill}
                <button type="button" onClick={() => removeSkill('offered', idx)}>✕</button>
              </span>
            ))}
          </div>
        </div>

        {/* Skills Wanted */}
        <div>
          <label className="block mb-1">Skills Wanted</label>
          <div className="flex gap-2 mb-2">
            <input type="text" value={wantedSkill} onChange={(e) => setWantedSkill(e.target.value)} className="flex-1 border p-2" />
            <button type="button" onClick={() => addSkill('wanted')} className="bg-[var(--color-main)] px-4 py-1 rounded text-white">Add</button>
          </div>
          <div className="flex flex-wrap gap-2">
            {formData.skillsWanted.map((skill, idx) => (
              <span key={idx} className="bg-gray-300 px-2 py-1 rounded-full text-sm flex items-center gap-1">
                {skill}
                <button type="button" onClick={() => removeSkill('wanted', idx)}>✕</button>
              </span>
            ))}
          </div>
        </div>

        {/* Availability */}
        <input type="text" name="availability" placeholder="Availability (e.g. weekends)" className="w-full border p-2" onChange={handleChange} />

        {/* Public/Private */}
        <div>
          <label className="block mb-1">Profile Visibility</label>
          <select name="isPublic" value={formData.isPublic ? 'true' : 'false'} onChange={handleChange} className="w-full border p-2">
            <option value="true">Public</option>
            <option value="false">Private</option>
          </select>
        </div>

        {/* Profile Photo Upload */}
        <div>
          <label className="block mb-1">Profile Photo</label>
          <input type="file" onChange={(e) => setProfilePhoto(e.target.files?.[0] || null)} />
        </div>

        <button type="submit" className="bg-[var(--color-highlight)] px-6 py-2 text-white rounded mt-4">
          Save Profile
        </button>
      </form>
    </div>
  );
}

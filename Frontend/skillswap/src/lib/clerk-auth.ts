// JWT Auth utilities for login, signup, and API calls

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080';

export function setToken(token: string) {
  localStorage.setItem('jwt_token', token);
}

export function getToken() {
  return localStorage.getItem('jwt_token');
}

export function removeToken() {
  localStorage.removeItem('jwt_token');
}

export async function apiCall(endpoint: string, options: RequestInit = {}) {
  const token = getToken();
  const headers: Record<string, string> = {
    'Content-Type': 'application/json',
    ...(options.headers as Record<string, string>),
  };
  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }
  const response = await fetch(`${API_BASE_URL}${endpoint}`, {
    ...options,
    headers,
  });
  if (!response.ok) {
    let errorText = await response.text();
    try {
      const json = JSON.parse(errorText);
      throw new Error(json.message || errorText);
    } catch {
      throw new Error(errorText);
    }
  }
  // Try to parse as JSON, fallback to text
  try {
    return await response.json();
  } catch {
    return await response.text();
  }
}

export async function signup(name: string, email: string, password: string) {
  const res = await fetch(`${API_BASE_URL}/api/auth/register`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ name, email, password }),
  });
  if (!res.ok) {
    let errorText = await res.text();
    try {
      const json = JSON.parse(errorText);
      throw new Error(json.message || errorText);
    } catch {
      throw new Error(errorText);
    }
  }
  // Try to parse as JSON, fallback to text
  try {
    return await res.json();
  } catch {
    return await res.text();
  }
}

export async function login(email: string, password: string) {
  const res = await fetch(`${API_BASE_URL}/api/auth/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password }),
  });
  if (!res.ok) {
    let errorText = await res.text();
    try {
      const json = JSON.parse(errorText);
      throw new Error(json.message || errorText);
    } catch {
      throw new Error(errorText);
    }
  }
  const data = await res.json();
  setToken(data.token);
  return data;
} 
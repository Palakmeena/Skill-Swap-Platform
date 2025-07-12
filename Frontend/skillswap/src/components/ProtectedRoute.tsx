"use client";

import { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { getToken } from '../lib/clerk-auth';

const ProtectedRoute = ({ children }: { children: React.ReactNode }) => {
  const router = useRouter();
  useEffect(() => {
    if (!getToken()) {
      router.replace('/login');
    }
  }, [router]);
  return <>{children}</>;
};

export default ProtectedRoute; 
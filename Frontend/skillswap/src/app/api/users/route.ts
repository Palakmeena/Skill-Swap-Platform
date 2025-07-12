import { NextResponse } from 'next/server';

export async function POST(req: Request) {
  const body = await req.json();

  // Debug log
  console.log('Received user data:', body);

  // Simulate saving user (replace with actual logic or backend call)
  return NextResponse.json({ message: 'User created', data: body }, { status: 201 });
}

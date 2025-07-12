import { NextResponse } from "next/server";

export function middleware() {
  // Your middleware logic here
  return NextResponse.next();
}

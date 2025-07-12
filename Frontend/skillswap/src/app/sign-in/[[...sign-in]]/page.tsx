import { SignIn } from "@clerk/nextjs";

export default function SignInpage() {
  return (
    <div className="min-h-screen flex items-center justify-center bg-[var(--background)] text-[var(--foreground)] pt-20 px-4 sm:px-6 lg:px-8">
      <SignIn />
    </div>
  );
}

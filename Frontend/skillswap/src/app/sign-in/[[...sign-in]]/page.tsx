import { SignIn } from '@clerk/nextjs'

export default function SignInpage() {
    return (
        <div className="min-h-screen flex items-center justify-center bg-[var(--background)] text-[var(--foreground)]">
          <SignIn/>
        </div>
      );
}
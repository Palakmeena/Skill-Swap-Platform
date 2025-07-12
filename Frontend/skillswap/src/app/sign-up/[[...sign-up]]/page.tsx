import { SignUp } from '@clerk/nextjs'

export default function SignUpage() {
    return (
        <div className="flex items-center justify-center min-h-screen bg-black text-white">
          <SignUp/>
        </div>
      );
}
// app/layout.tsx
import Navbar from "@/components/Navbar";
import "./globals.css";

export const metadata = {
  title: "Skill Swap Platform",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body>
        <Navbar /> {/* ✅ Only here */}
        <main>{children}</main>
      </body>
    </html>
  );
}

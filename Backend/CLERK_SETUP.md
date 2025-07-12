# Clerk Authentication Setup Guide

This guide explains how to set up Clerk authentication with your Spring Boot backend.

## Prerequisites

1. A Clerk account and application
2. Your Clerk domain (e.g., `clerk.your-app.com`)
3. Your Clerk audience (found in your Clerk dashboard)

## Configuration Steps

### 1. Update Application Properties

Edit `src/main/resources/application.properties` and replace the placeholder values:

```properties
# Clerk Configuration
clerk.jwks.url=https://touched-honeybee-60.clerk.accounts.dev/.well-known/jwks.json
clerk.issuer=https://touched-honeybee-60.clerk.accounts.dev
clerk.audience=https://your-app.com
```

**Note**: The audience should match your application's domain. If you're running locally, you might want to use `http://localhost:3000` instead of `https://your-app.com`.

### 2. Get Your Clerk Configuration

1. Go to your Clerk Dashboard
2. Navigate to JWT Templates
3. Note down your:
   - Domain (e.g., `clerk.your-app.com`)
   - Audience (e.g., `https://your-app.com`)

### 3. Frontend Configuration

In your Next.js frontend, make sure you have:

1. Clerk environment variables in `.env.local`:
```env
NEXT_PUBLIC_CLERK_PUBLISHABLE_KEY=pk_test_dG91Y2hlZC1ob25leWJlZS02MC5jbGVyay5hY2NvdW50cy5kZXYk
CLERK_SECRET_KEY=sk_test_KINPb1oYCcjZF9sd9D4x2RA9M3zjoFrZ8NftaiZJV8
NEXT_PUBLIC_API_URL=http://localhost:8080
```

2. Update the API base URL in `src/lib/auth.ts` if needed.

## How It Works

### Authentication Flow

1. **User signs in with Clerk** in the frontend
2. **Frontend gets JWT token** from Clerk
3. **Frontend sends token** to backend as Bearer token
4. **Backend validates token** using Clerk's public keys (JWKs)
5. **Backend extracts user info** (email, name, clerkId) from token
6. **Backend creates/finds user** in database
7. **Backend returns user data** to frontend

### Backend Components

- **ClerkJwtService**: Validates JWT tokens and extracts user information
- **ClerkJwtAuthenticationFilter**: Custom filter that processes JWT tokens on each request
- **SecurityConfig**: Configures Spring Security to use the custom filter
- **AuthController**: Provides endpoints for Clerk authentication

### API Endpoints

- `POST /api/auth/clerk`: Authenticate with Clerk token
- `GET /api/auth/me`: Get current user information
- All other endpoints require authentication via Bearer token

## Testing

1. Start your Spring Boot application
2. Start your Next.js frontend
3. Sign in with Clerk in the frontend
4. The frontend will automatically authenticate with the backend
5. You can now make authenticated API calls

## Security Features

- JWT signature verification using Clerk's public keys
- Token expiration validation
- Issuer and audience validation
- Automatic user creation from Clerk data
- Stateless authentication (no sessions)

## Troubleshooting

### Common Issues

1. **Invalid JWT token**: Check your Clerk configuration in `application.properties`
2. **CORS errors**: The backend is configured to allow all origins for development
3. **User not found**: Users are automatically created from Clerk data on first authentication

### Debug Mode

To enable debug logging, add to `application.properties`:
```properties
logging.level.com.example.GrowTogether=DEBUG
logging.level.org.springframework.security=DEBUG
``` 
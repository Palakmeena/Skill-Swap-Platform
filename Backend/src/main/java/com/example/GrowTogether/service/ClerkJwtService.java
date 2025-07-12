package com.example.GrowTogether.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.text.ParseException;
import java.util.Map;

@Service
public class ClerkJwtService {

    @Value("${clerk.jwks.url}")
    private String jwksUrl;

    @Value("${clerk.issuer}")
    private String issuer;

    @Value("${clerk.audience}")
    private String audience;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, Object> validateAndExtractClaims(String token) throws Exception {
        try {
            // Parse the JWT
            SignedJWT signedJWT = SignedJWT.parse(token);
            
            // Get the key ID from the header
            String keyId = signedJWT.getHeader().getKeyID();
            
            // Fetch the JWK set from Clerk
            JWKSet jwkSet = JWKSet.load(new URL(jwksUrl));
            
            // Find the key that matches the key ID
            JWK jwk = jwkSet.getKeyByKeyId(keyId);
            if (jwk == null) {
                throw new RuntimeException("No matching key found for key ID: " + keyId);
            }
            
            // Verify the signature
            if (!(jwk instanceof RSAKey)) {
                throw new RuntimeException("Expected RSA key");
            }
            
            RSAKey rsaKey = (RSAKey) jwk;
            RSASSAVerifier verifier = new RSASSAVerifier(rsaKey);
            
            if (!signedJWT.verify(verifier)) {
                throw new RuntimeException("JWT signature verification failed");
            }
            
            // Get the claims
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
            
            // Validate issuer
            if (!issuer.equals(claimsSet.getIssuer())) {
                throw new RuntimeException("Invalid issuer: " + claimsSet.getIssuer());
            }
            
            // Validate audience
            if (!claimsSet.getAudience().contains(audience)) {
                throw new RuntimeException("Invalid audience: " + claimsSet.getAudience());
            }
            
            // Check if token is expired
            if (claimsSet.getExpirationTime() != null && 
                claimsSet.getExpirationTime().before(new java.util.Date())) {
                throw new RuntimeException("JWT token has expired");
            }
            
            // Convert claims to Map
            return claimsSet.getClaims();
            
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse JWT", e);
        } catch (Exception e) {
            throw new RuntimeException("JWT validation failed", e);
        }
    }

    public String extractEmail(String token) throws Exception {
        Map<String, Object> claims = validateAndExtractClaims(token);
        return (String) claims.get("email");
    }

    public String extractClerkId(String token) throws Exception {
        Map<String, Object> claims = validateAndExtractClaims(token);
        return (String) claims.get("sub"); // Clerk uses 'sub' for user ID
    }

    public String extractName(String token) throws Exception {
        Map<String, Object> claims = validateAndExtractClaims(token);
        return (String) claims.get("name");
    }
} 
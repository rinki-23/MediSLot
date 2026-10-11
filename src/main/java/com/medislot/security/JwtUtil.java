package com.medislot.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}") // get secret text from application.properties
	private String secret;
	@Value("${jwt.expiration}") //get expire from application.properties
	private Long expiration;
	
	// Method 1 : convert the secret text into a real signing key
	private SecretKey getKey() {
		// text -> bytes -> HMAC key
		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}
	
	// Method 2 : creates a jwt token after a successful login
	public String generateToken(String email, String role) {
		return Jwts.builder() // the machine which create empty token
				.subject(email) // who the token belongs to
				.claim("role", role) // extra data stored in the token
				.issuedAt(new Date()) // time the token was created(now)
				.expiration(new Date(System.currentTimeMillis() + expiration)) // expiry = now + configured duration
				.signWith(getKey())  // sign the token with our secret key
				.compact(); // build the final token string
	}
	
	// Method 3 : opens the token, verifies the signature and expiry, return the payload
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getKey())  // verify the signature with our secret key
				.build()   //build the parser
				.parseSignedClaims(token)  //throw the exception if token is fake, tampered or expired
				.getPayload();  // return the data stored inside the token
	}
	
	// Method 4 : read the email(subject) from the token
	public String extractEmails(String token) {
		return extractAllClaims(token).getSubject();
	}
	
	// Method 5: check whether the token is valid for the given user
	public boolean isTokenValid(String token, UserDetails userDetails) {
		try {
			String email = extractEmails(token);
			return email.equals(userDetails.getUsername());
			
		}catch(JwtException | IllegalArgumentException e) {
			return false;
		}
	}
}

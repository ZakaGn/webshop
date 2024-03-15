package com.example.webshop.security;

import java.util.Date;
import com.example.webshop.exception.apiException.badRequestException.InvalidJwtTokenException;
import io.jsonwebtoken.*;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenProvider{
	@Value("${application.security.jwt.expiration}")
	private int expirationInMs;
	@Value("${application.security.jwt.secret-key}")
	private final String SECRET_KEY = "2B7E151628AED2A6ABF7158809CF4F3C2B7E151628AED2A6ABF7158809CF4F3C";
	private final byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);

	public String generateToken(Authentication authentication){
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		JwtBuilder builder = Jwts.builder()
		                         .setSubject(authentication.getName())
		                         .setIssuedAt(new Date(nowMillis))
		                         .setExpiration(new Date(nowMillis + expirationInMs))
		                         .claim("authorities", authentication.getAuthorities())
		                         .signWith(signingKey, signatureAlgorithm);
		return builder.compact();
	}

	public String getEmailFromJWT(String token){
		return Jwts.parserBuilder().setSigningKey(apiKeySecretBytes).build().parseClaimsJws(token).getBody().getSubject();
	}

	public void validateToken(String token){
		try{
			Jwts.parserBuilder().setSigningKey(apiKeySecretBytes).build().parseClaimsJws(token);
		}catch(SecurityException ex){
			throw new InvalidJwtTokenException("Invalid JWT signature");
		}catch(MalformedJwtException ex){
			throw new InvalidJwtTokenException("Invalid JWT token");
		}catch(ExpiredJwtException ex){
			throw new InvalidJwtTokenException("Expired JWT token");
		}catch(UnsupportedJwtException ex){
			throw new InvalidJwtTokenException("Unsupported JWT token");
		}catch(IllegalArgumentException ex){
			throw new InvalidJwtTokenException("JWT claims string is empty");
		}
	}

}
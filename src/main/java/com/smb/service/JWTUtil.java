package com.smb.service;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JWTUtil {
	@Value("${app.secret.key}")
    private String secretKey;
    
	public String generateToken(String subject) {
		return Jwts
        		.builder()
        		.setIssuer("SMB")
        		.setSubject(subject)
        		.setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
                .signWith(
                		SignatureAlgorithm.HS512, 
                		Base64.getEncoder().encode(secretKey.getBytes()))
                .compact();
    }
	
	public Claims getClaims(String token) {
    	System.out.println("JSON:- " + token);
    	String[] splited = token.split("\\s+");
    	token = splited[1];
        return Jwts.parser().setSigningKey(Base64.getEncoder().encode(secretKey.getBytes())).parseClaimsJws(token).getBody();
    }

    public boolean isValidToken(String token) {
        return getClaims(token).getExpiration().after(new Date(System.currentTimeMillis()));
    }

    public boolean isValidToken(String token, String userName) {
        String getTokenuserName = getClaims(token).getSubject();
        return (userName.equals(getTokenuserName) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
    }

    public Date getExpirationDate(String token) {
		return getClaims(token).getExpiration();
	}
	
	public String getSubject(String token) {
		return getClaims(token).getSubject();
	}

}

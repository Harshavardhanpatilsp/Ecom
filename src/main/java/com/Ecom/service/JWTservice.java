package com.Ecom.service;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.Ecom.Model.User;
import com.Ecom.repo.TokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@EnableJpaRepositories
@Service
public class JWTservice {
	
	@Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.access-token-expiration}")
    private long accessTokenExpire;

    @Value("${application.security.jwt.refresh-token-expiration}")
    private long refreshTokenExpire;
    
    @Autowired
    private final TokenRepository tokenRepository;
    
	public JWTservice(TokenRepository tokenRepository) {
		this.tokenRepository = tokenRepository;
	}

	private final String SECRET_KEY="ea8c7bdf5a55fd10854c98783225509fa9b64591535a500a49af696dbc42f77e";
	
	public String generatetoken(User user) {
		
		String token = Jwts.builder().setSubject(user.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 24*60*60)).signWith(getsignkey()).compact();
		return token;
		
	}
	
	private SecretKey getsignkey() {
		byte[] keybytes = Decoders.BASE64URL.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keybytes);
	}
	
//	private Claims extracAllclaims(String token) {
//		return Jwts.parser().verifyWith(getsignkey()).build()
//				.parseSignedClaims(	token).getPayload();
//		}
	private Claims extracAllclaims(String token) {
	    return Jwts.parserBuilder()
	            .setSigningKey(getsignkey()) // Use setSigningKey instead
	            .build()
	            .parseClaimsJws(token)
	            .getBody();
	}

	
	public <T> T extractclaims(String token, Function<Claims, T> resolver) {
		Claims claims = extracAllclaims(token);
		return resolver.apply(claims);
	}
	
	public String extractUsername(String token) {
		return extractclaims(token, Claims::getSubject);
	}
	
	public boolean isvalid(String token,UserDetails User) {
			String username = extractUsername(token);
			return username.equals(User.getUsername()) & !istokenExpired(token) ;
	}

	private boolean istokenExpired(String token) {
		// TODO Auto-generated method stub
		return extractExpirationtoken(token).before(new Date());
	}

	private Date extractExpirationtoken(String token) {
		// TODO Auto-generated method stub
		return extractclaims(token, Claims::getExpiration);
	}
	
	 public String generateAccessToken(User user) {
	        return generateToken(user, accessTokenExpire);
	    }

	    public String generateRefreshToken(User user) {
	        return generateToken(user, refreshTokenExpire );
	    }
	    
	    private String generateToken(User user, long expireTime) {
	        String token = Jwts
	                .builder()
	                .setSubject(user.getUsername())
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + expireTime ))
	                .signWith(getSigninKey())
	                .compact();

	        return token;
	    }
	    
	    private SecretKey getSigninKey() {
	        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
	        return Keys.hmacShaKeyFor(keyBytes);
	    }

	    public boolean isValidRefreshToken(String token, User user) {
	        String username = extractUsername(token);

	        boolean validRefreshToken = tokenRepository
	                .findByRefreshToken(token)
	                .map(t -> !t.isLoggedOut())
	                .orElse(false);

	        return (username.equals(user.getUsername())) && !istokenExpired(token) && validRefreshToken;
	    }

}

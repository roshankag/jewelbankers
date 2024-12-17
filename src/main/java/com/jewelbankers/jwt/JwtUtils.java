package com.jewelbankers.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.jewelbankers.dto.JwtLogin;
import com.jewelbankers.dto.LoginResponse;
import com.jewelbankers.entity.User;
import com.jewelbankers.services.UserDetailsImpl;
import com.jewelbankers.services.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    private AuthenticationManager authenticationManager;
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${bezkoder.app.jwtSecret}")
  private String jwtSecret;

  @Value("${bezkoder.app.jwtExpirationMs}")
  private int jwtExpirationMs;


  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  public String generateJwtToken(User user) {


      UserDetailsImpl userPrincaple = userDetailsService.loadUserByEmail(user.getEmail());

      //System.out.println(user.getEmail());
   // Adding claims to the token
      Map<String, Object> claims = new HashMap<>();
      claims.put("id", user.getId());
      claims.put("username", user.getUsername());
      claims.put("email", user.getEmail());
      claims.put("roles", user.getRoles());
      claims.put("userDatabaseName", user.getUserDatabaseName().toLowerCase());
		/*
		 * System.out.println("Dta:"+userPrincaple.getUserDatabaseName());
		 * System.out.println(userPrincaple.getUsername());
		 */
    return Jwts.builder()
            .setSubject(userPrincaple.getUsername())
            .setClaims(claims)
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(key(), SignatureAlgorithm.HS256)
            .compact();
}

  public String generateJwtToken(Authentication authentication) {

    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
    
 // Adding claims to the token
    Map<String, Object> claims = new HashMap<>();
    claims.put("id", userPrincipal.getId());
    claims.put("username", userPrincipal.getUsername());
    claims.put("email", userPrincipal.getEmail());
    claims.put("userDatabaseName", userPrincipal.getUserDatabaseName());
    claims.put("roles", userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

    return Jwts.builder()
    	.setClaims(claims)
        .setSubject((userPrincipal.getUsername()))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(key(), SignatureAlgorithm.HS256)
        .compact();
  }
  
  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key()).build()
               .parseClaimsJws(token).getBody().getSubject();
  }

  public String getRequestUserdatabaseFromJwtToken(String token) {
	  return Jwts.parserBuilder()
	            .setSigningKey(key())
	            .build()
	            .parseClaimsJws(token)
	            .getBody()
	            .get("userDatabaseName", String.class); // Extract the "databasename" claim
  }
  
  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
      return true;
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }

  public LoginResponse login(JwtLogin jwtLogin) throws Exception{
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtLogin.getEmail(),
                jwtLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = generateJwtToken(authenticate);
        return new LoginResponse(token);
    }
}
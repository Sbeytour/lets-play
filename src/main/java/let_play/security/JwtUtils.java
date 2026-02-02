package let_play.security;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import let_play.entities.User;
import io.jsonwebtoken.JwtException;

@Service
public class JwtUtils {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public String generateToken(User user) {
        try {
            String email = user.getEmail();
            Date now = new Date();
            Date expireDate = new Date(now.getTime() + jwtExpiration);

            return Jwts.builder().setSubject(email).setIssuedAt(now).setExpiration(expireDate)
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            throw new JwtException("Failed to generate JWT token", e);
        }
    }

    private Key getSignInKey() {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            throw new JwtException("Invalid JWT signing key", e);
        }
    }

    public String extractUserEmail(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new JwtException("JWT token has expired");
        } catch (MalformedJwtException e) {
            throw new JwtException("Invalid JWT token format");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            throw new JwtException("JWT token is empty or null");
        } catch (Exception e) {
            throw new JwtException("Failed to extract user email from token", e);
        }
    }

    public boolean validateToken(String token) {
        try {
            if (token == null || token.trim().isEmpty()) {
                return false;
            }

            return !isTokenExpired(token);

        } catch (ExpiredJwtException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new JwtException("Invalid JWT token", e);
        }
    }

}

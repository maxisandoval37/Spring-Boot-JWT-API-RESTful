package ar.dev.maxisandoval.springbootjwt.service;

import ar.dev.maxisandoval.springbootjwt.models.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class TokenJWTService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;
    @Value("${security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String generateToken(final Usuario usuario) {
        return buildToken(usuario, jwtExpiration);
    }

    public String generateRefreshToken(final Usuario usuario) {
        return buildToken(usuario, refreshExpiration);
    }

    private String buildToken(final Usuario usuario, final long expiration) {

        String tokenRet = Jwts
                .builder()
                .claims(Map.of("name", usuario.getName()))
                .subject(usuario.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();

        log.info("***buildToken: ".concat(tokenRet).concat(" ***"));

        return tokenRet;
    }

    public boolean isTokenValid(String token, Usuario usuario) {
        final String username = extractUsername(token);
        return (username.equals(usuario.getEmail())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    private SecretKey getSignInKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
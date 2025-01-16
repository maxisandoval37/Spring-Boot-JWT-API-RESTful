package ar.dev.maxisandoval.springbootjwt.service;

import ar.dev.maxisandoval.springbootjwt.models.*;
import ar.dev.maxisandoval.springbootjwt.records.*;
import ar.dev.maxisandoval.springbootjwt.utils.TokenJWTUtils;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioService usuarioService;
    private final TokenJWTService tokenJWTService;
    private final TokenJWTUtils tokenJWTUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public TokenJWTResponse register(final RegisterRequest request) {
        final Usuario usuario = Usuario.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        final Usuario savedUser = usuarioService.saveUser(usuario);
        final String jwtToken = tokenJWTUtils.generateToken(savedUser);
        final String refreshToken = tokenJWTUtils.generateRefreshToken(savedUser);

        saveUserToken(savedUser, jwtToken);
        return new TokenJWTResponse(jwtToken, refreshToken);
    }

    public TokenJWTResponse authenticate(final AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        final Usuario usuario = usuarioService.findUserByEmail(request.email());
        final String accessToken = tokenJWTUtils.generateToken(usuario);
        final String refreshToken = tokenJWTUtils.generateRefreshToken(usuario);

        revokeAllUserTokens(usuario);
        saveUserToken(usuario, accessToken);
        return new TokenJWTResponse(accessToken, refreshToken);
    }

    private void saveUserToken(Usuario usuario, String jwtToken) {
        final TokenJWT token = TokenJWT.builder()
                .usuario(usuario)
                .token(jwtToken)
                .tokenType(TokenJWT.TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenJWTService.saveTokenJWT(token);
    }

    private void revokeAllUserTokens(final Usuario usuario) {// En nuestra lógica, solo permite un usuario logeado a la vez
        final List<TokenJWT> validUserTokens = tokenJWTService.findAllValidTokenByUser(usuario.getId());
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> {
                token.setIsExpired(true);
                token.setIsRevoked(true);
            });
            tokenJWTService.saveAllTokenJWT(validUserTokens);
        }
    }

    public TokenJWTResponse refreshToken(@NotNull final String authentication) {
        if (!authentication.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid auth header");
        }

        String refreshToken = authentication.substring(7);
        String userEmail = tokenJWTUtils.extractUsername(refreshToken);
        if (userEmail == null || !tokenJWTUtils.isTokenValid(refreshToken, usuarioService.findUserByEmail(userEmail))) {
            return null;
        }

        Usuario usuario = usuarioService.findUserByEmail(userEmail);
        String accessToken = tokenJWTUtils.generateRefreshToken(usuario);

        revokeAllUserTokens(usuario);
        saveUserToken(usuario, accessToken);

        return new TokenJWTResponse(accessToken, refreshToken);
    }
}
package ar.dev.maxisandoval.springbootjwt.controllers;

import ar.dev.maxisandoval.springbootjwt.records.*;
import ar.dev.maxisandoval.springbootjwt.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<TokenJWTResponse> register(@RequestBody RegisterRequest request) {
        final TokenJWTResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenJWTResponse> authenticate(@RequestBody AuthRequest request) {
        final TokenJWTResponse response = authService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public TokenJWTResponse refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authentication) {
        return authService.refreshToken(authentication);
    }

}
package com.match.matching.auth;

import com.match.matching.auth.dto.AuthResponse;
import com.match.matching.auth.dto.LoginRequest;
import com.match.matching.auth.dto.RegisterRequest;
import com.match.matching.security.JwtService;
import com.match.matching.user.User;
import com.match.matching.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        User user = userService.createUser(request.email(), request.password());


        String accessToken = jwtService.generateToken(user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());


        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        User user = userService.authenticate(request.email(), request.password());


        String accessToken = jwtService.generateToken(user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());


        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new IllegalArgumentException("Refresh Token отсутствует");
        }

        String email = jwtService.extractEmail(refreshToken);
        if (email != null) {
            User user = userService.findByEmail(email);

            String newAccessToken = jwtService.generateToken(user.getEmail());
            return ResponseEntity.ok(new AuthResponse(newAccessToken, refreshToken));
        }

        throw new IllegalArgumentException("Невалидный Refresh Token");
    }
}
package com.gestNutri.controller;

import com.gestNutri.dto.resquest.AuthResquest;
import com.gestNutri.dto.response.AuthResponse;
import com.gestNutri.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
            JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthResquest request) {
        String identifiant = "admin1".equalsIgnoreCase(request.email())
            ? "admin1@gestnutri.local"
            : request.email();
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(identifiant, request.motDePasse()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(identifiant);
        return new AuthResponse(jwtUtil.genererToken(userDetails));
    }
}

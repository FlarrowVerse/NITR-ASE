package com.ase.rrts.controller;

import com.ase.rrts.model.AuthRequest;
import com.ase.rrts.model.AuthResponse;
import com.ase.rrts.model.ResponseMessage;
import com.ase.rrts.security.CustomUserDetailsService;
import com.ase.rrts.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints related to authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil; // Utility class for generating and validating JWTs

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Username/Password based login  system")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            // Authenticate the user
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            // Load the user details
            var userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

            // Generate a JWT token
            String token = jwtUtil.generateToken(userDetails);

            ResponseCookie authCookie = ResponseCookie.from("token", token)
            .httpOnly(true).path("/").build();

            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            ResponseCookie roleCookie = ResponseCookie.from("role", roles.get(0))
            .httpOnly(false).path("/").build();

            return ResponseEntity.ok()
            .header("Set-Cookie", authCookie.toString())
            .header("Set-Cookie", roleCookie.toString())
            .body(new AuthResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    @GetMapping("/logout")
    @Operation(summary = "Logout", description = "Logging out the current user")
    public ResponseEntity<ResponseMessage> logout(@NonNull HttpServletRequest httpServletRequest) {
        try {
            
            // Logic to invalidate the token server-side
            String token = httpServletRequest.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7); // Extract the token
    
                // Invalidate the token in your server-side token management logic
                // For example, remove it from a database or a token blacklist
            }

            ResponseCookie authCookie = ResponseCookie.from("token", "")
            .maxAge(0).httpOnly(true).path("/").build();

    
            return ResponseEntity.ok()
            .header("Set-Cookie", authCookie.toString())
            .body(new ResponseMessage("success", "Logged out successfully"));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ResponseMessage("failure", "Logged out failed"));
        }
    }
}

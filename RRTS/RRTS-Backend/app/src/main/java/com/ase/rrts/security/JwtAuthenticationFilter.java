package com.ase.rrts.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.ase.rrts.util.JwtUtil;

import io.micrometer.common.lang.NonNull;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Get the Authorization header
        String authHeader = request.getHeader("Authorization");

        // Check if the header contains a Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Extract the token

            String username = jwtUtil.extractUsername(token);

            if (username != null && !username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Retrieve the UserDetails from the SecurityContext
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);;
    
                // Check if the token is valid
                if (this.jwtUtil.validateToken(token, userDetails)) {
                    // If the token is valid, create and set an authentication object in the
                    // SecurityContext
                    // Create an authentication object
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
    
                    // Set the authentication object in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
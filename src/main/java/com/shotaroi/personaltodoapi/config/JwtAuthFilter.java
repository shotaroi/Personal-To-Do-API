package com.shotaroi.personaltodoapi.config;

import com.shotaroi.personaltodoapi.auth.JwtService;
import com.shotaroi.personaltodoapi.user.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepo;

    public JwtAuthFilter(JwtService jwtService, UserRepository userRepo) {
        this.jwtService = jwtService;
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        try {
            String email = jwtService.validateAndGetSubject(token);

            // Optional: ensure user still exists
            if (userRepo.findByEmail(email).isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }

            Authentication auth = new UsernamePasswordAuthenticationToken(email, null, List.of());
            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception ignored) {
            // Invalid token -> act as unauthenticated
        }

        filterChain.doFilter(request, response);
    }
}


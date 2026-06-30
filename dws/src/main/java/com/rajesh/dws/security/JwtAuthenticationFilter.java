package com.rajesh.dws.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.
        UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.
        SecurityContextHolder;
import org.springframework.security.web.authentication.
        WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rajesh.dws.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.rajesh.dws.repository.UserRepository;
import com.rajesh.dws.entity.User;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
private UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)

            throws ServletException, IOException {
                System.out.println("JWT Filter Executed");
        String authHeader =
                request.getHeader("Authorization");
                System.out.println("Auth Header: " + authHeader);

        if(authHeader == null ||
           !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token =
                authHeader.substring(7);

        if(jwtService.isTokenValid(token)) {

            String email =
                    jwtService.extractEmail(token);

            User user = userRepository
        .findByEmail(email)
        .orElseThrow();

List<GrantedAuthority> authorities =
        List.of(
                new SimpleGrantedAuthority(
                        "ROLE_" + user.getRole()
                )
        );

UsernamePasswordAuthenticationToken auth =
        new UsernamePasswordAuthenticationToken(
                email,
                null,
                authorities
        );

            auth.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
package com.arpita.nexus.security.filter;

import com.arpita.nexus.security.jwt.JwtService;
import com.arpita.nexus.security.userdetails.CustomUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);

            return;
        }

        String jwt = authHeader.substring(7);
        System.out.println("========== JWT FILTER ==========");
        System.out.println("JWT Received: " + jwt);

        try {

            if (!jwtService.isTokenValid(jwt)) {

                filterChain.doFilter(request, response);

                return;
            }

            String username = jwtService.extractUsername(jwt);
            System.out.println("Username from JWT: " + username);

            if (username != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

                System.out.println("User loaded: " + userDetails.getUsername());
                System.out.println("Authorities: " + userDetails.getAuthorities());


                if (jwtService.isTokenValid(jwt, userDetails)) {

                    System.out.println("JWT is VALID");

                    UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());

                    authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                            .buildDetails(request));

                    SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);

                    System.out.println("Authentication stored in SecurityContext");
                }
            }

        } catch (JwtException ex) {
        ex.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}

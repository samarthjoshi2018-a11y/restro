package com.example.restro.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.restro.Security.services.CustomUserDetailService;
import com.example.restro.Security.services.CustomUserDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

    @Autowired
    private JwtUtil jutil;

    @Autowired
    private CustomUserDetailService customuds;


    @Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

    String token = null;
    String useremail = null;
    String requestURI = request.getRequestURI();

   

    if (request.getCookies() != null) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("jwt")) {
                token = cookie.getValue();
                System.out.println("JWT token found for: " + requestURI);
            }
        }
    } else {
        System.out.println("No cookies for: " + requestURI);
    }

    try {
        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            useremail = jutil.extractEmail(token);
            System.out.println("Email from token for " + requestURI + ": " + useremail);

            UserDetails ud = customuds.loadUserByUsername(useremail);
            System.out.println("User loaded for " + requestURI + " with roles: " + ud.getAuthorities());

            if (jutil.validateToken(token, (CustomUserDetails) ud)) {
                UsernamePasswordAuthenticationToken auth = 
                    new UsernamePasswordAuthenticationToken(useremail, null, ud.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                System.out.println("Token validation failed for: " + requestURI);
            }
        } else {
            System.out.println("No valid token for: " + requestURI);
        }
    } catch (Exception e) {
        System.out.println("JWT error for " + requestURI + ": " + e.getMessage());
    }

    filterChain.doFilter(request, response);
    
}
    

}
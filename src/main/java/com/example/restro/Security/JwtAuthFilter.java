package com.example.restro.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.restro.services.CustomUserDetailService;

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

        String jwt=null;
        String useremail=null;

        if(request.getCookies()!=null){
            for(Cookie cookie : request.getCookies()){
                if(cookie.getName().equals("jwt")){
                    jwt=cookie.getValue();
                }
            }
        }   


        
        if(jwt!=null){
            useremail=jutil.extractEmail(jwt);
        }





        if(useremail!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userdetails=customuds.loadUserByUsername(useremail);

            if(jutil.validateToken(jwt, userdetails)){
                UsernamePasswordAuthenticationToken authtoken=new UsernamePasswordAuthenticationToken(userdetails,null,userdetails.getAuthorities());
           
                authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authtoken);
            }       


        }


        filterChain.doFilter(request, response);

        
    
    }


    

}
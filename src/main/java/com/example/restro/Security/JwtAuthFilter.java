package com.example.restro.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.restro.services.CustomUserDetailService;
import com.example.restro.services.CustomUserDetails;

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

        String token=null;
        String useremail=null;
        System.out.println("filter request");

        
        if(request.getCookies()!=null){
            for(Cookie cookie : request.getCookies()){
                if(cookie.getName().equals("jwt")){
                    token=cookie.getValue();
                }
            }
        }   



        try{


            if(token!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                useremail=jutil.extractEmail(token);

                UserDetails ud=customuds.loadUserByUsername(useremail);

                if(jutil.validateToken(token,(CustomUserDetails)ud)){
                    UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(useremail, null,ud.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }


        }catch(Exception e){
            System.out.println("jwt invalid: "+e.getMessage());

        }




       

        filterChain.doFilter(request, response);

        
    
    }


    

}
package com.example.restro.services;

import java.util.Collection;
import  java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class CustomUserDetails  implements  UserDetails{

    private final String email;
    private final String password;
    private final String userType;

    public CustomUserDetails(String email,String password,String usertype){
        this.email=email;
        this.password=password;
        this.userType=usertype;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+userType));
    }

    @Override
    public  String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
    public String getUserType(){
        return this.userType;
    }

    

    
}

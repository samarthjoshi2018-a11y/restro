package com.example.restro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.restro.models.User;
import com.example.restro.repositories.UserRepo;

import jakarta.persistence.DiscriminatorValue;

@Service
public class CustomUserDetailService implements UserDetailsService{

    @Autowired
    private UserRepo urepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user=urepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found"));

        String userType=user.getClass().getAnnotation(DiscriminatorValue.class).value();


        return new CustomUserDetails(user.getEmail(),user.getPassword(),userType);
    }

    


}

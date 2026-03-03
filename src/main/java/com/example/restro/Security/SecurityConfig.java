package com.example.restro.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtauthfilter;


    @Bean
    public  SecurityFilterChain filterChain(HttpSecurity http )throws Exception{

        http
            .logout(logout->logout.disable())
            .csrf(csrf->csrf.disable())
            .cors(cors->cors.disable())
            .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
             .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/", "/login","/register", "/categories", "/style.css").permitAll()
                .requestMatchers(HttpMethod.POST, "/login","/register").permitAll()
                .requestMatchers("/categories/**","/orderupdate/cart","/orderupdate").hasRole("CUSTOMER")
                .requestMatchers(HttpMethod.POST, "/orderupdate/**","/orderupdate").hasRole("CUSTOMER")
                .requestMatchers(HttpMethod.GET, "/additem").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/additem").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form->form.disable())
            .exceptionHandling(ex->ex.authenticationEntryPoint((request, response, authException) -> {
                response.sendRedirect("/login");
            }))
            .addFilterBefore(jwtauthfilter,UsernamePasswordAuthenticationFilter.class);

            return  http.build();




    }

    @Bean
    public PasswordEncoder passencoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)throws Exception{
        return config.getAuthenticationManager();
    }

    
    
}
   

    


    

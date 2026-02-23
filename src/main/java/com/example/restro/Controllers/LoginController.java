package com.example.restro.Controllers;
import org.springframework.beans.factory.annotation.Autowired;  // ✅ CORRECTimport org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.restro.Security.JwtUtil;
import com.example.restro.Security.services.CustomUserDetailService;
import com.example.restro.Security.services.CustomUserDetails;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;



@Controller
public class LoginController {

    @Autowired
    private AuthenticationManager authmanager;
     
    @Autowired
    private JwtUtil jwtutil;


    
    @Autowired
    private CustomUserDetailService uds;

    @GetMapping("/login")
    public String getLogin(@RequestParam(value="error",required=false)String error,Model model) {
        if(error!=null){
            model.addAttribute("error","true");
        }
        return "login";
    }


   @PostMapping("/login")
   public String getLogin(@RequestParam("email") String email,@RequestParam("password") String password,HttpServletResponse res){
        try {


            Authentication authentication=authmanager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
            System.out.println("entered admin login mapping");

            CustomUserDetails ud=(CustomUserDetails)authentication.getPrincipal();

            
            String jwt=jwtutil.generateToken(ud);


            Cookie cookie=new Cookie("jwt", jwt);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(60*60);
            
            res.addCookie(cookie);


            System.out.println("cookie sent");
            
            return "redirect:/categories";
            
        }catch(AuthenticationException e){
            System.out.println("authentication failed: "+e.getMessage());
            return "redirect:/login?error=true";
        }


       
   }

   @GetMapping("/logout")
    public String logout(
                        HttpServletResponse response) {

        // Clear Spring Security context
        SecurityContextHolder.clearContext();

        // Delete JWT cookie
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);   // delete immediately
        response.addCookie(cookie);

        // Redirect to home page
        return "redirect:/login";
    }
    
    
        
    
}

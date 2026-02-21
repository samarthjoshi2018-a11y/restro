package com.example.restro.Controllers;
import org.springframework.beans.factory.annotation.Autowired;  // ✅ CORRECTimport org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.restro.Security.JwtUtil;
import com.example.restro.services.CustomUserDetailService;

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
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                       Model model) {
        System.out.println("Entered login");

        if(error!=null ){
        model.addAttribute("error", "true");
        }
        return "login";
    }


   @PostMapping("/adminlogin")
   public String getAdminlogin(@RequestParam("adminemail") String email,@RequestParam("adminpassword") String password,HttpServletResponse res){
        try {
            authmanager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
            System.out.println("entered admin login mapping");

            String jwt=jwtutil.generateToken(email, "ADMIN");
            Cookie cookie=new Cookie("jwt", jwt);
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(60*60);
            
            res.addCookie(cookie);
            
            return "redirect:/categories";
            
        } catch (BadCredentialsException e) {
            System.out.println("an exception occured");
            return "redirect:/login?error=true";
        }

       
   }

    @PostMapping("/customerlogin")
    public String getCustomerLogin() {
        return "redirect:/categories";
    }
    
    
        
    
}

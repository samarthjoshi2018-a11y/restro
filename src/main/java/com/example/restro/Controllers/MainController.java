package com.example.restro.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;






@Controller
public class MainController {

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }
    
    
    
    @GetMapping("/register")
    public String getRegister() {
        return "register";
    } 
    
    
    
}

package com.example.restro.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.restro.models.Customer;
import com.example.restro.repositories.CustomerRepo;

import jakarta.validation.Valid;







@Controller
public class MainController {
    
    @Autowired
    CustomerRepo crepo;
    
    @GetMapping("/")
    public String getIndex() {
        return "index";
    }
    
    
    
    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    } 

    @PostMapping("/register")
    public String customerRegister(@Valid @ModelAttribute("customer") Customer customer,RedirectAttributes redirectAttributes) {

        boolean exists = crepo.existsByEmail(customer.getEmail());
        System.out.println("exists: " + exists);

        if(exists){
            redirectAttributes.addFlashAttribute("error", "Email already exists!");
            return "redirect:/register";
        }

        crepo.save(customer);
        redirectAttributes.addFlashAttribute("message", "Registration successful! Please login.");
        return "redirect:/login";
    }
     @GetMapping("/categories")
    public String viewCategories(){
        return "categories";

    }
    

    
    
}

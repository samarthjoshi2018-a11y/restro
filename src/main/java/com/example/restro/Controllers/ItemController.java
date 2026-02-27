package com.example.restro.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.restro.models.Item;
import com.example.restro.repositories.ItemRepo;




@Controller
public class ItemController {

    @Autowired
    ItemRepo itemRepo;

    @GetMapping("/additem")
    public String getMethodName(Model model) {
        model.addAttribute("item", new Item());
        return "additem";
    }

    @PostMapping("/additem")
    public String postMethodName(@ModelAttribute("item") Item item,RedirectAttributes redirectAttributes) {
        if(itemRepo.findByItemName(item.getItemName()) != null) {
            redirectAttributes.addFlashAttribute("message", "Item already exists!");
            return "redirect:/additem";
        }
        itemRepo.save(item);
        redirectAttributes.addFlashAttribute("message", "Item added successfully!");
        return "redirect:/additem";
    }

    

    

}

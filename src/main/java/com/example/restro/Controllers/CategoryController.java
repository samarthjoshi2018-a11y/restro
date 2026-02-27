package com.example.restro.Controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.restro.models.Item;
import com.example.restro.repositories.ItemRepo;


@RequestMapping("/categories")
@Controller
public class CategoryController {

    @Autowired
    private ItemRepo itemRepo;

   

    @GetMapping("/{categoryname}")
    public String displayCategory(@PathVariable String categoryname, Model model){
        List<Item>items;
        items = itemRepo.findByCategory(categoryname);
        model.addAttribute("items", items);
        model.addAttribute("categoryname", categoryname);

        return "display";
    }
   

}

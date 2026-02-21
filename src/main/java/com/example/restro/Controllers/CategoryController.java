package com.example.restro.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CategoryController {

    @GetMapping("/categories")
    public String viewCategories(){
        return "categories";

    }

    @GetMapping("/north-indian")
    public String displayNorthIndian(){
        return "north-indian";
    }
    @GetMapping("/south-indian")
    public String displaySouthIndian(){
        return "south-indian";
    }
    @GetMapping("/sweets")
    public String displaySweets(){
        return "north-indian";
    }
    @GetMapping("/ice-creams")
    public String displayIceCreams(){
        return "north-indian";
    }
    @GetMapping("/juices")
    public String displayJuices(){
        return "juices";
    }
    

}

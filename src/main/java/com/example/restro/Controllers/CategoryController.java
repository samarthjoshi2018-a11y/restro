package com.example.restro.Controllers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.restro.models.Item;
import com.example.restro.models.OrderInfo;
import com.example.restro.orderservice.OrderService;
import com.example.restro.repositories.CustomerRepo;
import com.example.restro.repositories.ItemRepo;


@RequestMapping("/categories")
@Controller
public class CategoryController {

    @Autowired
    private ItemRepo itemRepo;

    
    @Autowired
    private CustomerRepo crepo;

    @Autowired
    private OrderService os;

   

    @GetMapping("/{categoryname}")
    public String displayCategory(@PathVariable String categoryname, Model model,Authentication auth){
        List<Item>items;
        items = itemRepo.findByCategory(categoryname);
        model.addAttribute("items", items);
        model.addAttribute("categoryname", categoryname);
        String cemail=auth.getName();
        int cid=crepo.findByEmail(cemail).getId();


        List<OrderInfo> cartItems = os.getCart(cid);
        Map<Integer,OrderInfo> orderInfoMap=new HashMap<>();
        for(OrderInfo info:cartItems){
            orderInfoMap.put(info.getItemId(), info);

        }

        model.addAttribute("orderInfoMap",orderInfoMap);
        
        

        return "display";
    }
   

}

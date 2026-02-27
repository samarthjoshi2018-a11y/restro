package com.example.restro.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.restro.feign.feighInterface;
import com.example.restro.repositories.CustomerRepo;
import com.example.restro.repositories.ItemRepo;
import com.example.restro.orderservice.OrderService;


@Controller
@RequestMapping("/orderupdate")
public class OrderController {

        public feighInterface fin;


        @Autowired
        public CustomerRepo crepo;

        @Autowired
        public ItemRepo irepo;

        @Autowired
        public OrderService os;


        

        @PostMapping("/addtocart")
        public String addItem(@RequestParam int itemId,Authentication auth) {
            String email = auth.getName();
            int cid = crepo.findByEmail(email).getId();
            String message= os.addToCart(cid, itemId);
            return "categories";
            
        }
    
        @PostMapping("/incrementitem")
        public String incrementItem( @RequestParam int itemId, Authentication auth) {
            String email = auth.getName();
            int cid = crepo.findByEmail(email).getId();
            String message = os.incrementItem(cid, itemId);
            return "categories";
        }
    
        @PostMapping("/decrementitem")
        public String decrementItem( @RequestParam int itemId, Authentication auth) {
            String email = auth.getName();
            int cid = crepo.findByEmail(email).getId();
            String message = os.decrementItem(cid, itemId);
            return "categories";
        
        }
    
        @PostMapping("/deleteitem")
        public String deleteItem( @RequestParam int itemId, Authentication auth) {
            String email = auth.getName();
            int cid = crepo.findByEmail(email).getId();
            String message = os.deleteItem(cid, itemId);
            return "categories";
        }
    
        @PostMapping("/placeorder")
        public String placeOrder( Authentication auth) {
            String email = auth.getName();
            int cid = crepo.findByEmail(email).getId();
            String message = os.placeOrder(cid);
            return "categories";
        }

        @GetMapping("/cart")
        public String viewCart(Model model) {


            return "cart";
        }
        
    
}

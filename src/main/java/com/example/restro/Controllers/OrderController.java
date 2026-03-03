package com.example.restro.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.restro.feign.feighInterface;
import com.example.restro.models.OrderInfo;
import com.example.restro.orderservice.OrderService;
import com.example.restro.repositories.CustomerRepo;
import com.example.restro.repositories.ItemRepo;



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


        

        @PostMapping("/addtocart/{itemId}")
        public String addItem(@PathVariable int itemId,Authentication auth) {
            String email = auth.getName();
            
            System.out.println("adding item"+itemId+" to cart of user "+email);
            int cid = crepo.findByEmail(email).getId();
            String message = os.addToCart(cid, itemId);
            System.out.println(message);
            String categoryname=irepo.findById(itemId).get().getCategory();
            return "redirect:/categories/"+categoryname;
            
        }
    
        @PostMapping("/incrementitem/{itemId}")
        public String incrementItem( @PathVariable int itemId, Authentication auth) {
            String email = auth.getName();
            int cid = crepo.findByEmail(email).getId();
            String message = os.incrementItem(cid, itemId);
            System.out.println("increment item= "+ message);
            String categoryname=irepo.findById(itemId).get().getCategory();
            return "redirect:/categories/"+categoryname;
        }
    
        @PostMapping("/decrementitem/{itemId}")
        public String decrementItem( @PathVariable int itemId, Authentication auth ) {
            String email = auth.getName();
            int cid = crepo.findByEmail(email).getId();
            String message = os.decrementItem(cid, itemId);
            System.out.println("message: "+message);
            String categoryname=irepo.findById(itemId).get().getCategory();
            return "redirect:/categories/"+categoryname;        
        }
    
        // @PostMapping("/deleteitem")
        // public String deleteItem( @RequestParam int itemId, Authentication auth) {
        //     String email = auth.getName();
        //     int cid = crepo.findByEmail(email).getId();
        //     String message = os.deleteItem(cid, itemId);
        //     return "categories";
        // }
        
        
        @PostMapping("/incrementincart/incrementitem/{itemId}")
        public String incrementIteminCart( @PathVariable int itemId, Authentication auth) {
            String email = auth.getName();
            int cid = crepo.findByEmail(email).getId();
            String message = os.incrementItem(cid, itemId);
            System.out.println("increment item= "+ message);

            return "redirect:/orderupdate/cart";
        }
    
        @PostMapping("/decrementincart/decrementitem/{itemId}")
        public String decrementItemInCart( @PathVariable int itemId, Authentication auth ) {
            String email = auth.getName();
            int cid = crepo.findByEmail(email).getId();
            String message = os.decrementItem(cid, itemId);
            System.out.println("message: "+message);
            return "redirect:/orderupdate/cart";       
        }
    
        @PostMapping("/placeorder")
        public String placeOrder( Authentication auth,RedirectAttributes redirectAttributes) {
            String email = auth.getName();
            int cid = crepo.findByEmail(email).getId();
            String message = os.placeOrder(cid);
            System.out.println("order placed: "+ message);
            redirectAttributes.addFlashAttribute(
                        "success_message",
                        "Order confirmed. Thank you"
                );            
            return "redirect:/categories";
        }

        @GetMapping("/cart")
        public String getCart(Authentication auth, Model model) {
            String email = auth.getName();
            int cid = crepo.findByEmail(email).getId();
            List<OrderInfo> cartItems = os.getCart(cid);

            
            double total=os.getTotal(cid);
            model.addAttribute("OrderInfo", cartItems);
            model.addAttribute("total",total);
            return "cart";
            
        }

       
        
        
    
}

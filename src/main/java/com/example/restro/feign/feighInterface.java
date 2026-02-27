package com.example.restro.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8081")
public interface feighInterface {

    @GetMapping("/orders/addtocart/{cid}/{itemid}/{price}")
    public String addtoCart(@PathVariable int cid, @PathVariable int itemid, @PathVariable double price) ;

    @GetMapping("/orders/incrementitem/{cid}/{itemid}/{price}")
    public String incrementItem(@PathVariable int cid, @PathVariable int itemid, @PathVariable double  price) ;

    @GetMapping("/orders/decrementitem/{cid}/{itemid}/{price}")
    public String decrementItem(@PathVariable int cid, @PathVariable int itemid, @PathVariable double price) ;

    @GetMapping("/orders/deleteitem/{cid}/{itemid}")
    public String deleteItem(@PathVariable int cid, @PathVariable int itemid);
    
    @GetMapping("/orders/placeorder/{cid}")
    public String placeOrder(@PathVariable int cid) ;
    

}

package com.example.restro.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.restro.models.OrderInfo;

@FeignClient(name = "user-service", url = "http://localhost:8081")
public interface feighInterface {

    @GetMapping("/orders/addtocart/{cid}/{itemid}/{price}")
    public String addtoCart(@PathVariable int cid, @PathVariable int itemid, @PathVariable double price) ;

    @PostMapping("/orders/incrementitem/{cid}/{itemid}/{price}")
    public String incrementItem(@PathVariable int cid, @PathVariable int itemid, @PathVariable double  price) ;

    @PostMapping("/orders/decrementitem/{cid}/{itemid}/{price}")
    public String decrementItem(@PathVariable int cid, @PathVariable int itemid, @PathVariable double price) ;

    @GetMapping("/orders/deleteitem/{cid}/{itemid}")
    public String deleteItem(@PathVariable int cid, @PathVariable int itemid);
    
    @GetMapping("/orders/placeorder/{cid}")
    public String placeOrder(@PathVariable int cid) ;




    @GetMapping("/orders/gettotal/{cid}")
    public double getTotal(@PathVariable int cid);

    @GetMapping("/orders/returncart/{cid}")
    public List<OrderInfo> getCart(@PathVariable int cid);
    

}

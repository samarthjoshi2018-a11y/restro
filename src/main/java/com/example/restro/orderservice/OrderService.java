package com.example.restro.orderservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restro.feign.feighInterface;
import com.example.restro.repositories.ItemRepo;


@Service
public class OrderService {
    private feighInterface fin;

    @Autowired
    ItemRepo irepo;

    public OrderService(feighInterface fin) {   
        this.fin = fin;
    }

    public String addToCart(int cid, int itemid) {
        double price = irepo.findById(itemid).get().getAmount();
        return fin.addtoCart(cid, itemid, price);
    }

    public String incrementItem(int cid, int itemid) {
        double price = irepo.findById(itemid).get().getAmount();
        return fin.incrementItem(cid, itemid, price);
    }

    public String decrementItem(int cid, int itemid) {
        double price = irepo.findById(itemid).get().getAmount();
        return fin.decrementItem(cid, itemid, price);
    }

    public String deleteItem(int cid, int itemid) {
        return fin.deleteItem(cid, itemid);
    }

    public String placeOrder(int cid) {
        return fin.placeOrder(cid);
    }


    

}

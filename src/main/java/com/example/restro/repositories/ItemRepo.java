package com.example.restro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restro.models.Item;

@Repository
public interface ItemRepo extends JpaRepository<Item,Integer>{
    
    public Item findByItemName(String itemName);
    public List<Item> findByCategory(String category);

    

}
package com.example.restro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restro.models.Customer;


@Repository
public interface CustomerRepo extends JpaRepository<Customer,Integer>{
    
    public boolean existsByEmail(String email);
    public Customer findByEmail(String email);
}

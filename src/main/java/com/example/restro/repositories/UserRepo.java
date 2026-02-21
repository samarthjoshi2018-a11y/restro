package com.example.restro.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.restro.models.User;


@Repository
public interface UserRepo extends JpaRepository<User,Integer>{

    public  Optional<User> findByEmail(String email);
}

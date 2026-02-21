package com.example.restro.models;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Pattern;

@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User{

    @Column(length=10 , nullable=false)
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    private String phone;


    public Customer(String email, String password){
        super( email, password);
    }

    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    

}

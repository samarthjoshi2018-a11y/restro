package com.example.restro.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Pattern;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User{

    @Column(length=10 , nullable=false)
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    private String phone;
    
    private long salary;

    public Admin(){super();}


    public Admin(String name,String email,String pass){
        super(name,email,pass);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

}

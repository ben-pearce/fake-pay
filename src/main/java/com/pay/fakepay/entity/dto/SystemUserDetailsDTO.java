package com.pay.fakepay.entity.dto;

import com.pay.fakepay.Currency;
import java.io.Serializable;

public class SystemUserDetailsDTO implements Serializable {
    
    private String username;
    
    private String name;
    
    private String surname;
    
    private float balance;
    
    private Currency currency;

    public SystemUserDetailsDTO(
            String username,
            String name, 
            String surname,
            float balance,
            Currency currency) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.balance = balance;
        this.currency = currency;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSurname() {
        return surname;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public float getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return currency;
    }
    
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    
    public void setBalance(float balance) {
        this.balance = balance;
    }
}

package com.pay.fakepay.entity.dto;

import com.pay.fakepay.Currency;
import java.io.Serializable;

public class SystemUserDTO implements Serializable {
    
    private Long id;
    
    private String userpassword;
    
    private final SystemUserDetailsDTO details;

    public SystemUserDTO(
            Long id,
            String username,
            String userpassword,
            String name, 
            String surname,
            Currency currency,
            float balance) {
        this.id = id;
        this.userpassword = userpassword;
        this.details = new SystemUserDetailsDTO(
                username,
                name, 
                surname, 
                balance, 
                currency);
    }
    
    public SystemUserDTO(
            String username,
            String userpassword,
            String name, 
            String surname,
            Currency currency,
            float balance) {
        this(null, username, userpassword, name, surname, currency, balance);
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getUserpassword() {
        return userpassword;
    }
    
    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public SystemUserDetailsDTO getDetails() {
        return details;
    }
    
    public String getUsername() {
        return getDetails().getUsername();
    }
    
    public String getName() {
        return getDetails().getName();
    }
    
    public String getSurname() {
        return getDetails().getSurname();
    }
    
    public Currency getCurrency() {
        return getDetails().getCurrency();
    }
    
    public float getBalance() {
        return getDetails().getBalance();
    }
}

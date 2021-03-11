package com.pay.fakepay.entity;

import com.pay.fakepay.Currency;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;


@Entity
@NamedQueries({
    @NamedQuery(
            name="SystemUser.countByName", 
            query="SELECT COUNT(u) FROM SystemUser u "
                    + "WHERE u.username = :username"),
    @NamedQuery(
            name="SystemUser.getUser",
            query="SELECT u FROM SystemUser u "
                    + "WHERE u.username = :username")
})

public class SystemUser implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @NotNull
    @Column(unique=true)
    String username;
    
    @NotNull
    String userpassword;
    
    @NotNull
    String name;
    
    @NotNull
    String surname;
    
    @NotNull
    float balance;
    
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    Currency currency;
    
    @Version
    protected int version;

    public SystemUser(
            String username, 
            String userpassword,
            String name, 
            String surname,
            Currency currency,
            float balance) {
        this.username = username;
        this.userpassword = userpassword;
        this.name = name;
        this.surname = surname;
        this.currency = currency;
        this.balance = balance;
    }
    
    public SystemUser(
            String username, 
            String userpassword,
            String name, 
            String surname,
            Currency currency) {
        this.username = username;
        this.userpassword = userpassword;
        this.name = name;
        this.surname = surname;
        this.currency = currency;
        this.balance = 1000;
    }
    
    public SystemUser() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
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

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getVersion() {
        return version;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.username);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SystemUser other = (SystemUser) obj;
        return Objects.equals(this.id, other.id);
    }
    
}

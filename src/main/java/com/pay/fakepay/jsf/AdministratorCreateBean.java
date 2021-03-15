package com.pay.fakepay.jsf;

import com.pay.fakepay.ejb.AdministratorCreateService;
import javax.annotation.security.DeclareRoles;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@DeclareRoles({"admin"})
@Stateless public class AdministratorCreateBean {
    
    @EJB
    AdministratorCreateService administratorCreateService;
    
    String username;
    String password;
    
    public AdministratorCreateBean() { }
    
    public String register() {
        administratorCreateService.create(username, password);
        return "admin";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.pay.fakepay.jsf;

import com.pay.fakepay.Currency;
import com.pay.fakepay.ejb.UserService;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped public class RegistrationBean {

    @EJB
    UserService userService;
    
    String username;
    String userpassword;
    String name;
    String surname;
    Currency currency;

    public RegistrationBean() { }

    public String register() {
        FacesContext context = FacesContext.getCurrentInstance();
        if(userService.userExists(username)) {
            context.addMessage("register:username", 
                    new FacesMessage("Username is already in use."));
            return null;
        }
        
        userService.register(
                username, 
                userpassword,
                name, 
                surname, 
                currency);
        
        return "index";
    }
    
    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService usrSrv) {
        this.userService = usrSrv;
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}

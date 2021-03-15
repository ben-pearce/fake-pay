package com.pay.fakepay.jsf;

import com.pay.fakepay.ejb.TransactionService;
import com.pay.fakepay.ejb.UserService;
import javax.annotation.security.DeclareRoles;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@DeclareRoles({"user", "admin"})
@RequestScoped public class PaymentBean {
    
    @EJB
    TransactionService transactionService;
    
    @EJB
    UserService userService;
    
    String username;
    
    float amount;
    
    public PaymentBean() { }
    
    public String pay() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        if(!userService.userExists(username)) {
            context.addMessage("payment:username", 
                    new FacesMessage("User does not exist."));
            return null;
        }
        
        HttpServletRequest request = (HttpServletRequest) context
                .getExternalContext().getRequest();
        
        String user = request.getRemoteUser();
        
        transactionService.makePayment(user, username, amount);
        return "user";
    }
    
    public String request() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        if(!userService.userExists(username)) {
            context.addMessage("request:username", 
                    new FacesMessage("User does not exist."));
            return null;
        }
        
        HttpServletRequest request = (HttpServletRequest) context
                .getExternalContext().getRequest();
        
        String user = request.getRemoteUser();
        
        transactionService.requestPayment(username, user, amount);
        return "user";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}

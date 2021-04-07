package com.pay.fakepay.jsf;

import com.pay.fakepay.ejb.TransactionService;
import com.pay.fakepay.entity.dto.MoneyTransactionDTO;
import java.io.Serializable;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;


@Named
@DeclareRoles({"user", "admin"})
@SessionScoped public class TransactionBean implements Serializable {
    @EJB
    TransactionService transactionService;
    
    public TransactionBean() { } 
    
    public List<MoneyTransactionDTO> getTransactions() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.
                getExternalContext().getRequest();
        
        String user = request.getRemoteUser();
        System.out.println(transactionService.getTransactions(user));
        return transactionService.getTransactions(user);
    }
    
    public List<MoneyTransactionDTO> getOutgoingTransactions() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.
                getExternalContext().getRequest();
        
        String user = request.getRemoteUser();
        return transactionService.getOutgoingTransactions(user);
    }
    
    public List<MoneyTransactionDTO> getIncomingTransactions() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.
                getExternalContext().getRequest();
        
        String user = request.getRemoteUser();
        return transactionService.getIncomingTransactions(user);
    }
}

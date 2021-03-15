package com.pay.fakepay.jsf;

import com.pay.fakepay.ejb.TransactionService;
import com.pay.fakepay.entity.MoneyTransaction;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;


@Named
@SessionScoped public class TransactionBean implements Serializable {
    @EJB
    TransactionService transactionService;
    
    public TransactionBean() { } 
    
    public List<MoneyTransaction> getTransactions() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.
                getExternalContext().getRequest();
        
        String user = request.getRemoteUser();
        return transactionService.getTransactions(user);
    }
    
    public List<MoneyTransaction> getOutgoingTransactions() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.
                getExternalContext().getRequest();
        
        String user = request.getRemoteUser();
        return transactionService.getOutgoingTransactions(user);
    }
    
    public List<MoneyTransaction> getIncomingTransactions() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.
                getExternalContext().getRequest();
        
        String user = request.getRemoteUser();
        return transactionService.getIncomingTransactions(user);
    }
}

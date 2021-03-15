package com.pay.fakepay.jsf;

import com.pay.fakepay.ejb.TransactionService;
import java.io.Serializable;
import javax.annotation.security.DeclareRoles;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@DeclareRoles({"user", "admin"})
@SessionScoped public class PendingTransactionBean implements Serializable {
    
    @EJB
    TransactionService transactionService;
    
    Long transactionId;

    public PendingTransactionBean() { }
    
    public void acceptPendingTransaction() {
        transactionService.acceptPendingTransaction(transactionId);
    }
    
    public void declinePendingTransaction() {
        transactionService.declinePendingTransaction(transactionId);
    }
    
    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
}

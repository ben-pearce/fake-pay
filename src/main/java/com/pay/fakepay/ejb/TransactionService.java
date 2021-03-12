package com.pay.fakepay.ejb;

import com.pay.fakepay.entity.MoneyTransaction;
import com.pay.fakepay.entity.SystemUser;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless public class TransactionService {
    
    @PersistenceContext
    EntityManager em;
    
    public TransactionService() { }
    
    public void makePayment(String from, String to, float amount) {
        Query query = em.createNamedQuery("SystemUser.getUser");
        query.setParameter("username", from);
        
        SystemUser sender = (SystemUser) query.getSingleResult();
        
        query = em.createNamedQuery("SystemUser.getUser");
        query.setParameter("username", to);
        
        SystemUser recipient = (SystemUser) query.getSingleResult();
        
        MoneyTransaction transaction = new MoneyTransaction(
                sender, 
                recipient, 
                amount, 
                recipient.getCurrency(),
                false);
        
        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);
        em.persist(sender);
        em.persist(recipient);
        em.persist(transaction);
    }
}

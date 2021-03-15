package com.pay.fakepay.ejb;

import com.pay.fakepay.entity.MoneyTransaction;
import com.pay.fakepay.entity.SystemUser;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@TransactionAttribute(REQUIRED)
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
                recipient.getCurrency());
        
        completeTransaction(transaction);
    }
    
    public void requestPayment(String from, String to, float amount) {
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
                recipient.getCurrency());
        em.persist(transaction);
    }
    
    public void acceptPendingTransaction(Long transactionId) {
        MoneyTransaction transaction = em.find(
                MoneyTransaction.class, 
                transactionId);
        
        completeTransaction(transaction);
    }
    
    public void declinePendingTransaction(Long transactionId) {
        MoneyTransaction transaction = em.find(
            MoneyTransaction.class, 
            transactionId);
        
        em.remove(transaction);
    }
    
    public List<MoneyTransaction> getTransactions(String username) {
        Query query = em.createNamedQuery("MoneyTransaction.byUsername");
        query.setParameter("username", username);
        return query.getResultList();
    }
    
    public List<MoneyTransaction> getOutgoingTransactions(String username) {
        Query query = em.createNamedQuery("MoneyTransaction.outgoingByUsername");
        query.setParameter("username", username);
        return query.getResultList();
    }
    
    public List<MoneyTransaction> getIncomingTransactions(String username) {
        Query query = em.createNamedQuery("MoneyTransaction.incomingByUsername");
        query.setParameter("username", username);
        return query.getResultList();
    }
    
    private void completeTransaction(MoneyTransaction transaction) {
        SystemUser recipient = transaction.getRecipient();
        SystemUser sender = transaction.getSender();
        
        sender.setBalance(sender.getBalance() - transaction.getAmount());
        
        // TODO: Convert currency!
        recipient.setBalance(recipient.getBalance() + transaction.getAmount());
        transaction.setPending(false);
        
        em.persist(sender);
        em.persist(recipient);
        em.persist(transaction);
    }
}

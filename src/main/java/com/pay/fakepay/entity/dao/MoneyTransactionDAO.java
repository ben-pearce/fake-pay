package com.pay.fakepay.entity.dao;

import com.pay.fakepay.entity.MoneyTransaction;
import com.pay.fakepay.entity.SystemUser;
import com.pay.fakepay.entity.dto.MoneyTransactionDTO;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless public class MoneyTransactionDAO {

    @PersistenceContext
    EntityManager em;
    
    public MoneyTransactionDTO get(Long id) {
        MoneyTransaction mt = em.find(MoneyTransaction.class, id);
        return mt.getMoneyTransactionData();
    }
    
    public List<MoneyTransactionDTO> get(String username) {
        Query query = em.createNamedQuery("MoneyTransaction.getByUsername");
        query.setParameter("username", username);
        System.out.println(query.getResultList());
        return (List<MoneyTransactionDTO>) query.getResultList()
                .stream()
                .map(mt -> ((MoneyTransaction) mt).getMoneyTransactionData())
                .collect(Collectors.toList());
    }
    
    public void save(MoneyTransactionDTO t) {
        Query query = em.createNamedQuery("SystemUser.getByUsername");
        query.setParameter("username", t.getSender().getUsername());
        SystemUser sender = (SystemUser) query.getSingleResult();
        query.setParameter("username", t.getRecipient().getUsername());
        SystemUser recipient = (SystemUser) query.getSingleResult();
        em.persist(new MoneyTransaction(
                sender, 
                recipient,
                t.getSenderAmount(), 
                t.getRecipientAmount(), 
                t.getCreatedOn(),
                t.isPending()));
    }
    
    public void update(MoneyTransactionDTO t) {
        MoneyTransaction transaction = em.find(MoneyTransaction.class, t.getId());
        Query query = em.createNamedQuery("SystemUser.getByUsername");
        query.setParameter("username", t.getSender().getUsername());
        SystemUser sender = (SystemUser) query.getSingleResult();
        query.setParameter("username", t.getRecipient().getUsername());
        SystemUser recipient = (SystemUser) query.getSingleResult();
        transaction.setSender(sender);
        transaction.setRecipient(recipient);
        transaction.setSenderAmount(t.getSenderAmount());
        transaction.setRecipientAmount(t.getRecipientAmount());
        transaction.setPending(t.isPending());
        em.persist(transaction);
    }
    
    public List<MoneyTransactionDTO> all() {
        Query query = em.createNamedQuery("MoneyTransaction.getAll");
        return (List<MoneyTransactionDTO>) query.getResultList()
                .stream()
                .map(mt -> ((MoneyTransaction) mt).getMoneyTransactionData())
                .collect(Collectors.toList());
    }
    
    public void delete(MoneyTransactionDTO t) {
        MoneyTransaction mt = em.find(MoneyTransaction.class, t.getId());
        em.remove(mt);
    }
    
    public List<MoneyTransactionDTO> outgoing(String username) {
        Query query = em.createNamedQuery("MoneyTransaction.getOutgoing");
        query.setParameter("username", username);
        return (List<MoneyTransactionDTO>) query.getResultList()
                .stream()
                .map(mt -> ((MoneyTransaction) mt).getMoneyTransactionData())
                .collect(Collectors.toList());
    }
    
    public List<MoneyTransactionDTO> incoming(String username) {
        Query query = em.createNamedQuery("MoneyTransaction.getIncoming");
        query.setParameter("username", username);
        return (List<MoneyTransactionDTO>) query.getResultList()
                .stream()
                .map(mt -> ((MoneyTransaction) mt).getMoneyTransactionData())
                .collect(Collectors.toList());
    }
}

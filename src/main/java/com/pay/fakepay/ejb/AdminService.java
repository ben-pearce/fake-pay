package com.pay.fakepay.ejb;

import com.pay.fakepay.entity.MoneyTransaction;
import com.pay.fakepay.entity.SystemUser;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless public class AdminService {
    
    @PersistenceContext
    EntityManager em;
    
    public AdminService() { }
    
    public List<SystemUser> getUsers() {
        Query query = em.createNamedQuery("SystemUser.findAll");
        return query.getResultList();
    }
    
    public List<MoneyTransaction> getTransactions() {
        Query query = em.createNamedQuery("MoneyTransaction.findAll");
        return query.getResultList();
    }
}

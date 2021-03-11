package com.pay.fakepay.ejb;

import com.pay.fakepay.Currency;
import com.pay.fakepay.entity.MoneyTransaction;
import com.pay.fakepay.entity.SystemUser;
import com.pay.fakepay.entity.SystemUserGroup;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless public class UserService {

    @PersistenceContext
    EntityManager em;

    public UserService() { }

    public void register(
            String username,
            String userpassword, 
            String name, 
            String surname,
            String currency) {
        try {
            SystemUser sysUser;
            SystemUserGroup sysUserGroup;
            
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = userpassword;
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
            String password = sb.toString();
            Currency userCurrency = Currency.valueOf(currency);
            sysUser = new SystemUser(
                    username, 
                    password, 
                    name, 
                    surname,
                    userCurrency
            );
            sysUserGroup = new SystemUserGroup(username, "users");

            em.persist(sysUser);
            em.persist(sysUserGroup);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<MoneyTransaction> getTransactions(String username) {
        Query query = em.createNamedQuery("MoneyTransaction.byUsername");
        query.setParameter("username", username);
        return query.getResultList();
    }
    
    public SystemUser getUser(String username) {
        Query query = em.createNamedQuery("SystemUser.getUser");
        query.setParameter("username", username);
        return (SystemUser) query.getSingleResult();
    }
}

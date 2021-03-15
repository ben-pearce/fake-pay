package com.pay.fakepay.ejb;

import com.pay.fakepay.Currency;
import com.pay.fakepay.CurrencyExchange;
import com.pay.fakepay.entity.SystemUser;
import com.pay.fakepay.entity.SystemUserGroup;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
            Currency currency) {
        try {
            SystemUser sysUser;
            SystemUserGroup sysUserGroup;
            
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(userpassword.getBytes("UTF-8"));
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer
                        .toString((digest[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            String passwordHash = sb.toString();
            
            Float balance = 1000f;
            if(currency != Currency.GBP) {
                balance = CurrencyExchange.convert(
                    Currency.GBP, 
                    currency, 
                    balance);
            }

            sysUser = new SystemUser(
                    username, 
                    passwordHash, 
                    name, 
                    surname,
                    currency,
                    balance
            );
            sysUserGroup = new SystemUserGroup(username, "users");

            em.persist(sysUser);
            em.persist(sysUserGroup);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserService.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    public Boolean userExists(String username) {
        Query query = em.createNamedQuery("SystemUser.countByName");
        query.setParameter("username", username);
        Long count = (Long) query.getSingleResult();

        return count.compareTo(0L) > 0;
    }
    
    public SystemUser getUser(String username) {
        Query query = em.createNamedQuery("SystemUser.getUser");
        query.setParameter("username", username);
        return (SystemUser) query.getSingleResult();
    }
}

package com.pay.fakepay.ejb;

import com.pay.fakepay.Currency;
import com.pay.fakepay.CurrencyExchange;
import com.pay.fakepay.entity.SystemUser;
import com.pay.fakepay.entity.dao.SystemUserDAO;
import com.pay.fakepay.entity.dao.SystemUserGroupDAO;
import com.pay.fakepay.entity.dto.SystemUserDTO;
import com.pay.fakepay.entity.dto.SystemUserDetailsDTO;
import com.pay.fakepay.entity.dto.SystemUserGroupDTO;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless public class UserService {
    
    @PersistenceContext
    EntityManager em;
    
    @EJB
    SystemUserDAO su ;
    
    @EJB
    SystemUserGroupDAO sug;
    
    public UserService() { }

    public void register(
            String username,
            String userpassword, 
            String name, 
            String surname,
            Currency currency) {
        try {
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

            SystemUserDTO user = new SystemUserDTO(
                    username,
                    passwordHash, 
                    name, 
                    surname,
                    currency,
                    balance);
            SystemUserGroupDTO userGroup = new SystemUserGroupDTO(
                    username, "users");
            su.save(user);
            sug.save(userGroup);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserService.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    public Boolean userExists(String username) {
        return su.exists(username);
    }
    
    public SystemUserDetailsDTO getUser(String username) {
        return su.details(username);
    }
}

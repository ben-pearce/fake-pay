package com.pay.fakepay.entity.dao;

import com.pay.fakepay.entity.SystemUser;
import com.pay.fakepay.entity.dto.SystemUserDTO;
import com.pay.fakepay.entity.dto.SystemUserDetailsDTO;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless public class SystemUserDAO {

    @PersistenceContext
    EntityManager em;
    
    public SystemUserDTO get(Long id) {
        SystemUser u = em.find(SystemUser.class, id);
        return u.getSystemUserData();
    }
    
    public SystemUserDTO get(String username) {
        Query query = em.createNamedQuery("SystemUser.getByUsername");
        query.setParameter("username", username);
        return ((SystemUser) query.getSingleResult()).getSystemUserData();
    }
    
    public void save(SystemUserDTO u) {
        em.persist(new SystemUser(
                u.getUsername(), 
                u.getUserpassword(), 
                u.getName(), 
                u.getSurname(), 
                u.getCurrency(), 
                u.getBalance()));
    }
    
    public void delete(SystemUserDTO u) {
        SystemUser su = em.find(SystemUser.class, u.getId());
        em.remove(su);
    }
    
    public void update(SystemUserDTO u) {
        SystemUser user = em.find(SystemUser.class, u.getId());
        user.setUsername(u.getUsername());
        user.setUserpassword(u.getUserpassword());
        user.setName(u.getName());
        user.setSurname(u.getSurname());
        user.setCurrency(u.getCurrency());
        user.setBalance(u.getBalance());
        em.persist(user);
    }
    
    public void update(SystemUserDetailsDTO u) {
        Query query = em.createNamedQuery("SystemUser.getByUsername");
        query.setParameter("username", u.getUsername());
        SystemUser user = (SystemUser) query.getSingleResult();
        user.setUsername(u.getUsername());
        user.setName(u.getName());
        user.setSurname(u.getSurname());
        user.setCurrency(u.getCurrency());
        user.setBalance(u.getBalance());
        em.persist(user);
    }
    
    public List<SystemUserDTO> all() {
        Query query = em.createNamedQuery("SystemUser.getAll");
        return (List<SystemUserDTO>) query.getResultList()
                .stream()
                .map(su -> ((SystemUser) su).getSystemUserData())
                .collect(Collectors.toList());
    }
    
    public boolean exists(String username) {
        Query query = em.createNamedQuery("SystemUser.countByName");
        query.setParameter("username", username);
        Long count = (Long) query.getSingleResult();

        return count.compareTo(0L) > 0;
    }
    
    public SystemUserDetailsDTO details(String username) {
        Query query = em.createNamedQuery("SystemUser.getByUsername");
        query.setParameter("username", username);
        return ((SystemUser) query.getSingleResult()).getSystemUserDetailsData();
    }
}

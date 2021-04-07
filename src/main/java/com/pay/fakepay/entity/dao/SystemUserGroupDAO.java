package com.pay.fakepay.entity.dao;

import com.pay.fakepay.entity.SystemUserGroup;
import com.pay.fakepay.entity.dto.SystemUserGroupDTO;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless public class SystemUserGroupDAO {
    
    @PersistenceContext
    EntityManager em;
    
    public void save(SystemUserGroupDTO ug) {
        em.persist(new SystemUserGroup(
                ug.getUsername(), 
                ug.getGroupname()));
    }
}

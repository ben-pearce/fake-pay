package com.pay.fakepay.ejb;

import com.pay.fakepay.entity.SystemUserGroup;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Startup
@Singleton public class AdministratorCreateService {
    
    @PersistenceContext
    EntityManager em;
    
    @EJB
    UserService userService;
    
    public static String adminUser = "admin1";
    
    public static String adminPassword = "admin1";
    
    @PostConstruct
    public void init() {
        Query query = em.createNamedQuery("SystemUser.countByName");
        query.setParameter("username", adminUser);
        Long count = (Long) query.getSingleResult();

        if(count.equals(0L)) {
            userService.register(
                    adminUser, 
                    adminPassword, 
                    adminUser, 
                    adminUser);
            
            SystemUserGroup sysUserGroup = new SystemUserGroup(
                    adminUser, "admins");
            em.persist(sysUserGroup);
        }
    }
}

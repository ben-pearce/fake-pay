package com.pay.fakepay.ejb;

import com.pay.fakepay.Currency;
import com.pay.fakepay.entity.dao.SystemUserGroupDAO;
import com.pay.fakepay.entity.dto.SystemUserGroupDTO;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Startup
@Singleton public class AdministratorCreateService {

    @EJB
    UserService userService;

    @EJB
    SystemUserGroupDAO sug;
    
    public static String adminUser = "admin1";
    
    public static String adminPassword = "admin1";
    
    @PostConstruct
    public void init() {
        Query query = em.createNamedQuery("SystemUser.countByName");
        query.setParameter("username", adminUser);
        Long count = (Long) query.getSingleResult();

        if(count.equals(0L)) {
            create(adminUser, adminPassword);
        }
    }
    
    public void create(String username, String password) {
        userService.register(
                username, 
                password, 
                "", "", Currency.GBP);

        SystemUserGroupDTO userGroup = 
                new SystemUserGroupDTO(username, "admins");
        sug.save(userGroup);
    }
}

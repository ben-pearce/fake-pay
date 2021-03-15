package com.pay.fakepay.jsf;

import com.pay.fakepay.ejb.AdminService;
import com.pay.fakepay.entity.MoneyTransaction;
import com.pay.fakepay.entity.SystemUser;
import java.io.Serializable;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@DeclareRoles({"admin"})
@SessionScoped public class AdminBean implements Serializable {
    
    @EJB
    AdminService adminService;
    
    public AdminBean() { } 
    
    public List<SystemUser> getUsers() {
        return adminService.getUsers();
    }
    
    public List<MoneyTransaction> getTransactions() {
        return adminService.getTransactions();
    }
}

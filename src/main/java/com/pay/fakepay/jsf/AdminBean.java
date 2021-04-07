package com.pay.fakepay.jsf;

import com.pay.fakepay.ejb.AdminService;
import com.pay.fakepay.entity.dto.MoneyTransactionDTO;
import com.pay.fakepay.entity.dto.SystemUserDTO;
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
    
    public List<SystemUserDTO> getUsers() {
        return adminService.getUsers();
    }
    
    public List<MoneyTransactionDTO> getTransactions() {
        return adminService.getTransactions();
    }
}

package com.pay.fakepay.ejb;

import com.pay.fakepay.entity.dao.MoneyTransactionDAO;
import com.pay.fakepay.entity.dao.SystemUserDAO;
import com.pay.fakepay.entity.dto.MoneyTransactionDTO;
import com.pay.fakepay.entity.dto.SystemUserDTO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless public class AdminService {
    
    @EJB
    SystemUserDAO su;
    
    @EJB
    MoneyTransactionDAO mt;
    
    public AdminService() { }
    
    public List<SystemUserDTO> getUsers() {
        return su.all();
    }
    
    public List<MoneyTransactionDTO> getTransactions() {
        return mt.all();
    }
}

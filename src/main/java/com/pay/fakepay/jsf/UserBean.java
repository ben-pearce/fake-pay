package com.pay.fakepay.jsf;

import com.pay.fakepay.ejb.UserService;
import com.pay.fakepay.entity.MoneyTransaction;
import com.pay.fakepay.entity.SystemUser;
import java.io.Serializable;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@DeclareRoles({"user", "admin"})
@SessionScoped public class UserBean implements Serializable {
    
    @EJB
    UserService usrSrv;
    
    public UserBean() { }
    
    public SystemUser getUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        
        String user = request.getRemoteUser();
        return usrSrv.getUser(user);
    }
    
    public List<MoneyTransaction> getTransactions() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        
        String user = request.getRemoteUser();
        return usrSrv.getTransactions(user);
    }
}

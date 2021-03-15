package com.pay.fakepay.jsf;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Named
@DeclareRoles({"user", "admin"})
@RequestScoped public class LoginBean implements Serializable {

    public void logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        try {
            request.logout();
            response.sendRedirect("../index.xhtml");
        } catch (ServletException | IOException e) {
            context.addMessage(null, new FacesMessage("Logout failed."));
        }
    }
}

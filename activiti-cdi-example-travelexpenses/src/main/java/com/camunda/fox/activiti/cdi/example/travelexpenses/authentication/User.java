package com.camunda.fox.activiti.cdi.example.travelexpenses.authentication;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@SessionScoped
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  private boolean loggedIn;

  public boolean isLoggedIn() {
    return loggedIn;
  }

  public void setLoggedIn(boolean loggedIn) {
    this.loggedIn = loggedIn;
  }

  public void logout() {
    // TODO: there are certainly better ways to logout...
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext ec = context.getExternalContext();
    HttpServletRequest request = (HttpServletRequest) ec.getRequest();
    request.getSession(false).invalidate();
    loggedIn = false;
  }

}

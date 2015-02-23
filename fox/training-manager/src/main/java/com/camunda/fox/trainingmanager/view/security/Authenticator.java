package com.camunda.fox.trainingmanager.view.security;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.camunda.fox.trainingmanager.model.User;

@Named
@SessionScoped
public class Authenticator implements Serializable {
  
  private static final long serialVersionUID = 1L;

  @PersistenceContext 
  private EntityManager entityManager;
  
  @Inject 
  private Conversation conversation;
  
  private User loggedInUser;
  
  private String username;
  private String password;
    
  private boolean isLoggedIn;  
  
  public String authenticate() {
    isLoggedIn = false;
    try { 
      loggedInUser = entityManager.createQuery("select u from User u where u.email=:email and u.password=:password", User.class)
        .setParameter("email", username)
        .setParameter("password", password)
        .getSingleResult();
      isLoggedIn = true;
      return "success";
    }catch (NoResultException e) {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Could not login: no such user"));
      isLoggedIn = false;
      return "failure";
    } finally {
      username = null;
      password = null;
    }
  }
  
  public void logout() throws IOException {
    if(!conversation.isTransient()) {
      conversation.end();
    }
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    externalContext.invalidateSession();    
    externalContext.redirect(externalContext.getRequestContextPath() + "/");   
  }
        
  public boolean isLoggedIn() {
    return isLoggedIn;
  }
  
  @Produces
  @Named
  @LoggedIn
  public User currentUser() {
    return loggedInUser;
  }
  
  public String getUsername() {
    return username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getPassword() {
    return password;
  }
    
  public void setPassword(String password) {
    this.password = password;
  }

}

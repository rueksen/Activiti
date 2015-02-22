package com.camunda.fox.activiti.cdi.example.travelexpenses.authentication;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.camunda.fox.activiti.cdi.actor.Actor;
import com.camunda.fox.activiti.cdi.example.travelexpenses.datasource.EmployeeDatabase;
import com.camunda.fox.activiti.cdi.example.travelexpenses.domain.Account;
import com.camunda.fox.activiti.cdi.example.travelexpenses.domain.Employee;

@Named
@Stateless
public class AuthenticationBean {

  @Inject
  Logger logger;

  @Inject
  Actor actor;

  @Inject
  User user;

  @Inject
  @EmployeeDatabase
  EntityManager entityManager;

  private String username;

  private String password;

  public String authenticate() {
    
    initIfEmpty();
    
    try {
      if (username == null || password == null) {
        throw new Exception("password or username null.");
      }
      TypedQuery<Account> accountQuery = entityManager.createQuery("SELECT a FROM Account a WHERE a.username='" + username + "'", Account.class);
      Account account = accountQuery.getSingleResult();
      if (!password.equals(account.getPassword())) {
        throw new Exception("password or username wrong.");
      }
      // use the username as actor-id in the business process
      actor.setActorId(username);
      user.setLoggedIn(true);
      return "success";
    } catch (Exception e) {
      logger.info("Could not login as '" + username + "': " + e.getMessage());
      return "failure";
    }
  }
  
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  private void initIfEmpty() {

    TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(e) FROM Employee e", Long.class);
    long numberOfEmployees = query.getSingleResult();

    if (numberOfEmployees > 0) {
      return;
    }

    Employee kermit = new Employee("Kermit", "the Frog");
    Account kermitsAccount = new Account();
    kermitsAccount.setUsername("kermit");
    kermitsAccount.setPassword("kermit");
    kermit.setAccount(kermitsAccount);

    Employee gonzo = new Employee("Gonzo", "");
    Account gonzosAccount = new Account();
    gonzosAccount.setUsername("gonzo");
    gonzosAccount.setPassword("gonzo");
    gonzo.setAccount(gonzosAccount);

    kermit.setManager(gonzo);

    entityManager.persist(kermit);
    
    entityManager.flush();

  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

}

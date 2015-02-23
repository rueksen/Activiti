package com.camunda.fox.activiti.cdi.example.travelexpenses.view;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import com.camunda.fox.activiti.cdi.example.travelexpenses.domain.Employee;

/**
 * Allows to manage an employee instance during a unit of work (create / edit
 * it)
 * 
 * @author meyerd
 */
@Stateful
@Named
@ConversationScoped
public class EmployeeEdit {

  private static Logger log = Logger.getLogger(EmployeeEdit.class.getName());

  @PersistenceContext(type = PersistenceContextType.EXTENDED)
  private EntityManager entityManager;

  @Inject
  private Conversation conversation;

  private Employee employee;

  @Produces
  @Named("employee")
  public Employee getEmployee() {
    if (employee == null) {
      employee = new Employee();
      conversation.begin(); // this starts a unit of work
    }
    return employee;
  }


  public void setId(String id) {
    employee = entityManager.find(Employee.class, Long.valueOf(id));
    conversation.begin(); // this begins a unit of work
  }
  
  public String save() {
    try {
      if (!entityManager.contains(employee)) {
        entityManager.persist(employee);
      }
      conversation.end(); // this ends the unit of work
      return "success";
    } catch (Exception e) {
      log.log(Level.WARNING, "Error while saving employee", e);
      FacesContext.getCurrentInstance()
        .addMessage(null, new FacesMessage(e.getMessage()));
      return "failure";
    }
  }
  
  public String getId() {
    return null;
  }

  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  // not supported because we do not want to provoke a premature flush here
  public boolean isNew() {
    return employee == null || entityManager.contains(employee);
  }

}

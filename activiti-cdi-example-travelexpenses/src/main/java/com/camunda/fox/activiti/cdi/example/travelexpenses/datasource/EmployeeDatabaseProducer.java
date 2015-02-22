package com.camunda.fox.activiti.cdi.example.travelexpenses.datasource;

import javax.ejb.Stateless;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * {@link Produces} a {@link ConversationScoped} {@link EntityManager} for
 * accessing the {@link EmployeeDatabase}.
 * 
 * @author Daniel Meyer
 */
@Stateless
public class EmployeeDatabaseProducer implements EmployeeDatabaseProducerLocal {

  private EntityManager entityManager;

  @Produces
  @EmployeeDatabase
  @ConversationScoped
  public EntityManager getEntityManager() {
    return entityManager;
  }

  @PersistenceContext
  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

}

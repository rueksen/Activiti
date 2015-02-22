package com.camunda.fox.activiti.cdi.example.travelexpenses.datasource;

import javax.ejb.Local;
import javax.persistence.EntityManager;

@Local
public interface EmployeeDatabaseProducerLocal {

  public EntityManager getEntityManager();

  public void setEntityManager(EntityManager entityManager);
}

package com.camunda.fox.trainingmanager.service;

import javax.ejb.Stateful;
import javax.enterprise.inject.Typed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Stateful
@Typed(RegistrationServiceConversational.class)
public class RegistrationServiceConversational extends RegistrationService {

  @PersistenceContext(type=PersistenceContextType.EXTENDED)
  private EntityManager entityManager;
  
  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

}

package com.camunda.fox.trainingmanager.service;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named("registrationService")
@Stateless
public class RegistrationServiceStateless extends RegistrationService {
  
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

}

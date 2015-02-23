package com.camunda.fox.trainingmanager.service;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 
 * @author meyerd
 */
@Stateless
@Named("trainingDateService")
public class TrainingDateServiceStateless extends TrainingDateService {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

}

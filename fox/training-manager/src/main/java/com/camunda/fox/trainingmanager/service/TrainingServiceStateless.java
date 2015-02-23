package com.camunda.fox.trainingmanager.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * This is the stateless version of the service. 
 *  
 * @author meyerd
 */
@Stateless
public class TrainingServiceStateless extends TrainingService {

  @PersistenceContext
  private EntityManager entityManager;
  
  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

}

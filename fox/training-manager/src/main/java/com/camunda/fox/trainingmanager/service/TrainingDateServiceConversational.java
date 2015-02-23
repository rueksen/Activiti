package com.camunda.fox.trainingmanager.service;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Typed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * 
 * @author meyerd
 */
@Stateful
@ConversationScoped
@Typed(TrainingDateServiceConversational.class)
public class TrainingDateServiceConversational extends TrainingDateService {

  @PersistenceContext(type = PersistenceContextType.EXTENDED)
  private EntityManager entityManager;

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

}

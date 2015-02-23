package com.camunda.fox.trainingmanager.service;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Typed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import com.camunda.fox.trainingmanager.model.Training;

/**
 * This is the conversational (stateful) view of the {@link TrainingService}
 * 
 * @author meyerd
 */
@ConversationScoped
@Stateful
// @Typed restricts the BeanTypes for a bean: this bean does not satisfy the injection 
// point @Inject TrainingService trainingService
@Typed(TrainingServiceConversational.class) 
public class TrainingServiceConversational extends TrainingService {

  // this is an EXTENDED persistence context we keep around 
  // for as long as we work on the training.
  // -> it can track and flush our changes to the training when we are done
  // -> we can lazily fetch associations

  // NOTE: the EXTENDED persistence context is flushed at the end of each transaction
  // it participates in. (because in JPA there is (still!) no 
  // FlushMode.MANUAL (as provided by hibernate, for example))
  
  // -> We have to make sure that the persistence context is only 
  //    flushed when the unit of work ends (and not earlier), which means, 
  //    that we have to be careful when calling methods on this bean: 
  //    if we use the entity manager inside the method 
  //                        AND 
  //    if the method is called inside a transaction, the entity manager 
  //    is flushed.
  // 
  //    EXAMPLE: this would flush the entity manager, when called inside a 
  //    transaction:
  //   
  //    public void callMethod() {  
  //      entityManager.contains(training);
  //    }
  
  @PersistenceContext(type = PersistenceContextType.EXTENDED)
  private EntityManager entityManager;

  protected EntityManager getEntityManager() {
    return entityManager;
  }
  
  @Override
  public void updateTraining(Training training) {
    super.updateTraining(training);
    entityManager.flush();
  }

}

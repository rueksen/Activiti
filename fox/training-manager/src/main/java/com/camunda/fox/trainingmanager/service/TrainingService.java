package com.camunda.fox.trainingmanager.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import com.camunda.fox.trainingmanager.model.Training;

/**
 * This is the abstract base class for building a training service. This allows
 * us to manage business logic needed to manage training instances in a central
 * place.
 * <ul>
 * <li>
 * In simple applications, this business logic is very simple, which rises the
 * question whether the existence of this service is justified. This is
 * typically the case if the service just delegates to the entity manager.</li>
 * <li>
 * In more complex applications, we need to perform complex logic which goes
 * beyond simply persisting an entity (we might need to start / signal business
 * processes, call web services, validate data etc.)</li>
 * </ul>
 * 
 * <p />
 * Transaction Management: This class assumes that transactions are managed for
 * us; either it is extended as an EJB, or it is called inside an open client
 * transaction
 * 
 * @author meyerd
 */
public abstract class TrainingService {

  /**
   * deletes a training by id
   */
  public void delete(Long id) {
    Training training = getEntityManager().find(Training.class, id);
    getEntityManager().remove(training);
  }

  /**
   * creates a TRANSIENT instance
   */
  public Training createNewTraining() {
    // potentially doing additional initialization etc.
    return new Training();
  }
  
  /**
   * persists a TRANSIENT instance
   */
  public void persistNewTraining(Training training) {
    // validate etc.
    // potentially starting a business process, calling a web service.
    getEntityManager().persist(training);
  }
 
  /**
   * retrieves PERSISTENT training by the provided id
   */
  public Training getTrainingById(Long id) {
    return getEntityManager().find(Training.class, id);
  }

  public List<Training> findAllTrainings() {
    CriteriaQuery<Training> queryDefinition = getEntityManager().getCriteriaBuilder().createQuery(Training.class);
    queryDefinition.from(Training.class);
    return getEntityManager().createQuery(queryDefinition).getResultList();
  }
  
  /**
   * update a PERSISTENT training (loaded by this bean's entity manager)
   * 
   * The training is retrieved using the {@link #getTrainingById(Long)} method:
   * - if we have a {@link PersistenceContextType#TRANSACTION} scoped entity manager, 
   *   both methods need to be called in the same transaction
   * - if we have an {@link PersistenceContextType#EXTENDED} persistence context, 
   *   this restriction does not apply (as long as the training was loaded by that 
   *   same entity manager)
   */
  public void updateTraining(Training training) {
    // validate etc.    
  }
  
  /**
   * provides an entity manager to this class.
   */
  protected abstract EntityManager getEntityManager();
  
}

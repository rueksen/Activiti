package com.camunda.fox.trainingmanager.service;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.activiti.cdi.BusinessProcess;
import org.activiti.cdi.annotation.StartProcess;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;

import com.camunda.fox.trainingmanager.model.Registration;
import com.camunda.fox.trainingmanager.model.Training;
import com.camunda.fox.trainingmanager.model.TrainingDate;

/**
 * 
 * @author meyerd
 */
public abstract class TrainingDateService {
  
  /**
   * the currently associated business process instance
   */
  @Inject private BusinessProcess businessProcess;
  
  @Inject private RuntimeService runtimeService;
    
  /**
   * deletes a trainingDate by id
   */
  public void delete(Long id) {
    TrainingDate trainingDate = getTrainingDateById(id);
    getEntityManager().remove(trainingDate);
  }

  /**
   * creates a TRANSIENT instance
   */
  public TrainingDate createNewTrainingDate() {
    return new TrainingDate();
  }
    
  // whenever a new training date is created, a new business process 
  // defining the lifecycle of a training date is started
  @StartProcess("createTrainingDate")
  public void createNewTrainingDate(TrainingDate trainingDate, Long trainingId) {
    // the businessProcess bean allows us to set variables 
    // before the process is started
    businessProcess.setVariable("newTrainingDate", trainingDate);
    businessProcess.setVariable("trainingId", trainingId);
  }
  
  public void persistNewTrainingDate() {
    TrainingDate trainingDate = businessProcess.getVariable("newTrainingDate");
    validateTrainingDate(trainingDate);
    
    Long trainingId = businessProcess.getVariable("trainingId");        
    Training training = getEntityManager().find(Training.class, trainingId);
    
    // guarantee referential integrity
    trainingDate.setTraining(training);
    training.getTrainingDates().add(trainingDate);
    
    getEntityManager().persist(trainingDate);
    // flush the entity manager in order to generate an id 
    getEntityManager().flush();
    
    businessProcess.setVariable("trainingDateId", trainingDate.getId());
    businessProcess.setVariable("trainingId", null);
    businessProcess.setVariable("newTrainingDate", null);
  }
 
  protected void validateTrainingDate(TrainingDate trainingDate) {   
    if(! trainingDate.getEndDate().after(trainingDate.getStartDate())) {
      throw new RuntimeException("endDate needs to be AFTER startDate.");
    }
  }
  
  public void setTrainingDateTakesPlace(boolean takesPlace) {
    Long trainingDateId = businessProcess.getVariable("trainingDateId");
    TrainingDate trainingDate = getTrainingDateById(trainingDateId);
    trainingDate.setCancelled(!takesPlace);
  }
  
  public void updateRegistrations() {
    Long trainingDateId = businessProcess.getVariable("trainingDateId");
    TrainingDate trainingDate = getTrainingDateById(trainingDateId);
    for (Registration registration : trainingDate.getRegistrations()) {
      // TODO: one transaction per signal?
      Execution execution = runtimeService.createExecutionQuery()
        .variableValueEquals("registrationId", registration.getId())
        .singleResult();
      runtimeService.setVariable(execution.getId(), "cancelled", trainingDate.isCancelled());
      runtimeService.signal(execution.getId());      
    }
  }
  
  /**
   * retrieves PERSISTENT trainingDate by the provided id
   */
  public TrainingDate getTrainingDateById(Long id) {
    return getEntityManager().find(TrainingDate.class, id);
  }

  public List<TrainingDate> findAllTrainingDates() {
    CriteriaQuery<TrainingDate> queryDefinition = getEntityManager().getCriteriaBuilder().createQuery(TrainingDate.class);
    queryDefinition.from(TrainingDate.class);
    TypedQuery<TrainingDate> createQuery = getEntityManager().createQuery(queryDefinition);
    return createQuery.getResultList();
  }
  
  /**
   * update a PERSISTENT trainingDate (loaded by this bean's entity manager)
   * 
   */
  public void updateTrainingDate(TrainingDate trainingDate) {
    validateTrainingDate(trainingDate);
  }
  
  /**
   * provides an entity manager to this class.
   */
  protected abstract EntityManager getEntityManager();
  
}

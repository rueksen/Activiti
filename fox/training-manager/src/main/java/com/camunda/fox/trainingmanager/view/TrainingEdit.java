package com.camunda.fox.trainingmanager.view;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.camunda.fox.trainingmanager.model.Training;
import com.camunda.fox.trainingmanager.service.TrainingServiceConversational;

/**
 * Backing bean for the new training / edit training usecases.
 * 
 * @author meyerd
 */
@Named
@ConversationScoped
public class TrainingEdit implements Serializable {
  
  private static final long serialVersionUID = 1L;

  @Inject  
  private TrainingServiceConversational trainingService;
  
  @Inject 
  private Conversation conversation;
  
  private Training training;
  private Long trainingId;
  private boolean isNew = true;
  
  @Produces
  @Named
  @ConversationScoped
  public Training getTraining() {
    if(training == null) {
      loadTraining();      
    }
    return training;
  }

  private void loadTraining() {
    if(trainingId == null) {
      training = trainingService.createNewTraining();
    } else {
      training = trainingService.getTrainingById(trainingId);     
    }   
  }

  public void save() {    
    trainingService.persistNewTraining(training);
  }
  
  public void cancel() {    
    // this ends the current unit of work
    if(!conversation.isTransient()) {
      conversation.end();    
    }
  }
  
  public void update() {
    trainingService.updateTraining(training);
    // this ends the current unit of work
    if(!conversation.isTransient()) {
      conversation.end();    
    }
  }
    
  public void setTrainingId(Long trainingId) {
    this.trainingId = trainingId;
    isNew = false;
    // this begins a unit of work
    if(conversation.isTransient()) {
      conversation.begin();
    }
  }
  
  public Long getTrainingId() {
    return trainingId;
  }
  
  public boolean isNew() {
    return isNew;
  }

}

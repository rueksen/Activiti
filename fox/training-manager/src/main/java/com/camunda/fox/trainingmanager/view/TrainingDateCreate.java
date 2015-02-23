package com.camunda.fox.trainingmanager.view;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.cdi.annotation.BusinessProcessScoped;

import com.camunda.fox.trainingmanager.model.TrainingDate;
import com.camunda.fox.trainingmanager.service.TrainingDateService;

/**
 * backing bean for implementing the new training date use case 
 * 
 * @author meyerd
 */
@Named
@ConversationScoped
public class TrainingDateCreate implements Serializable {
  
  private static final long serialVersionUID = 1L;

  @Inject private Conversation conversation;
  
  @Inject private TrainingDateService trainingDateService;
    
  private Long trainingId;  
  private TrainingDate newTrainingDate;
  
  public void setTrainingId(Long trainingId) {
    this.trainingId = trainingId;
    // this begins a unit of work
    if(conversation.isTransient()) {
      conversation.begin();
    }
  }
    
  public Long getTrainingId() {
    return trainingId;
  }
  
  @Produces
  @Named("newTrainingDate")
  @BusinessProcessScoped  
  public TrainingDate getTrainingDate() {
    if(newTrainingDate == null) {
      newTrainingDate = new TrainingDate();
    }
    return newTrainingDate;
  }
    
  public String save() {    
    try {
      trainingDateService.createNewTrainingDate(newTrainingDate, trainingId);    
      // this ends the use case / unit of work:
      if(!conversation.isTransient()) {
        conversation.end();
      }
      return "success";
    }catch (RuntimeException e) {
      FacesContext.getCurrentInstance()
        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error while persisting new training date", e.getMessage()));
      return "failure";
    }
  }

}

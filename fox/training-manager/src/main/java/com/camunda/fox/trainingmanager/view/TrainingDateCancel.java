package com.camunda.fox.trainingmanager.view;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.cdi.annotation.ProcessVariable;

import com.camunda.fox.trainingmanager.model.TrainingDate;
import com.camunda.fox.trainingmanager.service.TrainingDateService;

@Named
@ConversationScoped
public class TrainingDateCancel implements Serializable {
  
  private static final long serialVersionUID = 1L;

  @Inject private TrainingDateService trainingDateService;
  
  @Inject @ProcessVariable("trainingDateId") Object trainingDateId;
  
  private TrainingDate trainingDate;
  
  public TrainingDate getTrainingDate() {
    if(trainingDate == null) {
      trainingDate =  trainingDateService.getTrainingDateById((Long) trainingDateId);
    }
    return trainingDate;
  }
  
  

}

package com.camunda.fox.trainingmanager.view;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.cdi.annotation.ProcessVariable;

import com.camunda.fox.trainingmanager.model.TrainingDate;
import com.camunda.fox.trainingmanager.service.TrainingDateServiceConversational;

/**
 * 
 * @author meyerd
 */
@Named
@ConversationScoped
public class DecideTrainingDateTakesPlace implements Serializable {

  private static final long serialVersionUID = 1L;

  // using the stateful version of the service allows us to lazily
  // fetch the associations of the loaded training
  @Inject
  private TrainingDateServiceConversational trainingDateService;

  @Inject
  @ProcessVariable("trainingDateId")
  private Object trainingDateId;

  private TrainingDate trainingDate;

  private boolean showRegistrations;
  private boolean showTraining;

  public TrainingDate getTrainingDate() {
    if (trainingDate == null) {
      trainingDate = trainingDateService.getTrainingDateById((Long) trainingDateId);
    }
    return trainingDate;
  }

  public boolean isShowRegistrations() {
    return showRegistrations;
  }

  public void setShowRegistrations(boolean showRegistrations) {
    this.showRegistrations = showRegistrations;
  }

  public boolean isShowTraining() {
    return showTraining;
  }

  public void setShowTraining(boolean showTraining) {
    this.showTraining = showTraining;
  }

}

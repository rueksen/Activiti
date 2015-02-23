package com.camunda.fox.trainingmanager.view;

import javax.inject.Inject;
import javax.inject.Named;

import com.camunda.fox.trainingmanager.model.Training;
import com.camunda.fox.trainingmanager.service.TrainingService;

@Named
public class TrainingList extends AbstractEntityList<Training> {
  
  @Inject TrainingService trainingService;
  
  private static final long serialVersionUID = 1L;

  @Override
  protected Class<Training> getEntityType() {
    return Training.class;
  }
  
  public void delete(Training training) {
    trainingService.delete(training.getId());
    list.remove(training);
  }

}

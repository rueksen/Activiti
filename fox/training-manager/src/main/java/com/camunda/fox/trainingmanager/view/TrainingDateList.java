package com.camunda.fox.trainingmanager.view;

import javax.inject.Named;

import com.camunda.fox.trainingmanager.model.TrainingDate;

@Named
public class TrainingDateList extends AbstractEntityList<TrainingDate>  {
  
  private static final long serialVersionUID = 1L;

  @Override
  protected Class<TrainingDate> getEntityType() {
    return TrainingDate.class;
  }
  
}

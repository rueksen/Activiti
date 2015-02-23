package com.camunda.fox.trainingmanager.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Trainer extends User {

  @OneToMany(mappedBy = "trainer")
  private List<TrainingDate> trainingDates = new ArrayList<TrainingDate>();

  public List<TrainingDate> getTrainingDates() {
    return trainingDates;
  }

  public void setTrainingDates(List<TrainingDate> trainingDates) {
    this.trainingDates = trainingDates;
  }

}

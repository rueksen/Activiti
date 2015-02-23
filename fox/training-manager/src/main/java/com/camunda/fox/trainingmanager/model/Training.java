package com.camunda.fox.trainingmanager.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Represents a seminar
 * 
 * @author Daniel Meyer
 */
@Entity
public class Training extends AbstractEntity {

  @NotNull
  @Size(min=10, max=50)
  private String name;

  @Basic(fetch = FetchType.LAZY)
  private String description;
  
  // if we remove a training, we remove the attached training dates:
  @OneToMany(cascade=CascadeType.REMOVE)
  private List<TrainingDate> trainingDates = new ArrayList<TrainingDate>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
    
  public List<TrainingDate> getTrainingDates() {
    return trainingDates;
  }
    
  public void setTrainingDates(List<TrainingDate> trainingDates) {
    this.trainingDates = trainingDates;
  }

}

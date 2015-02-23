package com.camunda.fox.trainingmanager.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;


@Entity
public class TrainingDate extends AbstractEntity {
  
  private static final long serialVersionUID = 1L;

  @ManyToOne
  @NotNull
  private Training training;

  @NotNull
  private Date startDate;

  @NotNull
  private Date endDate;

  @NotNull
  private String location;
  
  private String comment;
  
  private boolean cancelled = false;

  @OneToMany(mappedBy = "trainingDate", cascade=CascadeType.REMOVE)
  private List<Registration> registrations = new ArrayList<Registration>();

  @ManyToOne
  private Trainer trainer;

  public Training getTraining() {
    return training;
  }

  public void setTraining(Training training) {
    this.training = training;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  
  public List<Registration> getRegistrations() {
    return registrations;
  }

  public void setRegistrations(List<Registration> registrations) {
    this.registrations = registrations;
  }

  public Trainer getTrainer() {
    return trainer;
  }

  public void setTrainer(Trainer trainer) {
    this.trainer = trainer;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
  
  public String getComment() {
    return comment;
  }
  
  public void setComment(String comment) {
    this.comment = comment;
  }
  
  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }
  
  public boolean isCancelled() {
    return cancelled;
  }

}

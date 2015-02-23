package com.camunda.fox.trainingmanager.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * A registration of an {@link Attendee} in a {@link TrainingDate}
 * 
 * @author meyerd
 */
@Entity
public class Registration extends AbstractEntity {

  @NotNull
  @ManyToOne
  private Attendee attendee;

  @NotNull
  @ManyToOne
  private TrainingDate trainingDate;

  private Date registrationDate;

  public Date getRegistrationDate() {
    return registrationDate;
  }

  public Attendee getAttendee() {
    return attendee;
  }

  public void setAttendee(Attendee attendee) {
    this.attendee = attendee;
  }

  public void setRegistrationDate(Date registrationDate) {
    this.registrationDate = registrationDate;
  }

  public TrainingDate getTrainingDate() {
    return trainingDate;
  }

  public void setTrainingDate(TrainingDate trainingDate) {
    this.trainingDate = trainingDate;
  }

}

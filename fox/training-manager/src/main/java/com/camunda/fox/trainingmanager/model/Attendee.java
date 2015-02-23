package com.camunda.fox.trainingmanager.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * A {@link Person} attending a {@link TrainingDate}
 * 
 * @author meyerd
 */
@Entity
public class Attendee extends Person {

  @OneToMany(mappedBy = "attendee")
  private List<Registration> registrations = new ArrayList<Registration>();

  public List<Registration> getRegistrations() {
    return registrations;
  }

  public void setRegistrations(List<Registration> registrations) {
    this.registrations = registrations;
  }

}

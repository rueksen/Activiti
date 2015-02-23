package com.camunda.fox.trainingmanager.service;

import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.activiti.cdi.BusinessProcess;
import org.activiti.cdi.annotation.StartProcess;

import com.camunda.fox.trainingmanager.model.Registration;
import com.camunda.fox.trainingmanager.model.TrainingDate;


public abstract class RegistrationService {
  
  @Inject private BusinessProcess businessProcess;
  
  public Registration createNewRegistration() {
    return new Registration();
  }
  
  protected abstract EntityManager getEntityManager();

  @StartProcess("attendTrainingDate")  
  public void submitNewRegistration(Registration newRegistration, Long trainingDateId) {
    newRegistration.setRegistrationDate(new Date());
    businessProcess.setVariable("newRegistration", newRegistration);
    businessProcess.setVariable("trainingDateId", trainingDateId);
  }
  
  public void addRegistrationToTrainingDate() {
    Registration newRegistration = businessProcess.getVariable("newRegistration");
    Long trainingDateId = businessProcess.getVariable("trainingDateId");
   
    
    // guarantee referential integrity
    newRegistration.getAttendee().getRegistrations().add(newRegistration);
    TrainingDate trainingDate = getEntityManager().find(TrainingDate.class, trainingDateId);    
    trainingDate.getRegistrations().add(newRegistration);
    newRegistration.setTrainingDate(trainingDate);
    
    getEntityManager().persist(newRegistration.getAttendee());
    getEntityManager().persist(newRegistration);
    
    getEntityManager().flush();
    
    
    
    businessProcess.setVariable("registrationId", newRegistration.getId());
    businessProcess.setVariable("newRegistration", null);
  }
  
  public void notifyAttendee() {
    Registration registration = getRegistrationById((Long) businessProcess.getVariable("registrationId"));
    System.out.println("notifying attendee that registration is ok "+registration.getAttendee().getEmail());
  }
  
  public void sendTrainingDetailsToAttendee() {
    Registration registration = getRegistrationById((Long) businessProcess.getVariable("registrationId"));
    System.out.println("sending training details to "+registration.getAttendee().getEmail());
  }
  
  public Registration getRegistrationById(Long id) {
    return getEntityManager().find(Registration.class, id);    
  }

}

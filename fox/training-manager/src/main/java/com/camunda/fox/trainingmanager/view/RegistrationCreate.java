package com.camunda.fox.trainingmanager.view;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.cdi.annotation.BusinessProcessScoped;

import com.camunda.fox.trainingmanager.model.Attendee;
import com.camunda.fox.trainingmanager.model.Registration;
import com.camunda.fox.trainingmanager.service.RegistrationService;

@Named
@ConversationScoped
public class RegistrationCreate implements Serializable {

  private static final long serialVersionUID = 1L;

  @Inject
  private RegistrationService registrationService;

  @Inject
  Conversation conversation;

  private Registration newRegistration;

  protected Long trainingDateId;

  public void setTrainingDateId(Long trainingDateId) {
    if (conversation.isTransient()) {
      conversation.begin();
    }
    this.trainingDateId = trainingDateId;
  }
  
  
  public Long getTrainingDateId() {
    return trainingDateId;
  }

  @Produces
  @Named
  @BusinessProcessScoped
  public Registration getNewRegistration() {
    if (newRegistration == null) {
      newRegistration = registrationService.createNewRegistration();
      newRegistration.setAttendee(new Attendee());
    }
    return newRegistration;
  }

  public String register() {
    try {
      registrationService.submitNewRegistration(newRegistration, trainingDateId);
      conversation.end();
      return "success";
    } catch (Exception e) {
      return "error";
    }
  }

}

package com.camunda.fox.trainingmanager.view;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.cdi.annotation.ProcessVariable;

import com.camunda.fox.trainingmanager.model.Registration;
import com.camunda.fox.trainingmanager.service.RegistrationServiceConversational;

@ConversationScoped
public class CurrentRegistrationProvider implements Serializable {

  private static final long serialVersionUID = 1L;

  @Inject
  @ProcessVariable
  private Object registrationId;

  @Inject
  private RegistrationServiceConversational registrationServiceConversational;

  private Registration registration;

  @Produces
  @Named("currentRegistration")  
  public Registration getRegistration() {
    if(registration == null) {
      registration = registrationServiceConversational.getRegistrationById((Long)registrationId);
    }
    return registration;
  }

}

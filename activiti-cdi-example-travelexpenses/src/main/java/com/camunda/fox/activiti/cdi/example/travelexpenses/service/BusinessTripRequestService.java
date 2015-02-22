package com.camunda.fox.activiti.cdi.example.travelexpenses.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import com.camunda.fox.activiti.cdi.actor.Actor;
import com.camunda.fox.activiti.cdi.annotations.StartProcess;
import com.camunda.fox.activiti.cdi.example.travelexpenses.domain.BusinessTripRequest;

@Named
@Stateless
public class BusinessTripRequestService {

  @Inject
  Actor actor;

  @Inject
  public BusinessTripRequest businessTripRequest;

  public String employee;

  @StartProcess(value = "authorizeBusinessTripRequest", //
  initialVariables = { "businessTripRequest", "employee" })
  public String submitRequest() {
    employee = actor.getActorId();
    System.out.println(businessTripRequest.getReason());
    return "success";
  }

}

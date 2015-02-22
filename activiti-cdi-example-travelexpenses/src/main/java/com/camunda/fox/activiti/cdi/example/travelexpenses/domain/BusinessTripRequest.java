package com.camunda.fox.activiti.cdi.example.travelexpenses.domain;


import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import com.camunda.fox.activiti.cdi.context.BusinessProcessScoped;

@Named
@BusinessProcessScoped
public class BusinessTripRequest implements Serializable {

  private String startDate;

  private String endDate;

  private String reason;

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

}

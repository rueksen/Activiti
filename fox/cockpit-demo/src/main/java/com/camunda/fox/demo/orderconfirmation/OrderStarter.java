package com.camunda.fox.demo.orderconfirmation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.engine.RuntimeService;

@Named
@SessionScoped
public class OrderStarter implements Serializable {

  private static final long serialVersionUID = 1L;
  private String customer;
  private String details;

  @Inject
  private RuntimeService runtimeService;

  public void placeOrder() {
    String orderXml = "<xml>\n<order>\n<customer>" + customer + "</customer>\n" + "<details>" + details + "</details>\n</order>";

    System.out.println("Staring order for customer '"+customer+"'....");

    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put("orderXml", orderXml);
    runtimeService.startProcessInstanceByKey("CockpitDemoProcess", variables);
    
    customer = null;
    details = null;
  }

  public String getCustomer() {
    return customer;
  }

  public void setCustomer(String customer) {
    this.customer = customer;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

}

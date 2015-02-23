package com.camunda.fox.activiti.cdi.example.travelexpenses.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

public class MailService implements JavaDelegate {

  private Expression from;
  private Expression to;
  private Expression subject;
  private Expression html;

  @Override
  public void execute(DelegateExecution execution) throws Exception {
    System.out.println("sending email from " + from.getValue(execution));
    System.out.println("to: " + to.getValue(execution));
    System.out.println("Subject: " + subject.getValue(execution));
    System.out.println("Body: " + html.getValue(execution));
  }

}

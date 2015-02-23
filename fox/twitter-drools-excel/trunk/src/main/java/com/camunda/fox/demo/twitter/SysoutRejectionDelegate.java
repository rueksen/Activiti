package com.camunda.fox.demo.twitter;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;


public class SysoutRejectionDelegate implements JavaDelegate {

  public void execute(DelegateExecution execution) throws Exception {
    
    System.out.println( ((Tweet)execution.getVariable("tweet")).getRejectionComment() );
    
  }
}

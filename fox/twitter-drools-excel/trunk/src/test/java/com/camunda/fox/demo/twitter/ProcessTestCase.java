package com.camunda.fox.demo.twitter;

import java.util.HashMap;
import java.util.List;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiTestCase;
import org.activiti.engine.test.Deployment;

public class ProcessTestCase extends ActivitiTestCase {
  
  @Deployment(resources = "diagrams/TwitterDemoProcessDroolsExcel.bpmn20.xml")
  public void testRejectJohnDePath() {
    HashMap<String, Object> variables = new HashMap<String, Object>();
    variables.put("content", "We will never see this content on Twitter, because it is about Jon Doe...");
    variables.put("email", "istme@somedomain.org");

    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("TwitterDemoProcessDroolsExcel", variables);
    String id = processInstance.getId();
    System.out.println("Started process instance id " + id);

    assertProcessEnded(id);

    HistoricProcessInstance historicProcessInstance = historicDataService.createHistoricProcessInstanceQuery().processInstanceId(id).singleResult();
    assertNotNull(historicProcessInstance);

    System.out.println("Finished, took " + historicProcessInstance.getDurationInMillis() + " millis");
  }

}
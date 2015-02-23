package com.camunda.fox.demo.twitter;

import java.util.HashMap;
import java.util.List;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiTestCase;
import org.activiti.engine.test.Deployment;
import org.h2.command.ddl.CreateTableData;

public class ProcessTestCase extends ActivitiTestCase {

  public void assertInActivity(String processInstanceId, String activityId) {
    List<String> activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);
    assertTrue("Current activities (" + activeActivityIds + ") does not contain " + activityId, activeActivityIds.contains(activityId));
  }

  @Deployment(resources = "diagrams/TwitterDemoProcessDrools.bpmn20.xml")
  public void testApprovedPath() {
    HashMap<String, Object> variables = new HashMap<String, Object>();
    // TODO: make sure you change the content of the tweet since Twitter doesn't accept duplicate tweets
    variables.put("content", "Hello world from our business rules activiti test case");
    variables.put("email", "istme@somedomain.org");
    
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("TwitterDemoProcessDrools", variables);
    String id = processInstance.getId();
    System.out.println("Started process instance id " + id);
    
    assertProcessEnded(id);

    HistoricProcessInstance historicProcessInstance = historicDataService.createHistoricProcessInstanceQuery().processInstanceId(id).singleResult();
    assertNotNull(historicProcessInstance);

    System.out.println("Finished, took " + historicProcessInstance.getDurationInMillis() + " millis");
  }
  
  @Deployment(resources = "diagrams/TwitterDemoProcessDrools.bpmn20.xml")
  public void testRejectJohnDoePath() {
    HashMap<String, Object> variables = new HashMap<String, Object>();
    variables.put("content", "We will never see this content on Twitter, because it is about John Doe...");
    variables.put("email", "istme@somedomain.org");
    
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("TwitterDemoProcessDrools", variables);
    String id = processInstance.getId();
    System.out.println("Started process instance id " + id);

    assertProcessEnded(id);
    
    // read object from activiti
    assertEquals("Please don't tweet about Jon Doe, he complained about it", CreateTweetObjectDelegate.lastTweetCreated.getRejectionComment());

    HistoricProcessInstance historicProcessInstance = historicDataService.createHistoricProcessInstanceQuery().processInstanceId(id).singleResult();
    assertNotNull(historicProcessInstance);

    System.out.println("Finished, took " + historicProcessInstance.getDurationInMillis() + " millis");
  }
  
  @Deployment(resources = "diagrams/TwitterDemoProcessDrools.bpmn20.xml")
  public void testRejectJohnDoeAndJakobPath() {
    HashMap<String, Object> variables = new HashMap<String, Object>();
    variables.put("content", "We will never see this content on Twitter, because it is about John Doe and from Jakob...");
    variables.put("email", "jakob.freund@camunda.com");   
    
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("TwitterDemoProcessDrools", variables);
    assertProcessEnded(processInstance.getId());
    
    assertEquals("Please don't tweet about Jon Doe, he complained about it AND Jakob isn't allowed tweeting anymore", CreateTweetObjectDelegate.lastTweetCreated.getRejectionComment());
    
  }  
}
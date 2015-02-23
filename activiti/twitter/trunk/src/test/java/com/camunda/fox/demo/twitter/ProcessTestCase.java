package com.camunda.fox.demo.twitter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiTestCase;
import org.activiti.engine.test.Deployment;
import org.junit.Test;

public class ProcessTestCase extends ActivitiTestCase {

  public void assertInActivity(String processInstanceId, String activityId) {
    List<String> activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);
    assertTrue("Current activities (" + activeActivityIds + ") does not contain " + activityId, activeActivityIds.contains(activityId));
  }

  @Deployment(resources = "diagrams/TwitterDemoProcess.bpmn20.xml")
  public void testApprovedPath() {
    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put("content", "Hello from JUnit test case!");
    
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("TwitterDemoProcess", variables);
    String id = processInstance.getId();
    System.out.println("Started process instance id " + id);
    
    assertInActivity(id, "Review_Tweet_3");

    Task task = taskService.createTaskQuery().taskAssignee("kermit").singleResult();
    variables.put("approved", Boolean.TRUE);
    taskService.complete(task.getId(), variables);
    
    assertProcessEnded(id);

    HistoricProcessInstance historicProcessInstance = historicDataService.createHistoricProcessInstanceQuery().processInstanceId(id).singleResult();
    assertNotNull(historicProcessInstance);

    System.out.println("Finished, took " + historicProcessInstance.getDurationInMillis() + " millis");
  }

  @Deployment(resources = "diagrams/TwitterDemoProcess.bpmn20.xml")
  public void testRejectedPath() {
    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put("content", "We will never see this content on Twitter");
    variables.put("email", "bernd.ruecker@camunda.com");
    
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("TwitterDemoProcess", variables);
    String id = processInstance.getId();
    System.out.println("Started process instance id " + id);
    
    assertInActivity(id, "Review_Tweet_3");

    Task task = taskService.createTaskQuery().taskAssignee("kermit").singleResult();
    variables.put("approved", Boolean.FALSE);
    variables.put("comments", "No, we will not publish this on Twitter");
    taskService.complete(task.getId(), variables);
    
    assertProcessEnded(id);

    HistoricProcessInstance historicProcessInstance = historicDataService.createHistoricProcessInstanceQuery().processInstanceId(id).singleResult();
    assertNotNull(historicProcessInstance);
    
    assertEquals(1, historicDataService
	    .createHistoricActivityInstanceQuery()
	    .processInstanceId(historicProcessInstance.getId())
	    .activityId("Send_rejection_notification_via_email__3")
	    .count());

    System.out.println("Finished, took " + historicProcessInstance.getDurationInMillis() + " millis");
  }

  @Test
  public void testStandalone() {
    // deploy process definition
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    RepositoryService repositoryService = processEngine.getRepositoryService();
    DeploymentBuilder deployment = repositoryService.createDeployment();
    deployment.addClasspathResource("diagrams/TwitterDemoProcess.bpmn20.xml");
    deployment.deploy();
   
    // create process variables
    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put("Tweet", "Testing Activiti 5");
    variables.put("Email", "info@camunda.com");

    // start a new process instance
    RuntimeService runtimeService = processEngine.getRuntimeService();
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("TwitterDemoProcess", variables);

    // test
    String id = processInstance.getId();
    System.out.println("Started process instance id " + id);
    assertInActivity(id, "Review_Tweet_3");
  }

}

package com.camunda.fox.demo.twitter;

import java.util.HashMap;
import java.util.List;

import javax.xml.ws.Endpoint;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiTestCase;
import org.activiti.engine.test.Deployment;
import org.restlet.Component;

import com.camunda.fox.demo.twitter.rest.RestServer;
import com.camunda.fox.demo.twitter.ws.WebserviceServer;

public class ProcessTestCase extends ActivitiTestCase {
  
  private Endpoint wsEndpoint;
  private Component restComponent;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    
    wsEndpoint = WebserviceServer.createWebserviceEnpoint();    
    restComponent = RestServer.createRestComponent();
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
    
    wsEndpoint.stop();
    restComponent.stop();
  }

  public void assertInActivity(String processInstanceId, String activityId) {
    List<String> activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);
    assertTrue("Current activities (" + activeActivityIds + ") does not contain " + activityId, activeActivityIds.contains(activityId));
  }

  @Deployment(resources = "TwitterWS.bpmn20.xml")
  public void testApprovedWs() {
    HashMap<String, Object> variables = new HashMap<String, Object>();
    variables.put("email", "bernd.ruecker@camunda.com");
    variables.put("content", "Hello world from the JUnit-Test case");
    
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("TwitterWS", variables);
    String id = processInstance.getId();    
    assertProcessEnded(id);
  }
  
  @Deployment(resources = "TwitterREST.bpmn20.xml")
  public void testApprovedRest() {
    HashMap<String, Object> variables = new HashMap<String, Object>();
    variables.put("email", "bernd.ruecker@camunda.com");
    variables.put("content", "Hello world from the JUnit-Test case");
    
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("TwitterREST", variables);
    String id = processInstance.getId();    
    assertProcessEnded(id);
  }
    
}
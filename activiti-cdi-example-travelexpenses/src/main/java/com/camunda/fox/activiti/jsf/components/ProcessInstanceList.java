package com.camunda.fox.activiti.jsf.components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * TODO: implement pagination
 * 
 * @author Daniel Meyer
 */
@Named
@ConversationScoped
public class ProcessInstanceList implements Serializable {

  @Inject
  ProcessEngine processEngine;

  public List<ProcessInstance> getList() {
    return processEngine.getRuntimeService().createProcessInstanceQuery().list();
  }

  public String getProcessDefinitionName(String id) {
    return processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(id).singleResult().getName();
  }

  public List<String> getVariables(String processInstanceId) {
    return new ArrayList<String>(processEngine.getRuntimeService().getVariables(processInstanceId).keySet());
  }

  public String getProcessVariableValue(String processInstanceId, String variableName) {
    Object value = processEngine.getRuntimeService().getVariable(processInstanceId, variableName);
    if (value == null) {
      return "";

    }
    return value.toString();
  }

}

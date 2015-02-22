package com.camunda.fox.activiti.jsf.components;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.ProcessDefinition;

/**
 * TODO: implement pagination
 * 
 * @author meyerd
 */
@Named
@ConversationScoped
public class ProcessDefinitionList implements Serializable {

  @Inject
  ProcessEngine processEngine;

  public List<ProcessDefinition> getList() {
    return processEngine.getRepositoryService().createProcessDefinitionQuery().list();
  }

}

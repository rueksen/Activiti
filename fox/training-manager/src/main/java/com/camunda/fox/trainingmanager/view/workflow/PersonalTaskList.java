package com.camunda.fox.trainingmanager.view.workflow;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

/**
 * 
 * @author meyerd
 */
@SessionScoped
public class PersonalTaskList implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  @Inject TaskService taskService;
  
  @Produces
  @Named("personalTaskList")
  public List<Task> getList() {
    return taskService.createTaskQuery().list();    
  }
  
}

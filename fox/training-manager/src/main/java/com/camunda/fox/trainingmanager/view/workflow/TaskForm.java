package com.camunda.fox.trainingmanager.view.workflow;

import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.engine.FormService;
import org.activiti.engine.task.Task;

/**
 * 
 * @author meyerd
 */
@Named
public class TaskForm {
  
  @Inject FormService formService;
  
  public String getFormKey(Task task) {
    return formService.getTaskFormData(task.getId()).getFormKey();
  }

}

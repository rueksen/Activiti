package com.camunda.fox.activiti.jsf.components;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.task.Task;

import com.camunda.fox.activiti.cdi.actor.Actor;

/**
 * TODO: implement pagination
 * 
 * @author meyerd
 */
@Named
@ConversationScoped
public class TaskList implements Serializable {

  @Inject
  Actor actor;

  @Inject
  ProcessEngine processEngine;

  public List<Task> getList() {
    return processEngine.getTaskService().createTaskQuery().taskAssignee(actor.getActorId()).list();
  }

  public List<Task> getTasksByName(String taskName) {
    return processEngine.getTaskService().createTaskQuery().taskAssignee(actor.getActorId()).taskName(taskName).list();
  }

  public boolean hasTask(String task) {
    return getTasksByName(task).size() > 0;
  }

}

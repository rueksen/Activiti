package com.camunda.fox.demo.orderconfirmation;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

@SessionScoped
@Named
public class TaskList implements Serializable {

  private static final long serialVersionUID = 1L;

  @Inject
  private TaskService taskService;

  @Inject
  private FormService formService;

  private String currentUser = null;

  public void update() {
    // do nothing here, since a refreh trigger a reload of the list anyway
  }

  public List<Task> getList() {
    if (currentUser != null && currentUser.length() > 0) {
      return taskService.createTaskQuery().processDefinitionKey("CockpitDemoProcess").taskAssignee(currentUser).list();
    } else {
      return taskService.createTaskQuery().processDefinitionKey("CockpitDemoProcess").list();
    }
  }

  public void unclaim(Task task) {
    taskService.claim(task.getId(), null);
  }

  public void claim(Task task) {
    taskService.claim(task.getId(), currentUser);
  }

  public void complete(Task task) {
    taskService.complete(task.getId());
  }

  public String getCurrentUser() {
    return currentUser;
  }

  public void setCurrentUser(String currentUser) {
    this.currentUser = currentUser;
  }

}

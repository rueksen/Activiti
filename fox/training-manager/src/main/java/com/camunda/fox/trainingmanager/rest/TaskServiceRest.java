package com.camunda.fox.trainingmanager.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;

import com.camunda.fox.trainingmanager.rest.dto.ActivitiTaskDto;

@Path("/tasks")
public class TaskServiceRest {

  @Inject
  TaskService taskService;

  @Inject
  FormService formService;

  @GET
  @Path("/")
  @Produces("application/json")
  public List<ActivitiTaskDto> getTasks() {
    // TODO: remove the comments
    // List<Task> tasks = taskService.createTaskQuery().list();
    List<ActivitiTaskDto> taskDtos = new ArrayList<ActivitiTaskDto>();
    // for(Task task : tasks) {
    // String formKey = formService.getTaskFormData(task.getId()).getFormKey();
    // taskDtos.add(new ActivitiTaskDto(task.getName(), task.getDescription(),
    // formKey, task.getAssignee()));
    // }

    // TODO: remove this...
    
    for(int i = 0; i<10; i++){
      taskDtos.add(new ActivitiTaskDto("Dummy Task " + (i+1), "This is a dummy task to test the REST API...", "dummy-form-key-" + (i+1), "kermit"));
    }
    return taskDtos;
  }

}

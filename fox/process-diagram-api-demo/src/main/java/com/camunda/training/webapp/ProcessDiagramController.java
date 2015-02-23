package com.camunda.training.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Bounds;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

@Named
@ApplicationScoped
public class ProcessDiagramController {

  @Inject
  RuntimeService runtimeService;
  
  @Inject
  RepositoryService repositoryService;
  
  @Inject
  TaskService taskService;
  
  List<Bounds> getActiveActivityBoundsOfLatestProcessInstance() {
    ArrayList<Bounds> list = new ArrayList<Bounds>();
    ProcessInstance processInstance = getLastProcessInstance();
    if (processInstance != null) {
      Map<String, Bounds> processDiagramLayout = repositoryService
              .getProcessDiagramLayout(processInstance.getProcessDefinitionId());
      List<String> activeActivityIds = runtimeService.getActiveActivityIds(processInstance.getId());
      for (String activeActivityId : activeActivityIds) {
        list.add(processDiagramLayout.get(activeActivityId));
      }
    }
    return list;
  }

  Task getTask() {
    ProcessInstance processInstance = getLastProcessInstance();
    if (processInstance != null) {
      Task task = taskService
              .createTaskQuery()
              .processInstanceId(processInstance.getId())
              .singleResult();
      return task;
    }
    return null;
  }

  private ProcessInstance getLastProcessInstance() {
    List<ProcessInstance> processInstances = runtimeService
            .createProcessInstanceQuery()
            .processDefinitionKey("process-diagram-api-demo")
            .list();
    if (!processInstances.isEmpty()) {
      return processInstances.get(processInstances.size() - 1);
    }
    return null;
  }

}

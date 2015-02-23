package com.camunda.fox.demo.orderconfirmation;


import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.task.Task;
import org.apache.commons.io.IOUtils;

@WebServlet(value = "/currentProcessDiagram", loadOnStartup = 1)
public class ProcessImageServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Inject
  private TaskService taskService;

  @Inject
  private RuntimeService runtimeService;

  
  @Inject
  private ProcessEngine processEngine;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("image/png");

    String taskId = request.getParameter("taskId");
    Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

    String processInstanceId = task.getProcessInstanceId();
    String processDefinitionId = task.getProcessDefinitionId();

    ProcessDefinitionEntity pde = (ProcessDefinitionEntity) ((RepositoryServiceImpl) processEngine.getRepositoryService()).getDeployedProcessDefinition(processDefinitionId);

    InputStream inputStream = ProcessDiagramGenerator.generateDiagram(pde, "png", runtimeService.getActiveActivityIds(processInstanceId));

    byte[] imageBytes = IOUtils.toByteArray(inputStream);

    response.getOutputStream().write(imageBytes);

  }
}
package com.camunda.fox.trainingmanager.rest.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ActivitiTaskDto {

  private String name;
  private String description;
  private String formKey;
  private String assignee;

  public ActivitiTaskDto(String name, String description, String formKey, String assignee) {
    super();
    this.name = name;
    this.description = description;
    this.formKey = formKey;
    this.assignee = assignee;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getFormKey() {
    return formKey;
  }

  public void setFormKey(String formKey) {
    this.formKey = formKey;
  }

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

}

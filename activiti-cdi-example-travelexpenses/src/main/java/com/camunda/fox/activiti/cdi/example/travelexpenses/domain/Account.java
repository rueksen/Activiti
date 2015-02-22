package com.camunda.fox.activiti.cdi.example.travelexpenses.domain;

import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Entity
public class Account extends AbstractEntity {

  @Size(min = 5, max = 24)
  private String username;

  @Size(min = 5, max = 24)
  private String password;

  @Override
  public String getDisplayText() {
    return username;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}

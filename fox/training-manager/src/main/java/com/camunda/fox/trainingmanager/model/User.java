package com.camunda.fox.trainingmanager.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author Daniel Meyer 
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class User extends Person {

  @NotNull
  @Size(min = 8, max = 16)
  private String password;
    
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}

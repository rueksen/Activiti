package com.camunda.fox.trainingmanager.rest.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AttendeeDto {

  private String firstname;
  private String lastname;
  private String email;

  public AttendeeDto(String firstname, String lastname, String email) {
    super();
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}

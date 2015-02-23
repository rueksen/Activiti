package com.camunda.fox.trainingmanager.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * Represents a person known to the system
 * @author meyerd
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Person extends AbstractEntity {

  @NotNull
  @Size(min = 2, max = 20)
  private String firstname;

  @NotNull
  @Size(min = 2, max = 20)
  private String lastname;

  @NotNull
  @Pattern(regexp = ".+@.+\\.[a-z]+", message="Must provide a vaild email")
  private String email;
  

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

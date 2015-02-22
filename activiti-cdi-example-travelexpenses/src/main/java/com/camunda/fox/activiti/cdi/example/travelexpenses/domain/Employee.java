package com.camunda.fox.activiti.cdi.example.travelexpenses.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

@Entity
public class Employee extends AbstractEntity {

  private String personalnummer;

  @OneToOne(cascade = { CascadeType.PERSIST })
  private Account account;

  @Column(length = 25, nullable = false)
  @Size(max = 25)
  private String firstName;

  @Column(length = 25, nullable = false)
  @Size(max = 25)
  private String lastName;

  @ManyToOne(cascade = { CascadeType.PERSIST })
  private Employee manager;

  public Employee() {
  }

  public Employee(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getName() {
    return firstName + " " + lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public String getDisplayText() {
    return getName();
  }

  @Override
  public String toString() {
    String exp = "%s [id = %d firstName = %s ,lastName = %s]";
    return String.format(exp, super.toString(), getId(), getFirstName(), getLastName());
  }

  public String getOrderedName() {
    return lastName + ", " + firstName;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public String getPersonalnummer() {
    return personalnummer;
  }

  public void setPersonalnummer(String personalnummer) {
    this.personalnummer = personalnummer;
  }

  public Employee getManager() {
    return manager;
  }

  public void setManager(Employee manager) {
    this.manager = manager;
  }

}

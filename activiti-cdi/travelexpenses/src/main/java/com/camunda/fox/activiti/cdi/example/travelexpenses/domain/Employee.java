/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.camunda.fox.activiti.cdi.example.travelexpenses.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

/**
 * 
 * @author Daniel Meyer
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class Employee extends AbstractEntity {

  @Column(length = 25, nullable = false)
  @Size(max = 25)
  private String firstname;

  @Column(length = 25, nullable = false)
  @Size(max = 25)
  private String lastname;

  @ManyToOne(cascade = { CascadeType.PERSIST })
  private Employee manager;

  private String emailAdress;

  public Employee() {
  }

  public Employee(String firstName, String lastName) {
    this.firstname = firstName;
    this.lastname = lastName;
  }

  @Size(min = 5, max = 24)
  private String username;

  @Size(min = 5, max = 24)
  private String password;

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

  public String getName() {
    return firstname + " " + lastname;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstName) {
    this.firstname = firstName;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastName) {
    this.lastname = lastName;
  }

  @Override
  public String getDisplayText() {
    return getName();
  }

  @Override
  public String toString() {
    String exp = "%s [id = %d firstName = %s ,lastName = %s]";
    return String.format(exp, super.toString(), getId(), getFirstname(), getLastname());
  }

  public String getOrderedName() {
    return lastname + ", " + firstname;
  }

  public Employee getManager() {
    return manager;
  }

  public void setManager(Employee manager) {
    this.manager = manager;
  }

  public String getEmailAdress() {
    return emailAdress;
  }

  public void setEmailAdress(String emailAdress) {
    this.emailAdress = emailAdress;
  }
}

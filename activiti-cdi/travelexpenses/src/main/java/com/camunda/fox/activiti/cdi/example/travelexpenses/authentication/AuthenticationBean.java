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
package com.camunda.fox.activiti.cdi.example.travelexpenses.authentication;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.camunda.fox.activiti.cdi.example.travelexpenses.domain.Employee;

/**
 * 
 * @author Daniel Meyer
 */
@Named
@Stateless
public class AuthenticationBean {

  Logger logger = Logger.getLogger(AuthenticationBean.class.getName());

  @Inject 
  private User user;

  @PersistenceContext 
  private EntityManager entityManager;

  private String username;

  private String password;

  public String authenticate() {

    initIfEmpty();

    try {
      if (username == null || password == null) {
        throw new Exception("password or username null.");
      }
      TypedQuery<Employee> accountQuery = entityManager.createQuery("SELECT e FROM Employee e WHERE e.username='" + username + "'", Employee.class);
      Employee account = accountQuery.getSingleResult();
      if (!password.equals(account.getPassword())) {
        throw new Exception("password or username wrong.");
      }
      user.setLoggedIn(username);
      return "success";
    } catch (Exception e) {
      logger.info("Could not login as '" + username + "': " + e.getMessage());
      return "failure";
    }
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  private void initIfEmpty() {

    TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(e) FROM Employee e", Long.class);
    long numberOfEmployees = query.getSingleResult();

    if (numberOfEmployees > 0) {
      return;
    }

    Employee kermit = new Employee("Kermit", "the Frog");
    kermit.setEmailAdress("daniel.meyer@camunda.com");
    kermit.setUsername("kermit");
    kermit.setPassword("kermit");

    Employee gonzo = new Employee("Gonzo", "");
    gonzo.setEmailAdress("daniel.meyer@camunda.com");
    gonzo.setUsername("gonzo");
    gonzo.setPassword("gonzo");

    kermit.setManager(gonzo);
    gonzo.setManager(gonzo);

    entityManager.persist(kermit);

    entityManager.flush();

  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

}

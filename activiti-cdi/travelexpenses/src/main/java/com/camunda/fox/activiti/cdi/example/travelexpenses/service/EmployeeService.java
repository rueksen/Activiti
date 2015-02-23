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
package com.camunda.fox.activiti.cdi.example.travelexpenses.service;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.activiti.cdi.annotation.ProcessVariable;

import com.camunda.fox.activiti.cdi.example.travelexpenses.domain.Employee;

/**
 * 
 * @author Daniel Meyer
 */
@Named
public class EmployeeService {

  @PersistenceContext
  private EntityManager entityManager;
  
  @Inject @ProcessVariable 
  private Object businessTripRequesterUsername;

  @Produces
  @Named
  public Employee businessTripRequester() {
    TypedQuery<Employee> query = entityManager
            .createQuery("SELECT e FROM Employee e WHERE e.username='" + businessTripRequesterUsername + "'", Employee.class);
    return query.getSingleResult();
  }
  
  @Produces
  @Named
  public Employee authorizingManager() {    
    return businessTripRequester().getManager();
  }
  
}

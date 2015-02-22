package com.camunda.fox.activiti.cdi.example.travelexpenses.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.camunda.fox.activiti.cdi.context.ProcessVariable;
import com.camunda.fox.activiti.cdi.example.travelexpenses.datasource.EmployeeDatabase;
import com.camunda.fox.activiti.cdi.example.travelexpenses.domain.Employee;

@Named
@Stateless
public class BusinessTripAuthorizationService {

  @Inject
  @ProcessVariable
  String employee;

  @Inject
  @EmployeeDatabase
  EntityManager entityManager;

  public String getAuthorizingManager() {
    TypedQuery<Employee> query = entityManager.createQuery("SELECT e FROM Employee e WHERE e.account.username='" + employee + "'", Employee.class);
    Employee employee = query.getSingleResult();
    return employee.getManager().getAccount().getUsername();
  }

}

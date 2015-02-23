package com.camunda.fox.activiti.cdi.example.travelexpenses.view;

import javax.inject.Named;

import com.camunda.fox.activiti.cdi.example.travelexpenses.domain.Employee;

/**
 * a list of employees
 * 
 * @author meyerd
 */
@Named
public class EmployeeList extends AbstractEntityList<Employee> {

  private static final long serialVersionUID = 1L;

  @Override
  protected Class<Employee> getEntityType() {
    return Employee.class;
  }

}

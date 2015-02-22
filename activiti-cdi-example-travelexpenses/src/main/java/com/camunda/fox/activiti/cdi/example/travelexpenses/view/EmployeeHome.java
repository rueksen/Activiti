package com.camunda.fox.activiti.cdi.example.travelexpenses.view;

import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import com.camunda.fox.activiti.cdi.example.travelexpenses.domain.Employee;

@Named
@ConversationScoped
public class EmployeeHome extends AbstractHome<Employee> {

  private static final long serialVersionUID = 1L;

  @Produces
  @Named("employees")
  public List<Employee> getList() {
    return employeeDatabaseDAO.getList("select e from Employee e");
  }

}

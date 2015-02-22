package com.camunda.fox.activiti.cdi.example.travelexpenses.view;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.enterprise.context.Conversation;
import javax.inject.Inject;

import com.camunda.fox.activiti.cdi.example.travelexpenses.datasource.EmployeeDatabaseDAO;

public abstract class AbstractHome<T> implements Serializable {

  @Inject
  protected EmployeeDatabaseDAO employeeDatabaseDAO;

  @Inject
  private Conversation conversation;

  private Long id;
  
  private T instance;

  public T getInstance() {
    if (instance == null) {
      if (id != null) {
        instance = loadInstance();
      } else {
        instance = createInstance();
      }
    }
    return instance;
  }

  public T loadInstance() {
    return employeeDatabaseDAO.find(getClassType(), getId());
  }

  public T createInstance() {
    try {
      return getClassType().newInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  private Class<T> getClassType() {
    ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
    return (Class<T>) parameterizedType.getActualTypeArguments()[0];
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public boolean isManaged() {
    return employeeDatabaseDAO.isManaged(getInstance());
  }

  public String save() {
    if (isManaged()) {
      employeeDatabaseDAO.updateObject(getInstance());
    } else {
      employeeDatabaseDAO.createObject(getInstance());
    }
    conversation.end();
    return "saved";
  }

  public String cancel() {
    conversation.end();
    return "cancelled";
  }

  public void initConversation() {
    if (conversation.isTransient()) {
      conversation.begin();
    }
  }

}

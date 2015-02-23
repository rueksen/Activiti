package com.camunda.fox.trainingmanager.view;

import javax.inject.Named;

import com.camunda.fox.trainingmanager.model.User;

/**
 * holds a list of users
 * 
 * @author meyerd
 */
@Named
public class UserList extends AbstractEntityList<User>  {
  
  private static final long serialVersionUID = 1L;

  @Override
  protected Class<User> getEntityType() {
    return User.class;
  }
  
}

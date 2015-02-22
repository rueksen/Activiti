package com.camunda.fox.activiti.cdi.example.travelexpenses.datasource;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Generic DAO.
 * 
 * @author Daniel Meyer
 * 
 */
// participate in EJB transactions
@Stateless
public class EmployeeDatabaseDAO implements Serializable {

  private static final long serialVersionUID = 1L;

  @Inject
  @EmployeeDatabase
  private EntityManager entityManager;

  public Object updateObject(Object o) {
    return entityManager.merge(o);
  }

  public void createObject(Object o) {
    try {
      entityManager.persist(o);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void refreshObject(Object o) {
    entityManager.refresh(o);
  }

  public <T> T find(Class<T> clazz, Long id) {
    return entityManager.find(clazz, id);
  }

  public void deleteObject(Object o) {
    entityManager.remove(o);
  }

  public boolean isManaged(Object o) {
    return entityManager.contains(o);
  }

  @SuppressWarnings("unchecked")
  public <T> List<T> getList(String query) {
    return entityManager.createQuery(query).getResultList();
  }

  @SuppressWarnings("unchecked")
  public <T> T getSingle(String query) {
    return (T) entityManager.createQuery(query).getSingleResult();
  }

}

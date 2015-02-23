package com.camunda.fox.trainingmanager.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Abstract base class for entities 
 * 
 * @author Daniel Meyer
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
  
  @Id
  @GeneratedValue
  private Long id;
  
  @Version
  private Long version;
      
  public Long getId() {
    return id;
  }
    
}

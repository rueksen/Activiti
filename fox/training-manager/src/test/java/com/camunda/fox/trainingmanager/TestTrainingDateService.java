package com.camunda.fox.trainingmanager;

import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.activiti.cdi.BusinessProcess;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.camunda.fox.trainingmanager.model.Training;
import com.camunda.fox.trainingmanager.model.TrainingDate;
import com.camunda.fox.trainingmanager.service.TrainingDateService;
import com.camunda.fox.trainingmanager.service.TrainingService;

@RunWith(Arquillian.class)
public class TestTrainingDateService {
  
  @Deployment
  public static JavaArchive createDeployment() {
     return ShrinkWrap.create(JavaArchive.class, "test.jar")
             .addPackages(true, "com.camunda.fox")
             .addAsResource("diagrams/createTrainingDate_executable.bpmn20.xml")
             .addAsManifestResource("META-INF/processes.xml", ArchivePaths.create("processes.xml"))
             .addAsManifestResource("META-INF/beans.xml", ArchivePaths.create("beans.xml"))
             .addAsManifestResource("META-INF/persistence.xml", ArchivePaths.create("persistence.xml"));
  }
  
  @Inject private TrainingService trainingService;
  @Inject private TrainingDateService trainingDateService; 
  
  @Inject private RuntimeService runtimeService;  
  @Inject private TaskService taskService;
  @Inject private BusinessProcess businessProcess;
  
  @PersistenceContext
  private EntityManager entityManager;

  
  @Test
  public void testTrainingDateLifecycle() throws Exception {
    
    Assert.assertEquals(0, runtimeService.createProcessInstanceQuery().count());
    
    Training training = trainingService.createNewTraining();
    training.setName("Java EE & Activiti Fundamentals");
    trainingService.persistNewTraining(training);  
         
    TrainingDate trainingDate = trainingDateService.createNewTrainingDate();
    trainingDate.setComment("New Comment");
    trainingDateService.createNewTrainingDate(trainingDate, training.getId());
    Long id = trainingDate.getId();
        
    Assert.assertEquals(1, runtimeService.createProcessInstanceQuery().count());    
    String pid = runtimeService.createProcessInstanceQuery().singleResult().getId();    
    Assert.assertEquals(id, runtimeService.getVariable(pid, "trainingDateId"));
    
    // now do the usertask:
    businessProcess.startTask(taskService.createTaskQuery().singleResult().getId());
    trainingDate = businessProcess.getVariable("newTrainingDate");
    trainingDate.setStartDate(new Date());
    trainingDate.setEndDate(new Date(System.currentTimeMillis()+ 1000));
    trainingDate.setLocation("Berlin");
    businessProcess.completeTask();
    
    businessProcess.associateExecutionById(runtimeService.createProcessInstanceQuery().singleResult().getId());
    // now the newTrainingDate is persistent in the database:
    Long trainingDateId = businessProcess.getVariable("trainingDateId");
    TrainingDate persistentTrainingDate = entityManager.find(TrainingDate.class,trainingDateId);
    Assert.assertNotNull(persistentTrainingDate);
    // 'newTrainingDate' is null and 'trainingId' as well:
    Assert.assertNull(businessProcess.getVariable("newTrainingDate"));
    Assert.assertNull(businessProcess.getVariable("trainingId"));   
    
  }
  
  
  
}

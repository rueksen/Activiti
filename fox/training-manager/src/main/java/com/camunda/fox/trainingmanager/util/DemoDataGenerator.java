package com.camunda.fox.trainingmanager.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import com.camunda.fox.trainingmanager.model.Attendee;
import com.camunda.fox.trainingmanager.model.Registration;
import com.camunda.fox.trainingmanager.model.Trainer;
import com.camunda.fox.trainingmanager.model.Training;
import com.camunda.fox.trainingmanager.model.TrainingDate;


// TODO: get rid of this, add stuff to import.sql OR use service layer
@Singleton
@Startup
public class DemoDataGenerator {
  
  @PersistenceContext
  private EntityManager entityManager;

  @PostConstruct
  public void setupTrainings() throws ParseException, NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
    
    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    
    // Trainers
    Trainer bernd = new Trainer();
    bernd.setEmail("bernd.ruecker@camunda.com");
    bernd.setFirstname("Bernd");
    bernd.setLastname("Rücker");
    bernd.setPassword("cam123456");
  
    entityManager.persist(bernd);
    
    Trainer jakob = new Trainer();
    jakob.setEmail("jakob.freund@camunda.com");
    jakob.setFirstname("jakob");
    jakob.setLastname("Freund");
    jakob.setPassword("cam123456");
    
    entityManager.persist(jakob);
    
    // Attendees
    Attendee kermit = new Attendee();
    kermit.setEmail("kermit@camunda.com");
    kermit.setFirstname("Kermit");
    kermit.setLastname("The Frog");

    entityManager.persist(kermit);
    
    Attendee fozzie = new Attendee();
    fozzie.setEmail("fozzie@camunda.com");
    fozzie.setFirstname("Fozzie");
    fozzie.setLastname("Bear");
    
    entityManager.persist(fozzie);
    
    // Trainings
    Training activitiTraining = new Training();
    activitiTraining.setName("ca045 - Activiti 5 Classroom Training");
    activitiTraining.setDescription("In our three-day training we prepare you for using the open source BPM Platform Activiti successfully in your own projects.");
    
    entityManager.persist(activitiTraining);
    
    Training bpmnTraining = new Training();
    bpmnTraining.setName("ca020 - BPMN 2.0 Training");
    bpmnTraining.setDescription("The Business Process Modeling Notation is the global standard for process modeling, and brings business and IT together.");

    entityManager.persist(bpmnTraining);
    
    // Training dates
    
    // bpmn
    TrainingDate bpmnDate01 = new TrainingDate();
    bpmnDate01.setStartDate(formatter.parse("12.09.2011"));
    bpmnDate01.setEndDate(formatter.parse("14.09.2011"));
    bpmnDate01.setLocation("Stuttgart");
    bpmnDate01.setTrainer(jakob);
    bpmnDate01.setTraining(bpmnTraining);
    bpmnTraining.getTrainingDates().add(bpmnDate01);
        
    entityManager.persist(bpmnDate01);
    
    TrainingDate bpmnDate02 = new TrainingDate();
    bpmnDate02.setStartDate(formatter.parse("17.10.2011"));
    bpmnDate02.setEndDate(formatter.parse("19.10.2011"));
    bpmnDate02.setLocation("Berlin");
    bpmnDate02.setTrainer(jakob);
    bpmnDate02.setTraining(bpmnTraining);
    bpmnTraining.getTrainingDates().add(bpmnDate02);
        
    entityManager.persist(bpmnDate02);
    
    TrainingDate bpmnDate03 = new TrainingDate();
    bpmnDate03.setStartDate(formatter.parse("14.11.2011"));
    bpmnDate03.setEndDate(formatter.parse("16.11.2011"));
    bpmnDate03.setLocation("Berlin");
    bpmnDate03.setTrainer(jakob);
    bpmnDate03.setTraining(bpmnTraining);
    bpmnTraining.getTrainingDates().add(bpmnDate03);
    
    entityManager.persist(bpmnDate03);
        
    // activiti
    TrainingDate actDate01 = new TrainingDate();
    actDate01.setStartDate(formatter.parse("19.09.2011"));
    actDate01.setEndDate(formatter.parse("21.09.2011"));
    actDate01.setLocation("Stuttgart");
    actDate01.setTrainer(bernd);
    actDate01.setTraining(activitiTraining);
    activitiTraining.getTrainingDates().add(actDate01);
    
    entityManager.persist(actDate01);
    
    TrainingDate actDate02 = new TrainingDate();
    actDate02.setStartDate(formatter.parse("21.11.2011"));
    actDate02.setEndDate(formatter.parse("23.11.2011"));
    actDate02.setLocation("Berlin");
    actDate02.setTrainer(bernd);
    actDate02.setTraining(activitiTraining);
    activitiTraining.getTrainingDates().add(actDate02);
    
    entityManager.persist(actDate02);
    
    // Registrations
    Registration bpmnRegKermit = new Registration();
    bpmnRegKermit.setAttendee(kermit);
    bpmnRegKermit.setTrainingDate(bpmnDate01);
    
    entityManager.persist(bpmnRegKermit);
    
    Registration bpmRegFozzie = new Registration();
    bpmRegFozzie.setAttendee(fozzie);
    bpmRegFozzie.setTrainingDate(bpmnDate01);
    
    entityManager.persist(bpmRegFozzie);
    // TODO add attendees and registrations...
  }
  
}

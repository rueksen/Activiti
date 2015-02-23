package com.camunda.fox.trainingmanager.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.camunda.fox.trainingmanager.model.Registration;
import com.camunda.fox.trainingmanager.model.TrainingDate;
import com.camunda.fox.trainingmanager.rest.dto.AttendeeDto;
import com.camunda.fox.trainingmanager.rest.dto.NewTrainingDateDto;
import com.camunda.fox.trainingmanager.rest.dto.TrainingDateDto;
import com.camunda.fox.trainingmanager.service.TrainingDateService;
import com.camunda.fox.trainingmanager.service.TrainingService;

@Stateless
@Path("/trainingDate")
public class TrainingDateServiceRest {
      
  @Inject
  private TrainingDateService trainingDateService;

  @Inject
  private TrainingService trainingService;
  
  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @Path("/")  
  public void createTraining(NewTrainingDateDto newTrainingDto) {
    
    TrainingDate trainingDate = new TrainingDate();
    trainingDate.setStartDate(newTrainingDto.getStartdate());
    trainingDate.setEndDate(newTrainingDto.getEnddate());
    trainingDate.setLocation(newTrainingDto.getLocation());
    
    trainingDateService.createNewTrainingDate(trainingDate, newTrainingDto.getTrainingId());
  }
  
  @GET
  @Path("/list")
  @Produces("application/json")
  public List<TrainingDateDto> getAllTrainingDates() {
    List<TrainingDate> trainingDates = trainingDateService.findAllTrainingDates();
    List<TrainingDateDto> traingDateDtos = new ArrayList<TrainingDateDto>();
    for(TrainingDate td : trainingDates) {
      List<AttendeeDto> attendees = new ArrayList<AttendeeDto>();
      for(Registration reg : td.getRegistrations()) {
        attendees.add(new AttendeeDto(reg.getAttendee().getFirstname(), reg.getAttendee().getLastname(), reg.getAttendee().getEmail()));
      }
      traingDateDtos.add(new TrainingDateDto(td.getId(), td.getTraining().getName(), td.getStartDate(), td.getEndDate(), td.getLocation(), attendees));
    }
    return traingDateDtos;
  }
}

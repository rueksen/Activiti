package com.camunda.fox.trainingmanager.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.camunda.fox.trainingmanager.model.Training;
import com.camunda.fox.trainingmanager.rest.dto.TrainingDto;
import com.camunda.fox.trainingmanager.service.TrainingService;

@Stateless
@Path("/training")
public class TrainingServiceRest {
      
  @Inject
  private TrainingService trainingService;

  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @Path("/")  
  public TrainingDto createTraining(TrainingDto trainingDto) {
    Training training = trainingService.createNewTraining();
    training.setName(trainingDto.getName());
    training.setDescription(trainingDto.getDescription());
    trainingService.persistNewTraining(training);
    return trainingDto;
  }

  @GET
  @Path("/{id:[0-9][0-9]*}")
  @Produces("application/json")
  public TrainingDto getTrainingById(@PathParam("id") long id) {
    Training training = trainingService.getTrainingById(id);
    if(training == null) {
      throw new RuntimeException("No Training found with id '" + id + "'");
    }
    return new TrainingDto(training.getId(), training.getName(), training.getDescription());
  }

  @GET
  @Path("/list")
  @Produces("application/json")
  public List<TrainingDto> getAllTrainings() {
    List<Training> trainings = trainingService.findAllTrainings();
    List<TrainingDto> trainingDtos = new ArrayList<TrainingDto>();
    for(Training training : trainings) {
      trainingDtos.add(new TrainingDto(training.getId(), training.getName(), training.getDescription()));
    }
    return trainingDtos;
  }

  @PUT
  @Consumes("application/json")  
  @Path("/{id:[0-9][0-9]*}")  
  public void updateTraining(@PathParam("id") long id, TrainingDto trainingDto) {    
    // as this is a @Stateless bean, the instance returned by getTrainingById(id); 
    // is not detached, we can pass it to updateTraining(training); and the entity manager that loaded 
    // it is still open.
    Training training = trainingService.getTrainingById(id);
    
    training.setName(trainingDto.getName());
    training.setDescription(trainingDto.getDescription());    
    
    trainingService.updateTraining(training);
  }

  @DELETE
  @Path("/{id:[0-9][0-9]*}")
  public void deleteTrainingById(@PathParam("id") long id) {
    trainingService.delete(id);
  }
}

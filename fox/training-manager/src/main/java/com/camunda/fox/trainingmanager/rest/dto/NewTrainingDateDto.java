package com.camunda.fox.trainingmanager.rest.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NewTrainingDateDto extends TrainingDateDto {

  private Long trainingId;
  private Date startdate;
  private Date enddate;
  private String location;

  public NewTrainingDateDto() {}

  public NewTrainingDateDto(Long trainingId, Date startdate, Date enddate, String location) {
    this.trainingId = trainingId;
    this.startdate = startdate;
    this.enddate = enddate;
    this.location = location;
  }

  public Long getTrainingId() {
    return trainingId;
  }

  public void setTrainingId(Long trainingId) {
    this.trainingId = trainingId;
  }

  public Date getStartdate() {
    return startdate;
  }

  public void setStartdate(Date startdate) {
    this.startdate = startdate;
  }

  public Date getEnddate() {
    return enddate;
  }

  public void setEnddate(Date enddate) {
    this.enddate = enddate;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

}

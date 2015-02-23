package com.camunda.fox.trainingmanager.rest.dto;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TrainingDateDto {

  private long id;
  private String name;
  private Date startdate;
  private Date enddate;
  private String location;
  private List<AttendeeDto> attendees;

  public TrainingDateDto() {}
  
  public TrainingDateDto(long id, String name, Date startdate, Date enddate, String location, List<AttendeeDto> attendees) {
    super();
    this.id = id;
    this.name = name;
    this.startdate = startdate;
    this.enddate = enddate;
    this.location = location;
    this.attendees = attendees;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public List<AttendeeDto> getAttendees() {
    return attendees;
  }

  public void setAttendees(List<AttendeeDto> attendees) {
    this.attendees = attendees;
  }

}

package org.dsmhack.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ReportUser {
  private String userGuid;
  private String firstName;
  private String lastName;
  private List<ReportProject> projects = new ArrayList<ReportProject>();
  private double totalHours = 0;

  public ReportUser() {
  }

  public String getUserGuid() {
    return userGuid;
  }

  public ReportUser setUserGuid(String userGuid) {
    this.userGuid = userGuid;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public ReportUser setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public ReportUser setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public List<ReportProject> getProjects() {
    return projects;
  }

  public ReportUser setProjects(List<ReportProject> projects) {
    this.projects = projects;
    return this;
  }

  public double getTotalHours() {
    return totalHours;
  }

  public ReportUser setTotalHours(double totalHours) {
    this.totalHours = totalHours;
    return this;
  }

  public String toJson() {
    return new Gson().toJson(this);
  }
}


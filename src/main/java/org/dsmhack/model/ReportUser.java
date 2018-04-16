package org.dsmhack.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReportUser {
  private UUID userGuid;
  private String firstName;
  private String lastName;
  private List<ReportProject> projects = new ArrayList<ReportProject>();
  private double totalHours = 0;

  public ReportUser() {
  }

  public UUID getUserGuid() {
    return userGuid;
  }

  public ReportUser setUserGuid(UUID userGuid) {
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


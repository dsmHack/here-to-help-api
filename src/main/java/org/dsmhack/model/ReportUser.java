package org.dsmhack.model;

import java.util.List;

public class ReportUser   {
  private String firstName = null;
  private String lastName = null;
  private List<ReportProject> projects = null;
  private Integer totalHours = null;

  public ReportUser() {
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

  public Integer getTotalHours() {
    return totalHours;
  }

  public ReportUser setTotalHours(Integer totalHours) {
    this.totalHours = totalHours;
    return this;
  }
}


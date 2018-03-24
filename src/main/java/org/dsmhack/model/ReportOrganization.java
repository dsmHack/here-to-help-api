package org.dsmhack.model;

import java.util.List;

public class ReportOrganization   {
  private List<ReportProject> projects = null;
  private List<ReportUser> users = null;
  private Integer totalHours = null;

  public ReportOrganization() {
  }

  public List<ReportProject> getProjects() {
    return projects;
  }

  public ReportOrganization setProjects(List<ReportProject> projects) {
    this.projects = projects;
    return this;
  }

  public List<ReportUser> getUsers() {
    return users;
  }

  public ReportOrganization setUsers(List<ReportUser> users) {
    this.users = users;
    return this;
  }

  public Integer getTotalHours() {
    return totalHours;
  }

  public ReportOrganization setTotalHours(Integer totalHours) {
    this.totalHours = totalHours;
    return this;
  }
}


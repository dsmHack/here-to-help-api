package org.dsmhack.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ReportOrganization   {
  private List<ReportProject> projects = new ArrayList<ReportProject>();
  private List<ReportUser> users = new ArrayList<ReportUser>();
  private double totalHours = 0;

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

  public double getTotalHours() {
    return totalHours;
  }

  public ReportOrganization setTotalHours(double totalHours) {
    this.totalHours = totalHours;
    return this;
  }

  public String toJson() {
    return new Gson().toJson(this);
  }
}


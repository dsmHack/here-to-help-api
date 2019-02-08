package org.dsmhack.model;

import com.google.gson.Gson;

public class ReportProject {
  private String projectGuid;
  private String name;
  private double totalHours = 0;

  public ReportProject() {
  }

  public String getProjectGuid() {
    return projectGuid;
  }

  public ReportProject setProjectGuid(String projectGuid) {
    this.projectGuid = projectGuid;
    return this;
  }

  public String getName() {
    return name;
  }

  public ReportProject setName(String name) {
    this.name = name;
    return this;
  }

  public double getTotalHours() {
    return totalHours;
  }

  public ReportProject setTotalHours(double totalHours) {
    this.totalHours = totalHours;
    return this;
  }

  public String toJson() {
    return new Gson().toJson(this);
  }
}


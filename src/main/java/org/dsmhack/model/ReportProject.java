package org.dsmhack.model;

import com.google.gson.Gson;

import java.util.UUID;

public class ReportProject   {
  private UUID projectGuid;
  private String name;
  private double totalHours = 0;

  public ReportProject() {
  }

  public UUID getProjectGuid() {
    return projectGuid;
  }

  public ReportProject setProjectGuid(UUID projectGuid) {
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


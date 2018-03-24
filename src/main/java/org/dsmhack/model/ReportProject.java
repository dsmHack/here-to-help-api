package org.dsmhack.model;

public class ReportProject   {
  private String name = null;
  private Integer totalHours = null;

  public ReportProject() {
  }

  public String getName() {
    return name;
  }

  public ReportProject setName(String name) {
    this.name = name;
    return this;
  }

  public Integer getTotalHours() {
    return totalHours;
  }

  public ReportProject setTotalHours(Integer totalHours) {
    this.totalHours = totalHours;
    return this;
  }
}


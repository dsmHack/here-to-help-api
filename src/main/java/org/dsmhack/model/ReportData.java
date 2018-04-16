package org.dsmhack.model;

import com.google.gson.Gson;

import java.time.LocalDateTime;


public class ReportData {
    private String projectGuid;
    private String projectName;
    private String userGuid;
    private String firstName;
    private String lastName;
    private LocalDateTime timeIn;
    private LocalDateTime timeOut;

    public ReportData() {
    }

    public String getProjectGuid() {
        return projectGuid;
    }

    public ReportData setProjectGuid(String projectGuid) {
        this.projectGuid = projectGuid;
        return this;
    }

    public String getProjectName() {
        return projectName;
    }

    public ReportData setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public ReportData setUserGuid(String userGuid) {
        this.userGuid = userGuid;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public ReportData setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ReportData setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public LocalDateTime getTimeIn() {
        return timeIn;
    }

    public ReportData setTimeIn(LocalDateTime timeIn) {
        this.timeIn = timeIn;
        return this;
    }

    public LocalDateTime getTimeOut() {
        return timeOut;
    }

    public ReportData setTimeOut(LocalDateTime timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}

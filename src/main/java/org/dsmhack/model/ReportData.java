package org.dsmhack.model;

import com.google.gson.Gson;

import java.sql.Timestamp;
import java.util.UUID;

public class ReportData {
    private UUID projectGuid;
    private String projectName;
    private UUID userGuid;
    private String firstName;
    private String lastName;
    private Timestamp timeIn;
    private Timestamp timeOut;

    public ReportData() {
    }

    public UUID getProjectGuid() {
        return projectGuid;
    }

    public ReportData setProjectGuid(UUID projectGuid) {
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

    public UUID getUserGuid() {
        return userGuid;
    }

    public ReportData setUserGuid(UUID userGuid) {
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

    public Timestamp getTimeIn() {
        return timeIn;
    }

    public ReportData setTimeIn(Timestamp timeIn) {
        this.timeIn = timeIn;
        return this;
    }

    public Timestamp getTimeOut() {
        return timeOut;
    }

    public ReportData setTimeOut(Timestamp timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}

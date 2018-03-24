package org.dsmhack.service;

import org.dsmhack.model.ReportData;
import org.dsmhack.model.ReportOrganization;
import org.dsmhack.model.ReportProject;
import org.dsmhack.model.ReportUser;
import org.dsmhack.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    public ReportOrganization getReportOrganization(String organizationId) {
        List<ReportData> reportDatas = reportRepository.findAllReportingInformation(organizationId);

        ReportOrganization reportOrganization = new ReportOrganization();
        List<ReportProject> organizationProjects = new ArrayList<ReportProject>();
        List<ReportUser> projectUsers = new ArrayList<ReportUser>();
        double organizationTotalHours = 0;
        for (ReportData reportData : reportDatas) {
            ReportProject organizationProject = new ReportProject();

            organizationProject.setName(reportData.getProjectName());
            organizationProject.setTotalHours(calculateHours(reportData.getTimeIn(), reportData.getTimeOut()));

            if (!userGuidExists(reportData.getUserGuid(), projectUsers)) {
                ReportUser reportUser = new ReportUser();
                reportUser.setUserGuid(reportData.getUserGuid());
                reportUser.setFirstName(reportData.getFirstName());
                reportUser.setLastName(reportData.getLastName());
                reportUser.setTotalHours(calculateHours(reportData.getTimeIn(), reportData.getTimeOut()));

                ReportProject userProject = new ReportProject();
                userProject.setProjectGuid(reportData.getProjectGuid());
                userProject.setName(reportData.getProjectName());
                userProject.setTotalHours(calculateHours(reportData.getTimeIn(), reportData.getTimeOut()));
                reportUser.setProjects(Arrays.asList(userProject));

                projectUsers.add(reportUser);
            } else {
                ReportUser reportUser = findUser(reportData.getUserGuid(), projectUsers);
                double totalHours = calculateHours(reportData.getTimeIn(), reportData.getTimeOut());
                if (!projectGuidExists(reportData.getProjectGuid(), reportUser.getProjects())) {
                    ReportProject userProject = new ReportProject();
                    userProject.setProjectGuid(reportData.getProjectGuid());
                    userProject.setName(reportData.getProjectName());

                    userProject.setTotalHours(totalHours);
                    List<ReportProject> reportProjects = new ArrayList<ReportProject>();
                    reportProjects.addAll(reportUser.getProjects());
                    reportProjects.add(userProject);
                    reportUser.setProjects(reportProjects);
                } else {
                    ReportProject reportProject = findReportProject(reportData.getProjectGuid(), reportUser.getProjects());
                    reportProject.setTotalHours(reportProject.getTotalHours() + totalHours);
                }
                reportUser.setTotalHours(reportUser.getTotalHours() + totalHours);
            }

            organizationProjects.add(organizationProject);
            organizationTotalHours += calculateHours(reportData.getTimeIn(), reportData.getTimeOut());
        }
        reportOrganization.setProjects(organizationProjects);
        reportOrganization.setUsers(projectUsers);
        reportOrganization.setTotalHours(organizationTotalHours);
        return reportOrganization;
    }

    ReportUser findUser(String guid, List<ReportUser> users) {
        for (ReportUser user : users) {
            if (user.getUserGuid().equals(guid)) {
                return user;
            }
        }
        return new ReportUser();
    }

    boolean projectGuidExists(String guid, List<ReportProject> projects) {
        for (ReportProject project : projects) {
            if (project.getProjectGuid().equals(guid)) {
                return true;
            }
        }
        return false;
    }

    ReportProject findReportProject(String guid, List<ReportProject> projects) {
        for (ReportProject project: projects) {
            if (project.getProjectGuid().equals(guid)) {
                return project;
            }
        }
        return new ReportProject();
    }

    boolean userGuidExists(String guid, List<ReportUser> users) {
        for (ReportUser user : users) {
            if (user.getUserGuid().equals(guid)) {
                return true;
            }
        }
        return false;
    }

    double calculateHours(Timestamp checkIn, Timestamp checkOut) {
        return (double) checkIn.toLocalDateTime().until(checkOut.toLocalDateTime(), ChronoUnit.MINUTES) / 60;
    }
}

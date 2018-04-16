package org.dsmhack.service;

import org.dsmhack.model.ReportData;
import org.dsmhack.model.ReportOrganization;
import org.dsmhack.model.ReportProject;
import org.dsmhack.model.ReportUser;
import org.dsmhack.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    public ReportOrganization getReportDataAsJson(String organizationId) {
        List<ReportData> reportDatas = reportRepository.findAllReportingInformation(organizationId);
        ReportOrganization reportOrganization = buildReportOrganizationSkeleton(reportDatas);
        for (ReportData reportData : reportDatas) {
            double totalHours = calculateHours(reportData.getTimeIn(), reportData.getTimeOut());

            ReportProject organizationProject = findReportProject(reportData.getProjectGuid(), reportOrganization.getProjects());
            organizationProject.setTotalHours(organizationProject.getTotalHours() + totalHours);

            ReportUser reportUser = findUser(reportData.getUserGuid(), reportOrganization.getUsers());
            ReportProject userProject = findReportProject(reportData.getProjectGuid(), reportUser.getProjects());
            userProject.setTotalHours(userProject.getTotalHours() + totalHours);
            reportUser.setTotalHours(reportUser.getTotalHours() + totalHours);

            reportOrganization.setTotalHours(reportOrganization.getTotalHours() + totalHours);
        }
        return reportOrganization;
    }

    public String getReportDataAsCsv(String organizationId) {
        List<ReportData> reportDatas = reportRepository.findAllReportingInformation(organizationId);
        String reportDataAsCsv = "ProjectName,FirstName,LastName,TimeIn,TimeOut\r\n";
        for (ReportData reportData : reportDatas) {
            reportDataAsCsv += reportData.getProjectName() + ","
                    + reportData.getFirstName() + ","
                    + reportData.getLastName() + ","
                    + reportData.getTimeIn() + ","
                    + reportData.getTimeOut();
            if (reportDatas.indexOf(reportData) != reportDatas.size() - 1) {
                reportDataAsCsv += "\r\n";
            }
        }
        return reportDataAsCsv;
    }

    public ReportOrganization buildReportOrganizationSkeleton(List<ReportData> reportDatas) {
        ReportOrganization reportOrganization = new ReportOrganization();

        List<ReportProject> uniqueProjects = buildUniqueProjects(reportDatas);
        reportOrganization.setProjects(uniqueProjects);

        List<ReportUser> uniqueUsers = buildUniqueUsers(reportDatas);
        for (ReportUser uniqueUser : uniqueUsers) {
            List<ReportProject> projects = new ArrayList<ReportProject>();
            for (ReportProject uniqueProject : uniqueProjects) {
                ReportProject project = new ReportProject();
                project.setProjectGuid(uniqueProject.getProjectGuid());
                project.setName(uniqueProject.getName());
                projects.add(project);
            }
            uniqueUser.setProjects(projects);
        }
        reportOrganization.setUsers(uniqueUsers);

        return reportOrganization;
    }

    private List<ReportProject> buildUniqueProjects(List<ReportData> reportDatas) {
        List<ReportProject> uniqueProjects = new ArrayList<ReportProject>();
        for (ReportData reportData : reportDatas) {
            if (!projectGuidExists(reportData.getProjectGuid(), uniqueProjects)) {
                ReportProject project = new ReportProject();
                project.setProjectGuid(reportData.getProjectGuid());
                project.setName(reportData.getProjectName());
                uniqueProjects.add(project);
            }
        }
        return uniqueProjects;
    }

    private List<ReportUser> buildUniqueUsers(List<ReportData> reportDatas) {
        List<ReportUser> uniqueUsers = new ArrayList<ReportUser>();
        for (ReportData reportData : reportDatas) {
            if (!userGuidExists(reportData.getUserGuid(), uniqueUsers)) {
                ReportUser user = new ReportUser();
                user.setUserGuid(reportData.getUserGuid());
                user.setFirstName(reportData.getFirstName());
                user.setLastName(reportData.getLastName());
                uniqueUsers.add(user);
            }
        }
        return uniqueUsers;
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

    double calculateHours(LocalDateTime checkIn, LocalDateTime checkOut) {
        if (checkOut == null) {
            return 0;
        }
        return (double) checkIn.until(checkOut, ChronoUnit.MINUTES) / 60;
    }
}

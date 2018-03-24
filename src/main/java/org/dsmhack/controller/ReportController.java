package org.dsmhack.controller;

import org.dsmhack.model.ReportOrganization;
import org.dsmhack.model.ReportProject;
import org.dsmhack.model.ReportUser;
import org.dsmhack.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/organizations/{organizationId}/reports")
    public ReportOrganization getReportOrganization(@PathVariable String organizationId) {
        //return reportService.getReportOrganization(organizationId);
        ReportOrganization reportOrganization = new ReportOrganization();

        ReportProject reportProject1 = new ReportProject();
        reportProject1.setName("Project #1");
        reportProject1.setTotalHours(16);
        ReportProject reportProject2 = new ReportProject();
        reportProject2.setName("Project #2");
        reportProject2.setTotalHours(20);
        reportOrganization.setProjects(Arrays.asList(reportProject1, reportProject2));

        ReportUser reportUser1 = new ReportUser();
        reportUser1.setFirstName("John");
        reportUser1.setLastName("Smith");
        ReportProject userReportProject1 = new ReportProject();
        userReportProject1.setName("Project #1");
        userReportProject1.setTotalHours(0);
        ReportProject userReportProject2 = new ReportProject();
        userReportProject2.setName("Project #2");
        userReportProject2.setTotalHours(8);
        reportUser1.setProjects(Arrays.asList(userReportProject1, userReportProject2));
        reportUser1.setTotalHours(8);
        ReportUser reportUser2 = new ReportUser();
        reportUser2.setFirstName("John");
        reportUser2.setLastName("Smith");
        ReportProject userReportProject3 = new ReportProject();
        userReportProject3.setName("Project #1");
        userReportProject3.setTotalHours(16);
        ReportProject userReportProject4 = new ReportProject();
        userReportProject4.setName("Project #2");
        userReportProject4.setTotalHours(12);
        reportUser2.setProjects(Arrays.asList(userReportProject3, userReportProject4));
        reportUser2.setTotalHours(28);
        reportOrganization.setUsers(Arrays.asList(reportUser1, reportUser2));

        reportOrganization.setTotalHours(36);

        return reportOrganization;
    }
}

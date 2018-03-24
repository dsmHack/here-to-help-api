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
        return reportService.getReportOrganization(organizationId);
    }
}

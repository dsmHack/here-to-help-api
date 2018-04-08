package org.dsmhack.controller;

import org.dsmhack.model.ReportData;
import org.dsmhack.model.ReportOrganization;
import org.dsmhack.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/organizations/{organizationId}/reports/json")
    public ReportOrganization getReportDataAsJson(@PathVariable String organizationId) {
        return reportService.getReportDataAsJson(organizationId);
    }

    @GetMapping("/organizations/{organizationId}/reports/csv")
    public String getReportDataAsCsv(@PathVariable String organizationId) {
        return reportService.getReportDataAsCsv(organizationId);
    }
}

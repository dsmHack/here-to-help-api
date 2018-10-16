package org.dsmhack.controller;

import org.dsmhack.model.ReportOrganization;
import org.dsmhack.service.ReportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class ReportControllerTest {
    private MockMvc mockMvc;
    @InjectMocks
    private ReportController reportController;
    @Mock
    private ReportService reportService;

    @Test
    public void getReportDataAsJsonByOrgIdReturns200() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();
        mockMvc.perform(get("/organizations/1/reports/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void getReportDataAsJsonReturnsReportOrganization() throws Exception {
        String organizationId = "12341235135";
        ReportOrganization expectedReportOrganization = new ReportOrganization();
        when(reportService.getReportDataAsJson(organizationId)).thenReturn(expectedReportOrganization);

        ReportOrganization actualReportOrganization = reportController.getReportDataAsJson(organizationId);

        assertEquals(expectedReportOrganization, actualReportOrganization);
    }

    @Test
    public void getReportDataAsCsvByOrgIdReturns200() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();
        mockMvc.perform(get("/organizations/1/reports/csv"))
                .andExpect(status().isOk());
    }

    @Test
    public void getReportDataAsCsvReturnsReportOrganization() throws Exception {
        String organizationId = "12341235135";
        String expectedReportDataAsCsv = "reportDataAsCsv";
        when(reportService.getReportDataAsCsv(organizationId)).thenReturn(expectedReportDataAsCsv);

        String actualReportDataAsCsv = reportController.getReportDataAsCsv(organizationId);

        assertEquals(expectedReportDataAsCsv, actualReportDataAsCsv);
    }
}

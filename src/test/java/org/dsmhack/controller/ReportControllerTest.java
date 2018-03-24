package org.dsmhack.controller;

import org.dsmhack.model.ReportOrganization;
import org.dsmhack.service.ReportService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class ReportControllerTest {
    private MockMvc mockMvc;
    @InjectMocks
    private ReportController reportController;
    @Mock
    private ReportService reportService;

    @Test
    public void getReportByOrgIdReturns200() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();
        mockMvc.perform(get("/organizations/1/reports"))
                .andExpect(status().isOk());
    }

    @Ignore
    @Test
    public void getReportOrganizationReturnsReportOrganization() throws Exception {
        String organizationId = "12341235135";
        ReportOrganization expectedReportOrganization = new ReportOrganization();
        when(reportService.getReportOrganization(organizationId)).thenReturn(expectedReportOrganization);

        ReportOrganization actualReportOrganization = reportController.getReportOrganization(organizationId);

        assertEquals(expectedReportOrganization, actualReportOrganization);
    }
}

package org.dsmhack.service;

import org.dsmhack.model.ReportData;
import org.dsmhack.model.ReportOrganization;
import org.dsmhack.model.ReportProject;
import org.dsmhack.model.ReportUser;
import org.dsmhack.repository.ReportRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportServiceTest {

    @InjectMocks
    private ReportService reportService;

    @Mock
    private ReportRepository reportRepository;

    @Test
    public void callsReportRepoAndMapsToApiObject_happyPath() throws Exception {
        String organizationId = "12341235135";
        ReportData reportData = new ReportData();
        reportData.setProjectGuid("projectGuid1");
        reportData.setProjectName("Project #1");
        reportData.setUserGuid("someGuid");
        reportData.setFirstName("John");
        reportData.setLastName("Doe");
        reportData.setTimeIn(Timestamp.valueOf("2018-01-01 09:00:00"));
        reportData.setTimeOut(Timestamp.valueOf("2018-01-01 17:00:00"));
        List<ReportData> reportDatas = Arrays.asList(reportData);
        when(reportRepository.findAllReportingInformation(organizationId)).thenReturn(reportDatas);

        ReportOrganization reportOrganization = reportService.getReportOrganization(organizationId);

        ReportProject organizationProject = reportOrganization.getProjects().get(0);
        assertEquals("Project #1", organizationProject.getName());
        assertEquals(8, organizationProject.getTotalHours(), 0.0001);

        ReportUser reportUser = reportOrganization.getUsers().get(0);
        assertEquals("someGuid", reportUser.getUserGuid());
        assertEquals("John", reportUser.getFirstName());
        assertEquals("Doe", reportUser.getLastName());
        ReportProject userProject = reportUser.getProjects().get(0);
        assertEquals("Project #1", userProject.getName());
        assertEquals(8, userProject.getTotalHours(), 0.0001);
        assertEquals(8, reportUser.getTotalHours(), 0.0001);

        assertEquals(8, reportOrganization.getTotalHours(), 0.0001);
    }

    @Test
    public void callsReportRepoAndMapsToApiObject_fractionsOfAnHourShouldStillAddUpToAnHour() throws Exception {
        String organizationId = "12341235135";
        ReportData reportData1 = new ReportData();
        reportData1.setProjectGuid("projectGuid1");
        reportData1.setProjectName("Project #1");
        reportData1.setUserGuid("someGuid");
        reportData1.setFirstName("John");
        reportData1.setLastName("Doe");
        reportData1.setTimeIn(Timestamp.valueOf("2018-01-01 09:00:00"));
        reportData1.setTimeOut(Timestamp.valueOf("2018-01-01 09:20:00"));
        ReportData reportData2 = new ReportData();
        reportData2.setProjectGuid("projectGuid1");
        reportData2.setProjectName("Project #1");
        reportData2.setUserGuid("someGuid");
        reportData2.setFirstName("John");
        reportData2.setLastName("Doe");
        reportData2.setTimeIn(Timestamp.valueOf("2018-01-01 10:00:00"));
        reportData2.setTimeOut(Timestamp.valueOf("2018-01-01 10:20:00"));
        ReportData reportData3 = new ReportData();
        reportData3.setProjectGuid("projectGuid1");
        reportData3.setProjectName("Project #1");
        reportData3.setUserGuid("someGuid");
        reportData3.setFirstName("John");
        reportData3.setLastName("Doe");
        reportData3.setTimeIn(Timestamp.valueOf("2018-01-01 11:00:00"));
        reportData3.setTimeOut(Timestamp.valueOf("2018-01-01 11:20:00"));
        List<ReportData> reportDatas = Arrays.asList(reportData1, reportData2, reportData3);
        when(reportRepository.findAllReportingInformation(organizationId)).thenReturn(reportDatas);

        ReportOrganization reportOrganization = reportService.getReportOrganization(organizationId);

        ReportProject organizationProject = reportOrganization.getProjects().get(0);
        assertEquals("Project #1", organizationProject.getName());
        assertEquals(1, organizationProject.getTotalHours(), 0.0001);

        ReportUser reportUser = reportOrganization.getUsers().get(0);
        assertEquals("someGuid", reportUser.getUserGuid());
        assertEquals("John", reportUser.getFirstName());
        assertEquals("Doe", reportUser.getLastName());
        ReportProject userProject = reportUser.getProjects().get(0);
        assertEquals("Project #1", userProject.getName());
        assertEquals(1, userProject.getTotalHours(), 0.0001);
        assertEquals(1, reportUser.getTotalHours(), 0.0001);

        assertEquals(1, reportOrganization.getTotalHours(), 0.0001);
    }

    @Test
    public void callsReportRepoAndMapsToApiObject_multipleUsersOneProject() throws Exception {
        String organizationId = "12341235135";
        ReportData reportData1 = new ReportData();
        reportData1.setProjectGuid("projectGuid1");
        reportData1.setProjectName("Project #1");
        reportData1.setUserGuid("someGuid1");
        reportData1.setFirstName("John");
        reportData1.setLastName("Doe");
        reportData1.setTimeIn(Timestamp.valueOf("2018-01-01 09:00:00"));
        reportData1.setTimeOut(Timestamp.valueOf("2018-01-01 17:00:00"));
        ReportData reportData2 = new ReportData();
        reportData2.setProjectGuid("projectGuid1");
        reportData2.setProjectName("Project #1");
        reportData2.setUserGuid("someGuid2");
        reportData2.setFirstName("Tim");
        reportData2.setLastName("Smith");
        reportData2.setTimeIn(Timestamp.valueOf("2018-02-02 09:00:00"));
        reportData2.setTimeOut(Timestamp.valueOf("2018-02-02 15:00:00"));
        List<ReportData> reportDatas = Arrays.asList(reportData1, reportData2);
        when(reportRepository.findAllReportingInformation(organizationId)).thenReturn(reportDatas);

        ReportOrganization reportOrganization = reportService.getReportOrganization(organizationId);

        assertEquals(1, reportOrganization.getProjects().size());
        ReportProject organizationProject = reportOrganization.getProjects().get(0);
        assertEquals("Project #1", organizationProject.getName());
        assertEquals(14, organizationProject.getTotalHours(), 0.0001);

        ReportUser reportUser1 = reportOrganization.getUsers().get(0);
        assertEquals("someGuid1", reportUser1.getUserGuid());
        assertEquals("John", reportUser1.getFirstName());
        assertEquals("Doe", reportUser1.getLastName());
        ReportProject userProject1 = reportUser1.getProjects().get(0);
        assertEquals("Project #1", userProject1.getName());
        assertEquals(8, userProject1.getTotalHours(), 0.0001);
        assertEquals(8, reportUser1.getTotalHours(), 0.0001);
        ReportUser reportUser2 = reportOrganization.getUsers().get(1);
        assertEquals("someGuid2", reportUser2.getUserGuid());
        assertEquals("Tim", reportUser2.getFirstName());
        assertEquals("Smith", reportUser2.getLastName());
        ReportProject userProject2 = reportUser2.getProjects().get(0);
        assertEquals("Project #1", userProject2.getName());
        assertEquals(6, userProject2.getTotalHours(), 0.0001);
        assertEquals(6, reportUser2.getTotalHours(), 0.0001);

        assertEquals(14, reportOrganization.getTotalHours(), 0.0001);
    }

    @Test
    public void callsReportRepoAndMapsToApiObject_multipleProjectsOneUser() throws Exception {
        String organizationId = "12341235135";
        ReportData reportData1 = new ReportData();
        reportData1.setProjectGuid("projectGuid1");
        reportData1.setProjectName("Project #1");
        reportData1.setUserGuid("userGuid");
        reportData1.setFirstName("John");
        reportData1.setLastName("Doe");
        reportData1.setTimeIn(Timestamp.valueOf("2018-01-01 09:00:00"));
        reportData1.setTimeOut(Timestamp.valueOf("2018-01-01 17:00:00"));
        ReportData reportData2 = new ReportData();
        reportData2.setProjectGuid("projectGuid2");
        reportData2.setProjectName("Project #2");
        reportData2.setUserGuid("userGuid");
        reportData2.setFirstName("John");
        reportData2.setLastName("Doe");
        reportData2.setTimeIn(Timestamp.valueOf("2018-01-02 09:00:00"));
        reportData2.setTimeOut(Timestamp.valueOf("2018-01-02 15:00:00"));
        List<ReportData> reportDatas = Arrays.asList(reportData1, reportData2);
        when(reportRepository.findAllReportingInformation(organizationId)).thenReturn(reportDatas);

        ReportOrganization reportOrganization = reportService.getReportOrganization(organizationId);

        assertEquals(2, reportOrganization.getProjects().size());
        ReportProject organizationProject1 = reportOrganization.getProjects().get(0);
        assertEquals("Project #1", organizationProject1.getName());
        assertEquals(8, organizationProject1.getTotalHours(), 0.0001);
        ReportProject organizationProject2 = reportOrganization.getProjects().get(1);
        assertEquals("Project #2", organizationProject2.getName());
        assertEquals(6, organizationProject2.getTotalHours(), 0.0001);

        assertEquals(1, reportOrganization.getUsers().size());
        ReportUser reportUser = reportOrganization.getUsers().get(0);
        assertEquals("userGuid", reportUser.getUserGuid());
        assertEquals("John", reportUser.getFirstName());
        assertEquals("Doe", reportUser.getLastName());
        ReportProject userProject1 = reportUser.getProjects().get(0);
        assertEquals("projectGuid1", userProject1.getProjectGuid());
        assertEquals("Project #1", userProject1.getName());
        assertEquals(8, userProject1.getTotalHours(), 0.0001);
        ReportProject userProject2 = reportUser.getProjects().get(1);
        assertEquals("projectGuid2", userProject2.getProjectGuid());
        assertEquals("Project #2", userProject2.getName());
        assertEquals(6, userProject2.getTotalHours(), 0.0001);
        assertEquals(14, reportUser.getTotalHours(), 0.0001);

        assertEquals(14, reportOrganization.getTotalHours(), 0.0001);
    }

    @Test
    public void callsReportRepoAndMapsToApiObject_oneUserOneProjectMultipleDays() throws Exception {
        String organizationId = "12341235135";
        ReportData reportData1 = new ReportData();
        reportData1.setProjectGuid("projectGuid");
        reportData1.setProjectName("Project #1");
        reportData1.setUserGuid("userGuid");
        reportData1.setFirstName("John");
        reportData1.setLastName("Doe");
        reportData1.setTimeIn(Timestamp.valueOf("2018-01-01 09:00:00"));
        reportData1.setTimeOut(Timestamp.valueOf("2018-01-01 17:00:00"));
        ReportData reportData2 = new ReportData();
        reportData2.setProjectGuid("projectGuid");
        reportData2.setProjectName("Project #1");
        reportData2.setUserGuid("userGuid");
        reportData2.setFirstName("John");
        reportData2.setLastName("Smith");
        reportData2.setTimeIn(Timestamp.valueOf("2018-02-02 09:00:00"));
        reportData2.setTimeOut(Timestamp.valueOf("2018-02-02 15:00:00"));
        List<ReportData> reportDatas = Arrays.asList(reportData1, reportData2);
        when(reportRepository.findAllReportingInformation(organizationId)).thenReturn(reportDatas);

        ReportOrganization reportOrganization = reportService.getReportOrganization(organizationId);

        assertEquals(1, reportOrganization.getProjects().size());
        ReportProject organizationProject = reportOrganization.getProjects().get(0);
        assertEquals("Project #1", organizationProject.getName());
        assertEquals(14, organizationProject.getTotalHours(), 0.0001);

        assertEquals(1, reportOrganization.getUsers().size());
        assertEquals(1, reportOrganization.getUsers().get(0).getProjects().size());
        ReportUser reportUser1 = reportOrganization.getUsers().get(0);
        assertEquals("userGuid", reportUser1.getUserGuid());
        assertEquals("John", reportUser1.getFirstName());
        assertEquals("Doe", reportUser1.getLastName());
        ReportProject userProject1 = reportUser1.getProjects().get(0);
        assertEquals("projectGuid", userProject1.getProjectGuid());
        assertEquals("Project #1", userProject1.getName());
        assertEquals(14, userProject1.getTotalHours(), 0.0001);
        assertEquals(14, reportUser1.getTotalHours(), 0.0001);

        assertEquals(14, reportOrganization.getTotalHours(), 0.0001);
    }

    @Ignore
    @Test
    public void callsReportRepoAndMapsToApiObject_twoUsersHaveHoursForOneProjectButOnlyOneUserHasHoursForAnotherProject() throws Exception {
        String organizationId = "12341235135";
        ReportData reportData1 = new ReportData();
        reportData1.setProjectGuid("projectGuid1");
        reportData1.setProjectName("Project #1");
        reportData1.setUserGuid("someGuid1");
        reportData1.setFirstName("John");
        reportData1.setLastName("Doe");
        reportData1.setTimeIn(Timestamp.valueOf("2018-01-01 09:00:00"));
        reportData1.setTimeOut(Timestamp.valueOf("2018-01-01 17:00:00"));
        ReportData reportData2 = new ReportData();
        reportData2.setProjectGuid("projectGuid1");
        reportData2.setProjectName("Project #1");
        reportData2.setUserGuid("someGuid2");
        reportData2.setFirstName("Tim");
        reportData2.setLastName("Smith");
        reportData2.setTimeIn(Timestamp.valueOf("2018-02-02 09:00:00"));
        reportData2.setTimeOut(Timestamp.valueOf("2018-02-02 15:00:00"));
        ReportData reportData3 = new ReportData();
        reportData3.setProjectGuid("projectGuid2");
        reportData3.setProjectName("Project #2");
        reportData3.setUserGuid("someGuid1");
        reportData3.setFirstName("John");
        reportData3.setLastName("Doe");
        reportData3.setTimeIn(Timestamp.valueOf("2018-01-01 09:00:00"));
        reportData3.setTimeOut(Timestamp.valueOf("2018-01-01 17:00:00"));
        List<ReportData> reportDatas = Arrays.asList(reportData1, reportData2, reportData3);
        when(reportRepository.findAllReportingInformation(organizationId)).thenReturn(reportDatas);

        ReportOrganization reportOrganization = reportService.getReportOrganization(organizationId);

        assertEquals(2, reportOrganization.getProjects().size());
        ReportProject organizationProject1 = reportOrganization.getProjects().get(0);
        assertEquals("Project #1", organizationProject1.getName());
        assertEquals(14, organizationProject1.getTotalHours(), 0.0001);
        ReportProject organizationProject2 = reportOrganization.getProjects().get(1);
        assertEquals("Project #2", organizationProject2.getName());
        assertEquals(8, organizationProject2.getTotalHours(), 0.0001);

        ReportUser reportUser1 = reportOrganization.getUsers().get(0);
        assertEquals("someGuid1", reportUser1.getUserGuid());
        assertEquals("John", reportUser1.getFirstName());
        assertEquals("Doe", reportUser1.getLastName());
        ReportProject userProject1 = reportUser1.getProjects().get(0);
        assertEquals("Project #1", userProject1.getName());
        assertEquals(8, userProject1.getTotalHours(), 0.0001);
        ReportProject userProject2 = reportUser1.getProjects().get(1);
        assertEquals("Project #2", userProject2.getName());
        assertEquals(8, userProject2.getTotalHours(), 0.0001);
        assertEquals(16, reportUser1.getTotalHours(), 0.0001);
        ReportUser reportUser2 = reportOrganization.getUsers().get(1);
        assertEquals("someGuid2", reportUser2.getUserGuid());
        assertEquals("Tim", reportUser2.getFirstName());
        assertEquals("Smith", reportUser2.getLastName());
        ReportProject userProject3 = reportUser2.getProjects().get(0);
        assertEquals("Project #1", userProject3.getName());
        assertEquals(6, userProject3.getTotalHours(), 0.0001);
        ReportProject userProject4 = reportUser2.getProjects().get(1);
        assertEquals("Project #2", userProject4.getName());
        assertEquals(0, userProject4.getTotalHours(), 0.0001);
        assertEquals(6, reportUser2.getTotalHours(), 0.0001);

        assertEquals(14, reportOrganization.getTotalHours(), 0.0001);
    }

    @Test
    public void buildsBaseStructureBasedOffUniqueProjectsAndUsers() throws Exception {
        String organizationId = "12341235135";
        ReportData reportData1 = new ReportData();
        reportData1.setProjectGuid("projectGuid1");
        reportData1.setProjectName("Project #1");
        reportData1.setUserGuid("someGuid1");
        reportData1.setFirstName("John");
        reportData1.setLastName("Doe");
        ReportData reportData2 = new ReportData();
        reportData2.setProjectGuid("projectGuid1");
        reportData2.setProjectName("Project #1");
        reportData2.setUserGuid("someGuid2");
        reportData2.setFirstName("Tim");
        reportData2.setLastName("Smith");
        ReportData reportData3 = new ReportData();
        reportData3.setProjectGuid("projectGuid2");
        reportData3.setProjectName("Project #2");
        reportData3.setUserGuid("someGuid1");
        reportData3.setFirstName("John");
        reportData3.setLastName("Doe");
        List<ReportData> reportDatas = Arrays.asList(reportData1, reportData2, reportData3);

        ReportOrganization reportOrganization = reportService.buildReportOrganizationSkeleton(reportDatas);

        assertEquals(2, reportOrganization.getProjects().size());
        ReportProject organizationProject1 = reportOrganization.getProjects().get(0);
        assertEquals("Project #1", organizationProject1.getName());
        assertEquals(0, organizationProject1.getTotalHours(), 0.0001);
        ReportProject organizationProject2 = reportOrganization.getProjects().get(1);
        assertEquals("Project #2", organizationProject2.getName());
        assertEquals(0, organizationProject2.getTotalHours(), 0.0001);

        ReportUser reportUser1 = reportOrganization.getUsers().get(0);
        assertEquals("someGuid1", reportUser1.getUserGuid());
        assertEquals("John", reportUser1.getFirstName());
        assertEquals("Doe", reportUser1.getLastName());
        ReportProject userProject1 = reportUser1.getProjects().get(0);
        assertEquals("Project #1", userProject1.getName());
        assertEquals(0, userProject1.getTotalHours(), 0.0001);
        ReportProject userProject2 = reportUser1.getProjects().get(1);
        assertEquals("Project #2", userProject2.getName());
        assertEquals(0, userProject2.getTotalHours(), 0.0001);
        assertEquals(0, reportUser1.getTotalHours(), 0.0001);
        ReportUser reportUser2 = reportOrganization.getUsers().get(1);
        assertEquals("someGuid2", reportUser2.getUserGuid());
        assertEquals("Tim", reportUser2.getFirstName());
        assertEquals("Smith", reportUser2.getLastName());
        ReportProject userProject3 = reportUser2.getProjects().get(0);
        assertEquals("Project #1", userProject3.getName());
        assertEquals(0, userProject3.getTotalHours(), 0.0001);
        ReportProject userProject4 = reportUser2.getProjects().get(1);
        assertEquals("Project #2", userProject4.getName());
        assertEquals(0, userProject4.getTotalHours(), 0.0001);
        assertEquals(0, reportUser2.getTotalHours(), 0.0001);

        assertEquals(0, reportOrganization.getTotalHours(), 0.0001);
    }

    @Test
    public void findTheProjectGivenTheGuid_exists() {
        ReportProject reportProject = new ReportProject();
        String guid = "someGuid";
        reportProject.setProjectGuid(guid);
        List<ReportProject> reportProjects = Arrays.asList(reportProject);

        ReportProject project = reportService.findReportProject(guid, reportProjects);

        assertEquals(guid, project.getProjectGuid());
    }

    @Test
    public void findTheProjectGivenTheGuid_doesNotExists() {
        String guid = "someGuid";
        List<ReportProject> reportProjects = Arrays.asList();

        ReportProject project = reportService.findReportProject(guid, reportProjects);

        assertNull(project.getProjectGuid());
    }

    @Test
    public void checkIfGuidExistsInProjectList_true() {
        ReportProject reportProject = new ReportProject();
        String guid = "someGuid";
        reportProject.setProjectGuid(guid);
        List<ReportProject> reportProjects = Arrays.asList(reportProject);

        boolean guidExists = reportService.projectGuidExists(guid, reportProjects);

        assertTrue(guidExists);
    }

    @Test
    public void checkIfGuidExistsInProjectList_false() {
        String guid = "someGuid";
        List<ReportProject> reportProjects = new ArrayList<ReportProject>();

        boolean guidExists = reportService.projectGuidExists(guid, reportProjects);

        assertFalse(guidExists);
    }

    @Test
    public void findTheUserGivenTheGuid_exists() {
        ReportUser reportUser = new ReportUser();
        String guid = "someGuid";
        reportUser.setUserGuid(guid);
        List<ReportUser> users = Arrays.asList(reportUser);

        ReportUser user = reportService.findUser(guid, users);

        assertEquals(guid, user.getUserGuid());
    }

    @Test
    public void findTheUserGivenTheGuid_doesNotExists() {
        String guid = "someGuid";
        List<ReportUser> users = Arrays.asList();

        ReportUser user = reportService.findUser(guid, users);

        assertNull(user.getUserGuid());
    }

    @Test
    public void checkIfGuidExistsInUserList_true() {
        ReportUser reportUser = new ReportUser();
        String guid = "someGuid";
        reportUser.setUserGuid(guid);
        List<ReportUser> users = Arrays.asList(reportUser);

        boolean guidExists = reportService.userGuidExists(guid, users);

        assertTrue(guidExists);
    }

    @Test
    public void checkIfGuidExistsInUserList_false() {
        String guid = "someGuid";
        List<ReportUser> users = new ArrayList<ReportUser>();

        boolean guidExists = reportService.userGuidExists(guid, users);

        assertFalse(guidExists);
    }

    @Test
    public void hoursBasedOnCheckInCheckOut_happyPath() throws Exception {
        Timestamp checkIn = Timestamp.valueOf("2018-01-01 09:00:00");
        Timestamp checkOut = Timestamp.valueOf("2018-01-01 16:30:00");

        double diffInHours = reportService.calculateHours(checkIn, checkOut);

        assertEquals(7.5, diffInHours, 0.0001);
    }

    //todo: If checkOut is null, we cannot calculate hours, so return 0. We could potientially assume they're in the middle of volunteering and use now as the end. Need to ask the business.
    @Test
    public void hoursBasedOnCheckInCheckOut_checkOutNull() throws Exception {
        Timestamp checkIn = Timestamp.valueOf("2018-01-01 09:00:00");
        Timestamp checkOut = null;

        double diffInHours = reportService.calculateHours(checkIn, checkOut);

        assertEquals(0, diffInHours, 0.0001);
    }

}
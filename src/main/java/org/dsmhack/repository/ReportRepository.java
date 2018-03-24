package org.dsmhack.repository;

import org.dsmhack.model.CheckIn;
import org.dsmhack.model.ReportData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<CheckIn, Long> {
    @Query("select project.proj_guid as projectGuid, project.name as projectName, user.user_guid as userGuid, user.first_name as firstName, user.last_name as lastName, check_in.time_in as timeIn, check_in.time_out as timeOut " +
            "from organization, project, check_in, user " +
            "where organization.org_guid = project.org_guid " +
            "and project.proj_guid = check_in.proj_guid " +
            "and check_in.user_guid = user.user_guid " +
            "and organization.org_guid = ?1")
    List<ReportData> findAllReportingInformation(String organizationId);
}
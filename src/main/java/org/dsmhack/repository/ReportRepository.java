package org.dsmhack.repository;

import org.dsmhack.model.ReportData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ReportRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ReportData> findAllReportingInformation(String organizationId) {
        String sql =
            "select project.project_guid as projectGuid, project.name as projectName, user.user_guid as userGuid, user.first_name as firstName, user.last_name as lastName, check_in.time_in as timeIn, check_in.time_out as timeOut " +
            "from organization, project, check_in, user " +
            "where organization.organization_guid = project.organization_guid " +
            "and project.project_guid = check_in.project_guid " +
            "and check_in.user_guid = user.user_guid " +
            "and organization.organization_guid = ?";
        return jdbcTemplate.query(sql, new Object[]{organizationId}, new BeanPropertyRowMapper(ReportData.class));
    }
}
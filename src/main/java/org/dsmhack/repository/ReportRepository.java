package org.dsmhack.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ReportRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public java.util.List<Map<String, Object>> findAllReportingInformation(String organizationId) {
        String sql =
            "select project.name, user.first_name, user.last_name, check_in.time_in, check_in.time_out " +
            "from organization, project, check_in, user " +
            "where organization.org_guid = project.org_guid " +
            "and project.proj_guid = check_in.proj_guid " +
            "and check_in.user_guid = user.user_guid " +
            "and organization.org_guid = ?";
        return jdbcTemplate.queryForList(sql, organizationId);
    }
}

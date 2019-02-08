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
    String sql = "select "
        + "project.proj_guid as projectGuid, "
        + "project.name as projectName, "
        + "user.user_guid as userGuid, "
        + "user.first_name as firstName, "
        + "user.last_name as lastName, "
        + "check_in.time_in as timeIn, "
        + "check_in.time_out as timeOut "
        + "from organization, project, check_in, user "
        + "where organization.org_guid = project.org_guid "
        + "and project.proj_guid = check_in.proj_guid "
        + "and check_in.user_guid = user.user_guid "
        + "and organization.org_guid = ?";

    return jdbcTemplate.query(
        sql, new Object[]{organizationId}, new BeanPropertyRowMapper(ReportData.class));
  }
}
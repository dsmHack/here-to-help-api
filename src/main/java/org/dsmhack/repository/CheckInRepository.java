package org.dsmhack.repository;

import org.dsmhack.model.CheckIn;
import org.dsmhack.model.Project;
import org.hibernate.annotations.Check;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {

    List<CheckIn> findByProjGuid(String projectGuid);
}
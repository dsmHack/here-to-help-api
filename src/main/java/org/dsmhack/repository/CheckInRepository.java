package org.dsmhack.repository;

import org.dsmhack.model.CheckIn;
import org.dsmhack.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
}

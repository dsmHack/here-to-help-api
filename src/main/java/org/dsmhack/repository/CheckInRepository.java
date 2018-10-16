package org.dsmhack.repository;

import org.dsmhack.model.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {

    List<CheckIn> findByProjGuid(String projectGuid);

    CheckIn findByProjGuidAndUserGuid(String projectGuid, String userGuid);

    List<CheckIn> findByUserGuid(String userGuid);

    @Query("select c from CheckIn c where c.userGuid = :userGuid and c.timeOut is null")
    List<CheckIn> findActiveByUserGuid(@Param("userGuid") String userGuid);
}
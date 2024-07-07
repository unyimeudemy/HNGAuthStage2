package com.HNG.userAuthStage2.repository;


import com.HNG.userAuthStage2.domain.entities.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, String> {

    @Query("SELECT o FROM OrganizationEntity o JOIN o.users u WHERE u.userId = :userId")
    List<OrganizationEntity> findAllByUserId(@Param("userId") String userId);

    Optional<OrganizationEntity> findByOrgId(String orgId);
}

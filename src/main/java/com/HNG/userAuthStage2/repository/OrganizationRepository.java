package com.HNG.userAuthStage2.repository;


import com.HNG.userAuthStage2.domain.entities.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, String> {

}

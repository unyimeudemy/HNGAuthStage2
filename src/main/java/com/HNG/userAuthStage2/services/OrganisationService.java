package com.HNG.userAuthStage2.services;


import com.HNG.userAuthStage2.domain.entities.OrganizationEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface OrganisationService {

    Optional<?> getAllUserOrg(String userId);

    Optional<?> getOrg(String orgId);

    Optional<?> createOrg(String userId, OrganizationEntity organizationEntity);

    Optional<?> addUserToOrg(String userId, String orgId);
}

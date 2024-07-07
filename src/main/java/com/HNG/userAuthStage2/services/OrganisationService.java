package com.HNG.userAuthStage2.services;


import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface OrganisationService {

    Optional<?> getAllUserOrg(String userId);

    Optional<?> getOrg(String orgId);
}

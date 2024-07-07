package com.HNG.userAuthStage2.services.impl;

import com.HNG.userAuthStage2.domain.dtos.ErrorResponseDto;
import com.HNG.userAuthStage2.domain.dtos.OrganisationResponseDto;
import com.HNG.userAuthStage2.domain.dtos.OrganizationDto;
import com.HNG.userAuthStage2.domain.dtos.UserOrganisationResponseDto;
import com.HNG.userAuthStage2.domain.entities.OrganizationEntity;
import com.HNG.userAuthStage2.repository.OrganizationRepository;
import com.HNG.userAuthStage2.services.OrganisationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganisationServiceImpl implements OrganisationService {

    private final OrganizationRepository organizationRepository;

    public OrganisationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Optional<?> getAllUserOrg(String userId) {
        try {
            List<OrganizationEntity> userOrgsList = organizationRepository.findAllByUserId(userId);

            List<OrganizationDto> organizationDtos = userOrgsList.stream()
                    .map(org -> OrganizationDto.builder()
                            .orgId(org.getOrgId())
                            .name(org.getName())
                            .description(org.getDescription())
                            .build())
                    .toList();

            return Optional.of(
                    UserOrganisationResponseDto.builder()
                            .status("success")
                            .message("organisations found")
                            .data(UserOrganisationResponseDto.DataDto.builder()
                                    .organizations(organizationDtos)
                                    .build())
                            .build());
        }catch (Exception e){
            System.out.println("======= Exception =======" + e.getMessage());
            return Optional.of(ErrorResponseDto.builder()
                    .status("Not found")
                    .message("User's organisations not found")
                    .statusCode(404)
                    .build());
        }
    }

    @Override
    public Optional<?> getOrg(String orgId) {
        try{
            Optional<OrganizationEntity> org = organizationRepository.findByOrgId(orgId);
            return Optional.of(OrganisationResponseDto.builder()
                    .status("success")
                    .message("Organization found")
                    .data(OrganisationResponseDto.DataDto.builder()
                            .orgId(org.get().getOrgId())
                            .name(org.get().getName())
                            .description(org.get().getDescription())
                            .build())
                    .build());

        }catch (Exception e){
            System.out.println("======= Exception =======" + e.getMessage());
            return Optional.of(ErrorResponseDto.builder()
                    .status("Not found")
                    .message("User's organisations not found")
                    .statusCode(404)
                    .build());
        }
    }
}

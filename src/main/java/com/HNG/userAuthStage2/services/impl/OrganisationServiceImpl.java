package com.HNG.userAuthStage2.services.impl;

import com.HNG.userAuthStage2.domain.dtos.*;
import com.HNG.userAuthStage2.domain.entities.OrganizationEntity;
import com.HNG.userAuthStage2.domain.entities.UserEntity;
import com.HNG.userAuthStage2.repository.OrganizationRepository;
import com.HNG.userAuthStage2.repository.UserRepository;
import com.HNG.userAuthStage2.services.OrganisationService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class OrganisationServiceImpl implements OrganisationService {

    private final OrganizationRepository organizationRepository;

    private final UserRepository userRepository;

    public OrganisationServiceImpl(OrganizationRepository organizationRepository, UserRepository userRepository) {
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
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

    @Override
    public Optional<?> createOrg(String userId, OrganizationEntity organizationEntity) {
        try{

            Optional<UserEntity> user = userRepository.findById(userId);

            var organization = OrganizationEntity.builder()
                    .name(organizationEntity.getName())
                    .description(organizationEntity.getDescription())
                    .users(new HashSet<>())
                    .build();

            organization.getUsers().add(user.get());
            user.get().getOrganizations().add(organization);


            OrganizationEntity org = organizationRepository.save(organization);
            return Optional.of(OrganisationResponseDto.builder()
                    .status("success")
                    .message("Organisation created successfully")
                    .data(OrganisationResponseDto.DataDto.builder()
                            .orgId(org.getOrgId())
                            .name(org.getName())
                            .description(org.getDescription())
                            .build())
                    .build());

        }catch (Exception e){
            System.out.println("======= Exception =======" + e.getMessage());
            return Optional.of(ErrorResponseDto.builder()
                    .status("Bad Request")
                    .message("Client error")
                    .statusCode(400)
                    .build());
        }
    }

    @Override
    public Optional<?> addUserToOrg(String userId, String orgId) {

        try {
            Optional<UserEntity> user = userRepository.findById(userId);
            if (user.isEmpty()) {
                return Optional.of(ErrorResponseDto.builder()
                        .status("Bad Request")
                        .message("User not found")
                        .statusCode(404)
                        .build());
            }

            Optional<OrganizationEntity> org = organizationRepository.findByOrgId(orgId);
            if (org.isEmpty()) {
                return Optional.of(ErrorResponseDto.builder()
                        .status("Bad Request")
                        .message("Organisation not found")
                        .statusCode(404)
                        .build());
            }

            org.get().getUsers().add(user.get());
            user.get().getOrganizations().add(org.get());

            organizationRepository.save(org.get());
            userRepository.save(user.get());

            return Optional.of(AddUserToOrgResponseDto.builder()
                    .status("success")
                    .message("User added to organisation successfully")
                    .build());
        }catch (Exception e){
            System.out.println("======= Exception =======" + e.getMessage());
            return Optional.of(ErrorResponseDto.builder()
                    .status("Bad Request")
                    .message("User not successfully added to organisation")
                    .statusCode(400)
                    .build());
        }
    }

}

package com.HNG.userAuthStage2.controllers;


import com.HNG.userAuthStage2.domain.dtos.*;
import com.HNG.userAuthStage2.domain.entities.OrganizationEntity;
import com.HNG.userAuthStage2.domain.entities.UserEntity;
import com.HNG.userAuthStage2.mappers.Mapper;
import com.HNG.userAuthStage2.services.OrganisationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/organisations")
public class OrganizationController {

    private final OrganisationService organisationService;

    private final Mapper<OrganizationEntity, OrganizationDto> organizationMapper;

    public OrganizationController(OrganisationService organisationService, Mapper<OrganizationEntity, OrganizationDto> organizationMapper) {
        this.organisationService = organisationService;
        this.organizationMapper = organizationMapper;
    }


    @GetMapping()
    public ResponseEntity<?> getAllUsersOrgs(
            Authentication authentication
    ){
        UserEntity user = (UserEntity) authentication.getPrincipal();
        String userId = user.getUserId();

        Optional<?> userOrgs = organisationService.getAllUserOrg(userId);
        if (userOrgs.isPresent()) {
            Object responseObject = userOrgs.get();

            if (responseObject instanceof UserOrganisationResponseDto userOrgsResponse) {
                return new ResponseEntity<>(userOrgsResponse, HttpStatus.OK);
            } else {
                ErrorResponseDto error = (ErrorResponseDto) responseObject;
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
        } else {
            ErrorResponseDto error = ErrorResponseDto.builder()
                    .status("Not found")
                    .message("User's organisations not found")
                    .statusCode(404)
                    .build();
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/{orgId}")
    public ResponseEntity<?> getOrg(@PathVariable String orgId){
        Optional<?> org = organisationService.getOrg(orgId);

        if (org.isPresent()) {
            Object responseObject = org.get();

            if (responseObject instanceof OrganisationResponseDto orgResponse) {
                return new ResponseEntity<>(orgResponse, HttpStatus.OK);
            } else {
                ErrorResponseDto error = (ErrorResponseDto) responseObject;
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
        } else {
            ErrorResponseDto error = ErrorResponseDto.builder()
                    .status("Not found")
                    .message("organisation not found")
                    .statusCode(404)
                    .build();
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping()
    public ResponseEntity<?> createOrg(
            @RequestBody OrganizationDto organizationDto,
            Authentication authentication

    ){
        UserEntity user = (UserEntity) authentication.getPrincipal();
        String userId = user.getUserId();

        OrganizationEntity organizationEntity = organizationMapper.mapFrom(organizationDto);
        Optional<?> org = organisationService.createOrg(userId, organizationEntity);

        if (org.isPresent()) {
            Object responseObject = org.get();

            if (responseObject instanceof OrganisationResponseDto orgResponse) {
                return new ResponseEntity<>(orgResponse, HttpStatus.OK);
            } else {
                ErrorResponseDto error = (ErrorResponseDto) responseObject;
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
        } else {
            ErrorResponseDto error = ErrorResponseDto.builder()
                    .status("Bad Request")
                    .message("Client error")
                    .statusCode(400)
                    .build();
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping(path = "/{orgId}/users")
    public ResponseEntity<?> addUserToOrg(
            @RequestBody UserDto userDto,
            @PathVariable String orgId
    ){
        String userId = userDto.getUserId();

        Optional<?> addUserToOrgRes = organisationService.addUserToOrg(userId, orgId);
        if (addUserToOrgRes.isPresent()) {
            Object responseObject = addUserToOrgRes.get();

            if (responseObject instanceof AddUserToOrgResponseDto response) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                ErrorResponseDto error = (ErrorResponseDto) responseObject;
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }
        } else {
            ErrorResponseDto error = ErrorResponseDto.builder()
                    .status("Bad Request")
                    .message("User not successfully added to organisation")
                    .statusCode(400)
                    .build();
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

    }
}

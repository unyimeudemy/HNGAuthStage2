package com.HNG.userAuthStage2.controllers;


import com.HNG.userAuthStage2.domain.dtos.ErrorResponseDto;
import com.HNG.userAuthStage2.domain.dtos.OrganisationResponseDto;
import com.HNG.userAuthStage2.domain.dtos.UserOrganisationResponseDto;
import com.HNG.userAuthStage2.domain.dtos.UserProfileDto;
import com.HNG.userAuthStage2.domain.entities.UserEntity;
import com.HNG.userAuthStage2.services.OrganisationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/organisations")
public class OrganizationController {

    private final OrganisationService organisationService;

    public OrganizationController(OrganisationService organisationService) {
        this.organisationService = organisationService;
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

    @GetMapping(path = "{orgId}")
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
}

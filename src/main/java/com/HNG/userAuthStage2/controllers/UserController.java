package com.HNG.userAuthStage2.controllers;

import com.HNG.userAuthStage2.domain.dtos.ErrorResponseDto;
import com.HNG.userAuthStage2.domain.dtos.UserProfileDto;
import com.HNG.userAuthStage2.domain.entities.UserEntity;
import com.HNG.userAuthStage2.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id){
        Optional<?> user = userService.findByUserId(id);

        if (user.isPresent()) {
            Object responseObject = user.get();

            if (responseObject instanceof UserProfileDto) {
                UserProfileDto userProfile = (UserProfileDto) responseObject;
                return new ResponseEntity<>(userProfile, HttpStatus.OK);
            } else {
                ErrorResponseDto error = (ErrorResponseDto) responseObject;
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
        } else {
            ErrorResponseDto error = ErrorResponseDto.builder()
                    .status("Bad request")
                    .message("User profile not found")
                    .statusCode(400)
                    .build();
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(path = "/users/test")
    public String getUser(){
        return "test";
    }


}

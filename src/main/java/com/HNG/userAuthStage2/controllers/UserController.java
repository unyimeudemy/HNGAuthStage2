package com.HNG.userAuthStage2.controllers;


import com.HNG.userAuthStage2.domain.dtos.ErrorResponseDto;
import com.HNG.userAuthStage2.domain.dtos.AuthResponseDto;
import com.HNG.userAuthStage2.domain.dtos.UserDto;
import com.HNG.userAuthStage2.domain.entities.UserEntity;
import com.HNG.userAuthStage2.mappers.Mapper;
import com.HNG.userAuthStage2.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final Mapper<UserEntity, UserDto> usermapper;

    private final UserService userService;

    public UserController(Mapper<UserEntity, UserDto> usermapper, UserService userService) {
        this.usermapper = usermapper;
        this.userService = userService;
    }


    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto){
        UserEntity userEntity = usermapper.mapFrom(userDto);

        AuthResponseDto signedUpUser = userService.register(userEntity);

        if(signedUpUser.getData().getAccessToken().equals("Email already exist")){
            ErrorResponseDto error = ErrorResponseDto.builder()
                    .status("")
                    .message("")
                    .statusCode(1)
                    .build();
            return new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }else {
            return new ResponseEntity<>(signedUpUser, HttpStatus.CREATED);
        }


    }
}

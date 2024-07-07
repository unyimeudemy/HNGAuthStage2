package com.HNG.userAuthStage2.controllers;


import com.HNG.userAuthStage2.domain.dtos.ErrorResponseDto;
import com.HNG.userAuthStage2.domain.dtos.FieldValidationError;
import com.HNG.userAuthStage2.domain.dtos.LoginRequestDto;
import com.HNG.userAuthStage2.domain.dtos.UserDto;
import com.HNG.userAuthStage2.domain.entities.UserEntity;
import com.HNG.userAuthStage2.mappers.Mapper;
import com.HNG.userAuthStage2.services.UserService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final Mapper<UserEntity, UserDto> usermapper;

    private final UserService userService;

    public AuthController(Mapper<UserEntity, UserDto> usermapper, UserService userService) {
        this.usermapper = usermapper;
        this.userService = userService;
    }


    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            List<FieldValidationError.FieldErrorDto> errors = bindingResult.getFieldErrors().stream()
                    .map(fieldError -> FieldValidationError.FieldErrorDto.builder()
                            .field(fieldError.getField())
                            .message(fieldError.getDefaultMessage())
                            .build())
                    .collect(Collectors.toList());

            FieldValidationError error = FieldValidationError.builder()
                    .errors(errors)
                    .build();

            System.out.println("==============================="+errors);

            return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
        }


        UserEntity userEntity = usermapper.mapFrom(userDto);
        if(userService.findByEmail(userEntity.getEmail()).isPresent()){

            ErrorResponseDto error = ErrorResponseDto.builder()
                    .status("Bad request")
                    .message("Registration unsuccessful")
                    .statusCode(400)
                    .build();
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Optional<?> signedUpUser = userService.register(userEntity);

        if(signedUpUser.isEmpty()){

            ErrorResponseDto error = ErrorResponseDto.builder()
                    .status("Bad request")
                    .message("Registration unsuccessful")
                    .statusCode(400)
                    .build();
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }else{
                return new ResponseEntity<>(signedUpUser, HttpStatus.CREATED);
        }
    }


    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto){
        Optional<?> signedInUser = userService.login(loginRequestDto);
        return  new ResponseEntity<>(signedInUser, HttpStatus.OK);
    }

}

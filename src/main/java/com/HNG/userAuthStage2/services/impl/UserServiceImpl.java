package com.HNG.userAuthStage2.services.impl;

import com.HNG.userAuthStage2.domain.dtos.AuthResponseDto;
import com.HNG.userAuthStage2.domain.dtos.ErrorResponseDto;
import com.HNG.userAuthStage2.domain.entities.UserEntity;
import com.HNG.userAuthStage2.repository.UserRepository;
import com.HNG.userAuthStage2.services.JwtServiceImpl;
import com.HNG.userAuthStage2.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtServiceimpl;
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, JwtServiceImpl jwtServiceimpl, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtServiceimpl = jwtServiceimpl;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @Override
    public AuthResponseDto register(UserEntity userEntity) {
        var user = UserEntity.builder()
                .userId(userEntity.getUserId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .password(passwordEncoder.encode(userEntity.getPassword()))
                .phone(userEntity.getPhone())
                .build();

        if(userRepository.findByEmail(userEntity.getEmail()).isPresent()){
//            return ErrorResponseDto.builder()
//                    .status("")
//                    .message("")
//                    .statusCode(1)
//                    .build() ;
        }else {
            userRepository.save(user);
            var jwtToken = jwtServiceimpl.generateToken(user);
            return AuthResponseDto.builder()
                    .status("success")
                    .message("Registration successful")
                    .data(AuthResponseDto.Data.builder()
                            .accessToken("eyJh...")
                            .user(AuthResponseDto.Data.User.builder()
                                    .userId("string")
                                    .firstName("string")
                                    .lastName("string")
                                    .email("string")
                                    .phone("string")
                                    .build())
                            .build())
                    .build();
        }
    }
}



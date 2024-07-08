package com.HNG.userAuthStage2.services.impl;

import com.HNG.userAuthStage2.domain.dtos.AuthResponseDto;
import com.HNG.userAuthStage2.domain.dtos.ErrorResponseDto;
import com.HNG.userAuthStage2.domain.dtos.LoginRequestDto;
import com.HNG.userAuthStage2.domain.dtos.UserProfileDto;
import com.HNG.userAuthStage2.domain.entities.OrganizationEntity;
import com.HNG.userAuthStage2.domain.entities.UserEntity;
import com.HNG.userAuthStage2.repository.OrganizationRepository;
import com.HNG.userAuthStage2.repository.UserRepository;
import com.HNG.userAuthStage2.services.JwtServiceImpl;
//import org.apache.el.stream.Optional;
import com.HNG.userAuthStage2.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtServiceimpl;
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final OrganizationRepository organizationRepository;

    public UserServiceImpl(
            PasswordEncoder passwordEncoder,
            JwtServiceImpl jwtServiceimpl,
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            OrganizationRepository organizationRepository
    ) {
        this.passwordEncoder = passwordEncoder;
        this.jwtServiceimpl = jwtServiceimpl;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Optional<?> register(UserEntity userEntity) {
        var user = UserEntity.builder()
                .userId(userEntity.getUserId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .password(passwordEncoder.encode(userEntity.getPassword()))
                .phone(userEntity.getPhone())
                .organizations(new HashSet<>())
                .build();

        try {

            // Create organization
            String orgName = userEntity.getFirstName() + "'s Organisation";
            OrganizationEntity organization = OrganizationEntity.builder()
                    .name(orgName)
                    .users(new HashSet<>())
                    .build();

            // Establish relationship
            user.getOrganizations().add(organization);
            organization.getUsers().add(user);

//            organizationRepository.save(organization);


            UserEntity savedUser = userRepository.save(user);
            var jwtToken = jwtServiceimpl.generateToken(user);

                return Optional.of(
                        AuthResponseDto.builder()
                                .status("success")
                                .message("Registration successful")
                                .data(AuthResponseDto.Data.builder()
                                        .accessToken(jwtToken)
                                        .user(AuthResponseDto.Data.User.builder()
                                                .userId(savedUser.getUserId())
                                                .firstName(savedUser.getFirstName())
                                                .lastName(savedUser.getLastName())
                                                .email(savedUser.getEmail())
                                                .phone(savedUser.getPhone())
                                                .build())
                                        .build())
                                .build()
                );


        } catch (DataAccessException e) {
            System.out.println("======== auth exception ============" + e.getMessage());
            return Optional.of(ErrorResponseDto.builder()
                    .status("Bad request")
                    .message("Registration unsuccessful")
                    .statusCode(400)
                    .build());
        }
    }

    @Override
    public Optional<?> findByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (DataAccessException e) {

            return Optional.of(ErrorResponseDto.builder()
                    .status("Bad request")
                    .message("Registration unsuccessful")
                    .statusCode(400)
                    .build());
        }
    }

    @Override
    public Optional<?> login(LoginRequestDto loginRequestDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getEmail(),
                            loginRequestDto.getPassword()
                    )
            );

            var user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow();

            String jwtToken = jwtServiceimpl.generateToken((UserDetails) user);
            UserEntity castedUser = (UserEntity) user;

            return Optional.of(
                    AuthResponseDto.builder()
                            .status("success")
                            .message("Registration successful")
                            .data(AuthResponseDto.Data.builder()
                                    .accessToken(jwtToken)
                                    .user(AuthResponseDto.Data.User.builder()
                                            .userId(castedUser.getUserId())
                                            .firstName(castedUser.getFirstName())
                                            .lastName(castedUser.getLastName())
                                            .email(castedUser.getEmail())
                                            .phone(castedUser.getPhone())
                                            .build())
                                    .build())
                            .build()
            );

        }catch (Exception e){
            System.out.println("===========================>" + e.getMessage());
            return Optional.of(ErrorResponseDto.builder()
                    .status("Bad request")
                    .message("Authentication failed")
                    .statusCode(401)
                    .build());
        }
    }

    @Override
    public Optional<?> findByUserId(String userId) {
        try{
            var user = userRepository.findByUserId(userId);
            return Optional.of(
                    UserProfileDto.builder()
                            .status("success")
                            .message("user profile found")
                            .data(UserProfileDto.Data.builder()
                                    .userId(userId)
                                    .lastName(user.get().getLastName())
                                    .email(user.get().getEmail())
                                    .phone(user.get().getPhone())
                                    .build())
                            .build()
            );
        }catch (Exception e){
            System.out.println("==========================" + e.getMessage());
            return Optional.of(ErrorResponseDto.builder()
                    .status("Not found")
                    .message("User not found")
                    .statusCode(404)
                    .build());
        }
    }


}



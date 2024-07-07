package com.HNG.userAuthStage2.services;

import com.HNG.userAuthStage2.domain.dtos.LoginRequestDto;
import com.HNG.userAuthStage2.domain.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public interface UserService {

    Optional<?> register(UserEntity userEntity);

    Optional<?> findByEmail(String email);

    Optional<?> login(LoginRequestDto loginRequestDto);

    Optional<?> findByUserId(String userId);

}
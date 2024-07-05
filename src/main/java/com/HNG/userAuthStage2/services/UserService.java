package com.HNG.userAuthStage2.services;

import com.HNG.userAuthStage2.domain.dtos.AuthResponseDto;
import com.HNG.userAuthStage2.domain.entities.UserEntity;

public interface UserService {

    AuthResponseDto register(UserEntity userEntity);
}

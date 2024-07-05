package com.HNG.userAuthStage2.mappers.impl;

import com.HNG.userAuthStage2.domain.dtos.UserDto;
import com.HNG.userAuthStage2.domain.entities.UserEntity;
import com.HNG.userAuthStage2.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserMapperImpl implements Mapper<UserEntity, UserDto> {
    private final ModelMapper modelMapper;

    @Autowired
    public UserMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public UserDto mapTo(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserEntity mapFrom(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }
}

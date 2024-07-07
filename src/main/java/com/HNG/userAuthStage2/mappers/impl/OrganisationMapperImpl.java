package com.HNG.userAuthStage2.mappers.impl;


import com.HNG.userAuthStage2.domain.dtos.OrganizationDto;
import com.HNG.userAuthStage2.domain.entities.OrganizationEntity;
import com.HNG.userAuthStage2.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrganisationMapperImpl implements Mapper<OrganizationEntity, OrganizationDto> {

    private final ModelMapper modelMapper;

    public OrganisationMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public OrganizationDto mapTo(OrganizationEntity organizationEntity) {
        return modelMapper.map(organizationEntity, OrganizationDto.class);
    }

    @Override
    public OrganizationEntity mapFrom(OrganizationDto organizationDto) {
        return modelMapper.map(organizationDto, OrganizationEntity.class);
    }
}

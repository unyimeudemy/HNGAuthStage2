package com.HNG.userAuthStage2.domain.dtos;


import com.HNG.userAuthStage2.domain.entities.OrganizationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrganisationResponseDto {
    private String status;
    private String message;
    private DataDto data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataDto {
        private List<OrganizationDto> organizations;
    }
}

package com.HNG.userAuthStage2.domain.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganisationResponseDto {
    private String status;
    private String message;
    private DataDto data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DataDto {
        private String orgId;
        private String name;
        private String description;
    }
}

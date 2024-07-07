package com.HNG.userAuthStage2.domain.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldValidationError {
    private List<FieldErrorDto> errors;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class FieldErrorDto {
        private String field;
        private String message;
    }
}



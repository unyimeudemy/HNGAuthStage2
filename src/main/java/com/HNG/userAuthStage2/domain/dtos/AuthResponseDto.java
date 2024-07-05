package com.HNG.userAuthStage2.domain.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseDto {

    private String status;
    private String message;
    private Data data;

    @lombok.Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Data {

        private String accessToken;
        private User user;

        @lombok.Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class User {

            private String userId;
            private String firstName;
            private String lastName;
            private String email;
            private String phone;
        }
    }
}

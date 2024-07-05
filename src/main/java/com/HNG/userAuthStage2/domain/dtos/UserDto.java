package com.HNG.userAuthStage2.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    @NotNull(message = "User ID must not be null")
    @NotEmpty(message = "User ID must not be empty")
    private String userId;

    @NotNull(message = "First name must not be null")
    @NotEmpty(message = "First name must not be empty")
    private String firstName;

    @NotNull(message = "Last name must not be null")
    @NotEmpty(message = "Last name must not be empty")
    private String lastName;

    @NotNull(message = "Email must not be null")
    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Password must not be null")
    @NotEmpty(message = "Password must not be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    private String phone;

}


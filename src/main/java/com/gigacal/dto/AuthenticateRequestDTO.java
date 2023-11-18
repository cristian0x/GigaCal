package com.gigacal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateRequestDTO {

    @Email(message = "Email invalid")
    private String email;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{8,20}$", message = "Password invalid")
    private String password;
}

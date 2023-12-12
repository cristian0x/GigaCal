package com.gigacal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

    @Email(message = "Email invalid")
    private String email;

    @Size(min = 8, message = "Username too short")
    private String username;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{8,20}$", message = "Password invalid")
    private String password;
}

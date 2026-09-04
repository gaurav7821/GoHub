package com.gohub.user.dto;

import com.gohub.user.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid mail")
    private String email;

    @NotBlank(message = "Name is  required")
    @Size(min = 2, max = 50, message = "Name must be between 2 to 50 characters")
    private String name;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 to 100 characters")
    private String password;

    @NotNull(message = "Gender is required")
    private Gender gender;

}

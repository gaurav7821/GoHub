package com.gohub.user.dto;

import com.gohub.user.enums.Gender;
import com.gohub.user.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String email;
    private String name;
    private Role role;
    private Gender gender;
    private String profilepic;
}

package com.gohub.user.entity;

import com.gohub.user.enums.Gender;
import com.gohub.user.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="users")
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @NotBlank(message = "Email is required")
    @Email(message = "Please Enter your valid mail")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    @Size(min = 2,max = 50, message = "Name must be between 2 to 50 characters")
    private String name;

    @NotNull(message = "Role is required")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 to 20 characters")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "Gender is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(name = "profile_pic")
    private String profilepic;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;


}

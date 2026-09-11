package com.gohub.user.controller;

import com.gohub.user.dto.UserResponse;
import com.gohub.user.dto.UserUpdateRequest;
import com.gohub.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication){

        String email = authentication.getName();

        UserResponse response = userService.getUserProfile(email);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateProfile(
            @Valid @RequestBody UserUpdateRequest updateRequest,
            Authentication authentication){

        String email = authentication.getName();

        UserResponse updateUser = userService.updateUser(email, updateRequest);

        return ResponseEntity.ok(updateUser);
    }
}

package com.gohub.user.controller;

import com.gohub.user.dto.UserRequest;
import com.gohub.user.dto.UserResponse;
import com.gohub.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/test")
        public String greet(){
            return "hello";
        }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid
                                                 @RequestBody UserRequest request){
        UserResponse response = userService.userRegister(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}

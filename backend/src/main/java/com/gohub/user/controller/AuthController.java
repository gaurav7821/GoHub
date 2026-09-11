package com.gohub.user.controller;

import com.gohub.security.service.JwtService;
import com.gohub.user.dto.LoginRequest;
import com.gohub.user.dto.LoginResponse;
import com.gohub.user.dto.UserRequest;
import com.gohub.user.dto.UserResponse;
import com.gohub.user.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserServiceImpl userServiceImpl;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(
            UserServiceImpl userServiceImpl,
            JwtService jwtService,
            AuthenticationManager authenticationManager){
        this.userServiceImpl = userServiceImpl;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid
                                                 @RequestBody UserRequest request){
        UserResponse response = userServiceImpl.userRegister(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        String token =
                jwtService.generateToken(
                        authentication.getName()
                );

        return ResponseEntity.ok(
                new LoginResponse(token, "Bearer")
        );
    }
}

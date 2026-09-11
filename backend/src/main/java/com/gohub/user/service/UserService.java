package com.gohub.user.service;

import com.gohub.user.dto.UserRequest;
import com.gohub.user.dto.UserResponse;
import com.gohub.user.dto.UserUpdateRequest;

public interface UserService {

    UserResponse userRegister(UserRequest request);
    UserResponse getUserProfile(String email);
    UserResponse updateUser(String email, UserUpdateRequest updateRequest);

}

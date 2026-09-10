package com.gohub.user.service;

import com.gohub.exception.DuplicateResourceException;
import com.gohub.exception.ResourceNotFoundException;
import com.gohub.user.dto.UserRequest;
import com.gohub.user.dto.UserResponse;
import com.gohub.user.entity.User;
import com.gohub.user.enums.Role;
import com.gohub.user.repository.UserRepository;
import com.gohub.user.utility.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService( UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        UserMapper userMapper){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserResponse userRegister(UserRequest request){

        if(userRepository.existsByEmail(request.getEmail())){
            throw new DuplicateResourceException(
                    "User already registered with email: " + request.getEmail()
            );
        }

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);

        return userMapper.toResponce(savedUser);
    }

    public UserResponse getUserProfile(String email){

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "User not found with Email: " + email
                ));

        return userMapper.toResponce(user);
    }


}

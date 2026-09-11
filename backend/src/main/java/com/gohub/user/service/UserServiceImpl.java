package com.gohub.user.service;

import com.gohub.exception.DuplicateResourceException;
import com.gohub.exception.ResourceNotFoundException;
import com.gohub.user.dto.UserRequest;
import com.gohub.user.dto.UserResponse;
import com.gohub.user.dto.UserUpdateRequest;
import com.gohub.user.entity.User;
import com.gohub.user.enums.Role;
import com.gohub.user.repository.UserRepository;
import com.gohub.user.utility.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,
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

    @Override
    public UserResponse updateUser(String email, UserUpdateRequest updateRequest) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with email: " + email));

        if (!user.getEmail().equals(updateRequest.getEmail())){
            if (userRepository.existsByEmail(updateRequest.getEmail())){
                throw new DuplicateResourceException(
                        "Email already registered: " +  updateRequest.getEmail()
                );
            }
            user.setEmail(updateRequest.getEmail());
        }

        user.setName(updateRequest.getName());
        user.setGender(updateRequest.getGender());

        if (updateRequest.getProfilepic() != null){
            user.setProfilepic(updateRequest.getProfilepic());
        }

        User updatedUser = userRepository.save(user);

        return userMapper.toResponce(updatedUser);
    }


}

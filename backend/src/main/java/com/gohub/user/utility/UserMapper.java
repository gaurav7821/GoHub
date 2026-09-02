package com.gohub.user.utility;

import com.gohub.user.dto.UserRequest;
import com.gohub.user.dto.UserResponse;
import com.gohub.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest request){

        User user = new User();

        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setGender(request.getGender());

        return user;
    }

    public UserResponse toResponce(User user){
        return new UserResponse (
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getGender(),
                user.getProfilepic()
                );
    }
}

package com.supermart.user.service;

import com.supermart.user.dto.UserAccountResponse;
import com.supermart.user.dto.UserResponse;
import com.supermart.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMapperService {
    private final UserAccountMapperService userAccountMapperService;

    public UserResponse mapToUserResponse(User user){
        UserAccountResponse userAccountResponse=userAccountMapperService.mapToUserAccountResponse(user.getUserAccount());
        return UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userAccountResponse(userAccountResponse)
                .build();
    }

    public List<UserResponse> mapToUserResponseList(List<User> userList) {
        return userList.stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }
}

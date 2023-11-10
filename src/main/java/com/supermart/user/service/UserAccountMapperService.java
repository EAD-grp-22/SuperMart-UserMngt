package com.supermart.user.service;

import com.supermart.user.dto.UserAccountResponse;
import com.supermart.user.model.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountMapperService {
    public UserAccountResponse mapToUserAccountResponse(UserAccount userAccount){
        return UserAccountResponse.builder()
                .email(userAccount.getEmail())
                .userName(userAccount.getUserName())
                .role(userAccount.getRole())
                .build();
    }
}

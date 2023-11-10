package com.supermart.user.dto;

import com.supermart.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountResponse {
    private String email;
    private String userName;
    private Role role;
}

package com.mathup.auth.dto;

import com.mathup.user.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {

    private String token;
    private Long userId;
    private String email;
    private String fullName;
    private Role role;
}

package com.example.Dingle.user.dto;

import com.example.Dingle.user.type.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String userId;
    private String password;
    private String username;
    private String phone;
    private String email;

    private UserRole role;
}

package com.Basha.Library.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String name;
    private String email;
    private String mobileNumber;
    private String password;
    private String userRole;
}

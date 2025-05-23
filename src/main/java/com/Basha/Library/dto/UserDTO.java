package com.Basha.Library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String mobileNumber;
    private String userRole;
    private String password;
    private Boolean isBorrowed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public UserDTO(Long id, String name, String email, String mobileNumber, String userRole) {
    }
}

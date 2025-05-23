package com.Basha.Library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenPayLoadDTO {
    private String sessionId;
    private String userName;
    private Date expiresTime;
}

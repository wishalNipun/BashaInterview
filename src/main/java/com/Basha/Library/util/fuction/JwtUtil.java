package com.Basha.Library.util.fuction;


import com.Basha.Library.dto.TokenDTO;
import com.Basha.Library.dto.TokenPayLoadDTO;
import com.Basha.Library.repository.UserRepository;
import com.Basha.Library.util.constant.JWTConfig;
import com.Basha.Library.util.mapping.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
    private final JWTConfig jwtConfig;
    private final UserRepository userRepository;

    @Autowired
    public JwtUtil(JWTConfig jwtConfig, UserRepository userRepository) {
        this.jwtConfig = jwtConfig;
        this.userRepository = userRepository;
    }

    public String getUsernameFromToken(String token) {
        if (token != null) {
            String payLoad = JWT.require(
                            Algorithm.HMAC512(jwtConfig.getSecretKey().getBytes()))
                    .build().verify(token).getSubject();
            Date expiresAt = JWT.require(
                            Algorithm.HMAC512(jwtConfig.getSecretKey().getBytes()))
                    .build().verify(token).getExpiresAt();
            if (payLoad != null && !payLoad.isEmpty()) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                TokenPayLoadDTO payLoadBean = gson.fromJson(payLoad, TokenPayLoadDTO.class);
                payLoadBean.setExpiresTime(expiresAt);
                return payLoadBean.getUserName();
            }
        }
        return null;
    }

    public TokenDTO getTokensDTO(String email, Date loggingTime) {
        TokenDTO refData = new TokenDTO();
        try {
            User optionalUser = userRepository.findByEmail(email).orElse(null);

            if (optionalUser == null) {
                // Log user not found
                log.error("User not found with email: {}", email);
                throw new IllegalArgumentException("User not found");
            }
            Date tokenExpireDate = new Date(loggingTime.getTime() + jwtConfig.getTokenExp());
            refData.setToken(this.getToken(email, loggingTime, tokenExpireDate));
            refData.setUserEmail(optionalUser.getEmail());
            refData.setUserID(String.valueOf(optionalUser.getId()));
            return refData;
        } catch (Exception ex) {
            log.error("Exception : ", ex);
            throw ex;
        }
    }

    private String getToken(String username, Date loggingTime, Date tokenExpireDate) {
        try {
            String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
            TokenPayLoadDTO payLoadBean = new TokenPayLoadDTO(sessionId, username, tokenExpireDate);

            Gson gson = new Gson();
            String payLoadJson = gson.toJson(payLoadBean);

            String token = JWT.create()
                    .withSubject(payLoadJson)
                    .withIssuedAt(loggingTime)
                    .withIssuer("Basha")
                    .withExpiresAt(tokenExpireDate)
                    .sign(Algorithm.HMAC512(jwtConfig.getSecretKey().getBytes()));

            token = jwtConfig.getTokenPrefix() + token;
            return token;
        } catch (Exception ex) {
            log.error("Exception : ", ex);
            throw ex;
        }
    }
}

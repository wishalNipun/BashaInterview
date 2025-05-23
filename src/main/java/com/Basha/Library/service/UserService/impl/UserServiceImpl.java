package com.Basha.Library.service.UserService.impl;

import com.Basha.Library.dto.UserDTO;
import com.Basha.Library.repository.UserRepository;
import com.Basha.Library.service.UserService.UserService;
import com.Basha.Library.util.Response.Response;
import com.Basha.Library.util.mapping.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Response insert(UserDTO dto) {
        // Check if user with email exists
        Optional<User> existingUser = userRepository.findByEmail(dto.getEmail());
        if (existingUser.isPresent()) {
            return new Response("409", "User with this email already exists!", null);
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setMobileNumber(dto.getMobileNumber());
        user.setUserRole(dto.getUserRole() != null ? dto.getUserRole() : "USER");
        user.setPassword(dto.getPassword());

        userRepository.save(user);
        return new Response("201", "User created successfully", null);
    }

    @Override
    public Response update(UserDTO dto) {
        Optional<User> optionalUser = userRepository.findById(dto.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (dto.getName() != null) user.setName(dto.getName());
            if (dto.getEmail() != null && !dto.getEmail().equals(user.getEmail())) {
                // Check email uniqueness for update
                if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
                    return new Response("409", "Email already in use by another user", null);
                }
                user.setEmail(dto.getEmail());
            }
            if (dto.getMobileNumber() != null) user.setMobileNumber(dto.getMobileNumber());
            if (dto.getUserRole() != null) user.setUserRole(dto.getUserRole());
            if (dto.getPassword() != null) user.setPassword(dto.getPassword()); // TODO: encode

            userRepository.save(user);
            return new Response("200", "User updated successfully", null);
        } else {
            return new Response("404", "User not found", null);
        }
    }

    @Override
    public Response findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserDTO dto = new UserDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getMobileNumber(),
                    user.getUserRole()
            );
            return new Response("200", "User found", Collections.singletonList(dto));
        } else {
            return new Response("404", "User not found", null);
        }
    }

    @Override
    public Response deleteById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
            return new Response("200", "User deleted successfully", null);
        } else {
            return new Response("404", "User not found", null);
        }
    }

    @Override
    public Response getAllUsers() {
        List<UserDTO> users = userRepository.findAll().stream().map(user -> {
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setEmail(user.getEmail());
            dto.setPassword(user.getPassword());
            dto.setUserRole(user.getUserRole());
            dto.setMobileNumber(user.getMobileNumber());
            dto.setCreatedAt(user.getCreatedAt());
            dto.setUpdatedAt(user.getUpdatedAt());

            return dto;
        }).collect(Collectors.toList());

        return new Response("200", "All users listed", Collections.singletonList(users));

    }
}

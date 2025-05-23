package com.Basha.Library.controller;


import com.Basha.Library.dto.UserDTO;
import com.Basha.Library.service.UserService.UserService;
import com.Basha.Library.util.Response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/insert")
    public ResponseEntity<Response> createUser(@RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.insert(dto));
    }

    @PutMapping("/update")
    public ResponseEntity<Response> updateUser(@RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.update(dto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Response> deleteUser(@RequestParam Long id) {
        return ResponseEntity.ok(userService.deleteById(id));
    }

    @GetMapping
    public ResponseEntity<Response> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}

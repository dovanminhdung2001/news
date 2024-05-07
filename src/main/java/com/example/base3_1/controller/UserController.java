package com.example.base3_1.controller;

import com.example.base3_1.dto.UserDTO;
import com.example.base3_1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/api/user/user/update")
    ResponseEntity<?> update (@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.update(userDTO));
    }
}

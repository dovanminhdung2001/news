package com.example.base3_1.controller;

import com.example.base3_1.dto.UserDTO;
import com.example.base3_1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/user/user/update")
    ResponseEntity<?> update (@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.update(userDTO));
    }

    @GetMapping("/admin/user/page")
    ResponseEntity<?> page (Pageable pageable) {
        return ResponseEntity.ok(userService.page(pageable));
    }

    @DeleteMapping("/admin/user/delete")
    ResponseEntity<?> delete (@RequestParam Integer id, @RequestParam Boolean isActive) {
        return ResponseEntity.ok(userService.delete(id, isActive));
    }
}

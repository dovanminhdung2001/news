package com.example.base3_1.service.impl;

import com.example.base3_1.dto.MessageResponseDTO;
import com.example.base3_1.dto.UserDTO;
import com.example.base3_1.entity.User;
import com.example.base3_1.repository.RoleRepository;
import com.example.base3_1.repository.UserRepository;
import com.example.base3_1.security.PasswordGenerator;
import com.example.base3_1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Override
    public User createUser(UserDTO dto) {
        if (existsByPhone(dto.getPhone()))
            throw new RuntimeException("register phone");

        if (dto.getRole().getId() != 3)
            throw new RuntimeException("Wrong role");

        User user = new User();

        user.setPhone(dto.getPhone());
        user.setPassword(PasswordGenerator.getHashString(dto.getPassword()));
        user.setRole(dto.getRole());

        return userRepository.save(user);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public User findByPhone(String phone) {
        return userRepository.findUserByPhone(phone);
    }
}

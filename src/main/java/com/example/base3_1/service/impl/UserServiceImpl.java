package com.example.base3_1.service.impl;

import com.example.base3_1.dto.MessageResponseDTO;
import com.example.base3_1.dto.UserDTO;
import com.example.base3_1.entity.User;
import com.example.base3_1.repository.RoleRepository;
import com.example.base3_1.repository.UserRepository;
import com.example.base3_1.security.PasswordGenerator;
import com.example.base3_1.service.UserService;
import io.micrometer.common.util.StringUtils;
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

    @Override
    public User update(UserDTO dto) {
        User user = userRepository.findById(dto.getId()).get();

        if (user == null)
            throw new RuntimeException("user not found");

        if (StringUtils.isNotBlank(dto.getPassword()))
            user.setPassword(PasswordGenerator.getHashString(dto.getPassword()));
        if (StringUtils.isNotBlank(dto.getName()))
            user.setName(dto.getName());
        if (StringUtils.isNotBlank(dto.getEmail()))
            user.setEmail(dto.getEmail());
        if (StringUtils.isNotBlank(dto.getAvatar()))
            user.setAvatar(dto.getAvatar());
        if (StringUtils.isNotBlank(dto.getGender()))
            user.setGender(dto.getGender());
        if (StringUtils.isNotBlank(dto.getDescription()))
            user.setDescription(dto.getDescription());

        return userRepository.save(user);
    }
}

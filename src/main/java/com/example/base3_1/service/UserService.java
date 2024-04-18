package com.example.base3_1.service;

import com.example.base3_1.dto.UserDTO;
import com.example.base3_1.entity.User;

public interface UserService {
    User createUser(UserDTO dto);

    boolean existsByPhone(String phone);

    User findByPhone(String phone);
}

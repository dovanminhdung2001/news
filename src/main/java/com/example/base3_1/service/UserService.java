package com.example.base3_1.service;

import com.example.base3_1.dto.UserDTO;
import com.example.base3_1.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User createUser(UserDTO dto);

    boolean existsByPhone(String phone);

    User findByPhone(String phone);

    User update(UserDTO userDTO);

    Page<User> page(Pageable pageable);

    User delete(Integer id, Boolean isActive);
}

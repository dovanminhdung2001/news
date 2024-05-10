package com.example.base3_1.repository;

import com.example.base3_1.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByPhone(String phone);

    boolean existsByPhone(String phone);
    Page<User> findAllByRole_Id(Pageable pageable, Integer roleId);
}

package com.example.base3_1.dto;

import com.example.base3_1.entity.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private int id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String avatar;
    private String gender;
    private String description;
    private Role role;
    private Integer roleId;
    private Boolean isActive = true;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;
    public UserDTO() {
    }
}

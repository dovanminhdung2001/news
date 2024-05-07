package com.example.base3_1.dto;

import com.example.base3_1.entity.Bookmark;
import com.example.base3_1.entity.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

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
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dob;
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private Date createdDate;
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private Date updatedDate;
    private Boolean isActive = true;
    private List<Bookmark> bookmarks;
    public UserDTO() {
    }
}

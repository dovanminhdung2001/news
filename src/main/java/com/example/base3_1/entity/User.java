package com.example.base3_1.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
    @Column(name = "phone")
    private String phone;
    @Column(columnDefinition = "LONGTEXT")
    private String avatar;

    @Column(name = "gender")
    private String gender;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId")
    private Role role;
    @Column(name = "isActive")
    private Boolean isActive = true;
    @Column(name = "birth_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @CreatedDate
    private Date birthDate;
}

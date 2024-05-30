package com.example.base3_1.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "phone"))
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

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "user_bookmarks",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "news_id") }
    )
    private List<News> bookmarks = new ArrayList<>();
}

package com.example.base3_1.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "hashtag")
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToMany(mappedBy = "hashtags")
    @JsonIgnore
    List<News> news;
    @Column(name = "created_date", updatable = false)
    @CreatedDate
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private Date createdDate;
}

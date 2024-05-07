package com.example.base3_1.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @Column(columnDefinition = "longtext")
    private String thumbnail;
    @Column(columnDefinition = "longtext")
    private String contentHtml;
    @Column(columnDefinition = "longtext")
    private String contentText;
    private Integer view;
    @Column(name = "created_date", updatable = false)
    @CreatedDate
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private Date createdDate;
    @Column(name = "updated_date", insertable = false)
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private Date updatedDate;
    private boolean deleted;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<Bookmark> bookmarks;
    @ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "news_hashtag",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private List<Hashtag> hashtags;
}

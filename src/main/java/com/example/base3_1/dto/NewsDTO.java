package com.example.base3_1.dto;

import com.example.base3_1.entity.Hashtag;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NewsDTO {
    private int id;
    private String title;
    private String thumbnail;
    private String contentHtml;
    private String contentText;
    private Integer view;
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private Date createdDate;
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private Date updatedDate;
    private boolean deleted;
    private List<Hashtag> hashtags;
    private List<String> hashtagListStr;
}

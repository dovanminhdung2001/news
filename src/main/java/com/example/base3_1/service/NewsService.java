package com.example.base3_1.service;

import com.example.base3_1.dto.NewsDTO;
import com.example.base3_1.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewsService {
    News create(NewsDTO dto);

    News update(NewsDTO dto);

    News get(Integer id);

    Page<News> find(Pageable pageable, String keys);

    List<News> bookmark(Integer id, Boolean favor, Integer userId);

    Page<News> home(Pageable pageable, Boolean newest);

    Page<News> page(Pageable pageable);

    News delete(Integer id, Boolean deleted);

    Page<News> listBookMark(Pageable pageable, Integer userId);

    String checkBookmark(Integer newsId, Integer userId);
}

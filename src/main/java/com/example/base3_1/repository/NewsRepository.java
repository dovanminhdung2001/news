package com.example.base3_1.repository;

import com.example.base3_1.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface NewsRepository extends JpaRepository<News, Integer> {
    Page<News> findAllByTitleContainingIgnoreCase(Pageable pageable, String key);
    Page<News> findAllByDeleted(Pageable pageable, Boolean deleted);

    @Query(value = "SELECT n FROM News n WHERE n.createdDate >= :startDate AND n.deleted = false " +
            "ORDER BY n.view DESC")
    Page<News> findHotNews(@Param("startDate") Date startDate, Pageable pageable);
}

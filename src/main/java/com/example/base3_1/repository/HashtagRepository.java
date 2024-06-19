package com.example.base3_1.repository;

import com.example.base3_1.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Integer> {
    Hashtag findByName(String name);
}

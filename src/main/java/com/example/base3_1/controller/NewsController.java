package com.example.base3_1.controller;

import com.example.base3_1.dto.NewsDTO;
import com.example.base3_1.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @PostMapping("/admin/news/create")
    ResponseEntity<?> create(@RequestBody NewsDTO dto) {
        return ResponseEntity.ok(newsService.create(dto));
    }

    @PutMapping("/admin/news/update")
    ResponseEntity<?> update(@RequestBody NewsDTO dto) {
        return ResponseEntity.ok(newsService.update(dto));
    }

    @GetMapping({"/admin/news/get", "/user/news/get", "/news/get"})
    ResponseEntity<?> get(@RequestParam Integer id) {
        return ResponseEntity.ok(newsService.get(id));
    }

    @GetMapping("/news/find")
    ResponseEntity<?> get(Pageable pageable, @RequestParam String keys) {
        System.out.println(keys);
        return ResponseEntity.ok(newsService.find(pageable, keys));
    }

    @PostMapping("/user/news/bookmark")
    ResponseEntity<?> get(@RequestParam Integer id, @RequestParam Boolean favor) {
        return ResponseEntity.ok(newsService.bookmark(id, favor));
    }

    @GetMapping("/news/home")
    ResponseEntity<?> home(Pageable pageable, @RequestParam Boolean newest) {
        return ResponseEntity.ok(newsService.home(pageable, newest));
    }
}

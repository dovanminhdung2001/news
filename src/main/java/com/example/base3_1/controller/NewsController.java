package com.example.base3_1.controller;

import com.example.base3_1.dto.MessageResponseDTO;
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
    ResponseEntity<?> get(
            Pageable pageable,
            @RequestParam(required = false) String keys,
            @RequestParam(required = false) String hashtag
            ) {

        return keys != null
                ? ResponseEntity.ok(newsService.find(pageable, keys))
                : ResponseEntity.ok(newsService.findByHashtag(pageable, hashtag));
    }

    @PostMapping("/user/news/bookmark")
    ResponseEntity<?> get(@RequestParam Integer id, @RequestParam Boolean favor, @RequestParam Integer userId) {
        return ResponseEntity.ok(newsService.bookmark(id, favor, userId));
    }

    @GetMapping("/user/news/list-bookmark")
    ResponseEntity<?> getListBookmark(Pageable pageable, @RequestParam Integer userId) {
        return ResponseEntity.ok(newsService.listBookMark(pageable, userId));
    }

    @GetMapping("/user/news/check-bookmark")
    ResponseEntity<?> checkBookmark(@RequestParam Integer newsId, @RequestParam Integer userId) {
        return ResponseEntity.ok(new MessageResponseDTO(newsService.checkBookmark(newsId, userId)));
    }

    @GetMapping("/news/home")
    ResponseEntity<?> home(Pageable pageable, @RequestParam Boolean newest) {
        return ResponseEntity.ok(newsService.home(pageable, newest));
    }

    @GetMapping("/admin/news/page")
    ResponseEntity<?> page(Pageable pageable) {
        return ResponseEntity.ok(newsService.page(pageable));
    }

    @DeleteMapping("/admin/news/delete")
    ResponseEntity<?> delete(@RequestParam Integer id, @RequestParam Boolean deleted) {
        return ResponseEntity.ok(newsService.delete(id, deleted));
    }
}

package com.example.base3_1.service.impl;

import com.example.base3_1.dto.NewsDTO;
import com.example.base3_1.entity.News;
import com.example.base3_1.entity.User;
import com.example.base3_1.repository.NewsRepository;
import com.example.base3_1.repository.UserRepository;
import com.example.base3_1.security.principal.UserPrincipal;
import com.example.base3_1.service.NewsService;
import com.example.base3_1.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public News create(NewsDTO dto) {
        News news = new News();

        news.setTitle(dto.getTitle());
        news.setThumbnail(dto.getThumbnail());
        news.setContentHtml(dto.getContentHtml());
        news.setContentText(dto.getContentText());
        news.setView(0);
        news.setCreatedDate(DateUtils.now());
        news.setDeleted(false);

        return newsRepository.save(news);
    }

    @Override
    public News update(NewsDTO dto) {
        News news = newsRepository.findById(dto.getId()).get();

        if (news == null)
            throw new RuntimeException("News not found");

        news.setTitle(dto.getTitle());
        news.setThumbnail(dto.getThumbnail());
        news.setContentHtml(dto.getContentHtml());
        news.setContentText(dto.getContentText());
        news.setUpdatedDate(DateUtils.now());

        return newsRepository.save(news);
    }

    @Override
    public News get(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        News news = newsRepository.findById(id).get();

        if (news == null)
            throw new RuntimeException("News not found");

        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            if (authentication.getPrincipal() instanceof UserPrincipal) {
                UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
                if (userPrincipal.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"))) {
                    news.setView(news.getView() + 1);
                    news = newsRepository.save(news);                }
            }
        }

        return news;
    }

    @Override
    public Page<News> find(Pageable pageable, String key) {
        Page<News> page = newsRepository.findAllByTitleContainingIgnoreCase(pageable, key);

        return page;
    }

    @Override
    public List<News> bookmark(Integer id, Boolean favor) {
        News news = newsRepository.findById(id).get();

        if (news == null)
            throw new RuntimeException("News not found");

        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findById(currentUser.getId()).get();

        if (favor && !user.getBookmarks().contains(news)) {
            user.getBookmarks().add(news);
            userRepository.save(user);
        } else if (!favor && user.getBookmarks().contains(news)) {
            user.getBookmarks().remove(news);
            userRepository.save(user);
        }

        return user.getBookmarks();
    }

    @Override
    public Page<News> home(Pageable pageable, Boolean newest) {
        if(newest)
            return newsRepository.findAllByDeleted(pageable, false);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -30);

        return newsRepository.findHotNews(calendar.getTime(), pageable);
    }

    @Override
    public Page<News> page(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }
}

package com.example.base3_1.service.impl;

import com.example.base3_1.dto.NewsDTO;
import com.example.base3_1.entity.Hashtag;
import com.example.base3_1.entity.News;
import com.example.base3_1.entity.User;
import com.example.base3_1.repository.HashtagRepository;
import com.example.base3_1.repository.NewsRepository;
import com.example.base3_1.repository.UserRepository;
import com.example.base3_1.security.principal.UserPrincipal;
import com.example.base3_1.service.NewsService;
import com.example.base3_1.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final HashtagRepository hashtagRepository;

    @Override
    public News create(NewsDTO dto) {
        News news = new News();
        List<Hashtag> hashtags = new ArrayList<>();

        if (!dto.getHashtagListStr().isEmpty()) {
             hashtags = dto.getHashtagListStr().stream()
                    .map(tagName -> {
                        Hashtag hashtag = hashtagRepository.findByName(tagName);
                        if (hashtag == null) {
                            hashtag = new Hashtag();
                            hashtag.setName(tagName);
                            hashtag.setCreatedDate(DateUtils.now());
                            hashtag = hashtagRepository.save(hashtag);
                        }
                        return hashtag;
                    })
                    .collect(Collectors.toList());
        }

        news.setHashtags(hashtags);
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
        List<Hashtag> hashtags = new ArrayList<>();

        if (news == null)
            throw new RuntimeException("News not found");

        if (!dto.getHashtagListStr().isEmpty()) {
            hashtags = dto.getHashtagListStr().stream()
                    .map(tagName -> {
                        Hashtag hashtag = hashtagRepository.findByName(tagName);
                        if (hashtag == null) {
                            hashtag = new Hashtag();
                            hashtag.setName(tagName);
                            hashtag.setCreatedDate(DateUtils.now());
                            hashtag = hashtagRepository.save(hashtag);
                        }
                        return hashtag;
                    })
                    .collect(Collectors.toList());
        }

        news.setHashtags(hashtags);
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
    public Page<News> findByHashtag(Pageable pageable, String hashtagName) {
        Hashtag hashtag = hashtagRepository.findByName(hashtagName);

        if (hashtag == null || hashtagName == null || hashtagName.isEmpty()) {
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }

        return newsRepository.findAllByHashtagsName(pageable, hashtagName);
    }

    @Override
    public List<News> bookmark(Integer id, Boolean favor, Integer userId) {
        News news = newsRepository.findById(id).get();

        if (news == null)
            throw new RuntimeException("News not found");

//        UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findById(userId).get();

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
            return newsRepository.findAllByDeletedOrderByCreatedDateDesc(pageable, false);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -30);

        return newsRepository.findHotNews(calendar.getTime(), pageable);
    }

    @Override
    public Page<News> page(Pageable pageable) {
        return newsRepository.findAllByOrderByCreatedDateDesc(pageable);
    }

    @Override
    public News delete(Integer id, Boolean deleted) {
        News news = newsRepository.findById(id).get();

        if (news == null)
            throw new RuntimeException("News notfound");

        news.setDeleted(!deleted);

        return newsRepository.save(news);
    }

    @Override
    public Page<News> listBookMark(Pageable pageable, Integer userId) {
        return newsRepository.findAllByBookmarkedBy_IdAndDeletedFalse(pageable, userId);
    }

    @Override
    public String checkBookmark(Integer newsId, Integer userId) {
        return "" + newsRepository.existsByIdAndBookmarkedBy_Id(newsId, userId);
    }
}

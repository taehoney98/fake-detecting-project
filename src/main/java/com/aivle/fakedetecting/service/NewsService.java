package com.aivle.fakedetecting.service;

import com.aivle.fakedetecting.entity.News;
import com.aivle.fakedetecting.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;

    public List<News> saveAll(List<News> newsList) {
        return newsRepository.saveAll(newsList);
    }
}

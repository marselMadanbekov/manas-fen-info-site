package com.example.manasfen.services.news;

import com.example.manasfen.exceptions.InvalidDataFormatException;
import com.example.manasfen.model.entyties.News;
import com.example.manasfen.model.payload.NewsCreate;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface NewsService {
    Page<News> findAllByPage(Integer page);

    News createNews(NewsCreate newsCreate) throws IOException, InvalidDataFormatException;

    News findById(Long newsId);

    List<News> getLastNews();

    void deleteById(Long neweId);
}

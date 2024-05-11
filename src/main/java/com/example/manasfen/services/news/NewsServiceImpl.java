package com.example.manasfen.services.news;


import com.example.manasfen.exceptions.InvalidDataFormatException;
import com.example.manasfen.model.entyties.News;
import com.example.manasfen.model.payload.NewsCreate;
import com.example.manasfen.reposiroties.NewsRepository;
import com.example.manasfen.services.photo.NewsPhotoDimensionHolder;
import com.example.manasfen.services.photo.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService{

    @Value("${custom.parameters.news.page-size}")
    private Integer pageSize;
    private final NewsPhotoDimensionHolder newsPhotoDimensionHolder;

    private final PhotoService photoService;

    private final NewsRepository newsRepository;
    @Override
    public Page<News> findAllByPage(Integer page) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return newsRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public News createNews(NewsCreate newsCreate) throws IOException, InvalidDataFormatException {
        String photo = photoService.savePhoto(newsCreate.photo(),newsPhotoDimensionHolder);


        return newsRepository.save(
                News.builder()
                        .title(newsCreate.title())
                        .eventDate(Date.valueOf(newsCreate.eventDate()))
                        .subtitle(newsCreate.subtitle())
                        .text(newsCreate.text())
                        .photo(photo)
                        .build()
        );
    }

    @Override
    public News findById(Long newsId) {
        return newsRepository.findById(newsId).orElseThrow(() -> new NoSuchElementException("Жаңылык табылган жок!"));
    }

    @Override
    public List<News> getLastNews() {
        return newsRepository.findFirst3ByOrderByCreateDateDesc();
    }

    @Override
    public void deleteById(Long neweId) {
        newsRepository.deleteById(neweId);
    }
}

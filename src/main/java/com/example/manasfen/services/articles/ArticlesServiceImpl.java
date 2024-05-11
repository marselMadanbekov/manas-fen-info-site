package com.example.manasfen.services.articles;


import com.example.manasfen.exceptions.InvalidDataFormatException;
import com.example.manasfen.model.entyties.StudentsArticle;
import com.example.manasfen.model.payload.ArticleCreate;
import com.example.manasfen.reposiroties.StudentArticleRepository;
import com.example.manasfen.services.photo.ArticlesPhotoDimensionHolder;
import com.example.manasfen.services.photo.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ArticlesServiceImpl implements ArticlesService {

    @Value("${custom.parameters.articles.page-size}")
    private Integer pageSize;

    private final PhotoService photoService;

    private final ArticlesPhotoDimensionHolder articlesPhotoDimensionHolder;
    private final StudentArticleRepository articlesRepository;

    @Override
    @Transactional
    public StudentsArticle createStudentsArticle(ArticleCreate newsCreate) throws IOException, InvalidDataFormatException {
        String photo = photoService.savePhoto(newsCreate.photo(), articlesPhotoDimensionHolder);
        return articlesRepository.save(
                StudentsArticle.builder()
                        .fullName(newsCreate.fullname())
                        .subtitle(newsCreate.subtitle())
                        .story(newsCreate.story())
                        .photo(photo)
                        .build()
        );
    }

    @Override
    public Page<StudentsArticle> findAllByPage(Integer page) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return articlesRepository.findAll(pageable);
    }

    @Override
    public StudentsArticle findById(Long newsId) {
        return articlesRepository.findById(newsId).orElseThrow(() -> new NoSuchElementException("Бүтүрүүчү табылган жок!"));
    }

    @Override
    public List<StudentsArticle> getLastArticles() {
        return articlesRepository.findFirst3ByOrderByCreateDateDesc();
    }

    @Override
    public void deleteById(Long articleId) {
        articlesRepository.deleteById(articleId);
    }
}

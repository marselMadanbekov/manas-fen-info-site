package com.example.manasfen.services.articles;

import com.example.manasfen.exceptions.InvalidDataFormatException;
import com.example.manasfen.model.entyties.StudentsArticle;
import com.example.manasfen.model.payload.ArticleCreate;
import com.example.manasfen.model.payload.NewsCreate;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface ArticlesService {
    Page<StudentsArticle> findAllByPage(Integer page);

    StudentsArticle createStudentsArticle(ArticleCreate articleCreate) throws IOException, InvalidDataFormatException;

    StudentsArticle findById(Long newsId);

    List<StudentsArticle> getLastArticles();

    void deleteById(Long articleId);
}

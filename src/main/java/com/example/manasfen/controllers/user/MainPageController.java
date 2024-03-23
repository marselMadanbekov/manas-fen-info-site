package com.example.manasfen.controllers.user;


import com.example.manasfen.model.entyties.News;
import com.example.manasfen.model.entyties.StudentsArticle;
import com.example.manasfen.services.articles.ArticlesService;
import com.example.manasfen.services.news.NewsService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class MainPageController {

    private final NewsService newsService;
    private final ArticlesService articlesService;

    @GetMapping
    public String mainPage(@RequestParam(value = "errors", defaultValue = "0") Integer hasErrors,
                           Model model,
                           HttpSession httpSession) {
        List<News> lastNews = newsService.getLastNews();
        List<StudentsArticle> articles = articlesService.getLastArticles();
        if (hasErrors.equals(0)) {
            httpSession.removeAttribute("payload");
            httpSession.removeAttribute("errors");
        }

        model.addAttribute("lastNews", lastNews);
        model.addAttribute("articles", articles);
        return "public/index";
    }
}

package com.example.manasfen.controllers.user;

import com.example.manasfen.model.entyties.News;
import com.example.manasfen.model.entyties.Survey;
import com.example.manasfen.model.entyties.UsefulLink;
import com.example.manasfen.services.news.NewsService;
import com.example.manasfen.services.surveys.SurveysService;
import com.example.manasfen.services.usefullinks.LinksService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller()
@RequestMapping("/news")
@RequiredArgsConstructor
public class PublicNewsController {

    private final NewsService newsService;
    private final LinksService linksService;
    private final SurveysService surveysService;

    @GetMapping()
    public String news(@RequestParam(value = "page", defaultValue = "0") Integer page,
                       Model model) {
        Page<News> news = newsService.findAllByPage(page);
        List<UsefulLink> links = linksService.getLastLinks();
        List<Survey> surveys = surveysService.getLastSurveys();
        model.addAttribute("surveys", surveys);
        model.addAttribute("news", news);
        model.addAttribute("links", links);
        return "public/news";
    }

    @GetMapping("/{newsId}")
    public String newsDetails(@PathVariable("newsId") Long newsId,
                              Model model) {
        News news = newsService.findById(newsId);
        List<UsefulLink> links = linksService.getLastLinks();
        List<Survey> surveys = surveysService.getLastSurveys();
        model.addAttribute("surveys", surveys);
        model.addAttribute("news", news);
        model.addAttribute("links", links);
        return "public/news-details";
    }
}

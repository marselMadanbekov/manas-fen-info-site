package com.example.manasfen.controllers.user;

import com.example.manasfen.model.entyties.News;
import com.example.manasfen.model.entyties.StudentsArticle;
import com.example.manasfen.model.entyties.UsefulLink;
import com.example.manasfen.services.articles.ArticlesService;
import com.example.manasfen.services.news.NewsService;
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
@RequestMapping("/links")
@RequiredArgsConstructor
public class PublicLinksController {

    private final ArticlesService articleService;
    private final LinksService linksService;

    @GetMapping()
    public String links(@RequestParam(value = "page", defaultValue = "0") Integer page,
                        Model model) {
        Page<UsefulLink> links = linksService.findAllByPage(page);
        List<StudentsArticle> articles = articleService.getLastArticles();
        model.addAttribute("links", links);
        model.addAttribute("articles", articles);
        return "public/links";
    }

    @GetMapping("/{linkId}")
    public String linkDetails(@PathVariable("linkId") Long linkId,
                              Model model) {
        UsefulLink usefulLink = linksService.findById(linkId);
        List<StudentsArticle> articles = articleService.getLastArticles();
        model.addAttribute("link", usefulLink);
        model.addAttribute("articles", articles);

        return "public/link-details";
    }
}

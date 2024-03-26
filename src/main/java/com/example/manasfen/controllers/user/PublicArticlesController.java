package com.example.manasfen.controllers.user;

import com.example.manasfen.model.entyties.StudentsArticle;
import com.example.manasfen.model.entyties.Survey;
import com.example.manasfen.model.entyties.Teacher;
import com.example.manasfen.services.articles.ArticlesService;
import com.example.manasfen.services.surveys.SurveysService;
import com.example.manasfen.services.users.UsersService;
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
@RequestMapping("/articles")
@RequiredArgsConstructor
public class PublicArticlesController {

    private final ArticlesService articlesService;
    private final UsersService usersService;
    private final SurveysService surveysService;

    @GetMapping()
    public String articles(@RequestParam(value = "page", defaultValue = "0") Integer page,
                           Model model) {

        Page<StudentsArticle> studentsArticles = articlesService.findAllByPage(page);
        model.addAttribute("articles",studentsArticles);
        return "public/articles";
    }

    @GetMapping("/{articleId}")
    public String articleDetails(@PathVariable("articleId") Long articleId,
                                 Model model){
        StudentsArticle article = articlesService.findById(articleId);
        List<Teacher> teachers = usersService.getLastTeachers();
        List<Survey> surveys = surveysService.getLastSurveys();

        model.addAttribute("surveys", surveys);
        model.addAttribute("article", article);
        model.addAttribute("teachers",teachers);
        return "public/article-details";
    }
}

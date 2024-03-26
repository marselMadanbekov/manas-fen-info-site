package com.example.manasfen.controllers.user;


import com.example.manasfen.model.entyties.*;
import com.example.manasfen.model.payload.SurveyResultCreate;
import com.example.manasfen.model.payload.SurveyStudentStatus;
import com.example.manasfen.services.articles.ArticlesService;
import com.example.manasfen.services.news.NewsService;
import com.example.manasfen.services.surveys.SurveysService;
import com.example.manasfen.services.users.UsersService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class MainPageController {

    private final NewsService newsService;
    private final ArticlesService articlesService;
    private final UsersService usersService;
    private final SurveysService surveysService;

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

    @GetMapping("/profile")
    public String profile(@RequestParam(value = "page", defaultValue = "0") Integer page,
                          Model model,
                          Principal principal) {
        User user = usersService.findUserByUsername(principal.getName());

        Page<SurveyStudentStatus> surveys = surveysService.findSurveyStudentStatusByPage(page, user);
        List<News> news = newsService.getLastNews();
        model.addAttribute("user", user);
        model.addAttribute("news", news);
        model.addAttribute("surveys", surveys);
        return "public/profile";
    }

    @GetMapping("/survey-details/{surveyId}")
    public String surveyDetails(@PathVariable("surveyId") Long surveyId,
                                Principal principal,
                                Model model) {
        Survey survey = surveysService.findById(surveyId);
        User user = usersService.findUserByUsername(principal.getName());
        List<SurveyResult> surveyResults = surveysService.findSurveyResultsByUserAndSurvey(user, survey);

        List<Teacher> teachers = new ArrayList<>();
        for(Teacher teacher : survey.getTargetTeachers()){
            boolean contains = false;
            for(SurveyResult result : surveyResults){
                if(result.getTargetTeacher().equals(teacher)){
                    contains = true;
                    break;
                }
            }
            if(!contains)   teachers.add(teacher);
        }

        model.addAttribute("survey", survey);
        model.addAttribute("user", user);
        model.addAttribute("teachers", teachers);
        model.addAttribute("surveyResults", surveyResults);

        return "public/survey-details";
    }

    @PostMapping("/survey-submit")
    public ResponseEntity<Map<String, String>> submit(@RequestBody SurveyResultCreate surveyResultCreate,
                                                      Principal principal) {
        Map<String, String> response = new HashMap<>();
        try {
            surveysService.createSurveyResult(surveyResultCreate,principal);
            response.put("message", "Форма успешно сохранена!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

    }

}

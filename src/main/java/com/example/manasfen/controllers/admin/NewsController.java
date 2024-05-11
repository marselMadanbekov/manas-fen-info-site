package com.example.manasfen.controllers.admin;

import com.example.manasfen.exceptions.InvalidDataFormatException;
import com.example.manasfen.model.entyties.News;
import com.example.manasfen.model.payload.NewsCreate;
import com.example.manasfen.services.news.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping()
    public String news(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                       Model model) {
        Page<News> news = newsService.findAllByPage(page);
        model.addAttribute("news", news);
        return "/admin/news/news";
    }
    @GetMapping("/{newsId}")
    public String news(@PathVariable("newsId") Long newsId,
                       Model model){
        model.addAttribute("news", newsService.findById(newsId));
        return "/admin/news/news-details";
    }

    @GetMapping("create")
    public String createNews() {
        return "/admin/news/news-create";
    }

    @PostMapping("/delete/{neweId}")
    public String deleteNews(@PathVariable("neweId") Long neweId){
        newsService.deleteById(neweId);
        return "redirect:/admin/news";
    }
    @PostMapping("create")
    public String createNews(@Valid @ModelAttribute("newsCreate") NewsCreate newsCreate,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", newsCreate);

            model.addAttribute("errors", bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage))
            );

            return "/admin/news/news-create";
        } else {
            try {
                News news = newsService.createNews(newsCreate);
                return "redirect:/admin/news/%d".formatted(news.getId());
            } catch (IOException e) {
                HashMap<String, String> errors = new HashMap<>();
                errors.put("photoIoError", e.getMessage());
                model.addAttribute("errors", errors);
                model.addAttribute("payload", newsCreate);
                return "/admin/news/news-create";

            } catch (InvalidDataFormatException e) {
                HashMap<String, String> errors = new HashMap<>();
                errors.put("photoFormatError", e.getMessage());
                model.addAttribute("errors", errors);
                model.addAttribute("payload", newsCreate);
                return "/admin/news/news-create";
            }
        }
    }
}

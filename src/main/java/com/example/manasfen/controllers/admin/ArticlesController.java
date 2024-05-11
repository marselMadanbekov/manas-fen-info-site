package com.example.manasfen.controllers.admin;

import com.example.manasfen.exceptions.InvalidDataFormatException;
import com.example.manasfen.model.entyties.StudentsArticle;
import com.example.manasfen.model.payload.ArticleCreate;
import com.example.manasfen.services.articles.ArticlesService;
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
@RequestMapping("/admin/articles")
@RequiredArgsConstructor
public class ArticlesController {

    private final ArticlesService articlesService;

    @GetMapping()
    public String articles(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                           Model model) {
        Page<StudentsArticle> articles = articlesService.findAllByPage(page);
        model.addAttribute("articles", articles);
        return "/admin/articles/articles";
    }

    @GetMapping("/{articlesId}")
    public String articles(@PathVariable("articlesId") Long articlesId,
                           Model model) {
        model.addAttribute("article", articlesService.findById(articlesId));
        return "/admin/articles/article-details";
    }

    @GetMapping("create")
    public String createArticle() {
        return "/admin/articles/article-create";
    }


    @PostMapping("/delete/{articleId}")
    public String deleteArticle(@PathVariable("articleId") Long articleId){
        articlesService.deleteById(articleId);
        return "redirect:/admin/articles";
    }
    @PostMapping("create")
    public String createArticle(@Valid @ModelAttribute("articlesCreate") ArticleCreate articlesCreate,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", articlesCreate);

            model.addAttribute("errors", bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage))
            );

            return "/admin/articles/article-create";
        } else {
            try {
                StudentsArticle articles = articlesService.createStudentsArticle(articlesCreate);
                return "redirect:/admin/articles/%d".formatted(articles.getId());
            } catch (IOException e) {
                HashMap<String, String> errors = new HashMap<>();
                errors.put("photoIoError", e.getMessage());
                model.addAttribute("errors", errors);
                model.addAttribute("payload", articlesCreate);
                return "/admin/articles/article-create";

            } catch (InvalidDataFormatException e) {
                HashMap<String, String> errors = new HashMap<>();
                errors.put("photoFormatError", e.getMessage());
                model.addAttribute("errors", errors);
                model.addAttribute("payload", articlesCreate);
                return "/admin/articles/article-create";
            }
        }
    }
}

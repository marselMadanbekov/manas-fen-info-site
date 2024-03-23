package com.example.manasfen.controllers.admin;

import com.example.manasfen.exceptions.InvalidDataFormatException;
import com.example.manasfen.model.entyties.UsefulLink;
import com.example.manasfen.model.payload.LinkCreate;
import com.example.manasfen.services.usefullinks.LinksService;
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
@RequestMapping("/admin/links")
@RequiredArgsConstructor
public class LinksController {

    private final LinksService linksService;

    @GetMapping()
    public String links(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                       Model model) {
        Page<UsefulLink> links = linksService.findAllByPage(page);
        model.addAttribute("links", links);
        return "/admin/links/links";
    }
    @GetMapping("/{linksId}")
    public String links(@PathVariable("linksId") Long linksId,
                       Model model){
        model.addAttribute("link", linksService.findById(linksId));
        return "/admin/links/link-details";
    }

    @GetMapping("create")
    public String createLinks() {
        return "/admin/links/link-create";
    }


    @PostMapping("create")
    public String createLinks(@Valid @ModelAttribute("linksCreate") LinkCreate linksCreate,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", linksCreate);

            model.addAttribute("errors", bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage))
            );

            return "/admin/links/link-create";
        } else {
            try {
                UsefulLink links = linksService.createLink(linksCreate);
                return "redirect:/admin/links/%d".formatted(links.getId());
            } catch (IOException e) {
                HashMap<String, String> errors = new HashMap<>();
                errors.put("photoIoError", e.getMessage());
                model.addAttribute("errors", errors);
                model.addAttribute("payload", linksCreate);
                return "/admin/links/link-create";

            } catch (InvalidDataFormatException e) {
                HashMap<String, String> errors = new HashMap<>();
                errors.put("photoFormatError", e.getMessage());
                model.addAttribute("errors", errors);
                model.addAttribute("payload", linksCreate);
                return "/admin/links/link-create";
            }
        }
    }
}

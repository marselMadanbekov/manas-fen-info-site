package com.example.manasfen.controllers.user;

import com.example.manasfen.model.entyties.News;
import com.example.manasfen.model.entyties.Teacher;
import com.example.manasfen.services.news.NewsService;
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

@Controller
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class PublicTeachersController {

    private final UsersService usersService;
    private final NewsService newsService;

    @GetMapping
    public String teachers(@RequestParam(value = "page", defaultValue = "0") Integer page,
                           Model model){
        Page<Teacher> teachers = usersService.findAllTeachersByPage(page);
        model.addAttribute("teachers", teachers);
        return "public/teachers";
    }

    @GetMapping("/{teacherId}")
    public String teacherDetails(@PathVariable("teacherId") Long teacherId,
                                 Model model){
        Teacher teacher = usersService.findTeacherById(teacherId);
        List<News> news = newsService.getLastNews();
        model.addAttribute("teacher",teacher);
        model.addAttribute("news", news);
        return "public/teacher-details";
    }
}

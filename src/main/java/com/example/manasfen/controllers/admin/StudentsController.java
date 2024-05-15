package com.example.manasfen.controllers.admin;

import com.example.manasfen.exceptions.InvalidDataFormatException;
import com.example.manasfen.model.entyties.Teacher;
import com.example.manasfen.model.entyties.User;
import com.example.manasfen.model.payload.TeacherCreate;
import com.example.manasfen.services.users.UsersService;
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
@RequestMapping("/admin/students")
@RequiredArgsConstructor
public class StudentsController {

    private final UsersService usersService;

    @GetMapping()
    public String students(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                           Model model) {
        Page<User> students = usersService.findAllStudentsByPage(page);
        model.addAttribute("students", students);
        return "/admin/students/students";
    }

    @GetMapping("/{studentId}")
    public String student(@PathVariable("studentId") Long studentId,
                          Model model) {
        model.addAttribute("student", usersService.findUserById(studentId));
        return "/admin/students/student-details";
    }

    @PostMapping("/delete/{studentId}")
    public String deleteStudent(@PathVariable("studentId") Long studentId){
        usersService.deleteUserById(studentId);
        return "redirect:/admin/students";
    }
}

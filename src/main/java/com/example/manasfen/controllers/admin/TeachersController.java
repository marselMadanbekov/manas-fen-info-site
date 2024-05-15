package com.example.manasfen.controllers.admin;

import com.example.manasfen.exceptions.InvalidDataFormatException;
import com.example.manasfen.model.entyties.Teacher;
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
@RequestMapping("/admin/teachers")
@RequiredArgsConstructor
public class TeachersController {

    private final UsersService usersService;

    @GetMapping()
    public String teacher(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                       Model model) {
        Page<Teacher> teacher = usersService.findAllTeachersByPage(page);
        model.addAttribute("teachers", teacher);
        return "/admin/teachers/teachers";
    }
    @GetMapping("/{teacherId}")
    public String teacher(@PathVariable("teacherId") Long teacherId,
                       Model model){
        model.addAttribute("teacher", usersService.findTeacherById(teacherId));
        return "/admin/teachers/teacher-details";
    }

    @GetMapping("create")
    public String createNews() {
        return "/admin/teachers/teacher-create";
    }


    @PostMapping("create")
    public String createNews(@Valid @ModelAttribute("teacherCreate") TeacherCreate teacherCreate,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", teacherCreate);

            model.addAttribute("errors", bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage))
            );

            return "/admin/teachers/teacher-create";
        } else {
            try {
                Teacher teacher = usersService.createTeacher(teacherCreate);
                return "redirect:/admin/teachers/%d".formatted(teacher.getId());
            } catch (IOException e) {
                HashMap<String, String> errors = new HashMap<>();
                errors.put("photoIoError", e.getMessage());
                model.addAttribute("errors", errors);
                model.addAttribute("payload", teacherCreate);
                return "/admin/teachers/teacher-create";

            } catch (InvalidDataFormatException e) {
                HashMap<String, String> errors = new HashMap<>();
                errors.put("photoFormatError", e.getMessage());
                model.addAttribute("errors", errors);
                model.addAttribute("payload", teacherCreate);
                return "/admin/teachers/teacher-create";
            }
        }
    }

    @PostMapping("/delete/{teacherId}")
    public String deleteTeacher(@PathVariable("teacherId") Long teacherId){
        usersService.deleteTeacher(teacherId);
        return "redirect:/admin/teachers";
    }
}

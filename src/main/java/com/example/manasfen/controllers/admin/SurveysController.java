package com.example.manasfen.controllers.admin;

import com.example.manasfen.model.entyties.Survey;
import com.example.manasfen.model.entyties.SurveyResult;
import com.example.manasfen.model.entyties.Teacher;
import com.example.manasfen.model.payload.QuestionCreate;
import com.example.manasfen.model.payload.QuestionStatsPair;
import com.example.manasfen.model.payload.SurveyCreate;
import com.example.manasfen.services.surveys.SurveysService;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/surveys")
@RequiredArgsConstructor
public class SurveysController {

    private final SurveysService surveysService;

    private final UsersService usersService;

    @GetMapping
    public String surveys(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                          Model model) {
        Page<Survey> surveys = surveysService.findAllByPage(page);
        model.addAttribute("surveys", surveys);
        return "admin/surveys/surveys";
    }

    @GetMapping("/results/{teacherId}/{surveyId}")
    public String results(@PathVariable("teacherId") Long teacherId,
                          @PathVariable("surveyId") Long surveyId,
                          Model model) {
        Map<String, Integer> stats = surveysService.getSurveyStatisticsByTeacherIdAndSurveyId(teacherId, surveyId);

        Teacher teacher = usersService.findTeacherById(teacherId);
        Survey survey = surveysService.findById(surveyId);
        List<SurveyResult> results = surveysService.findSurveyResultsByTeacherAndSurvey(teacher,survey);
        model.addAttribute("results",results);
        model.addAttribute("teacher", teacher);
        model.addAttribute("survey", survey);
        model.addAttribute("stats", stats);

        return "admin/surveys/survey-stats";

    }

    @GetMapping("/{surveyId}")
    public String surveyDetails(@PathVariable("surveyId") Long surveyId,
                                Model model) {
        Survey survey = surveysService.findById(surveyId);
        List<Teacher> allTeachers = usersService.findAllTeachersNotInSurvey(survey);
        model.addAttribute("survey", survey);
        model.addAttribute("allExistsTeachers", allTeachers);
        return "admin/surveys/survey-details";
    }

    @PostMapping("/create-question")
    public String createQuestion(@Valid QuestionCreate questionCreate,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", questionCreate);
            model.addAttribute("errors", bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage))
            );
            return "admin/surveys/survey-details";
        } else {
            Survey survey = surveysService.createQuestion(questionCreate);
            return "redirect:/admin/surveys/%d".formatted(survey.getId());
        }
    }

    @PostMapping("/add-teachers")
    public String createQuestion(@RequestParam("surveyId") Long surveyId,
                                 @RequestParam("teachers") List<Long> teacherIds) {

        Survey survey = surveysService.addTeachersToSurvey(teacherIds, surveyId);
        return "redirect:/admin/surveys/%d".formatted(survey.getId());
    }

    @PostMapping("/remove-question")
    public String removeQuestion(QuestionCreate questionCreate) {
        Survey survey = surveysService.removeQuestion(questionCreate);
        return "redirect:/admin/surveys/%d".formatted(survey.getId());
    }

    @PostMapping("/remove-teacher")
    public String removeTeacher(@RequestParam("surveyId") Long surveyId,
                                @RequestParam("teacherId") Long teacherId) {
        Survey survey = surveysService.removeTeacher(surveyId, teacherId);
        return "redirect:/admin/surveys/%d".formatted(survey.getId());
    }

    @PostMapping("/create")
    public String surveyCreate(@Valid SurveyCreate surveyCreate,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", surveyCreate);
            model.addAttribute("errors", bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage))
            );

            return "admin/surveys/surveys";
        } else {

            Survey survey = surveysService.create(surveyCreate);

            return "redirect:/admin/surveys/%d".formatted(survey.getId());
        }
    }
}

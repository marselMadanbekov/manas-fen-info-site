package com.example.manasfen.controllers.admin;

import com.example.manasfen.model.entyties.Survey;
import com.example.manasfen.model.entyties.SurveyResult;
import com.example.manasfen.model.entyties.Teacher;
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

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher/surveys")
@RequiredArgsConstructor
public class TeacherOwnController {
    private final SurveysService surveysService;

    private final UsersService usersService;

    @GetMapping
    public String surveys(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                          Principal principal,
                          Model model) {
        Page<Survey> surveys = surveysService.findAllByPageAndTeacherUsername(page,principal.getName());
        model.addAttribute("surveys", surveys);
        return "admin/teacher/surveys/surveys";
    }

    @GetMapping("/results/{surveyId}")
    public String results(@PathVariable("surveyId") Long surveyId,
                          Principal principal,
                          Model model) {
        Map<String, Integer> stats = surveysService.getSurveyStatisticsByTeacherUsernameAndSurveyId(principal.getName(), surveyId);

        Teacher teacher = usersService.findTeacherByUsername(principal.getName());
        Survey survey = surveysService.findById(surveyId);
        Map<String, Double> generalStatByQuestion = new HashMap<>();
        for (String question : survey.getQuestions()) {
            int sum = 0;
            int count = 0;
            for (String statQuestion : stats.keySet()) {
                if (statQuestion.startsWith(question)) {
                    sum += stats.get(statQuestion);
                    count++;
                }
            }
            generalStatByQuestion.put(question, ((double) sum / count));
        }
        List<SurveyResult> results = surveysService.findSurveyResultsByTeacherAndSurvey(teacher,survey);
        model.addAttribute("results",results);
        model.addAttribute("teacher", teacher);
        model.addAttribute("survey", survey);
        model.addAttribute("stats", stats);
        model.addAttribute("mainStat", generalStatByQuestion);

        return "admin/teacher/surveys/survey-stats";

    }
//
//    @GetMapping("/{surveyId}")
//    public String surveyDetails(@PathVariable("surveyId") Long surveyId,
//                                Model model) {
//        Survey survey = surveysService.findById(surveyId);
//        List<Teacher> allTeachers = usersService.findAllTeachersNotInSurvey(survey);
//        model.addAttribute("survey", survey);
//        model.addAttribute("allExistsTeachers", allTeachers);
//        return "admin/surveys/survey-details";
//    }
//
//    @PostMapping("/create-question")
//    public String createQuestion(@Valid QuestionCreate questionCreate,
//                                 BindingResult bindingResult,
//                                 Model model) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("payload", questionCreate);
//            model.addAttribute("errors", bindingResult.getFieldErrors().stream()
//                    .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage))
//            );
//            return "admin/surveys/survey-details";
//        } else {
//            Survey survey = surveysService.createQuestion(questionCreate);
//            return "redirect:/admin/surveys/%d".formatted(survey.getId());
//        }
//    }
//
//    @PostMapping("/add-teachers")
//    public String createQuestion(@RequestParam("surveyId") Long surveyId,
//                                 @RequestParam("teachers") List<Long> teacherIds) {
//
//        Survey survey = surveysService.addTeachersToSurvey(teacherIds, surveyId);
//        return "redirect:/admin/surveys/%d".formatted(survey.getId());
//    }
//
//    @PostMapping("/remove-question")
//    public String removeQuestion(QuestionCreate questionCreate) {
//        Survey survey = surveysService.removeQuestion(questionCreate);
//        return "redirect:/admin/surveys/%d".formatted(survey.getId());
//    }
//
//    @PostMapping("/remove-teacher")
//    public String removeTeacher(@RequestParam("surveyId") Long surveyId,
//                                @RequestParam("teacherId") Long teacherId) {
//        Survey survey = surveysService.removeTeacher(surveyId, teacherId);
//        return "redirect:/admin/surveys/%d".formatted(survey.getId());
//    }
//
//    @PostMapping("/create")
//    public String surveyCreate(@Valid SurveyCreate surveyCreate,
//                               BindingResult bindingResult,
//                               Model model) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("payload", surveyCreate);
//            model.addAttribute("errors", bindingResult.getFieldErrors().stream()
//                    .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage))
//            );
//
//            return "admin/surveys/surveys";
//        } else {
//
//            Survey survey = surveysService.create(surveyCreate);
//
//            return "redirect:/admin/surveys/%d".formatted(survey.getId());
//        }
//    }
}

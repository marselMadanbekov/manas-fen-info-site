package com.example.manasfen.services.surveys;

import com.example.manasfen.model.entyties.Survey;
import com.example.manasfen.model.entyties.SurveyResult;
import com.example.manasfen.model.entyties.Teacher;
import com.example.manasfen.model.entyties.User;
import com.example.manasfen.model.payload.*;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface SurveysService {
    Page<Survey> findAllByPage(Integer page);

    Survey findById(Long surveyId);

    Survey create(SurveyCreate linksCreate);

    void deleteById(Long surveyId);

    Survey createQuestion(QuestionCreate questionCreate);

    Survey removeQuestion(QuestionCreate questionCreate);

    Survey addTeachersToSurvey(List<Long> teacherIds, Long surveyId);

    Survey removeTeacher(Long surveyId, Long teacherId);

    List<Survey> getLastSurveys();

    Page<SurveyStudentStatus> findSurveyStudentStatusByPage(Integer page, User user);

    List<SurveyResult> findSurveyResultsByUserAndSurvey(User user, Survey survey);

    void createSurveyResult(SurveyResultCreate surveyResultCreate,Principal principal);

    Map<String, Integer> getSurveyStatisticsByTeacherIdAndSurveyId(Long teacherId, Long surveyId);

    List<SurveyResult> findSurveyResultsByTeacherAndSurvey(Teacher teacher, Survey survey);

    Page<Survey> findAllByPageAndTeacherUsername(Integer page, String name);

    Map<String, Integer> getSurveyStatisticsByTeacherUsernameAndSurveyId(String name, Long surveyId);
}

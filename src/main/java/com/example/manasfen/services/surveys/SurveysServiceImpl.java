package com.example.manasfen.services.surveys;


import com.example.manasfen.model.entyties.Survey;
import com.example.manasfen.model.entyties.SurveyResult;
import com.example.manasfen.model.entyties.Teacher;
import com.example.manasfen.model.entyties.User;
import com.example.manasfen.model.payload.*;
import com.example.manasfen.reposiroties.SurveyRepository;
import com.example.manasfen.reposiroties.SurveyResultRepository;
import com.example.manasfen.services.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveysServiceImpl implements SurveysService {

    @Value("${custom.parameters.surveys.page-size}")
    private Integer pageSize;
    private final SurveyRepository surveyRepository;
    private final SurveyResultRepository surveyResultRepository;
    private final UsersService usersService;

    @Override
    public Page<Survey> findAllByPage(Integer page) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return surveyRepository.findAll(pageable);
    }

    @Override
    public Survey findById(Long surveyId) {
        return surveyRepository.findById(surveyId).orElseThrow(() -> new NoSuchElementException("Сурамжылоо табылган жок!"));
    }

    @Override
    @Transactional
    public Survey create(SurveyCreate surveyCreate) {
        return surveyRepository.save(
                Survey.builder()
                        .name(surveyCreate.name())
                        .build()
        );
    }

    @Override
    @Transactional
    public void deleteById(Long surveyId) {
        surveyRepository.deleteById(surveyId);
    }

    @Override
    @Transactional
    public Survey createQuestion(QuestionCreate questionCreate) {
        Survey survey = findById(questionCreate.surveyId());
        survey.addQuestion(questionCreate.question());
        return survey;
    }

    @Override
    @Transactional
    public Survey removeQuestion(QuestionCreate questionCreate) {
        Survey survey = findById(questionCreate.surveyId());
        survey.removeQuestion(questionCreate.question());
        return survey;
    }

    @Override
    @Transactional
    public Survey addTeachersToSurvey(List<Long> teacherIds, Long surveyId) {
        Survey survey = findById(surveyId);
        List<Teacher> teachers = usersService.findAllTeachersByIds(teacherIds);

        teachers.forEach(survey::addTeacher);
        return survey;
    }

    @Override
    @Transactional
    public Survey removeTeacher(Long surveyId, Long teacherId) {
        Survey survey = findById(surveyId);
        Teacher teacher = usersService.findTeacherById(teacherId);

        survey.removeTeacher(teacher);
        return survey;
    }

    @Override
    public List<Survey> getLastSurveys() {
        return surveyRepository.findFirst6ByOrderByCreateDateDesc();
    }

    @Override
    public Page<SurveyStudentStatus> findSurveyStudentStatusByPage(Integer page, User user) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Survey> surveys = surveyRepository.findAll(pageable);


        return surveys.map(survey ->
                new SurveyStudentStatus(surveyResultRepository.findByIntervieweeAndSurvey(user, survey).size() == survey.getTargetTeachers().size(), survey)
        );
    }

    @Override
    public List<SurveyResult> findSurveyResultsByUserAndSurvey(User user, Survey survey) {
        return surveyResultRepository.findByIntervieweeAndSurvey(user, survey);
    }

    @Override
    @Transactional
    public void createSurveyResult(SurveyResultCreate surveyResultCreate, Principal principal) {
        Teacher teacher = usersService.findTeacherById(surveyResultCreate.teacherId());
        User user = usersService.findUserByUsername(principal.getName());
        Survey survey = findById(surveyResultCreate.surveyId());
        SurveyResult surveyResult = SurveyResult.builder()
                .interviewee(user)
                .targetTeacher(teacher)
                .survey(survey)
                .marks(surveyResultCreate
                        .answers()
                        .stream()
                        .collect(Collectors.toMap(
                                QuestionAnswerPair::question, QuestionAnswerPair::value))
                ).build();

        surveyResultRepository.save(surveyResult);
    }

    @Override
    public Map<String, Integer> getSurveyStatisticsByTeacherIdAndSurveyId(Long teacherId, Long surveyId) {
        Teacher teacher = usersService.findTeacherById(teacherId);
        Survey survey = findById(surveyId);

        List<SurveyResult> results = findSurveyResultsByTeacherAndSurvey(teacher, survey);


        Map<String, Integer> statsPairs = new HashMap<>();

        for (String question : survey.getQuestions()) {
            for (int i = 1; i < 6; i++) {
                statsPairs.put(question + i, 0);
            }
        }

        for (SurveyResult result : results) {

            for (String question : result.getMarks().keySet()) {

                if (statsPairs.containsKey(question+result.getMarks().get(question))) {
                    statsPairs.put(question+result.getMarks().get(question), statsPairs.get(question+result.getMarks().get(question)) + 1);
                }
            }
        }

        return statsPairs;
    }


    @Override
    public List<SurveyResult> findSurveyResultsByTeacherAndSurvey(Teacher teacher, Survey survey) {
        return surveyResultRepository.findByTargetTeacherAndSurvey(teacher, survey);
    }
}

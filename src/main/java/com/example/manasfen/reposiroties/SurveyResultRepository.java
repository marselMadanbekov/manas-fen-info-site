package com.example.manasfen.reposiroties;

import com.example.manasfen.model.entyties.Survey;
import com.example.manasfen.model.entyties.SurveyResult;
import com.example.manasfen.model.entyties.Teacher;
import com.example.manasfen.model.entyties.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface SurveyResultRepository extends JpaRepository<SurveyResult, Long> {
    List<SurveyResult> findByIntervieweeAndSurvey(User user, Survey survey);

    List<SurveyResult> findByTargetTeacherAndSurvey(Teacher teacher, Survey survey);

    @Modifying
    @Transactional
    @Query("DELETE FROM SurveyResult s " +
            "WHERE s.targetTeacher = :teacher")
    void deleteSurveyResultByTeacher(Teacher teacher);

    @Modifying
    @Transactional
    @Query("DELETE FROM SurveyResult s " +
            "WHERE s.interviewee = :user")
    void deleteSurveyResultByUser(User user);
    @Modifying
    @Transactional
    @Query("DELETE FROM SurveyResult s " +
            "WHERE s.survey = :survey")
    void deleteSurveyResultBySurvey(Survey survey);
}

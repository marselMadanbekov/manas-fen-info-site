package com.example.manasfen.reposiroties;

import com.example.manasfen.model.entyties.Survey;
import com.example.manasfen.model.entyties.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey,Long> {
    List<Survey> findFirst6ByOrderByCreateDateDesc();

    Page<Survey> findByTargetTeachers(Teacher teacher, Pageable pageable);
}

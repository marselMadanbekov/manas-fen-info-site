package com.example.manasfen.reposiroties;

import com.example.manasfen.model.entyties.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    List<Teacher> findFirst3ByOrderByCreateDateDesc();
}

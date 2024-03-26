package com.example.manasfen.reposiroties;

import com.example.manasfen.model.entyties.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    List<Teacher> findFirst3ByOrderByCreateDateDesc();

    @Query("SELECT t from Teacher t where t NOT IN (:targetTeachers)")
    List<Teacher> findAllNotInTargetList(Set<Teacher> targetTeachers);
}

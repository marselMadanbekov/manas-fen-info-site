package com.example.manasfen.services.users;

import com.example.manasfen.exceptions.InvalidDataFormatException;
import com.example.manasfen.model.entyties.Survey;
import com.example.manasfen.model.entyties.Teacher;
import com.example.manasfen.model.entyties.UsefulLink;
import com.example.manasfen.model.entyties.User;
import com.example.manasfen.model.payload.LinkCreate;
import com.example.manasfen.model.payload.TeacherCreate;
import com.example.manasfen.model.payload.UserCreate;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface UsersService {
    Page<Teacher> findAllTeachersByPage(Integer page);

    Teacher findTeacherById(Long teacherId);

    Teacher createTeacher(TeacherCreate linksCreate) throws IOException, InvalidDataFormatException;

    Page<User> findAllStudentsByPage(Integer page);

    User findUserById(Long linksId);

    User createUser(UserCreate linksCreate);

    List<Teacher> getLastTeachers();

    List<Teacher> findAllTeachersNotInSurvey(Survey survey);

    List<Teacher> findAllTeachersByIds(List<Long> teacherIds);

    User findUserByUsername(String name);

    Teacher findTeacherByUserInfo(User user);

    Teacher findTeacherByUsername(String name);

    void deleteTeacher(Long teacherId);

    void deleteUserById(Long studentId);
}

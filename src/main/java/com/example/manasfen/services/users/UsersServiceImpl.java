package com.example.manasfen.services.users;


import com.example.manasfen.exceptions.InvalidDataFormatException;
import com.example.manasfen.model.entyties.Survey;
import com.example.manasfen.model.entyties.SurveyResult;
import com.example.manasfen.model.entyties.Teacher;
import com.example.manasfen.model.entyties.User;
import com.example.manasfen.model.enums.Role;
import com.example.manasfen.model.payload.TeacherCreate;
import com.example.manasfen.model.payload.UserCreate;
import com.example.manasfen.reposiroties.SurveyRepository;
import com.example.manasfen.reposiroties.SurveyResultRepository;
import com.example.manasfen.reposiroties.TeacherRepository;
import com.example.manasfen.reposiroties.UserRepository;
import com.example.manasfen.services.photo.ArticlesPhotoDimensionHolder;
import com.example.manasfen.services.photo.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final SurveyRepository surveyRepository;
    private final SurveyResultRepository surveyResultRepository;
    @Value("${custom.parameters.teachers.page-size}")
    private Integer pageSize;
    private final ArticlesPhotoDimensionHolder articlesPhotoDimensionHolder;

    private final PhotoService photoService;
    private final TeacherRepository teacherRepository;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public Page<Teacher> findAllTeachersByPage(Integer page) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return teacherRepository.findAll(pageable);
    }

    @Override
    public Teacher findTeacherById(Long teacherId) {
        return teacherRepository.findById(teacherId).orElseThrow(() -> new NoSuchElementException("Мугалим табылган жок!"));
    }

    @Override
    @Transactional
    public Teacher createTeacher(TeacherCreate teacherCreate) throws IOException, InvalidDataFormatException {
        String photo = photoService.savePhoto(teacherCreate.photo(), articlesPhotoDimensionHolder);
        User user = userRepository.save(User.builder()
                .firstname(teacherCreate.firstname())
                .lastname(teacherCreate.lastname())
                .username(teacherCreate.username())
                .password(passwordEncoder.encode(teacherCreate.password()))
                .role(Role.ROLE_TEACHER)
                .active(true)
                .build());

        try {
            return teacherRepository.save(
                    Teacher.builder()
                            .userInfo(user)
                            .degree(teacherCreate.degree())
                            .info(teacherCreate.info())
                            .photo(photo)
                            .build()
            );
        } catch (DataIntegrityViolationException e) {
            photoService.deletePhoto(photo);
            throw e;
        }
    }

    @Override
    public Page<User> findAllStudentsByPage(Integer page) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return userRepository.findAllByRole(Role.ROLE_STUDENT, pageable);
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Студент табылган жок!"));
    }

    @Override
    @Transactional
    public User createUser(UserCreate userCreate) {

        return userRepository.save(
                User.builder()
                        .firstname(userCreate.firstname())
                        .lastname(userCreate.lastname())
                        .username(userCreate.username())
                        .password(passwordEncoder.encode(userCreate.password()))
                        .email(userCreate.studNum() + "@manas.edu.kg")
                        .active(true)
                        .role(Role.ROLE_STUDENT)
                        .build());
    }

    @Override
    public List<Teacher> getLastTeachers() {
        return teacherRepository.findFirst3ByOrderByCreateDateDesc();
    }

    @Override
    public List<Teacher> findAllTeachersNotInSurvey(Survey survey) {
        return teacherRepository.findAllNotInTargetList(survey.getTargetTeachers());
    }

    @Override
    public List<Teacher> findAllTeachersByIds(List<Long> teacherIds) {
        return teacherRepository.findAllById(teacherIds);
    }

    @Override
    public User findUserByUsername(String name) {
        return userRepository.findUserByUsername(name).orElseThrow(() -> new NoSuchElementException("Колдонуучу табылган жок!"));
    }

    @Override
    public Teacher findTeacherByUserInfo(User user) {
        return teacherRepository.findByUserInfo(user).orElseThrow(() -> new NoSuchElementException("Мугалим табылган жок!"));
    }

    @Override
    public Teacher findTeacherByUsername(String name) {
        User user = findUserByUsername(name);

        return findTeacherByUserInfo(user);
    }

    @Override
    @Transactional
    public void deleteTeacher(Long teacherId) {
        Teacher teacher = findTeacherById(teacherId);
        List<Survey> surveys = surveyRepository.findAllByTargetTeachers(teacher);
        surveyResultRepository.deleteSurveyResultByTeacher(teacher);
        surveys.forEach(survey -> {
            survey.removeTeacher(teacher);
        });
        teacherRepository.deleteById(teacherId);
    }

    @Transactional
    @Override
    public void deleteUserById(Long studentId) {
        User user = findUserById(studentId);
        surveyResultRepository.deleteSurveyResultByUser(user);
        userRepository.deleteById(studentId);
    }
}

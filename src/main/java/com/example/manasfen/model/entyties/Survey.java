package com.example.manasfen.model.entyties;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Survey extends ParentEntity{
    @Column(nullable = false,unique = true)
    private String name;
    @ElementCollection
    private Set<String> questions = new HashSet<>();
    @ManyToMany
    private Set<Teacher> targetTeachers = new HashSet<>();

    public void addQuestion(String question) {
        this.questions.add(question);
    }

    public void removeQuestion(String question) {
        this.questions.remove(question);
    }

    public void addTeacher(Teacher teacher) {
        this.targetTeachers.add(teacher);
    }

    public void removeTeacher(Teacher teacher) {
        this.targetTeachers.remove(teacher);
    }
}

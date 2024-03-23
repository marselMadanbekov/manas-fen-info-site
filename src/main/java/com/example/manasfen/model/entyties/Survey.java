package com.example.manasfen.model.entyties;

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
    @ElementCollection
    private Set<String> questions = new HashSet<>();
    @ManyToMany
    private Set<Teacher> targetTeachers = new HashSet<>();
}

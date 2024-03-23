package com.example.manasfen.model.entyties;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyResult extends ParentEntity{
    @ManyToOne
    private User interviewee;

    @ManyToOne
    private Teacher targetTeacher;

    @ElementCollection
    private Map<String,Integer> marks;

    @ManyToOne
    private Survey survey;
}

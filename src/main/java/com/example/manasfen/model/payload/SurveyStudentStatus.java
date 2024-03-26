package com.example.manasfen.model.payload;

import com.example.manasfen.model.entyties.Survey;

public record SurveyStudentStatus(
        Boolean isComplete,
        Survey survey
){}

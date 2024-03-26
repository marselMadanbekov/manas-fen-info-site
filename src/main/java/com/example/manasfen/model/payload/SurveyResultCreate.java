package com.example.manasfen.model.payload;

import java.util.List;

public record SurveyResultCreate(
        Long surveyId,
        Long teacherId,
        List<QuestionAnswerPair> answers
) {
}

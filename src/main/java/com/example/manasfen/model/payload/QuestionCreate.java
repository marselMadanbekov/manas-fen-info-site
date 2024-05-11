package com.example.manasfen.model.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record QuestionCreate(
        @NotNull(message = "Сурамжылоонун id-си бош боло албайт!")
        Long surveyId,
        @Size(min = 4,max = 255,message = "Суроонун узундугу {min}-{max} тамганын арасында болуусу зарыл!")
        String question
) {
}

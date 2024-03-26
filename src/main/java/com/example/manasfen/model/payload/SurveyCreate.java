package com.example.manasfen.model.payload;

import jakarta.validation.constraints.Size;

public record SurveyCreate(
        @Size(min = 4, max = 100, message = "Сурамжылоонун аталышы {min}-{max} тамганын арасында болуусу зарыл!")
        String name
) {
}

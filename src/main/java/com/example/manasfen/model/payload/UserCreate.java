package com.example.manasfen.model.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreate(
        @Size(min=3,max = 30, message = "Студенттин аты {min}-{max} тамгадан туруусу зарыл!")
        String firstname,
        @Size(min=3,max = 30, message = "Студенттин фамилиясы {min}-{max} тамгадан туруусу зарыл!")
        String lastname,
        @Size(min=3,max = 30, message = "Студенттин логини {min}-{max} тамгадан туруусу зарыл!")
        String username,
        @Size(min=6,max = 90, message = "Студенттин сыр сөзү {min}-{max} тамгадан туруусу зарыл!")
        String password,
        @NotNull(message = "Студенттин даражасы бош боло албайт!")
        String studNum
) {}

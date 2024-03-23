package com.example.manasfen.model.payload;

import jakarta.validation.constraints.Size;

public record LoginRequest(
        @Size(min=3,max = 30, message = "Логин {min}-{max} тамгадан туруусу зарыл!")
        String username,
        @Size(min=6,max = 90, message = "Сыр сөз {min}-{max} тамгадан туруусу зарыл!")
        String password
) { }

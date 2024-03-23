package com.example.manasfen.model.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record TeacherCreate(
        @Size(min=3,max = 30, message = "Мугалимдин аты {min}-{max} тамгадан туруусу зарыл!")
        String firstname,
        @Size(min=3,max = 30, message = "Мугалимдин фамилиясы {min}-{max} тамгадан туруусу зарыл!")
        String lastname,
        @Size(min=3,max = 30, message = "Мугалимдин логини {min}-{max} тамгадан туруусу зарыл!")
        String username,
        @Size(min=6,max = 90, message = "Мугалимдин сыр сөзү {min}-{max} тамгадан туруусу зарыл!")
        String password,
        @NotNull(message = "Мугалимдин даражасы бош боло албайт!")
        String degree,
        @NotNull(message = "Мугалим жөнүндө маалымат бош боло албайт!")
        String info,

        @NotNull(message = "Мугалимдин сүрөтү бош боло албайт!")
        MultipartFile photo
) {
}

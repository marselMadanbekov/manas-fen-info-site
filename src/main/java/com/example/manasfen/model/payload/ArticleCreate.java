package com.example.manasfen.model.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record ArticleCreate(
        @Size(min = 5, max = 80, message = "Студенттин аты-жөнү {min}-{max} тамгага чейин болуусу зарыл!")
        String fullname,
        String subtitle,

        @Size(min = 20, message = "Студенттин окуясы эн аз {min} тамга болуусу зарыл!")
        String story,

        @NotNull(message = "Студенттин сүрөтү бош боло албайт!")
        MultipartFile photo
) {
}

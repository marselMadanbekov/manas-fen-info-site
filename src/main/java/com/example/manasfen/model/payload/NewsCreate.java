package com.example.manasfen.model.payload;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;


public record NewsCreate(
        @NotNull(message = "Жанылыктын күнү бош боло албайт!")
        String eventDate,

        @Size(min = 5, max = 80, message = "Жанылыктын аталышы {min}-{max} тамгага чейин болуусу зарыл!")
        String title,
        String subtitle,

        @Size(min = 20, message = "Жанылыктын мазмуну эн аз {min} тамга болуусу зарыл!")
        String text,

        @NotNull(message = "Жаңылыктын сүрөтү бош боло албайт!")
        MultipartFile photo
) {
}

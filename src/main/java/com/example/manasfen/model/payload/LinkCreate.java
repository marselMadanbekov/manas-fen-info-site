package com.example.manasfen.model.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record LinkCreate(
        @Size(min = 5, max = 80, message = "Маалыматтын аталышы {min}-{max} тамгага чейин болуусу зарыл!")
        String title,
        String subtitle,

        @Size(min = 20, message = "Маалыматтын мазмуну эн аз {min} тамга болуусу зарыл!")
        String text,

        @NotNull(message = "Маалыматтын сүрөтү бош боло албайт!")
        MultipartFile photo
) {
}

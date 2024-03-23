package com.example.manasfen.services.photo;

import com.example.manasfen.exceptions.InvalidDataFormatException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class PhotoService {
    @Value("${custom.parameters.upload-photo-directory}")
    private String loadPath = "/development/manas-fen/images/";

    public String savePhoto(MultipartFile file, PhotoDimensionHolder photoDimensionHolder) throws IOException, InvalidDataFormatException {
        if (file.isEmpty()) throw new InvalidDataFormatException("Cурот бош боло албайт!");

        BufferedImage image = ImageIO.read(file.getInputStream());
        double ASPECT_RATIO = (double) photoDimensionHolder.getMaxWidth() / photoDimensionHolder.getMaxHeight();
        if (image.getWidth() > photoDimensionHolder.getMaxWidth() || image.getHeight() > photoDimensionHolder.getMaxHeight() ||
                Math.abs((double) image.getWidth() / image.getHeight() - ASPECT_RATIO) > 0.1) {
            throw new InvalidDataFormatException("Сүрөттүн узун туурасы %d x %d болуусу зарыл!".formatted(photoDimensionHolder.getMaxWidth(), photoDimensionHolder.getMaxHeight()));
        }
        String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String filename = UUID.randomUUID() + "_" + originalFilename;
        Path filePath = Paths.get(loadPath, filename);

        File uploadDire = new File(loadPath);
        if (!uploadDire.exists())
            uploadDire.mkdirs();

        Files.copy(file.getInputStream(), filePath);

        return filename;
    }

    public void deletePhoto(String fileName) throws IOException {
        File file = new File(loadPath + fileName);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Файл успешно удален: " + fileName);
            } else {
                System.out.println("Не удалось удалить файл: " + fileName);
            }
        } else {
            System.out.println("Файл не найден: " + fileName);
        }
    }
}

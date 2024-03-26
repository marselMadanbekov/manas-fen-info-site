package com.example.manasfen.services.photo;

import com.example.manasfen.exceptions.InvalidDataFormatException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
//        double ASPECT_RATIO = (double) photoDimensionHolder.getMaxWidth() / photoDimensionHolder.getMaxHeight();
//        if (image.getWidth() > photoDimensionHolder.getMaxWidth() || image.getHeight() > photoDimensionHolder.getMaxHeight() ||
//                Math.abs((double) image.getWidth() / image.getHeight() - ASPECT_RATIO) > 0.1) {
//            throw new InvalidDataFormatException("Сүрөттүн узун туурасы %d x %d болуусу зарыл!".formatted(photoDimensionHolder.getMaxWidth(), photoDimensionHolder.getMaxHeight()));
//        }
        String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String filename = UUID.randomUUID() + "_" + originalFilename;
        String extension = StringUtils.getFilenameExtension(originalFilename);
        Path filePath = Paths.get(loadPath, filename);

        File uploadDire = new File(loadPath);
        if (!uploadDire.exists())
            uploadDire.mkdirs();

        BufferedImage resizedImage = resizeImageWithWhiteBackground(image,photoDimensionHolder);

        ImageIO.write(resizedImage,extension,filePath.toFile());


        return filename;
    }
    private BufferedImage resizeImageWithWhiteBackground(BufferedImage originalImage, PhotoDimensionHolder photoDimensionHolder) throws IOException {
        // Вычисляем соотношение сторон оригинального изображения
        double aspectRatio = (double) originalImage.getWidth() / originalImage.getHeight();

        // Вычисляем новые размеры изображения с сохранением соотношения сторон
        int newWidth;
        int newHeight;
        if (aspectRatio > 1) {
            newWidth = photoDimensionHolder.getMaxWidth();
            newHeight = (int) (newWidth / aspectRatio);
        } else {
            newHeight = photoDimensionHolder.getMaxHeight();
            newWidth = (int) (newHeight * aspectRatio);
        }

        // Создаем новое изображение с новыми размерами
        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        // Создаем фоновое изображение
        BufferedImage resultImage = new BufferedImage(photoDimensionHolder.getMaxWidth(), photoDimensionHolder.getMaxHeight(), BufferedImage.TYPE_INT_RGB);
        g2d = resultImage.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, photoDimensionHolder.getMaxWidth(), photoDimensionHolder.getMaxHeight());

        // Определяем позицию для вставки измененного изображения
        int x = (photoDimensionHolder.getMaxWidth() - newWidth) / 2;
        int y = (photoDimensionHolder.getMaxHeight() - newHeight) / 2;

        // Вставляем измененное изображение на фоновое
        g2d.drawImage(scaledImage, x, y, null);
        g2d.dispose();

        return resultImage;
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

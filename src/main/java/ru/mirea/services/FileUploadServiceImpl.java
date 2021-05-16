package ru.mirea.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private static final String UPLOADED_FOLDER = System.getProperty("user.dir") +
            "/src/main/resources/static/images/";

    @Override
    public String upload(MultipartFile file) {
        String fileName = null;
        if (file.isEmpty())
            return "null";
        File uploadDir = new File(UPLOADED_FOLDER);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }


        //  String fileName = null;
        try {
            fileName = generateFileName(file.getOriginalFilename());

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + fileName);
            file.transferTo(new File(UPLOADED_FOLDER + "/" + fileName));
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileName;

    }

    private String generateFileName(String file) {
        String ext = file.substring(file.lastIndexOf("."));
        return System.currentTimeMillis() + ext;
    }
}
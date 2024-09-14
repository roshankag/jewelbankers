package com.jewelbankers.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private static String uploadDir;

    public static String storeFile(MultipartFile file) throws IOException {
        Path path = Paths.get(uploadDir + File.separator + file.getOriginalFilename());
        Files.copy(file.getInputStream(), path);
        return path.toString();
    }
}


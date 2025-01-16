package com.aivle.fakedetecting.service;

import com.aivle.fakedetecting.entity.Image;
import com.aivle.fakedetecting.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    @Value("${upload.path}")
    private String upload_path;

    public Image uploadImage(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }
        String originalFileName = multipartFile.getOriginalFilename();
        String extension = originalFileName != null && originalFileName.contains(".")
                ? originalFileName.substring(originalFileName.lastIndexOf("."))
                : ""; // 확장자 추출

        // UUID를 사용해 고유한 파일 이름 생성
        String uuidFileName = UUID.randomUUID().toString() + extension;

        // 저장할 파일 경로 설정
        Path path = Paths.get(upload_path + uuidFileName);

        // 디렉토리가 없으면 생성
        Files.createDirectories(path.getParent());

        // 파일을 저장
        Files.copy(multipartFile.getInputStream(), path);
        Image image = new Image();
        image.setName(uuidFileName);
        return imageRepository.save(image);
    }
}

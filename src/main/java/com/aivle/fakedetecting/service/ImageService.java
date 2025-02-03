package com.aivle.fakedetecting.service;

import com.aivle.fakedetecting.entity.Image;
import com.aivle.fakedetecting.repository.ImageRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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
    @Value("${cloud.aws.s3.bucket}")
    private String dataBucketName;
    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String s3BucketName;


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

    public Image uploadImageS3(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String extension = (originalFileName != null && originalFileName.contains("."))
                ? originalFileName.substring(originalFileName.lastIndexOf("."))
                : "";

        // UUID를 사용해 고유한 파일 이름 생성
        String uuidFileName = UUID.randomUUID().toString() + extension;

        InputStream is = multipartFile.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/" + extension);
        metadata.setContentLength(bytes.length);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        try{
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(s3BucketName, uuidFileName, byteArrayInputStream, metadata);
            amazonS3.putObject(putObjectRequest); // put image to S3
        } finally {
            byteArrayInputStream.close();
            is.close();
        }

        String url = amazonS3.getUrl(s3BucketName, uuidFileName).toString();

        // DB 저장
        Image image = new Image();
        image.setName(uuidFileName);
        image.setUrl(url); // 엔티티에 S3 URL 필드 추가 필요

        return imageRepository.save(image);
    }

    public ResponseEntity<byte[]> downloadFile(String filePath) throws IOException {
        try {
            // S3에서 파일 가져오기
            S3Object s3Object = amazonS3.getObject(dataBucketName, filePath);
            InputStream inputStream = s3Object.getObjectContent();

            byte[] content = inputStream.readAllBytes();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePath + "\"");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(content);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

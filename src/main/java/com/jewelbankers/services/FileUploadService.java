package com.jewelbankers.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.jewelbankers.entity.FileUploadResponse;

import java.io.File;

@Service
public class FileUploadService {

    @Value("${file.io.upload.url}")
    private String fileIoUploadUrl;

    @Value("${file.io.auth.token}")
    private String authorizationHeader;
    public FileUploadResponse uploadFile(String filePath) {
        RestTemplate restTemplate = new RestTemplate();
        
        // Prepare file to be uploaded
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }
        System.out.println("File to be upload");
        // Prepare request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", authorizationHeader);

        // Prepare request body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(file));
        
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        System.out.println(fileIoUploadUrl+" "+file.toString());

        // Make the request
        ResponseEntity<FileUploadResponse> responseEntity = restTemplate.exchange(
                fileIoUploadUrl,
                HttpMethod.POST,
                requestEntity,
                FileUploadResponse.class
        );
        System.out.println("File upload called");

        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody().toString());

        // Check if the request was successful
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException("File upload failed with status code: " + responseEntity.getStatusCode());
        }
    }
}


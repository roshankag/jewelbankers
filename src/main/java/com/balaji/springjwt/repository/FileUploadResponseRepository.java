package com.balaji.springjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.balaji.springjwt.models.FileUploadResponse;

@Repository
public interface FileUploadResponseRepository extends JpaRepository<FileUploadResponse, Long> {
    FileUploadResponse findByFileId(String fileId);
}

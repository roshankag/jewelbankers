package com.jewelbankers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelbankers.entity.FileUploadResponse;


@Repository
public interface FileUploadResponseRepository extends JpaRepository<FileUploadResponse, String> {
    FileUploadResponse findByFileId(String fileId);
}

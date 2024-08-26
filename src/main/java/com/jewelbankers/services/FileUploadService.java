package com.jewelbankers.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class FileUploadService {

    @Autowired
    private SettingsService settingsService;

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    // Method to create a Google Drive service instance
    private Drive getDriveService() throws GeneralSecurityException, IOException {
        InputStream in = this.getClass().getResourceAsStream("/credentials.json");
        if (in == null) {
            throw new IOException("Credentials file not found");
        }

        GoogleCredential credential = GoogleCredential.fromStream(in)
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY, credential)
                .setApplicationName("JewelBankers")
                .build();
    }

    // Method to upload a file to Google Drive
    public String uploadFile(MultipartFile file) throws IOException, GeneralSecurityException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // Retrieve the Google Drive folder ID from settings
        String driveFolderId = settingsService.getDriveLink();  // Assuming getDriveLink returns the folder ID

        if (driveFolderId == null || driveFolderId.isEmpty()) {
            throw new IllegalArgumentException("Drive folder ID is not set in settings");
        }

        Drive driveService = getDriveService();

        // Create file metadata
        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());
        fileMetadata.setParents(Collections.singletonList(driveFolderId));  // Set parent folder ID

        // Create the file on Google Drive
        File googleFile = driveService.files().create(fileMetadata,
                new InputStreamContent(file.getContentType(), file.getInputStream()))
                .setFields("id, parents")
                .execute();

        // Return the file ID or a success message
        return "File uploaded successfully to Google Drive with ID: " + googleFile.getId();
    }
}

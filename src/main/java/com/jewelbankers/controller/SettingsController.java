package com.jewelbankers.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jewelbankers.entity.Settings;

import com.jewelbankers.services.SettingsService;
import com.jewelbankers.Utility.ErrorResponse;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/settings")
public class SettingsController {

    @Autowired
    private SettingsService settingsService;
    
//    @Autowired
//    private FileUploadServiceDummy fileUploadService;

    @GetMapping
    public ResponseEntity<?> getSettings() {
        try {
            List<Settings> settingsList = settingsService.getSettings();
            return ResponseEntity.ok(settingsList);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(
                "Error fetching settings",
                e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchSettings(@RequestParam String query) {
        try {
            List<Settings> settingsList = settingsService.searchSettings(query);
            return ResponseEntity.ok(settingsList);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(
                "Error searching settings",
                e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
//    @PostMapping("/upload-photo")
//    public ResponseEntity<?> uploadPhoto(@RequestParam("file") MultipartFile file) {
//        try {
//            if (file.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file uploaded");
//            }
//            
//            String result = fileUploadService.uploadFile(file);
//            return ResponseEntity.ok(result);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
//        }
//    }
    
    @PostMapping("/upload-photo")
    public ResponseEntity<?> uploadCustomerPhoto(@RequestParam("file") MultipartFile file) {
        try {
            // Get the directory path from the settings
            String photoDir = settingsService.getCustomerPhotoDirectory();

            // Ensure the directory exists
            Path directoryPath = Paths.get(photoDir);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // Save the file to the specified directory
            Path filePath = directoryPath.resolve(file.getOriginalFilename());
            Files.write(filePath, file.getBytes());

            return ResponseEntity.ok("Photo uploaded successfully: " + filePath.toString());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading photo: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateAllSettings(@RequestBody List<Settings> settingsList) {
        try {
            List<Settings> updatedSettings = settingsService.updateAllSettings(settingsList);
            return ResponseEntity.ok(updatedSettings);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(
                "Error updating settings",
                e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateSettings(@PathVariable("id") Long paramSeq, @RequestBody Settings inputSettings) {
//        try {
//            Optional<Settings> existingSettings = settingsService.findByParamSeq(paramSeq);
//            
//            if (!existingSettings.isPresent()) {
//                ErrorResponse errorResponse = new ErrorResponse(
//                    "Settings not found",
//                    "Settings with paramSeq " + paramSeq + " not found"
//                );
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
//            }
//
//            Settings settingsSelect = existingSettings.get();
//            
//            if (inputSettings.getParamSeq() != null) settingsSelect.setParamSeq(inputSettings.getParamSeq());
//            if (inputSettings.getParamId() != null) settingsSelect.setParamId(inputSettings.getParamId());
//            if (inputSettings.getParamValue() != null) settingsSelect.setParamValue(inputSettings.getParamValue());
//            if (inputSettings.getParamExample() != null) settingsSelect.setParamExample(inputSettings.getParamExample());
//            
//            Settings updatedSettings = settingsService.save(settingsSelect);
//            return ResponseEntity.ok(updatedSettings);
//        } catch (Exception e) {
//            ErrorResponse errorResponse = new ErrorResponse(
//                "Error updating settings",
//                e.getMessage()
//            );
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
//        }
//    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSettings(@PathVariable("id") Long paramSeq, @RequestBody Settings inputSettings) {
        try {
            // Retrieve existing settings by paramSeq
            Optional<Settings> existingSettings = settingsService.findByParamSeq(paramSeq);

            if (existingSettings.isPresent()) {
                Settings settingsSelect = existingSettings.get();
                
                // Update settings with new values if present
                if (inputSettings.getParamSeq() != null) settingsSelect.setParamSeq(inputSettings.getParamSeq());
                if (inputSettings.getParamId() != null) settingsSelect.setParamId(inputSettings.getParamId());
                if (inputSettings.getParamValue() != null) settingsSelect.setParamValue(inputSettings.getParamValue());
                if (inputSettings.getParamExample() != null) settingsSelect.setParamExample(inputSettings.getParamExample());
                
                // Save updated settings
                Settings updatedSettings = settingsService.save(settingsSelect);
                return ResponseEntity.ok(updatedSettings);
            } else {
                // Handle case where settings are not found
                ErrorResponse errorResponse = new ErrorResponse(
                    "Settings not found",
                    "Settings with paramSeq " + paramSeq + " not found"
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }

        } catch (Exception e) {
            // Handle any exceptions
            ErrorResponse errorResponse = new ErrorResponse(
                "Error updating settings",
                e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}

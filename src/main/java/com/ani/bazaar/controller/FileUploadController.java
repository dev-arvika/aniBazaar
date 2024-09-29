package com.ani.bazaar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ani.bazaar.dto.PhotoResponseDto;
import com.ani.bazaar.entity.UserEntity;
import com.ani.bazaar.exception.UserNotFoundException;
import com.ani.bazaar.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;

@RestController
@RequestMapping("/api")
public class FileUploadController {

	@Value("${upload.dir}")
    private String uploadDir;

	@Value("${upload.max-size}")
    private long maxSize; // Max file size in bytes
	
	@Autowired
	private UserRepository userRepository;
	
    @PostMapping("users/{id}/images/upload")
    public ResponseEntity<String> uploadImage(@PathVariable long id, @RequestParam("file") MultipartFile file) {
    	UserEntity user = userRepository.findById(id);
		if (user == null)
			throw new UserNotFoundException("id: "+id);
		
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        if (!file.getContentType().startsWith("image/")) {
            return ResponseEntity.badRequest().body("File is not an image");
        }
        // Check file size
        if (file.getSize() > maxSize) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .body("File size exceeds the maximum limit of 2 MB");
        }
        try {
            // Ensure the upload directory exists
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String extension = "";
            int lastDotIndex = file.getOriginalFilename().lastIndexOf('.');
            if (lastDotIndex > 0) {
                extension = file.getOriginalFilename().substring(lastDotIndex);
            }
            
            String newFileName = "photo_"+id+extension;
            // Define file path
            Path path = Paths.get(uploadDir, newFileName);

            // Check if file already exists and delete it if necessary
            if (Files.exists(path)) {
                Files.delete(path);
            }

            // Save the file
            Files.copy(file.getInputStream(), path);
            user.setUserPhoto(newFileName);
            user.setModifiedAt(LocalDateTime.now());
    		userRepository.save(user);
            return ResponseEntity.ok("File uploaded successfully: " + newFileName);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File upload failed");
        }
    }

    @GetMapping("/images/files/{filename}")
    public ResponseEntity<Object> getImage(@PathVariable String filename) {
        try {
            Path path = Paths.get(uploadDir, filename);
            byte[] imageBytes = Files.readAllBytes(path);
            
            // Convert byte array to Base64
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            return ResponseEntity.ok().body(new PhotoResponseDto(base64Image));
        } catch (IOException e) {
            return ResponseEntity.status(404).body("Photo Not Found");
        }
    }
}

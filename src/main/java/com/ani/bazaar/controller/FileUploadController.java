package com.ani.bazaar.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ani.bazaar.entity.UserEntity;
import com.ani.bazaar.exception.UserNotFoundException;
import com.ani.bazaar.repository.UserRepository;
import com.ani.bazaar.utils.FileUploadUtil;

@RestController
@RequestMapping("/api")
public class FileUploadController {

	@Value("${upload.dir}")
    private String uploadDir;
	
	@Value("${upload.dir-animal}")
	private String aniUploadDir;

	@Value("${upload.max-size}")
    private long maxSize; // Max file size in bytes
	
	@Autowired
	private UserRepository userRepository;
	
    @PostMapping("users/{id}/images/upload")
    public ResponseEntity<Map<String, String>> uploadImage(@PathVariable long id, @RequestParam("file") MultipartFile file) {
    	UserEntity user = userRepository.findById(id);
		if (user == null)
			throw new UserNotFoundException("id: "+id);
       
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "File is empty"));
        }
        if (!file.getContentType().startsWith("image/")) {
            return ResponseEntity.badRequest().body(Map.of("error","File is not an image"));
        }
        // Check file size
        if (file.getSize() > maxSize) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .body(Map.of("error","File size exceeds the maximum limit of 2 MB"));
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
            
            long currentTimeMillis = System.currentTimeMillis();
            int randomNumber = new Random().nextInt(1000); // random number up to 999
            String uniqueId = currentTimeMillis + "-" + randomNumber;
            
            String newFileName = id+"_"+uniqueId+extension;
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
    		// Construct the URL for the uploaded file
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/user-image/")
                .path(newFileName)
                .toUriString();
            
            return ResponseEntity.ok(Map.of(
            	    "message", "File uploaded successfully",
            	    "userImage", fileDownloadUri
            	));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("error","File upload failed"));
        }
    }

    @GetMapping("/images/files/{filename}")
    public ResponseEntity<Object> getImage(@PathVariable String filename) {
        try {
            Path path = Paths.get(uploadDir, filename);
            byte[] imageBytes = Files.readAllBytes(path);
            
            // Convert byte array to Base64
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            
            return ResponseEntity.ok().body(Map.of("photoFile", base64Image));
        } catch (IOException e) {
            return ResponseEntity.status(404).body("Photo Not Found");
        }
    }
    
    @GetMapping("/user-image/{filename:.+}")
    public ResponseEntity<Resource> getProfileImage(@PathVariable String filename) {
        Path filePath = Paths.get(uploadDir, filename);
        File file = filePath.toFile();

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(file);
        
        // Set content type based on file extension
        String contentType = FileUploadUtil.getContentType(filename);
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    
    @PostMapping("/animal-image/{postid}/upload")
	public ResponseEntity<Map<String, String>> uploadAnimalImage(@RequestParam("image") MultipartFile image) { 

		if (image.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "File is empty"));
        }
        if (!image.getContentType().startsWith("image/")) {
            return ResponseEntity.badRequest().body(Map.of("error","File is not an image"));
        }
        if (image.getSize() > maxSize) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .body(Map.of("error","File size exceeds the maximum limit of 2 MB"));
        }

		try {
			String fileDownloadUri = FileUploadUtil.uploadAnimalImage(image, aniUploadDir, maxSize);
			
			 return ResponseEntity.ok(Map.of(
	            	    "message", "File uploaded successfully",
	            	    "userImage", fileDownloadUri
	            	));
			
		} catch (IOException e) {
			return ResponseEntity.status(500).body(Map.of("error","File upload failed"));
		}
	}
    
    @GetMapping("/animal-image/{filename:.+}")
    public ResponseEntity<Resource> getAnimalImage(@PathVariable String filename) {
        Path filePath = Paths.get(aniUploadDir, filename);
        File file = filePath.toFile();

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(file);
        
        // Set content type based on file extension
        String contentType = FileUploadUtil.getContentType(filename);
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    
}

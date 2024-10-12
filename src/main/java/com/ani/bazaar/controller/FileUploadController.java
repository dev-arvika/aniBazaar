package com.ani.bazaar.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.ani.bazaar.entity.UserEntity;
import com.ani.bazaar.exception.UserNotFoundException;
import com.ani.bazaar.repository.UserRepository;
import com.ani.bazaar.utils.FileUploadUtils;

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
 
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	@PostMapping("users/{id}/images/upload")
	public ResponseEntity<Map<String, String>> uploadImage(@PathVariable long id,
			@RequestParam("file") MultipartFile file) {
		UserEntity user = userRepository.findById(id);
		if (user == null)
			throw new UserNotFoundException("id: " + id);

		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body(Map.of("error", "File is empty"));
		}
		if (!file.getContentType().startsWith("image/")) {
			return ResponseEntity.badRequest().body(Map.of("error", "File is not an image"));
		}
		// Check file size
		if (file.getSize() > maxSize) {
			return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
					.body(Map.of("error", "File size exceeds the maximum limit of 2 MB"));
		}
		try {
			String oldImage = user.getUserImage();
			String uploadedFileName = FileUploadUtils.uploadImage(file, uploadDir, id);
			user.setUserImage(uploadedFileName);
			user.setModifiedAt(LocalDateTime.now());
			userRepository.save(user);
			// Construct the URL for the uploaded file
			String fileDownloadUri = FileUploadUtils.getImageDownloadUri(uploadedFileName, "/api/user-image/");
			deleteImageFile(oldImage, uploadDir);
			return ResponseEntity.ok(Map.of("message", "File uploaded successfully", "userImage", fileDownloadUri));
		} catch (IOException e) {
			return ResponseEntity.status(500).body(Map.of("error", "File upload failed"));
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
		String contentType = FileUploadUtils.getContentType(filename);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@PostMapping("/animal-image/upload")
	public ResponseEntity<Map<String, String>> uploadAnimalImage(@RequestParam("image") MultipartFile image) {

		if (image.isEmpty()) {
			return ResponseEntity.badRequest().body(Map.of("error", "File is empty"));
		}
		if (!image.getContentType().startsWith("image/")) {
			return ResponseEntity.badRequest().body(Map.of("error", "File is not an image"));
		}
		if (image.getSize() > maxSize) {
			return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
					.body(Map.of("error", "File size exceeds the maximum limit of 2 MB"));
		}

		try {
			String uploadedFileName = FileUploadUtils.uploadImage(image, aniUploadDir, 0);
			String fileDownloadUri = FileUploadUtils.getImageDownloadUri(uploadedFileName, "/api/animal-image/");

			return ResponseEntity.ok(Map.of("message", "File uploaded successfully", "animalImage", fileDownloadUri));
		} catch (IOException e) {
			return ResponseEntity.status(500).body(Map.of("error", "File upload failed"));
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
		String contentType = FileUploadUtils.getContentType(filename);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	private void deleteImageFile(String filename, String imageUploadDir) {
        File file = new File(imageUploadDir + File.separator + filename);
        if (file.exists() && file.delete()) {
        	logger.info("Deleted file: {}", filename);
        } else {
        	logger.warn("File does not exist or could not be deleted: {}", filename);
        }
    }
	/*
	 * @GetMapping("/images/files/{filename}") public ResponseEntity<Object>
	 * getImage(@PathVariable String filename) { try { Path path =
	 * Paths.get(uploadDir, filename); byte[] imageBytes = Files.readAllBytes(path);
	 * 
	 * // Convert byte array to Base64 String base64Image =
	 * Base64.getEncoder().encodeToString(imageBytes);
	 * 
	 * return ResponseEntity.ok().body(Map.of("photoFile", base64Image)); } catch
	 * (IOException e) { return ResponseEntity.status(404).body("Photo Not Found");
	 * } }
	 */
}

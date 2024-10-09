package com.ani.bazaar.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class FileUploadUtil {

	public static String uploadAnimalImage(MultipartFile image, String uploadDir, long maxSize) throws IOException {

		try {
			// Ensure the upload directory exists
			File directory = new File(uploadDir);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			long currentTimeMillis = System.currentTimeMillis();
			int randomNumber = new Random().nextInt(1000); // random number up to 999
			String uniqueId = currentTimeMillis + "-" + randomNumber;

			String newFileName = uniqueId + getExtension(image.getOriginalFilename());
			Path filePath = Paths.get(uploadDir + File.separator + newFileName);

			Files.copy(image.getInputStream(), filePath);
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/animal-image/")
					.path(newFileName).toUriString();

			return fileDownloadUri;
		} catch (IOException e) {
			return "File upload failed.";
		}
	}

	// Utility method to get the file extension
	private static String getExtension(String fileName) {
		String extension = "";
		int lastDotIndex = fileName.lastIndexOf('.');
		if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
			extension = fileName.substring(lastDotIndex); // Includes the dot
		}
		return extension;
	}

	public static String getContentType(String filename) {
		if (filename.endsWith(".png")) {
			return "image/png";
		} else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
			return "image/jpeg";
		} else if (filename.endsWith(".gif")) {
			return "image/gif";
		}
		return "application/octet-stream"; // Default content type
	}

}

package com.ani.bazaar.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class FileUploadUtils {

	private static String defaultUserImage;

	public FileUploadUtils(ApplicationContext context) {
		defaultUserImage = context.getEnvironment().getProperty("default.user.image");
	}

	public static String uploadImage(MultipartFile image, String uploadDir, long id) throws IOException {

		// Ensure the upload directory exists
		File directory = new File(uploadDir);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		long currentTimeMillis = System.currentTimeMillis();
		int randomNumber = new Random().nextInt(1000); // random number up to 999
		String uniqueId = currentTimeMillis + "-" + randomNumber;

		String newFileName = id > 0 ? id + "_" + uniqueId + getExtension(image.getOriginalFilename())
				: uniqueId + getExtension(image.getOriginalFilename());
		Path filePath = Paths.get(uploadDir + File.separator + newFileName);

		Files.copy(image.getInputStream(), filePath);

		return newFileName;
	}

	/*
	 * public static String uploadUserImage(MultipartFile file, String uploadDir,
	 * long id) throws IOException {
	 * 
	 * // Ensure the upload directory exists File directory = new File(uploadDir);
	 * if (!directory.exists()) { directory.mkdirs(); } String extension = ""; int
	 * lastDotIndex = file.getOriginalFilename().lastIndexOf('.'); if (lastDotIndex
	 * > 0) { extension = file.getOriginalFilename().substring(lastDotIndex); }
	 * 
	 * long currentTimeMillis = System.currentTimeMillis(); int randomNumber = new
	 * Random().nextInt(1000); // random number up to 999 String uniqueId =
	 * currentTimeMillis + "-" + randomNumber;
	 * 
	 * String newFileName = id + "_" + uniqueId + extension; // Define file path
	 * Path path = Paths.get(uploadDir, newFileName);
	 * 
	 * // Check if file already exists and delete it if necessary if
	 * (Files.exists(path)) { Files.delete(path); }
	 * 
	 * // Save the file Files.copy(file.getInputStream(), path);
	 * 
	 * return newFileName; }
	 */

	/*
	 * public static String uploadAnimalImage(MultipartFile image, String uploadDir,
	 * long maxSize) throws IOException {
	 * 
	 * // Ensure the upload directory exists File directory = new File(uploadDir);
	 * if (!directory.exists()) { directory.mkdirs(); }
	 * 
	 * long currentTimeMillis = System.currentTimeMillis(); int randomNumber = new
	 * Random().nextInt(1000); // random number up to 999 String uniqueId =
	 * currentTimeMillis + "-" + randomNumber;
	 * 
	 * String newFileName = uniqueId + getExtension(image.getOriginalFilename());
	 * Path filePath = Paths.get(uploadDir + File.separator + newFileName);
	 * 
	 * Files.copy(image.getInputStream(), filePath); String fileDownloadUri =
	 * ServletUriComponentsBuilder.fromCurrentContextPath().path(
	 * "/api/animal-image/") .path(newFileName).toUriString();
	 * 
	 * return fileDownloadUri; }
	 */

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

	public static String getImageDownloadUri(String image, String path) {
		return ServletUriComponentsBuilder.fromCurrentContextPath().path(path)
				.path(image != null ? image : defaultUserImage).toUriString();
	}
}

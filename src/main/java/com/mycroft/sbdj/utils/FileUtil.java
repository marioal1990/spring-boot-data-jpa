package com.mycroft.sbdj.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.logging.log4j.util.Strings;
import org.springframework.web.multipart.MultipartFile;

import com.mycroft.sbdj.model.entities.Product;
import com.mycroft.sbdj.model.entities.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {

	/**
	 * Upload file obtains with MultipartFile object and generate ID with UUID
	 * @param file The file to uploads
	 * @return The name of the uploaded file
	 */
	public static String uploadFile(MultipartFile file) {
		String idFile = null;
		try {
			idFile = UUID.randomUUID().toString().concat("_").concat(file.getOriginalFilename());
			Path rootAbsolutePath = getPathByName(idFile);
			Files.copy(file.getInputStream(), rootAbsolutePath.toAbsolutePath());
			return idFile;
		} catch (IOException e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	public static Path getPathByName(String fileName) {
		Path pathPhoto = Paths.get(ConstantsUtil.VARIABLE_NAME_FILES_FOLDER)
				.resolve(fileName).toAbsolutePath();
		return pathPhoto;
	}
	
	public static String getPathByNameString(String fileName) {
		String pathPhoto = Paths.get(ConstantsUtil.VARIABLE_NAME_FILES_FOLDER)
				.resolve(fileName).toAbsolutePath().toString();
		return pathPhoto;
	}
	
	public static void deleteFileIfExist(User user) {
		if (user.getId() != null 
				&& user.getId() > 0 
				&& Strings.isNotBlank(user.getPhoto())) {
			File fileExist = getPathByName(user.getPhoto()).toFile();
			if (fileExist.canRead() && fileExist.exists()) {
				log.warn(ConstantsUtil.MESSAGE_WARNING_FILE_TO_DELETE_EXISTING);
				fileExist.delete();
			}
		}
	}

	public static void deleteFileIfExist(Product product) {
		if (product.getId() != null 
				&& product.getId() > 0 
				&& Strings.isNotBlank(product.getPhoto())) {
			File fileExist = getPathByName(product.getPhoto()).toFile();
			if (fileExist.canRead() && fileExist.exists()) {
				log.warn(ConstantsUtil.MESSAGE_WARNING_FILE_TO_DELETE_EXISTING);
				fileExist.delete();
			}
		}
	}
}

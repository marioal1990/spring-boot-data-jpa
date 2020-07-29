package com.mycroft.sbdj.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import com.mycroft.sbdj.utils.ConstantsUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileServices {

	public void deleteUploadsDirectory() {
		FileSystemUtils.deleteRecursively(Paths.get(ConstantsUtil.VARIABLE_NAME_FILES_FOLDER).toFile());
		log.info(ConstantsUtil.MESSAGE_INFO_DELETE_DIRECTORY_UPLOADS);
	}
	
	public void createUploadsDirectory() throws IOException {
		Files.createDirectories(Paths.get(ConstantsUtil.VARIABLE_NAME_FILES_FOLDER));
		log.info(ConstantsUtil.MESSAGE_INFO_CREATE_DIRECTORY_UPLOADS);
	}
}

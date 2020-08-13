package com.mycroft.sbdj.controllers;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycroft.sbdj.model.entities.User;
import com.mycroft.sbdj.services.UserRepoServices;
import com.mycroft.sbdj.utils.ConstantsUtil;
import com.mycroft.sbdj.utils.FileUtil;
import com.mycroft.sbdj.utils.LabelsProperties;
import com.mycroft.sbdj.utils.MessagesFlashUtil;
import com.mycroft.sbdj.utils.paginator.PageRender;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {

	@Autowired
	private UserRepoServices userRepoServices;

	@Autowired
	private LabelsProperties labelsProperties;
	
	@ModelAttribute("labelsProperties")
	public LabelsProperties getProperties() {
		return this.labelsProperties;
	}
	
	@GetMapping("/users")
	public ResponseEntity<?> getUsers(){
		List<User> users = this.userRepoServices.getUsers();
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/uploads/{filename:.+}")
	public ResponseEntity<Resource> getPhoto(@PathVariable String filename){
		Resource resource = null;
		try {
			Path pathPhoto = Paths.get(ConstantsUtil.VARIABLE_NAME_FILES_FOLDER).resolve(filename).toAbsolutePath();
			resource = new UrlResource(pathPhoto.toUri());
			if (!resource.exists() && !resource.isReadable()) {
				throw new RuntimeException(MessageFormat.format(ConstantsUtil.MESSAGE_DANGER_FILE_DOESNT_LOAD, pathPhoto.toString()));
			}
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"".concat(resource.getFilename()).concat("\""))
					.body(resource);
		} catch (MalformedURLException e) {
			log.error(e.getMessage());
			return new ResponseEntity<Resource>(resource, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(ConstantsUtil.SLASH + ConstantsUtil.PATH_USER_VIEW)
	public String goToView(@RequestParam(name = ConstantsUtil.VARIABLE_NAME_PAGE, defaultValue = "0") int page, Model model) {
		Pageable pageable = PageRequest.of(page, 5);
		Page<User> usersPage = this.userRepoServices.getUsers(pageable);
		PageRender<User> pgUsers = new PageRender<>(ConstantsUtil.SLASH + ConstantsUtil.PATH_USER_VIEW, usersPage);
		model.addAttribute(ConstantsUtil.VARIABLE_NAME_USERS, usersPage);
		model.addAttribute(ConstantsUtil.VARIABLE_NAME_PAGE, pgUsers);
		return ConstantsUtil.PATH_USER_VIEW;
	}
	
	@GetMapping(ConstantsUtil.SLASH + ConstantsUtil.PATH_USER_CREATE)
	public String goToCreate(Model model) {
		User user = new User();
		model.addAttribute(ConstantsUtil.VARIABLE_NAME_USER, user);
		return ConstantsUtil.PATH_USER_CREATE;
	}
	
	@GetMapping(ConstantsUtil.SLASH + ConstantsUtil.PATH_USER_EDIT)
	public String goToEdit(@PathVariable(ConstantsUtil.VARIABLE_NAME_ID) Long id, RedirectAttributes redirect, Model model) {
		if (id > 0) {
			Optional<User> optionalUser = this.userRepoServices.findById(id);
			if (!optionalUser.isPresent()) {
				MessagesFlashUtil.messageError(redirect, ConstantsUtil.MESSAGE_DANGER_USER_DOESNT_EXIST);
				return ConstantsUtil.METHOD_REDIRECT
						.concat(ConstantsUtil.SLASH)
						.concat(ConstantsUtil.PATH_USER_VIEW);
			}
			model.addAttribute(ConstantsUtil.VARIABLE_NAME_USER, optionalUser.get());
			return ConstantsUtil.SLASH.concat(ConstantsUtil.PATH_USER_CREATE);
		} else {
			MessagesFlashUtil.messageError(redirect, ConstantsUtil.MESSAGE_DANGER_ID_DOESNT_BE_ZERO);
			return ConstantsUtil.METHOD_REDIRECT
					.concat(ConstantsUtil.SLASH)
					.concat(ConstantsUtil.PATH_USER_VIEW);
		}
	}
	
	@GetMapping(ConstantsUtil.SLASH + ConstantsUtil.PATH_USER_PROFILE_ID)
	public String goToProfile(@PathVariable(ConstantsUtil.VARIABLE_NAME_ID) Long id, 
			RedirectAttributes redirect, Model model) {
		if (id > 0) {
			Optional<User> optionalUser = this.userRepoServices.findById(id);
			if (!optionalUser.isPresent()) {
				MessagesFlashUtil.messageError(redirect, ConstantsUtil.MESSAGE_DANGER_USER_DOESNT_EXIST);
				return ConstantsUtil.METHOD_REDIRECT.concat(ConstantsUtil.SLASH).concat(ConstantsUtil.PATH_USER_VIEW);
			}
			User user = optionalUser.get();
			model.addAttribute("titleProfileUser", MessageFormat.format(ConstantsUtil.TITLE_PROFILE_USER, 
					user.getName(), 
					user.getLastname()));
			if (Strings.isNotBlank(user.getPhoto())) {
				Boolean isPhotoExist = Files.exists(
						Paths.get(Paths.get(ConstantsUtil.VARIABLE_NAME_FILES_FOLDER).toAbsolutePath().toUri()));
				model.addAttribute(ConstantsUtil.VARIABLE_IS_PHOTO_EXIST, isPhotoExist);
			} else {				
				model.addAttribute(ConstantsUtil.VARIABLE_IS_PHOTO_EXIST, false);
			}
			if (user.getBills().isEmpty()) {
				model.addAttribute(ConstantsUtil.VARIABLE_NAME_MESSAGE_BILL_IS_EMPTY, ConstantsUtil.MESSAGE_WARNING_BILL_IS_EMPTY);
			}
			model.addAttribute(ConstantsUtil.VARIABLE_NAME_MESSAGE_FILE_DOESNT_EXIST, ConstantsUtil.MESSAGE_WARNING_FILE_DOESNT_EXIST);
			model.addAttribute(ConstantsUtil.VARIABLE_NAME_USER, user);
			return ConstantsUtil.SLASH.concat(ConstantsUtil.PATH_USER_PROFILE);
		} else {
			MessagesFlashUtil.messageError(redirect, ConstantsUtil.MESSAGE_DANGER_ID_DOESNT_BE_ZERO);
			return ConstantsUtil.METHOD_REDIRECT.concat(ConstantsUtil.SLASH).concat(ConstantsUtil.PATH_USER_VIEW);
		}
	}
	
	@GetMapping(ConstantsUtil.SLASH + ConstantsUtil.PATH_USER_DELETE)
	public String delete(@PathVariable(ConstantsUtil.VARIABLE_NAME_ID) Long id, 
			RedirectAttributes redirect, Model model) {
		if (id > 0) {
			Optional<User> optionalUser = this.userRepoServices.findById(id);
			if (optionalUser.isPresent()) {
				User user = optionalUser.get();
				this.deleteFile(redirect, user.getPhoto());
				this.userRepoServices.delete(id);
				MessagesFlashUtil.messageSuccess(redirect, ConstantsUtil.MESSAGE_SUCCESS_USER_DELETE);
			} else {
				MessagesFlashUtil.messageError(redirect, ConstantsUtil.MESSAGE_DANGER_USER_DOESNT_EXIST);	
			}
		} else {
			MessagesFlashUtil.messageError(redirect, ConstantsUtil.MESSAGE_DANGER_ID_DOESNT_BE_ZERO);
		}
		return ConstantsUtil.METHOD_REDIRECT.concat(ConstantsUtil.SLASH).concat(ConstantsUtil.PATH_USER_VIEW);
	}

	private void deleteFile(RedirectAttributes redirect, String photo) {
		File filePhoto = FileUtil.getPathByName(photo).toFile();
		if (filePhoto.exists() && filePhoto.isFile() && filePhoto.canRead()) {
			filePhoto.delete();
			MessagesFlashUtil.messageSuccess(redirect, ConstantsUtil.MESSAGE_SUCCESS_PHOTO_DELETE);
		}
	}
	
	@PostMapping(ConstantsUtil.SLASH + ConstantsUtil.PATH_USER_CREATE)
	public String save(@Valid User user, BindingResult result, Model model, 
			@RequestParam(ConstantsUtil.VARIABLE_NAME_FILE) MultipartFile file,
			RedirectAttributes redirect, 
			SessionStatus status) {
		if (result.hasErrors()) {
			return ConstantsUtil.SLASH.concat(ConstantsUtil.PATH_USER_CREATE);
		}
		if (!file.isEmpty()) {
			FileUtil.deleteFileIfExist(user);
			String fileName = FileUtil.uploadFile(file);
			if (Strings.isBlank(fileName)) {
				MessagesFlashUtil.messageError(redirect, ConstantsUtil.MESSAGE_DANGER_UPLOAD_ERROR);
				return ConstantsUtil.METHOD_REDIRECT.concat(ConstantsUtil.PATH_VIEW);
			}
			user.setPhoto(fileName);
			MessagesFlashUtil.messageSuccess(redirect, MessageFormat.format(ConstantsUtil.MESSAGE_INFO_UPLOAD, fileName));
		}
		String message = (user.getId() != null) ? 
				MessageFormat.format(ConstantsUtil.MESSAGE_SUCCESS_USER_EDIT, user.getName(), user.getLastname()) : 
					MessageFormat.format(ConstantsUtil.MESSAGE_SUCCESS_USER_CREATE, user.getName(), user.getLastname());
		this.userRepoServices.save(user);
		status.setComplete();
		MessagesFlashUtil.messageSuccess(redirect, message);
		return ConstantsUtil.METHOD_REDIRECT.concat(ConstantsUtil.PATH_VIEW);
	}
}

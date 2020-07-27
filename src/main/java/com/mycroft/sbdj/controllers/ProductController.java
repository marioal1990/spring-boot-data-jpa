package com.mycroft.sbdj.controllers;

import java.io.File;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycroft.sbdj.model.entities.Product;
import com.mycroft.sbdj.services.ProductServices;
import com.mycroft.sbdj.utils.ConstantsUtil;
import com.mycroft.sbdj.utils.FileUtil;
import com.mycroft.sbdj.utils.LabelsProperties;
import com.mycroft.sbdj.utils.paginator.PageRender;

@Controller
@SessionAttributes("product")
public class ProductController {

	@Autowired
	private ProductServices services;

	@Autowired
	private LabelsProperties labelsProperties;
	
	@ModelAttribute("labelsProperties")
	public LabelsProperties getProperties() {
		return this.labelsProperties;
	}
	
	@GetMapping(ConstantsUtil.SLASH + ConstantsUtil.PATH_PRODUCT_VIEW)
	public String goToView(@RequestParam(name = ConstantsUtil.VARIABLE_NAME_PAGE, 
			defaultValue = "0") int page, Model model, Locale locale) {
		locale = new Locale("es","CL");
		Pageable pageable = PageRequest.of(page, 2);
		Page<Product> productsPage = this.services.getProducts(pageable);
		PageRender<Product> pgProducts = new PageRender<>(ConstantsUtil.SLASH + ConstantsUtil.PATH_PRODUCT_VIEW, productsPage);
		model.addAttribute(ConstantsUtil.VARIABLE_NAME_PRODUCTS, productsPage);
		model.addAttribute(ConstantsUtil.VARIABLE_NAME_PAGE, pgProducts);
		return ConstantsUtil.PATH_PRODUCT_VIEW;
	}
	
	@GetMapping(ConstantsUtil.SLASH + ConstantsUtil.PATH_PRODUCT_CREATE)
	public String goToCreate(Model model) {
		Product product = new Product();
		model.addAttribute(ConstantsUtil.VARIABLE_NAME_PRODUCT, product);
		return ConstantsUtil.PATH_PRODUCT_CREATE;
	}
	
	@GetMapping(ConstantsUtil.SLASH + ConstantsUtil.PATH_PRODUCT_EDIT)
	public String goToEdit(@PathVariable(ConstantsUtil.VARIABLE_NAME_ID) Long id, 
			RedirectAttributes redirect, Model model) {
		if (id > 0) {
			Optional<Product> optionalProduct = this.services.getProduct(id);
			if (!optionalProduct.isPresent()) {
				redirect.addFlashAttribute(ConstantsUtil.VARIABLE_NAME_ERROR, 
						ConstantsUtil.MESSAGE_DANGER_PRODUCT_DOESNT_EXIST);
				return ConstantsUtil.METHOD_REDIRECT
						.concat(ConstantsUtil.SLASH)
						.concat(ConstantsUtil.PATH_PRODUCT_VIEW);
			}
			model.addAttribute(ConstantsUtil.VARIABLE_NAME_PRODUCT, optionalProduct.get());
			return ConstantsUtil.SLASH.concat(ConstantsUtil.PATH_PRODUCT_CREATE);
		} else {
			redirect.addFlashAttribute(ConstantsUtil.VARIABLE_NAME_ERROR, 
					ConstantsUtil.MESSAGE_DANGER_ID_DOESNT_BE_ZERO);
			return ConstantsUtil.METHOD_REDIRECT
					.concat(ConstantsUtil.SLASH)
					.concat(ConstantsUtil.PATH_PRODUCT_VIEW);
		}
	}
	
	@GetMapping(ConstantsUtil.SLASH + ConstantsUtil.PATH_PRODUCT_DELETE)
	public String delete(@PathVariable(ConstantsUtil.VARIABLE_NAME_ID) Long id, 
			RedirectAttributes redirect, Model model) {
		if (id > 0) {
			Optional<Product> optionalProduct = this.services.getProduct(id);
			if (optionalProduct.isPresent()) {
				Product product = optionalProduct.get();
				File filePhoto = FileUtil.getPathByName(product.getPhoto()).toFile();
				if (filePhoto.exists() && filePhoto.canRead()) {
					if (filePhoto.delete()) {
						this.services.delete(id);
						redirect.addFlashAttribute(ConstantsUtil.VARIABLE_NAME_SUCCESS, 
								ConstantsUtil.MESSAGE_SUCCESS_PRODUCT_DELETE);
					} else {
						redirect.addFlashAttribute(ConstantsUtil.VARIABLE_NAME_ERROR, 
								MessageFormat.format(ConstantsUtil.MESSAGE_DANGER_FILE_DOESNT_DELETE, 
										product.getPhoto()));
					}
				} else {
					redirect.addFlashAttribute(ConstantsUtil.VARIABLE_NAME_ERROR, ConstantsUtil.MESSAGE_DANGER_FILE_DOESNT_EXIST);
				}
			} else {
				redirect.addFlashAttribute(ConstantsUtil.VARIABLE_NAME_ERROR, ConstantsUtil.MESSAGE_DANGER_PRODUCT_DOESNT_EXIST);		
			}
		} else {
			redirect.addFlashAttribute(ConstantsUtil.VARIABLE_NAME_ERROR, ConstantsUtil.MESSAGE_DANGER_ID_DOESNT_BE_ZERO);
		}
		return ConstantsUtil.METHOD_REDIRECT
				.concat(ConstantsUtil.SLASH)
				.concat(ConstantsUtil.PATH_PRODUCT_VIEW);
	}
	
	@PostMapping(ConstantsUtil.SLASH + ConstantsUtil.PATH_PRODUCT_CREATE)
	public String save(@Valid Product product, 
			BindingResult result, 
			Model model, 
			@RequestParam(ConstantsUtil.VARIABLE_NAME_FILE) MultipartFile file,
			RedirectAttributes redirect, 
			SessionStatus status) {
		if (result.hasErrors()) {
			return ConstantsUtil.SLASH.concat(ConstantsUtil.PATH_PRODUCT_CREATE);
		}
		if (!file.isEmpty()) {
			FileUtil.deleteFileIfExist(product);
			String fileName = FileUtil.uploadFile(file);
			if (Strings.isBlank(fileName)) {
				redirect.addFlashAttribute(ConstantsUtil.VARIABLE_NAME_ERROR, 
						ConstantsUtil.MESSAGE_DANGER_UPLOAD_ERROR);
				return ConstantsUtil.METHOD_REDIRECT.concat("view");
			}
			product.setPhoto(fileName);
			redirect.addFlashAttribute(ConstantsUtil.VARIABLE_NAME_INFO, 
					MessageFormat.format(ConstantsUtil.MESSAGE_INFO_UPLOAD, fileName));
		}
		String message = (product.getId() != null) ? 
				MessageFormat.format(ConstantsUtil.MESSAGE_SUCCESS_PRODUCT_EDIT, product.getName()) : 
					MessageFormat.format(ConstantsUtil.MESSAGE_SUCCESS_PRODUCT_CREATE, product.getName());
		this.services.save(product);
		status.setComplete();
		redirect.addFlashAttribute(ConstantsUtil.VARIABLE_NAME_SUCCESS, message);
		return ConstantsUtil.METHOD_REDIRECT.concat("view");
	}
}

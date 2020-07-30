package com.mycroft.sbdj.controllers;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycroft.sbdj.editors.ProductPropertyEditor;
import com.mycroft.sbdj.model.entities.Bill;
import com.mycroft.sbdj.model.entities.BillDetail;
import com.mycroft.sbdj.model.entities.Product;
import com.mycroft.sbdj.model.entities.User;
import com.mycroft.sbdj.services.BillServices;
import com.mycroft.sbdj.services.ProductServices;
import com.mycroft.sbdj.services.UserRepoServices;
import com.mycroft.sbdj.utils.ConstantsUtil;
import com.mycroft.sbdj.utils.LabelsProperties;

@Controller
@SessionAttributes("bill")
public class BillController {

	@Autowired
	private ProductServices productServices;
	
	@Autowired
	private UserRepoServices userServices;
	
	@Autowired
	private BillServices services;
	
	@Autowired
	private ProductPropertyEditor productPropertyEditor;

	@Autowired
	private LabelsProperties labelsProperties;
	
	@ModelAttribute("labelsProperties")
	public LabelsProperties getProperties() {
		return this.labelsProperties;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Product.class, "product", productPropertyEditor);
	}
	
	@GetMapping(ConstantsUtil.SLASH + ConstantsUtil.PATH_BILL_DETAIL_VIEW)
	public String goToView(@PathVariable(ConstantsUtil.VARIABLE_NAME_ID) Long id, 
			RedirectAttributes redirect, Model model) {
		if (id > 0) {
			Optional<Bill> optionalBill = this.services.findById(id);
			if (!optionalBill.isPresent()) {
				redirect.addFlashAttribute(ConstantsUtil.VARIABLE_NAME_ERROR, 
						ConstantsUtil.MESSAGE_DANGER_BILL_DOESNT_EXIST);
				return ConstantsUtil.METHOD_REDIRECT
						.concat(ConstantsUtil.SLASH)
						.concat(ConstantsUtil.PATH_BILL_VIEW);
			}
			Bill bill = optionalBill.get();
			if (bill.getBillDetails().isEmpty()) {
				redirect.addFlashAttribute(ConstantsUtil.VARIABLE_NAME_ERROR, 
						ConstantsUtil.MESSAGE_DANGER_BILL_DETAILS_EMPTY);
				return ConstantsUtil.METHOD_REDIRECT
						.concat(ConstantsUtil.SLASH)
						.concat(ConstantsUtil.PATH_BILL_VIEW);
			}
			model.addAttribute(ConstantsUtil.VARIABLE_NAME_BILL_DETAILS, bill.getBillDetails());
			model.addAttribute(ConstantsUtil.VARIABLE_NAME_BILL, bill);
		} else {
			redirect.addFlashAttribute(ConstantsUtil.VARIABLE_NAME_ERROR, 
					ConstantsUtil.MESSAGE_DANGER_ID_DOESNT_BE_ZERO);
			return ConstantsUtil.METHOD_REDIRECT
					.concat(ConstantsUtil.SLASH)
					.concat(ConstantsUtil.PATH_BILL_VIEW);
		}
		return ConstantsUtil.PATH_BILL_VIEW;
	}
	
	@GetMapping(ConstantsUtil.SLASH + ConstantsUtil.PATH_BILL_CREATE_ID)
	public String goToCreate(@PathVariable(ConstantsUtil.VARIABLE_NAME_ID) Long id, 
			RedirectAttributes redirect, Model model) {
		if (id > 0) {			
			Optional<User> optionalUser = this.userServices.findById(id);
			if (optionalUser.isPresent()) {
				User user = optionalUser.get();
				List<Product> products = this.productServices.getProducts();
				Bill bill = new Bill();
				bill.setUser(user);
				model.addAttribute(ConstantsUtil.VARIABLE_NAME_PRODUCTS, products);
				model.addAttribute(ConstantsUtil.VARIABLE_NAME_BILL, bill);
			} else {
				redirect.addFlashAttribute(ConstantsUtil.VARIABLE_NAME_ERROR, 
						ConstantsUtil.MESSAGE_DANGER_USER_DOESNT_EXIST);
				return ConstantsUtil.METHOD_REDIRECT
						.concat(ConstantsUtil.SLASH)
						.concat(ConstantsUtil.PATH_BILL_VIEW);
			}
		} else {
			redirect.addFlashAttribute(ConstantsUtil.VARIABLE_NAME_ERROR, 
					ConstantsUtil.MESSAGE_DANGER_ID_DOESNT_BE_ZERO);
			return ConstantsUtil.METHOD_REDIRECT
					.concat(ConstantsUtil.SLASH)
					.concat(ConstantsUtil.PATH_BILL_VIEW);
		}
		return ConstantsUtil.PATH_BILL_CREATE;
	}
	
	@PostMapping(ConstantsUtil.SLASH + ConstantsUtil.PATH_BILL_CREATE)
	public String create(Bill bill, 
			@RequestParam(name = "products[]", required = false) String[] products, 
			@RequestParam(name = "quantities[]", required = false) String[] quantities, 
			RedirectAttributes redirect, 
			SessionStatus status) {
		for (int x = 0; x < products.length; x++) {
			Long id = Long.valueOf(products[x]);
			Integer qnty = Integer.parseInt(quantities[x]);
			if (id > 0) {
				Optional<Product> optionalProduct = this.productServices.findById(id);
				if (optionalProduct.isPresent()) {
					Product product = optionalProduct.get();
					Integer calculateStockWithQuantity = (product.getStock() - qnty);
					if (calculateStockWithQuantity >= 0) {
						BillDetail billDetail = new BillDetail();
						product.setStock(calculateStockWithQuantity);
						billDetail.setProduct(product);
						billDetail.setQuantity(qnty);
						bill.addBillDetails(billDetail);
					} else {
						String message = MessageFormat.format(ConstantsUtil.MESSAGE_DANGER_PRODUCT_NO_STOCK, 
								product.getName(), 
								product.getStock(), 
								qnty);
						redirect.addFlashAttribute(ConstantsUtil.VARIABLE_NAME_ERROR, message);
						return ConstantsUtil.METHOD_REDIRECT
								.concat(ConstantsUtil.SLASH)
								.concat(ConstantsUtil.PATH_BILL_VIEW);
					}
				}
			} else {
				redirect.addFlashAttribute(ConstantsUtil.VARIABLE_NAME_ERROR, 
						ConstantsUtil.MESSAGE_DANGER_ID_DOESNT_BE_ZERO);
				return ConstantsUtil.METHOD_REDIRECT
						.concat(ConstantsUtil.SLASH)
						.concat(ConstantsUtil.PATH_BILL_VIEW);
			}
		}
		this.services.save(bill);
		status.setComplete();
		redirect.addFlashAttribute(ConstantsUtil.VARIABLE_NAME_SUCCESS, 
				MessageFormat.format(ConstantsUtil.MESSAGE_SUCCESS_BILL_CREATE, bill.getId()));
		return ConstantsUtil.METHOD_REDIRECT
				.concat(ConstantsUtil.PATH_VIEW)
				.concat(ConstantsUtil.SLASH)
				.concat(bill.getId().toString());
	}
	
	@GetMapping(ConstantsUtil.SLASH + ConstantsUtil.PATH_BILL_DELETE)
	public String delete(@PathVariable(ConstantsUtil.VARIABLE_NAME_ID) Long id, 
			RedirectAttributes redirect, Model model) {
		User user = null;
		if (id > 0) {
			Optional<Bill> optionalBill = this.services.findById(id);
			if (optionalBill.isPresent()) {
				Bill bill = optionalBill.get();
				user = bill.getUser();
				this.services.delete(bill.getId());
			} else {
				redirect.addFlashAttribute(ConstantsUtil.VARIABLE_NAME_ERROR, ConstantsUtil.MESSAGE_DANGER_BILL_DOESNT_EXIST);		
			}
		} else {
			redirect.addFlashAttribute(ConstantsUtil.VARIABLE_NAME_ERROR, ConstantsUtil.MESSAGE_DANGER_ID_DOESNT_BE_ZERO);
		}
		return ConstantsUtil.METHOD_REDIRECT
				.concat(ConstantsUtil.SLASH)
				.concat(ConstantsUtil.PATH_USER_PROFILE)
				.concat(ConstantsUtil.SLASH)
				.concat(user.getId().toString());
	}
	
	@GetMapping(value = ConstantsUtil.PATH_BILL_PRODUCT_TERM, produces = {ConstantsUtil.APP_JSON})
	public @ResponseBody List<Product> autoComplete(@PathVariable(ConstantsUtil.VARIABLE_NAME_TERM) String term) {
		return this.productServices.findByName(term);
	}
}

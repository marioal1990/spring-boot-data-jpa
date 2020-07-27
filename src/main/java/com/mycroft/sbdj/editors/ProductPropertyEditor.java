package com.mycroft.sbdj.editors;

import java.beans.PropertyEditorSupport;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycroft.sbdj.model.entities.Product;
import com.mycroft.sbdj.services.ProductServices;
import com.mycroft.sbdj.utils.ConstantsUtil;
import com.mycroft.sbdj.utils.ValidateUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProductPropertyEditor extends PropertyEditorSupport {

	@Autowired
	private ProductServices services;

	@Override
	public void setAsText(String id) throws IllegalArgumentException {
		if (ValidateUtil.isNumberValidate(id)) {
			try {				
				Long numberId = Long.valueOf(id);
				Optional<Product> optionalProduct = this.services.getProduct(numberId);
				if (optionalProduct.isPresent()) {					
					this.setValue(optionalProduct.get());
				} else {
					log.error(ConstantsUtil.MESSAGE_DANGER_PRODUCT_DOESNT_EXIST);
					this.setValue(null);
				}
			} catch (NumberFormatException e) {
				log.error(e.getMessage());
				this.setValue(null);
			}
		} else {			
			log.error(ConstantsUtil.MESSAGE_DANGER_ID_DOESNT_BE_ZERO);
			this.setValue(null);
		}
	}
}

package com.mycroft.sbdj.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycroft.sbdj.model.entities.Product;
import com.mycroft.sbdj.repositories.ProductRepository;
import com.mycroft.sbdj.utils.ConstantsUtil;

@Service
public class ProductServices {

	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public List<Product> getProducts(){
		return (List<Product>) this.repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Product> findById(Long id){
		return this.repository.findById(id);
	}
	
	@Transactional(readOnly = true)
	public List<Product> findByName(String name){
		name = ConstantsUtil.PORCENT.
				concat(name).
				concat(ConstantsUtil.PORCENT);
		return (List<Product>) this.repository.findByNameLikeIgnoreCase(name);
	}
	
	@Transactional(readOnly = true)
	public Page<Product> getProducts(Pageable pageable){
		return this.repository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Optional<Product> getProduct(Long id){
		return this.repository.findById(id);
	}
	
	@Transactional
	public Product save(Product product) {
		if (product.getId() == null || product.getId() == 0) {
			product.setCreated(new Date());
			product.setModified(new Date());
		} else {
			product.setModified(new Date());			
		}
		return this.repository.save(product);
	}
	
	@Transactional
	public void delete(Long id) {
		this.repository.deleteById(id);
	}
}

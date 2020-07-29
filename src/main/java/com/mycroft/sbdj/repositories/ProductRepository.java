package com.mycroft.sbdj.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.mycroft.sbdj.model.entities.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

	@Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
	List<Product> findByName(String name);
	
	List<Product> findByNameLikeIgnoreCase(String name);
}

package com.mycroft.sbdj.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycroft.sbdj.model.entities.Bill;
import com.mycroft.sbdj.repositories.BillRepository;

@Service
public class BillServices {

	@Autowired
	private BillRepository repository;
	
	@Transactional(readOnly = true)
	public List<Bill> getBills(){
		return (List<Bill>) this.repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Page<Bill> getBills(Pageable pageable){
		return this.repository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Optional<Bill> findById(Long id){
		return this.repository.findById(id);
	}
	
	@Transactional
	public Bill save(Bill bill) {
		bill.setCreated(new Date());
		return this.repository.save(bill);
	}
	
	@Transactional
	public void delete(Long id) {
		this.repository.deleteById(id);
	}
}

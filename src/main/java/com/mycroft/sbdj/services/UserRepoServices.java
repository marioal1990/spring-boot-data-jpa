package com.mycroft.sbdj.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mycroft.sbdj.model.entities.User;
import com.mycroft.sbdj.repositories.UserRepository;

@Service
public class UserRepoServices {

	@Autowired
	private UserRepository repository;
	
	@Transactional(readOnly = true)
	public List<User> getUsers(){
		return (List<User>) this.repository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Page<User> getUsers(Pageable pageable){
		return this.repository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Optional<User> findById(Long id) {
		return this.repository.findById(id);
	}
	
	@Transactional
	public User save(User user){
		if (user.getId() == null || user.getId() == 0) {			
			user.setCreated(new Date());
			user.setModified(new Date());
		} else {			
			user.setModified(new Date());
		}
		return this.repository.save(user);
	}
	
	@Transactional
	public void delete(Long id) {
		this.repository.deleteById(id);
	}
}

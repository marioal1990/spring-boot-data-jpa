package com.mycroft.sbdj.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycroft.sbdj.model.daos.UserDAO;
import com.mycroft.sbdj.model.entities.User;

@Service
public class UserServices {

	@Autowired
	private UserDAO dao;
	
	public List<User> getUsers(){
		return this.dao.getUsers();
	}
	
	public User findById(Long id) {
		return this.dao.findById(id);
	}
	
	public void save(User user){
		this.dao.save(user);
	}
	
	public void delete(Long id) {
		this.dao.delete(id);
	}
}

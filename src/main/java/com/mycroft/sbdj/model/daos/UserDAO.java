package com.mycroft.sbdj.model.daos;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mycroft.sbdj.model.entities.User;
import com.mycroft.sbdj.utils.ConstantsUtil;

@Repository
public class UserDAO {

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<User> getUsers(){
		return this.em.createQuery(ConstantsUtil.QUERY_USER_LISTS)
				.getResultList();
	}
	
	@Transactional(readOnly = true)
	public User findById(Long id) {
		return this.em.find(User.class, id);
	}
	
	@Transactional
	public void save(User user){
		if (user.getId() == null || user.getId() == 0) {
			user.setCreated(new Date());
			user.setModified(new Date());
			this.em.persist(user);
		} else {
			user.setModified(new Date());
			this.em.merge(user);
		}
	}
	
	@Transactional
	public void delete(Long id) {
		User user = this.findById(id);
		this.em.remove(user);
	}
}

package com.mycroft.sbdj.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.mycroft.sbdj.model.entities.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

}

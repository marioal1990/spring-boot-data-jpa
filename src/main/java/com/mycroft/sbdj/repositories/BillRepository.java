package com.mycroft.sbdj.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.mycroft.sbdj.model.entities.Bill;

public interface BillRepository extends PagingAndSortingRepository<Bill, Long> {

}

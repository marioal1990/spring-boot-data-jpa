package com.mycroft.sbdj.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.mycroft.sbdj.model.entities.BillDetail;

public interface BillDetailRepository extends PagingAndSortingRepository<BillDetail, Long> {
	
}

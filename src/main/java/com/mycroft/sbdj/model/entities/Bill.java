package com.mycroft.sbdj.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bills")
@Getter
@Setter
@AllArgsConstructor
public class Bill implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date created;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private User user;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "bill_id")
	private List<BillDetail> billDetails;
	
	public Bill() {
		this.billDetails = new ArrayList<BillDetail>();
	}
	
	public void addBillDetails(BillDetail billDetail) {
		this.billDetails.add(billDetail);
	}
	
	public Long calculateTotal() {
		return this.getBillDetails().stream()
				.mapToLong(BillDetail::calculateSubTotal)
				.sum();
	}
	
	public Integer calculateTotalProducts() {
		return billDetails.stream()
				.mapToInt(BillDetail::getQuantity)
				.sum();
	}
}

package com.mycroft.sbdj.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@PropertySources({
	@PropertySource("classpath:labels.properties")
})
@Getter
@Setter
public class LabelsProperties {

	//DEFAULT LABELS
	@Value("${index.title}")
	private String title;
	
	@Value("${actions.title}")
	private String actions;
	
	//MENU NAVIGATOR LABELS
	@Value("${menu.nav.user}")
	private String mnUser;
	
	@Value("${menu.nav.user.view}")
	private String mnuView;
	
	@Value("${menu.nav.user.create}")
	private String mnuCreate;
	
	@Value("${menu.nav.bill}")
	private String mnBill;
	
	@Value("${menu.nav.bill.view}")
	private String mnbView;
	
	@Value("${menu.nav.bill.create}")
	private String mnbCreate;
	
	@Value("${menu.nav.product}")
	private String mnProduct;

	@Value("${menu.nav.product.view}")
	private String mnpView;

	@Value("${menu.nav.product.create}")
	private String mnpCreate;
	
	//TITLE PAGES LABELS
	@Value("${bill.view.title}")
	private String bviewTitle;
	
	@Value("${bill.create.title}")
	private String bcreateTitle;
	
	@Value("${user.view.title}")
	private String uviewTitle;
	
	@Value("${user.create.title}")
	private String ucreateTitle;
	
	@Value("${product.view.title}")
	private String pviewTitle;
	
	@Value("${product.create.title}")
	private String pcreateTitle;

	//BUTTONS LABELS
	@Value("${create.bt.save}")
	private String cbSave;
	
	@Value("${create.bt.edit}")
	private String cbEdit;
	
	@Value("${create.bt.delete}")
	private String cbDelete;
	
	@Value("${create.bt.buy}")
	private String cbBuy;
	
	//TABLE USER LABELS
	@Value("${user.table.id}")
	private String id;

	@Value("${user.table.name}")
	private String name;
	
	@Value("${user.table.lastname}")
	private String lastname;
	
	@Value("${user.table.email}")
	private String email;
	
	@Value("${user.table.created}")
	private String created;
	
	@Value("${user.table.modified}")
	private String modified;
	
	@Value("${user.table.photo}")
	private String photo;
	
	//TABLE BILL LABELS
	@Value("${bill.table.id}")
	private String btId;
	
	@Value("${bill.table.description}")
	private String btDescription;
	
	@Value("${bill.table.created}")
	private String btCreated;
	
	@Value("${bill.table.details}")
	private String btDetails;
	
	@Value("${bill.table.product}")
	private String btProduct;
	
	@Value("${bill.table.found.product}")
	private String btFoundProduct;
	
	@Value("${bill.table.total}")
	private String btTotal;
	
	@Value("${bill.table.subTotal}")
	private String btSubTotal;
	
	@Value("${bill.table.iva}")
	private String btIva;

	@Value("${bill.table.quantity}")
	private String btQuantity;
	
	//TABLE PRODUCTS LABELS
	@Value("${product.table.id}")
	private String ptId;
	
	@Value("${product.table.name}")
	private String ptName;
	
	@Value("${product.table.price}")
	private String ptPrice;
	
	@Value("${product.table.stock}")
	private String ptStock;
	
	@Value("${product.table.created}")
	private String ptCreated;
	
	@Value("${product.table.modified}")
	private String ptModified;
	
	//PAGINATOR
	@Value("${paginator.first}")
	private String pFirst;
	
	@Value("${paginator.previous}")
	private String pPrevious;
	
	@Value("${paginator.next}")
	private String pNext;
	
	@Value("${paginator.last}")
	private String pLast;
}

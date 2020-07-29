package com.mycroft.sbdj.utils.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PageRender<T> {

	@Getter
	private String url;
	private Page<T> page;
	
	@Getter
	private int totalPages;
	private int quantityForPages;
	@Getter
	private int currentPage;
	@Getter
	private List<PageItem> pages;
	
	public PageRender(String url, Page<T> page){
		this.url = url;
		this.page = page;
		this.pages = new ArrayList<PageItem>();
		
		totalPages = page.getTotalPages();
		quantityForPages = page.getSize();
		currentPage = page.getNumber() + 1;
		
		int from, until;
		if (totalPages <= quantityForPages) {
			from = 1;
			until = totalPages;
		} else {
			if (currentPage <= (quantityForPages/2)) {
				from = 1;
				until = quantityForPages;
			} else if (currentPage >= (totalPages - quantityForPages/2)) {
				from = totalPages - quantityForPages + 1;
				until = quantityForPages;
			} else {
				from = currentPage - quantityForPages/2;
				until = quantityForPages;
			}
		}
		
		for (int i = 0; i < until; i++) {
			int numberPage = from + i;
			Boolean isCurrentPage = currentPage == from + i;
			pages.add(new PageItem(numberPage, isCurrentPage));
		}
	}
	
	public Boolean isFirst() {
		return page.isFirst();
	}
	
	public Boolean isLast() {
		return page.isLast();
	}
	
	public Boolean isHasNext() {
		return page.hasNext();
	}
	
	public Boolean isHasPrevious() {
		return page.hasPrevious();
	}
}

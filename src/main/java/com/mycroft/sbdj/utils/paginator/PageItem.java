package com.mycroft.sbdj.utils.paginator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageItem {

	private int numberPage;
	private Boolean isCurrentPage;
}

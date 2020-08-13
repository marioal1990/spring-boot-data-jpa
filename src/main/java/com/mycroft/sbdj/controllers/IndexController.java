package com.mycroft.sbdj.controllers;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.mycroft.sbdj.utils.ConstantsUtil;
import com.mycroft.sbdj.utils.LabelsProperties;

@Controller
public class IndexController {

	@Autowired
	private LabelsProperties labelsProperties;
	
	@ModelAttribute("labelsProperties")
	public LabelsProperties getProperties() {
		return this.labelsProperties;
	}

	@GetMapping("/index")
	public String index(Model model) {
		String message = MessageFormat.format(ConstantsUtil.TITLE_VIEW, this.labelsProperties.getTitle());
		model.addAttribute("title", 
				message);
		return "/index";
	}
}

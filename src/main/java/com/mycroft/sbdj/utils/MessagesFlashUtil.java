package com.mycroft.sbdj.utils;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class MessagesFlashUtil {

	public static void messageInfo(RedirectAttributes redirect, String message) {
		redirect.addFlashAttribute(ConstantsUtil.VARIABLE_NAME_INFO, message);
	}
	
	public static void messageSuccess(RedirectAttributes redirect, String message) {
		redirect.addFlashAttribute(ConstantsUtil.VARIABLE_NAME_SUCCESS, message);
	}
	
	public static void messageError(RedirectAttributes redirect, String message) {
		redirect.addFlashAttribute(ConstantsUtil.VARIABLE_NAME_ERROR, message);
	}
}

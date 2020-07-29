package com.mycroft.sbdj.utils;

public class ValidateUtil {
	
	public static Boolean isNumberValidate(String param) {
		return (param != null 
				&& !param.isEmpty() 
				&& param != "0");
	}

	public static Boolean isNumberValidate(Long param) {
		return (param != null 
				&& param > 0);
	}
}

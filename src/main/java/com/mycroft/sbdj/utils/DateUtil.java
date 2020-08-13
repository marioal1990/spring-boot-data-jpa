package com.mycroft.sbdj.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	/**
	 * java.util.Date to String from string pattern
	 * @param date The java.util.Date 
	 * @param pattern The string pattern
	 * @return The string date converted
	 */
	public static String dateToString(Date date, String pattern) {
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}
}

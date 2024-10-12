package com.ani.bazaar.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {

	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public static String dateToString(LocalDate date) {
		
		return date != null ? date.format(formatter) : "";
	}

	public static LocalDate stringToDate(String dateString) {
		try {
		return LocalDate.parse(dateString, formatter);
		}catch(DateTimeParseException e) {
			throw new DateTimeParseException(e.getMessage(), dateString, e.getErrorIndex());
		}
	}
}

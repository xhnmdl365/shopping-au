package com.cscd.shoppingau.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @Description
 * @Author Anthony
 * @Date 8/01/2018 7:24 PM
 */
public class Tool {



	public static boolean emptyCheck(String str) {
		return str != null && !("").equals(str);
	}
	public static boolean emptyCheck(List list) {
		return list != null && !list.isEmpty();
	}
	public static boolean emptyCheck(Map map) {
		return map != null && !map.isEmpty();
	}
	public static boolean emptyCheck(Set set) {
		return set != null && !set.isEmpty();
	}
	public static boolean emptyCheck(Date date) {
		return date != null;
	}

	public static void redirect(HttpServletRequest request, HttpServletResponse response,
								String path) throws ServletException, IOException {
		request.getRequestDispatcher(path).forward(request, response);
	}

	public static Map fieldValidHandle(BindingResult result, MessageSource messageSource) {
		Map rsMap = new HashMap();
		if(result.hasErrors()) {
			List<FieldError> fieldErrors = result.getFieldErrors();
			Locale currentLocale = LocaleContextHolder.getLocale();
			for(FieldError fieldError: fieldErrors) {
				String errMsg = messageSource.getMessage(fieldError, currentLocale);
				rsMap.put(fieldError.getField(), errMsg);
			}
		}
		return rsMap;
	}

}

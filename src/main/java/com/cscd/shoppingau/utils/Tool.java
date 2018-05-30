package com.cscd.shoppingau.utils;

import com.github.pagehelper.PageInfo;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author Anthony
 * @Date 8/01/2018 7:24 PM
 */
public class Tool {



	public static boolean isNotEmpty(String str) {
		return str != null && !("").equals(str);
	}
	public static boolean isNotEmpty(List list) {
		return list != null && !list.isEmpty();
	}
	public static boolean isNotEmpty(Map map) {
		return map != null && !map.isEmpty();
	}
	public static boolean isNotEmpty(Set set) {
		return set != null && !set.isEmpty();
	}
	public static boolean isNotEmpty(Date date) {
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

	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	public static Map getPageInfo(PageInfo pageInfo) {
		Map pageInfoMap = new HashMap();
		pageInfoMap.put("total", pageInfo.getTotal());
		pageInfoMap.put("currentPage", pageInfo.getPageNum());
		pageInfoMap.put("totalPage", pageInfo.getPages());
		pageInfoMap.put("isHasNextPage", pageInfo.isHasNextPage());
		pageInfoMap.put("isHasPreviousPage", pageInfo.isHasPreviousPage());
		return pageInfoMap;
	}


}

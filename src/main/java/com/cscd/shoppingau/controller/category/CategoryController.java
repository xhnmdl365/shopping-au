package com.cscd.shoppingau.controller.category;


import com.cscd.shoppingau.service.category.CategoryService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Anthony
 * @Date 8/01/2018 11:13 AM
 */

@RestController
public class CategoryController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CategoryService categoryService;

	@RequestMapping("/getCurrentVerticalCAT")
	public Map getCurrentVerticalCAT() {
		Map rtMap = new HashMap();
		String code = "000";
		String msg = "success";
		try {
			List list = categoryService.getCurrentVerticalCAT();
			rtMap.put("data", list);
		} catch(Exception e) {
			code = "999";
			msg = "system busy, please try later";
			e.printStackTrace();
		}
		rtMap.put("code", code);
		rtMap.put("msg", msg);

		return rtMap;
	}
}

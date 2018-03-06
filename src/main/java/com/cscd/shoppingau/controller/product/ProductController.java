package com.cscd.shoppingau.controller.product;

import com.cscd.shoppingau.service.product.ProductService;
import com.cscd.shoppingau.utils.Tool;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Anthony
 * @Date 1/03/2018 12:10 PM
 */

@Controller
public class ProductController {

	@Autowired
	ProductService productService;
	@ResponseBody
	@PostMapping("/getProductsBeyKeyword")
	public Map getProductsBeyKeyword(String q, String bId, String cIds){
		//@RequestBody Map p

		if(!Tool.isNotEmpty(q) && !Tool.isNotEmpty(bId) && !Tool.isNotEmpty(cIds)) {
			return null;
		}
		Map rtMap = new HashMap();
		String code = "000";
		String msg = "success";


		try {
			Map map = productService.getProductsByKeyword(q, bId, cIds);

			rtMap.put("data", map);
		} catch(Exception e) {
			code = "999";
			msg = "system busy, please try later";
			e.printStackTrace();
		}
		rtMap.put("code", code);
		rtMap.put("msg", msg);

		return rtMap;
	}
	@RequestMapping("/productList")
	public String index() {
		System.out.println("productList:");
		System.out.println(SecurityUtils.getSubject().getPrincipal());
		return "dist/html/product/search_product";
	}
}

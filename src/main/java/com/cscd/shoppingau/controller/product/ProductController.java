package com.cscd.shoppingau.controller.product;

import com.cscd.shoppingau.model.Brand;
import com.cscd.shoppingau.service.brand.BrandService;
import com.cscd.shoppingau.service.category.CategoryService;
import com.cscd.shoppingau.service.product.ProductService;
import com.cscd.shoppingau.utils.Tool;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
	@Autowired
	CategoryService categoryService;
	@Autowired
	BrandService brandService;


	@RequestMapping("/productList")
	public String index(String q, String bIds, String cIds) {
		if(!Tool.isNotEmpty(q) && !Tool.isNotEmpty(bIds) && !Tool.isNotEmpty(cIds)) {
			return "redirect:/";
		}
		return "dist/html/product/search_product";
	}


	@RequestMapping("/product")
	public String productInfo(String productId) {
		System.out.println(SecurityUtils.getSubject().getPrincipal());
		if(!Tool.isNotEmpty(productId)) {
			return "redirect:/";
		}
		return "dist/html/product/product-info";
	}

	@ResponseBody
	@PostMapping("/getProductsByKeyword")
	public Map getProductsBeyKeyword(String q, String bIds, String cIds, String priceRange, String sort, String cp){
		//@RequestBody Map p

		if(!Tool.isNotEmpty(q) && !Tool.isNotEmpty(bIds) && !Tool.isNotEmpty(cIds)) {
			return null;
		}
		int currentPage = 1;
		if(Tool.isNotEmpty(cp) && Tool.isNumber(cp)) {
			currentPage = Integer.valueOf(cp);
		}
		Map rtMap = new HashMap();
		String code = "000";
		String msg = "success";


		try {
			List searchCategoriesList = null;
			List<Brand>  searchBrandList = null;
			Map map = productService.getProductsByKeyword(q, bIds, cIds, priceRange, sort, currentPage);
			if(Tool.isNotEmpty(cIds)) {
				searchCategoriesList = categoryService.getCategoriesByIds(cIds);
			}
			if(Tool.isNotEmpty(bIds)) {
				searchBrandList = brandService.getBrandByIds(bIds);
			}
			map.put("searchCategoriesList", searchCategoriesList);
			map.put("searchBrandList", searchBrandList);
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
	@ResponseBody
	@PostMapping("/getProductInfo")
	public Map getProductInfo(String productId){
		System.out.println(productId);
		Map rtMap = new HashMap();
		String code = "000";
		String msg = "success";

		Map dataMap = new HashMap();
		Map productBaseInfo = new HashMap();
		List productImgs = new ArrayList();
		List productSkuList = new ArrayList();
		List productAttrs = new ArrayList();

		try {

			if(Tool.isNotEmpty(productId)) {
				productBaseInfo = productService.getProductBaseInfo(productId);
				productImgs = productService.getProductImgs(productId);
				productSkuList = productService.getProductSkuList(productId);
				productAttrs = productService.getProductAttrs(productId);

			}
		} catch(Exception e) {
			code = "999";
			msg = "system busy, please try later";
			e.printStackTrace();
		}

		dataMap.put("productBaseInfo", productBaseInfo);
		dataMap.put("productImgs", productImgs);
		dataMap.put("productSkuList", productSkuList);
		dataMap.put("productAttrs", productAttrs);

		rtMap.put("data", dataMap);
		rtMap.put("code", code);
		rtMap.put("msg", msg);

		return rtMap;
	}

	@ResponseBody
	@PostMapping("/getProductUserRate")
	public Map getProductUserRate(String productId, String cp) {
		Map rtMap = new HashMap();
		String code = "000";
		String msg = "success";

		int currentPage = 1;
		if(Tool.isNotEmpty(cp) && Tool.isNumber(cp)) {
			currentPage = Integer.valueOf(cp);
		}
		Map userRateMap = new HashMap();
		try {
			if(Tool.isNotEmpty(productId)) {
				userRateMap = productService.getProductUserRate(productId, currentPage);
			}
		} catch(Exception e) {
			code = "999";
			msg = "system busy, please try later";
			e.printStackTrace();
		}

		rtMap.put("code", code);
		rtMap.put("msg", msg);
		rtMap.put("data", userRateMap);

		return rtMap;
	}


}

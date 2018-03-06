package com.cscd.shoppingau.service.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.support.spring.MappingFastJsonValue;
import com.cscd.shoppingau.mapper.indexPromote.IndexPromoteMapper;
import com.cscd.shoppingau.mapper.product.ProductMapper;
import com.cscd.shoppingau.model.Brand;
import com.cscd.shoppingau.model.category.VerticalCategory;
import com.cscd.shoppingau.service.brand.BrandService;
import com.cscd.shoppingau.service.category.CategoryService;
import com.cscd.shoppingau.utils.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Anthony
 * @Date 1/03/2018 10:39 AM
 */

@Service
public class ProductService {



	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private BrandService brandService;

	@Autowired
	private CategoryService categoryService;

	public Map getProductsByKeyword(String keyword, String brandId, String cIds) {

		String[] keywords = null;
		if(Tool.isNotEmpty(keyword)) {
			keywords = keyword.split(" ");
		}

		// categoryId use the last categoryId.
		// because cIds is sorted by parent to child
		String categoryId = null;
		if(Tool.isNotEmpty(cIds)) {
			String[] cIdArr = cIds.split(",");
			categoryId = cIdArr[cIdArr.length -1];
		}
		System.out.println("keyword= "+ keyword);
		System.out.println("keywords = " + keywords);
		System.out.println("brandId = " + brandId);
		System.out.println("categoryId = " + categoryId);


		List ProductsList = productMapper.getProductsByKeyWord(keyword, keywords, brandId, categoryId);

		List brandsProductsList = null;
		if(!Tool.isNotEmpty(brandId)) {
			// if brandId is not null. It means there are more than 1 brand in the product list
			// otherwise, there already is a brand selected
			brandsProductsList = productMapper.getBrandsByProducts(keyword, keywords,  categoryId);
		}

		List categoriesProductsList = null;
		categoriesProductsList = productMapper.getCategoriesByProducts(keyword, keywords, brandId, categoryId);

		List<VerticalCategory> subCategoriesList = new ArrayList();
		subCategoriesList = categoryService.getCategoriesByParentId(categoryId);

		if(!Tool.isNotEmpty(categoryId)) {
			// if categoryId is not null. It means there are more than 1 brand in the product list
			// otherwise, there already is a brand selected


		}
		for(int i=0; i< subCategoriesList.size(); i ++) {
			String subCategoryId = String.valueOf(subCategoriesList.get(i).getCategoryId());
			for(int j=0; j< categoriesProductsList.size(); j ++) {

			}
		}
		Map productInfoMap = new HashMap();
		productInfoMap.put("brandsProductsList", brandsProductsList);
		productInfoMap.put("ProductsList", ProductsList);
		productInfoMap.put("categoriesProductsList", categoriesProductsList);

		return productInfoMap;
	}

//	private Boolean isContainName(List<Brand> brandList, String keyword) {
//		int i= 0;
//		for(Brand brand: brandList) {
//			System.out.println(i++);
//			if(keyword.equals(brand.getName())) {
//				return true;
//			}
//		}
//		return false;
//	}
}

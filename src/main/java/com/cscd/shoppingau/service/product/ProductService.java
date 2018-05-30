package com.cscd.shoppingau.service.product;

import com.cscd.shoppingau.mapper.product.ProductMapper;
import com.cscd.shoppingau.service.brand.BrandService;
import com.cscd.shoppingau.service.category.CategoryService;
import com.cscd.shoppingau.utils.Tool;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.rmi.CORBA.Util;
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

	private final int productPageSize = 2;
	private final int maxProductPrice = 99999999;
	public Map getProductsByKeyword(String keyword, String brandId,
									String cIds, String priceRange, String sort, int currentPage) {

		// when user input the key words, We split the word to servral words
		// And we keep the whole word to a situation eg. input: microsoft Surface , we match the
		// whole word "microsoft Surface" and "microsoft" and "Surface"
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
		int priceRangeFrom = -1;
		int priceRangeTo = -1;
		if(Tool.isNotEmpty(priceRange)) {
			String[] priceRangeArr = priceRange.split(",");
			priceRangeFrom = Integer.valueOf(priceRangeArr[0]);
			if(priceRangeArr.length == 1) {
				priceRangeTo = maxProductPrice;
			} else {
				priceRangeTo = Integer.valueOf(priceRangeArr[1]);
			}
		}
		String sortBy = "";
		if(Tool.isNotEmpty(sort)) {
			String[] sortByArr = sort.split("_");
			if(sortByArr.length == 1) {
				sortBy = sortByArr[0];
				if("orders".equals(sortBy)){
					sortBy = "sale_volume DESC";
				} else if("rating".equals(sortBy)){
					sortBy = "rate DESC";
				} else if("price".equals(sortBy)) {
					sortBy = "price DESC";
				}
			}else if(sortByArr.length == 2){
				String tempSortBy = sortByArr[0];
				if("orders".equals(tempSortBy) || "rating".equals(tempSortBy) || "price".equals(tempSortBy)) {
					sortBy = tempSortBy;
					if("asc".equalsIgnoreCase(sortByArr[1])) {
						sortBy = tempSortBy + " ASC";
					}
				}
			}
		}

		// page
		PageHelper.startPage(currentPage, productPageSize);
		List ProductsList = productMapper.getProductsByKeyWord(keyword, keywords, brandId, categoryId, priceRangeFrom, priceRangeTo, sortBy);

		// pageInfo
		PageInfo pageInfo = new PageInfo(ProductsList);
		Map productsListPageInfo = Tool.getPageInfo(pageInfo);

		List productsBrandsList = null;
		if(!Tool.isNotEmpty(brandId)) {
			// if brandId is not null. It means there are more than 1 brand in the product list
			// otherwise, there already is a brand selected
			productsBrandsList = productMapper.getBrandsByProducts(keyword, keywords, categoryId, priceRangeFrom, priceRangeTo, sortBy);
		}

		List productsCategoriesList = null;
		productsCategoriesList = productMapper.getCategoriesByProducts(keyword, keywords, brandId, categoryId, priceRangeFrom, priceRangeTo, sortBy);


		Map productInfoMap = new HashMap();
		productInfoMap.put("productsListPageInfo", productsListPageInfo);
		productInfoMap.put("productsBrandsList", productsBrandsList);
		productInfoMap.put("productsList", ProductsList);
		productInfoMap.put("productsCategoriesList", productsCategoriesList);

		return productInfoMap;
	}

	public Map getProductBaseInfo(String productId) {
		return productMapper.getProductBaseInfo(productId);
	}

	public List getProductAttrs(String productId) {
		return productMapper.getProductAttrs(productId);
	}
	public List getProductImgs(String productId) {
		return productMapper.getProductImgs(productId);
	}
	public List getProductSkuList(String productId) {
		return productMapper.getProductSkuList(productId);
	}
	public Map getProductUserRate(String productId, int currentPage) {
		final int userRatePageSize = 20;
		PageHelper.startPage(currentPage, userRatePageSize);
		List userRateList = productMapper.getProductUserRate(productId);
		PageInfo pageInfo = new PageInfo(userRateList);
		Map userRatePageInfo = Tool.getPageInfo(pageInfo);

		Map productUserRate = new HashMap();
		productUserRate.put("userRatePageInfo", userRatePageInfo);
		productUserRate.put("userRateList", userRateList);
		return productUserRate;
	}

	public Map getProductBySkuId(String skuId) {
		return productMapper.getProductBySkuId(skuId);
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

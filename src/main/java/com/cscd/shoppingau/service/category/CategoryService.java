package com.cscd.shoppingau.service.category;

/**
 * @Description
 * @Author Anthony
 * @Date 8/01/2018 11:15 AM
 */

import com.cscd.shoppingau.mapper.category.CategoryMapper;
import com.cscd.shoppingau.model.category.Category;
import com.cscd.shoppingau.model.category.VerticalCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;

	@Cacheable(value = "verticalCAT", key="#root.methodName")
	public List<VerticalCategory> getCurrentVerticalCAT() {
		return categoryMapper.getCurrentVerticalCAT();
	}
	public List<Category> getCategoriesByParentId(String parentCategoryId) {
		return categoryMapper.getCategoriesByParentId(parentCategoryId);
	}
	@Cacheable(value = "brandList", key="#categoryId")
	public List<Category> getCategoryId(String categoryId) {
		return categoryMapper.getCategoryById(categoryId);
	}

	public List<Category> getCategoriesByIds(String categoryIds) {
		return categoryMapper.getCategoriesByIds(categoryIds);
	}
}
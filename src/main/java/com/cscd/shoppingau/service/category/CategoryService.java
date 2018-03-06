package com.cscd.shoppingau.service.category;

/**
 * @Description
 * @Author Anthony
 * @Date 8/01/2018 11:15 AM
 */

import com.cscd.shoppingau.mapper.category.CategoryMapper;
import com.cscd.shoppingau.model.category.VerticalCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;

	public List<VerticalCategory> getCurrentVerticalCAT() {
		return categoryMapper.getCurrentVerticalCAT();
	}
	public List<VerticalCategory> getCategoriesByParentId(String parentCategoryId) {
		return categoryMapper.getCategoriesByParentId(parentCategoryId);
	}

}
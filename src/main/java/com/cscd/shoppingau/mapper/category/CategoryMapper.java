package com.cscd.shoppingau.mapper.category;

import com.cscd.shoppingau.model.category.Category;
import com.cscd.shoppingau.model.category.VerticalCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * @Description
 * @Author Anthony
 * @Date 8/01/2018 11:14 AM
 */
@Mapper
public interface CategoryMapper {


	@Select("select category_id, parent_id, name, url, is_popular, background, icon, is_show_sub" +
			" from vertical_category where status = 1")
	List<VerticalCategory> getCurrentVerticalCAT();

	@Select("select category_id, parent_id, name" +
			" from category where status = 1" +
			" AND CASE WHEN #{parentCategoryId} is NULL then category_id=parent_id else parent_id=#{parentCategoryId} end;")
	List<Category> getCategoriesByParentId(@Param("parentCategoryId") final String parentCategoryId);

	@Select("select category_id, parent_id, name" +
			" from category where status = 1" +
			" and category_id=#{categoryId} ")
	List<Category> getCategoryById(@Param("categoryId") final String categoryId);

	@Select("select category_id, parent_id, name, status" +
			" from category where status = 1" +
			" and position(category_id in #{categoryIds})")
	List<Category> getCategoriesByIds(@Param("categoryIds") final String categoryIds);

}

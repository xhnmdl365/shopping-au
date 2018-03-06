package com.cscd.shoppingau.mapper.brand;

import com.cscd.shoppingau.model.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @Description
 * @Author Anthony
 * @Date 8/01/2018 11:14 AM
 */
@Mapper
public interface BrandMapper {


	@Select("select brand_id, name, img_path, status from brand where status = 1")
	List<Brand> getAllBrands();
}

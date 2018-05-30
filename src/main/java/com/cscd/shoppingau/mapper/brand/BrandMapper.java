package com.cscd.shoppingau.mapper.brand;

import com.cscd.shoppingau.model.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @Description
 * @Author Anthony
 * @Date 8/01/2018 11:14 AM
 */
@Mapper
public interface BrandMapper {


	@Select("select brand_id, name, img_path from brand where status = 1")
	List<Brand> getAllBrands();

	@Select("select brand_id, name, img_path from brand where status = 1 and brand_id=#{brandId}")
	Brand getBrandById(@Param("brandId") String brandId);

	@Select("select brand_id, name, img_path from brand where status = 1 and POSITION(brand_id IN #{brandIds})")
	List<Brand> getBrandByIds(@Param("brandIds") String brandIds);
}

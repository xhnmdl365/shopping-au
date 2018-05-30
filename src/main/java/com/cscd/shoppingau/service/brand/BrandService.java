package com.cscd.shoppingau.service.brand;

/**
 * @Description
 * @Author Anthony
 * @Date 8/01/2018 11:15 AM
 */

import com.cscd.shoppingau.mapper.brand.BrandMapper;
import com.cscd.shoppingau.model.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

	@Autowired
	private BrandMapper brandMapper;
	@Cacheable(value = "brandList", key="#root.methodName")
	public List<Brand> getAllBrands() {
		return brandMapper.getAllBrands();
	}

	@Cacheable(value = "brand", key="#brandId")
	public Brand getBrandById(String brandId) {
		return brandMapper.getBrandById(brandId);
	}
	@Cacheable(value = "brand", key="#brandIds")
	public List<Brand> getBrandByIds(String brandIds) {
		return brandMapper.getBrandByIds(brandIds);
	}

}
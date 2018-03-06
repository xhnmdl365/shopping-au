package com.cscd.shoppingau.service.brand;

/**
 * @Description
 * @Author Anthony
 * @Date 8/01/2018 11:15 AM
 */

import com.cscd.shoppingau.mapper.brand.BrandMapper;
import com.cscd.shoppingau.model.Brand;
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
	@Cacheable(value = "brandList", key="1")
	public List<Brand> getAllBrands() {
		return brandMapper.getAllBrands();
	}

	@CacheEvict(value = "brandList" , key="1")
	public Brand addToCache(Brand brand) {



		System.out.println("addToCache");
		brand.setBrandId(1);
		brand.setName("abc");
		return brand;
	}
	@CacheEvict(value = "brandList", key="1")
	public void deleteFromCache() {
		System.out.println("delete from cache");
	}
}
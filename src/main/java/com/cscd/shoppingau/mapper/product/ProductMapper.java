package com.cscd.shoppingau.mapper.product;

import com.cscd.shoppingau.sqlprovider.product.ProductSqlProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Anthony
 * @Date 28/02/2018 5:06 PM
 */
public interface ProductMapper {


	@Select("select title, categories, products, promote_img, promote_page from index_promote")
	List<Map> getProductsByCategoryName();

	@Select("select title, categories, products, promote_img, promote_page from index_promote")
	List<Map> getProductsByBrandId();

	@Select("select title, categories, products, promote_img, promote_page from index_promote")
	List<Map> getProductsByCategoryId();

	@Select("select title, categories, products, promote_img, promote_page from index_promote")
	List<Map> getProductByProductId();

	@SelectProvider(type=ProductSqlProvider.class, method="getProductListByKeyword")
	List<Map> getProductsByKeyWord(@Param("wholeWord") final String wholeWord,
								   @Param("keywords") final String[] keywords,
								   @Param("brandId") final String brandId,
								   @Param("categoryId") final String categoryId);

	@SelectProvider(type=ProductSqlProvider.class, method="getBrandsByProducts")
	List<Map> getBrandsByProducts( @Param("wholeWord") final String wholeWord,
								   @Param("keywords") final String[] keywords,
								   @Param("categoryId") final String categoryId);

	@SelectProvider(type=ProductSqlProvider.class, method="getCategoriesByProducts")
	List<Map> getCategoriesByProducts(@Param("wholeWord") final String wholeWord,
									  @Param("keywords") final String[] keywords,
									  @Param("brandId") final String brandId,
									  @Param("categoryId") final String categoryId);

}

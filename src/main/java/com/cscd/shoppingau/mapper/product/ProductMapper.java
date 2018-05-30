package com.cscd.shoppingau.mapper.product;

import com.cscd.shoppingau.sqlprovider.product.ProductSqlProvider;
import org.apache.ibatis.annotations.*;

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
	@Results({
		@Result(property = "productId", column = "product_id"),
		@Result(property = "brandId", column = "brand_id"),
		@Result(property = "categoryId", column = "category_id"),
		@Result(property = "imgPath", column = "img_path"),
		@Result(property = "saleVolume", column = "sale_volume"),
		@Result(property = "name", column = "name"),
		@Result(property = "price", column = "price"),
		@Result(property = "rate", column = "rate")
	})
	List<Map> getProductsByKeyWord(@Param("wholeWord") final String wholeWord,
								   @Param("keywords") final String[] keywords,
								   @Param("brandId") final String brandId,
								   @Param("categoryId") final String categoryId,
								   @Param("priceRangeFrom") final int priceRangeFrom,
								   @Param("priceRangeTo") final int priceRangeTo,
								   @Param("sortBy") final String sortBy);

	@SelectProvider(type=ProductSqlProvider.class, method="getBrandsByProducts")
	@Results({
		@Result(property = "brandId", column = "brand_id"),
		@Result(property = "imgPath", column = "img_path"),
		@Result(property = "name", column = "name"),
		@Result(property = "num", column = "num")
	})
	List<Map> getBrandsByProducts( @Param("wholeWord") final String wholeWord,
								   @Param("keywords") final String[] keywords,
								   @Param("categoryId") final String categoryId,
								   @Param("priceRangeFrom") final int priceRangeFrom,
								   @Param("priceRangeTo") final int priceRangeTo,
								   @Param("sortBy") final String sortBy);

	@Results({
		@Result(property = "categoryId", column = "category_id"),
		@Result(property = "name", column = "name"),
		@Result(property = "num", column = "num")
	})
	@SelectProvider(type=ProductSqlProvider.class, method="getCategoriesByProducts")
	List<Map> getCategoriesByProducts(@Param("wholeWord") final String wholeWord,
									  @Param("keywords") final String[] keywords,
									  @Param("brandId") final String brandId,
									  @Param("categoryId") final String categoryId,
									  @Param("priceRangeFrom") final int priceRangeFrom,
									  @Param("priceRangeTo") final int priceRangeTo,
									  @Param("sortBy") final String sortBy);

	@Select("SELECT p.product_id, p.name, pr.rate, p.description" +
			" from product p, product_rate pr" +
			" where p.product_id=#{productId}" +
			" and p.product_id = pr.product_id and p.status='1'")
	Map getProductBaseInfo(@Param("productId") final String productId);

	@Select("select product_img_id, img_path from product_img where product_id = #{productId} and status='1' ORDER BY sort_order ASC")
	List<Map> getProductImgs(@Param("productId") final String productId);

	@Select("SELECT pac.attr_cate_id, pa.attr_val_id, pac.name, pav.value " +
	" FROM product_attribute pa, product_attribute_category pac, product_attribute_value pav" +
	" WHERE pa.product_id = #{productId}" +
	" AND pa.attr_val_id = pav.attr_val_id" +
	" AND pac.attr_cate_id = pav.attr_cate_id")
	List<Map> getProductAttrs(@Param("productId") final String productId);

	@Select("SELECT sku.sku_id, sku.price, sku.sale_volume, sku.stock, sku.attribute FROM sku " +
			" WHERE product_id = #{productId}" +
			" AND sku.status = '1'")
	List<Map> getProductSkuList(@Param("productId") final String productId);

	@Select("SELECT pur.rate *1 as rate, pur.commit, u.username,u.head_img, pur.create_time" +
			" FROM product_user_rate pur, user u" +
			" where pur.product_id = #{productId}" +
			" and pur.user_id=u.user_id" +
			" and pur.status='1'")
	@Results({
		@Result(property = "createTime", column = "create_time")
	})
	List<Map> getProductUserRate(@Param("productId") final String productId);

	@Select(" SELECT p.product_id, p.name, sku.stock, sku.price FROM product p, sku" +
			" WHERE p.product_id = sku.product_id AND p.status='1' AND sku.status='1'" +
			" AND sku.sku_id = #{skuId}")
	Map getProductBySkuId(@Param("skuId") final String skuId);

}

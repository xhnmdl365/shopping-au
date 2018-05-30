package com.cscd.shoppingau.mapper.product;

import com.cscd.shoppingau.model.Cart;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Anthony
 * @Date 28/02/2018 5:06 PM
 */
public interface CartMapper {

	@Insert("insert into cart(user_id, product_id, sku_id, quantity, selected_attrs, total_price)" +
			" values (#{userId},#{productId},#{skuId}," +
			"#{quantity},#{selectedAttrs}, #{totalPrice}) ON DUPLICATE KEY UPDATE quantity = quantity+#{quantity} " +
			",total_price = total_price+#{totalPrice}")

	int addToCart(Cart cart);

	@Select ("SELECT user_id, product_id, sku_id, quantity, total_price" +
			" FROM cart" +
			" where user_id = #{userId}" +
			" and sku_id = #{skuId}")
	Map getCartSingleSku(@Param("userId") final Long userId,
						 @Param("skuId") final Long skuId);

	@Select ("SELECT p.product_id, p.name, pi.img_path, c.sku_id, c.quantity, " +
			" sku.price, c.total_price, c.selected_attrs, sku.stock" +
			" FROM cart c, product p, sku, product_img pi" +
			" WHERE user_id = #{userId}" +
			" AND p.status = '1' AND SKU.status = '1'" +
			" AND c.product_id = p.product_id" +
			" AND sku.product_id = c.product_id" +
			" AND sku.sku_id = c.sku_id" +
			" AND pi.product_id = c.product_id" +
			" AND pi.status = '1'" +
			" AND pi.sort_order = (select min(sort_order) from product_img where product_id = c.product_id)" +
			" ORDER BY c.update_time DESC")

	@Results({
		@Result(property = "productId", column = "product_id"),
		@Result(property = "imgPath", column = "img_path"),
		@Result(property = "skuId", column = "sku_id"),
		@Result(property = "totalPrice", column = "total_price"),
		@Result(property = "selectedAttrs", column = "selected_attrs")
	})
	List<Map> getCartProducts(@Param("userId") final Long userId);

	@Update("UPDATE CART SET quantity = #{quantity} WHERE sku_id= #{skuId} AND user_id=#{userId}")
	int updateCartProduct(Cart cart);

	@Delete("DELETE FROM CART WHERE sku_id= #{skuId} AND user_id=#{userId}")
	int removeCartProduct(Cart cart);
}

package com.cscd.shoppingau.service.product;

import com.cscd.shoppingau.mapper.product.CartMapper;
import com.cscd.shoppingau.mapper.product.ProductMapper;
import com.cscd.shoppingau.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Anthony
 * @Date 1/03/2018 10:39 AM
 */

@Service
public class CartService {



	@Autowired
	private CartMapper cartMapper;

	public int addToCart(Cart cart) {
		return cartMapper.addToCart(cart);
	}

	public Map getCartSingleSku(Long userId, Long skuId) {
		return cartMapper.getCartSingleSku(userId, skuId);
	}
	public List<Map> getCartProducts(Long userId) {
		return cartMapper.getCartProducts(userId);
	}
	public int updateCartProduct(Cart cart) {
		return cartMapper.updateCartProduct(cart);
	}
	public int removeCartProduct(Cart cart){
		return cartMapper.removeCartProduct(cart);
	}
}

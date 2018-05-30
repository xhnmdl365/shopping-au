package com.cscd.shoppingau.controller.product;

import com.cscd.shoppingau.model.Cart;
import com.cscd.shoppingau.model.Product;
import com.cscd.shoppingau.model.account.User;
import com.cscd.shoppingau.service.product.CartService;
import com.cscd.shoppingau.service.product.ProductService;
import com.cscd.shoppingau.utils.Tool;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Anthony
 * @Date 25/05/2018 3:56 PM
 */

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	CartService cartService;
	@Autowired
	ProductService productService;


	@RequestMapping("/myCart")
	public String mycart() {
		return "dist/html/my-cart";
	}

	@ResponseBody
	@PostMapping("/addToCart")
	public Map addToCart(String skuId, String selectedAttrs, int quantity) {
		Map rtMap = new HashMap();
		String code = "000";
		String msg = "success";

		int successCount = 0;
		try {
			if(Tool.isNotEmpty(skuId)) {

				User u = (User)(SecurityUtils.getSubject().getPrincipal());
				Map product = productService.getProductBySkuId(skuId);
				Map cartSku = cartService.getCartSingleSku(u.getUserId(), Long.valueOf(skuId));

				int exitsQuantity = 0;
				if(cartSku!=null && !cartSku.isEmpty()) {
					exitsQuantity= (int)cartSku.get("quantity");
				}

				int stock =  (int)product.get("stock");
				if(quantity + exitsQuantity > stock) {
					code = "997";
					msg = "quantity can't be more than stock";
				} else {
					Cart cart = new Cart();
					cart.setUserId(u.getUserId());
					cart.setProductId((int)product.get("product_id"));
					cart.setSkuId(Long.valueOf(skuId));
					cart.setQuantity(quantity);
					cart.setSelectedAttrs(selectedAttrs);

					cart.setTotalPrice((BigDecimal)((BigDecimal) product.get("price")).multiply(BigDecimal.valueOf(quantity)));

					successCount = cartService.addToCart(cart);
					if(successCount == 0) {
						code = "998";
						msg = "system error, please contact the admin";
					}
				}
			}
		} catch(Exception e) {
			code = "999";
			msg = "system busy, please try later";
			e.printStackTrace();
		}

		rtMap.put("code", code);
		rtMap.put("msg", msg);

		return rtMap;
	}
	@ResponseBody
	@PostMapping("/getCartProducts")
	public Map getCartProducts() {
		Map rtMap = new HashMap();
		String code = "000";
		String msg = "success";

		List cartProducts = new ArrayList();
		try {
			User u = (User)(SecurityUtils.getSubject().getPrincipal());
			cartProducts = cartService.getCartProducts(Long.valueOf(1000));
//			System.out.println(cartProducts.get("productId").getClass());

		} catch(Exception e) {
			code = "999";
			msg = "system busy, please try later";
			e.printStackTrace();
		}
		rtMap.put("data", cartProducts);
		rtMap.put("code", code);
		rtMap.put("msg", msg);

		return rtMap;
	}
	@ResponseBody
	@PostMapping("/removeCartProduct")
	public Map removeCartProduct(Cart cart) {
		Map rtMap = new HashMap();
		String code = "000";
		String msg = "success";

		List cartProducts = new ArrayList();
		try {
			User u = (User)(SecurityUtils.getSubject().getPrincipal());
			cart.setUserId(u.getUserId());
			int count = cartService.removeCartProduct(cart);
			if(count != 1) {
				code = "991";
			}
			cartProducts = cartService.getCartProducts(u.getUserId());
		} catch(Exception e) {
			code = "999";
			msg = "system busy, please try later";
			e.printStackTrace();
		}
		rtMap.put("data", cartProducts);
		rtMap.put("code", code);
		rtMap.put("msg", msg);

		return rtMap;
	}
	@ResponseBody
	@PostMapping("/updateCartProduct")
	public Map updateCartProduct(Cart cart) {
		Map rtMap = new HashMap();
		String code = "000";
		String msg = "success";

		List cartProducts = new ArrayList();
		try {
			User u = (User)(SecurityUtils.getSubject().getPrincipal());
			cart.setUserId(u.getUserId());
			int count = cartService.updateCartProduct(cart);
			if(count != 1) {
				code = "991";
			}
			cartProducts = cartService.getCartProducts(u.getUserId());
		} catch(Exception e) {
			code = "999";
			msg = "system busy, please try later";
			e.printStackTrace();
		}
		rtMap.put("data", cartProducts);
		rtMap.put("code", code);
		rtMap.put("msg", msg);

		return rtMap;
	}
}

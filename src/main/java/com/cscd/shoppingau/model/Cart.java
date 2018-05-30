package com.cscd.shoppingau.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Description
 * @Author Anthony
 * @Date 25/05/2018 3:45 PM
 */
public class Cart {
	private long userId;
	private long productId;
	private long skuId;
	private int quantity;
	private String selectedAttrs;
	private BigDecimal totalPrice;
	private Timestamp createTime;
	private Timestamp lastLoginTime;


	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getSkuId() {
		return skuId;
	}

	public void setSkuId(long skuId) {
		this.skuId = skuId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getSelectedAttrs() {
		return selectedAttrs;
	}

	public void setSelectedAttrs(String selectedAttrs) {
		this.selectedAttrs = selectedAttrs;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
}

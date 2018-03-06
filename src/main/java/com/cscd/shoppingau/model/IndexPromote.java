package com.cscd.shoppingau.model;

import java.sql.Timestamp;

/**
 * @Description
 * @Author Anthony
 * @Date 27/02/2018 11:18 AM
 */
public class IndexPromote {
	private long promoteId;
	private String title;
	private String categories;
	private String products;
	private String promoteImg;
	private String promotePage;
	private Timestamp createTime;

	public long getPromoteId() {
		return promoteId;
	}

	public void setPromoteId(long promoteId) {
		this.promoteId = promoteId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public String getPromoteImg() {
		return promoteImg;
	}

	public void setPromoteImg(String promoteImg) {
		this.promoteImg = promoteImg;
	}

	public String getPromotePage() {
		return promotePage;
	}

	public void setPromotePage(String promotePage) {
		this.promotePage = promotePage;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}

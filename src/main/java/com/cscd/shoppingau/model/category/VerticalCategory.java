package com.cscd.shoppingau.model.category;

import java.sql.Timestamp;

/**
 * @Description
 * @Author Anthony
 * @Date 9/02/2018 10:46 AM
 */
public class VerticalCategory {

	private int categoryId;
	private int parentId;
	private String name;
	private String desc;
	private String url;
	private int status;
	private int isPopular;
	private String background;
	private String icon;
	private int isShowSub;
	private Timestamp updateTime;


	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsPopular() {
		return isPopular;
	}

	public void setIsPopular(int isPopular) {
		this.isPopular = isPopular;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getIsShowSub() {
		return isShowSub;
	}

	public void setIsShowSub(int isShowSub) {
		this.isShowSub = isShowSub;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
}

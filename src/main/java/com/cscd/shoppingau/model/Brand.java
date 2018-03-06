package com.cscd.shoppingau.model;

import java.sql.Timestamp;

/**
 * @Description
 * @Author Anthony
 * @Date 22/02/2018 11:31 AM
 */
public class Brand {
	private long brandId;
	private String name;
	private String imgPath;
	private int status;
	private Timestamp updateTime;

	public long getBrandId() {
		return brandId;
	}

	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
}

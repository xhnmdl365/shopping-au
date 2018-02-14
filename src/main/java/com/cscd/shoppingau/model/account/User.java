package com.cscd.shoppingau.model.account;

import com.cscd.shoppingau.utils.validator.DataUnique;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

/**
 * @Description
 * @Author Anthony
 * @Date 8/01/2018 11:14 AM
 */
public class User {

	@NotNull
	@NotEmpty
	@Length(min = 6, max = 30, message = "The password must be more than 6 and less than 30 characters long")
	private String password;

	@NotNull
	@NotEmpty
	@Pattern(regexp = "[A-Za-z0-9_]+", message = "The username can only consist of alphabetical, number, dot and underscore")
	@Length(min = 6, max = 30, message = "The username must be more than 6 and less than 30 characters long")
//	@DataUnique(keyName = "user_name", dataTable = "user", message = "this username is existed")
	private String username;

	@Email(message = "email address is invalid")
	@DataUnique(keyName = "email", dataTable = "user", message = "this email address is existed")
	private String email;

	private int userId;
	private int status;
	private String firstname;
	private String lastname;
	private String headImg;
	private Timestamp createTime;
	private Timestamp lastLoginTime;
	private String userGroupId;
	private String salt;

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
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

	public String getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {

		return "{userId = " + this.getUserId() + "\n" +
				"username = " + this.getUsername() + "\n" +
				"userGroupId = "+ this.getUserGroupId() + "\n" +
				"email = " + this.getEmail() + "\n" +
				"password = " + this.getPassword() + "\n" +
				"status = " + this.getStatus() + "\n" +
				"firstname = " + this.getFirstname() + "\n" +
				"lastname = " + this.getLastname() + "\n" +
				"headImg = " + this.getHeadImg() + "\n" +
				"createTime = " + this.getCreateTime() + "\n" +
				"lastLoginTime = " + this.getLastLoginTime() + "\n" +
				"salt = " + this.getSalt() + "}";
	}
}

package com.cscd.shoppingau.service.account;

/**
 * @Description
 * @Author Anthony
 * @Date 8/01/2018 11:15 AM
 */

import com.cscd.shoppingau.mapper.account.UserMapper;
import com.cscd.shoppingau.model.account.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;


	@Transactional
	public int saveUser(User user) {
		return userMapper.save(user);
	}
	public List<User> getAllUser() {
		return userMapper.getAllUser();
	}
	public User getUserByEmail(String email) {
		return userMapper.getUserByEmail(email);
	}
	@Transactional
	public int updateLastLoginTime(User user) {
		return userMapper.updateLastLoginTime(user);
	}
}
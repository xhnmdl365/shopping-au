package com.cscd.shoppingau.mapper.account;

import com.cscd.shoppingau.model.account.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Description
 * @Author Anthony
 * @Date 8/01/2018 11:14 AM
 */
@Mapper
public interface UserMapper {

	@Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
	@Insert("insert into user(username, email, password, firstname, lastname," +
			"head_img, salt) values (#{username},#{email},#{password}," +
			"#{firstname},#{lastname},#{headImg},#{salt})")
	int save(User user);

	User findOne(User user);

	@Select("select * from user")
	List<User> getAllUser();

	@Select("select * from user where email = #{email}")
	User getUserByEmail(@Param("email") String email);

	@Update("update user set last_login_time = #{lastLoginTime} where user_id = #{userId}")
	int updateLastLoginTime(User user);
}

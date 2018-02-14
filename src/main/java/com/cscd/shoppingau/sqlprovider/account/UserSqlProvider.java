package com.cscd.shoppingau.sqlprovider.account;

import com.cscd.shoppingau.model.account.User;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Description
 * @Author Anthony
 * @Date 8/01/2018 4:36 PM
 */
public class UserSqlProvider {

	public String addNewUser1(final User user) {

		SQL sql = new SQL(){{
			SELECT("id,name,email");
			FROM("demo");
			if(user.getUsername() != null){
				WHERE("name=#{name}");
			}
			if(user.getEmail() != null){
				WHERE("email=#{email}");
			}
		}};
		return sql.toString();
	}

}

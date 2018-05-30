package com.cscd.shoppingau.shiro;

import com.cscd.shoppingau.model.account.User;
import com.cscd.shoppingau.service.account.UserService;
import com.cscd.shoppingau.config.CredentialsMatcher;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;

/**
 * shiro core class
 * 
 * @author Anthony
 */

public class MyShiroRealm extends AuthorizingRealm {

	private final static Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

	@Autowired
	private UserService userService;
//
//	@Autowired
//	private SysPermissionServiceImpl sysPermissionService;
//
//	@Autowired
//	private SysRoleServiceImpl sysRoleService;

	/**
	 * 认证信息.(身份验证) : Authentication 是用来验证用户身份
	 * 
	 * @param authcToken
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		System.out.println("authentic：MyShiroRealm.doGetAuthenticationInfo()");
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		logger.info("user login : " + token.getUsername());
		User user = userService.getUserByEmail(token.getUsername());
		if(user == null){
			logger.error("user { "+token.getUsername()+" } doesn't exist ");
			throw new AccountException("This account doesn't exist");
		}
		if(user.getStatus() == 0){
			logger.error("user { "+token.getUsername()+" } is disabled ");
			throw new DisabledAccountException("This account is disabled");
		}else{
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			user.setLastLoginTime(timestamp);
			userService.updateLastLoginTime(user);
		}
		return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		System.out.println("权限认证方法：MyShiroRealm.doGetAuthorizationInfo()");

        return null;
	}

	@PostConstruct
	public void initCredentialsMatcher() {
		//该句作用是重写shiro的密码验证，让shiro用我自己的验证
		setCredentialsMatcher(new CredentialsMatcher(userService));
	}

	/**
	 * 清空当前用户权限信息
	 */
	public void clearCachedAuthorizationInfo() {
		PrincipalCollection principalCollection = SecurityUtils.getSubject()
				.getPrincipals();
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 指定principalCollection 清除
	 */
	public void clearCachedAuthorizationInfo(
			PrincipalCollection principalCollection) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}

}

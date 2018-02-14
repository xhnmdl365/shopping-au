package com.cscd.shoppingau.config;

import com.cscd.shoppingau.model.account.User;
import com.cscd.shoppingau.service.account.UserService;
import com.cscd.shoppingau.utils.MD5Util;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CredentialsMatcher extends SimpleCredentialsMatcher {
 
    private final static Logger LOGGER = LoggerFactory.getLogger(CredentialsMatcher.class);


    private UserService userService;
    public CredentialsMatcher(UserService userService) {
        this.userService = userService;
    }

    @Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken authcToken = (UsernamePasswordToken) token;
        User user = userService.getUserByEmail(authcToken.getUsername());
        Object tokenCredentials = MD5Util.getMD5WithSalt(String.valueOf(authcToken.getPassword()) , user.getSalt());
        Object accountCredentials = getCredentials(info);
        return accountCredentials.equals(tokenCredentials);
    }
}
package com.cscd.shoppingau.controller.account;

import com.cscd.shoppingau.model.account.User;
import com.cscd.shoppingau.service.account.UserService;
import com.cscd.shoppingau.shiro.ShiroToken;
import com.cscd.shoppingau.utils.MD5Util;
import com.cscd.shoppingau.utils.Tool;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @Description
 * @Author Anthony
 * @Date 8/01/2018 11:13 AM
 */

@Controller
public class UseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private UserService userService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	public UseController(UserService userService) {
		this.userService = userService;
	}


	@RequestMapping(value={"", "/index"})
	public String index() {
		System.out.println("index:");
		System.out.println(SecurityUtils.getSubject().getPrincipal());
		return "dist/html/index";
	}
	@RequestMapping("/signup")
	public String signUp() {
		return "dist/html/account/signup";
	}


	@PostMapping("/doSignUp")
	@ResponseBody
	public Map doSignUp(@Valid @RequestBody User user, BindingResult result) {
		Map rtMap = new HashMap();
		String code = "000";
		String msg = "success";
		try {
			if(result.hasErrors()) {
				code = "001";
				msg = "data is invalid";
				rtMap.put("errData", Tool.fieldValidHandle(result, messageSource));
			} else {
				// save data
				// get the password salt
				String salt = MD5Util.getSalt();
				user.setSalt(salt);
				user.setPassword(MD5Util.getMD5WithSalt(user.getPassword(), salt));
				int i = userService.saveUser(user);
			}
		} catch(Exception e) {
			code = "999";
			msg = "system busy, please try later";
			e.printStackTrace();
		}
		rtMap.put("code", code);
		rtMap.put("msg", msg);

		return rtMap;
	}

	@RequestMapping("/signin")
	public String login() {
		return "dist/html/account/signin";
	}
	/**
	 * ajax login
	 * @param email
	 * @param password
	 * @return
	 */
	@PostMapping("/doSignIn")
	@ResponseBody
	public Map<String,Object> doLogin(String email, String password) {
		Map rtMap = new HashMap();
		String code = "000";
		String msg = "success";
		try {
			ShiroToken token = new ShiroToken(email, password);
			SecurityUtils.getSubject().login(token);
		}catch(IncorrectCredentialsException incorrectErr) {
			code = "009";
			msg = "email or password error";
		}catch(AccountException accountErr) {
			code = "009";
			msg = "email or password error";
		}catch (Exception e) {
			code = "999";
			msg = "system busy, please try later";
		}
		rtMap.put("code", code);
		rtMap.put("msg", msg);
		return rtMap;
	}

	/**
	 * logout
	 * @return
	 */
	@RequestMapping(value = "logout",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> logout(){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			//
			SecurityUtils.getSubject().logout();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return resultMap;
	}
}

package com.cscd.shoppingau.controller.slider;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Anthony
 * @Date 14/02/2018 12:39 PM
 */

@RestController
public class SliderController {


	@PostMapping("/getSlider")
	public Map getSlider(String name) {

		Map rtMap = new HashMap();
		String code = "000";
		String msg = "success";
		try {
			List list = new ArrayList();
			rtMap.put("data", list);
		} catch(Exception e) {
			code = "999";
			msg = "system busy, please try later";
			e.printStackTrace();
		}
		rtMap.put("code", code);
		rtMap.put("msg", msg);

		return rtMap;
	}
}

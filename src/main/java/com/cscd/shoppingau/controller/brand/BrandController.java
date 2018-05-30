package com.cscd.shoppingau.controller.brand;

import com.cscd.shoppingau.model.Brand;
import com.cscd.shoppingau.service.brand.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Anthony
 * @Date 22/02/2018 11:33 AM
 */

@RestController
public class BrandController {

	@Autowired
	private BrandService brandService;


	@PostMapping("/getAllBrands")
	public Map getAllBrands() {


		Map rtMap = new HashMap();
		String code = "000";
		String msg = "success";
		try {
//			PageHelper.startPage(1, 2);
			List list = brandService.getAllBrands();
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
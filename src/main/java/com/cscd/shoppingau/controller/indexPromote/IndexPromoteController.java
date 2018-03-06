package com.cscd.shoppingau.controller.indexPromote;

import com.cscd.shoppingau.service.indexPromote.IndexPromoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Anthony
 * @Date 27/02/2018 2:51 PM
 */
@RestController
public class IndexPromoteController {

	@Autowired
	private IndexPromoteService indexPromoteService;

	@RequestMapping("/getIndexPromoteList")
	public Map getAllBrands() {

		Map rtMap = new HashMap();
		String code = "000";
		String msg = "success";
		try {
			List list = indexPromoteService.getIndexPromoteList();
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

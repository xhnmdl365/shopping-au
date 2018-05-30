package com.cscd.shoppingau.service.indexPromote;

import com.cscd.shoppingau.mapper.indexPromote.IndexPromoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author Anthony
 * @Date 27/02/2018 2:47 PM
 */
@Service
public class IndexPromoteService {

	@Autowired
	private IndexPromoteMapper indexPromoteMapper;

	@Cacheable(value = "indexPromoteList", key="#root.methodName")
	public List getIndexPromoteList() {
		return indexPromoteMapper.getIndexPromoteList();
	}

}

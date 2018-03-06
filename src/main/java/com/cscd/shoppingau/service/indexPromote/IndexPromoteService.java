package com.cscd.shoppingau.service.indexPromote;

import com.cscd.shoppingau.mapper.indexPromote.IndexPromoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

	public List getIndexPromoteList() {
		return indexPromoteMapper.getIndexPromoteList();
	}

}
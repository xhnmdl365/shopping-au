package com.cscd.shoppingau.mapper.indexPromote;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;


/**
 * @Description
 * @Author Anthony
 * @Date 8/01/2018 11:14 AM
 */
@Mapper
public interface IndexPromoteMapper {


	@Select("select title, categories, products, promote_img, promote_page from index_promote")
	List<Map> getIndexPromoteList();
}

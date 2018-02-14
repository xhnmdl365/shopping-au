package com.cscd.shoppingau.mapper.utils;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Anthony
 * @Date 30/01/2018 3:52 PM
 */


public interface SqlMapper {

	@Select("select ${keyName} from ${dataTable} where ${keyName}= #{value}")
	public List<String> getKeyFromTable(@Param("keyName") String keyName,
										@Param("dataTable") String dataTable,
										@Param("value") String value);
}
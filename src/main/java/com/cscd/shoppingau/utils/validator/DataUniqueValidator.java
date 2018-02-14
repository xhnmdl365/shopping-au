package com.cscd.shoppingau.utils.validator;

import com.cscd.shoppingau.mapper.utils.SqlMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * @Description
 * @Author Anthony
 * @Date 30/01/2018 3:04 PM
 */
public class DataUniqueValidator implements ConstraintValidator<DataUnique, Object> {

	private String keyName;
	private String dataTable;

	@Autowired
	private SqlMapper sqlMapper;

	@Override
	public void initialize(DataUnique dataUnique) {
		this.keyName = dataUnique.keyName();
		this.dataTable = dataUnique.dataTable();
	}

	// do validate
	@Override
	public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {

		List keyList = sqlMapper.getKeyFromTable(keyName, dataTable, (String)o);
		if(keyList != null && !keyList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
}

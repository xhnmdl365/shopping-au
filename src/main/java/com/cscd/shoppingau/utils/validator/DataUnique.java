package com.cscd.shoppingau.utils.validator;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @Description
 * @Author Anthony
 * @Date 30/01/2018 3:00 PM
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Constraint(validatedBy = DataUniqueValidator.class)
public @interface DataUnique {

	String keyName();
	String dataTable();

	String message() default "data is existed";
	Class<?>[] groups() default {};

	Class<? extends Payload> []payload() default {};
}

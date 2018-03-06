package com.cscd.shoppingau;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author Anthony
 * @Date 20/12/2017 4:45 PM
 */

@SpringBootApplication
@MapperScan("com.cscd.shoppingau.mapper")
@EnableCaching
public class ShoppingauApp {
	@Bean
	public HttpMessageConverters FastJsonHttpMessageConverters() {
		// 1、define convert object
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

		// 2、add fastJson config infomation，eg.if we need to format json
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(
				SerializerFeature.PrettyFormat
		);
		// deal with Chinese String
		List<MediaType> fastMediaTypes = new ArrayList();
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		fastConverter.setSupportedMediaTypes(fastMediaTypes);

		// 3、add config into convert
		fastConverter.setFastJsonConfig(fastJsonConfig);
		HttpMessageConverter<?> converter = fastConverter;
		return new HttpMessageConverters(converter);
	}

	public static void main(String[] args) {
		SpringApplication.run(ShoppingauApp.class);
	}


}


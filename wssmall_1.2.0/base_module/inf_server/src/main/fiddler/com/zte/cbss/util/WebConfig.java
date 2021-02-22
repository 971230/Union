package com.zte.cbss.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class WebConfig {

	@Bean
	public AbstractHandlerMapping getHandlerMapping() {
		AbstractHandlerMapping handlerMapping = new ControllerClassNameHandlerMapping();
		handlerMapping.setInterceptors(new Object[]{getLocaleChangeInterceptor()});
		return handlerMapping;
	}
	
	@Bean
	public HandlerAdapter getHandlerAdapter() {
		AnnotationMethodHandlerAdapter adapter = new AnnotationMethodHandlerAdapter();
		MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
		/*添加controller的json支持*/
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.getSerializationConfig().setDateFormat(new SimpleDateFormat(DateUtil.FULL_PATTERN));
		converter.setObjectMapper(objectMapper);
		
		List<MediaType> types = new ArrayList<MediaType>();
		types.add(MediaType.APPLICATION_JSON);
		types.add(MediaType.APPLICATION_OCTET_STREAM);
		types.add(MediaType.MULTIPART_FORM_DATA);
		types.add(MediaType.TEXT_PLAIN);
		types.add(MediaType.parseMediaType("text/json"));
		converter.setSupportedMediaTypes(types);
		adapter.setMessageConverters(new HttpMessageConverter[]{converter});
		//设置数据提交转换器
		adapter.setWebBindingInitializer(new CustomWebBindingInitializer());
		return adapter;
	}

	/**
	 * @return <br/>
	 *         添加文件上传的支持
	 */
	@Bean(name = { "multipartResolver" })
	public MultipartResolver getMultipartResolver() {
		CommonsMultipartResolver mr = new CommonsMultipartResolver();
		mr.setMaxInMemorySize(1);
		return mr;
	}
	
	@Bean(name = {"propertyConfigurer"})
	public CustomPropertyPlaceholderConfigurer getPropertyConfigurer() {
		CustomPropertyPlaceholderConfigurer propertyConfigurer = new CustomPropertyPlaceholderConfigurer();
		Resource[] resources = new Resource[]{new ClassPathResource("common.properties")}; 
		propertyConfigurer.setLocations(resources);
		propertyConfigurer.setFileEncoding("UTF-8");
		return propertyConfigurer;
	}
	
	@Bean(name={"localeChangeInterceptor"})
	public LocaleChangeInterceptor getLocaleChangeInterceptor() {
		return new LocaleChangeInterceptor();
	}
	
	@Bean(name={"localeResolver"})
	public LocaleResolver getLocaleResolver() {
		return new SessionLocaleResolver();
	}
	
	@Bean(name={"viewResolver"})
	public ViewResolver getJspViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setOrder(1);
		viewResolver.setPrefix("/page/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
}

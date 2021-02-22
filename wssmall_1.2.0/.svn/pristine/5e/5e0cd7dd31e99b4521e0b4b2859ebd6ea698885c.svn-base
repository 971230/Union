package com.ztesoft.rop.client;


import com.ztesoft.rop.annotation.IgnoreSign;
import com.ztesoft.rop.annotation.Temporary;
import com.ztesoft.rop.client.unmarshaller.JacksonJsonRopUnmarshaller;
import com.ztesoft.rop.client.unmarshaller.JaxbXmlRopUnmarshaller;
import com.ztesoft.rop.common.DefaultRopContext;
import com.ztesoft.rop.common.RopRequest;
import com.ztesoft.rop.common.SystemParameterNames;
import com.ztesoft.rop.marshaller.MessageMarshallerUtils;
import com.ztesoft.rop.request.RopConverter;
import com.ztesoft.rop.response.ErrorResponse;
import com.ztesoft.rop.utils.RopUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class DefaultClientRequest implements ClientRequest {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private RopClient ropClient;

	private Map<String, String> paramMap = new HashMap<String, String>(20);

	private List<String> ignoreSignParams = new ArrayList<String>();

	private RopUnmarshaller xmlUnmarshaller = new JaxbXmlRopUnmarshaller();

	private RopUnmarshaller jsonUnmarshaller = new JacksonJsonRopUnmarshaller();

	private RestTemplate restTemplate = new RestTemplate();
	// 应用密钥
	private String appSecret;
	private String serverUrl;
	// 应用键
	private String appKey;	
	private Locale locale = Locale.SIMPLIFIED_CHINESE;
	private String msgType = "";

	// 请求类所有请求参数
	private Map<Class<?>, List<Field>> requestAllFields = new HashMap<Class<?>, List<Field>>();

	// 请求类所有不需要进行签名的参数
	private Map<Class<?>, List<String>> requestIgnoreSignFieldNames = new HashMap<Class<?>, List<String>>();

	// 键为转换的目标类型
	private static Map<Class<?>, RopConverter<String, ?>> ropConverterMap = new HashMap<Class<?>, RopConverter<String, ?>>();

	public DefaultClientRequest(RopClient ropClient, String appKey,String msgType, Locale locale, String appSecret,
			String serverUrl) {
		//
		this.ropClient = ropClient;
		//
		this.appKey = appKey;
		this.msgType = msgType;		
		this.locale = locale;
		this.appSecret = appSecret;
		this.serverUrl = serverUrl;				
		//
		paramMap.put(SystemParameterNames.getAppKey(), appKey);
		paramMap.put(SystemParameterNames.getFormat(), msgType);
		paramMap.put(SystemParameterNames.getLocale(), locale.toString());
		
		/*List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
		
		StringHttpMessageConverter messageConverter = new StringHttpMessageConverter();
		List<MediaType> mediaList = new ArrayList<MediaType>();
		mediaList.add(MediaType.parseMediaType("text/html;utf-8"));
		messageConverter.setSupportedMediaTypes(mediaList);
		
		messageConverters.add(messageConverter);
		
		restTemplate.setMessageConverters(messageConverters);*/		
		
	}

	@Override
	public ClientRequest addParam(String paramName, Object paramValue) {
		// TODO Auto-generated method stub
		addParam(paramName, paramValue, false);
		return this;
	}

	@Override
	public ClientRequest addParam(String paramName, Object paramValue,
			boolean ignoreSign) {
		// TODO Auto-generated method stub
		Assert.isTrue(paramName != null && paramName.length() > 0, "参数名不能为空");
		Assert.notNull(paramValue, "参数值不能为null");
		
		// 将参数添加到参数列表中
		String valueAsStr = paramValue.toString();

		paramMap.put(paramName, valueAsStr);

		IgnoreSign typeIgnore = AnnotationUtils.findAnnotation(
				paramValue.getClass(), IgnoreSign.class);
		if (ignoreSign || typeIgnore != null) {
			ignoreSignParams.add(paramName);
		}
		//
		return this;

	}

	@Override
	public ClientRequest clearParam() {
		// TODO Auto-generated method stub
		paramMap.clear();
		return this;
	}

	/**
	 * 
	 */
	@Override
	public <T> CompositeResponse post(Class<T> ropResponseClass,
			String methodName, String version) {
		Map<String, String> requestParams = addOtherParamMap(methodName,
				version);
		return post(ropResponseClass, requestParams);
	}

	/**
     * 
     */
	@Override
	public <T> CompositeResponse post(RopRequest ropRequest,
			Class<T> ropResponseClass, String methodName, String version) {
		Map<String, String> requestParams = getRequestForm(ropRequest,
				methodName, version);
		return post(ropResponseClass, requestParams);
	}

	/**
	 * 
	 * @param ropResponseClass
	 * @param requestParams
	 * @return
	 */
	private <T> CompositeResponse post(Class<T> ropResponseClass,
			Map<String, String> requestParams) {
		
		String responseContent = restTemplate.postForObject(serverUrl,
				toMultiValueMap(requestParams), String.class);
		if (logger.isDebugEnabled()) {
			logger.debug("response:\n" + responseContent);
		}
		return toCompositeResponse(responseContent, ropResponseClass);
	}

	/**
     * 
     */
	@Override
	public <T> CompositeResponse get(Class<T> ropResponseClass,
			String methodName, String version) {
		Map<String, String> requestParams = addOtherParamMap(methodName,
				version);
		return get(ropResponseClass, requestParams);
	}

	@Override
	public <T> CompositeResponse get(RopRequest ropRequest,
			Class<T> ropResponseClass, String methodName, String version) {
		Map<String, String> requestParams = getRequestForm(ropRequest,
				methodName, version);
		return get(ropResponseClass, requestParams);
	}

	private <T> CompositeResponse get(Class<T> ropResponseClass,
			Map<String, String> requestParams) {
		String responseContent = restTemplate.getForObject(
				buildGetUrl(requestParams), String.class, requestParams);
		if (logger.isDebugEnabled()) {
			logger.debug("response:\n" + responseContent);
		}
		return toCompositeResponse(responseContent, ropResponseClass);
	}

	private Map<String, String> addOtherParamMap(String methodName,
			String version) {
		paramMap.put(SystemParameterNames.getMethod(), methodName);
		paramMap.put(SystemParameterNames.getVersion(), version);
		String signValue = RopUtils.sign(paramMap, ignoreSignParams, appSecret);
		paramMap.put(SystemParameterNames.getSign(), signValue);
		return paramMap;
	}

	private <T> CompositeResponse toCompositeResponse(String content,
			Class<T> ropResponseClass) {
		logger.info("content==" + content);
		boolean successful = isSuccessful(content);
		DefaultCompositeResponse<T> compositeResponse = new DefaultCompositeResponse<T>(
				successful);

		if (msgType.equals("json")) {
			if (successful) {
				T ropResponse = jsonUnmarshaller.unmarshaller(content,
						ropResponseClass);
				compositeResponse.setSuccessRopResponse(ropResponse);
			} else {
				ErrorResponse errorResponse = jsonUnmarshaller.unmarshaller(
						content, ErrorResponse.class);
				compositeResponse.setErrorResponse(errorResponse);
			}
		} else {
			if (successful) {
				T ropResponse = xmlUnmarshaller.unmarshaller(content,ropResponseClass);
				compositeResponse.setSuccessRopResponse(ropResponse);
			} else {
				ErrorResponse errorResponse = xmlUnmarshaller.unmarshaller(
						content, ErrorResponse.class);
				compositeResponse.setErrorResponse(errorResponse);
			}
		}
		return compositeResponse;
	}

	private final String jsonErrorFlag = "\"errorflag\":\"-100\"";
	private boolean isSuccessful(String content) {
		if (msgType.equals("json")) {
			return !(content.contains(jsonErrorFlag));
		} else {
			return !(content.contains("<errorflag"));
		}
	}

	private String buildGetUrl(Map<String, String> form) {
		StringBuilder requestUrl = new StringBuilder();
		requestUrl.append(serverUrl);
		requestUrl.append("?");
		String joinChar = "";
		for (Map.Entry<String, String> entry : form.entrySet()) {
			requestUrl.append(joinChar);
			requestUrl.append(entry.getKey());
			requestUrl.append("=");
			requestUrl.append(entry.getValue());
			joinChar = "&";
		}
		return requestUrl.toString();
	}

	private Map<String, String> getRequestForm(RopRequest ropRequest,
			String methodName, String version) {

		Map<String, String> form = new LinkedHashMap<String, String>(16);

		// 系统级参数
		form.put(SystemParameterNames.getAppKey(), appKey);
		form.put(SystemParameterNames.getMethod(), methodName);
		form.put(SystemParameterNames.getVersion(), version);
		form.put(SystemParameterNames.getFormat(), msgType);
		form.put(SystemParameterNames.getLocale(), locale.toString());
		
		// 业务级参数
		form.putAll(getParamFields(ropRequest, msgType));

		// 对请求进行签名
		String signValue = sign(ropRequest.getClass(), appSecret, form);
		form.put("sign", signValue);
		return form;
	}

	private MultiValueMap<String, String> toMultiValueMap(
			Map<String, String> form) {
		MultiValueMap<String, String> mvm = new LinkedMultiValueMap<String, String>();
		for (Map.Entry<String, String> entry : form.entrySet()) {
			String value = entry.getValue();			
			/* try {
				value = new String(entry.getValue().getBytes("gbk"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				value = entry.getValue();
			}
			*/
			mvm.add(entry.getKey(), value);
		}
		return mvm;
	}

	/**
	 * 对请求参数进行签名
	 * 
	 * @param ropRequestClass
	 * @param appSecret
	 * @param form
	 * @return
	 */
	private String sign(Class<?> ropRequestClass, String appSecret,
			Map<String, String> form) {
		List<String> ignoreFieldNames = requestIgnoreSignFieldNames
				.get(ropRequestClass);
		return RopUtils.sign(form, ignoreFieldNames, appSecret);
	}

	/**
	 * 获取ropRequest对应的参数名列表
	 * 
	 * @param ropRequest
	 * @param mf
	 * @return
	 */
	private Map<String, String> getParamFields(RopRequest ropRequest, String mf) {
		if (!requestAllFields.containsKey(ropRequest.getClass())) {
			parseRopRequestClass(ropRequest);
		}
		return toParamValueMap(ropRequest, mf);
	}

	/**
	 * 获取ropRequest对象的对应的参数列表
	 * 
	 * @param ropRequest
	 * @param mf
	 * @return
	 */
	private Map<String, String> toParamValueMap(RopRequest ropRequest, String mf) {
		List<Field> fields = requestAllFields.get(ropRequest.getClass());
		Map<String, String> params = new HashMap<String, String>();
		for (Field field : fields) {
			RopConverter convertor = getConvertor(field.getType());
			Object fieldValue = ReflectionUtils.getField(field, ropRequest);
			if (fieldValue != null) {
				if (convertor != null) {// 有对应转换器
					String strParamValue = (String) convertor.unconvert(fieldValue);
					params.put(field.getName(), strParamValue);
				} else if (field.getType().isAnnotationPresent(XmlRootElement.class) || field.getType().isAnnotationPresent(XmlType.class)) {
					String message = MessageMarshallerUtils.getMessage(fieldValue, mf);
					params.put(field.getName(), message);
				} else {
					params.put(field.getName(), fieldValue.toString());
				}
			}
		}
		return params;
	}

	private RopConverter getConvertor(Class<?> fieldType) {
		for (Class<?> aClass : ropConverterMap.keySet()) {
			if (ClassUtils.isAssignable(aClass, fieldType)) {
				return ropConverterMap.get(aClass);
			}
		}
		return null;
	}

	private void parseRopRequestClass(RopRequest ropRequest) {
		final ArrayList<Field> allFields = new ArrayList<Field>();
		final List<String> ignoreSignFieldNames = DefaultRopContext
				.getIgnoreSignFieldNames(ropRequest.getClass());
		ReflectionUtils.doWithFields(ropRequest.getClass(),
				new ReflectionUtils.FieldCallback() {
					@Override
					public void doWith(Field field)
							throws IllegalArgumentException,
							IllegalAccessException {
						ReflectionUtils.makeAccessible(field);
						if (!isTemporaryField(field)) {
							allFields.add(field);
						}
					}

					private boolean isTemporaryField(Field field) {
						Annotation[] declaredAnnotations = field
								.getDeclaredAnnotations();
						if (declaredAnnotations != null) {
							for (Annotation declaredAnnotation : declaredAnnotations) {
								Temporary varTemporary = field
										.getAnnotation(Temporary.class);
								if (varTemporary != null) {
									return true;
								}
							}
						}
						return false;
					}
				});

		requestAllFields.put(ropRequest.getClass(), allFields);
		requestIgnoreSignFieldNames.put(ropRequest.getClass(),
				ignoreSignFieldNames);
	}

	/**
	 * 获取ropRequest对应的参数名列表
	 * 
	 * @param ropRequest
	 * @param mf
	 * @return
	 */
	private Map<String, String> getParamFields(RopRequest ropRequest) {
		if (!requestAllFields.containsKey(ropRequest.getClass())) {
			parseRopRequestClass(ropRequest);
		}
		return toParamValueMap(ropRequest, msgType);
	}

}

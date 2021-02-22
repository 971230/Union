package com.ztesoft.rop.route.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.ConstructorUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.ServletRequestDataRopBinder;

import com.ztesoft.common.util.XmlUtils;
import com.ztesoft.parameters.AbstractRopRequest;
import com.ztesoft.rop.annotation.HttpAction;
import com.ztesoft.rop.common.MessageFormat;
import com.ztesoft.rop.common.RequestContextBuilder;
import com.ztesoft.rop.common.RopContext;
import com.ztesoft.rop.common.RopRequest;
import com.ztesoft.rop.common.RopRequestContext;
import com.ztesoft.rop.common.ServiceMethodHandler;
import com.ztesoft.rop.common.SystemParameterNames;
import com.ztesoft.rop.response.MainErrorType;
import com.ztesoft.rop.response.MainErrors;
import com.ztesoft.rop.session.SessionManager;
import commons.CommonTools;

/**
 * <pre>
 *    构建{@link com.rop.RopRequestContext}实例
 * </pre>
 * 
 * @author 陈雄华
 * @version 1.0
 */
public class ServletRequestContextBuilder implements RequestContextBuilder {

	// 通过前端的负载均衡服务器时，请求对象中的IP会变成负载均衡服务器的IP，因此需要特殊处理，下同。
	public static final String X_REAL_IP = "X-Real-IP";

	public static final String X_FORWARDED_FOR = "X-Forwarded-For";

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private FormattingConversionService conversionService;

	private Validator validator;

	private SessionManager sessionManager;

	public ServletRequestContextBuilder(FormattingConversionService conversionService,SessionManager sessionManager) {
		this.conversionService = conversionService;
		this.sessionManager = sessionManager;
	}

	@Override
	public SimpleRopRequestContext buildBySysParams(RopContext ropContext,Object request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new IllegalArgumentException("请求对象必须是HttpServletRequest的类型");
		}

		HttpServletRequest servletRequest = (HttpServletRequest) request;
		SimpleRopRequestContext requestContext = new SimpleRopRequestContext(ropContext);

		// 设置请求对象及参数列表
		requestContext.setRawRequestObject(servletRequest);
		requestContext.setAllParams(getRequestParams(servletRequest));
		requestContext.setIp(getRemoteAddr(servletRequest)); // 感谢melin所指出的BUG

		// 设置服务的系统级参数
		requestContext.setAppKey(servletRequest.getParameter(SystemParameterNames.getAppKey()));
		//requestContext.setSessionId(servletRequest.getParameter(SystemParameterNames.getSessionId()));
		requestContext.setMethod(servletRequest.getParameter(SystemParameterNames.getMethod()));
		String version =servletRequest.getParameter(SystemParameterNames.getVersion());
		if(com.ztesoft.ibss.common.util.StringUtils.isEmpty(version))
			version="1.0";
		requestContext.setVersion(version);
		requestContext.setLocale(getLocale(servletRequest));
		requestContext.setFormat(getFormat(servletRequest));
		requestContext.setMessageFormat(getResponseFormat(servletRequest));
		requestContext.setSign(servletRequest.getParameter(SystemParameterNames.getSign()));
		requestContext.setHttpAction(HttpAction.fromValue(servletRequest.getMethod()));

		// 设置服务处理器
		ServiceMethodHandler serviceMethodHandler = ropContext.getServiceMethodHandler(requestContext.getMethod(),requestContext.getVersion());
		requestContext.setServiceMethodHandler(serviceMethodHandler);

		return requestContext;
	}

	private String getRemoteAddr(HttpServletRequest request) {
		String remoteIp = request.getHeader(X_REAL_IP); // nginx反向代理
		if (StringUtils.hasText(remoteIp)) {
			return remoteIp;
		} else {
			remoteIp = request.getHeader(X_FORWARDED_FOR);// apache反射代理
			if (StringUtils.hasText(remoteIp)) {
				String[] ips = remoteIp.split(",");
				for (String ip : ips) {
					if (!"null".equalsIgnoreCase(ip)) {
						return ip;
					}
				}
			}
			return request.getRemoteAddr();
		}
	}

	/**
	 * 将{@link HttpServletRequest}的数据绑定到{@link com.rop.RopRequestContext}的
	 * {@link com.rop.RopRequest}中，同时使用 JSR 303对请求数据进行校验，将错误信息设置到
	 * {@link com.rop.RopRequestContext}的属性列表中。
	 * 
	 * @param ropRequestContext
	 */
	@Override
	public void bindBusinessParams(RopRequestContext ropRequestContext) {
		AbstractRopRequest ropRequest = null;
		if (ropRequestContext.getServiceMethodHandler().isRopRequestImplType()) {
			try {
			HttpServletRequest request = (HttpServletRequest) ropRequestContext.getRawRequestObject();
			BindingResult bindingResult = doBind(request, ropRequestContext.getServiceMethodHandler().getRequestType(),ropRequestContext);
			ropRequest = buildRopRequestFromBindingResult(ropRequestContext,bindingResult);
			
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			ropRequestContext.setAttribute(SimpleRopRequestContext.SPRING_VALIDATE_ERROR_ATTRNAME,allErrors);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		} else {
			ropRequest = new DefaultRopRequest();
		}
		ropRequest.setRopRequestContext(ropRequestContext);
		ropRequestContext.setRopRequest(ropRequest);
	}

	/**
	 * 
	 * @param servletRequest
	 * @return
	 */
	private String getFormat(HttpServletRequest servletRequest) {
		
		return getClientParamFormat(servletRequest);
	}
		
    private String getClientParamFormat(HttpServletRequest servletRequest) {
    	
    	Map srcParamMap = servletRequest.getParameterMap();
    	if(null == srcParamMap || srcParamMap.isEmpty()) {
    		return "json";
    	}
    	
    	String[] values = (String[]) srcParamMap.get("format");  //(SystemParameterNames.getFormat());
		if (values != null && values.length > 0) {
			return values[0];
		}else{
			values = (String[]) srcParamMap.get("messageFormat");  
			if (values != null && values.length > 0) {
				return values[0];
			}
		}
			
		return "json";
    	
    }

	/**
	 * 
	 * @param webRequest
	 * @return
	 */
	public static Locale getLocale(HttpServletRequest webRequest) {
		if (webRequest.getParameter(SystemParameterNames.getLocale()) != null) {
			try {
				LocaleEditor localeEditor = new LocaleEditor();
				localeEditor.setAsText(webRequest
						.getParameter(SystemParameterNames.getLocale()));
				Locale locale = (Locale) localeEditor.getValue();
				if (isValidLocale(locale)) {
					return locale;
				}
			} catch (Exception e) {
				return Locale.SIMPLIFIED_CHINESE;
			}
		}
		return Locale.SIMPLIFIED_CHINESE;
	}

	/**
	 * 
	 * @param locale
	 * @return
	 */
	private static boolean isValidLocale(Locale locale) {
		if (Locale.SIMPLIFIED_CHINESE.equals(locale)
				|| Locale.ENGLISH.equals(locale)) {
			return true;
		} else {
			try {
				MainErrors.getError(MainErrorType.INVALID_APP_KEY, locale);
			} catch (Exception e) {
				return false;
			}
			return true;
		}
	}

	/**
	 * 
	 * @param servletRequest
	 * @return
	 */
	public static String getResponseFormat(HttpServletRequest servletRequest) {
		
		Map srcParamMap = servletRequest.getParameterMap();
		
		if(null == srcParamMap || srcParamMap.isEmpty()) {
    		return "json";
    	}
		
	/*	String messageFormat = (String) srcParamMap.get(SystemParameterNames
				.getFormat());*/
		
		String[] values = (String[]) srcParamMap.get("format");
		if (values != null && values.length > 0) {
			return values[0];
		}else{
			values = (String[]) srcParamMap.get("messageFormat");
			if (values != null && values.length > 0) {
				return values[0];
			}
		}
			
		return "json";
		
		/*if (null != messageFormat && !"".equals(messageFormat)) {
			return messageFormat;
		} else {
			return "xml";
		}*/
	}

	private AbstractRopRequest buildRopRequestFromBindingResult(
			RopRequestContext ropRequestContext, BindingResult bindingResult) {
		AbstractRopRequest ropRequest = (AbstractRopRequest) bindingResult
				.getTarget();
		if (ropRequest instanceof AbstractRopRequest) {
			AbstractRopRequest abstractRopRequest = ropRequest;
			abstractRopRequest.setRopRequestContext(ropRequestContext);
		} else {
			logger.warn(ropRequest.getClass().getName() + "不是扩展于"
					+ AbstractRopRequest.class.getName() + ",无法设置"
					+ RopRequestContext.class.getName());
		}
		return ropRequest;
	}

	private HashMap<String, String> getRequestParams(HttpServletRequest request) {
		Map srcParamMap = request.getParameterMap();
		//String encoding = request.getCharacterEncoding();
		HashMap<String, String> destParamMap = new HashMap<String, String>(
				srcParamMap.size());
	
		for (Object obj : srcParamMap.keySet()) {
			String[] values = (String[]) srcParamMap.get(obj);
			if (values != null && values.length > 0) {
				String valuesinge = values[0];
				//try{
				//	valuesinge = values[0];
					//String value =  new String(values[0].getBytes("utf-8"),"iso-8859-1");
				logger.debug(obj+"::"+valuesinge);
//					logger.info(valuesinge);
				//} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
				//	valuesinge = values[0];
				//}
				
				destParamMap.put((String) obj, valuesinge);
			} else {
				destParamMap.put((String) obj, null);
			}
		}
		
		return destParamMap;
	}

	private BindingResult doBind(HttpServletRequest webRequest,
			Class<? extends RopRequest> requestType,RopRequestContext ropRequestContext) {
		RopRequest bindObject = BeanUtils.instantiateClass(requestType);
		
		//add by wui fastjson数据拷贝
		if(MessageFormat.fjson.name().equals(ropRequestContext.getFormat())) // add by wui fastjson转换
		{
			//转换入参
			String param_json = webRequest.getParameter("param_json");
			bindObject = CommonTools.jsonToBean(param_json,requestType);
		}else if(MessageFormat.httpjson.name().equals(ropRequestContext.getFormat())) { //httpjson map转换为内部对象
			String param_json = webRequest.getParameter("param_json");
			Map httpReqMapNew = CommonTools.jsonToBean(param_json, Map.class);
			Object object;
			try {
				object = ConstructorUtils.invokeConstructor(Class.forName((String)httpReqMapNew.get("class")), null);
				com.ztesoft.common.util.BeanUtils.mapToBean(httpReqMapNew, object);
				bindObject = (RopRequest) object;
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}else if(MessageFormat.httpxml.name().equals(ropRequestContext.getFormat())){
			String param_json = webRequest.getParameter("param_json");
			Map httpReqMapNew = CommonTools.jsonToBean(param_json, Map.class);
			Object object;
			try{
				object = ConstructorUtils.invokeConstructor(Class.forName((String)httpReqMapNew.get("class")), null);
				String xmlString = httpReqMapNew.get("xmlStr").toString();
				Document doc = DocumentHelper.parseText(xmlString);
				Map map = XmlUtils.Dom2Map(doc);
				httpReqMapNew.put("xmlMap", map);
				com.ztesoft.common.util.BeanUtils.mapToBean(httpReqMapNew, object);
				bindObject = (RopRequest) object;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		ServletRequestDataRopBinder dataBinder = new ServletRequestDataRopBinder(bindObject, "bindObject");
		dataBinder.setConversionService(getFormattingConversionService());
		//dataBinder.setValidator(getValidator());
		dataBinder.bind(webRequest);
		try {
			//dataBinder.validate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dataBinder.getBindingResult();
	}

	private Validator getValidator() {
		if (this.validator == null) {
			try {
				LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
				localValidatorFactoryBean.afterPropertiesSet();
				this.validator = localValidatorFactoryBean;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return this.validator;
	}

	public FormattingConversionService getFormattingConversionService() {
		return conversionService;
	}

	// 默认的{@link RopRequest}实现类
	private class DefaultRopRequest extends AbstractRopRequest {
	}
}

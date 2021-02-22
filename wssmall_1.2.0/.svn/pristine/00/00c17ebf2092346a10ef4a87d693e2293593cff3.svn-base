package com.ztesoft.net.eop.processor.backend;

import com.ztesoft.net.eop.processor.Processor;
import com.ztesoft.net.eop.processor.core.Response;
import com.ztesoft.net.eop.processor.core.StringResponse;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.plugin.IAjaxExecuteEnable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 插件异步执行器
 * @author kingapex
 *
 */
public class AjaxPluginProcessor implements Processor {

	@Override
	public Response process(int mode, HttpServletResponse httpResponse,
			HttpServletRequest httpRequest) {
		
		Response response  = new StringResponse();
		String beanid = httpRequest.getParameter("beanid");
		Object plugin = SpringContextHolder.getBean(beanid);
		
		/**
		 * 有效性验验
		 */
		if(plugin==null){
			response.setContent("error:plugin is null");
			return response;
		}
		
		if(! (plugin instanceof IAjaxExecuteEnable )){
			response.setContent("error:plugin is not a IAjaxExecuteEnable");
			return  response;
		}
		
		IAjaxExecuteEnable ajaxPlugin = (IAjaxExecuteEnable)plugin;
		
		String content = ajaxPlugin.execute();		
		response.setContent(content);
		return response;
	}

}

package com.ztesoft.form.processor;

import com.ztesoft.face.comm.FaceContext;
import com.ztesoft.form.LocalRequest;
import com.ztesoft.form.Request;
import com.ztesoft.form.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author kingapex
 * @version 1.0
 */
public abstract class Processor {

	/**
	 * 
	 * @param model
	 * @param httpResponse
	 * @param httpRequest
	 */
	
	public HttpServletResponse httpResponse;
	public HttpServletRequest httpRequest;
	
	public  abstract Response process() throws Exception;
	
	
	public Object getObj(String name){
		return FaceContext.get().getMap().get(name)==null?
				FaceContext.get().getMap().get(name.toUpperCase()):
					FaceContext.get().getMap().get(name);
	}
	
	public String getVal(String name){
		return getObj(name)==null?"":(String)getObj(name);
	}
	
	public void setVal(String name,Object value){
		FaceContext.get().getMap().put(name.toUpperCase(), value);
	}
	
	
	
	/**
	 * 获取参数
	 * @param httpRequest
	 * @return
	 */
	public Map getParamter()
	{
		return httpRequest.getParameterMap();
	}
	
	
	/**
	 * 获取参数
	 * @param httpRequest
	 * @return
	 */
	public String getParamter(String name)
	{
		 Map parMap = httpRequest.getParameterMap();
		 Object value  =parMap.get(name);
		 String p_value ="";
		 if(value instanceof String){
			 p_value =  (String)value;
		 }
		 if(value instanceof String[]){
			 String[] values = (String[])value;
			 String prefix =",";
			 for (int i = 0; i < values.length; i++) {
				 if(i==values.length-1)
					 prefix ="";
				 p_value+=values[i]+prefix;
			}
		 }
		 
		 return p_value;
		 
	}
	
	
	public Response toUrl(String url){
		Request request = new LocalRequest();
		Response response = request.execute(url, httpResponse,httpRequest);
		return response;
	}


	public HttpServletResponse getHttpResponse() {
		return httpResponse;
	}


	public void setHttpResponse(HttpServletResponse httpResponse) {
		this.httpResponse = httpResponse;
	}


	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}


	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}
	
	

}

package com.ztesoft.net.eop.processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;

public class XssHttpRequestWrapper extends HttpServletRequestWrapper {
	private static Logger logger = Logger.getLogger(XssHttpRequestWrapper.class);
    public XssHttpRequestWrapper(HttpServletRequest request) {
        super(request);
    }
    
    @Override
	public String getQueryString() {   
        return HetaoBlogXssHTMLFilter.filter(super.getQueryString());   
    }      
    
    @Override
	public String getHeader(String name) {
        return HetaoBlogXssHTMLFilter.filter(super.getHeader(name));   
    } 
    
    @Override
	public String getParameter(String name) {  
        return HetaoBlogXssHTMLFilter.filter(super.getParameter(name));   
    } 
    
    public boolean isXssParameter(String name){
    	//订单报文参数不做判断
    	if("query_type".equalsIgnoreCase(name) || "query_page_type".equalsIgnoreCase(name) 
    			|| name.indexOf("param")>=0)
    		return false;
    	String value = super.getParameter(name);
    	String new_value = HetaoBlogXssHTMLFilter.filter(value);
    	if(!value.equals(new_value)){
    		logger.info("XssHttpRequestWrapper---name:"+name+";value="+value+";new_value"+new_value);
    		return true;
    	}
        return false;   
    }
    
    @Override
	public String[] getParameterValues(String name) {   
        String[] values = super.getParameterValues(name);   
        if(values != null) {   
            int length = values.length;   
            String[] escapseValues = new String[length];   
            for(int i = 0; i < length; i++){   
                escapseValues[i] = HetaoBlogXssHTMLFilter.filter(values[i]);   
            }   
            return escapseValues;   
        }   
        return super.getParameterValues(name);   
    }     
}

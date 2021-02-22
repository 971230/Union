package com.ztesoft.net.util;

import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;

public class SendWMSUtil {
	private static Logger logger = Logger.getLogger(SendWMSUtil.class);
	public String callServices(String msg,String url,String space_name,String method_name) throws ServiceException, RemoteException{
		//webService访问地址        
//		  String url = "http://135.125.75.27:9081/IOMPROJ/services/PfService4IOS";       
		  //创建服务        
		  Service service = new Service();        
		  //创建调用句柄       
		  Call call = (Call) service.createCall();        
		  //设置请求地址        
		  call.setTargetEndpointAddress(url);       
		 
		  call.setOperationName(new QName(space_name, method_name));                
		  /**         
		   * 用call调用getName方法，设置请求的参数，返回的就是返回值了        
		   */     
		  String result = (String) call.invoke(new Object[] { msg });  

		  return result;
		
	}
	
    
    public static String getWSDLCall(String url,String xml){ 

        String result = ""; 

        try { 

        	Service service = new Service();  
			Call call2 = (Call) service.createCall();  
			 call2.setTargetEndpointAddress(new java.net.URL(  
					 url));  
			 call2.setUseSOAPAction(true);  
			 call2.setReturnType(new QName("http://www.w3.org/2001/XMLSchema",  
			         "string"));  
			 // 第二种设置返回值类型为String的方法  
			 call2.setOperationName(new QName("http://tempuri.org/", "ReqSKU"));  
			 call2.setSOAPActionURI("http://tempuri.org/ReqSKU");  
			 call2.addParameter(new QName("http://tempuri.org/", "strJson"),// 这里的name对应参数名称  
			         XMLType.XSD_STRING, ParameterMode.IN);  
			 String retVal2 = (String) call2  
			         .invoke(new Object[] { xml});  
			 logger.info(retVal2);  
			 result = retVal2;
        } catch (Exception e) { 
        	result = e.getMessage().replaceAll("\r", " ").replaceAll("\n", " ").replaceAll("\r\n", " ");
            e.printStackTrace();
        } 

        return result; 

    } 
}

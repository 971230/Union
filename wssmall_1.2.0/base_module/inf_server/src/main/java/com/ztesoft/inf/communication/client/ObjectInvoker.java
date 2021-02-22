package com.ztesoft.inf.communication.client;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.thoughtworks.xstream.XStream;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.inf.framework.commons.CodedException;


public class ObjectInvoker extends Invoker{

	String qnameEncoding;
	String qname;
	String operClassPath;
	String operMethodName;
	String reqPath;
	String rspPath;
	String rspType;  //add by wen.jinyang 2012-08-23
	@Override
	public Object invoke(InvokeContext context) throws Exception {
		// TODO Auto-generated method stub
		String operationCode = context.getOperationCode();
		Map param = (Map)context.getParameters();
		context.setEndpoint(endpoint);
		context.setRequestTime(DateUtil.currentTime());
		Object result = new Object();
		try {
			Object object = this.getClassObject(reqPath);
			Object request = this.getReqeust(reqPath, param);
			
			
			Object resultObj = this.getClassObject(rspPath);
			Service service = new Service();
	        Call call = (Call) service.createCall();
//	        String [] qName = ClassUtils.getQName(operationCode);
//	        String [] operator = ClassUtils.getOperatorName(operationCode);
	        QName qn = new QName(qnameEncoding,qname);
	        //注册请求参数类型
	        call.registerTypeMapping(object.getClass(), qn,  
	        		new org.apache.axis.encoding.ser.
                    BeanSerializerFactory(object.getClass(), qn), 
                    new org.apache.axis.encoding.ser.
                    BeanDeserializerFactory(object.getClass(), qn));
	        //注册返回类型
	        // 注册 rspPath 
	        call.registerTypeMapping(resultObj.getClass(), qn,  
	        		new org.apache.axis.encoding.ser.
                  BeanSerializerFactory(resultObj.getClass(), qn), 
                  new org.apache.axis.encoding.ser.
                  BeanDeserializerFactory(resultObj.getClass(), qn));
	        
	        //wjy_0823  add by wen.jinyang 2012-08-23
       	 
       	 	// 注册  rspType
	        if(rspType!=null && !"".equals(rspType)&&!"null".equals(rspType)){
		        String[] responseTypes= rspType.split("\\;");
		        
		        for (String rspTypeString:responseTypes ){
		        	Object rspObject = this.getClassObject(rspTypeString);
		        	call.registerTypeMapping(rspObject.getClass(), qn,  
			        		new org.apache.axis.encoding.ser.
		                    BeanSerializerFactory(rspObject.getClass(), qn), 
		                    new org.apache.axis.encoding.ser.
		                    BeanDeserializerFactory(rspObject.getClass(), qn));
		        }
	        }        
	        //end  wjy_0823
	        
	        call.setReturnType(new QName(resultObj.getClass().toString()), resultObj.getClass());//设置返回值类型
	        call.setTargetEndpointAddress(new java.net.URL(endpoint));//设置服务地址
	        call.setOperationName((new QName(operClassPath,operMethodName)));//设置服务端类名和方法名
	        call.addParameter(operationCode, qn, ParameterMode.IN);//设置请求参数
	        
	      /*  if(soapActionUri!=null&&!"".equals(soapActionUri)){
	        	call.setSOAPActionURI(soapActionUri);
	        }*/
	        if (timeout != null)
				call.setTimeout(timeout * 1000);
	        else
	        	call.setTimeout(20 * 1000);
	        result = call.invoke(new Object[] {request});
	        context.setResponseTime(DateUtil.currentTime());
	        context.setRequestString(new XStream().toXML(request));
	        context.setResponeString(new XStream().toXML(result));
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	
	
	/**
	 * 获取请求对象
	 * @param operationCode
	 * @param context
	 * @return
	 * @throws Exception
	 */
	private Object getReqeust(String operationCode ,Map context) throws Exception{
		Object object = new Object();
		try {
			object = this.getClassObject(operationCode);
			if(object != null){
				Method [] methods = object.getClass().getMethods();
				for(Method method : methods){
					String setFieldName  = method.getName();
					if(setFieldName == null || !setFieldName.startsWith("set")){
						continue;
					}
					Iterator itor= context.entrySet().iterator();
					while(itor.hasNext()){
						Map.Entry map=(Map.Entry)itor.next();
						String key =String.valueOf(map.getKey());
						String setKey = "set"+key.substring(0,1).toUpperCase()+key.substring(1);
						if(setKey.equals(setFieldName)){
							method.invoke(object, map.getValue());
						}
					}
				}
			}
			
		}catch(Exception e){
			throw e;
		}
		return object;
	}
	
	private Object getClassObject(String classPath) throws Exception{
		Object object = new Object();
		try {
			if(classPath !=null && !"".equals(classPath)){
				object = Class.forName(classPath).newInstance();
			}else {
				throw new CodedException("9999", "操作编码["+classPath+"] 未能找到对应的类");
			}
		}catch(Exception e){
			throw e;
		}
		return object;
	}
	
	public static void main(String [] args){
		try {
			Map map = new HashMap();
			CommCaller call = new CommCaller();
			//欠费查询
			map.put("instanceType", "3");
			map.put("instanceId", "13307498133");
			map.put("longInstanceId", Long.parseLong("33813786"));
			call.invoke("bill.queryBalance", map);
			//余额查询，扣减
//			map.put("requestId", "test12366");
//			map.put("accNbr", "0735-07355218779");
//			map.put("disctId", "500020220");
//			map.put("operType", "1");//1－余额查询 2－余额冻结 3－余额冻结回退
//
//			map.put("balance", "");
//			call.invoke("bill.balanceService", map);

//			map.put("requestId", "test12345");
//			map.put("requestTime", "20110509095444");
//			map.put("partyId", "7395000346");
//			map.put("partyRoleId", "180141");
//			call.invoke("bill.queryAgentDepositAmount", map); //313700
//			staff_id:999992176 party_id:999992173
			
			//代理商预存金扣减
//			map.put("requestId", "113098940065");
//			map.put("requestTime", "20110704204549");
//			map.put("partyRoleId", "11309505");
//			map.put("partyId", "89648912");
//			map.put("accNbr", "18973100908");
//			map.put("actionType", "600");//扣减
//			map.put("amount", "1");
//			AgentDeduceRespDto resp = (AgentDeduceRespDto)call.invoke("bill.deduceAmount", map);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	  
	
}

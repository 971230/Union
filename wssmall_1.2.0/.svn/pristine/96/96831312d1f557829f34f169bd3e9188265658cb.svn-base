package com.ztesoft.inf.communication.client;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.ztesoft.common.util.Clazz;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.inf.framework.commons.CodedException;


public class AxisInvoker extends Invoker{

	String qnameEncoding;
	String qname;
	String operClassPath;
	String operMethodName;
	@Override
	public Object invoke(InvokeContext context) throws Exception {
		// TODO Auto-generated method stub
		String operationCode = context.getOperationCode();
		Object param = context.getParameters();
		context.setEndpoint(endpoint);
		context.setRequestTime(DateUtil.currentTime());
		Object result = new Object();
		try {
			Object object = this.getClassObject(operClassPath);
			
			org.apache.axis.client.Stub _stub=(org.apache.axis.client.Stub)object;
		
			
			   if (timeout != null){
					_stub.setTimeout(timeout*1000);
			   }else{
					_stub.setTimeout(30*1000);
			   }
			   
            StringUtils.setObjectValue(_stub, "cachedEndpoint",new java.net.URL(endpoint));
            Method method=Clazz.findMethod(object.getClass(), operMethodName);
	        if(method==null){
	        	throw new CodedException("9999", "操作编码["+operationCode+"] 对应的["+reqPath+"]类方法["+operMethodName+"]");
	        }
	        result= method.invoke(object, param);
	        
	        context.setResponseTime(DateUtil.currentTime());
	        context.setRequestString(new XStream().toXML(param));
	        context.setResponeString(new XStream().toXML(result));
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("接口异常");
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

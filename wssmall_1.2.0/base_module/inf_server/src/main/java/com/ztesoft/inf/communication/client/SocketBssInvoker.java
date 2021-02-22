package com.ztesoft.inf.communication.client;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import params.ZteResponse;

import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.date.DateUtil;

public class SocketBssInvoker extends Invoker{
	
	@Override
	public Object invoke(InvokeContext context) throws Exception {
		Object params = context.getParameters();
		Map param =new HashMap();
		BeanUtils.bean2Map(param, params);
		String mobile = (String) param.get("phone_num");
		String[] strs = doGetParam(param);
		int prarmLength = doGetParamLength(strs);
		String end = (char)0x1a+"";
		Socket socket = null;
		OutputStream out = null;
		Reader reader = null;   
        StringBuffer sb1 = new StringBuffer();
		sb1.append(doGetBaoTouString(prarmLength,mobile));
		sb1.append(doGetParamToString(strs)).append(end);
        String body = sb1.toString();
        System.out.println("from client: " + body);
        context.setRequestString(body);
        context.setEndpoint(endpoint);
		context.setRequestTime(DateUtil.currentTime());
        StringBuffer sb = new StringBuffer();   
		try{ 
			socket = new Socket();  
			socket.setTcpNoDelay(false);
		    socket.connect(new InetSocketAddress(endpoint.substring(0,endpoint.indexOf(":")),
		    		Integer.parseInt(endpoint.substring(endpoint.indexOf(":")+1,endpoint.length())))); 
	        out = socket.getOutputStream();
	        reader = new InputStreamReader(socket.getInputStream());
	        out.write(body.getBytes());
	        out.flush();   
	        //写完以后进行读操作   
	        char chars[] = new char[64];   
	        int len;   
	        while ((len=reader.read(chars)) != -1) {   
	        	  String temp = new String(chars, 0, len);
	        	  sb.append(temp);   
	        	  //如果读取到结束符就跳出循环
	        	  if(temp.contains(end)){
	        		  break;
	        	  }
	        }   
	        System.out.println("from server: " + sb);
		}catch(Exception e) {
			System.out.println(e);
		}finally{
			if(out!=null){
		        out.close();   
			}
			if(reader!=null){
		        reader.close();   
			}
			if(socket!=null){
		        socket.close();	
			}			
		}
		//解析结果
		String code=null;
		String result = sb.toString();
		if(!StringUtils.isBlank(result)&&Integer.parseInt(result.substring(0,result.indexOf(" ")))>1080){
	        if(StringUtils.equals(result.substring(80, result.length()).trim(),"1000000000")){
	        	code = "0000";
	        }else{
	        	code = "-9999";
	        }
		}else{
			code = "-9999";
		}
		Class<?> clazz = Class.forName(rspPath);
		result =  "{\"x_check_info\":\""+result+"\"}";
		ZteResponse resp = (ZteResponse) JsonUtil.fromJson(result, clazz);
        resp.setError_code(code);
		if("0000".equals(code)){
			logColMap.put("col2", "success");
		}else{
			logColMap.put("col2", "error");
		}		
		context.setResponeString(sb.toString());
		context.setResultString(sb.toString());
		context.setResponseTime(DateUtil.currentTime());
		return resp;
	}
	
	@Override
	public Object invokeTest(InvokeContext context) throws Exception {
		Object params = context.getParameters();
		Map param =new HashMap();
		BeanUtils.bean2Map(param, params);
		String mobile = (String) param.get("phone_num");
		String[] strs = doGetParam(param);
		int prarmLength = doGetParamLength(strs);
		String end = (char)0x1a+"";
		StringBuffer sb1 = new StringBuffer();
		sb1.append(doGetBaoTouString(prarmLength,mobile));
		sb1.append(doGetParamToString(strs)).append(end);
        context.setRequestString(sb1.toString());
        context.setEndpoint(endpoint);
		context.setRequestTime(DateUtil.currentTime());
		Class<?> clazz=null;
        ZteResponse resp = null;
		try{
			clazz = Class.forName(rspPath);
       	 	resp = (ZteResponse) JsonUtil.fromJson(transform, clazz);
		}catch(Exception e) {
			System.out.println(e);
		}
		logColMap.put("col2", "success");
		context.setResponeString(transform);
		context.setResultString(transform);
		context.setResponseTime(DateUtil.currentTime());
		return resp;
	}
	
	/*
	 * 封装包头数据
     */
	public String doGetBaoTouString(int prarmLength,String mobile){
		StringBuffer sb = new StringBuffer();
		//A0版本号信息（2位）
		String A0 = "10";
		//A1数据包大小（5位）  动态
		int length = 86 + prarmLength + 1;
		String A1 = "";
		if(length >= 100){
			A1 = length+"  ";
		}else{
			A1 = length+"   ";
		}
		//A2流水号（20位）
		String A2 = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime())+"      ";
		//A3标志（1位）
		String A3 = "1";
		//A4服务类型（12位）    动态
		String A4 = "700000003011";
		//A5业务号码（20位）
		String A5 = mobile+"         ";
		//A6业务号码类型（1位）
		String A6 = " ";
		//A7营业点（6位）
		String A7 = "F010ZB";
		//A8营业员（8位）
		String A8 = "SUPERUSR";
		//A9包编号（5位）
		String A9 = "1    ";
		//A10最后一包标志（1位）
		String A10 = "1";
		//A11错误码（5位）
		String A11 = "00000";
		sb.append(A0).append(A1).append(A2).append(A3).append(A4).append(A5).append(A6).append(A7).append(A8)
		.append(A9).append(A10).append(A11);
		return sb.toString();
	}
	
	/*
	 * 获取参数
	 */
	public String[] doGetParam(Map param){
		String proKey = (String) param.get("pro_key");
		String eparchyCode = (String) param.get("eparchy_code");
		String paraCode2 = (String) param.get("para_code2");
		String prepayTag = (String) param.get("prepay_tag");
		String xTagchar = (String) param.get("x_tagchar");
		String paramValue2 = (String) param.get("param_value2");
		String[] strs = new String[]{eparchyCode,paraCode2," ",prepayTag," ",xTagchar,proKey,paramValue2," "," "};
		return 	strs;
	}
	
	/*
	 * 计算参数值长度
	 */
	public int doGetParamLength(String... param){
		int len = 0;
		for(int i = 0;i<param.length;i++){
				String str = param[i]==null ? " ":param[i];
				len += str.length();
		}
		return len;
	}
	
	/*
	 * 封装参数
	 */
	public String doGetParamToString(String... param){
		StringBuffer sb = new StringBuffer();
		String tab = (char)0x09+"";
		for(int i = 0;i<param.length;i++){
			if(i == param.length - 1){
				sb.append(param[i]);
			}else{
				sb.append(param[i]).append(tab);
			}
		}
		return sb.toString();
	}

}

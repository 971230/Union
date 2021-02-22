package com.ztesoft.inf.communication.client;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import params.ZteResponse;

import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.date.DateUtil;

public class SocketBssNewInvoker extends Invoker{

	@Override
	public Object invoke(InvokeContext context) throws Exception {
		Object params = context.getParameters();
		Map param =new HashMap();
		BeanUtils.bean2Map(param, params);
		String[] strs = doGetParam(param);
		String end = (char)0x1a+"";
		Socket socket = null;
		OutputStream out = null;
		Reader reader = null;
		String reqStr = doGetParamToString(strs);
		System.out.println("from client: " + reqStr);
		context.setRequestString(reqStr);//req_xml字段保存发送报文
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
			out.write(reqStr.getBytes());
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
		String respStr = sb.toString();
		String respHead = "";
		String respBody = "";
		if(respStr.length()>=87){//包头86位+结束符1位:至少87位
//			respHead = respStr.substring(0, 86);
//			respBody = respStr.substring(86, respStr.length()-1);//去掉包头和结束符
			if(StringUtils.equals(respStr.substring(81, 86),"00000")){//A11包头成功编码"00000"
				code = "0000";//成功逻辑
			}else{
				code = "-9999";//业务包头失败逻辑
			}
		}else{
			respHead = "非正常返回协议报文";
//			respBody = "非正常返回协议报文";
		}
		Class<?> clazz = Class.forName(rspPath);
		respStr =  "{\"respStr\":\""+respStr+"\"" +
				",\"respHead\":\""+respHead+"\"" +
//				",\"respBody\":\""+respBody+"\"" +
				"}";
		ZteResponse resp = (ZteResponse) JsonUtil.fromJson(respStr, clazz);
		//		resp.setError_code(code);//外层通过A11判断成功与否
		if("0000".equals(code)){
			logColMap.put("col2", "success");
		}else{
			logColMap.put("col2", "error");
		}
		context.setResponeString(sb.length()<=0?"未获得返回报文":sb.toString());//rsp_xml字段保存原始报文
		context.setResultString(resp.toString());//result_desc分解返回结果
		context.setResponseTime(DateUtil.currentTime());
		return resp;
	}

	@Override
	public Object invokeTest(InvokeContext context) throws Exception {
		Object params = context.getParameters();
		Map param =new HashMap();
		BeanUtils.bean2Map(param, params);
		String[] strs = doGetParam(param);
		String end = (char)0x1a+"";
		String body = doGetParamToString(strs);
		context.setRequestString(body);
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
	 * 获取参数(包头、包体、结束符)
	 */
	public String[] doGetParam(Map param){
		String head = (String) param.get("head");
		try{
			String params = reqUser.getUser_param();
			JSONObject object = JSONObject.fromObject(params);
			String A7 = object.getString("A7");
			String A8 = object.getString("A8");
			head = head.substring(0, 61) + A7 + A8 + head.substring(75, head.length());
		}catch(Exception e){
			System.out.println("SocketBssNewInvoker拼装包头A7 A8失败");
			e.printStackTrace();
		}
		String body = (String) param.get("body");
		String end = (String) param.get("end");
		String[] strs = new String[]{head,body,end};
		return 	strs;
	}

	/*
	 * 封装参数
	 */
	public String doGetParamToString(String... param){
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i<param.length;i++){
			sb.append(param[i]);
		}
		return sb.toString();
	}

}

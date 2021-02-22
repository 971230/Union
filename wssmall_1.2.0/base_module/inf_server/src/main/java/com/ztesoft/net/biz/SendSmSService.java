package com.ztesoft.net.biz;

import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;

import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.util.DBUtil;
import com.ztesoft.common.util.HttpUtils;
import com.ztesoft.ibss.common.util.SqlMapExe;
import com.ztesoft.inf.AbstractService;
import com.ztesoft.inf.communication.client.bo.ICommClientBO;
import com.ztesoft.inf.communication.client.vo.ClientEndPoint;
import com.ztesoft.inf.communication.client.vo.ClientOperation;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONException;
//import net.sf.json.JSONObject;

public class SendSmSService extends AbstractService{

	private static Logger log = Logger.getLogger(SendSmSService.class);
	
	private final String DX_pSpNo = "JXCT10000";
	private final String DX_pSpPasswd = "zte0923";
	private final String OTHER_pSpNo = "wtwifi";
	private final String OTHER_pSpPasswd = "wt6987";
	private  ICommClientBO commClientBO;
	private  ICommClientBO getICommClientBO(){
		if(commClientBO==null){
			commClientBO = SpringContextHolder.getBean("commClientBO");
		}
		return commClientBO;
	}
	/**
	 * 根据请求流水 请求时间 鉴权工号 鉴权密码 区号 号码 设备类型，开通欠费停机用户
	 * 
	 * @param param
	 *            pSpNo：短信发送SP鉴权代号（行业客户注册账号） pSpPasswd: 短信发送SP鉴权密码（行业客户注册密码）
	 *            pSpId : 
	 *            pSrcTerminal: 短信发送号码（1065接入号或绑定的电话号码，为空则电信方提供）。
	 *            pDestTerminals:短信接收号码,允许多个 
	 *            pMsgData:短信内容，通过UTF-8和URLEncoder形式对短信内容进行编码
	 *            pMsgKind: 短信类型，默认即时短信。3即时短信；5通知短信。
	 *            pScheduleTime：短信发送时间，格式为：yyyyMMddhhmmss,允许为空
	 *            pIntervalTime：群发间隔时间，单位为秒。
	 * @return
	 * @throws Exception
	 */

	public String sendStrMsg(Object[] params, Object[] values) throws Exception {
		String operationCode = "sms.sendMsg";
		ClientOperation commOp = getICommClientBO().getOperationByCode(operationCode,null);
		ClientEndPoint commend = getICommClientBO().getEndPoint(commOp.getEndpointId(),null);
		String endPointAdd = commend.getEndpointAddress();
		Integer timeout = commend.getTimeout();
		int flag = params.length;
		for (int i = 0; i < params.length; i++) {
			if (i == 0) {
				endPointAdd += "?" + params[i] + "=" + values[i];
			} else
				endPointAdd += "&" + params[i] + "=" + values[i];
		}
		logger.info("endPointAdd---" + endPointAdd);
		return HttpUtils.getContentByUrl(endPointAdd, timeout);
	}


	/**
	 * 根据请求流水 请求时间 鉴权工号 鉴权密码 区号 号码 设备类型，开通欠费停机用户
	 * 
	 * @param param
	 *            pDestTerminals:短信接收号码,允许多个 
	 *            pMsgData:短信内容，通过UTF-8和URLEncoder形式对短信内容进行编码
	 *            H_189_RANGE: 电信手机号段
	 * @return
	 * @throws Exception
	 */

	public Map sendMsg(Map param) throws Exception {
		
		Map retMap = new HashMap(); // toMap(operationCode,jsonString);
		
		List pDestTerminals = (List)param.get("pDestTerminals");
		List DXTerminalsList = new ArrayList(); //电信手机号码
		List otherTerminalsList = new ArrayList(); //他网手机号码
		
		String H_189_RANGE = (String)param.get("H_189_RANGE");
		if(StringUtils.isEmpty(H_189_RANGE))
			H_189_RANGE = "_189_181_180_153_133_";
		
		for(int i=0; pDestTerminals!=null && i<pDestTerminals.size(); i++){
			String terminalNo = (String)pDestTerminals.get(i);
			if(H_189_RANGE.indexOf(terminalNo.substring(0,3))>-1){
				DXTerminalsList.add(terminalNo);
			}else{
				otherTerminalsList.add(terminalNo);
			}
		}
		
		String operationCode = "";
		//通过URLEncoder形式对短信内容进行编码
		String pmsg=param.get("pMsgData").toString();
		String encodeStr = URLEncoder.encode(pmsg, "utf-8");	
		param.put("pMsgData", encodeStr);
		try{ // 发送短信，不影响受理
			String jsonString = "";
			List list = new ArrayList();
			//电信手机发送短信接口调用
			if(DXTerminalsList.size()>0){
//				param.put("pDestTerminal", DXTerminalsList);
//				param.put("pSpNo", DX_pSpNo);
//				param.put("pSpPasswd", DX_pSpPasswd);
//				jsonString = (String) caller.invoke(operationCode, param);
//				log.debug(jsonString);
//				// json格式数据类型转换成list<map>类型
//				list.addAll(getList(jsonString));
				for(int i=0; i<DXTerminalsList.size(); i++){
					param.put("pDestTerminal", DXTerminalsList.get(i).toString());
					operationCode = "sms.sendMsgInner";
					jsonString = (String) caller.invoke(operationCode, param);
					log.debug(jsonString);
					// json格式数据类型转换成list<map>类型
					Map smsRetMap = getMap(jsonString);
					if("0".equals(smsRetMap.get("errorId"))){
						smsRetMap.put("success", "true");
					}else{
						smsRetMap.put("success", "false");
					}
					smsRetMap.put("ErrorMsg", smsRetMap.get("errorMsg"));
					list.add(smsRetMap);
				}
			}
			//他网手机发送短信接口调用
			if(otherTerminalsList.size()>0){
				param.put("pDestTerminals", otherTerminalsList);
				param.put("pSpNo", OTHER_pSpNo);
				param.put("pSpPasswd", OTHER_pSpPasswd);
				operationCode = "sms.sendMsg";
				jsonString = (String) caller.invoke(operationCode, param);
				log.debug(jsonString);
				// json格式数据类型转换成list<map>类型
				list.addAll(getList(jsonString));
			}
			
			for (int i = 0; i < list.size(); i++) {
	//			retMap.put(i + "", toMap("sms.sendMsg",list.get(i).toString()));
	//			Map retap =toMap("sms.sendMsg",list.get(i).toString());
	//			logger.info("retap--"+retap);
				retMap.put(i + "", getMap(list.get(i).toString()));
			}
			//记录发送了的短信信息列表
			List  sqlParams=new ArrayList();
			sqlParams.add(pmsg);
			
			StringBuffer bf=new StringBuffer();
			for(int i=0;i<retMap.size();i++){
				  Map map=(Map)retMap.get(i+"");
				  if("true".equalsIgnoreCase(map.get("success").toString())){
					  bf.append(map.get("pDestTerminals"));
					  if(i<retMap.size()-1){
						  bf.append("|");
					  }
				  }	  
			}
			sqlParams.add(bf.toString());
			sqlParams.add(new java.sql.Date(new java.util.Date().getTime()));
//			saveSendMsgLog(sqlParams);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}

	/**
	 * 
	 * 把json 转换为Map 形式
	 * 
	 * @return
	 */
	public static Map getMap(String jsonString){
		try{
			JSONObject jsonObject = JSONObject.fromObject(jsonString);
			Iterator keyIter = jsonObject.keys();
			String key;
			Object value;
			Map valueMap = new HashMap();
			while (keyIter.hasNext()){
				key = (String) keyIter.next();
				value = jsonObject.get(key);
				valueMap.put(key, value);
			}
			return valueMap;
		}catch (JSONException e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * 把json 转换为ArrayList 形式
	 * 
	 * @return
	 */

	public static List getList(String jsonString) {
		List list = null;
		try{
			JSONArray jsonArray = JSONArray.fromObject(jsonString);
			JSONObject jsonObject;
			list = new ArrayList();
			for (int i = 0; i < jsonArray.size(); i++)
			{
				jsonObject = jsonArray.getJSONObject(i);
				list.add(getMap(jsonObject.toString()));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;

	}
	public static void saveSendMsgLog(List sqlParams) throws Exception{
		 Connection conn = null;
		  try {
				conn = DBUtil.getConnection();
				SqlMapExe sqlMapExe=new SqlMapExe(conn);
				String sql = "insert into inf_comm_sendmsg_log(LOG_ID,pMsgData,pDestTerminals,pScheduleTime) values(SENDMSG_LOG_SEQ.nextval,?,?,?) ";
				sqlMapExe.excuteUpdate2(sql, sqlParams);
				conn.commit();
			}catch (FrameException e) {
				e.printStackTrace();
				throw e;
			} finally {
				DBUtil.closeConnection(conn);
			}
	}

	public static void main(String[] args) throws Exception {

		Map param = new HashMap();
		
		List pDestTerminals = new ArrayList();
		pDestTerminals.add("18970825308");
		pDestTerminals.add("13767120221");
		param.put("pDestTerminals", pDestTerminals);
		
		param.put("pMsgData", "SimpleShop-爱恋金誓 18970826038<hao> 南昌 后付费 业务支撑中心测试卡");
		Map retMap = new SendSmSService().sendMsg(param);
         
		
		log.info("返回报文="+retMap);
		for(int i=0;i<retMap.size();i++){
			  Map map=(Map)retMap.get(i+"");
			  log.info("返回短信提交第"+(i+1)+"个报文信息如下:");
			  log.info("ErrorMsg="+map.get("ErrorMsg"));
			  log.info("pDestTerminals="+map.get("pDestTerminals"));
			  log.info("SplitAmount="+map.get("SplitAmount"));
			  log.info("BillType="+map.get("BillType"));
		}
		

	}

}

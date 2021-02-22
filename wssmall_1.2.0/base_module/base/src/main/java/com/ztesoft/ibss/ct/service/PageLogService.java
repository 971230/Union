package com.ztesoft.ibss.ct.service;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.util.ContextHelper;
import com.ztesoft.ibss.common.dao.DAOUtils;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.ibss.ct.vo.PageLogVO;
import com.ztesoft.ibss.wb.consts.KeyValues;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author Reason.Yea
 * @version Created Feb 3, 2012
 */
public class PageLogService {
	public static Logger logger = Logger.getLogger(PageLogService.class);
	
    //session 中的log列表
	public static final String SESSION_LOG = "SESSION_LOG";
	//session 中的log列表大小
	public static final int SESSION_LOG_SIZE=10;
    //静态加载服务类别信息
	static Map Url_Map = new HashMap();
	static{
		DynamicDict dict = new DynamicDict(1);
		dict.m_ActionId = "QRY_SERV_KIND_URL";
		dict.flag=0;
		try{
			dict = ActionDispatch.dispatch(dict,null);
			List ls = (List) dict.getValueByName("WB_SERV_KIND", false);
			for(Iterator iter = ls.iterator(); iter.hasNext();){
				HashMap map = (HashMap)iter.next();
				Url_Map.put(map.get("URL"),map.get("SERV_KIND"));
			}
		}catch(Exception e){
			logger.info("QRY_SERV_KIND_URL error====================");
		}
	}
	/**
	 * 保存用户行为日志
	 * @param request
	 */
	public static void execLog(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object sessionLog = session.getAttribute(SESSION_LOG);
		List sessionLogs = new ArrayList();
		if(sessionLog!=null ){
			sessionLogs = (List)sessionLog;
		}
		PageLogVO log = new PageLogVO();
		// 取存储的前一sessionID值
		String strPreSessionID = session.getAttribute("PRE_SESSION_ID") == null ? "" : (String) session.getAttribute("PRE_SESSION_ID");
		// 取当前sessionID
		String strSessionID = session.getId();

		// 取session存储的URL
		String strPreSessionURL = session.getAttribute("PRE_URL") == null ? "" : (String) session.getAttribute("PRE_URL");
		// 取URL信息
		String vVISIT_URL = request.getParameter("VISIT_URL") == null ? "" : request.getParameter("VISIT_URL");
		String urlParts[] = vVISIT_URL.split("\\?");
		
		// 生成objectID
		String vObjectID = StringUtils.getUrlQryStr(urlParts,"OBJECT_ID");
		if(vObjectID.equals("")){
			vObjectID = urlParts[0];
		}
		// 生成业务种类，业务类型，业务信息
		String vSERV_KIND = StringUtils.getUrlQryStr(urlParts,"SERV_KIND");
		String vSERV_TYPE = StringUtils.getUrlQryStr(urlParts,"SERV_TYPE");
		String vSERV_NO = StringUtils.getUrlQryStr(urlParts,"SERV_NO");
		if(vSERV_KIND.equals("")){
			vSERV_KIND = getServKind(vVISIT_URL);
		}

		// 生成session比对值
		String strSessionURL = vObjectID + "@" + vSERV_KIND + "@" + vSERV_TYPE + "@" + vSERV_NO;
		// 判断是否在同一个页面重复提交
		if (strPreSessionID.equals(strSessionID) && strPreSessionURL.equals(strSessionURL)) {
			return;
		}
		// js传递
		String vBrowser_name = request.getParameter("BROWSER_NAME") == null ? "" : request.getParameter("BROWSER_NAME");
		String vOper_system = request.getParameter("OPER_SYSTEM") == null ? "" : request.getParameter("OPER_SYSTEM");
		String vScreen_size = request.getParameter("SCREEN_SIZE") == null ? "" : request.getParameter("SCREEN_SIZE");
		String vSourceURL = request.getParameter("SOURCE_URL") == null ? "" : request.getParameter("SOURCE_URL");
		String vVisitURL = request.getParameter("VISIT_URL") == null ? "" : request.getParameter("VISIT_URL");
		String vLogin_Name = "";

		// 取session序列值
		String strSeqNo = session.getAttribute("SEQ_NO") == null ? "1" : (String) session.getAttribute("SEQ_NO");
		if (strSeqNo.equals("")) strSeqNo = "1";


		try {
			String vIP = getIp(request);
			// 生成Session中用户操作信息/ 签协议之前的session值；
			HashMap user = (HashMap) session.getAttribute("user")==null ? 
					(HashMap) session.getAttribute("USER_BEFORE_LOGIN"):(HashMap) session.getAttribute("user");

			String vLOGIN_TYPE = StringUtils.getUrlQryStr(urlParts,"LOGIN_TYPE");
			String vUSER_NO = StringUtils.getUrlQryStr(urlParts,"USER_NO");
			String vLOGIN_PROD_NO = StringUtils.getUrlQryStr(urlParts,"LOGIN_PROD_NO");
			String vLOGIN_AREA_CODE = StringUtils.getUrlQryStr(urlParts,"LOGIN_AREA_CODE");
			String vCUSTBRAND = StringUtils.getUrlQryStr(urlParts,"CUSTBRAND");
			String vCUSTGROUP = StringUtils.getUrlQryStr(urlParts,"CUSTGROUP");
			String vOPR_USER_NO = StringUtils.getUrlQryStr(urlParts,"OPR_USER_NO");
			String vOPR_PROD_NO = StringUtils.getUrlQryStr(urlParts,"OPR_PROD_NO");
			String vOPR_AREA_CODE = StringUtils.getUrlQryStr(urlParts,"OPR_AREA_CODE").equals("")?"0000":StringUtils.getUrlQryStr(urlParts,"OPR_AREA_CODE");
			// 如果未登录,则USER_NO为IP地址
			if (user != null ) {
				//客户列表
				ArrayList cust_list = (ArrayList) user.get("CUST_LIST");
				if (cust_list!=null && cust_list.size() > 0 && cust_list.get(0)!=null) {
					vLogin_Name = user.get("LOGIN_NAME") == null ? "": (String) user.get("LOGIN_NAME");
					vLOGIN_TYPE = user.get("LOGIN_TYPE") == null ? "": (String) user.get("LOGIN_TYPE");
					vUSER_NO = user.get("USER_NO") == null ? "" : (String) user.get("USER_NO");
					vLOGIN_PROD_NO = user.get("LOGIN_PROD_NO") == null ? "": (String) user.get("LOGIN_PROD_NO");
					vLOGIN_AREA_CODE = user.get("LOGIN_AREA_CODE") == null ? "": (String) user.get("LOGIN_AREA_CODE");
					if(vOPR_USER_NO==null||vOPR_USER_NO.equals(""))vOPR_USER_NO=vUSER_NO;
					if(vOPR_PROD_NO==null ||vOPR_PROD_NO.equals(""))vOPR_PROD_NO=vLOGIN_PROD_NO;
					
					HashMap custInfo = (HashMap) cust_list.get(0);
					vCUSTBRAND = custInfo.get("CUSTBRAND") == null ? "": (String) custInfo.get("CUSTBRAND");
					vCUSTGROUP = custInfo.get("CUSTGROUP") == null ? "": (String) custInfo.get("CUSTGROUP");
					if (vCUSTBRAND == null)vCUSTBRAND = "";
					if (vCUSTGROUP == null)vCUSTGROUP = "";
				}
			}
			log.setUser_no(vUSER_NO);
			log.setLogin_type(vLOGIN_TYPE);
			log.setVisit_url(vVisitURL);
			log.setObject_id(vObjectID);
			log.setVisit_ip(vIP);
			log.setLogin_prod_no(vLOGIN_PROD_NO);
			log.setOpr_user_no(vOPR_USER_NO);
			log.setOpr_prod_no(vOPR_PROD_NO);
			log.setOpr_area_code(vOPR_AREA_CODE);
			log.setLogin_area_code(vLOGIN_AREA_CODE);
			log.setCustbrand(vCUSTBRAND);
			log.setCustgroup(vCUSTGROUP);
			log.setLogon_name(vLogin_Name);
			log.setServ_kind(vSERV_KIND);
			log.setServ_type(vSERV_TYPE);
			log.setServ_no(vSERV_NO);
			log.setSession_id(strSessionID);
			log.setSequ(strSeqNo);
			log.setSource_url(vSourceURL);
			log.setOper_system(vOper_system);
			log.setBrowser_name(vBrowser_name);
			log.setScreen_size(vScreen_size);
			log.setEvent_time(DAOUtils.getFormatedDate());
			sessionLogs.add(log);
			if(sessionLogs.size()==SESSION_LOG_SIZE){//到达session数量限制才批量插入数据库
				DynamicDict dict = new DynamicDict(1);
				dict.m_ActionId = "PAGE_LOG";
				dict.setValueByName("SESSIONLOGS", sessionLogs);
				dict = ActionDispatch.dispatch(dict);
				dict.destroy();
				//清空sessionlogs
				sessionLogs.clear();
			}
			// 写入session
			session.setAttribute("PRE_SESSION_ID", strSessionID);
			session.setAttribute("PRE_URL", strSessionURL);
			int nextSeqNo = Integer.parseInt(strSeqNo)+1;
			session.setAttribute("SEQ_NO", String.valueOf(nextSeqNo));
			session.setAttribute(SESSION_LOG, sessionLogs);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("记录访问日志失败:" + e.getMessage());
		}
	
	}
	/**
	 * 获取用户IP
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		return ContextHelper.getRequestIp(request);
	}
	/**
	 * 用户session失效
	 * @param sessionLogs
	 */
	public static void sessionInvalidLog(List sessionLogs){
		if(sessionLogs==null || sessionLogs.size()==0)return;
		
		DynamicDict dict = new DynamicDict(1);
		dict.m_ActionId = "PAGE_LOG";
		try {
			dict.setValueByName("SESSIONLOGS", sessionLogs);
			dict = ActionDispatch.dispatch(dict);
			dict.destroy();
		} catch (FrameException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据数据库配置得到服务类别
	 * @param url
	 * @return
	 */
	public static String getServKind(String url){
		try{
			Set set = Url_Map.keySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				if(key!=null && url.toLowerCase().indexOf(key.toLowerCase())>-1 && !key.equals("/")){
					return (String)Url_Map.get(key);
				}
			}
			return (String)Url_Map.get("/");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 从session中取String值，取不到则返回默认值
	 * @param session
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getSessionStr(HttpSession session,String key,String defValue){
		String ret = (String) session.getAttribute(key);
		if(ret==null || ret.equals(""))ret = defValue;
		return ret;
	}
	/**
	 * 从request中取String值，取不到则返回默认值
	 * @param request
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getParameterStr(HttpServletRequest request,String key,String defValue){
		String ret = request.getParameter(key);
		if(ret==null || ret.equals(""))ret = defValue;
		return ret;
	}
	/**
	 * 初始化用户选择城市
	 * @param request
	 */
//	public static void initPageCityInfo(HttpServletRequest request){
//		HttpSession session = request.getSession();
//		try{
//			//记录是否已经选择了城市,0未选，1已选
//			String hasSelectedCity = PageLogService.getSessionStr(session,"HAS_SELECTED_CITY","0");
//			//获取用户ip
//			if(null != hasSelectedCity  && "0" == hasSelectedCity ){
//				String strAreaCode = PageLogService.getSessionStr(session,"SELECT_CITY_NO","0000");
//				String cityName = PageLogService.getSessionStr(session,"SELECT_CITY_NAME","全省");
//				String my_ip = PageLogService.getIp(request);
//				HashMap hm = (HashMap)CTConfig.getInstance().getIpLan(my_ip);
//				String flag = (String)hm.get("flag");
//				if(flag!=null && flag == "1"){
//					String area_code = (String)hm.get("area_code");
//					String city_name = (String)hm.get("city_name");
//					strAreaCode = area_code;	
//					cityName = city_name;
//					session.setAttribute("HAS_SELECTED_CITY","1");
//					session.setAttribute("SELECT_CITY_NO",strAreaCode);
//					session.setAttribute("SELECT_CITY_NAME",cityName);
//				}
//			}
//		}catch(Exception e){
//			log.info("initPageCityInfo error=======================");
//		}
//	}
	/**
	 * 返回在线用户
	 * @return
	 */
	public static String getOnlineUserNum(HttpServletRequest request){
		return "1";
//		return LoginUserInfo.getUserNum(request);
	}
	
	public static String canFileEdit(HttpServletRequest request){
		return "";
//		return LoginUserInfo.getUserNum(request);
	}
	
	
	/**
	 * 校验管理员ip是否在ibss.xml的配置允许的ip段范围，两个ip之间用"|"竖线分割<br>
	 * 例如：<ADMIN_IP>134.224.0.0|134.224.255.255</ADMIN_IP>
	 * @param request
	 * @return
	 */
	public static boolean checkAdminIp(HttpServletRequest request) {
		String adminIp = Const.getSystemInfo("ADMIN_IP");
		if(adminIp==null || adminIp.equals("") || adminIp.split("\\|").length!=2){
			return true;
		}
		try{
			String ips[] = adminIp.split("\\|");
			long beginIp = StringUtils.translateIP(ips[0]);
			long endIp = StringUtils.translateIP(ips[1]);
			long userIp = StringUtils.translateIP(PageLogService.getIp(request));
			if(userIp>beginIp && userIp<endIp){
				return true;
			}
		}catch (Exception e) {
		}
		return false;
	}
		
	/**
	 * 获取session中客户对象
	 * @param session
	 * @return
	 */
	public static HashMap getCust(HttpSession session){
		HashMap user = (HashMap) session.getAttribute("user");
		if(user==null)return null;
		List custList=(List) user.get("CUST_LIST");
		if(custList!=null && custList.size()>0){
			HashMap cust = (HashMap) custList.get(0);
			return cust;
		}else{
			return null;
		}
	}
	/**
	 * 获取session客户熟悉信息
	 * @param prop 客户属性
	 * @param session
	 * @return
	 */
	public static String getCustProp(String prop,HttpSession session){
		HashMap cust = getCust(session);
		if(cust==null)return "";
		if(cust.containsKey(prop)){
			return (String) cust.get(prop);
		}else{
			return "";
		}
	}
	/**
	 * 获取session中手机号码
	 * @param session
	 * @return
	 */
	public static String getSessionMobile(HttpSession session){
		String mobile ="";
		HashMap user = (HashMap) session.getAttribute("user");
		if(user!=null && !user.isEmpty()){
			String prodType = (String)user.get("PRODUCT_ID");
			if(prodType.equalsIgnoreCase(KeyValues.CDMA_PRODDUCT_ID)){//登陆产品为cmda
				mobile=(String) user.get("USER_NO");
			}else{//否则取客户联系号码
				mobile = getCustProp("mobile_phone",session);
			}
			
		}
		return mobile;
	}
	
	/**
	 * 获取session中登陆号码
	 * @param session
	 * @return
	 */
	public static String getSessionUserNo(HttpSession session){
		HashMap user = (HashMap) session.getAttribute("user");
		String userNo = "";
		if(user!=null && !user.isEmpty()){
			userNo=(String) user.get("USER_NO");
		}
		return userNo;
	}
	public static String getSessionUserArea(HttpSession session){
		HashMap user = (HashMap) session.getAttribute("user");
		String area_code = "";
		if(user!=null && !user.isEmpty()){
			area_code=(String) user.get("AREA_CODE");
		}
		return area_code;
	}
	public static String getSessionUserNo(){
		HttpSession session = ContextHelper.getSession();
		return getSessionUserNo(session);
	}
	public static String getSessionUserArea(){
		HttpSession session = ContextHelper.getSession();
		return getSessionUserArea(session);
	}
	/**
	 * 获取session中客户姓名
	 * @param session
	 * @return
	 */
	public static String getSessionCustName(HttpSession session){
		return getCustProp("cust_name",session);
	}
	/**
	 * 获取session中客户地址
	 * @param session
	 * @return
	 */
	public static String getSessionCustAddr(HttpSession session){
		return getCustProp("cust_addr",session);
	}
	/**
	 * 获取session中客户email
	 * @param session
	 * @return
	 */
	public static String getSessionCustEmail(HttpSession session){
		return getCustProp("email_address",session);
	}
	/**
	 * 获取session中客户证件类型
	 * @param session
	 * @return
	 */
	public static String getSessionCustCertiType(HttpSession session){
		return getCustProp("certi_type",session);
	}
	/**
	 * 获取session中客户证件号码
	 * @param session
	 * @return
	 */
	public static String getSessionCustCertiNum(HttpSession session){
		return getCustProp("certi_number",session);
	}
	
	
}
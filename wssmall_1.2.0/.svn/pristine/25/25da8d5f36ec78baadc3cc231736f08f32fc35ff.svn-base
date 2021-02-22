package com.ztesoft.ibss.ct.vo;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.ibss.common.dao.DAOUtils;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Reason.Yea
 * @version Created Feb 3, 2012
 */
public class PageLogVO implements Serializable{
	public static final String PARAM_USER_NO="user_no";
	public static final String PARAM_LOGIN_TYPE="login_type";
	public static final String PARAM_VISIT_URL="visit_url";
	public static final String PARAM_OBJECT_ID="obj_id";
	public static final String PARAM_VISIT_IP="ip";
	public static final String PARAM_LOGIN_PROD_NO="user_no";
	public static final String PARAM_OPR_USER_NO="user_no";
	public static final String PARAM_OPR_PROD_NO="user_no";
	public static final String PARAM_LOGIN_AREA_CODE="area_code";
	public static final String PARAM_OPR_AREA_CODE="area_code";
	public static final String PARAM_CUSTGROUP="cust_group";
	public static final String PARAM_CUSTBRAND="cust_brand";
	public static final String PARAM_LOGON_NAME="user_name";
	public static final String PARAM_SERV_KIND="serv_kind";
	public static final String PARAM_SERV_TYPE="serv_type";
	public static final String PARAM_SERV_NO="serv_no";
	public static final String PARAM_SESSION_ID="session_id";
	public static final String PARAM_SOURCE_URL="source_url";
	public static final String PARAM_SOURCE_DOMAIN="source_domain";
	public static final String PARAM_OPER_SYSTEM="os";
	public static final String PARAM_BROWSER_NAME ="browser";
	public static final String PARAM_SCREEN_SIZE ="screen";
	
	public static final String PARAM_SEQU ="sequ";
	
	
	private String event_id;
	private String user_no;
	private String event_time;
	private String login_type;
	private String visit_url;
	private String object_id;
	private String visit_ip;
	private String login_prod_no;
	private String opr_user_no;
	private String opr_prod_no;
	private String login_area_code;
	private String opr_area_code;
	private String custgroup;
	private String custbrand;
	private String logon_name;
	private String serv_kind;
	private String serv_type;
	private String serv_no;
	private String session_id;
	private String sequ;
	private String source_url;
	private String source_domain;
	private String oper_system;
	private String browser_name;
	private String screen_size;
	
	public void loadFromHashMap(Map m){
		this.setEvent_id("");
		this.setSequ(Const.getStrValue(m, PARAM_SEQU));
		this.setEvent_time(DAOUtils.getFormatedDate());
		
		this.setSession_id(Const.getStrValue(m, PARAM_SESSION_ID));
		this.setSource_url(Const.getStrValue(m, PARAM_SOURCE_URL));
		this.setVisit_ip(Const.getStrValue(m, PARAM_VISIT_IP));
		this.setVisit_url(Const.getStrValue(m, PARAM_VISIT_URL));
		
		this.setUser_no(Const.getStrValue(m, PARAM_USER_NO));
		this.setCustbrand(Const.getStrValue(m, PARAM_CUSTBRAND));
		this.setCustgroup(Const.getStrValue(m, PARAM_CUSTGROUP));
		this.setLogin_area_code(Const.getStrValue(m, PARAM_LOGIN_AREA_CODE));
		this.setLogin_prod_no(Const.getStrValue(m, PARAM_LOGIN_PROD_NO));
		this.setLogin_type(Const.getStrValue(m, PARAM_LOGIN_TYPE));
		this.setLogon_name(Const.getStrValue(m, PARAM_LOGON_NAME));
		this.setOpr_area_code(Const.getStrValue(m, PARAM_OPR_AREA_CODE));
		this.setOpr_prod_no(Const.getStrValue(m, PARAM_OPR_PROD_NO));
		this.setOpr_user_no(Const.getStrValue(m, PARAM_OPR_USER_NO));

		this.setOper_system(Const.getStrValue(m, PARAM_OPER_SYSTEM));
		this.setScreen_size(Const.getStrValue(m, PARAM_SCREEN_SIZE));
		this.setBrowser_name(Const.getStrValue(m, PARAM_BROWSER_NAME));
		this.setObject_id(Const.getStrValue(m, PARAM_OBJECT_ID));
		this.setServ_kind(Const.getStrValue(m, PARAM_SERV_KIND));
		this.setServ_no( Const.getStrValue(m, PARAM_SERV_NO));
		this.setServ_type(Const.getStrValue(m, PARAM_SERV_TYPE));
		
		String urlParts[] = this.visit_url.toLowerCase().split("\\?");
		this.setObject_id(urlParts[0]);
		if(StringUtils.isEmpty(this.serv_kind)){
			this.setServ_kind(StringUtils.getUrlQryStr(urlParts,"serv_kind"));
		}
		if(StringUtils.isEmpty(this.serv_type)){
			this.setServ_type(StringUtils.getUrlQryStr(urlParts,"serv_type"));
		}
		if(StringUtils.isEmpty(this.serv_no)){
			this.setServ_no(StringUtils.getUrlQryStr(urlParts,"serv_no"));
		}
		
		if(!this.source_url.equals("")){
			this.setSource_domain(getDomainFromUrl(this.getSource_url()));
		}
	}
	
	private String getDomainFromUrl(String url) {
		if(url.indexOf("http://")==0){
			url = url.substring(7);
		}else if(url.indexOf("https://")==0){
			url = url.substring(8);
		}
		int e = url.indexOf("/");
		url = url.substring(0,e);
		return url;
	}
//	public static void main(String[] args) {
//		String url ="http://10.34.34.123:10001/service/vipservice/action/membercard.jsp";
//		if(url.indexOf("http://")==0){
//			url = url.substring(7);
//		}else if(url.indexOf("https://")==0){
//			url = url.substring(8);
//		}
//		int e = url.indexOf("/");
//		url = url.substring(0,e);
//		logger.info(url);
//	}
	public String getEvent_id() {
		return event_id;
	}
	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}
	public String getUser_no() {
		return user_no;
	}
	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}
	public String getEvent_time() {
		return event_time;
	}
	public void setEvent_time(String event_time) {
		this.event_time = event_time;
	}
	public String getLogin_type() {
		return login_type;
	}
	public void setLogin_type(String login_type) {
		this.login_type = login_type;
	}
	public String getVisit_url() {
		return visit_url;
	}
	public void setVisit_url(String visit_url) {
		this.visit_url = visit_url;
	}
	public String getObject_id() {
		return object_id;
	}
	public void setObject_id(String object_id) {
		this.object_id = object_id;
	}
	public String getVisit_ip() {
		return visit_ip;
	}
	public void setVisit_ip(String visit_ip) {
		this.visit_ip = visit_ip;
	}
	public String getLogin_prod_no() {
		return login_prod_no;
	}
	public void setLogin_prod_no(String login_prod_no) {
		this.login_prod_no = login_prod_no;
	}
	public String getOpr_user_no() {
		return opr_user_no;
	}
	public void setOpr_user_no(String opr_user_no) {
		this.opr_user_no = opr_user_no;
	}
	public String getOpr_prod_no() {
		return opr_prod_no;
	}
	public void setOpr_prod_no(String opr_prod_no) {
		this.opr_prod_no = opr_prod_no;
	}
	public String getLogin_area_code() {
		return login_area_code;
	}
	public void setLogin_area_code(String login_area_code) {
		this.login_area_code = login_area_code;
	}
	public String getOpr_area_code() {
		return opr_area_code;
	}
	public void setOpr_area_code(String opr_area_code) {
		this.opr_area_code = opr_area_code;
	}
	public String getCustgroup() {
		return custgroup;
	}
	public void setCustgroup(String custgroup) {
		this.custgroup = custgroup;
	}
	public String getCustbrand() {
		return custbrand;
	}
	public void setCustbrand(String custbrand) {
		this.custbrand = custbrand;
	}
	public String getLogon_name() {
		return logon_name;
	}
	public void setLogon_name(String logon_name) {
		this.logon_name = logon_name;
	}
	public String getServ_kind() {
		return serv_kind;
	}
	public void setServ_kind(String serv_kind) {
		this.serv_kind = serv_kind;
	}
	public String getServ_type() {
		return serv_type;
	}
	public void setServ_type(String serv_type) {
		this.serv_type = serv_type;
	}
	public String getServ_no() {
		return serv_no;
	}
	public void setServ_no(String serv_no) {
		this.serv_no = serv_no;
	}
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public String getSequ() {
		return sequ;
	}
	public void setSequ(String sequ) {
		this.sequ = sequ;
	}
	public String getSource_url() {
		return source_url;
	}
	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}
	public String getOper_system() {
		return oper_system;
	}
	public void setOper_system(String oper_system) {
		this.oper_system = oper_system;
	}
	public String getBrowser_name() {
		return browser_name;
	}
	public void setBrowser_name(String browser_name) {
		this.browser_name = browser_name;
	}
	public String getScreen_size() {
		return screen_size;
	}
	public void setScreen_size(String screen_size) {
		this.screen_size = screen_size;
	}

	public void save() throws FrameException {
		List logs = new ArrayList();
		logs.add(this);
		DynamicDict dict = new DynamicDict(1);
		dict.m_ActionId = "PAGE_LOG";
		dict.setValueByName("SESSIONLOGS", logs);
		dict = ActionDispatch.dispatch(dict);
		dict.destroy();
	}

	public String getSource_domain() {
		return source_domain;
	}

	public void setSource_domain(String source_domain) {
		this.source_domain = source_domain;
	}
	
}

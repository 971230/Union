package com.ztesoft.ibss.ct.vo;

import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.ListUtil;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 客户数据对象，需要什么数据直接定义对象获取，统一获取入口 <br>
 * [LOGIN_TYPE=21, CUST_LIST=[{CUSTADDRESS=西山区, HEADSHIP=, VIP_TYPE=,
 * CUSTFLAG=1, CUSTRELALIST=, MODIFYDATE=20110309, NATIONNO=, BUREAU_NO=17104,
 * SEX=, CARDADDR=西山区, MARRIAGE=, RELAMAILNUM=650100, CUSTRELATEL=18987122054,
 * CUSTCARDTYPE=31, CUSTGROUP=401, BIRTHDAY=, CUSTTYPE=10003,
 * CUSTCARDNO=zxq-20080929-1, AREA_CODE=17104, CUSTBRAND=,
 * RELAEMAIL=18987122054@189.com, CUSTRELANAME=zxq-20080929-1, CUSTORG=,
 * BUREAU_NAME=, CUSTINDUSTYPE=, CREATEDATE=20080929,
 * CUST_SYS_ID=2871073856821000, CUSTDEGREE=, NAME=zxq-20080929-1, POLITYTYPE=,
 * ID=717385682}], LOGIN_NAME=18970807787, USER_LOGIN_ID=717385682, VIP_TYPE=,
 * HINT_MSG=, CREATE_TIME=20090609, LOGIN_PASSWD=AE:9E:66:46:C6:1E:A3:AA,
 * CUST_TYPE=10003,
 * SESSION_ID=PnG1c9J0wy1LBNcvfpTTmnyyzJT4sSw890y11TTh7MTVwhG88JmD!529665788!1328006773968,
 * USEREBRANCHNAME=, INDUSTRY_TYPE=, CARD_TYPE=, CARD_NO=, LOGIN_TYPE_NAME=,
 * AREA_CODE_NAME=南昌, LOGIN_PROD_NO=379, CUSTGROUP=401, BUREAUNO=17104,
 * USER_NAME=18970807787, ACCT_LIST=[{BUREAU_NAME=, NAME=zxq-20080929-1,
 * BUREAU_NO=, AREA_CODE=, ACCTNBR97=410101841, CUST_ID=717385682,
 * ID=410101859}, {BUREAU_NAME=, NAME=zxq-20080929-1, BUREAU_NO=, AREA_CODE=,
 * ACCTNBR97=714394455, CUST_ID=717385682, ID=714394455}],
 * USER_LIST=[{PROD_TYPE=4, ACCT_ID=410101859, PHONE_NUM=18908710026,
 * CITYNO=0871, FEETYPE=非OCS预付费, STOPSTATENAME=null, CITY_NAME=南昌市, AgentName=,
 * BUREAU_NO=0871, CustMgrName=, STOPSTATE=, CustMgrTel=, ADDR_DETAIL=西山区,
 * UserBranchName=, EFF_DATE=20081216, PROD_NAME=移动手机(189号段), BureauNo=17104,
 * SENDMESSAGE_FLAG=, PRODSTATE=, NUMSTATE=1, netAccount=, JoinDate=,
 * NUMSTATECNAME=在用, AREA_CODE=0871, BureauName=南昌市盘龙, ACCTNBR97=410101841,
 * PROD_NO=379, NUM=18908710026, UserBranchNo=, AgentTel=, AgentCode=,
 * BUREAU_NAME=null, CUST_SYS_ID=2871073856821000, CRM_ID_TYPE=21,
 * NAME=zxq-20080929-1, CustMgrCode=, USER_PASSWD=, CUST_ID=717385682,
 * ID=230799435}], SENDMESSAGE_FLAG=, BUREAUNAME=南昌市盘龙, LOGIN_AREA_CODE=0871,
 * LAST_LOGON_TIME=2012-01-31 18:50:50, AREA_CODE=0871, CUSTBRAND=,
 * NUM=18987122054, LOGON_TIME=2012-01-31 18:50:50,
 * CUST_SYS_ID=2871073856821000, LOGIN_CITY_NAME=南昌市, USERBRANCHNO=,
 * USER_NO=18970807787, LOGON_IP=127.0.0.1, CUST_ID=717385682]
 * 
 * @author wui<br>
 *         用户信息对象
 * 
 */
public class User {
	public static HttpSession getSession() {
		WebContext ctx = WebContextFactory.get();
		HttpSession session = ctx.getSession();
		return session;
	}

	/**
	 * 获取用户信息
	 * 
	 * @return
	 */
	public static Map getUser() {
		Map user = (Map) getSession().getAttribute("user");
		return user;
	}
	
	/**
	 * 获取用户信息
	 * 
	 * @return
	 */
	public static Map getAucUser() {
		Map user = (Map) getSession().getAttribute("auc_user");
		return user;
	}
	
	/**
	 * 获取用户编号
	 * 
	 * @return
	 */
	public static String getAucUserId() {
		Map user = getAucUser();
		if (user == null)
			return "-1";
		return Const.getStrValue(user, "PHONE_NUM");
	}
	
	/**
	 * 获取用户编号
	 * 
	 * @return
	 */
	public static String getAucEmail() {
		Map user = getAucUser();
		if (user == null)
			return "-1";
		return Const.getStrValue(user, "EMAIL");
	}

	/**
	 * 获取用户编号
	 * 
	 * @return
	 */
	public static String getUser_no() {
		Map user = getUser();
		if (user == null)
			return "";
		return Const.getStrValue(user, "USER_NO");
	}

	public static String getAcctId() {
		Map user = getUser();
		String acct_id ="";
		List acctList = (List)user.get("ACCT_LIST");
		if(!ListUtil.isEmpty(acctList)){
			Map acctMap =(Map)acctList.get(0);
			acct_id =Const.getStrValue(acctMap, "acct_id");
		}
		return acct_id;
	}
	
	/**
	 * 获取客户id
	 * 
	 * @return
	 */
	public static String getCust_id() {
		Map user = getUser();
		if (user == null)
			return "";
		return Const.getStrValue(user, "USE_CUST_ID");
	}
	
	//登录类型
	public static String getLogin_type() {
		Map user = getUser();
		if (user == null)
			return "";
		return Const.getStrValue(user, "LOGIN_TYPE");
	}
	
	public static String getStaffNo() {
		Map user = getUser();
		if (user == null)
			return "";
		return Const.getStrValue(user, "STAFF-NO");
	}
	
	public static String getBureau_no() {
		Map user = getUser();
		if (user == null)
			return "";
		return Const.getStrValue(user, "BUREAU-NO");
	}
	
	public static String getSite_no() {
		Map user = getUser();
		if (user == null)
			return "";
		return Const.getStrValue(user, "SITE-NO");
	}
	
	public static String getCust_brand() {
		Map user = getUser();
		if (user == null)
			return "";
		return Const.getStrValue(user, "CUSTBRAND");
	}
	
	
	public static String getCust_group() {
		Map user = getUser();
		if (user == null)
			return "";
		return Const.getStrValue(user, "CUSTGROUP");
	}
	
	public static String getLogin_num() {
		Map user = getUser();
		if (user == null)
			return "";
		return Const.getStrValue(user, "NUM");
	}
	
	public static String getLogin_area_code() {
		Map user = getUser();
		if (user == null)
			return "";
		return Const.getStrValue(user, "LAN_CODE");
	}
	public static String getPreIp() {
		Map user = getUser();
		if (user == null)
			return "";
		return Const.getStrValue(user, "LOGON_IP");
	}
	
	public static String getLogin_name() {
		Map user = getUser();
		if (user == null)
			return "";
		return Const.getStrValue(user, "USER_NAME");
	}
	
	
	public static String getUser_name() {
		Map user = getUser();
		if (user == null)
			return "";
		return Const.getStrValue(user, "USER_NAME");
	}
	
	
	
	
	public static String getSessionId() {
		return getSession().getId();
	}
	
	public static String getBusinessId() {
		String vSERVID = "";
		String vBUSINESSID = "";
		String vUSER_NO = "";
		String LOGIN_NAME = "";
		String NUM = "";
		HashMap user = (HashMap)getSession().getAttribute("user");
		if(user==null){
			user = (HashMap)getSession().getAttribute("USER_BEFORE_LOGIN");  //签协议之前的session值；
		}
		if(user != null){
			vUSER_NO = user.get("USER_NO")==null?"":getUser_no();
			LOGIN_NAME = user.get("USER_NAME")==null?"":getLogin_name();
			ArrayList user_list =(ArrayList)user.get("USER_LIST");
			if(user_list != null){
				for(int i=0;i<user_list.size();i++){
					HashMap hasUser = (HashMap)user_list.get(i);
					vSERVID = hasUser.get("SERVID")==null?"":(String)hasUser.get("SERVID");
					NUM = hasUser.get("NUM")==null?"":(String)hasUser.get("NUM");
					if(vUSER_NO.equals(vSERVID)||LOGIN_NAME.equals(NUM)){
						vBUSINESSID = (String)hasUser.get("BusinessId");
						break;
					}
					vSERVID = "";
				}
			}
		}
		return vBUSINESSID;
	}
	
	
	public static String getServId() {
		String vSERVID = "";
		String vBUSINESSID = "";
		String vUSER_NO = "";
		String LOGIN_NAME = "";
		String NUM = "";
		HashMap user = (HashMap)getSession().getAttribute("user");
		if(user==null){
			user = (HashMap)getSession().getAttribute("USER_BEFORE_LOGIN");  //签协议之前的session值；
		}
		if(user != null){
			vUSER_NO = user.get("USER_NO")==null?"":getUser_no();
			LOGIN_NAME = user.get("LOGIN_NAME")==null?"":getLogin_name();
			ArrayList user_list =(ArrayList)user.get("USER_LIST");
			if(user_list != null){
				for(int i=0;i<user_list.size();i++){
					HashMap hasUser = (HashMap)user_list.get(i);
					vSERVID = hasUser.get("SERVID")==null?"":(String)hasUser.get("SERVID");
					NUM = hasUser.get("NUM")==null?"":(String)hasUser.get("NUM");
					if(vUSER_NO.equals(vSERVID)||LOGIN_NAME.equals(NUM)){
						vBUSINESSID = (String)hasUser.get("BusinessId");
						break;
					}
					vSERVID = "";
				}
			}
		}
		return vSERVID;
	}
	
	
}

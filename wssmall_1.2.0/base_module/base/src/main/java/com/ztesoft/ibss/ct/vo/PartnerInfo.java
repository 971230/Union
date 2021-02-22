package com.ztesoft.ibss.ct.vo;

import com.ztesoft.common.util.ContextHelper;
import com.ztesoft.ibss.common.util.Const;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 
 * {partname=测试账号, bindphone=18970993899, linkname=, coopname=测试账号, sex=,
 *  partid=10000, linkaddress=南昌市西湖区洛阳路, linkphone=18970993899,
 *   email=, state=1, roleid=2, qq=, longmark=}
 * @author wui<br>
 *         用户信息对象
 * 
 */
public class PartnerInfo {

	public static HttpServletRequest getRequest() {
		return ContextHelper.getRequest();
	}

	public static HttpSession getSession() {
		return ContextHelper.getSession();
	}

	/**
	 * 获取用户信息
	 * 
	 * @return
	 */
	public static Map getUser() {
		HttpSession session = getSession();
		if(session  == null)
			return null;
		else{
			Map user = (Map) getSession().getAttribute("partner_info");
			return user;
		}
	}

	/**
	 * 获取用户名称
	 * 
	 * @return
	 */
	public static String getPartyName() {
		Map user = getUser();
		return Const.getStrValue(user, "partname");
	}


	/**
	 * 获取绑定电话
	 * 
	 * @return
	 */
	public static String getBindPhone() {
		Map user = getUser();
		String phone_num = Const.getStrValue(user, "bindphone");
		if(StringUtils.isEmpty(phone_num))
			phone_num =getLinkPhone();
		return phone_num;
	}


	/**
	 * 获取联系人
	 * 
	 * @return
	 */
	public static String getLinkName() {
		Map user = getUser();
		return Const.getStrValue(user, "linkname");
	}

	/**
	 * 获取公司名称
	 * 
	 * @return
	 */
	public static String getCoopName() {
		Map user = getUser();
		return Const.getStrValue(user, "coopname");
	}

	/**
	 * 获取用户id
	 * 
	 * @return
	 */
	public static String getPartyId() {
		Map user = getUser();
		return Const.getStrValue(user, "partid");
	}
	
	/**
	 * 获取联系地址
	 * 
	 * @return
	 */
	public static String getLinkAddress() {
		Map user = getUser();
		return Const.getStrValue(user, "linkaddress");
	}
	
	/**
	 * 获取联系电话
	 * 
	 * @return
	 */
	public static String getLinkPhone() {
		Map user = getUser();
		return Const.getStrValue(user, "linkphone");
	}
	
	/**
	 * 获取角色id
	 * 
	 * @return
	 */
	public static String getRoleId() {
		Map user = getUser();
		return Const.getStrValue(user, "roleid");
	}
	/**
	 * 获取状态
	 * 
	 * @return
	 */
	public static String getState() {
		Map user = getUser();
		return Const.getStrValue(user, "state");
	}
	/**
	 * 价保款
	 * 
	 * @return
	 */
	public static String getJB() {
		Map user = getUser();
		return Const.getStrValue(user, "bj");
	}	
	/**
	 * 提货奖励
	 * 
	 * @return
	 */
	public static String getJL() {
		Map user = getUser();
		return Const.getStrValue(user, "jb");
	}	
}

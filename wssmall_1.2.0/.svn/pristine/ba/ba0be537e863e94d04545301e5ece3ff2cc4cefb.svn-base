package com.powerise.ibss.system;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.util.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Vector;

public class LogonAction{
	private String msg; 		//保存提示或错误信息
	private Vector staff_fun;	//保存员工菜单信息
	
   	/*
	用户登录操作,用户验证在PowerIBSS系统进行
	@param p_staff_code			员工编号
	@param p_passwd   			密码
	@param p_ip       			IP地址
	@param p_request Request	对象
	*/
	public int Logon(String p_staff_code, String p_passwd, String p_ip, HttpServletRequest p_request){
		HttpSession session = p_request.getSession();
		int 		iFlag	= 0;
		if (session == null){
			msg	= "系统不支持Session回话，操作无法进行！";
			return -1;
		}
		int login_times = 0;
		if (session.getAttribute("login_time") != null)
			login_times = Integer.parseInt((String)session.getAttribute("login_time"));
		/*
		if (login_times > 3){
			msg = "你已登录系统三次，为确保系统安全起见，现在你不能登录了。有什么疑问与系统管理员联系！";
			return -1;
		}
		*/
		try{
			DynamicDict _dict	= new DynamicDict(1);
			_dict.m_ActionId	= "MSM_000";
			_dict.setValueByName("staff_code",	p_staff_code);
			_dict.setValueByName("password",	p_passwd);
			_dict.setValueByName("ip",			p_ip);
			_dict = ActionDispatch.dispatch(_dict);
			if (_dict.flag < 0){
				iFlag	= _dict.flag;
				//msg		= Utility.isoToGBK(_dict.msg);
				msg		= _dict.msg;
				login_times++;
				session.setAttribute("login_time",String.valueOf(login_times));     
			}else{
				session.removeAttribute("login_time");
				HashMap _info = (HashMap)_dict.getValueByName("staff_info");
				
				Utility.println("设置员工会话信息.............1");
				SessionOBJ sessionObj = new SessionOBJ(_info);
				session.setAttribute("user", sessionObj);
				//得到员工菜单信息
				staff_fun = (Vector)_dict.getValueByName("staff_fun");
			}
		}catch(FrameException fre){
			iFlag	= fre.getErrorCode();
			msg		= fre.getErrorMsg();
		}
		return iFlag;
	}

	public String getMsg(){
		//return SysSet.isoToGBK(msg);
		//去掉回车
		msg=msg.replace('\n',' ');
		return msg;
	}
	
	public Vector getStaffFun(){
		return staff_fun;
	}
}

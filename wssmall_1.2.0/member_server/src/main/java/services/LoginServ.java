package services;


import params.LoginUser;
import params.ZteError;
import params.login.req.LoginReq;
import params.login.resp.LoginResp;

import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import commons.CommonTools;

import consts.ConstsCore;

/**
 * 用户登录
* @作者 MoChunrun 
* @创建日期 2013-9-25 
* @版本 V 1.0
 */
public class LoginServ extends ServiceBase implements LoginInf{

	private MemberInf memberServ;
	
	@Override
	public LoginResp login(LoginReq req){
		try{
			LoginResp resp = new LoginResp();
			if(StringUtil.isEmpty(req.getUserName())&&StringUtil.isEmpty(req.getMobile())) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"UserName不能为空"));
			if(req.getPwd()==null || "".equals(req.getPwd())) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"Pwd不能为空"));
			
			resp = memberServ.loginValid(req);
			Member member = null;
			if("0".equals(resp.getError_code())){
				member = (Member) ThreadContextHolder.getSessionContext().getAttribute(IUserService.CURRENT_MEMBER_KEY);
				resp.setMember(member);
			}else{
				CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"用户名或密码错误"));
			}
			LoginUser lu = new LoginUser();
			lu.setMember(member);
			lu.setLoginTime(System.currentTimeMillis());
			resp.setUserSessionId(req.getUserSessionId());
			addReturn(req,resp);
			return resp;
		}catch(RuntimeException ex){
			throw ex;
		}catch(Exception ex){
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"系统繁忙"));
			return null;
		}
	}

	
	public MemberInf getMemberServ() {
		return memberServ;
	}

	public void setMemberServ(MemberInf memberServ) {
		this.memberServ = memberServ;
	}

}

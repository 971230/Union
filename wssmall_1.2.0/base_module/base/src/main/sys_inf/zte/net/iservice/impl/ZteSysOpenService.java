package zte.net.iservice.impl;

import params.CallerLogReq;
import params.ZteResponse;
import params.adminuser.req.AccessLogReq;
import params.adminuser.req.AdminUserPageReq;
import params.adminuser.req.IsLoginReq;
import params.adminuser.req.LoginLogReq;
import params.adminuser.req.MessageDetailReq;
import params.adminuser.req.MessageListReq;
import params.adminuser.req.SmsValidCodeGetReq;
import params.adminuser.req.UserPasswordUpdateReq;
import params.adminuser.req.UserWdLoginReq;
import params.adminuser.req.ZbAdminUserGetReq;
import params.adminuser.resp.AccessLogResp;
import params.adminuser.resp.AdminUserPageResp;
import params.adminuser.resp.IsLoginResp;
import params.adminuser.resp.LoginLogResp;
import params.adminuser.resp.MessageDetaiResp;
import params.adminuser.resp.MessageListResp;
import params.adminuser.resp.SmsValidCodeGetResp;
import params.adminuser.resp.UserPasswordUpdateResp;
import params.adminuser.resp.UserWdLoginResp;
import params.adminuser.resp.ZbAdminUserGetResp;
import zte.net.iservice.ISysService;

import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;


@ServiceMethodBean(version="1.0")
public class ZteSysOpenService implements ISysService{
	
	private ISysService sysService;
	private void init(){
		sysService= ApiContextHolder.getBean("sysService");
    }
	
	@Override
	@ServiceMethod(method="zte.sysService.admin.qryUser",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public AdminUserPageResp qryAdminUser(AdminUserPageReq adminUserPageReq){
		
		this.init();
		
		return this.sysService.qryAdminUser(adminUserPageReq);
	}
	
	@Override
	@ServiceMethod(method="zte.sysService.admin.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZbAdminUserGetResp getAdminUser(ZbAdminUserGetReq req){
		this.init();
		return this.sysService.getAdminUser(req);
	}
	
	@Override
	@ServiceMethod(method="messageServ.listMsg",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MessageListResp listMsg(MessageListReq messageListReq) {
		this.init();
		return  this.sysService.listMsg(messageListReq);
	}

	@Override
	@ServiceMethod(method="messageServ.getMessageDetail",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MessageDetaiResp getMessageDetail(MessageDetailReq messageDetailReq) {
		this.init();
		return  this.sysService.getMessageDetail(messageDetailReq);
	}

	@Override
	@ServiceMethod(method="sysService.accessLog",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public AccessLogResp accessLog(AccessLogReq accessLogReq) {
		this.init();
		return this.sysService.accessLog(accessLogReq);
	}

	@Override
	@ServiceMethod(method="sysService.loginLog",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public LoginLogResp loginLog(LoginLogReq loginLogReq) {
		this.init();
		return this.sysService.loginLog(loginLogReq);
	}

	@Override
	@ServiceMethod(method="sysService.logCall",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse logCall(CallerLogReq callerLogReq) {
		this.init();
		return this.sysService.logCall(callerLogReq);
	}

	@Override
	@ServiceMethod(method="zte.service.sysService.getUserWd",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public UserWdLoginResp getUserWd(UserWdLoginReq req) {
		this.init();
		return this.sysService.getUserWd(req);
	}
	
	@Override
	@ServiceMethod(method="zte.service.sysService.updateUserPassword",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public UserPasswordUpdateResp updateUserPassword(UserPasswordUpdateReq req) {
		this.init();
		return  this.sysService.updateUserPassword(req);
	}
	@Override
	@ServiceMethod(method="zte.service.sysService.IsLogin",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public IsLoginResp isLogin(IsLoginReq req) {
		this.init();
		return this.sysService.isLogin(req);
	}
	
	@Override
	@ServiceMethod(method="zte.service.sysService.getSmsValidCode",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public SmsValidCodeGetResp getSmsValidCode(SmsValidCodeGetReq req){
		this.init();
		return this.sysService.getSmsValidCode(req);
	}
	
	public static void main(String[] str){
		/*
		//登录
	    UserWdLoginReq userWdLoginReq = new UserWdLoginReq();
	    userWdLoginReq.setUsername("admin");
	    userWdLoginReq.setPassword("123");
	    ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_SHP);
	    UserWdLoginResp resp = client.execute(userWdLoginReq,UserWdLoginResp.class);
	    System.out.print("===="+resp.getUserSessionId());*/
		/*
		//修改密码
		UserPasswordUpdateReq req = new UserPasswordUpdateReq();
		req.setUsername("admin11111");
		req.setOldPassword("123");
		req.setNewPassword("1234");
		req.setIs_loser(false);
		ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_SHP);
		UserPasswordUpdateResp resp =  client.execute(req,UserPasswordUpdateResp.class);
		System.out.print("==========="+resp.getError_code());-*/
		/*MessageListReq	req = new MessageListReq(); 
		req.setNum(1);//1是收件箱 2是发件箱
		req.setPageNo(1);
		req.setPageSize(10);
		req.setUserSessionId("1fc89068f85d4ca5a823c9ab9d56324a");
		req.setReciverState(Consts.READ_STATE_3);//3是查询查询所有
		ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_SHP);
		MessageListResp resp =  client.execute(req,MessageListResp.class);
		System.out.print(resp.getError_code());*/
		
		/*MessageDetailReq  req = new MessageDetailReq();
		req.setM_id("201309122159001404");
		req.setUser_id("1");
		ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_SHP);
		MessageDetaiResp resp = client.execute(req,MessageDetaiResp.class);
		System.out.print(resp.getError_code());*/
		
		IsLoginReq IsLoginReq = new IsLoginReq();
		IsLoginReq.setUserSessionId("5f4f9acb6f414c4db0d3b5e1a3bbfb03");
		ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_SHP);
		IsLoginResp resp = client.execute(IsLoginReq,IsLoginResp.class);
	    
	}

	

}
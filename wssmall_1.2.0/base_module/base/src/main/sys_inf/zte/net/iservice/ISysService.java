package zte.net.iservice;

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

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;


@ZteSoftCommentAnnotation(type="class",desc="系统管理API",summary="系统管理API[用户查询等API]")
public interface ISysService {

	@ZteSoftCommentAnnotation(type="method",desc="查询用户",summary="查询用户")
	public AdminUserPageResp qryAdminUser(AdminUserPageReq adminUserPageReq);
	
	@ZteSoftCommentAnnotation(type="method",desc="查询消息",summary="查询用户")
	public MessageListResp listMsg(MessageListReq messageListReq); 
	
	@ZteSoftCommentAnnotation(type="method",desc="根据id查询消息",summary="查询用户")
	public MessageDetaiResp getMessageDetail(MessageDetailReq messageDetailReq);
	
	@ZteSoftCommentAnnotation(type="method",desc="访问日志",summary="访问日志")
	public AccessLogResp accessLog(AccessLogReq accessLogReq);
	
	@ZteSoftCommentAnnotation(type="method",desc="登陆日志",summary="登陆日志")
	public LoginLogResp loginLog(LoginLogReq loginLogReq);
	
	@ZteSoftCommentAnnotation(type="method",desc="调用sdk写接口日志",summary="调用sdk写接口日志")
	public ZteResponse logCall(CallerLogReq callerLogReq);	
	
	@ZteSoftCommentAnnotation(type="method",desc="获取操作人信息",summary="获取操作人信息")
	public ZbAdminUserGetResp getAdminUser(ZbAdminUserGetReq req);
	@ZteSoftCommentAnnotation(type="method",desc="获取微店登录人信息",summary="获取微店登录人信息")
	public UserWdLoginResp getUserWd(UserWdLoginReq req);
	@ZteSoftCommentAnnotation(type="method",desc="修改密码或者找回密码",summary="修改密码或者找回密码")
	public UserPasswordUpdateResp updateUserPassword(UserPasswordUpdateReq req);
	@ZteSoftCommentAnnotation(type="method",desc="判断是否已登录",summary="判断是否已登录")
	public IsLoginResp isLogin(IsLoginReq req);
	@ZteSoftCommentAnnotation(type="method",desc="获取登录验证码",summary="获取登录验证码")
	public SmsValidCodeGetResp getSmsValidCode(SmsValidCodeGetReq req);
}
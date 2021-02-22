package com.ztesoft.parameters;

import java.util.UUID;

import params.ZteBusiRequest;
import params.ZteRequest;
import utils.CoreThreadLocalHolder;
import utils.GlobalThreadLocalHolder;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.rop.annotation.Temporary;
import com.ztesoft.rop.common.RopRequest;
import com.ztesoft.rop.common.RopRequestContext;
import commons.CommonTools;

/**
 * <pre>
 * 所有请求对象应该通过扩展此抽象类实现
 * </pre>
 * 
 * @author
 * @version 1.0
 */
public abstract class AbstractRopRequest extends ZteBusiRequest  implements RopRequest {

	//@NotNull
	protected String sessionId = "";
	
	//@NotNull
	public String  userSessionId = "";

	@Temporary
	private RopRequestContext ropRequestContext;

	@Override
	@NotDbField
	public RopRequestContext getRopRequestContext() {
		return ropRequestContext;
	}
	@NotDbField
	public final void setRopRequestContext(RopRequestContext ropRequestContext) {
		this.ropRequestContext = ropRequestContext;
	}

	@Override
	@NotDbField
	@Deprecated
	public String getSessionId() { //后续改用getUserSessionId获取sessionId
		return sessionId;
	}

	@Override
	@NotDbField
	@Deprecated
	public void setSessionId(String sessionId) {//后续改用getUserSessionId获取sessionId
		this.sessionId = sessionId;
	}
	
	@Override
	@NotDbField
	public String getUserSessionId() {
		
		//dubbo、rop方式调用，zteRequest sessionId优先级最高
		ZteRequest zteRequest = CoreThreadLocalHolder.getInstance().getZteCommonData().getZteRequest();
		if(zteRequest!=null && !StringUtil.isEmpty(zteRequest.userSessionId)) //用户登录的sessionId
		{
			userSessionId = zteRequest.userSessionId;
			GlobalThreadLocalHolder.getInstance().setUserSessionUUID(userSessionId);
		}
		//后面按正常逻辑处理，rop、dubbo方式调用必须生成sessionId，生成sessionId后走缓存那套session共享的逻辑
		if(StringUtil.isEmpty(userSessionId) || "null".equals(userSessionId)){
			userSessionId = UUID.randomUUID().toString().replace("-", "");//add by wui强制生成session_id
//			userSessionId = CommonTools.getUserSessionId();
		}else if(!userSessionId.equals(CommonTools.getUserSessionId())){
			 GlobalThreadLocalHolder.getInstance().setUserSessionUUID(userSessionId);
		}
//		System.out.print(userSessionId+"==========================================================================getSessionId");
		return userSessionId;
	}

	
	public void setRealUserSessionId(String userSessionId) {
		this.userSessionId ="";
	}
	
	/**
	 * 登录后设置会话id
	 */
	@Override
	@NotDbField
	public void setUserSessionId(String userSessionId) {
		if(StringUtil.isEmpty(userSessionId)){
			this.userSessionId =""; //add by wui
			return;
		}
		this.userSessionId =userSessionId;
		GlobalThreadLocalHolder.getInstance().setUserSessionUUID(userSessionId);
		ZteRequest zteRequest = CoreThreadLocalHolder.getInstance().getZteCommonData().getZteRequest();
		if(zteRequest!=null && StringUtil.isEmpty(zteRequest.userSessionId))
		{
			zteRequest.userSessionId = userSessionId;
		}
		//System.out.print(userSessionId+"==========================================================================setSessionId");
	}
	
	@Override
	@NotDbField
	public  void check() throws ApiRuleException{
	}
	/**
	 * 获取TOP的API名称。
	 * 
	 * @return API名称
	 */
	@Override
	@NotDbField
	public  String getApiMethodName(){
		return "";
	}
}

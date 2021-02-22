package zte.net.iservice;

import params.RuleParams;
import params.ZteResponse;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.params.order.req.EcsTestReq;
import zte.params.order.req.SmsSendReq;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="广东联通ECS对外开放API",summary="广东联通ECS对外开放API[调用本地规则]")
public interface IEcsServices {

	
	/**
	 * 执行规则
	 * @作者 wu.i
	 * @创建日期 2014-3-4 
	 * @param RuleParams
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="执行应用平台规则",summary="执行应用平台规则")
	public ZteResponse processrule(RuleParams ruleParams) throws ApiBusiException;
	
	public ZteResponse ecsTest(EcsTestReq test) throws ApiBusiException;
	
	public ZteResponse smsSend(SmsSendReq req) throws ApiBusiException;
	
	
}

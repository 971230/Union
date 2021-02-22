package zte.net.iservice;

import zte.net.params.req.CataloguePlanExeReq;
import zte.net.params.req.PlanQueryReq;
import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.req.PlanRuleTreeQueryReq;
import zte.net.params.req.RuleConfigGetReq;
import zte.net.params.req.RuleExeLogDelReq;
import zte.net.params.req.RuleLogQueryReq;
import zte.net.params.req.RuleLogReq;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.CataloguePlanExeResp;
import zte.net.params.resp.PlanQueryResp;
import zte.net.params.resp.PlanRuleTreeExeResp;
import zte.net.params.resp.PlanRuleTreeQueryResp;
import zte.net.params.resp.RuleConfigGetResp;
import zte.net.params.resp.RuleExeLogDelResp;
import zte.net.params.resp.RuleLogQueryResp;
import zte.net.params.resp.RuleLogRsp;
import zte.net.params.resp.RuleTreeExeResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="规则管理API",summary="规则管理API")
public interface IRuleService {

	@ZteSoftCommentAnnotation(type="method",desc="查询方案信息",summary="查询方案信息")
	public PlanQueryResp queryPlan(PlanQueryReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="查询方案规则树",summary="查询方案规则树")
	public PlanRuleTreeQueryResp queryPlanRuleTree(PlanRuleTreeQueryReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="执行方案规则树",summary="执行方案规则树")
	public PlanRuleTreeExeResp exePlanRuleTree(PlanRuleTreeExeReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="执行规则树",summary="执行规则树")
	public RuleTreeExeResp exeRuleTree(RuleTreeExeReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="目录方案",summary="目录方案[所有方案互斥]")
	public CataloguePlanExeResp exeCataloguePlan(CataloguePlanExeReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="删除方案规则执行日志",summary="删除方案规则执行日志")
	public RuleExeLogDelResp delRuleExeLog(RuleExeLogDelReq req);
	@ZteSoftCommentAnnotation(type="method",desc="查询规则",summary="查询规则")
	public RuleConfigGetResp getRuleConfig(RuleConfigGetReq req);

	@ZteSoftCommentAnnotation(type="method",desc="查询指定规则执行日志",summary="查询指定规则执行日志")
	public RuleLogQueryResp queryRuleLog(RuleLogQueryReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="查询规则执行日志",summary="查询规则执行日志")
	public RuleLogRsp qryRuleLog(RuleLogReq req) throws Exception;
	
//	@ZteSoftCommentAnnotation(type="method",desc="根据环节查询方案",summary="根据环节查询方案")
//	public PlanGetResp getPlanByTache(PlanGetReq req);
	
}

package com.ztesoft.zsmart.hound.client.encoder;

import com.ztesoft.net.auto.rule.fact.AutoFact;

import zte.net.params.req.CataloguePlanExeReq;
import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.CataloguePlanExeResp;
import zte.net.params.resp.PlanRuleTreeExeResp;
import zte.net.params.resp.RuleTreeExeResp;

public class ReqParamsEncoder extends AbstractHoundEncoder {

	@Override
	public String encode(Object arg0) {
		// TODO Auto-generated method stub
		long begin = System.currentTimeMillis();
		String objStr = this.toJSONString(arg0, "fact");
		long begin1 = System.currentTimeMillis();
		//logger.info("=======PlanRuleReqEncoder=========begin1-begin:"
		//		+ (begin1 - begin));
		if (arg0 instanceof PlanRuleTreeExeReq
				|| arg0 instanceof RuleTreeExeReq
				|| arg0 instanceof CataloguePlanExeReq
				|| arg0 instanceof PlanRuleTreeExeResp
				|| arg0 instanceof RuleTreeExeResp
				|| arg0 instanceof CataloguePlanExeResp) {

			AutoFact fact = null;
			String factStr = "";
			if (arg0 instanceof RuleTreeExeReq) {
				fact = ((RuleTreeExeReq) arg0).getFact();
				factStr = new AutoFactEncoder().encode(fact);
			} else if (arg0 instanceof PlanRuleTreeExeReq) {
				fact = ((PlanRuleTreeExeReq) arg0).getFact();
				factStr = new AutoFactEncoder().encode(fact);
			} else if (arg0 instanceof CataloguePlanExeReq) {
				fact = ((CataloguePlanExeReq) arg0).getFact();
				factStr = new AutoFactEncoder().encode(fact);
			} else if (arg0 instanceof RuleTreeExeResp) {
				fact = ((RuleTreeExeResp) arg0).getFact();
				factStr = new AutoFactEncoder().encode(fact);
			} else if (arg0 instanceof PlanRuleTreeExeResp) {
				fact = ((PlanRuleTreeExeResp) arg0).getFact();
				factStr = new AutoFactEncoder().encode(fact);
			} else if (arg0 instanceof CataloguePlanExeResp) {
				fact = ((CataloguePlanExeResp) arg0).getFact();
				factStr = new AutoFactEncoder().encode(fact);
			} 

			long begin2 = System.currentTimeMillis();

			//logger.info("=======PlanRuleReqEncoder=======begin2-begin1:"
			//		+ (begin2 - begin1));
			return objStr + ",fact=" + factStr;

		}


		return objStr;
	}
}

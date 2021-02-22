package com.ztesoft.net.rule.common;

import java.util.List;
import org.apache.log4j.Logger;

import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.auto.rule.vo.RuleConsts;

//@DroolsFact(name="公共fact",code="commonFact")
public class CommonFact extends AutoFact {
	private static Logger logger = Logger.getLogger(CommonFact.class);
	@Override
	public void exeAction(AutoFact fact, List<RuleConsts> consts)
			throws RuntimeException {
		// TODO Auto-generated method stub
		logger.info("===============>>>");
	}

	@Override
	public String getObj_id() {
		// TODO Auto-generated method stub
		return "1";
	}

	@Override
	public String getTrace_flow_id() {
		
		return "abc";
	}
}

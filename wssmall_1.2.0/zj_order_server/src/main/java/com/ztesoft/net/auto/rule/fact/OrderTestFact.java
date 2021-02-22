package com.ztesoft.net.auto.rule.fact;

import java.util.List;

import com.ztesoft.net.auto.rule.vo.RuleConsts;

public class OrderTestFact extends AutoFact {
	
	@Override
	public void exeAction(AutoFact fact, List<RuleConsts> consts) throws RuntimeException {
//		if("4".equals(this.getRule().getRule_id())){
//			resp.setError_code("1");
//			throw new RuntimeException("11111111");
//		}
	}

	@Override
	public String getObj_id() {
		return "1";
	}

	@Override
	public String getTrace_flow_id() {
		return "1111";
	}
	
	public int getIdx(){
		return 1;
	}

}

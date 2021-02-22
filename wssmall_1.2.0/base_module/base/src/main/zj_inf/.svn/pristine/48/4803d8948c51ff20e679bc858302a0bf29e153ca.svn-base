package zte.net.ecsord.rule.comm;

import java.util.List;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.net.annotation.DroolsFact;
import com.ztesoft.net.annotation.DroolsFactField;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.auto.rule.vo.RuleConsts;

@DroolsFact(name="异常通知",code="exceptionFact")
public class ExceptionFact extends AutoFact {
	
	private String order_id;	
	
	@DroolsFactField(name="异常来源", ele_type="checkbox", stype_code="ORDER_ABNORMAL_TYPE")
	private String exception_from;
	
	private String exception_type;//异常类型
	
	private String exception_desc;//异常描述
	
	private String notify_sr;	//是否通知森锐掉卡
	
	@Override
	public void exeAction(AutoFact fact, List<RuleConsts> consts)
			throws RuntimeException {
		
	}

	@Override
	public String getObj_id() {
		// TODO Auto-generated method stub
		return order_id;
	}

	@Override
	public String getTrace_flow_id() {
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		trace_flow_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		return trace_flow_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getException_from() {
		return exception_from;
	}

	public void setException_from(String exception_from) {
		this.exception_from = exception_from;
	}

	public String getException_type() {
		return exception_type;
	}

	public void setException_type(String exception_type) {
		this.exception_type = exception_type;
	}

	public String getException_desc() {
		return exception_desc;
	}

	public void setException_desc(String exception_desc) {
		this.exception_desc = exception_desc;
	}

	public String getNotify_sr() {
		return notify_sr;
	}

	public void setNotify_sr(String notify_sr) {
		this.notify_sr = notify_sr;
	}
}

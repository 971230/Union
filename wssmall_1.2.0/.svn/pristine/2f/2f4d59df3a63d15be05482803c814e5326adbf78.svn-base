package zte.net.ecsord.rule.logistics;

import java.util.List;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.net.annotation.DroolsFact;
import com.ztesoft.net.annotation.DroolsFactField;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.auto.rule.vo.RuleConsts;

@DroolsFact(name="物流打印模板匹配",code="logisticsFact")
public class LogisticsFact extends AutoFact{
	private String order_id;
	
	@DroolsFactField(name="物流公司编码", ele_type="checkbox", stype_code="DC_LOGI_COMPANY")
	private String company_code;
	
	@DroolsFactField(name="物流打印模板编码", ele_type="checkbox", stype_code="DC_MODEL_CD")
	private String model_id;

	public String getCompany_code() {
		
		return company_code;
		//return CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIPPING_COMPANY);
	}

	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}
	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	@Override
	public void exeAction(AutoFact fact, List<RuleConsts> consts)
			throws RuntimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getObj_id() {
		
		return obj_id;
	}

	@Override
	public String getTrace_flow_id() {
		// TODO Auto-generated method stub
		return null;
	}
	
}

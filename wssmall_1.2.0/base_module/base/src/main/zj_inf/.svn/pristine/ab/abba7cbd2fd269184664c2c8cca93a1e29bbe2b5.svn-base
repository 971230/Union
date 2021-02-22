package zte.net.ecsord.rule.mode;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztesoft.net.annotation.DroolsFact;
import com.ztesoft.net.annotation.DroolsFactField;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.auto.rule.vo.RuleConsts;

@DroolsFact(name="前置规则",code="preRuleFact")
public class PreRuleFact extends AutoFact {
	private static Logger logger = Logger.getLogger(PreRuleFact.class);
	private Map paramMap;
	private String order_id;

	@DroolsFactField(name="地市", ele_type="checkbox", stype_code="DC_MODE_REGION")
	private String region;
	
	@DroolsFactField(name="商品类型", ele_type="checkbox", stype_code="DC_MODE_GOODS_TYPE")
	private String goods_type;
	
	@DroolsFactField(name="订单来源", ele_type="checkbox", stype_code="DC_MODE_SHOP_CODE")
	private String shop_code;
	@DroolsFactField(name="商品小类", ele_type="checkbox", stype_code="DC_GOODS_CAT_ID")
	private String goods_cat_id;
	
	@DroolsFactField(name="商品编码", ele_type="input", stype_code="")
	private String goods_id;
	
	@DroolsFactField(name="生产模式", ele_type="radio", stype_code="DC_MODE_OPER_MODE")
	private String oper_mode;
	
	@DroolsFactField(name="流程", ele_type="list", stype_code="/shop/admin/workFlowAction!getWorkFlowPage.do?ajax=yes")
	private String flow_id;

	
	@Override
	public void exeAction(AutoFact fact, List<RuleConsts> consts)
			throws RuntimeException {
		// TODO Auto-generated method stub
	}

	@Override
	public String getObj_id() {
		// TODO Auto-generated method stub
		return this.order_id;
	}

	@Override
	public String getTrace_flow_id() {
		return trace_flow_id;
	}

	public Map getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map paramMap) {
		this.paramMap = paramMap;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getGoods_type() {
		return goods_type;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	public String getShop_code() {
		return shop_code;
	}

	public void setShop_code(String shop_code) {
		this.shop_code = shop_code;
	}

	public String getGoods_cat_id() {
		return goods_cat_id;
	}

	public void setGoods_cat_id(String goods_cat_id) {
		this.goods_cat_id = goods_cat_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	
	

	public String getOper_mode() {
		return oper_mode;
	}

	public void setOper_mode(String oper_mode) {
		this.oper_mode = oper_mode;
	}
	
	public String getFlow_id() {
		return flow_id;
	}

	public void setFlow_id(String flow_id) {
		this.flow_id = flow_id;
	}


	public void toStringN(){
		logger.info("================================================================>");
		logger.info("zte.net.ecsord.rule.mode.PreRuleFact 日志信息打印:"+
																"订单编号："+this.getOrder_id()+
																"地市："+this.getRegion()+
																"商品类型:"+this.getGoods_type()+
																"订单来源:"+this.getShop_code()+
																"商品小类:"+this.getGoods_cat_id()+
																"商品编码:"+this.getGoods_id());
		logger.info("================================================================>");
	}
}

package zte.net.ecsord.rule.workflow;

import java.io.Serializable;
import java.util.List;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.net.annotation.DroolsFact;
import com.ztesoft.net.annotation.DroolsFactField;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.auto.rule.vo.RuleConsts;

@DroolsFact(name="工作流匹配",code="workFlowFact")
public class WorkFlowFact extends AutoFact implements Serializable {
	
	private String order_id;
//	private OrderTreeBusiRequest orderTreeBusiRequest;
	
	@DroolsFactField(name="是否开户", ele_type="checkbox", stype_code="DC_IS_OR_NO")
	private String need_open_act;
	
	@DroolsFactField(name="用户类型", ele_type="checkbox", stype_code="DC_NEW_USER_TYPE")
	private String user_flag;
	
	@DroolsFactField(name="商品类型", ele_type="checkbox", stype_code="DC_MODE_GOODS_TYPE")
	private String goods_type;
	
	@DroolsFactField(name="业务类型", ele_type="checkbox", stype_code="DC_BUSINESS_TYPE")
	private String busi_type;
	
	@DroolsFactField(name="网别", ele_type="checkbox", stype_code="DC_MODE_NET_TYPE")
	private String net_type;
	
	@DroolsFactField(name="流程", ele_type="list", stype_code="/shop/admin/workFlowAction!getWorkFlowPage.do?ajax=yes")
	private String flow_id;
	
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
		trace_flow_id = getOrderTree()
				.getOrderExtBusiRequest().getFlow_trace_id();
		return trace_flow_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getNeed_open_act() {
		need_open_act = CommonDataFactory.getInstance()
				.getAttrFieldValue(getOrderTree(),order_id, AttrConsts.IS_ACCOUNT);
		return need_open_act;
	}

	public void setNeed_open_act(String need_open_act) {
		this.need_open_act = need_open_act;
	}

	public String getUser_flag() {
		user_flag = CommonDataFactory.getInstance()
				.getAttrFieldValue(getOrderTree(),order_id, AttrConsts.IS_OLD);
		return user_flag;
	}

	public void setUser_flag(String user_flag) {
		this.user_flag = user_flag;
	}

	public String getBusi_type() {
		busi_type = CommonDataFactory.getInstance()
				.getAttrFieldValue(getOrderTree(),order_id, AttrConsts.SPECIAL_BUSI_TYPE);
		return busi_type;
	}

	public void setBusi_type(String busi_type) {
		this.busi_type = busi_type;
	}

	public String getGoods_type() {
		goods_type = CommonDataFactory.getInstance()
				.getAttrFieldValue(getOrderTree(),order_id, AttrConsts.GOODS_TYPE);
		return goods_type;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	public String getNet_type() {
		OrderTreeBusiRequest orderTreeBusiRequest = getOrderTree();
		net_type = CommonDataFactory.getInstance().getProductSpec(order_id, 
				SpecConsts.TYPE_ID_10002, "", SpecConsts.NET_TYPE);
		return net_type;
	}

	public void setNet_type(String net_type) {
		this.net_type = net_type;
	}

	public String getFlow_id() {
		return flow_id;
	}

	public void setFlow_id(String flow_id) {
		this.flow_id = flow_id;
	}
	
	public OrderTreeBusiRequest getOrderTree(){
		return CommonDataFactory.getInstance().getOrderTree(order_id);
	}
	public void toStringN(){
		logger.info("================================================================>");
		logger.info("zte.net.ecsord.rule.workflow.WorkFlowFact日志信息打印："+"订单编号："+this.getOrder_id()+"是否开户："+this.getNeed_open_act()+",用户类型:"+this.getUser_flag()
				+",商品类型"+this.getGoods_type()+",业务类型:"+this.getBusi_type()+",网别:"+this.getNet_type()+",流程:"+this.getFlow_id());
		logger.info("================================================================>");
	}
	
}

package zte.net.ecsord.rule.workflow;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.alibaba.fastjson.annotation.JSONField;
import com.ztesoft.net.annotation.DroolsFact;
import com.ztesoft.net.annotation.DroolsFactField;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.auto.rule.vo.RuleConsts;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
 
@DroolsFact(name="工作流匹配",code="workFlowFact")
public class WorkFlowFact extends AutoFact implements Serializable {
	private static Logger logger = Logger.getLogger(WorkFlowFact.class);
	private String order_id;
	private OrderTreeBusiRequest orderTreeBusiRequest;
	
	
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
	
	//zengxianlian
	@DroolsFactField(name="是否预约单", ele_type="checkbox", stype_code="DC_IS_OR_NO")
	private String wm_isreservation_from;
	
	@DroolsFactField(name="参与活动", ele_type="checkbox", stype_code="DIC_999_PROD_CAT_ID")
	private String prod_cat_id;
	
	@DroolsFactField(name="流程", ele_type="list", stype_code="/shop/admin/workFlowAction!getWorkFlowPage.do?ajax=yes")
	private String flow_id;
	
	@DroolsFactField(name="订单来源", ele_type="checkbox", stype_code="DC_MODE_SHOP_CODE")
	private String order_from;
	
	@DroolsFactField(name="商品小类", ele_type="checkbox", stype_code="DC_GOODS_CAT_ID")
	private String goods_cat_id;
	
	@DroolsFactField(name="支付类型", ele_type="checkbox", stype_code="DIC_PAY_TYPE")
	private String pay_type;
	
	@DroolsFactField(name = "产品订购类型", ele_type = "checkbox", stype_code = "DC_OPT_TYPE")
	private String optType;
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
				.getAttrFieldValue(order_id, AttrConsts.IS_ACCOUNT);
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
		goods_type =  getOrderTree().getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getGoods_type();
		return goods_type;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	public String getNet_type() {
		net_type = CommonDataFactory.getInstance().getProductSpec(order_id, 
				SpecConsts.TYPE_ID_10002, "", SpecConsts.NET_TYPE);
		return net_type;
	}

	public void setNet_type(String net_type) {
		this.net_type = net_type;
	}

	public String getProd_cat_id() {
		prod_cat_id = getOrderTree().getOrderExtBusiRequest().getProd_cat_id();
		return prod_cat_id;
	}

	public void setProd_cat_id(String prod_cat_id) {
		this.prod_cat_id = prod_cat_id;
	}

	public String getFlow_id() {
		return flow_id;
	}

	public void setFlow_id(String flow_id) {
		this.flow_id = flow_id;
	}
	 
	public String getOrder_from() {
		this.order_from = getOrderTree().getOrderExtBusiRequest().getOrder_from();
		return order_from;
	}

	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}
	
	public String getGoods_cat_id() {
		goods_cat_id = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.GOODS_CAT_ID);
		return goods_cat_id;
	}

	public void setGoods_cat_id(String goods_cat_id) {
		this.goods_cat_id = goods_cat_id;
	}

	public void toStringN(){
		logger.info("================================================================>");
		logger.info("zte.net.ecsord.rule.workflow.WorkFlowFact日志信息打印："+"订单编号："+this.order_id+"是否开户："+this.need_open_act+",用户类型:"+this.user_flag
				+",商品类型"+this.goods_type+",业务类型:"+this.busi_type+",网别:"+this.net_type+",流程:"+this.flow_id+",参与活动:"+this.prod_cat_id);
		logger.info("================================================================>");
	}

	public String getWm_isreservation_from() {
		wm_isreservation_from = getOrderTree().getOrderExtBusiRequest().getWm_isreservation_from();
		return wm_isreservation_from;
	}

	public void setWm_isreservation_from(String wm_isreservation_from) {
		this.wm_isreservation_from = wm_isreservation_from;
	}
	
	public String getPay_type(){
	    this.pay_type = getOrderTree().getOrderExtBusiRequest().getPay_type();
	    return this.pay_type;
	  }

	public void setPay_type(String pay_type) {
	    this.pay_type = pay_type;
	  }
	
	public String getOptType() {
	    IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
        String sql = "select t.opttype from  es_order_extvtl t  where t.source_from = '"
                + ManagerUtils.getSourceFrom() + "' and t.order_id = '" + order_id + "'";
        optType = baseDaoSupport.queryForString(sql);
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    @JSONField(deserialize=true,serialize=false)
	public OrderTreeBusiRequest getOrderTree(){
		if(orderTreeBusiRequest ==null)
			orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id);
		return orderTreeBusiRequest;
	}
	public void removeOrderTree(){
		orderTreeBusiRequest=null;
	}
}

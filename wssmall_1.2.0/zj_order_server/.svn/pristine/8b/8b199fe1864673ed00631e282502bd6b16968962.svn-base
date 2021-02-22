package com.ztesoft.net.ecsord.params.ecaop.req;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.BroadbandFeeVO;
import com.ztesoft.net.util.ZjCommonUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

public class FtthPreSubReq extends ZteRequest{
	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "对端操作点", type = "String", isNecessary = "Y", desc = "对端操作点")
	private String office_id;
	@ZteSoftCommentAnnotationParam(name = "对端操作员", type = "String", isNecessary = "Y", desc = "对端操作员")
	private String operator_id;
	@ZteSoftCommentAnnotationParam(name = "业务号码", type = "String", isNecessary = "N", desc = "业务号码")
	private String service_num;
	@ZteSoftCommentAnnotationParam(name = "业务类型", type = "String", isNecessary = "N", desc = "业务类型")
	private String service_type;
	@ZteSoftCommentAnnotationParam(name = "新业务类型", type = "String", isNecessary = "N", desc = "新业务类型")
	private String new_service_type;
	@ZteSoftCommentAnnotationParam(name = "速率", type = "String", isNecessary = "N", desc = "速率")
	private String speed_level;
	@ZteSoftCommentAnnotationParam(name = "费用清单", type = "String", isNecessary = "N", desc = "格式：科目1|应收费用|减免费用|实收费用|fee_rule_id;科目2|应收费用|减免费用|实收费用|fee_rule_id;……")
	private String fee_list;
	@ZteSoftCommentAnnotationParam(name = "光猫物品ID", type = "String", isNecessary = "N", desc = "光猫物品ID")
	private String model_id;
	@ZteSoftCommentAnnotationParam(name = "光猫物品状态", type = "String", isNecessary = "N", desc = "1:已领取;0未领取")
	private String object_status;
	@ZteSoftCommentAnnotationParam(name = "产品ID", type = "String", isNecessary = "N", desc = "产品ID")
	private String product_id;
	@ZteSoftCommentAnnotationParam(name = "是否收费", type = "String", isNecessary = "N", desc = "是否收费")
	private String is_pay;
	
	private OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);

	
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getOffice_id() {
		String source_from = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getOrder_from();
		String OUT_OFFICE = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OFFICE);
		if(!StringUtil.isEmpty(OUT_OFFICE)){
			office_id = OUT_OFFICE;
		}else{
			office_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
		}
		return office_id;
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	public String getOperator_id() {
		String source_from = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getOrder_from();
		String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
		if(!StringUtil.isEmpty(OUT_OPERATOR)){
			operator_id = OUT_OPERATOR;
		}else{
			operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
		}
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getService_num() {
		service_num = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getAdsl_number();
		return service_num;
	}

	public void setService_num(String service_num) {
		this.service_num = service_num;
	}

	public String getService_type() {
		service_type = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getOld_service_type();
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public String getNew_service_type() {
		/*String goods_id=CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getGoods_id();
		String service_type1 = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, goods_id, "service_type");
		new_service_type = service_type1;*/
		new_service_type="6115";
		return new_service_type;
	}

	public void setNew_service_type(String new_service_type) {
		this.new_service_type = new_service_type;
	}

	public String getSpeed_level() {
		String goods_id=CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getGoods_id();
		String bss_brand_speed = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, goods_id, "bss_brand_speed");
		speed_level = bss_brand_speed;
		return speed_level;
	}

	public void setSpeed_level(String speed_level) {
		this.speed_level = speed_level;
	}

	public String getFee_list() {
		List<BroadbandFeeVO> list = new ArrayList();
		String Fee_list  = "";
		List<AttrFeeInfoBusiRequest> feeInfoBusiRequests = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getFeeInfoBusiRequests();
		for(int i=0;i<feeInfoBusiRequests.size();i++){
			if(!StringUtils.isEmpty(feeInfoBusiRequests.get(i).getIs_aop_fee())
					&&StringUtil.equals("2", feeInfoBusiRequests.get(i).getIs_aop_fee())){
				
				Fee_list += feeInfoBusiRequests.get(i).getFee_item_code()+"|"+feeInfoBusiRequests.get(i).getO_fee_num()+"|"+feeInfoBusiRequests.get(i).getDiscount_fee()+"|"+feeInfoBusiRequests.get(i).getN_fee_num()+"|"+feeInfoBusiRequests.get(i).getFee_category()+";";
			}
		}
		if(!StringUtil.isEmpty(Fee_list)){
			Fee_list = Fee_list.substring(0, Fee_list.lastIndexOf(";"));
		}
		fee_list = Fee_list;
		return fee_list;
	}

	public void setFee_list(String fee_list) {
		this.fee_list = fee_list;
	}

	public String getModel_id() {
		model_id = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getModerm_id();
		return model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

	public String getObject_status() {
		String newobject_status = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getObject_status();
		if(!StringUtil.isEmpty(newobject_status)){
			object_status = newobject_status;
		}else{
			object_status = "0";
		}
		return object_status;
	}

	public void setObject_status(String object_status) {
		this.object_status = object_status;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public OrderTreeBusiRequest getOrderTree() {
		return orderTree;
	}

	public void setOrderTree(OrderTreeBusiRequest orderTree) {
		this.orderTree = orderTree;
	}

	public String getIs_pay() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "is_pay");
	}

	public void setIs_pay(String is_pay) {
		this.is_pay = is_pay;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.zj.broadband.ftthPreSubReq";
	}

}

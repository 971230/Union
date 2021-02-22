package zte.net.ecsord.params.bss.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.bss.resp.BSSAccountOfficialSubResp;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class BSSAccountOfficialSubReq extends ZteRequest<BSSAccountOfficialSubResp> {

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	private String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name = "预提交订单号", type = "String", isNecessary = "Y", desc = "订单号")
	private String serial_num;

	@ZteSoftCommentAnnotationParam(name = "订单类型（0提交 1取消）", type = "String", isNecessary = "Y", desc = "订单类型（0提交 1取消）")
	private String order_type;

	@ZteSoftCommentAnnotationParam(name = "办理操作员", type = "String", isNecessary = "Y", desc = "办理操作员")
	private String operator_id;

	@ZteSoftCommentAnnotationParam(name = "办理操作点", type = "String", isNecessary = "Y", desc = "办理操作点")
	private String office_id;

	
//	protected EmpOperInfoVo essOperInfo;
	public String getSerial_num() {
		serial_num = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ACTIVE_NO);
		return serial_num;
	}

	public void setSerial_num(String serial_num) {
		this.serial_num = serial_num;
	}

	public String getOrder_type() {
		this.order_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_TYPE);
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
 
	public String getOperator_id() {
		EmpOperInfoVo essOperInfo=null;
		if(essOperInfo==null){
			essOperInfo = CommonDataFactory.getEssInfoByOrderId(notNeedReqStrOrderId).getOperInfo();
			 operator_id = essOperInfo.getEss_emp_id();
		}
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getOffice_id() {
		office_id=CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OFFICE);
		return office_id;
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.bss.subAccountOfficial";
	}

}

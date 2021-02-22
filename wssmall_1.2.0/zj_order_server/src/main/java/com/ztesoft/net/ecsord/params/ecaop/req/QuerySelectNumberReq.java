package com.ztesoft.net.ecsord.params.ecaop.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.QRY_SELECTION_NUM_REQ;
import com.ztesoft.net.ecsord.params.ecaop.vo.UNIBSSBODYQRYVO;
import params.ZteRequest;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;

public class QuerySelectNumberReq extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "资源中心能力名", type = "String", isNecessary = "Y", desc = "资源中心服务名")
	private String api;
	@ZteSoftCommentAnnotationParam(name = "资源中心服务名", type = "String", isNecessary = "Y", desc = "资源中心服务名")
	private String service_name;
	@ZteSoftCommentAnnotationParam(name = "资源中心系统名", type = "String", isNecessary = "Y", desc = "资源中心系统名")
	private String system_id;
	@ZteSoftCommentAnnotationParam(name = "资源中心报文体", type = "String", isNecessary = "Y", desc = "资源中心报文体")
	private UNIBSSBODYQRYVO unibssbody;
	
	

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getApi() {
		return StringUtil.isEmpty(api)?"numberCenter":api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public String getService_name() {
		return StringUtil.isEmpty(service_name)?"qrySelectionNum":service_name;
	}

	public void setService_name(String service_name) {
		this.service_name = service_name;
	}

	public String getSystem_id() {
		return StringUtil.isEmpty(system_id)?"resourceCenter":system_id;
	}

	public void setSystem_id(String system_id) {
		this.system_id = system_id;
	}

	

	public UNIBSSBODYQRYVO getUnibssbody() {
		EmpOperInfoVo essOperInfo = CommonDataFactory.getEssInfoByOrderId(notNeedReqStrOrderId).getOperInfo();
		UNIBSSBODYQRYVO new_unibssbody = new UNIBSSBODYQRYVO();
		QRY_SELECTION_NUM_REQ new_SELECTEDNUMREQ= new QRY_SELECTION_NUM_REQ();
		String staff_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "cbss_out_operator");
		String order_city_code = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getOrder_city_code();
		String city_code = CommonDataFactory.getInstance().getOtherDictVodeValue("city", order_city_code);
		String district_code = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "district_code");
		String channel_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "cbss_out_office");
		if(StringUtil.isEmpty(staff_id)){
			staff_id = essOperInfo.getEss_emp_id();
			channel_id = essOperInfo.getChannel_id();
			city_code = essOperInfo.getCity();
			district_code = essOperInfo.getDistrict();
		}
		
		new_SELECTEDNUMREQ.setSTAFF_ID(staff_id);
		new_SELECTEDNUMREQ.setPROVINCE_CODE("36");
		new_SELECTEDNUMREQ.setCITY_CODE(city_code);
		new_SELECTEDNUMREQ.setDISTRICT_CODE(district_code);
		new_SELECTEDNUMREQ.setCHANNEL_ID(channel_id);
		new_SELECTEDNUMREQ.setSYS_CODE("3602");
		new_SELECTEDNUMREQ.setSERIAL_NUMBER(CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getPhone_num());

		new_unibssbody.setQRY_SELECTION_NUM_REQ(new_SELECTEDNUMREQ);
		unibssbody = new_unibssbody;
		return unibssbody;
	}

	public void setUnibssbody(UNIBSSBODYQRYVO unibssbody) {
		this.unibssbody = unibssbody;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.zj.broadband.queryselectnumber";
	}
}

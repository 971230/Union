package zte.net.ecsord.params.bss.req;

import params.ZteBusiRequest;
import params.ZteRequest;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.bss.resp.NumberResourceChangePreCaptureZjResponse;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class NumberResourceChangePreCaptureZjRequest  extends ZteBusiRequest {
	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "N", desc = "notNeedReqStrOrderId:订单系统内部订单")
	protected String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name = "预占序列号", type = "String", isNecessary = "Y", desc = "预占序列号，非空[20]")
	protected String seq_id;
	
	@ZteSoftCommentAnnotationParam(name = "预占渠道", type = "String", isNecessary = "N", desc = "预占渠道,可空[7]")
	protected String order_channel;
	
	@ZteSoftCommentAnnotationParam(name = "预占号码", type = "String", isNecessary = "Y", desc = "预占号码，非空[20]")
	protected String service_num;
	
	@ZteSoftCommentAnnotationParam(name = "号码归属省分编码", type = "String", isNecessary = "N", desc = "号码归属省分编码，可空[2]")
	protected String prov_code;
	
	@ZteSoftCommentAnnotationParam(name = "号码归属地市编码", type = "String", isNecessary = "N", desc = "号码归属地市编码，可空[10]")
	protected String city_code;
	
	@ZteSoftCommentAnnotationParam(name = "号码归属地市编码", type = "String", isNecessary = "N", desc = "号码归属县级编码，可空[20]")
	protected String county_code;
	
	@ZteSoftCommentAnnotationParam(name = "客户身份证号码", type = "String", isNecessary = "N", desc = "客户身份证号码，可空[30]")
	protected String cert_num;
	
	@ZteSoftCommentAnnotationParam(name = "操作点", type = "String", isNecessary = "Y", desc = "操作点，非空")
	protected String office_id;

	@ZteSoftCommentAnnotationParam(name = "操作员", type = "String", isNecessary = "Y", desc = "操作员，非空")
	protected String operator_id;
	
	@ZteSoftCommentAnnotationParam(name = "预留字段", type = "String", isNecessary = "N", desc = "预留字段，可空[256]")
	protected String param1;
	

	protected EmpOperInfoVo essOperInfo;
	
	public EmpOperInfoVo getEssOperInfo() {
		if(essOperInfo==null){
			essOperInfo = CommonDataFactory.getEssInfoByOrderId(notNeedReqStrOrderId).getOperInfo();
		}
		return essOperInfo;
	}
	
	public String getEssid(){
		
		String id =  getEssOperInfo().getEss_emp_id();
		return id;
	}
	
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}




	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}




	public String getSeq_id() {
		return seq_id;
	}




	public void setSeq_id(String seq_id) {
		this.seq_id = seq_id;
	}




	public String getOrder_channel() {
		return order_channel;
	}




	public void setOrder_channel(String order_channel) {
		this.order_channel = order_channel;
	}




	public String getService_num() {
		return service_num;
	}




	public void setService_num(String service_num) {
		this.service_num = service_num;
	}




	public String getProv_code() {
		return prov_code;
	}




	public void setProv_code(String prov_code) {
		this.prov_code = prov_code;
	}




	public String getCity_code() {
		return city_code;
	}




	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}




	public String getCounty_code() {
		return county_code;
	}




	public void setCounty_code(String county_code) {
		this.county_code = county_code;
	}




	public String getCert_num() {
		return cert_num;
	}




	public void setCert_num(String cert_num) {
		this.cert_num = cert_num;
	}




	public String getOffice_id() {
		return office_id;
	}




	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}




	public String getOperator_id() {
		return operator_id;
	}




	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}




	public String getParam1() {
		return param1;
	}




	public void setParam1(String param1) {
		this.param1 = param1;
	}


	public void setEssOperInfo(EmpOperInfoVo essOperInfo) {
		this.essOperInfo = essOperInfo;
	}


	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		
		return "com.zte.unicomService.bss.NumberResourceChangePreCaptureZj";
	}

	
}

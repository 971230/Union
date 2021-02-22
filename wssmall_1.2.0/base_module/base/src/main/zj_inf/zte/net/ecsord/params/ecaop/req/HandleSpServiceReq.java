package zte.net.ecsord.params.ecaop.req;

import java.text.SimpleDateFormat;
import java.util.Date;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class HandleSpServiceReq extends ZteRequest {
	private static final long serialVersionUID = 1L;
	
	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：内部订单号 ")
	private String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name="accessID",type="String",isNecessary="Y",desc="accessID")
	private String accessID;
	@ZteSoftCommentAnnotationParam(name="accessSEQ",type="String",isNecessary="Y",desc="accessSEQ")
	private String accessSEQ;
	@ZteSoftCommentAnnotationParam(name="businessCode",type="String",isNecessary="Y",desc="businessCode")
	private String businessCode;
	@ZteSoftCommentAnnotationParam(name="订购号码",type="String",isNecessary="Y",desc="订购号码")
	private String serial_number;
	@ZteSoftCommentAnnotationParam(name="标志",type="String",isNecessary="Y",desc="0：订购 C：取消")
	private String trade_tag;
	@ZteSoftCommentAnnotationParam(name="SP标识",type="String",isNecessary="Y",desc="SP标识")
	private String sp_id;
	@ZteSoftCommentAnnotationParam(name="增值业务产品标识",type="String",isNecessary="Y",desc="增值业务产品标识")
	private String sp_product_id;
	@ZteSoftCommentAnnotationParam(name="订购/退订时间",type="String",isNecessary="Y",desc="订购/退订时间")
	private String cancel_time;
	@ZteSoftCommentAnnotationParam(name="地市",type="String",isNecessary="Y",desc="地市")
	private String city_code ;
	@ZteSoftCommentAnnotationParam(name="保留字段",type="String",isNecessary="N",desc="保留字段")
	private String reserver1 ;
	@ZteSoftCommentAnnotationParam(name = "渠道ID ", type = "String", isNecessary = "Y", desc = "channel_id：渠道ID ")
	private String channel_id;
	@ZteSoftCommentAnnotationParam(name = "cb员工号 ", type = "String", isNecessary = "Y", desc = "oper_id：cb员工号 ")
	private String oper_id;
	private EmpOperInfoVo essOperInfo;
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getAccessID() {
		return accessID;
	}

	public void setAccessID(String accessID) {
		this.accessID = accessID;
	}

	public String getAccessSEQ() {
		return CommonDataFactory.getInstance().getAccessSEQ();
	}

	public void setAccessSEQ(String accessSEQ) {
		this.accessSEQ = accessSEQ;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getSerial_number() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
	}

	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}

	public String getTrade_tag() {
		return trade_tag;
	}

	public void setTrade_tag(String trade_tag) {
		this.trade_tag = trade_tag;
	}

	public String getSp_id() {
		return sp_id;
	}

	public void setSp_id(String sp_id) {
		this.sp_id = sp_id;
	}

	public String getSp_product_id() {
		return sp_product_id;
	}

	public void setSp_product_id(String sp_product_id) {
		this.sp_product_id = sp_product_id;
	}

	public String getCancel_time() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	public void setCancel_time(String cancel_time) {
		this.cancel_time = cancel_time;
	}

	public String getCity_code() {
		String city=CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CITY_CODE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("bss_area_code", city);
	}

	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}

	public String getReserver1() {
		return reserver1;
	}

	public void setReserver1(String reserver1) {
		this.reserver1 = reserver1;
	}
	
	public String getChannel_id() {
		return getEssOperInfo().getChannel_id();
	}

	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}

	public String getOper_id() {
		return getEssOperInfo().getEss_emp_id();
	}

	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}

	@Override
	public void check() throws ApiRuleException {

	}

	@Override
	public String getApiMethodName() {
		return "soa.zte.net.iservice.impl.handleSpService";
	}

	public EmpOperInfoVo getEssOperInfo() {
		if(essOperInfo==null){
			essOperInfo = CommonDataFactory.getEssInfoByOrderId(notNeedReqStrOrderId).getOperInfo();
		}
		return essOperInfo;
	}

	public void setEssOperInfo(EmpOperInfoVo essOperInfo) {
		this.essOperInfo = essOperInfo;
	}

}

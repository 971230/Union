package params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;
import params.ZteResponse;

public class ModifyOpenAccountGoodsReq extends ZteRequest<ZteResponse>{
	@ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="Y",desc="订单ID")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name="外部订单号",type="String",isNecessary="Y",desc="外部订单号")
	private String orderNo;
	@ZteSoftCommentAnnotationParam(name="证件号",type="String",isNecessary="Y",desc="证件号")
	private String idCard;//证件号
	@ZteSoftCommentAnnotationParam(name="证件类型 ",type="String",isNecessary="Y",desc="证件类型01:15位身份证,02:18位身份证  ")
	private String certType;
	@ZteSoftCommentAnnotationParam(name="证件名称",type="String",isNecessary="Y",desc="证件名称 :15位身份证;18位身份证")
	private String certName;
	@ZteSoftCommentAnnotationParam(name="证件地址 ",type="String",isNecessary="Y",desc="证件地址 ")
	private String certAddr;
	@ZteSoftCommentAnnotationParam(name="机主",type="String",isNecessary="Y",desc="机主")
	private String custName;
	@ZteSoftCommentAnnotationParam(name="推荐人",type="String",isNecessary="Y",desc="推荐人")
	private String referrerName;
	@ZteSoftCommentAnnotationParam(name="推荐人号码",type="String",isNecessary="Y",desc="推荐人号码")
	private String referrerPhone;
	@ZteSoftCommentAnnotationParam(name="发展人名称",type="String",isNecessary="Y",desc="发展人名称")
	private String developmentName;
	@ZteSoftCommentAnnotationParam(name="发展人编号",type="String",isNecessary="Y",desc="发展人编号")
	private String developmentCode;
	@ZteSoftCommentAnnotationParam(name="发展人部门",type="String",isNecessary="Y",desc="发展人部门")
	private String developmentDepartId;
	@ZteSoftCommentAnnotationParam(name="发展人渠道名称",type="String",isNecessary="Y",desc="发展人渠道名称")
	private String channelName;
	@ZteSoftCommentAnnotationParam(name="发展人手机号码",type="String",isNecessary="Y",desc="发展人手机号码")
	private String recommendNumber;
	@ZteSoftCommentAnnotationParam(name="是否手动开户",type="String",isNecessary="N",desc="是否手动开户 1：是  |（0或者不填）：否")
	private String isManual;
	
	
	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.iservice.impl.ZteCrawlerOpenService.modifyOpenAccountGoods";
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertName() {
		return certName;
	}

	public void setCertName(String certName) {
		this.certName = certName;
	}

	public String getCertAddr() {
		return certAddr;
	}

	public void setCertAddr(String certAddr) {
		this.certAddr = certAddr;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getReferrerName() {
		return referrerName;
	}

	public void setReferrerName(String referrerName) {
		this.referrerName = referrerName;
	}

	public String getReferrerPhone() {
		return referrerPhone;
	}

	public void setReferrerPhone(String referrerPhone) {
		this.referrerPhone = referrerPhone;
	}

	public String getDevelopmentName() {
		return developmentName;
	}

	public void setDevelopmentName(String developmentName) {
		this.developmentName = developmentName;
	}

	public String getDevelopmentCode() {
		return developmentCode;
	}

	public void setDevelopmentCode(String developmentCode) {
		this.developmentCode = developmentCode;
	}

	public String getDevelopmentDepartId() {
		return developmentDepartId;
	}

	public void setDevelopmentDepartId(String developmentDepartId) {
		this.developmentDepartId = developmentDepartId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getRecommendNumber() {
		return recommendNumber;
	}

	public void setRecommendNumber(String recommendNumber) {
		this.recommendNumber = recommendNumber;
	}

	public String getIsManual() {
		return isManual;
	}

	public void setIsManual(String isManual) {
		this.isManual = isManual;
	}
	
}

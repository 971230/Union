package params.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

public class CrawlerUpdatePostInfoReq extends ZteRequest{

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.iservice.impl.ZteCrawlerOpenService.updatePostInfo";
	}
	
	private String orderNo;//总商订单编号
	
	private String receiverName;//姓名

	private String mobilePhone;//电话
	
	private String deliverTypeCode;//配送方式编码 01:第三方物流 04：现场提货 05：自行配送
	
	private String dispatchName;//配送方式名称：快递
	
	private String proviceCode;//省份编码 330000：浙江
	
	private String cityCode;//地市编码
	
	private String districtCode;//区县编码

	private String wishLgtsCode;//物流公司编码 1001：邮政EMS，1002：顺丰速运
	
	private String postAddr;//详细地址
	
	private String deliverDateType;//送货日期  04：延时配送，01： 不限时配送，02：只工作日送货，03只有双休日、节假日送货

	private String fixDelvTime;//送货时间，送货日期为延时配送deliverDateType = 04时传

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getDeliverTypeCode() {
		return deliverTypeCode;
	}

	public void setDeliverTypeCode(String deliverTypeCode) {
		this.deliverTypeCode = deliverTypeCode;
	}

	public String getDispatchName() {
		return dispatchName;
	}

	public void setDispatchName(String dispatchName) {
		this.dispatchName = dispatchName;
	}

	public String getProviceCode() {
		return proviceCode;
	}

	public void setProviceCode(String proviceCode) {
		this.proviceCode = proviceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getWishLgtsCode() {
		return wishLgtsCode;
	}

	public void setWishLgtsCode(String wishLgtsCode) {
		this.wishLgtsCode = wishLgtsCode;
	}

	public String getPostAddr() {
		return postAddr;
	}

	public void setPostAddr(String postAddr) {
		this.postAddr = postAddr;
	}

	public String getDeliverDateType() {
		return deliverDateType;
	}

	public void setDeliverDateType(String deliverDateType) {
		this.deliverDateType = deliverDateType;
	}

	public String getFixDelvTime() {
		return fixDelvTime;
	}

	public void setFixDelvTime(String fixDelvTime) {
		this.fixDelvTime = fixDelvTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

/*	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}*/

	
}
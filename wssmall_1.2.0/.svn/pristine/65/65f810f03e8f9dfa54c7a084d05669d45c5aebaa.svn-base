package zte.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;


/**
 * 
 * @author sguo 
 * 验证身份证信息是否正确
 * 
 */
public class CheckIDECAOPRequset extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="ActiveNo：访问流水")
	private String ActiveNo;

	@ZteSoftCommentAnnotationParam(name="省份编码",type="String",isNecessary="Y",desc="province：取值范围在省份编码范围内；固定为：51")
	private String province;

	@ZteSoftCommentAnnotationParam(name="地市编码",type="String",isNecessary="Y",desc="city：取值范围在省份编码范围内；固定为：51")
	private String city;

	@ZteSoftCommentAnnotationParam(name="身份证编码",type="String",isNecessary="Y",desc="certId：长度15或者18位，满足身份证号码格式要求")
	private String certId;

	@ZteSoftCommentAnnotationParam(name="身份证姓名",type="String",isNecessary="Y",desc="certName：身份证姓名")
	private String certName;
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="OrderId：订单编号")
	private String OrderId;
	
	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public String getActiveNo() {
		return CommonDataFactory.getInstance().getActiveNo(AttrConsts.ACTIVE_NO_ON_OFF);
	}

	public void setActiveNo(String activeNo) {
		ActiveNo = activeNo;
	}

	public String getProvince() {
		return "51";
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return "510";
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCertId() {
		return CommonDataFactory.getInstance().getAttrFieldValue(OrderId,AttrConsts.CERT_CARD_NUM);
	}

	public void setCertId(String certId) {
		this.certId = certId;
	}

	public String getCertName() {
		return CommonDataFactory.getInstance().getAttrFieldValue(OrderId,AttrConsts.PHONE_OWNER_NAME);

	}

	public void setCertName(String certName) {
		this.certName = certName;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.ecaop.checkID";
	}

}

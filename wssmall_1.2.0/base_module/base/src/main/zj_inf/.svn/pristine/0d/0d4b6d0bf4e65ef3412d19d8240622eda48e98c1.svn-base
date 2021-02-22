package zte.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 
 * @author sguo 验证身份证信息是否正确
 * 
 */
public class CheckIDECAOPRequset extends ZteRequest {

	//@ZteSoftCommentAnnotationParam(name = "访问流水", type = "String", isNecessary = "Y", desc = "ActiveNo：访问流水")
	//private String ActiveNo;

	@ZteSoftCommentAnnotationParam(name = "省份编码", type = "String", isNecessary = "Y", desc = "province：取值范围在省份编码范围内；固定为：36")
	private String province;

	@ZteSoftCommentAnnotationParam(name = "地市编码", type = "String", isNecessary = "Y", desc = "city：取值范围在省份编码范围内；固定为：360")
	private String city;

	@ZteSoftCommentAnnotationParam(name = "身份证编码", type = "String", isNecessary = "Y", desc = "certId：长度15或者18位，满足身份证号码格式要求")
	private String certId;

	@ZteSoftCommentAnnotationParam(name = "身份证姓名", type = "String", isNecessary = "Y", desc = "certName：身份证姓名")
	private String certName;
	
	@ZteSoftCommentAnnotationParam(name = "认证类型", type = "String", isNecessary = "Y", desc = "certType：01 组合认证,02 公安认证")
	private String certType;

	@ZteSoftCommentAnnotationParam(name = "订单编号", type = "String", isNecessary = "Y", desc = "OrderId：订单编号")
	private String notNeedReqStrOrderId;

	

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	/*public String getActiveNo() {
		return CommonDataFactory.getInstance().getActiveNo(
				AttrConsts.ACTIVE_NO_ON_OFF);
	}*/

	
	public String getProvince() {
		return "36";
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return "360";
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCertId() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.CERT_CARD_NUM);
	}

	public void setCertId(String certId) {
		this.certId = certId;
	}

	public String getCertName() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.PHONE_OWNER_NAME);

	}

	public void setCertName(String certName) {
		this.certName = certName;
	}

	public String getCertType() {
		//certType="01";
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
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

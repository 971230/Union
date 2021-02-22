/**
 * 
 */
package zte.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * @author XMJ
 * @version 2015-06-23
 * @see 2-3G转4G
 * 
 */
@SuppressWarnings("rawtypes")
public class Check23to4Request extends ZteRequest {
	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	private String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name = "操作员ID", type = "String", isNecessary = "Y", desc = "operatorId：操作员ID")
	private String operatorId;

	@ZteSoftCommentAnnotationParam(name = "省份编码，取值范围在省份编码范围内", type = "String", isNecessary = "Y", desc = "province：省份编码，取值范围在省份编码范围内")
	private String province;

	@ZteSoftCommentAnnotationParam(name = "地市编码", type = "String", isNecessary = "Y", desc = "city：地市编码")
	private String city;
	
	@ZteSoftCommentAnnotationParam(name = "区县", type = "String", isNecessary = "N", desc = "district：区县")
	private String district;

	@ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "Y", desc = "channelId：渠道编码")
	private String channelId;

	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "Y", desc = "channelType：渠道类型")
	private String channelType;
	
	@ZteSoftCommentAnnotationParam(name = "网别", type = "String", isNecessary = "N", desc = "serClassCode：网别")
	private String serClassCode;
	
	@ZteSoftCommentAnnotationParam(name = "服务号码", type = "String", isNecessary = "Y", desc = "numId：服务号码")
	private String numId;
	
	@ZteSoftCommentAnnotationParam(name = "用户密码", type = "String", isNecessary = "Y", desc = "userPasswd：用户密码")
	private String userPasswd;

	private EmpOperInfoVo essOperInfo;
	
	public String getNotNeedReqStrOrderId() {		
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getOperatorId() {
		return getEssOperInfo().getEss_emp_id();
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getProvince() {
		return getEssOperInfo().getProvince();
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return getEssOperInfo().getCity();
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return getEssOperInfo().getDistrict();
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getChannelId() {
		return getEssOperInfo().getChannel_id();
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelType() {
		return getEssOperInfo().getChannel_type();
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getSerClassCode() {
		serClassCode ="0010";
		return serClassCode;
	}

	public void setSerClassCode(String serClassCode) {
		this.serClassCode = serClassCode;
	}

	public String getNumId() {
		numId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
		return numId;
	}

	public void setNumId(String numId) {
		this.numId = numId;
	}

	public String getUserPasswd() {
		return userPasswd;
	}

	public void setUserPasswd(String userPasswd) {
		this.userPasswd = userPasswd;
	}

	@NotDbField
	public void check() throws ApiRuleException {
	}

	@NotDbField
	public String getApiMethodName() {
		return "ecaop.trades.query.comm.23to4.check";
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

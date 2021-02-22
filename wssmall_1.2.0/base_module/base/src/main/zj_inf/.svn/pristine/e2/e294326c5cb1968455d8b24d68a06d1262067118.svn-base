/**
 * 
 */
package zte.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * @author ZX
 * @version 2015-05-04
 * @see 发展人查询
 * 
 */
@SuppressWarnings("all")
public class DevelopPersonQueryReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	private String notNeedReqStrOrderId;

	@ZteSoftCommentAnnotationParam(name = "省份编码，取值范围在省份编码范围内", type = "String", isNecessary = "Y", desc = "province：省份编码，取值范围在省份编码范围内")
	private String province;

	@ZteSoftCommentAnnotationParam(name = "地市编码", type = "String", isNecessary = "Y", desc = "city：地市编码")
	private String city;

	@ZteSoftCommentAnnotationParam(name = "发展人渠道名称", type = "String", isNecessary = "N", desc = "district：发展人渠道名称")
	private String chnlName;

	@ZteSoftCommentAnnotationParam(name = "发展人渠道编码", type = "String", isNecessary = "N", desc = "channelId：发展人渠道编码")
	private String channelId;

	@ZteSoftCommentAnnotationParam(name = "发展人Id", type = "String", isNecessary = "N", desc = "developerId：发展人Id")
	private String developerId;

	@ZteSoftCommentAnnotationParam(name = "发展人名称", type = "String", isNecessary = "N", desc = "developerName：发展人名称")
	private String developerName;

	@ZteSoftCommentAnnotationParam(name = "发展人手机号码", type = "String", isNecessary = "N", desc = "developerNumber：发展人手机号码")
	private String developerNumber;
		
	public String getNotNeedReqStrOrderId() {		
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getProvince() {
		province = EcsOrderConsts.AOP_PROVICE_36;
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		String code = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CITY_CODE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("city", code);
	}
	
	public void setCity(String city) {
		this.city = city;
	}

	public String getChnlName() {
		return chnlName;
	}

	public void setChnlName(String chnlName) {
		this.chnlName = chnlName;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getDeveloperId() {
		//developerId= CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.DEVELOPMENT_CODE);
	    developerId= CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "develop_code_new");
		 // developerId="3600819549";
		return developerId;
	}

	public void setDeveloperId(String developerId) {
		this.developerId = developerId;
	}

	public String getDeveloperName() {//这个接口只能传发展人id和地市，其他条件都不能传，哪怕数据库有正确的发展人名称也不行
		return developerName;
	}

	public void setDeveloperName(String developerName) {
		this.developerName = developerName;
	}

	
	public String getDeveloperNumber() {
		return developerNumber;
	}

	public void setDeveloperNumber(String developerNumber) {
		this.developerNumber = developerNumber;
	}

	@NotDbField
	public void check() throws ApiRuleException {
	}

	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.zb.devrPersonQuery";
	}

}

package com.ztesoft.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class QryFtthObjIdReq  extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name="订单中心订单ID",type="String",isNecessary="Y",desc="order_id：订单中心订单ID")
	private String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name="物品信息",type="String",isNecessary="Y",desc="extra_info：物品信息")
	private String extra_info;
	
	@ZteSoftCommentAnnotationParam(name="地市编码",type="String",isNecessary="Y",desc="region_id：地市编码")
	private String region_id;
	
	@ZteSoftCommentAnnotationParam(name="设备类型",type="String",isNecessary="Y",desc="voiceorline：设备类型 1:语音2:宽带3:融合")
	private String voiceorline;
	
	@ZteSoftCommentAnnotationParam(name="业务类型",type="String",isNecessary="Y",desc="svc_type：业务类型")
	private String svc_type;
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}
	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	public String getExtra_info() {
		return extra_info;
	}
	public void setExtra_info(String extra_info) {
		this.extra_info = extra_info;
	}
	public String getRegion_id() {
		return region_id;
	}
	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}
	public String getVoiceorline() {
		String type_id = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, null,"type_id");
		if(type_id != null) {
			if(EcsOrderConsts.GOODS_TYPE_DKD.equals(type_id)) {
				voiceorline = "2";
			}
			else if(EcsOrderConsts.GOODS_TYPE_RONGHE.equals(type_id)) {
				voiceorline = "3";
			}else {
				voiceorline = "1";
			}
		}
		return voiceorline;
	}
	public void setVoiceorline(String voiceorline) {
		this.voiceorline = voiceorline;
	}
	public String getSvc_type() {
		svc_type = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, null, AttrConsts.ORDER_SERVICE_TYPE);
		return svc_type;
	}
	public void setSvc_type(String svc_type) {
		this.svc_type = svc_type;
	}
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZJInfServices.qryFtthObjID";
	}

}
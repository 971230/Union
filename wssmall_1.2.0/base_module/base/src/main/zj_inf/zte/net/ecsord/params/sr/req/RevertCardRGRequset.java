package zte.net.ecsord.params.sr.req;

import java.util.Date;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.InfConst;
import zte.net.ecsord.params.sr.vo.RecycleCardDataRG;

import com.alibaba.fastjson.JSON;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class RevertCardRGRequset extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name = "该数据在自动开户系统中配置生成，作为合法校验的依据，所提供的KEY必需和开户系统中配置的KEY匹配", type = "String", isNecessary = "Y", desc = "KEY：该数据在自动开户系统中配置生成，作为合法校验的依据，所提供的KEY必需和开户系统中配置的KEY匹配")
	private String key;

	@ZteSoftCommentAnnotationParam(name = "发起开户请求的浏览器的SESSION_ID值，用于远程写卡的匹配", type = "String", isNecessary = "N", desc = "sessionId：发起开户请求的浏览器的SESSION_ID值，用于远程写卡的匹配")
	private String sessionId;

	@ZteSoftCommentAnnotationParam(name = "请求类型", type = "String", isNecessary = "Y", desc = "recycleCard_回收卡；outCard_退卡")
	private String request;

	@ZteSoftCommentAnnotationParam(name = "请求ID", type = "String", isNecessary = "Y", desc = "sequence：请求ID")
	private String sequence;

	@ZteSoftCommentAnnotationParam(name = "请求协议", type = "String", isNecessary = "N", desc = "data：请求协议")
	private String data;

	@ZteSoftCommentAnnotationParam(name = "请求协议", type = "String", isNecessary = "N", desc = "data：请求协议")
	private RecycleCardDataRG notNeedReqStrData;

	@ZteSoftCommentAnnotationParam(name = "订单编号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：订单编号")
	private String notNeedReqStrOrderId;

	public String getKEY() {
		key = InfConst.SR_WRITECARD_KEY;
		return key;
	}

	public void setKEY(String key) {
		key = key;
	}

	public String getSessionId() {
		sessionId = "";
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getSequence() {
		return (new Date().getTime() + "");
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getData() {
		if (null == this.notNeedReqStrData) {
			this.getNotNeedReqStrData();
		}
		return JSON.toJSONString(this.notNeedReqStrData);
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public RecycleCardDataRG getNotNeedReqStrData() {
		if (null == this.notNeedReqStrData) {
			this.notNeedReqStrData = new RecycleCardDataRG();
			this.notNeedReqStrData.setMachineNo(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.READID));
			this.notNeedReqStrData.setOrderId(getNotNeedReqStrOrderId());
			this.notNeedReqStrData.setIccid(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ICCID));
		}
		return this.notNeedReqStrData;
	}

	public void setNotNeedReqStrData(RecycleCardDataRG notNeedReqStrData) {
		this.notNeedReqStrData = notNeedReqStrData;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.sr.rgrevertCard";
	}

}

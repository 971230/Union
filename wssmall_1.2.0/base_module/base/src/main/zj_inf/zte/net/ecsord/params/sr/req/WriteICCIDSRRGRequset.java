package zte.net.ecsord.params.sr.req;

import java.util.Date;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.InfConst;
import zte.net.ecsord.params.sr.vo.WriteCardDataRG;

import com.alibaba.fastjson.JSON;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 
 * @author sguo 订单系统信息传入写卡系统，将信息写入卡中
 * 2016-11-18 新加人工批量写卡模式，接口地址不同，拷贝接口，以防未来两套接口变更，可以分开维护
 * 
 */
public class WriteICCIDSRRGRequset extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name = "该数据在自动开户系统中配置生成，作为合法校验的依据，所提供的KEY必需和开户系统中配置的KEY匹配", type = "String", isNecessary = "Y", desc = "KEY：该数据在自动开户系统中配置生成，作为合法校验的依据，所提供的KEY必需和开户系统中配置的KEY匹配")
	private String key;

	@ZteSoftCommentAnnotationParam(name = "发起开户请求的浏览器的SESSION_ID值，用于远程写卡的匹配", type = "String", isNecessary = "N", desc = "sessionId：发起开户请求的浏览器的SESSION_ID值，用于远程写卡的匹配")
	private String sessionId;

	@ZteSoftCommentAnnotationParam(name = "请求类型", type = "String", isNecessary = "Y", desc = "request：请求类型")
	private String request;

	@ZteSoftCommentAnnotationParam(name = "请求ID", type = "String", isNecessary = "Y", desc = "sequence：请求ID")
	private String sequence;

	@ZteSoftCommentAnnotationParam(name = "请求协议", type = "String", isNecessary = "N", desc = "data：请求协议")
	private String data;

	@ZteSoftCommentAnnotationParam(name = "请求协议", type = "String", isNecessary = "N", desc = "data：请求协议")
	private WriteCardDataRG notNeedReqStrData;

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
		return InfConst.SR_WRITECARD_DATA;
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

	public WriteCardDataRG getNotNeedReqStrData() {
		if (null == this.notNeedReqStrData) {
			this.notNeedReqStrData = new WriteCardDataRG();
			// 用户号码
			this.notNeedReqStrData.setNumber(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM));
			// 写卡脚本
			this.notNeedReqStrData.setOption(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SCRIPT_SEQ));
			// 白卡卡号
			this.notNeedReqStrData.setSimCardNo(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ICCID));
			// machineNo:写卡机编号
			this.notNeedReqStrData.setMachineNo(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.READID));
			// imsi号
			this.notNeedReqStrData.setImsi(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SIMID));
			// 内部订单号
			this.notNeedReqStrData.setOrderId(getNotNeedReqStrOrderId());
		}
		return this.notNeedReqStrData;
	}

	public void setNotNeedReqStrData(WriteCardDataRG notNeedReqStrData) {
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
		return "com.zte.unicomService.sr.rgwriteICCID";
	}

}

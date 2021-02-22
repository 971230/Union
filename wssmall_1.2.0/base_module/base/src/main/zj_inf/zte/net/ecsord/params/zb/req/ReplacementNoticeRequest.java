package zte.net.ecsord.params.zb.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.zb.vo.Par;

public class ReplacementNoticeRequest extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name = "访问流水", type = "String", isNecessary = "Y", desc = "ActiveNo：访问流水")
	private String ActiveNo;

	@ZteSoftCommentAnnotationParam(name = "最新终端串号", type = "String", isNecessary = "N", desc = "NewResourcesCode：最新终端串号")
	private String NewResourcesCode;

	@ZteSoftCommentAnnotationParam(name = "被换终端串号", type = "String", isNecessary = "N", desc = "OldResourcesCode：被换终端串号")
	private String OldResourcesCode;

	@ZteSoftCommentAnnotationParam(name = "物流单号", type = "String", isNecessary = "N", desc = "ExpNo：物流单号")
	private String ExpNo;

	@ZteSoftCommentAnnotationParam(name = "变更时间", type = "String", isNecessary = "Y", desc = "StateChgTime：变更时间")
	private String StateChgTime;

	@ZteSoftCommentAnnotationParam(name = "变更描述", type = "String", isNecessary = "N", desc = "StateChgDesc：变更描述")
	private String StateChgDesc;
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

	public String getNewResourcesCode() {
		return CommonDataFactory.getInstance().getAttrFieldValue(OrderId,
				AttrConsts.TERMINAL_NUM);
	}

	public void setNewResourcesCode(String newResourcesCode) {
		NewResourcesCode = newResourcesCode;
	}

	public String getOldResourcesCode() {
		return CommonDataFactory.getInstance().getAttrFieldValue(OrderId,
				AttrConsts.TERMINAL_NUM);
	}

	public void setOldResourcesCode(String oldResourcesCode) {
		OldResourcesCode = oldResourcesCode;
	}

	public String getExpNo() {

		return CommonDataFactory.getInstance().getAttrFieldValue(OrderId, AttrConsts.LOGI_NO);

	}

	public void setExpNo(String expNo) {
		ExpNo = expNo;
	}

	public String getStateChgTime() {
		return StateChgTime;
	}

	public void setStateChgTime(String stateChgTime) {
		StateChgTime = stateChgTime;
	}

	public String getStateChgDesc() {
		return StateChgDesc;
	}

	public void setStateChgDesc(String stateChgDesc) {
		StateChgDesc = stateChgDesc;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.zte.unicomService.zb.ReplacementNotice";
	}

}

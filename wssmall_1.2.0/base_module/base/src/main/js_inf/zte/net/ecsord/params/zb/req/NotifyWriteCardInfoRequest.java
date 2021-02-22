package zte.net.ecsord.params.zb.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;


public class NotifyWriteCardInfoRequest extends ZteRequest{
	
	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="ActiveNo：访问流水")
	private String ActiveNo;
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="OrderId：订单编号")
	private String OrderId;
	
	@ZteSoftCommentAnnotationParam(name="大卡卡号",type="String",isNecessary="Y",desc="ICCID：大卡卡号")
	private String ICCID;
	
	@ZteSoftCommentAnnotationParam(name="0：写卡成功,非0则由读卡器返回的错误代码",type="String",isNecessary="Y",desc="ReasonID：0：写卡成功,非0则由读卡器返回的错误代码")
	private String ReasonID;
	
	@ZteSoftCommentAnnotationParam(name="错误说明",type="String",isNecessary="Y",desc="ErrorComments：错误说明")
	private String ErrorComments;
	
	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：内部订单号 ")
	private String notNeedReqStrOrderId;
	

	public String getActiveNo() {
		boolean isZbOrder = false;
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM);
		if(EcsOrderConsts.ORDER_FROM_10003.equals(order_from)){
			isZbOrder = true;
		}
		return CommonDataFactory.getInstance().getActiveNo(isZbOrder);
	}

	public void setActiveNo(String activeNo) {
		ActiveNo = activeNo;
	}

	public String getOrderId() {
		String out_tid = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID);
		return out_tid;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public String getICCID() {
		ICCID = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ICCID);
		return ICCID==null?"":ICCID;
	}

	public void setICCID(String iCCID) {
		ICCID = iCCID;
	}

	public String getReasonID() {
		//写卡结果类型，0：成功，-1：失败
		ReasonID = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getWrite_card_result();
		return ReasonID==null?"":ReasonID;
	}

	public void setReasonID(String reasonID) {
		ReasonID = reasonID;
	}

	public String getErrorComments() {
		ErrorComments = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ERRORCOMMENTS);
		if(StringUtil.isEmpty(ErrorComments)){
			if(EcsOrderConsts.WRITE_CARD_RESULT_SUCC.equals(this.getReasonID())){
				ErrorComments = "写卡成功";
			}else {
				ErrorComments = "写卡失败";
			}
		}
		return ErrorComments;
	}

	public void setErrorComments(String errorComments) {
		ErrorComments = errorComments;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
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
		return "com.zte.unicomService.zb.NotifyWriteCardInfo";
	}

}

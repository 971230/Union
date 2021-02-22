package zte.net.ecsord.params.zb.req;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.zb.vo.statesys.Fetch;
import zte.net.ecsord.params.zb.vo.statesys.Order;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class StateSynToZBRequest extends ZteRequest{
	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="ActiveNo：访问流水")
	private String ActiveNo;
	
	@ZteSoftCommentAnnotationParam(name="状态标记",type="String",isNecessary="Y",desc="StateTag：状态标记，业务组件传入")
	private String notNeedReqStrStateTag;
	
	@ZteSoftCommentAnnotationParam(name="变更原因",type="String",isNecessary="Y",desc="StateChgReason：变更原因")
	private String notNeedReqStrStateChgReason;
	
	@ZteSoftCommentAnnotationParam(name="变更描述",type="String",isNecessary="Y",desc="StateChgDesc：变更描述")
	private String notNeedReqStrStateChgDesc;

	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="ActiveNo：访问流水")
	private List<Order> Orders;	


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

	public List<Order> getOrders() {
		Orders = new ArrayList<Order>();
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//状态标记【订单状态】StateTag业务组件传入
		Order order = new Order();
		order.setOrderId(CommonDataFactory.getInstance()
				.getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID));
		order.setStateChgTime(dtf.format(new Date()));
		order.setStateTag(this.notNeedReqStrStateTag);
		order.setStateChgReason(this.notNeedReqStrStateChgReason == null ? EcsOrderConsts.STATE_CHG_REASON_OTHEOR : this.notNeedReqStrStateChgReason );
		order.setStateChgDesc(this.notNeedReqStrStateChgDesc);
		order.setFetchInfoList(new ArrayList<Fetch>());
		Orders.add(order);
		return Orders;
	}

	public void setOrders(List<Order> orders) {
		Orders = orders;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	
	public String getNotNeedReqStrStateTag() {
		return notNeedReqStrStateTag;
	}

	public void setNotNeedReqStrStateTag(String notNeedReqStrStateTag) {
		this.notNeedReqStrStateTag = notNeedReqStrStateTag;
	}

	public String getNotNeedReqStrStateChgReason() {
		return notNeedReqStrStateChgReason;
	}

	public void setNotNeedReqStrStateChgReason(String notNeedReqStrStateChgReason) {
		this.notNeedReqStrStateChgReason = notNeedReqStrStateChgReason;
	}

	public String getNotNeedReqStrStateChgDesc() {
		return notNeedReqStrStateChgDesc;
	}

	public void setNotNeedReqStrStateChgDesc(String notNeedReqStrStateChgDesc) {
		this.notNeedReqStrStateChgDesc = notNeedReqStrStateChgDesc;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.zb.stateSynToZBRequest";
	}

	
}

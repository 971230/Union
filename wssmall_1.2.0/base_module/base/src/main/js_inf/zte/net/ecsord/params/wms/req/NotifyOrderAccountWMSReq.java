package zte.net.ecsord.params.wms.req;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.InfConst;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.wms.resp.NotifyOrderAccountWMSResp;
import zte.net.ecsord.params.wms.vo.NotifyAccountGoodInfoVo;
import zte.net.ecsord.params.wms.vo.NotifyAccountOrderInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * 订单业务完成状态通知（接收方WMS）
 * 入参对象
 */
public class NotifyOrderAccountWMSReq extends ZteRequest<NotifyOrderAccountWMSResp>{
	
	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="访问流水")
	private String activeNo;
	@ZteSoftCommentAnnotationParam(name="接入Id",type="String",isNecessary="Y",desc="接入Id(由WMS提供)")
	private String reqId;
	@ZteSoftCommentAnnotationParam(name="请求类型",type="String",isNecessary="Y",desc="请求类型,固定值：at_syn_account")
	private String reqType;
	@ZteSoftCommentAnnotationParam(name="请求时间",type="String",isNecessary="Y",desc="请求时间，格式：yyyy-mm-dd HH:mi:ss")
	private String reqTime;
	@ZteSoftCommentAnnotationParam(name="订单信息",type="List<NotifyAccountOrderInfoVo>",isNecessary="Y",desc="订单信息")
	private NotifyAccountOrderInfoVo orderInfo;
	@ZteSoftCommentAnnotationParam(name="内部订单Id",type="String",isNecessary="Y",desc="内部订单Id")
	private String orderId;
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		
		return "com.zte.unicomService.wms.notifyOrderAccountWMS";
	}

	public String getActiveNo() {
		return CommonDataFactory.getInstance().getActiveNo(AttrConsts.ACTIVE_NO_ON_OFF);
	}

	public void setActiveNo(String activeNo) {
		this.activeNo = activeNo;
	}

	public String getReqId() {
		reqId = "";
		return "";
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getReqType() {
		return InfConst.WMS_AT_SYN_ACCOUNT;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getReqTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return dateFormat.format(new Date());
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public NotifyAccountOrderInfoVo getOrderInfo() {
		NotifyAccountOrderInfoVo notifyAccountOrderInfoVo=new NotifyAccountOrderInfoVo();
		notifyAccountOrderInfoVo.setOrderId(orderId);
		List<NotifyAccountGoodInfoVo> notifyAccountGoodInfoVos=new ArrayList<NotifyAccountGoodInfoVo>();
		
		//取实物货品
		List<OrderItemBusiRequest> items = CommonDataFactory.getInstance().getOrderTree(orderId).getOrderItemBusiRequests();
		OrderItemBusiRequest item = items.get(0);
		List<OrderItemProdBusiRequest> prodBusis = item.getOrderItemProdBusiRequests();
		if(prodBusis != null && prodBusis.size() > 0){
			for(OrderItemProdBusiRequest prodBusi : prodBusis){
				if(EcsOrderConsts.PRODUCT_TYPE_CODE_OFFER.equals(prodBusi.getProd_spec_type_code())){//送套餐货品
					NotifyAccountGoodInfoVo notifyAccountGoodInfoVo=new NotifyAccountGoodInfoVo();
					notifyAccountGoodInfoVo.setOrderProductId(prodBusi.getProd_spec_goods_id());//套餐货品goods_id，订单内唯一
					notifyAccountGoodInfoVo.setBusinessType(EcsOrderConsts.BUSINESS_TYPE_01);//开户完成 businessType=01(ESS业务办理)，业务办理完成businessType=02(BSS业务办理)，目前暂时只有01
					notifyAccountGoodInfoVo.setStatus(EcsOrderConsts.ORDER_STATUS_WMS_0);
					notifyAccountGoodInfoVo.setDescr("");
					notifyAccountGoodInfoVo.setUserDef1("");
					notifyAccountGoodInfoVo.setUserDef2("");
					notifyAccountGoodInfoVo.setPackageId(prodBusi.getItem_spec_goods_id());
					notifyAccountGoodInfoVos.add(notifyAccountGoodInfoVo);
				}
			}
		}
		notifyAccountOrderInfoVo.setGoodInfo(notifyAccountGoodInfoVos);
		return notifyAccountOrderInfoVo;
	}

	public void setOrderInfo(NotifyAccountOrderInfoVo orderInfo) {
		this.orderInfo = orderInfo;
	}
}

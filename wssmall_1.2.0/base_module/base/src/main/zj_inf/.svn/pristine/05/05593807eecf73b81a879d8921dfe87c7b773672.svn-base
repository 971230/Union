package zte.net.ecsord.params.zb.req;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.OrderRouteLogBusiRequest;
import zte.net.ecsord.params.zb.vo.RouteOrder;
import zte.net.ecsord.params.zb.vo.RouteSteps;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * 
 * @author 推送物流信息到总部
 * 
 */
public class NotifyRouteInfoZBRequest extends ZteRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6156811809551225422L;

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;

	@ZteSoftCommentAnnotationParam(name = "是否历史订单", type = "String", isNecessary = "Y", desc = "是否历史订单")
	private boolean notNeedReqStrIsHis;

	@ZteSoftCommentAnnotationParam(name = "访问流水", type = "String", isNecessary = "Y", desc = "ActiveNo：访问流水")
	private String ActiveNo;

	@ZteSoftCommentAnnotationParam(name = "物流公司编码", type = "String", isNecessary = "Y", desc = "物流公司编码")
	private String ExpressCode;

	@ZteSoftCommentAnnotationParam(name = "订单信息", type = "String", isNecessary = "Y", desc = "订单信息")
	private List<RouteOrder> Orders;

	public String getActiveNo() {
		return CommonDataFactory.getInstance().getActiveNo(true);
//		return null;
	}

	public void setActiveNo(String activeNo) {
		ActiveNo = activeNo;
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
		return "com.zte.unicomService.zb.notifyRouteInfoZBRequest";
	}

	public String getExpressCode() {
		String logi_company_id ="";
		if(notNeedReqStrIsHis){
			logi_company_id = CommonDataFactory.getInstance().getAttrFieldValueHis(notNeedReqStrOrderId, AttrConsts.SHIPPING_COMPANY);
		}else{
			logi_company_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SHIPPING_COMPANY);			
		}
		String logi_company_code = CommonDataFactory.getInstance().getLogiCompanyCode(logi_company_id);
//		return "EMAL";
		return CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ZB_LOGISTICS_COMPANY_CODE, logi_company_code);
	}

	public void setExpressCode(String expressCode) {
		ExpressCode = expressCode;
	}	

	public boolean getNotNeedReqStrIsHis() {
		return notNeedReqStrIsHis;
	}

	public void setNotNeedReqStrIsHis(boolean notNeedReqStrIsHis) {
		this.notNeedReqStrIsHis = notNeedReqStrIsHis;
	}

	public List<RouteOrder> getOrders() {
		Orders = new ArrayList<RouteOrder>();
		RouteOrder ordervo = new RouteOrder();
		List<RouteSteps> steps = new ArrayList<RouteSteps>();
		List<OrderRouteLogBusiRequest> orderRouteLogRequests = new ArrayList<OrderRouteLogBusiRequest>();
		String mail_no = "";
		String shipping_company = "";
		if(notNeedReqStrIsHis){//历史单
			ordervo.setOrderNo(CommonDataFactory.getInstance().getAttrFieldValueHis(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID));
			mail_no = CommonDataFactory.getInstance().getAttrFieldValueHis(notNeedReqStrOrderId, AttrConsts.LOGI_NO);
			shipping_company = CommonDataFactory.getInstance().getAttrFieldValueHis(notNeedReqStrOrderId, AttrConsts.SHIPPING_COMPANY);
			orderRouteLogRequests = CommonDataFactory.getInstance().getOrderTreeHis(notNeedReqStrOrderId).getOrderRouteLogRequests();
		}else{
			ordervo.setOrderNo(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID));
			mail_no = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.LOGI_NO);
			shipping_company = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SHIPPING_COMPANY);
			orderRouteLogRequests = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderRouteLogRequests();
		}
//		mail_no="2016031814355742000";
		ordervo.setMailNo(mail_no);
		String op_code = "";
		for(OrderRouteLogBusiRequest vo: orderRouteLogRequests){
			if(StringUtils.equals(mail_no, vo.getMail_no())){
				RouteSteps stepvo = new RouteSteps();
				stepvo.setAcceptTime(vo.getRoute_acceptime());
				stepvo.setAcceptAddress(vo.getRoute_acceptadress());
				//总部用的旧协议，订单用的顺丰新协议，映射下。只传送给总部可以映射出来的路由信息
				if(StringUtils.equals(shipping_company, EcsOrderConsts.LOGI_COMPANY_SFFYZQYF)){
					op_code = CommonDataFactory.getInstance().getOtherDictVodeValue("route_op_code_sf", vo.getOp_code());
				}else if (StringUtils.equals(shipping_company, EcsOrderConsts.LOGI_COMPANY_EMS0001)){
					op_code = CommonDataFactory.getInstance().getOtherDictVodeValue("route_op_code_ems", vo.getOp_code());
				}
				
				stepvo.setAcceptState(op_code);
				if(!StringUtils.isEmpty(op_code))steps.add(stepvo);
			}
		}
		ordervo.setSteps(steps);
		Orders.add(ordervo);
		return Orders;
	}

	public void setOrders(List<RouteOrder> orders) {
		Orders = orders;
	}	

}

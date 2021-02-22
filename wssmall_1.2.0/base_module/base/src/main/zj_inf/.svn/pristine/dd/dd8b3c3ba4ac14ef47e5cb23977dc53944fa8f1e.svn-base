package zte.net.ecsord.params.bss.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderAdslBusiRequest;

import java.util.List;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;

/**
 * 查询操作员
 * 
 * @author song.qi
 */
public class OrderListForWorkReq extends ZteRequest {

	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：内部订单号 ")
	private String notNeedReqStrOrderId;

	@ZteSoftCommentAnnotationParam(name = "eparchyCode", type = "String", isNecessary = "Y", desc = "地市编码")
	private String eparchyCode;// 必填，地市编码 A
	@ZteSoftCommentAnnotationParam(name = "cityCode", type = "String", isNecessary = "Y", desc = "营业县分编码")
	private String cityCode;// 必填，营业县分编码，如ABJ
	@ZteSoftCommentAnnotationParam(name = "Ordertype", type = "String", isNecessary = "Y", desc = "派单类型")
	private String Ordertype;// 必填，派单类型

	public String getEparchyCode() {
		if (StringUtils.isEmpty(eparchyCode)) {
			IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
			String sql="select order_city_code from es_order_intent  where order_id='"+notNeedReqStrOrderId+"'";
			eparchyCode = baseDaoSupport.queryForString(sql);
		}
		if (StringUtils.isEmpty(eparchyCode)) {
			eparchyCode = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CITY_CODE);
		}
		// 330100-A
		return CommonDataFactory.getInstance().getOtherDictVodeValue("bss_area_code", eparchyCode);
	}

	public void setEparchyCode(String eparchyCode) {
		this.eparchyCode = eparchyCode;
	}

	public String getCityCode() {
		if (StringUtils.isEmpty(cityCode)) {
			IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
			String sql="select county_id from es_order_intent  where order_id='"+notNeedReqStrOrderId+"'";
			cityCode = baseDaoSupport.queryForString(sql);
		}
		if (StringUtils.isEmpty(cityCode)) {
			List<OrderAdslBusiRequest> orderAdslBusiRequest = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest();
			if (null != orderAdslBusiRequest && orderAdslBusiRequest.size() > 0) {
				cityCode = orderAdslBusiRequest.get(0).getCounty_id();
			}
		}
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getOrdertype() {
		// Ordertype = "01";

		Ordertype = "00000000";
		return Ordertype;
	}

	public void setOrdertype(String ordertype) {
		Ordertype = ordertype;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	@Override
	public void check() throws ApiRuleException {
	}

	@Override
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZteInfOpenService.getOperator";
	}

}

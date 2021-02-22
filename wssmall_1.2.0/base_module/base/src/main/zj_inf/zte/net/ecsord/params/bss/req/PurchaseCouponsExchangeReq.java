package zte.net.ecsord.params.bss.req;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.drools.core.util.StringUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.AttrDiscountInfoBusiRequest;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 社会渠道购机券换手机BSS端支持(转兑)
 */
public class PurchaseCouponsExchangeReq extends BaseBSSReq {

	@ZteSoftCommentAnnotationParam(name = "手机串号", type = "String", isNecessary = "Y", desc = "TERMINAL_ID：手机串号 ")
	private String TERMINAL_ID;
	
	@ZteSoftCommentAnnotationParam(name = "代金券编码", type = "String", isNecessary = "Y", desc = "DATA_CODE：代金券编码 ")
	private String DATA_CODE;
	
	@ZteSoftCommentAnnotationParam(name = "业务流水号", type = "String", isNecessary = "Y", desc = "RELATION_TRADE_ID：业务流水号 ")
	private String RELATION_TRADE_ID;
	
	@ZteSoftCommentAnnotationParam(name = "业务时间", type = "String", isNecessary = "Y", desc = "TRADE_TIME：业务时间 ")
	private String TRADE_TIME;
	
	@ZteSoftCommentAnnotationParam(name = "发展人工号", type = "String", isNecessary = "Y", desc = "DEVELOP_STAFF_ID：发展人工号 ")
	private String DEVELOP_STAFF_ID;
	
	@ZteSoftCommentAnnotationParam(name = "操作工号归属地州", type = "String", isNecessary = "Y", desc = "TRADE_EPARCHY_CODE：操作工号归属地州 ")
	private String TRADE_EPARCHY_CODE;
	
	@ZteSoftCommentAnnotationParam(name = "预留字段1", type = "String", isNecessary = "N", desc = "RESERVE1：预留字段1 ")
	private String RESERVE1;
	
	@ZteSoftCommentAnnotationParam(name = "预留字段2", type = "String", isNecessary = "N", desc = "RESERVE2：预留字段1 ")
	private String RESERVE2;
	
	@ZteSoftCommentAnnotationParam(name = "预留字段3", type = "String", isNecessary = "N", desc = "RESERVE3：预留字段1 ")
	private String RESERVE3;
	
	@ZteSoftCommentAnnotationParam(name = "预留字段4", type = "String", isNecessary = "N", desc = "RESERVE4：预留字段1 ")
	private String RESERVE4;

	@Override
	public String getBody() {//此方法子类必须复写
		if(StringUtils.isEmpty(body)){
			body = new StringBuffer()
			.append(getTERMINAL_ID()).append(getTab())
			.append(getDATA_CODE()).append(getTab())
			.append(getRELATION_TRADE_ID()).append(getTab())
			.append(getTRADE_TIME()).append(getTab())
			.append(getDEVELOP_STAFF_ID()).append(getTab())
			.append(getTRADE_EPARCHY_CODE()).append(getTab())
			.append(getRESERVE1()).append(getTab())
			.append(getRESERVE2()).append(getTab())
			.append(getRESERVE3()).append(getTab())
			.append(getRESERVE4())
			.toString();
		}
		return body;
	}

	public String getA4() {//服务类型,需要子类复写
//		if(StringUtils.isEmpty(a4)){
			a4 = "700000001036";//转兑
//		}
		return a4;
	}

	public String getTERMINAL_ID() {
		if(StringUtils.isEmpty(TERMINAL_ID)){
			TERMINAL_ID = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.TERMINAL_NUM);
		}
		return TERMINAL_ID;
	}

	public void setTERMINAL_ID(String tERMINAL_ID) {
		TERMINAL_ID = tERMINAL_ID;
	}

	public String getRELATION_TRADE_ID() {
		if(null==RELATION_TRADE_ID||"".equals(RELATION_TRADE_ID)){
			RELATION_TRADE_ID = CommonDataFactory.getInstance().getActiveNo(AttrConsts.ACTIVE_NO_ON_OFF);
		}
		return RELATION_TRADE_ID;
	}

	public void setRELATION_TRADE_ID(String rELATION_TRADE_ID) {
		RELATION_TRADE_ID = rELATION_TRADE_ID;
	}

	public String getTRADE_TIME() {
		if(null==TRADE_TIME||"".equals(TRADE_TIME)){
			TRADE_TIME = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		}
		return TRADE_TIME;
	}

	public void setTRADE_TIME(String tRADE_TIME) {
		TRADE_TIME = tRADE_TIME;
	}

	public String getDATA_CODE() {
		if(null==DATA_CODE||"".equals(DATA_CODE)){
			List<AttrDiscountInfoBusiRequest> discounts = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getDiscountInfoBusiRequests();
			
			List<Map<String,String>> discount_types = CommonDataFactory.getInstance().listDcPublicData(StypeConsts.DISCOUNTTYPE);
			List<String> need_types = new ArrayList<String>();
			for (Map<String,String> map : discount_types) {//需要调接口的类型
				if("1".equals(map.get("codea"))){
					need_types.add(map.get("pkey"));
				}
			}
			
			if(null!=discounts&&discounts.size()>0){
				for(AttrDiscountInfoBusiRequest discount:discounts){
					if(need_types.contains(discount.getActivity_type())){//根据activity_type判断该记录是否为要使用的代金券
						DATA_CODE = discount.getActivity_code();
						break;//目前只支持一张代金券
					}
				}
			}
		}
		return DATA_CODE;
	}

	public void setDATA_CODE(String dATA_CODE) {
		DATA_CODE = dATA_CODE;
	}

	public String getDEVELOP_STAFF_ID() {
		if(StringUtils.isEmpty(DEVELOP_STAFF_ID)){//发展人工号,仕鹏确认发展人id
			String development_code = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.DEVELOPMENT_CODE);
			DEVELOP_STAFF_ID = development_code;
		}
		return DEVELOP_STAFF_ID;
	}

	public void setDEVELOP_STAFF_ID(String dEVELOP_STAFF_ID) {
		DEVELOP_STAFF_ID = dEVELOP_STAFF_ID;
	}

	public String getTRADE_EPARCHY_CODE() {//此接口固定传0020;仕鹏说按订单归属地市
		if(StringUtils.isEmpty(TRADE_EPARCHY_CODE)){
			String city=CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CITY_CODE);//邮编
			TRADE_EPARCHY_CODE = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.BSS_AREA_CODE, city);//区号
			if(StringUtils.isEmpty(TRADE_EPARCHY_CODE)){//如果翻译不到地市编码，就默认广州
				TRADE_EPARCHY_CODE = "0020";
			}
		}
		return TRADE_EPARCHY_CODE;
	}

	public void setTRADE_EPARCHY_CODE(String tRADE_EPARCHY_CODE) {
		TRADE_EPARCHY_CODE = tRADE_EPARCHY_CODE;
	}

	public String getRESERVE1() {
		RESERVE1 = "RESERVE1";//预留字段1
		return RESERVE1;
	}

	public void setRESERVE1(String rESERVE1) {
		RESERVE1 = rESERVE1;
	}

	public String getRESERVE2() {
		RESERVE2 = "RESERVE2";//预留字段2
		return RESERVE2;
	}

	public void setRESERVE2(String rESERVE2) {
		RESERVE2 = rESERVE2;
	}

	public String getRESERVE3() {
		RESERVE3 = "RESERVE3";//预留字段3
		return RESERVE3;
	}

	public void setRESERVE3(String rESERVE3) {
		RESERVE3 = rESERVE3;
	}

	public String getRESERVE4() {
		RESERVE4 = "RESERVE4";//预留字段4
		return RESERVE4;
	}

	public void setRESERVE4(String rESERVE4) {
		RESERVE4 = rESERVE4;
	}

	@Override
	public String getOp_Code(){//这个由子类复写以区分调用的接口
		return "bss.purchaseCouponsExchange";
	}

}

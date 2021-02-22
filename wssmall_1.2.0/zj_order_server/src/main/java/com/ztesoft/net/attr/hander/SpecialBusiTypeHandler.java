package com.ztesoft.net.attr.hander;

import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.model.AttrDef;
/**
 * 特殊业务类型
 * @author Administrator
 * 00-非特殊业务、02-副卡、03-3G预付费转后付费【待补充】、04-2G/3G转4G
 */
public class SpecialBusiTypeHandler extends BaseSupport implements IAttrHandler {
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
		String inst_id = params.getInst_id(); // 数据实列ID（如订单扩展表ID、子订单扩展表ID、货品扩展表ID、物流ID、支付记录ID）
		String goods_id = params.getGoods_id(); // 商品ID 只有子订单或货品单才有值
		String pro_goods_id = params.getPro_goods_id(); // 货品ID 只有货品单才有值
		String order_from = params.getOrder_from(); // 订单来源
		String value = params.getValue(); // 原始值
		AttrDef attrDef = params.getAttrDef(); // 属性定议配置信息
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		
		String special_busi_type = EcsOrderConsts.SPECIAL_BUSI_TYPE_00;//默认为非特殊业务类型
		String vicecard_no = (String) orderAttrValues.get(AttrConsts.VICECARD_NO);
		String is_to4g = (String) orderAttrValues.get(AttrConsts.RESERVE1);
		/*ZX add 2015-12-25 start 判断总商订单非4G自由组合套餐*/
		String plat_type = (String) orderAttrValues.get(AttrConsts.PLAT_TYPE);
		boolean iszbmall = CommonDataFactory.getInstance().isZbOrder(plat_type);
		String goodsAttInfo = (String) orderAttrValues.get("zb_fuka_info");
		//非总部订单赋值为[]
		if(!iszbmall){
			goodsAttInfo = "[]";
		}
		JSONArray jsonArray = JSONArray.fromObject(goodsAttInfo);
		String cat_id = CommonDataFactory.getInstance().getGoodSpec(order_id, null, SpecConsts.CAT_ID);
		//号码原网别
//		String old_net_type = (String) orderAttrValues.get(AttrConsts.OLD_NET_TYPE);
//		//订购产品网别
//		String net_type = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		/*ZX add 2015-12-25 end 判断总商订单非4G自由组合套餐*/
		if((!StringUtils.isEmpty(vicecard_no)) /*ZX add 2015-12-25 start*/
				|| (iszbmall && (StringUtils.isNotBlank(goodsAttInfo)&&jsonArray.size()>0)
						&& (!EcsOrderConsts.GOODS_CAT_ID_4G_GXZH.equals(cat_id)))/*ZX add 2015-12-25 end*/){//如果副卡号码不为空，则认为是副卡业务
			special_busi_type = EcsOrderConsts.SPECIAL_BUSI_TYPE_02;
		}
		else if(EcsOrderConsts.IS_TO4G.equals(is_to4g)){//2G/3G转4G业务
			special_busi_type = EcsOrderConsts.SPECIAL_BUSI_TYPE_04;
		}
		else if(1==2){//3G预付费转后付费
			special_busi_type = EcsOrderConsts.SPECIAL_BUSI_TYPE_03;
		}
		if(EcsOrderConsts.ORDER_FROM_10012.equals(order_from)){//天猫分销直接取原值
			special_busi_type = value;
		}
		//总部会下发原号码网别，与订购产品网别对比，判断是否23转4
//		if(!StringUtils.isEmpty(old_net_type)){
//			if(StringUtils.equals(net_type, EcsOrderConsts.NET_TYPE_4G) 
//					&& !StringUtils.equals(old_net_type, EcsOrderConsts.NET_TYPE_4G)){
//				special_busi_type = EcsOrderConsts.SPECIAL_BUSI_TYPE_04;				
//			}
//		}
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(special_busi_type);
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		return null;
	}

}
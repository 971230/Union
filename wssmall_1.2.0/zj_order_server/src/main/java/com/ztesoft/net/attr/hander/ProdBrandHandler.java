package com.ztesoft.net.attr.hander;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.model.AttrDef;
/**
 * 产品品牌
 * @author Administrator
 * 2GPH、3GPH、4GPH
 */
public class ProdBrandHandler extends BaseSupport implements IAttrHandler {
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
		
		String prod_brand = "";
		if(StringUtils.isEmpty(value)){
			goods_id = (String) orderAttrValues.get("goods_id");
			String type_id = (String) orderAttrValues.get(AttrConsts.GOODS_TYPE);
			if(EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(type_id)){
				String net_type = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, SpecConsts.NET_TYPE);
				if(!StringUtils.isEmpty(net_type)){
					if(EcsOrderConsts.NET_TYPE_2G.equalsIgnoreCase(net_type)){
    	    			prod_brand = EcsOrderConsts.PROD_BRAND_2GPH;
    	    		}else if(EcsOrderConsts.NET_TYPE_3G.equalsIgnoreCase(net_type)){
    	    			prod_brand = EcsOrderConsts.PROD_BRAND_3GPH;
    	    		}else if(EcsOrderConsts.NET_TYPE_4G.equalsIgnoreCase(net_type)){
    	    			prod_brand = EcsOrderConsts.PROD_BRAND_4GPH;
    	    		}
				}
				if(StringUtils.isEmpty(prod_brand)){
					String catId = CommonDataFactory.getInstance().getGoodSpec(order_id, goods_id, SpecConsts.CAT_ID);
					prod_brand=CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.PRODUCT_BRAND,catId);
				}
			}
		}
		else{
			prod_brand = value;
		}
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(prod_brand);
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

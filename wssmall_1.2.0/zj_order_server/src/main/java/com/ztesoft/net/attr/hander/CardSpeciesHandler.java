package com.ztesoft.net.attr.hander;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
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
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.model.AttrDef;

/**
 * 礼品种类
 * @author wu.i
 * 01有价卡、02实物品
 *
 */
public class CardSpeciesHandler extends BaseSupport implements IAttrHandler {
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
		String product_pro_id = params.getGoods_pro_id(); //货品product_id
		String order_from = params.getOrder_from(); // 订单来源
		String value = params.getValue(); // 原始值
		AttrDef attrDef = params.getAttrDef(); // 属性定议配置信息
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值

		String card_species = null;
		String plat_type = (String) orderAttrValues.get(AttrConsts.PLAT_TYPE);
		if(CommonDataFactory.getInstance().isZbOrder(plat_type)){
			String giftInfos = (String) orderAttrValues.get(AttrConsts.GIFT_INFOS);
			if(!StringUtils.isEmpty(giftInfos)){
				JSONArray jsonArray = JSONArray.fromObject(giftInfos);
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
					String giftDesc = jsonObject.getString("giftDesc");
					if(!StringUtils.isEmpty(giftDesc) && giftDesc.indexOf("卡")!=-1){
						card_species = EcsOrderConsts.CARD_SPECIES_01;
					}
					else{
						card_species = EcsOrderConsts.CARD_SPECIES_02;
					}
				}
			}
		}
		else{
			String activity_list = (String) orderAttrValues.get(AttrConsts.ACTIVITY_LIST);
			if(!StringUtils.isEmpty(activity_list)){
				JSONArray jsonArray = JSONArray.fromObject(activity_list);
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
					String gift_list = jsonObject.getString("gift_list");
					if(!StringUtils.isEmpty(gift_list)){
						JSONArray giftArray = JSONArray.fromObject(gift_list);
						for (int j = 0; j < giftArray.size(); j++) {
							JSONObject giftObject = JSONObject.fromObject(giftArray.get(j));
							String gift_id = giftObject.getString("gift_id");
							Goods goods=CommonDataFactory.getInstance().getGoodsBySku(gift_id);
							if(goods!=null){
								String cat_id = goods.getCat_id();
								String is_lipin = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.IS_LIPIN, cat_id);
								if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_lipin)){
									if(EcsOrderConsts.PRODUCT_CAT_ID_1.equals(cat_id)){
										card_species = EcsOrderConsts.CARD_SPECIES_01;
									}
									else{
										card_species = EcsOrderConsts.CARD_SPECIES_02;
									}
								}
							}
						}
					}
				}
			}
		}
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(card_species);
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

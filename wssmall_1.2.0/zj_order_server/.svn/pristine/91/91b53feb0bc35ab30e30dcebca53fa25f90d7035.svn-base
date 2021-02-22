package com.ztesoft.net.attr.hander;

import java.util.List;
import java.util.Map;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

import com.powerise.ibss.framework.Const;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.model.AttrDef;
/**
 * 是否一键开户
 * @author Administrator
 * 1是、0否
 */
public class IsEasyAccountHandler extends BaseSupport implements IAttrHandler {
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
		
		AttrInstLoadResp resp = new AttrInstLoadResp();
		
		String is_old = (String) orderAttrValues.get(AttrConsts.IS_OLD);
		goods_id = (String) orderAttrValues.get("goods_id");
		Map goodsSpec = CommonDataFactory.getInstance().getGoodsSpecMap(order_id, goods_id);
		String goods_type = Const.getStrValue(goodsSpec, SpecConsts.TYPE_ID);
		Map planSpec = CommonDataFactory.getInstance().getProductSpecMap(order_id, SpecConsts.TYPE_ID_10002);
		String net_type = Const.getStrValue(planSpec, SpecConsts.NET_TYPE);
		String pay_type = Const.getStrValue(planSpec, SpecConsts.PAY_TYPE);
		//卡种类【成卡、白卡】
		String mall_sim_type = (String) orderAttrValues.get(AttrConsts.SIM_TYPE);
		String sim_type = CommonDataFactory.getInstance().getSimType(order_id, order_from, mall_sim_type, orderAttrValues);
		//合约计划分类
		String hy_cat_id = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, SpecConsts.CAT_ID);
		//卡时长
		String card_time = Const.getStrValue(goodsSpec, SpecConsts.CARD_TIME);
		String cat_id = Const.getStrValue(goodsSpec, SpecConsts.CAT_ID);
		String society_flag = EcsOrderConsts.EMPTY_STR_VALUE;//是否社会机，默认否
		if(EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(goods_type) || EcsOrderConsts.GOODS_TYPE_PHONE.equals(goods_type)){
			String is_customized = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.IS_CUSTOMIZED, cat_id);
			if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_customized)){
				society_flag =  EcsOrderConsts.NO_DEFAULT_VALUE;
			}else if(EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_customized)){
				society_flag =  EcsOrderConsts.IS_DEFAULT_VALUE;
			}
		}
		
		IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
		DcPublicInfoCacheProxy proxy = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		List<Map> configs = proxy.getIsSendZbConfig();
		String is_easy_account = EcsOrderConsts.IS_EASY_ACCOUNT_NO;
		
		boolean flag=false;
		flag = CommonDataFactory.getInstance().matchSendZbOption(card_time, pay_type, goods_type, sim_type, society_flag, is_old, hy_cat_id, net_type, configs);
		if(flag){
			is_easy_account = EcsOrderConsts.IS_EASY_ACCOUNT_YES;
		}
		
		if("10092".equals(order_from)||"10097".equals(order_from)){//压单开户
			is_easy_account = EcsOrderConsts.IS_EASY_ACCOUNT_YES;
		}
		
		
		resp.setField_value(is_easy_account);
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

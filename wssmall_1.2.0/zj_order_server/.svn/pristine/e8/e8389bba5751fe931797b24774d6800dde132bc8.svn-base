package com.ztesoft.net.attr.hander;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

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
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.model.AttrDef;
/**
 * 是否发送总部
 * @author Administrator
 *
 */
public class IsSendZbHandler extends BaseSupport implements IAttrHandler {
	@Resource
	private IDcPublicInfoManager dcPublicInfoManager;
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
		String is_send_zb = EcsOrderConsts.NO_DEFAULT_VALUE;
		AttrInstLoadResp resp = new AttrInstLoadResp();

		/*ZX add 2015-12-25 修改总商订单默认不与总部交互 (所有情况都不与总部交互) start*/
		if (StringUtils.isNotBlank(is_send_zb)) { // 此判断无实际意义，只要能保证if永久成立即可，目的返回不予总部交互
			resp.setField_value(EcsOrderConsts.NO_DEFAULT_VALUE);
			return resp;
		}
		/*ZX add 2015-12-25 修改总商订单默认不与总部交互 end*/
		
		String order_city_code = (String) orderAttrValues.get(AttrConsts.ORDER_CITY_CODE);
		if(StringUtils.isEmpty(order_city_code)){
			is_send_zb = EcsOrderConsts.NO_DEFAULT_VALUE;
			resp.setField_value(is_send_zb);
			return resp;
		}
		//订单来源为总部发送总部
		String plat_type = (String) orderAttrValues.get(AttrConsts.PLAT_TYPE);
		if(CommonDataFactory.getInstance().isZbOrder(plat_type)){
			is_send_zb = EcsOrderConsts.IS_DEFAULT_VALUE;
			resp.setField_value(is_send_zb);
			return resp;
		}

		//含宽带赠品（智慧沃家产品）不发送总部
		try{
			String json = (String)orderAttrValues.get("activity_list");

			if(!StringUtils.isEmpty(json) && !"-1".equals(json)){
				JSONArray jsonArray = JSONArray.fromObject(json);
				DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		        List<Map> list = dcPublicCache.getList(StypeConsts.BROADBAND_GIFT);
		        String broadband_gift = (String)list.get(0).get("pname");
		        /*智慧沃家产品活动 的pmt_code配置在es_dc_publict_ext表pkey及pname字段，每个pmt_code以","隔开;其中pkey首尾不含",",pname首尾均含","
		        目前的逻辑是：凡活动赠品中包含一个或以上智慧沃家产品，均不同步总部*/
				for(int i=0;i<jsonArray.size();i++){
					JSONObject json1 = (JSONObject) jsonArray.get(i);
					if(json1.containsKey("activity_code")){					
						String activity_code = json1.getString("activity_code");
						if(broadband_gift.contains(","+activity_code+",")){
							is_send_zb = EcsOrderConsts.NO_DEFAULT_VALUE;
							resp.setField_value(is_send_zb);
							return resp;
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		//非总部合约不发送总部
		goods_id = (String) orderAttrValues.get("goods_id");
		String is_group_contract = CommonDataFactory.getInstance().isGroupContract(order_from, goods_id);
		if(EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_group_contract)){
			is_send_zb = EcsOrderConsts.NO_DEFAULT_VALUE;
			resp.setField_value(is_send_zb);
			return resp;
		}
		
		//预约单就不发送总部---zengxianlian
		String wm_isreservation_from = (String) orderAttrValues.get(AttrConsts.WM_ISRESERVATION_FROM);
		if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(wm_isreservation_from)){
			is_send_zb = EcsOrderConsts.NO_DEFAULT_VALUE;
			resp.setField_value(is_send_zb);
			return resp;
		}
		
		// 特殊业务类型订单不发总部
		String vicecard_no = (String) orderAttrValues.get(AttrConsts.VICECARD_NO);
		String is_to4g = (String) orderAttrValues.get(AttrConsts.RESERVE1);
		String sub_no = (String) orderAttrValues.get(AttrConsts.SUB_NO);
		boolean is_special_busi = isSpecialBusinessType(vicecard_no, is_to4g,sub_no);
		if(is_special_busi){
			is_send_zb = EcsOrderConsts.NO_DEFAULT_VALUE;
			resp.setField_value(is_send_zb);
			return resp;
		}
		
		//订单来源为非总部，根据规则判断是否发送总部(与IsEasyAccountHandler判断一致)
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

		boolean flag = CommonDataFactory.getInstance().matchSendZbOption(card_time, pay_type, goods_type, sim_type, society_flag, is_old, hy_cat_id, net_type, configs);
		if(flag){
			is_send_zb = EcsOrderConsts.IS_DEFAULT_VALUE;
		}
		resp.setField_value(is_send_zb);
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
	
	/**
	 * 判断是否特殊业务类型
	 * @param order_id
	 * @return
	 */
	private boolean isSpecialBusinessType(String vicecard_no, String is_to4g,String sub_no){
		boolean is_special_busi = false;
		//有副卡号码是特殊业务类型
		if(!StringUtils.isEmpty(vicecard_no)){
			is_special_busi = true;
		}
		//2G/3G转4G是特殊业务类型
		else if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_to4g)){
			is_special_busi = true;
		}
		//有共享子号是特殊业务类型
		else if(!StringUtils.isEmpty(sub_no)){
			is_special_busi = true;
		}
		return is_special_busi;
	}

}

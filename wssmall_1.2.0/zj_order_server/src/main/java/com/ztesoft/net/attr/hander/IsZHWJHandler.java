package com.ztesoft.net.attr.hander;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

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
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.model.AttrDef;
/**
 * 是否发送总部
 * @author Administrator
 *
 */
public class IsZHWJHandler extends BaseSupport implements IAttrHandler {
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
		String is_zhwj = EcsOrderConsts.NO_DEFAULT_VALUE;//默认非智慧沃家
		AttrInstLoadResp resp = new AttrInstLoadResp();
		//含宽带赠品（智慧沃家产品）
		try{
			String json = (String)orderAttrValues.get("activity_list");
			if(!StringUtils.isEmpty(json) && !"-1".equals(json)){
				JSONArray jsonArray = JSONArray.fromObject(json);
				DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		        List<Map> list = dcPublicCache.getList(StypeConsts.BROADBAND_GIFT);
		        String broadband_gift = (String)list.get(0).get("pname");
		        /*智慧沃家产品活动 的pmt_code配置在es_dc_publict_ext表pkey及pname字段，每个pmt_code以","隔开;其中pkey首尾不含",",pname首尾均含","*/
				for(int i=0;i<jsonArray.size();i++){
					JSONObject json1 = (JSONObject) jsonArray.get(i);
					if(json1.containsKey("activity_code")){					
						String activity_code = json1.getString("activity_code");
						if(broadband_gift.contains(","+activity_code+",")){
							is_zhwj = EcsOrderConsts.IS_DEFAULT_VALUE;//是智慧沃家
							resp.setField_value(is_zhwj);
							return resp;
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		resp.setField_value(is_zhwj);
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

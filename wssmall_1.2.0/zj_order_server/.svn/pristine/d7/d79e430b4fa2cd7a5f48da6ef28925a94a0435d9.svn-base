/**
 * 
 */
package com.ztesoft.net.attr.hander;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.AttrPackageActivityBusiRequest;
import zte.net.ecsord.params.busi.req.OrderActivityBusiRequest;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.AttrDef;

import consts.ConstsCore;

/**
 * @author ZX
 * @version 2015-12-23
 * @see 总商副卡信息处理器
 * 
 */
public class ZbActivityHandler extends BaseSupport implements IAttrHandler {

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo)
			throws ApiBusiException {
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
		String plat_type = (String) orderAttrValues.get(AttrConsts.PLAT_TYPE);
		boolean iszbmall = CommonDataFactory.getInstance().isZbOrder(plat_type);
		String zb_activity = (String) orderAttrValues.get("zb_activity");
		
		if (!StringUtils.isEmpty(zb_activity) && !"null".equals(zb_activity)
				&& !EcsOrderConsts.ATTR_DEFAULT_VALUE.equals(zb_activity)) {
			if (iszbmall) {
				JSONArray jsonArray = JSONArray.fromObject(zb_activity);
				if (jsonArray != null && jsonArray.size() > 0) {
					for (int i = 0; i < jsonArray.size(); i ++) {
						String ES_ORDER_ACTIVITY_SEQ = baseDaoSupport.getSequences("S_ES_ORDER_ACTIVITY");
						JSONObject jsonObject =JSONObject.fromObject(jsonArray.get(i));
						String activityType = jsonObject.getString("activityType")!=null?jsonObject.getString("activityType"):"";
						String activityCode = jsonObject.getString("activityCode")!=null?jsonObject.getString("activityCode"):"";
						String activityName = jsonObject.getString("activityName")!=null?jsonObject.getString("activityName"):"";
						String actProtPer = jsonObject.getString("actProtPer")!=null?jsonObject.getString("actProtPer"):"";
						OrderActivityBusiRequest orderActivityBusiRequest = new OrderActivityBusiRequest();
						orderActivityBusiRequest.setInst_id(ES_ORDER_ACTIVITY_SEQ);
						orderActivityBusiRequest.setFuka_inst_id("0"); // 主卡默认全部填0
						orderActivityBusiRequest.setOrder_id(order_id);
						orderActivityBusiRequest.setActivity_type(activityType);
						orderActivityBusiRequest.setActivity_code(activityCode);
						orderActivityBusiRequest.setActivity_name(activityName);
						orderActivityBusiRequest.setAct_prot_per(actProtPer);
						orderActivityBusiRequest.setActivity_type_zhufu(EcsOrderConsts.ZB_CARD_TYPE_ZHU); // 主卡
						orderActivityBusiRequest.setSource_from(ManagerUtils.getSourceFrom());
						orderActivityBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
						orderActivityBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						orderActivityBusiRequest.store();
						String activityPackage = jsonObject.getString("package")!=null?jsonObject.getString("package"):"";
						JSONArray packages = JSONArray.fromObject(activityPackage);
						if (packages != null && packages.size() > 0) {
							for (int j = 0; j < packages.size(); j ++) {
								JSONObject subProd = JSONObject.fromObject(packages.get(j));
								String PACKAGE_INST_ID = baseDaoSupport.getSequences("S_ES_ATTR_PACKAGE_ACTIVITY");
								String packageCode = subProd.getString("packageCode")!=null?subProd.getString("packageCode"):"";
								String packageName = subProd.getString("packageName")!=null?subProd.getString("packageName"):"";
								String elementCode = subProd.getString("elementCode")!=null?subProd.getString("elementCode"):"";
								String elementType = subProd.getString("elementType")!=null?subProd.getString("elementType"):"";
								String elementName = subProd.getString("elementName")!=null?subProd.getString("elementName"):"";
								AttrPackageActivityBusiRequest attrPackageActivityBusiRequest = new AttrPackageActivityBusiRequest();
								attrPackageActivityBusiRequest.setInst_id(PACKAGE_INST_ID);
								attrPackageActivityBusiRequest.setActivity_inst_id(ES_ORDER_ACTIVITY_SEQ);
								attrPackageActivityBusiRequest.setOrder_id(order_id);
								attrPackageActivityBusiRequest.setPackage_code(packageCode);
								attrPackageActivityBusiRequest.setPackage_name(packageName);
								attrPackageActivityBusiRequest.setElement_code(elementCode);
								attrPackageActivityBusiRequest.setElement_type(elementType);
								attrPackageActivityBusiRequest.setElement_name(elementName);
								attrPackageActivityBusiRequest.setSource_from(ManagerUtils.getSourceFrom());
								attrPackageActivityBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
								attrPackageActivityBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
								attrPackageActivityBusiRequest.store();
							}
						}
					}
				}
			}
		}
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(zb_activity);
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

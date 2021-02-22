package com.ztesoft.net.attr.hander;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.AttrPackageBusiRequest;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.AttrDef;

import consts.ConstsCore;

/**
 * 总部可选包
 * @author Administrator
 *
 */
public class ZbPackagesHandler extends BaseSupport implements IAttrHandler {
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
		
		String plat_type = (String) orderAttrValues.get(AttrConsts.PLAT_TYPE);
		if (CommonDataFactory.getInstance().isZbOrder(plat_type)) {
			String zbpackages = (String) orderAttrValues.get(AttrConsts.ZB_PACKAGES);
			if(!StringUtils.isEmpty(zbpackages) && !"-1".equals(zbpackages)){
				saveZBPackages(order_id, inst_id, zbpackages);
			}
		} 
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(null);
		return resp;
	}
	
	private void saveZBPackages(String order_id, String inst_id, String zbpackages){
		String PackageCode = "";
		String PackageName = "";
		String ProductCode = "";
		String ElementCode = "";
		String ElementType = "";
		String ElementName = "";
		String OperType = EcsOrderConsts.BSS_OPER_TYPE_E;
		String ChageType = EcsOrderConsts.CHANGE_TYPE;
		String BizType = EcsOrderConsts.BIZ_TYPE_FWYH;

		JSONArray jsonArray = JSONArray.fromObject(zbpackages);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
			PackageCode = jsonObject.get("packageCode")==null?"":jsonObject.get("packageCode").toString();
			PackageName = jsonObject.get("packageName")==null?"":jsonObject.get("packageName").toString();
			ProductCode = jsonObject.get("packageCode")==null?"":jsonObject.get("packageCode").toString();
			ElementCode = jsonObject.get("elementCode")==null?"":jsonObject.get("elementCode").toString();
			ElementType = jsonObject.get("elementType")==null?"":jsonObject.get("elementType").toString();
			ElementName = jsonObject.get("elementName")==null?"":jsonObject.get("elementName").toString();
			Map mapValue = new HashMap();
//			mapValue.put("package_inst_id", this.baseDaoSupport.getSequences("seq_attr_zb_package"));
			mapValue.put("order_id", order_id);
			mapValue.put("inst_id", inst_id);
			mapValue.put("package_code", PackageCode);
			mapValue.put("package_name", PackageName);
			mapValue.put("product_code", ProductCode);
			mapValue.put("element_code", ElementCode);
			mapValue.put("element_type", ElementType);
			mapValue.put("element_name", ElementName);
			mapValue.put("oper_type", OperType);
			mapValue.put("change_type", ChageType);
			mapValue.put("biz_type", BizType);
			mapValue.put("source_from", ManagerUtils.getSourceFrom());
//			this.baseDaoSupport.insert("es_attr_package", mapValue);
			//long start2 = System.currentTimeMillis();
			AttrPackageBusiRequest attrPackageBusiRequest = new AttrPackageBusiRequest();
			try {
				BeanUtils.copyProperties(attrPackageBusiRequest, mapValue);
				attrPackageBusiRequest.setOrder_id(order_id);
				attrPackageBusiRequest.setPackage_inst_id( this.baseDaoSupport.getSequences("seq_attr_zb_package"));
				attrPackageBusiRequest.setPackageCode(PackageCode);
				attrPackageBusiRequest.setPackageName(PackageName);
				attrPackageBusiRequest.setProductCode(ProductCode);
				attrPackageBusiRequest.setElementCode(ElementCode);
				attrPackageBusiRequest.setElementType(ElementType);
				attrPackageBusiRequest.setElementName(ElementName);
				attrPackageBusiRequest.setOperType(OperType);
				attrPackageBusiRequest.setChageType(ChageType);
				attrPackageBusiRequest.setBizType(BizType);
//				BeanUtils.copyProperties(attrPackageBusiRequest, mapValue);
				attrPackageBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
				attrPackageBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				attrPackageBusiRequest.store();
			} catch (Exception e) {
				e.printStackTrace();
			}
			//long end2 = System.currentTimeMillis();
			//logger.info("ZbPackagesHandler订单属性转换其它时处理时间==========>："+(end2-start2));
		}
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

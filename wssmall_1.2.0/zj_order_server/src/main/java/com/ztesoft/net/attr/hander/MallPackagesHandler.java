package com.ztesoft.net.attr.hander;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.AttrPackageBusiRequest;
import zte.net.ecsord.utils.SpecUtils;

import com.powerise.ibss.framework.Const;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsParam;
import com.ztesoft.net.mall.core.model.support.ParamGroup;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.AttrDef;

import consts.ConstsCore;
/**
 * 新商城可选包
 * @author Administrator
 *
 */
public class MallPackagesHandler extends BaseSupport implements IAttrHandler {
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
		
		String wcfPackages = (String) orderAttrValues.get(AttrConsts.WCF_PACKAGES);
		if(!StringUtils.isEmpty(wcfPackages) && !"-1".equals(wcfPackages) ){
			saveWcfPackages(order_id, inst_id, wcfPackages);
		}
		//淘宝可选包
		boolean isTbOrder = CommonDataFactory.getInstance().isTbOrder(order_from);//是否淘宝订单,默认否
		if(isTbOrder/*EcsOrderConsts.ORDER_FROM_TAOBAO.equals(order_from)
				|| EcsOrderConsts.ORDER_FROM_10001.equals(order_from)*/){
			saveTaobaoPackages(order_id,inst_id);
		}
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(null);
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
	
	private void saveTaobaoPackages(String order_id, String inst_id){
		List<Goods> products = SpecUtils.getAllOrderProducts(order_id);
		if(products!=null && products.size()>0){
			for(Goods product:products){
				String type_id = product.getType_id();
				if(EcsOrderConsts.PRODUCT_TYPE_KEXUANBAO.equals(type_id)){
					Map<String, String> spec = SpecUtils.getProductSpecMap(product);
					AttrPackageBusiRequest attrPackageBusiRequest = new AttrPackageBusiRequest();
					try {
						attrPackageBusiRequest.setOrder_id(order_id);
						attrPackageBusiRequest.setInst_id(inst_id);
						attrPackageBusiRequest.setPackage_inst_id( this.baseDaoSupport.getSequences("seq_attr_zb_package"));
						attrPackageBusiRequest.setPackageName(spec.get("package_name"));
						attrPackageBusiRequest.setProductCode(spec.get("package_code"));
						attrPackageBusiRequest.setElementCode(spec.get("package_element_code"));
						attrPackageBusiRequest.setElementType(EcsOrderConsts.ELEMENT_TYPE_D);
						attrPackageBusiRequest.setElementName(spec.get("name"));
						attrPackageBusiRequest.setOperType(EcsOrderConsts.BSS_OPER_TYPE_E);
						attrPackageBusiRequest.setPackageCode(spec.get("package_code"));
						attrPackageBusiRequest.setChageType(EcsOrderConsts.CHANGE_TYPE);
						attrPackageBusiRequest.setBizType(EcsOrderConsts.BIZ_TYPE_FWYH);
						attrPackageBusiRequest.setSku(product.getSku());
						attrPackageBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
						attrPackageBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						attrPackageBusiRequest.store();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	
	private void saveWcfPackages(String order_id, String inst_id, String wcfPackages){
		JSONArray jsonArray = JSONArray.fromObject(wcfPackages);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
			String packageCode = jsonObject.getString("packageCode");
			Map<String,String> params = getProductParams(packageCode);
			
			String bss_code = "";
			String package_code = "";
			String package_element_code = "";
			String package_name = null;
			String paramsJson = params.get("paramsJson");
			ParamGroup[] groups = JsonStrHandler.converFormString(paramsJson);
			if (groups != null && groups.length > 0) {
				for(ParamGroup group : groups){
					List<GoodsParam> paramsList = group.getParamList();
					for (int j = 0; j < paramsList.size(); j++) {
						GoodsParam param = paramsList.get(j);
						if ("bss_code".equalsIgnoreCase(param.getEname()) && "BSS编码".equalsIgnoreCase(param.getName())) {
							bss_code = param.getValue();
						}
						if ("package_code".equalsIgnoreCase(param.getEname()) && "可选包编码".equalsIgnoreCase(param.getName())) {
							package_code = param.getValue();
						}
						if ("package_element_code".equalsIgnoreCase(param.getEname()) && "可选元素编码".equalsIgnoreCase(param.getName())) {
							package_element_code = param.getValue();
						}
						if("package_name".equalsIgnoreCase(param.getEname())){
							package_name = param.getValue();
						}
//						this.baseDaoSupport.insert("es_attr_package", mapValue);
					}
					Map mapValue = new HashMap();
					
//					mapValue.put("package_inst_id", this.baseDaoSupport.getSequences("seq_attr_zb_package"));
//					mapValue.put("order_id", order_id);
//					mapValue.put("inst_id", inst_id);
//					mapValue.put("package_code", package_code);
//					mapValue.put("package_name", params.get("name"));
//					mapValue.put("product_code", package_code);
//					mapValue.put("element_code", package_element_code);
//					mapValue.put("element_type", EcsOrderConsts.ELEMENT_TYPE_D);
//					mapValue.put("element_name", params.get("name"));
//					mapValue.put("oper_type", EcsOrderConsts.BSS_OPER_TYPE_E);
//					mapValue.put("change_type", EcsOrderConsts.CHANGE_TYPE);
//					mapValue.put("biz_type", EcsOrderConsts.BIZ_TYPE_FWYH);
					mapValue.put("source_from", ManagerUtils.getSourceFrom());
					/**
					 * {biz_type=FWYH, package_inst_id=201412272928000149, source_from=ECS, element_code=5502000,
					 *  package_name=16元包300MB, oper_type=E, order_id=DG201412275905143133, element_type=D, 
					 *  change_type=0, element_name=16元包300MB, package_code=51002514, product_code=51002514, inst_id=DG201412275905143133}
					 */
					AttrPackageBusiRequest attrPackageBusiRequest = new AttrPackageBusiRequest();
					try {
						BeanUtils.copyProperties(attrPackageBusiRequest, mapValue);
						attrPackageBusiRequest.setOrder_id(order_id);
						attrPackageBusiRequest.setPackage_inst_id( this.baseDaoSupport.getSequences("seq_attr_zb_package"));
						attrPackageBusiRequest.setPackageName(package_name);
						attrPackageBusiRequest.setProductCode(package_code);
						attrPackageBusiRequest.setElementCode(package_element_code);
						attrPackageBusiRequest.setElementType(EcsOrderConsts.ELEMENT_TYPE_D);
						attrPackageBusiRequest.setElementName(Const.getStrValue(params, "name"));
						attrPackageBusiRequest.setOperType(EcsOrderConsts.BSS_OPER_TYPE_E);
						attrPackageBusiRequest.setPackageCode(package_code);
						attrPackageBusiRequest.setChageType(EcsOrderConsts.CHANGE_TYPE);
						attrPackageBusiRequest.setSku(packageCode);
						attrPackageBusiRequest.setBizType(EcsOrderConsts.BIZ_TYPE_FWYH);
						attrPackageBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
						attrPackageBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						attrPackageBusiRequest.store();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * 获取商品的参数
	 * @param sku
	 * @return
	 */
	private Map<String,String> getProductParams(String sku){
		Map<String,String> params = new HashMap();
		try {
			Goods goods = CommonDataFactory.getInstance().getGoodsBySku(sku);
			if (null != goods ) {
				params.put("name", goods.getName());
				params.put("paramsJson", goods.getParams());
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return params;
	}
	
}

package com.ztesoft.net.attr.hander;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.AttrPackageActivityBusiRequest;
import zte.net.ecsord.params.busi.req.AttrPackageBusiRequest;
import zte.net.ecsord.params.busi.req.AttrPackageSubProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderActivityBusiRequest;
import zte.net.ecsord.params.busi.req.OrderSpProductBusiRequest;
import zte.net.ecsord.params.busi.req.OrderSubProductBusiRequest;
import zte.net.ecsord.utils.SpecUtils;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import consts.ConstsCore;

/**
 * 
 * 合约业务包入库
 *
 */
public class BusinessPackageHandler extends BaseSupport implements IAttrHandler {

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo)
			throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();
		String inst_id = params.getInst_id(); // 数据实列ID（如订单扩展表ID、子订单扩展表ID、货品扩展表ID、物流ID、支付记录ID）
		Map orderAttrValues = params.getOrderAttrValues();
		String order_from = params.getOrder_from();
		String plat_type = (String) orderAttrValues.get(AttrConsts.PLAT_TYPE);
		boolean iszbmall = CommonDataFactory.getInstance().isZbOrder(plat_type);
		Map<String, String> spec = null;
		String type_id = null;
		//查询所有货品
		List<Goods> products = SpecUtils.getAllOrderProducts(order_id);
		//非总部订单
		if(!iszbmall && !EcsOrderConsts.ORDER_FROM_10003.equals(order_from)){
			List<Goods> activityPackages = new ArrayList<Goods>();
			String activity_seq = null;
			for(Goods product : products){
				type_id = product.getType_id();
				if(EcsOrderConsts.PRODUCT_TYPE_CONTRACT.equals(type_id)){//合约计划货品
					spec = SpecUtils.getProductSpecMap(product);
					activity_seq = baseDaoSupport.getSequences("S_ES_ORDER_ACTIVITY");
					OrderActivityBusiRequest orderActivityBusiRequest = new OrderActivityBusiRequest();
					orderActivityBusiRequest.setInst_id(activity_seq);
					orderActivityBusiRequest.setFuka_inst_id("0"); // 主卡默认全部填0
					orderActivityBusiRequest.setOrder_id(order_id);
					orderActivityBusiRequest.setActivity_type(spec.get("package_type"));	//合约类型
					orderActivityBusiRequest.setActivity_code("");
					orderActivityBusiRequest.setActivity_name("");
					orderActivityBusiRequest.setAct_prot_per(spec.get("package_limit"));	//合约期限
					orderActivityBusiRequest.setActivity_type_zhufu(EcsOrderConsts.ZB_CARD_TYPE_ZHU); // 主卡
					orderActivityBusiRequest.setSource_from(ManagerUtils.getSourceFrom());
					orderActivityBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					orderActivityBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderActivityBusiRequest.store();
				}else if(EcsOrderConsts.PRODUCT_TYPE_KEXUANBAO.equals(type_id) && 
						product.getCat_path().contains("|"+EcsOrderConsts.PRODUCT_CAT_HYYWB+"|")){//合约业务包
					activityPackages.add(product);
				}
			}
			//保存合约活动下自选包表
			if(StringUtils.isNotEmpty(activity_seq) && activityPackages.size() > 0){
				for(Goods p : activityPackages){
					spec = SpecUtils.getProductSpecMap(p);
					String package_inst_id = baseDaoSupport.getSequences("S_ES_ATTR_PACKAGE_ACTIVITY");
					AttrPackageActivityBusiRequest attrPackageActivityBusiRequest = new AttrPackageActivityBusiRequest();
					attrPackageActivityBusiRequest.setInst_id(package_inst_id);
					attrPackageActivityBusiRequest.setActivity_inst_id(activity_seq);
					attrPackageActivityBusiRequest.setOrder_id(order_id);
					attrPackageActivityBusiRequest.setPackage_code(spec.get("package_code"));
					attrPackageActivityBusiRequest.setPackage_name(spec.get("package_name"));
					attrPackageActivityBusiRequest.setElement_code(spec.get("package_element_code"));
					attrPackageActivityBusiRequest.setElement_type(EcsOrderConsts.ELEMENT_TYPE_D);
					attrPackageActivityBusiRequest.setElement_name(spec.get("element_name"));
					attrPackageActivityBusiRequest.setSource_from(ManagerUtils.getSourceFrom());
					attrPackageActivityBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					attrPackageActivityBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					attrPackageActivityBusiRequest.store();
				}
			}
		}
		
		//所有商城遍历货品，取附加产品、sp、套餐业务包
		//用来记录已经处理过的附加产品
		String cat_path = CommonDataFactory.getInstance().getGoodSpec(order_id, null, SpecConsts.CAT_PATH);
		if (cat_path.contains("|"+SpecConsts.TYPE_ID_20006+"|")) { // 增值业务类商品才解析入库(因为4G自由组合的套餐业务包是商品传过来的，不需要解析商品中的)
			Map<String,String> subProdInsertList = new HashMap<String,String>();
			for(Goods product : products){
				type_id = product.getType_id();
				spec = SpecUtils.getProductSpecMap(product);
				if(EcsOrderConsts.PRODUCT_TYPE_SUB_PRODUCT.equals(type_id)){//附加产品
					String cbss_product_code = spec.get("cbss_product_code");
					String sub_pro_inst_id = "";
					if(!subProdInsertList.containsKey(cbss_product_code)){
						sub_pro_inst_id = baseDaoSupport.getSequences("ES_ORDER_SUB_PRODCUT_SEQ");
						OrderSubProductBusiRequest subProd = new OrderSubProductBusiRequest();
						subProd.setSub_pro_inst_id(sub_pro_inst_id);
						subProd.setOrder_id(order_id);
						subProd.setProd_inst_id("0");
						subProd.setSub_pro_code(cbss_product_code);
						subProd.setSub_pro_name("");
						subProd.setSub_pro_desc("");
						subProd.setSub_prod_type(EcsOrderConsts.ZB_CARD_TYPE_ZHU);
						subProd.setSource_from(ManagerUtils.getSourceFrom());
						subProd.setDb_action(ConstsCore.DB_ACTION_INSERT);
						subProd.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						subProd.store();					
		
						//标识附加产品已处理，并保存ES_ORDER_SUB_PRODCUT表中的Sub_pro_inst_id
						subProdInsertList.put(cbss_product_code, sub_pro_inst_id);
					}else{
						sub_pro_inst_id = subProdInsertList.get(cbss_product_code);
					}
					
					//附加产品可选包
					String package_inst_id = baseDaoSupport.getSequences("ES_ATTR_PACKAGE_SubProd_SEQ");
					AttrPackageSubProdBusiRequest subProdPackage = new AttrPackageSubProdBusiRequest();
					subProdPackage.setPackage_inst_id(package_inst_id);
					subProdPackage.setOrder_id(order_id);
					subProdPackage.setSubProd_inst_id(sub_pro_inst_id);
					subProdPackage.setPackage_code(spec.get("package_code"));
					subProdPackage.setPackage_name(spec.get("package_name"));
					subProdPackage.setProduct_code(cbss_product_code);
					subProdPackage.setElement_code(spec.get("element_code"));
					subProdPackage.setElement_type("");
					subProdPackage.setElement_name(spec.get("element_name"));
					subProdPackage.setOper_type("");
					subProdPackage.setChange_type("");
					subProdPackage.setBiz_type("");
					subProdPackage.setSku(product.getSku());
					subProdPackage.setStatus("0");	//办理状态默认为0（未办理）
					subProdPackage.setSource_from(ManagerUtils.getSourceFrom());
					subProdPackage.setDb_action(ConstsCore.DB_ACTION_INSERT);
					subProdPackage.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					subProdPackage.store();
				}else if(EcsOrderConsts.PRODUCT_TYPE_SP_PRODUCT.equals(type_id)){//SP业务
					String sp_inst_id = baseDaoSupport.getSequences("S_ES_ORDER_SP_PRODUCT");
					OrderSpProductBusiRequest spProd = new OrderSpProductBusiRequest();
					spProd.setSp_inst_id(sp_inst_id);
					spProd.setOrder_id(order_id);
					spProd.setSp_id(spec.get("sp_id"));
					spProd.setSp_code(spec.get("sp_service_code"));
					spProd.setSp_provider(spec.get("sp_service_applyer"));
					spProd.setSp_name(spec.get("sp_product_name"));
					spProd.setAccept_platform(spec.get("accept_platform"));
					spProd.setStatus("0");
					spProd.setSku(product.getSku());
					spProd.setSource_from(ManagerUtils.getSourceFrom());
					spProd.setDb_action(ConstsCore.DB_ACTION_INSERT);
					spProd.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					spProd.store();
				}else if(EcsOrderConsts.PRODUCT_TYPE_KEXUANBAO.equals(type_id) && 
						product.getCat_path().contains("|"+EcsOrderConsts.PRODUCT_CAT_TCYWB+"|")){//套餐业务包
					AttrPackageBusiRequest attrPackageBusiRequest = new AttrPackageBusiRequest();
					attrPackageBusiRequest.setOrder_id(order_id);
					attrPackageBusiRequest.setInst_id(inst_id);
					attrPackageBusiRequest.setPackage_inst_id( this.baseDaoSupport.getSequences("seq_attr_zb_package"));
					attrPackageBusiRequest.setPackageName(spec.get("package_name"));
					attrPackageBusiRequest.setProductCode(spec.get("package_code"));
					attrPackageBusiRequest.setElementCode(spec.get("package_element_code"));
					attrPackageBusiRequest.setElementType(EcsOrderConsts.ELEMENT_TYPE_D);
					attrPackageBusiRequest.setElementName(spec.get("element_name"));
					attrPackageBusiRequest.setOperType(EcsOrderConsts.BSS_OPER_TYPE_E);
					attrPackageBusiRequest.setPackageCode(spec.get("package_code"));
					attrPackageBusiRequest.setChageType(EcsOrderConsts.CHANGE_TYPE);
					attrPackageBusiRequest.setBizType(EcsOrderConsts.BIZ_TYPE_FWYH);
					attrPackageBusiRequest.setSku(product.getSku());
					attrPackageBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					attrPackageBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					attrPackageBusiRequest.store();
				}
			}
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

}

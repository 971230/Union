package com.ztesoft.newstd.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.powerise.ibss.framework.Const;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsParam;
import com.ztesoft.net.mall.core.model.support.ParamGroup;
import com.ztesoft.newstd.common.CommonData;

import consts.ConstsCore;
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

public class AttrPackageHandler extends BaseSupport implements IAttrHandler {
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
		String inst_id = params.getInst_id(); // 数据实列ID（如订单扩展表ID、子订单扩展表ID、货品扩展表ID、物流ID、支付记录ID）
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		List<AttrPackageBusiRequest> attrPackageBusiRequests = (ArrayList<AttrPackageBusiRequest>) params
				.getBusiRequest();
		// orderAttrValues获取
		String zbpackages = (String) orderAttrValues.get(AttrConsts.ZB_PACKAGES);
		String wcfPackages = (String) orderAttrValues.get(AttrConsts.WCF_PACKAGES);

		List<Goods> products = CommonData.getData().getProducts();

		boolean isZbOrder = Boolean.parseBoolean(CommonData.getData().getIsZbOrder());
		boolean isTbOrder = Boolean.parseBoolean(CommonData.getData().getIsTbOrder());

		if (isZbOrder) {
			if (!StringUtils.isEmpty(zbpackages) && !"-1".equals(zbpackages)) {
				saveZBPackages(order_id, inst_id, zbpackages, attrPackageBusiRequests);
			}
		}

		if (!StringUtils.isEmpty(wcfPackages) && !"-1".equals(wcfPackages)) {
			saveWcfPackages(order_id, inst_id, wcfPackages, attrPackageBusiRequests);
		}
		// 淘宝可选包
		if (isTbOrder) {
			saveTaobaoPackages(products, order_id, inst_id, attrPackageBusiRequests);
		}

		return null;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		return null;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		return null;
	}

	private void saveZBPackages(String order_id, String inst_id, String zbpackages,
			List<AttrPackageBusiRequest> attrPackageBusiRequests) {
		String PackageCode = "";
		String PackageName = "";
		String ProductCode = "";
		String ElementCode = "";
		String ElementType = "";
		String ElementName = "";
		String OperType = EcsOrderConsts.BSS_OPER_TYPE_E;
		String ChageType = EcsOrderConsts.CHANGE_TYPE;
		String BizType = EcsOrderConsts.BIZ_TYPE_FWYH;
		CommHandler commHandler = new CommHandler();
		String source_from = commHandler.getSourceFrom();

		JSONArray jsonArray = JSONArray.fromObject(zbpackages);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
			PackageCode = jsonObject.get("packageCode") == null ? "" : jsonObject.get("packageCode").toString();
			PackageName = jsonObject.get("packageName") == null ? "" : jsonObject.get("packageName").toString();
			ProductCode = jsonObject.get("packageCode") == null ? "" : jsonObject.get("packageCode").toString();
			ElementCode = jsonObject.get("elementCode") == null ? "" : jsonObject.get("elementCode").toString();
			ElementType = jsonObject.get("elementType") == null ? "" : jsonObject.get("elementType").toString();
			ElementName = jsonObject.get("elementName") == null ? "" : jsonObject.get("elementName").toString();
			if (ListUtil.isEmpty(attrPackageBusiRequests))
				attrPackageBusiRequests = new ArrayList<AttrPackageBusiRequest>();
			Map mapValue = new HashMap();
			// mapValue.put("package_inst_id",
			// this.baseDaoSupport.getSequences("seq_attr_zb_package"));
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
			mapValue.put("source_from", source_from);
			// this.baseDaoSupport.insert("es_attr_package", mapValue);
			// long start2 = System.currentTimeMillis();
			try {
				AttrPackageBusiRequest attrPackageBusiRequest = new AttrPackageBusiRequest();
				BeanUtils.copyProperties(attrPackageBusiRequest, mapValue);
				attrPackageBusiRequest.setOrder_id(order_id);
				attrPackageBusiRequest.setPackage_inst_id(this.baseDaoSupport.getSequences("seq_attr_zb_package"));
				attrPackageBusiRequest.setPackageCode(PackageCode);
				attrPackageBusiRequest.setPackageName(PackageName);
				attrPackageBusiRequest.setProductCode(ProductCode);
				attrPackageBusiRequest.setElementCode(ElementCode);
				attrPackageBusiRequest.setElementType(ElementType);
				attrPackageBusiRequest.setElementName(ElementName);
				attrPackageBusiRequest.setOperType(OperType);
				attrPackageBusiRequest.setChageType(ChageType);
				attrPackageBusiRequest.setBizType(BizType);
				// BeanUtils.copyProperties(attrPackageBusiRequest, mapValue);
				attrPackageBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
				attrPackageBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				attrPackageBusiRequests.add(attrPackageBusiRequest);

			} catch (Exception e) {
				e.printStackTrace();
			}
			// long end2 = System.currentTimeMillis();
			// logger.info("ZbPackagesHandler订单属性转换其它时处理时间==========>："+(end2-start2));
		}
		CommonData.getData().getOrderTreeBusiRequest().getPackageBusiRequests().addAll(attrPackageBusiRequests);

	}

	private void saveTaobaoPackages(List<Goods> products, String order_id, String inst_id,
			List<AttrPackageBusiRequest> attrPackageBusiRequests) {
		// List<Goods> products = SpecUtils.getAllOrderProducts(order_id);
		if (products != null && products.size() > 0) {
			for (Goods product : products) {
				AttrPackageBusiRequest attrPackageBusiRequest = new AttrPackageBusiRequest();
				String type_id = product.getType_id();
				if (EcsOrderConsts.PRODUCT_TYPE_KEXUANBAO.equals(type_id)) {
					Map<String, String> spec = SpecUtils.getProductSpecMap(product);
					try {
						Map params = new HashMap();
						params.put("order_id", order_id);
						params.put("inst_id", inst_id);
						params.put("package_inst_id", this.baseDaoSupport.getSequences("seq_attr_zb_package"));
						params.put("packageName", spec.get("package_name"));
						params.put("productCode", spec.get("package_code"));
						params.put("elementType", EcsOrderConsts.ELEMENT_TYPE_D);
						params.put("elementName", spec.get("name"));
						params.put("operType", EcsOrderConsts.BSS_OPER_TYPE_E);
						params.put("packageCode", spec.get("package_code"));
						params.put("chageType", EcsOrderConsts.CHANGE_TYPE);
						params.put("bizType", EcsOrderConsts.BIZ_TYPE_FWYH);
						params.put("sku", product.getSku());
						BeanUtils.copyProperties(attrPackageBusiRequest, params);
						attrPackageBusiRequests.add(attrPackageBusiRequest);
						// this.baseDaoSupport.insert("es_attr_package", params);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void saveWcfPackages(String order_id, String inst_id, String wcfPackages,
			List<AttrPackageBusiRequest> attrPackageBusiRequests) {
		JSONArray jsonArray = JSONArray.fromObject(wcfPackages);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
			String packageCode = jsonObject.getString("packageCode");
			Map<String, String> params = getProductParams(packageCode);
			String package_code = "";
			String package_element_code = "";
			String package_name = null;
			String paramsJson = params.get("paramsJson");
			ParamGroup[] groups = JsonStrHandler.converFormString(paramsJson);
			if (groups != null && groups.length > 0) {
				for (ParamGroup group : groups) {
					List<GoodsParam> paramsList = group.getParamList();
					AttrPackageBusiRequest attrPackageBusiRequest = new AttrPackageBusiRequest();
					for (int j = 0; j < paramsList.size(); j++) {
						GoodsParam param = paramsList.get(j);
						if ("package_code".equalsIgnoreCase(param.getEname())
								&& "可选包编码".equalsIgnoreCase(param.getName())) {
							package_code = param.getValue();
						}
						if ("package_element_code".equalsIgnoreCase(param.getEname())
								&& "可选元素编码".equalsIgnoreCase(param.getName())) {
							package_element_code = param.getValue();
						}
						if ("package_name".equalsIgnoreCase(param.getEname())) {
							package_name = param.getValue();
						}
					}
					/**
					 * {biz_type=FWYH, package_inst_id=201412272928000149, source_from=ECS,
					 * element_code=5502000, package_name=16元包300MB, oper_type=E,
					 * order_id=DG201412275905143133, element_type=D, change_type=0,
					 * element_name=16元包300MB, package_code=51002514, product_code=51002514,
					 * inst_id=DG201412275905143133}
					 */
					try {
						Map param = new HashMap();
						param.put("order_id", order_id);
						param.put("package_inst_id", this.baseDaoSupport.getSequences("seq_attr_zb_package"));
						param.put("packageName", package_name);
						param.put("productCode", package_code);
						param.put("elementCode", package_element_code);
						param.put("elementType", EcsOrderConsts.ELEMENT_TYPE_D);
						param.put("elementName", Const.getStrValue(params, "name"));
						param.put("operType", EcsOrderConsts.BSS_OPER_TYPE_E);
						param.put("packageCode", package_code);
						param.put("chageType", EcsOrderConsts.CHANGE_TYPE);
						param.put("sku", packageCode);
						param.put("bizType", EcsOrderConsts.BIZ_TYPE_FWYH);
						// this.baseDaoSupport.insert("es_attr_package", param);
						BeanUtils.copyProperties(attrPackageBusiRequest, param);
						attrPackageBusiRequests.add(attrPackageBusiRequest);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 获取商品的参数
	 * 
	 * @param sku
	 * @return
	 */
	private Map<String, String> getProductParams(String sku) {
		Map<String, String> params = new HashMap();
		try {
			Goods goods = CommonDataFactory.getInstance().getGoodsBySku(sku);
			if (null != goods) {
				params.put("name", goods.getName());
				params.put("paramsJson", goods.getParams());
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return params;
	}

}

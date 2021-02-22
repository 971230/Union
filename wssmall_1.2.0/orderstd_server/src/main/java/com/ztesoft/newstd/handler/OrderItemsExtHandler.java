package com.ztesoft.newstd.handler;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.powerise.ibss.framework.Const;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.newstd.common.CommonData;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;

/**
 * @author licong
 *
 */
public class OrderItemsExtHandler implements IAttrHandler {

	private static CommHandler comm = new CommHandler();

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		OrderItemExtBusiRequest orderItemExtBusiRequest = (OrderItemExtBusiRequest) params.getBusiRequest();
		String goods_id = params.getGoods_id();
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值

		// 从orderAttrValues获取属性值
		String phone_owner_name = (String) orderAttrValues.get(AttrConsts.PHONE_OWNER_NAME);
		String bill_type = (String) orderAttrValues.get(AttrConsts.BILL_TYPE);
		String type_id = (String) orderAttrValues.get(AttrConsts.TYPE_ID);
		Map goodsSpec = CommonData.getData().getGoodsSpec();
		String cat_id = "";
		if (goodsSpec != null && !goodsSpec.isEmpty()) {
			cat_id = Const.getStrValue(goodsSpec, SpecConsts.CAT_ID);
		}

		Map offerSpec = CommonData.getData().getOfferSpec();
		if (MapUtils.isEmpty(offerSpec)) {
			offerSpec = CommonDataFactory.getInstance().getProductSpecMapByGoodsId(goods_id, SpecConsts.TYPE_ID_10002);
			CommonData.getData().setOfferSpec(offerSpec);
		}

		orderItemExtBusiRequest.setIs_change(orderAttrValues.get(AttrConsts.IS_CHANGE) == null ? "0"
				: orderAttrValues.get(AttrConsts.IS_CHANGE).toString());
		orderItemExtBusiRequest.setGoods_num(orderAttrValues.get(AttrConsts.GOODS_NUM) == null ? "0"
				: orderAttrValues.get(AttrConsts.GOODS_NUM).toString());
		orderItemExtBusiRequest.setPhone_num(orderAttrValues.get(AttrConsts.PHONE_NUM) == null ? ""
				: orderAttrValues.get(AttrConsts.PHONE_NUM).toString());

		/** ------------------------InventoryNameHandler---------------------------- **/
		String inventoryName = orderItemExtBusiRequest.getInventory_name();
		String inventory_name = (inventoryName == null || "".equals(inventoryName))
				? EcsOrderConsts.DEFAULT_INVENTORY_NAME
				: inventoryName;
		orderItemExtBusiRequest.setInventory_name(inventory_name);

		/** ------------------------GoodsTypeHandler---------------------------- **/

		if (StringUtils.isEmpty(type_id)) {
			// orderItemExtBusiRequest.setGoods_type(CommonDataFactory.getInstance().getGoodSpec(null,
			// goods_id, SpecConsts.TYPE_ID));
			orderItemExtBusiRequest.setGoods_type(Const.getStrValue(goodsSpec, SpecConsts.TYPE_ID));
		} else {
			orderItemExtBusiRequest.setGoods_type(type_id);
		}

		/** ------------------------collectionHandler---------------------------- **/
		String collection = orderItemExtBusiRequest.getCollection();
		if (EcsOrderConsts.BILL_TYPE_15.equals(bill_type)) {// 账户付费方式为托收
			orderItemExtBusiRequest.setCollection(EcsOrderConsts.IS_DEFAULT_VALUE);
		}
		if (StringUtils.isEmpty(collection))
			orderItemExtBusiRequest.setCollection(EcsOrderConsts.NO_DEFAULT_VALUE);

		/** ------------------------phoneDepositHandler---------------------------- **/
		if (StringUtils.isEmpty(orderItemExtBusiRequest.getPhone_deposit()))
			orderItemExtBusiRequest.setPhone_deposit(EcsOrderConsts.DEFAULT_FEE_ZERO);

		/**
		 * ------------------------invoicePrintTypeHandler----------------------------
		 **/
		// String invoice_print_type =
		// comm.getkeyByName(orderItemExtBusiRequest.getInvoice_print_type(),
		// orderItemExtBusiRequest.getInvoice_print_type(),
		// StypeConsts.INVOICE_PRINT_TYPE);
		// 优化
		String invoice_print_type = comm.getValueBySTypeId(orderItemExtBusiRequest.getInvoice_print_type(),
				orderItemExtBusiRequest.getInvoice_print_type(), StypeConsts.INVOICE_PRINT_TYPE);
		orderItemExtBusiRequest.setInvoice_print_type(invoice_print_type);
		/** ------------------------discountIdHandler---------------------------- **/
		String activity_list = (String) orderAttrValues.get(AttrConsts.ACTIVITY_LIST);
		if (!StringUtils.isEmpty(activity_list) && !EcsOrderConsts.ATTR_DEFAULT_VALUE.equals(activity_list)) {
			JSONArray jsonArray = JSONArray.fromObject(activity_list);
			if (jsonArray != null && jsonArray.size() > 0) {
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(0));
				orderItemExtBusiRequest.setDiscountid((String) jsonObject.get("activity_code"));
			}
		}

		/** ------------------------bssStatusHandler---------------------------- **/
		String goodsType = orderItemExtBusiRequest.getGoods_type();
		// 裸机和配件不需要业务办理
		if (EcsOrderConsts.GOODS_TYPE_PHONE.equals(goodsType) || EcsOrderConsts.GOODS_TYPE_ADJUNCT.equals(goodsType)) {
			orderItemExtBusiRequest.setBss_status(ZjEcsOrderConsts.BSS_STATUS_NOT_NEED);
		} else {
			orderItemExtBusiRequest.setBss_status(ZjEcsOrderConsts.BSS_STATUS_0);
		}

		/** ------------------------isCustomizedHandler---------------------------- **/
		String is_customized = null;
		// 合约机、裸机才区分定制机社会机
		if (EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(goodsType)
				|| EcsOrderConsts.GOODS_TYPE_PHONE.equals(goodsType)) {
			is_customized = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.IS_CUSTOMIZED, cat_id);
			if (StringUtils.isEmpty(is_customized)) {
				is_customized = EcsOrderConsts.NO_DEFAULT_VALUE;
			}
		}
		orderItemExtBusiRequest.setIs_customized(is_customized);

		/** ------------------------goodsCatIdHandler---------------------------- **/
		orderItemExtBusiRequest.setGoods_cat_id(cat_id);

		/** ------------------------bssTimeTypeHandler---------------------------- **/
		// String bssTimeType =
		// (String)CommonDataFactory.getInstance().getProductSpecMapByGoodsId(goods_id,
		// SpecConsts.TYPE_ID_10002).get(SpecConsts.BSS_TIME_TYPE);
		String bssTimeType = Const.getStrValue(offerSpec, SpecConsts.BSS_TIME_TYPE);
		// 若参数为空，默认值为非当月处理
		if (StringUtil.isEmpty(bssTimeType)) {
			bssTimeType = "0";
		}

		/** ------------------------invoiceTitleHandler---------------------------- **/
		// 如果发票打印方式为一次性打印，发票抬头默认机主姓名
		// 为空，则为买家姓名
		if (StringUtil.isEmpty(orderItemExtBusiRequest.getReserve8())) {
			// if(EcsOrderConsts.INVOICE_PRINT_TYPE_ONCE_1.equals(invoice_print_type)){
			orderItemExtBusiRequest.setReserve8(phone_owner_name);
			// }
		}

		orderItemExtBusiRequest.setBss_time_type(Integer.valueOf(bssTimeType));
		// dingxiaotao 6.7新增
		orderItemExtBusiRequest.setBrand_number(String.valueOf((goodsSpec.get("brand_code"))));
		// phoneNumHander 设置原值
		orderItemExtBusiRequest.setPhone_num((String) orderAttrValues.get(AttrConsts.PHONE_NUM));
		// superiorsBankCodeHander 设置原值
		orderItemExtBusiRequest.setSuperiors_bankcode((String) orderAttrValues.get(AttrConsts.SUPERIORS_BANK_CODE));
		// activeNoHandler 设置原值
		orderItemExtBusiRequest.setActive_no((String) orderAttrValues.get(AttrConsts.ACTIVE_NO));

		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setBusiRequest(orderItemExtBusiRequest);
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

package com.ztesoft.newstd.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.powerise.ibss.framework.Const;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
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
import zte.net.ecsord.params.busi.req.AttrGiftInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtvlBusiRequest;
import zte.net.ecsord.utils.SpecUtils;

public class OrderExtvtlHandler extends BaseSupport implements IAttrHandler {

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
		String goods_id = params.getGoods_id();// 商品ID
		String pro_goods_id = params.getPro_goods_id(); // 货品ID 只有货品单才有值
		String order_from = params.getOrder_from();
		String inst_id = params.getInst_id(); // 数据实列ID（如订单扩展表ID、子订单扩展表ID、货品扩展表ID、物流ID、支付记录ID）
		Map goodsSpec = CommonData.getData().getGoodsSpec();
		String goods_type = "";
		String cat_id = "";
		// String is_set="";
		String cat_path = "";
		String type_id = "";
		String goods_name = "";
		if (goodsSpec != null && !goodsSpec.isEmpty()) {
			goods_type = Const.getStrValue(goodsSpec, SpecConsts.TYPE_ID);
			cat_id = Const.getStrValue(goodsSpec, SpecConsts.CAT_ID);
			// is_set=Const.getStrValue(goodsSpec, SpecConsts.SWK_IS_SET);
			cat_path = Const.getStrValue(goodsSpec, SpecConsts.CAT_PATH);
			type_id = Const.getStrValue(goodsSpec, SpecConsts.TYPE_ID);
			goods_name = Const.getStrValue(goodsSpec, SpecConsts.GOODS_NAME);
		}

		List<Goods> products = CommonData.getData().getProducts();
		Map offperSpec = CommonData.getData().getOfferSpec();
		Map terminalSpec = CommonData.getData().getTerMinalSpec();
		// Map contactSpec=CommonData.getData().getContactSpec();
		Map liPinSpec = CommonData.getData().getLiPinSpec();
		if (products != null && products.size() > 0) {
			for (Goods prod : products) {
				if (SpecConsts.TYPE_ID_10002.equals(prod.getType_id()) && MapUtils.isEmpty(offperSpec)) {
					offperSpec = SpecUtils.getProductSpecMap(prod);
					CommonData.getData().setOfferSpec(offperSpec);
					break;
				} else if (SpecConsts.TYPE_ID_10000.equals(prod.getType_id()) && MapUtils.isEmpty(terminalSpec)) {
					terminalSpec = SpecUtils.getProductSpecMap(prod);
					CommonData.getData().setTerMinalSpec(terminalSpec);
					break;
				}
				// else if(SpecConsts.TYPE_ID_10001.equals(prod.getType_id())){
				// contactSpec = SpecUtils.getProductSpecMap(prod);
				// break ;
				// }
				else if (SpecConsts.TYPE_ID_10007.equals(prod.getType_id()) && MapUtils.isEmpty(liPinSpec)) {
					liPinSpec = SpecUtils.getProductSpecMap(prod);
					CommonData.getData().setLiPinSpec(liPinSpec);
					break;
				}
			}
		}

		Map orderAttrValues = params.getOrderAttrValues();
		// orderAttrValues中的属性
		String plat_type = (String) orderAttrValues.get(AttrConsts.PLAT_TYPE);
		String specificatio_nname = (String) orderAttrValues.get(AttrConsts.SPECIFICATION_NAME);
		String order_total_fee = (String) orderAttrValues.get(AttrConsts.ORDER_TOTAL_FEE);
		String order_real_fee = (String) orderAttrValues.get(AttrConsts.ORDER_REAL_FEE);
		String giftInfos = (String) orderAttrValues.get(AttrConsts.GIFT_INFOS);
		String ext_order_status = (String) orderAttrValues.get(AttrConsts.PLATFORM_STATUS);
		String vicecard_no = (String) orderAttrValues.get(AttrConsts.VICECARD_NO);
		String is_to4g = (String) orderAttrValues.get(AttrConsts.RESERVE1);
		String goodsAttInfo = (String) orderAttrValues.get(AttrConsts.ZB_FUKA_INFO);
		String first_fee_type = (String) orderAttrValues.get(AttrConsts.FIRST_FEE_TYPE);
		String active_sort = (String) orderAttrValues.get(AttrConsts.ACTIVE_SORT);
		String order_city_code = (String) orderAttrValues.get(AttrConsts.ORDER_CITY_CODE);
		String sending_type = (String) orderAttrValues.get(AttrConsts.SENDING_TYPE);
		String has_entity_prod = (String) orderAttrValues.get(AttrConsts.IS_PHISICS);
		String is_old = (String) orderAttrValues.get(AttrConsts.IS_OLD);
		// String order_froms =Const.getStrValue(orderAttrValues,
		// AttrConsts.ORDER_FROM);
		String activity_list = (String) orderAttrValues.get(AttrConsts.ACTIVITY_LIST);
		// String wcfPackages = (String) orderAttrValues.get(AttrConsts.WCF_PACKAGES);
		// String couponsList = (String) orderAttrValues.get(AttrConsts.COUPONS_LIST);
		String order_type = (String) orderAttrValues.get(AttrConsts.TYPE);
		String activity_code = Const.getStrValue(orderAttrValues, SpecConsts.ACTIVITY_TYPE);
		String plan_title = Const.getStrValue(offperSpec, SpecConsts.PLAN_TITLE);
		String out_tid = (String) orderAttrValues.get(AttrConsts.OUT_TID);

		boolean isTbOrder = Boolean.parseBoolean(CommonData.getData().getIsTbOrder());
		boolean isZbOrder = Boolean.parseBoolean(CommonData.getData().getIsZbOrder());
		CommHandler commHandler = new CommHandler();
		String source_from = commHandler.getSourceFrom();

		IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
		DcPublicInfoCacheProxy proxy = new DcPublicInfoCacheProxy(dcPublicInfoManager);

		OrderExtvlBusiRequest req;
		if (params.getBusiRequest() == null)
			req = new OrderExtvlBusiRequest();
		else
			req = (OrderExtvlBusiRequest) params.getBusiRequest();

		// (invoice_type, invoiceTypeHandler);
		if (EcsOrderConsts.DEFAULT_STR_SERO.equals(req.getInvoice_type())) {
			req.setInvoice_type("");
		}
		// (supply_id, supplierHandler);
		// Map
		// specMap_goods_type=CommonDataFactory.getInstance().getProductSpecMapByGoodsId(pro_goods_id,
		// goods_type);
		// String supply_id = Const.getStrValue(specMap_goods_type,
		// SpecConsts.SUPPLY_ID);
		String supply_id = Const.getStrValue(liPinSpec, SpecConsts.SUPPLY_ID);
		if (!StringUtils.isEmpty(supply_id)) {
			req.setSupply_id(supply_id);
		}
		// (bill_mail_type, billMailTypeHandler);
		if (StringUtil.isEmpty(req.getBill_mail_type()))
			req.setBill_mail_type(EcsOrderConsts.BILL_MALL_TYPE_00);
		// (freeze_tran_no, freezeTranNoHander); 设置原值
		req.setFreeze_tran_no((String) orderAttrValues.get(AttrConsts.FREEZE_TRAN_NO));
		//ADD BY SGF
		if(!StringUtil.isEmpty((String) orderAttrValues.get("sale_mode"))){
		    req.setSale_mode((String) orderAttrValues.get("sale_mode"));
		}
		if(!StringUtil.isEmpty((String) orderAttrValues.get("object_esn"))){
	        req.setObject_esn((String) orderAttrValues.get("object_esn"));
        }
		if(!StringUtil.isEmpty((String) orderAttrValues.get("object_id"))){
	        req.setObject_id((String) orderAttrValues.get("object_id"));
        }
		if(!StringUtil.isEmpty((String) orderAttrValues.get("share_svc_num"))){
            req.setShare_svc_num((String) orderAttrValues.get("share_svc_num"));
        }
		if(!StringUtil.isEmpty((String) orderAttrValues.get("activity_name"))){
            req.setActivity_name((String) orderAttrValues.get("activity_name"));
        }
		if(!StringUtil.isEmpty((String) orderAttrValues.get("market_user_id"))){
            req.setMarket_user_id((String) orderAttrValues.get("market_user_id"));
        }
		if(!StringUtil.isEmpty((String) orderAttrValues.get("seed_user_id"))){
            req.setSeed_user_id((String) orderAttrValues.get("seed_user_id"));
        }
		req.setBuyer_message((String)orderAttrValues.get("buyer_message"));
	    req.setBuyer_name((String)orderAttrValues.get("buyer_name"));
		// (bss_order_type, bssOrderTypeHander); 设置原值
		req.setBss_order_type((String) orderAttrValues.get(AttrConsts.BSS_ORDER_TYPE));
		// (bss_operator, bssOperAttrHander); 设置原值
		req.setBss_operator((String) orderAttrValues.get(AttrConsts.BSS_OPERATOR));
		// (liang_price, liangPriceHandler);
		// 没有使用暂时屏蔽 xiao.ruidan 20180528
		req.setLiang_price(EcsOrderConsts.DEFAULT_FEE_ZERO);
		// if(isZbOrder){
		// req.setLiang_price(EcsOrderConsts.DEFAULT_FEE_ZERO);
		// } else if(StringUtils.isEmpty(req.getLiang_price())){
		// if(!StringUtils.isEmpty(phone_num)){
		// req.setLiang_price(CommonDataFactory.getInstance().getNumberSpec(phone_num,
		// SpecConsts.NUMERO_DEPOSIT));
		// }
		// }
		// (group_code, groupCodeHader); 设置原值
		req.setGroup_code((String) orderAttrValues.get(AttrConsts.GROUP_CODE));
		// (GoodsName, goodsNameHandler);
		if (StringUtils.isEmpty(req.getGoodsname())) {
			// req.setGoodsname(CommonDataFactory.getInstance().getGoodSpec(goods_id==null?order_id:null,
			// goods_id, SpecConsts.GOODS_NAME));
			req.setGoodsname(goods_name);
		}
		if (!StringUtils.isEmpty(req.getGoodsname())) {
			req.setGoodsname(req.getGoodsname().replaceAll("# #d#", ""));
		}
		// (bill_type, billTypeHandler);
		// 账单寄送方式，00：不寄送，01：寄送，默认不寄送
		if (StringUtils.isEmpty(req.getBill_type())) {
			req.setBill_type(EcsOrderConsts.BILL_TYPE_10);
		}

		// (discountname, discountNameHandler);
		if (!isZbOrder && StringUtils.isEmpty(req.getDiscountname())) {

			if (!StringUtils.isEmpty(activity_code)) {
				req.setDiscountname(
						CommonDataFactory.getInstance().getActivitySpec(activity_code, SpecConsts.ACTIVITY_NAME));
			}
		}
		// (zbpackages, zbPackagesHandler);attrPackageBusiRequest
		req.setZbpackages(null);
		// (bank_user, bankUserHander); 设置原值
		req.setBank_user((String) orderAttrValues.get(AttrConsts.BANK_USER));
		// (ICCID, ICCIDAttrHandler); 设置原值
		req.setIccid((String) orderAttrValues.get(AttrConsts.ICCID));
		// (isgifts, isGiftsHandler);
		if (StringUtils.isEmpty(req.getIsgifts())) {
			req.setIsgifts(CommonDataFactory.getInstance().isGift(pro_goods_id));
		}
		// (discountInfos, discountInfosHandler);attrDiscountInfoBusiRequest
		req.setDiscountinfos(null);
		// (giftInfos, giftInfosHandler);attrGiftInfoBusiRequest
		req.setGiftinfos(null);
		// (activity_list, activityListHandler);attrDiscountInfoBusiRequest
		// 迁移至attrDiscountInfoBusiRequest
		req.setActivity_list(null);
		// (oss_operator, ossOperAttrHander); 设置原值
		req.setOss_operator((String) orderAttrValues.get(AttrConsts.OSS_OPERATOR));
		// (CustomerType, customerTypeHandler);

		
		String customertype = req.getCustomertype();
		if(StringUtils.isBlank(customertype) || "null".equals(customertype)){
			customertype = String.valueOf(orderAttrValues.get("CustomerType"));
		}
		
		if(StringUtils.isBlank(customertype) || "null".equals(customertype)){
			req.setCustomertype(EcsOrderConsts.CUSTOMER_CUST_TYPE_GRKH);
		}else{
			req.setCustomertype(customertype);
		}


		// (net_type, netTypeHandler);
		String net_type = Const.getStrValue(offperSpec, SpecConsts.NET_TYPE);
		req.setNet_type(net_type);
		// (reserve6, reserve6Hander); 设置原值
		req.setReserve6((String) orderAttrValues.get(AttrConsts.RESERVE6));
		// (group_name, groupNameHander); 设置原值
		req.setGroup_name((String) orderAttrValues.get(AttrConsts.GROUP_NAME));
		// (reliefpres_flag, reliefPresFlagHandler)
		if (StringUtil.isEmpty(req.getReliefpres_flag())
				|| EcsOrderConsts.NO_DEFAULT_VALUE.equals(req.getReliefpres_flag()))
			req.setReliefpres_flag(EcsOrderConsts.RELIEFPRES_FLAG_NO);
		else if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(req.getReliefpres_flag()))
			req.setReliefpres_flag(EcsOrderConsts.RELIEFPRES_FLAG_YES);
		// (specificatio_nname, specificationNameHandler);
		if (!isZbOrder && StringUtils.isEmpty(specificatio_nname)) {
			// req.setSpecificatio_nname(CommonDataFactory.getInstance().getProductSpec(order_id,
			// SpecConsts.TYPE_ID_10000, null, SpecConsts.MODEL_NAME));
			req.setSpecificatio_nname(Const.getStrValue(terminalSpec, SpecConsts.MODEL_NAME));
		}

		// (regist_type, registTypeHandler);
		if (!isZbOrder) {
			req.setRegist_type(EcsOrderConsts.REGIST_TYPE_MALL);
		}
        
		// (order_disfee, orderDisfeeHandler);
		if (StringUtil.isEmpty(req.getOrder_disfee()))
			req.setOrder_disfee("0");
		// (guarantor, guarantorHandler);
		if (StringUtils.isEmpty(req.getGuarantor()))
			req.setGuarantor(EcsOrderConsts.DEFAULT_GUARANTOR);
		// (disfee_select, disfeeSelectHandler); 设置原值
		req.setDisfee_select((String) orderAttrValues.get(AttrConsts.DISFEE_SELECT));
		// (package_sale, packageSaleHandler);
		if (isTbOrder) {
			req.setPackage_sale(EcsOrderConsts.PACKAGE_SALE_NO);
		}

		// (discountValue, discountValueHandler);
		Double order_totalfee = StringUtils.isEmpty(order_total_fee) ? 0 : Double.valueOf(order_total_fee);
		// 订单实收总价
		Double order_realfee = StringUtils.isEmpty(order_real_fee) ? 0 : Double.valueOf(order_real_fee);
		Double discount_value = order_totalfee - order_realfee;
		req.setDiscountvalue(discount_value.toString());

		// (out_plan_id_bss, outPlanIdBssHandler);
		if (StringUtils.isEmpty(req.getOut_plan_id_bss())) {
			// req.setOut_plan_id_bss(CommonDataFactory.getInstance().getProductSpec(order_id,
			// SpecConsts.TYPE_ID_10002, SpecConsts.OUT_PLAN_ID_BSS));
			req.setOut_plan_id_bss(Const.getStrValue(offperSpec, SpecConsts.OUT_PLAN_ID_BSS));
		}

		// (specification_code, specificationCodeHandler);
		if (!isZbOrder && StringUtils.isEmpty(req.getSpecification_code())) {
			// req.setSpecification_code(CommonDataFactory.getInstance().getProductSpec(order_id,
			// SpecConsts.TYPE_ID_10000, null, SpecConsts.MODEL_CODE));
			req.setSpecification_code(Const.getStrValue(terminalSpec, SpecConsts.MODEL_CODE));
		}

		// (is_group_contract, isGroupContractHandler);
		if (StringUtils.isEmpty(req.getIs_group_contract())) {
			req.setIs_group_contract(CommonDataFactory.getInstance().isGroupContract(order_from, goods_id));
		}

		// (bank_code, bankCodeHander); 设置原值
		req.setBank_code((String) orderAttrValues.get(AttrConsts.BANK_CODE));

		// (wcfPackages, mallPackagesHandler);attrPackageBusiRequest
		// 迁移到attrPackageHandler
		// if(!StringUtils.isEmpty(wcfPackages) && !"-1".equals(wcfPackages) ){
		// saveWcfPackages(order_id, inst_id, wcfPackages);
		// }
		// //淘宝可选包
		// if(isTbOrder){
		// saveTaobaoPackages(products,order_id,inst_id);
		// }
		req.setWcfpackages(null);
		// (baidu_id, baiDuIdHander); 设置原值
		req.setBaidu_id((String) orderAttrValues.get(AttrConsts.BAIDU_ID));
		// (order_join_category, orderJoinCategoryHandler);
		if (isZbOrder) {
			order_from = EcsOrderConsts.ORDER_FROM_10003;
		}
		req.setOrder_join_category(
				CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.SOURCE_TYPE, order_from));
		// (phone_owner_name, phoneOwnerNameHander); 设置原值
		req.setPhone_owner_name((String) orderAttrValues.get(AttrConsts.PHONE_OWNER_NAME));
		// (terminalInfo, terminalHandler); AttrTmResourceInfoBusiRequest
		// (reserve5, reserve5Hander); 设置原值
		req.setReserve5((String) orderAttrValues.get(AttrConsts.RESERVE5));
		// (freeze_free, freezeFreeHander); 设置原值
		req.setFreeze_free((String) orderAttrValues.get(AttrConsts.FREEZE_FREE));
		// (bank_account, bankAccountHander); 设置原值
		req.setBank_account((String) orderAttrValues.get(AttrConsts.BANK_ACCOUNT));
        req.setSex((String) orderAttrValues.get("cert_sex"));
        req.setBirthday((String) orderAttrValues.get("cust_birthday"));
		// (source_type, sourceTypeHandler);
		if (!isZbOrder) {
			req.setSource_type(EcsOrderConsts.SOURCE_TYPE_MALL);
		}
		// (out_plan_id_ess, outPlanIdEssHandler);
		if (StringUtils.isEmpty(req.getOut_plan_id_ess())) {
			// req.setOut_plan_id_ess(CommonDataFactory.getInstance().getProductSpec(order_id,
			// SpecConsts.TYPE_ID_10002, SpecConsts.ESS_CODE));
			req.setOut_plan_id_ess(Const.getStrValue(offperSpec, SpecConsts.ESS_CODE));
		}

		// (card_species, cardSpeciesHandler);
		if (isZbOrder) {
			if (!StringUtils.isEmpty(giftInfos)) {
				JSONArray jsonArray = JSONArray.fromObject(giftInfos);
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
					String giftDesc = jsonObject.getString("giftDesc");
					if (!StringUtils.isEmpty(giftDesc) && giftDesc.indexOf("卡") != -1) {
						req.setCard_species(EcsOrderConsts.CARD_SPECIES_01);
					} else {
						req.setCard_species(EcsOrderConsts.CARD_SPECIES_02);
					}
				}
			}
		} else {
			if (!StringUtils.isEmpty(activity_list)) {
				JSONArray jsonArray = JSONArray.fromObject(activity_list);
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
					String gift_list = jsonObject.getString("gift_list");
					if (!StringUtils.isEmpty(gift_list)) {
						JSONArray giftArray = JSONArray.fromObject(gift_list);
						for (int j = 0; j < giftArray.size(); j++) {
							JSONObject giftObject = JSONObject.fromObject(giftArray.get(j));
							String gift_id = giftObject.getString("gift_id");
							Goods goods = CommonDataFactory.getInstance().getGoodsBySku(gift_id);
							if (goods != null) {
								String catId = goods.getCat_id();
								String is_lipin = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.IS_LIPIN,
										cat_id);
								if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_lipin)) {
									if (EcsOrderConsts.PRODUCT_CAT_ID_1.equals(catId)) {
										req.setCard_species(EcsOrderConsts.CARD_SPECIES_01);
									} else {
										req.setCard_species(EcsOrderConsts.CARD_SPECIES_02);
									}
								}
							}
						}
					}
				}
			}
		}

		// (plan_title, planTitleHandler);
		// String plan_title = CommonDataFactory.getInstance().getProductSpec(order_id,
		// SpecConsts.TYPE_ID_10002, SpecConsts.PLAN_TITLE);
		if (StringUtils.isEmpty(plan_title)) {
			plan_title = req.getPlan_title();
		}
		req.setPlan_title(plan_title);

		// (couponslist, couponslistHandler); attrDiscountInfoBusiRequest
		// 迁移到attrDiscountInfoHandler

		// (business_package, businessPackageHandler); orderActivityBusiRequest
		savePackage(products, cat_path, order_id, inst_id, orderAttrValues, order_from, goods_id, isZbOrder,
				source_from);
		// (hsOrderInfo, hsOrderHandler);直接插入表
		// (acceptanceForm, acceptanceFormHandler);orderItemAptPrt

		// (is_physics, isPhysicsHandler);
		String has_entity_product = CommonDataFactory.getInstance().hasEntityProduct(order_id, goods_type);
		if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(has_entity_product)) {// 如果没有实物货品,判断赠品中是否有实物
			// 号卡、新用户有实物货品
			if (EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(goods_type)
					&& EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_old)) {
				has_entity_product = EcsOrderConsts.IS_DEFAULT_VALUE;
			}
			// 判断赠品是否有实物
			List<AttrGiftInfoBusiRequest> gifts = CommonData.getData().getOrderTreeBusiRequest()
					.getGiftInfoBusiRequests();
			for (AttrGiftInfoBusiRequest gift : gifts) {
				if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(gift.getIs_virtual())) {
					has_entity_product = EcsOrderConsts.IS_DEFAULT_VALUE;
					break;
				}
			}
		}
		req.setIs_physics(has_entity_product);

		// (pro_brand, prodBrandHandler);
		if (StringUtils.isEmpty(req.getPro_brand())) {
			if (EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(type_id)) {
				// net_type = CommonDataFactory.getInstance().getProductSpec(order_id,
				// SpecConsts.TYPE_ID_10002, SpecConsts.NET_TYPE);
				net_type = Const.getStrValue(offperSpec, SpecConsts.NET_TYPE);
				if (!StringUtils.isEmpty(net_type)) {
					if (EcsOrderConsts.NET_TYPE_2G.equalsIgnoreCase(net_type)) {
						req.setPro_brand(EcsOrderConsts.PROD_BRAND_2GPH);
					} else if (EcsOrderConsts.NET_TYPE_3G.equalsIgnoreCase(net_type)) {
						req.setPro_brand(EcsOrderConsts.PROD_BRAND_3GPH);
					} else if (EcsOrderConsts.NET_TYPE_4G.equalsIgnoreCase(net_type)) {
						req.setPro_brand(EcsOrderConsts.PROD_BRAND_4GPH);
					}
				}
				if (StringUtils.isEmpty(req.getPro_brand())) {
					req.setPro_brand(
							CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.PRODUCT_BRAND, cat_id));
				}
			}
		}

		// (platform_status_name, platformStatusNameHandler);
		if (!StringUtils.isEmpty(vicecard_no)) {
			req.setPlatform_status_name(commHandler.getNameBykey(req.getPlatform_status(), ext_order_status,
					StypeConsts.DIC_ORDER_EXT_STATUS));
		} else {
			req.setPlatform_status_name(req.getPlatform_status());
		}

		// (special_busi_type, specialBusiTypeHandler);
		String special_busi_type = EcsOrderConsts.SPECIAL_BUSI_TYPE_00;// 默认为非特殊业务类型
		// 非总部订单赋值为[]
		if (!isZbOrder) {
			goodsAttInfo = "[]";
		}
		JSONArray jsonArray = JSONArray.fromObject(goodsAttInfo);
		// String cat_id = CommonDataFactory.getInstance().getGoodSpec(order_id,
		// goods_id, SpecConsts.CAT_ID);
		if ((!StringUtils.isEmpty(vicecard_no)) /* ZX add 2015-12-25 start */
				|| (isZbOrder && (StringUtils.isNotBlank(goodsAttInfo) && jsonArray.size() > 0)
						&& (!EcsOrderConsts.GOODS_CAT_ID_4G_GXZH.equals(cat_id)))/* ZX add 2015-12-25 end */) {// 如果副卡号码不为空，则认为是副卡业务
			special_busi_type = EcsOrderConsts.SPECIAL_BUSI_TYPE_02;
		} else if (EcsOrderConsts.IS_TO4G.equals(is_to4g)) {// 2G/3G转4G业务
			special_busi_type = EcsOrderConsts.SPECIAL_BUSI_TYPE_04;
		}
		if (EcsOrderConsts.ORDER_FROM_10012.equals(order_from)) {// 天猫分销直接取原值
			special_busi_type = req.getSpecial_busi_type();
		}
		req.setSpecial_busi_type(special_busi_type);

		// (first_fee_type_name, firstFeeTypeNameHandler);
		if (!StringUtils.isEmpty(first_fee_type)) {
			String first_fee_type_name = null;
			first_fee_type_name = commHandler.getNameBykey(req.getFirst_fee_type_name(), first_fee_type,
					StypeConsts.OFFER_EFF_TYPE);
			req.setFirst_fee_type_name(first_fee_type_name);
		}

		// (discountrange, disacountRangeHandler);
		if (!isZbOrder && StringUtils.isEmpty(req.getDiscountrange())) {
			if (!StringUtils.isEmpty(activity_code)) {
				req.setDiscountrange(
						CommonDataFactory.getInstance().getActivitySpec(activity_code, SpecConsts.DISACOUNT_RANGE));
			}
		}
		// (sim_type, simTypeHandler);
		req.setSim_type("1");// 只有白卡
		// (card_type, cardTypeHandler);
		if (StringUtils.isEmpty(vicecard_no)) {
			req.setCard_type(EcsOrderConsts.CARD_TYPE_ZHU);
		} else {
			req.setCard_type(EcsOrderConsts.CARD_TYPE_FU);
		}

		// (active_sort_name, activeSortNameHandler);
		if (!StringUtils.isEmpty(active_sort)) {
			String active_sort_name = null;
			active_sort_name = commHandler.getNameBykey(req.getActive_sort_name(), active_sort,
					StypeConsts.ACTIVE_SORT);
			req.setActive_sort_name(active_sort_name);
		}

		// (shipping_company_name, shippingCompanyNameHandler);
		// shipping_company字段从deleveryhandler执行后获取
		String shipping_company = CommonData.getData().getOrderTreeBusiRequest().getOrderDeliveryBusiRequests().get(0)
				.getShipping_company();
		if (!StringUtils.isEmpty(shipping_company)) {
			List<Map> logiCompanies = proxy.getLogiCompanyList();
			for (Map company : logiCompanies) {
				if (shipping_company.equals(Const.getStrValue(company, "id"))) {
					req.setShipping_company_name(Const.getStrValue(company, "name"));
					break;
				}
			}
		}
		//取默认值落库需要进行更改操作，针对于cb业务 本次不提交代码
		// (development_code, developmentCodeHandler);
		String development_code = req.getDevelopment_code();//makeup初始转化时候是 bss是发展点编码  cbss发展人编码
		String development_point_code = req.getDevelopment_point_code();
		// 商品分类是省内号卡
		// String type_id_new = CommonDataFactory.getInstance().getGoodSpec(null,
		// goods_id, "type_id");
	
	    ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
	    String DEVELOP_CAT_ID = cacheUtil.getConfigInfo("DEVELOP_CAT_ID");
		if (!StringUtil.isEmpty(DEVELOP_CAT_ID) && !StringUtil.isEmpty(cat_id) && DEVELOP_CAT_ID.contains(cat_id+",")) {//小类排除特定的值信息
		    
        }else{
            if (EcsOrderConsts.GOODS_TYPE_ID_HKSN.equals(type_id) || EcsOrderConsts.GOODS_TYPE_ID_JKHK.equals(type_id) || EcsOrderConsts.GOODS_TYPE_FEI_HK.equals(type_id)) {
                //development_code = req.getDevelopment_code();
            } else {
                if (StringUtils.isEmpty(development_code) || EcsOrderConsts.ATTR_DEFAULT_VALUE.equals(development_code)) {
                    development_code = proxy.getDevelopmentCode(order_city_code, null);
                    req.setDevelopment_code(development_code);
                    req.setDevelop_code_new(development_code);//新值入库    发展人编码
                    if (StringUtils.isEmpty(development_point_code)|| EcsOrderConsts.ATTR_DEFAULT_VALUE.equals(development_point_code)) {
                        development_point_code = proxy.getDevelopmentPointCode(order_city_code, null);
                        req.setDevelopment_point_code(development_point_code);
                        req.setDevelop_point_code_new(development_point_code);//--新值入库 发展点编码
                    }
                }
            }
        }
		//req.setDevelopment_code(development_code);
		//req.setDevelop_code_new(development_code);//新值入库    发展人编码
		//req.setDevelopment_point_code(development_point_code);
		//req.setDevelop_point_code_new(development_point_code);//--新值入库 发展点编码
	    String net_region = (String)orderAttrValues.get("net_region");
		if(StringUtil.isEmpty(net_region)){
		    net_region = EcsOrderConsts.EMPTY_STR_VALUE;
		}
		// 没有使用暂时屏蔽 xiao.ruidan 20180528
		// if(!com.ztesoft.common.util.StringUtils.isEmpty(phone_num))
		// net_region = CommonDataFactory.getInstance().getNumberSpec(phone_num,
		// SpecConsts.NUMERO_REGION_ID);
		// if(com.ztesoft.common.util.StringUtils.isEmpty(net_region)){
		// net_region = EcsOrderConsts.DEFAULT_NET_REGION;
		// }
		req.setNet_region(net_region);
		// (is_liang, isLiangHandler);
		String is_liang = EcsOrderConsts.NO_DEFAULT_VALUE;
		
		req.setIs_liang(is_liang);
		// (is_send_goods, isSendGoodsHandler);
		String is_send_goods = EcsOrderConsts.NO_DEFAULT_VALUE;
		// 配送方式不是自提、有实物货品，需要进行发货
		if (!EcsOrderConsts.SENDING_TYPE_ZT.equals(sending_type)
				&& EcsOrderConsts.IS_DEFAULT_VALUE.equals(has_entity_prod)) {
			is_send_goods = has_entity_prod;
		}
		req.setIs_send_goods(is_send_goods);
		// (proactivity, proActivityHandler); 直接入库
		if (!StringUtils.isEmpty(out_tid) && !StringUtils.isEmpty(req.getProactivity())) {
			String SQL_SEARCH = "select a.* from es_goods a  join es_pmt_goods b on a.goods_id=b.goods_id join es_promotion c on c.pmt_id=b.pmt_id"
					+ " and c.pmts_id='gift' and c.promotion_type='010' "
					+ " join es_promotion_activity d on d.id=c.pmta_id where a.type_id='10010'  and d.pmt_code=? and a.source_from=?";
			String SQL_INSERT = "insert into es_gd_zhuanduibao(order_id,out_tid,pmt_code,goods_id) values(?,?,?,?)";
			String[] pmt_codes = req.getProactivity().split(",");
			for (String pmt_code : pmt_codes) {
				List<Map> result = this.baseDaoSupport.queryForList(SQL_SEARCH, pmt_code, source_from);
				for (Map item : result) {
					this.baseDaoSupport.execute(SQL_INSERT, order_id, out_tid, pmt_code, item.get("goods_id"));
				}
			}
		}

		// (virtual_pro_num, virtualProNumHandler);OrderItemExtvtlBusiRequest
		if (EcsOrderConsts.ORDER_FROM_10053.equals(order_from) && EcsOrderConsts.ORDER_TYPE_07.equals(order_type)) {// order_from属性处理器在此之后，此时以order_from作为判断条件风险点为:总部、淘宝订单来源未转为内部编码
			JSONArray array = null;
			try {
				array = JSONArray.fromObject(req.getVirtual_pro_num());
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("virtual_pro_num属性解析失败");
			}
			if (null != array) {
				// 设置同一sku入库次数,生成序列用
				Map<String, String> mapSku = new HashMap<String, String>();
				for (int i = 0; i < array.size(); i++) {
					try {
						JSONObject object = (JSONObject) array.get(i);
						int num = Integer.parseInt(object.getString("virtualGoodsNum"));// 商品数量
						String goodsId = object.getString("virtualGoodsId");// 商品id
						Double goodsPrice = Double.parseDouble(object.getString("virtualGoodsPrice"));// 商品价格

						// Map mobileTerminalSpec =
						// CommonDataFactory.getInstance().getProductSpecMapByGoodsId(goodsId,
						// SpecConsts.TYPE_ID_10000);//货品手机信息
						Map mobileTerminalSpec = terminalSpec;// 货品手机信息

						// String goods_name = CommonDataFactory.getInstance().getGoodSpec(null,
						// goodsId, SpecConsts.GOODS_NAME);
						String product_id = (String) mobileTerminalSpec.get(SpecConsts.PROD_GOODS_ID);// 取es_goods表的goods_id
						String goods_type_local = SpecConsts.TYPE_ID_10000;
						String machine_type_code = (String) mobileTerminalSpec.get(SpecConsts.GOODS_SN);
						String machine_type_name = (String) mobileTerminalSpec.get(SpecConsts.MODEL_NAME);
						String resources_brand_code = (String) mobileTerminalSpec.get(SpecConsts.BRAND_CODE);
						String resources_brand_name = (String) mobileTerminalSpec.get(SpecConsts.BRAND_NAME);
						String resources_model_code = (String) mobileTerminalSpec.get(SpecConsts.MODEL_CODE);
						String resources_model_name = (String) mobileTerminalSpec.get(SpecConsts.MODEL_NAME);
						String resources_color = (String) mobileTerminalSpec.get(SpecConsts.COLOR_NAME);
						String sku = (String) mobileTerminalSpec.get(SpecConsts.SKU);
						Map mapValue = new HashMap();
						mapValue.put("order_id", order_id);// 内部订单号
						mapValue.put("goods_id", goodsId);// 商品ID
						mapValue.put("goods_name", goods_name);// 商品名称
						mapValue.put("product_id", product_id);// 货品ID
						mapValue.put("goods_type", goods_type_local);// 货品类型（例如10000：手机;10006：配件）
						mapValue.put("machine_type_code", machine_type_code);// 机型编码
						mapValue.put("machine_type_name", machine_type_name);// 机型名称
						mapValue.put("resources_brand_code", resources_brand_code);// 品牌编码
						mapValue.put("resources_brand_name", resources_brand_name);// 品牌名称
						mapValue.put("resources_model_code", resources_model_code);// 型号编码
						mapValue.put("resources_model_name", resources_model_name);// 型号名称
						mapValue.put("resources_color", resources_color);// 颜色
						mapValue.put("sku", sku);// 货品sku
						mapValue.put("source_from", source_from);
						mapValue.put("goods_price", goodsPrice);// 商城推送订单时，NewMallServlet已将厘转为元

						if (!mapSku.containsKey(sku)) {
							mapSku.put(sku, "1");
						}
						for (int j = 0; j < num; j++) {// 保存商品(实际上是货品)信息
							// 生成6位序列
							String zline = mapSku.get(sku);
							while (zline.length() < 6)
								zline += "0";
							mapValue.put("zline", zline);
							// OrderItemExtvtlBusiRequest request = new OrderItemExtvtlBusiRequest();

							mapValue.put("items_id", this.baseDaoSupport.getSequences("s_es_order_items_extvtl"));// 序列号

							// BeanUtils.copyProperties(request, mapValue);
							this.baseDaoSupport.insert("es_order_items_extvtl", mapValue);
							// request.setDb_action(ConstsCore.DB_ACTION_INSERT);
							// request.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							// request.store();
							// 序列加1
							int nums = Integer.parseInt(mapSku.get(sku)) + 1;
							mapSku.put(sku, nums + "");
						}
					} catch (Exception e) {
						e.printStackTrace();
						continue;// 商品数量或商品价格不是数字,无法入库
					}
				}
			}
		}
		req.setVirtual_pro_num("1");
		// (RHbroadinfo, rHBroadinfoHandler);OrderAdslBusiRequest
		// (subprodinfo, orderSubProdInfoHandler);orderSubProdInfoBusiRequest
		// (subotherinfo, orderSubOtherInfoHandler);orderSubOtherInfoBusiRequest
		// (broadinfo, broadinfoHandler);OrderAdslBusiRequest
		// (is_write_card, isWriteCardHandler);
		if (EcsOrderConsts.SIM_TYPE_BK.equals(req.getSim_type())) {
			req.setIs_write_card(EcsOrderConsts.IS_WRITE_CARD_YES);
		}
		// (development_name, developmentNameHandler);
		if (!StringUtil.isEmpty(DEVELOP_CAT_ID) && !StringUtil.isEmpty(cat_id) && DEVELOP_CAT_ID.contains(cat_id)) {
        }else{
            if (!EcsOrderConsts.GOODS_TYPE_ID_HKSN.equals(goods_type) && !EcsOrderConsts.GOODS_TYPE_ID_JKHK.equals(goods_type) && !EcsOrderConsts.GOODS_TYPE_FEI_HK.equals(goods_type)) {
                if ((StringUtils.isEmpty(req.getDevelopment_name()) || EcsOrderConsts.ATTR_DEFAULT_VALUE.equals(req.getDevelopment_name())) && StringUtils.isNotEmpty(req.getDevelopment_code())) {
                    req.setDevelopment_name(proxy.getDevelopmentName(req.getDevelopment_code(), null));
                    if (!StringUtils.isEmpty(req.getDevelopment_name())) {
                        req.setDevelopment_name(req.getDevelopment_name());
                        req.setDevelop_name_new(req.getDevelopment_name());
                    }
                }
            }
        }

		String is_easy_account = EcsOrderConsts.IS_EASY_ACCOUNT_NO;
		req.setIs_easy_account(is_easy_account);
		// (pro_realfee, proRealFeeHander); 设置原值
		req.setPro_realfee((String) orderAttrValues.get(AttrConsts.PRO_REALFEE));
		// (out_package_id, outPackageIdHandler);
		String p_code = CommonDataFactory.getInstance().getPCode(goods_id);
		if (StringUtils.isEmpty(p_code)) {
			p_code = EcsOrderConsts.DEFAULT_P_CODE;
		}
		req.setOut_package_id(p_code);
		// (platform_status, platformStatusHandler);
		List<Map<String, String>> datas = CommonDataFactory.getInstance()
				.getDictRelationListData(StypeConsts.PLATFORM_STATUS);
		String platform_status = null;
		for (Map data : datas) {
			if (data.get("other_system").equals(order_from)
					&& data.get("other_field_value").equals(req.getPlatform_status())) {
				platform_status = data.get("field_value").toString();
				break;
			}
		}
		if (StringUtils.isEmpty(platform_status)) {
			platform_status = EcsOrderConsts.EXT_ORDER_STATUS_02;
		}
		req.setPlatform_status(platform_status);
		// (phoneInfo, phoneInfoHandler);orderPhoneInfoBusiRequest

		// itemid ITEM_PROD_INST_ID,delivery_ID字段从commonData取
		req.setItem_id(CommonData.getData().getOrderTreeBusiRequest().getOrderItemBusiRequests().get(0).getItem_id());
		req.setItem_prod_inst_id(CommonData.getData().getOrderTreeBusiRequest().getOrderItemBusiRequests().get(0)
				.getOrderItemProdBusiRequests().get(0).getItem_prod_inst_id());
		req.setDelivery_id(
				CommonData.getData().getOrderTreeBusiRequest().getOrderDeliveryBusiRequests().get(0).getDelivery_id());
		CommonData.getData().setOrderExtvlBusiRequest(req);
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

	/**
	 * 合约包入库
	 * 
	 * @param order_id
	 * @param inst_id
	 * @param orderAttrValues
	 * @param order_from
	 */
	public void savePackage(List<Goods> products, String cat_path, String order_id, String inst_id, Map orderAttrValues,
			String order_from, String goods_id, boolean isZbOrder, String source_from) {
		// String plat_type = (String) orderAttrValues.get(AttrConsts.PLAT_TYPE);
		Map<String, String> spec = null;
		String type_id = null;
		// 查询所有货品
		// List<Goods> products = SpecUtils.getAllOrderProducts(order_id);
		// List<Goods> products = SpecUtils.getAllOrderProductsByGoodsId(goods_id);

		// 非总部订单
		if (!isZbOrder && !EcsOrderConsts.ORDER_FROM_10003.equals(order_from)) {
			List<Goods> activityPackages = new ArrayList<Goods>();
			String activity_seq = null;
			for (Goods product : products) {
				type_id = product.getType_id();
				if (EcsOrderConsts.PRODUCT_TYPE_CONTRACT.equals(type_id)) {// 合约计划货品
					spec = SpecUtils.getProductSpecMap(product);
					activity_seq = baseDaoSupport.getSequences("S_ES_ORDER_ACTIVITY");
					//
					Map params = new HashMap();
					params.put("inst_id", activity_seq);
					params.put("fuka_inst_id", "0");
					params.put("order_id", order_id);
					params.put("activity_type", spec.get("package_type"));
					params.put("activity_code", "");
					params.put("activity_name", "");
					params.put("act_prot_per", spec.get("package_limit"));
					params.put("activity_type_zhufu", EcsOrderConsts.ZB_CARD_TYPE_ZHU);
					params.put("source_from", source_from);
					this.baseDaoSupport.insert("es_order_activity", params);

				} else if (EcsOrderConsts.PRODUCT_TYPE_KEXUANBAO.equals(type_id)
						&& product.getCat_path().contains("|" + EcsOrderConsts.PRODUCT_CAT_HYYWB + "|")) {// 合约业务包
					activityPackages.add(product);
				}
			}
			// 保存合约活动下自选包表
			if (StringUtils.isNotEmpty(activity_seq) && activityPackages.size() > 0) {
				for (Goods p : activityPackages) {
					spec = SpecUtils.getProductSpecMap(p);
					String package_inst_id = baseDaoSupport.getSequences("S_ES_ATTR_PACKAGE_ACTIVITY");

					Map params = new HashMap();
					params.put("inst_id", package_inst_id);
					params.put("activity_inst_id", activity_seq);
					params.put("order_id", order_id);
					params.put("package_code", spec.get("package_code"));
					params.put("package_name", spec.get("package_name"));
					params.put("element_code", spec.get("package_element_code"));
					params.put("element_type", EcsOrderConsts.ELEMENT_TYPE_D);
					params.put("element_name", spec.get("element_name"));
					params.put("source_from", source_from);
					this.baseDaoSupport.insert("es_attr_package_activity", params);
				}
			}
		}

		// 所有商城遍历货品，取附加产品、sp、套餐业务包
		// 用来记录已经处理过的附加产品
		// String cat_path = CommonDataFactory.getInstance().getGoodSpec(order_id,
		// goods_id, SpecConsts.CAT_PATH);

		if (cat_path.contains("|" + SpecConsts.TYPE_ID_20006 + "|")) { // 增值业务类商品才解析入库(因为4G自由组合的套餐业务包是商品传过来的，不需要解析商品中的)
			Map<String, String> subProdInsertList = new HashMap<String, String>();
			for (Goods product : products) {
				type_id = product.getType_id();
				spec = SpecUtils.getProductSpecMap(product);
				if (EcsOrderConsts.PRODUCT_TYPE_SUB_PRODUCT.equals(type_id)) {// 附加产品
					String cbss_product_code = spec.get("cbss_product_code");
					String sub_pro_inst_id = "";
					if (!subProdInsertList.containsKey(cbss_product_code)) {
						sub_pro_inst_id = baseDaoSupport.getSequences("ES_ORDER_SUB_PRODCUT_SEQ");
						// OrderSubProductBusiRequest subProd = new OrderSubProductBusiRequest();
						// subProd.setSub_pro_inst_id(sub_pro_inst_id);
						// subProd.setOrder_id(order_id);
						// subProd.setProd_inst_id("0");
						// subProd.setSub_pro_code(cbss_product_code);
						// subProd.setSub_pro_name("");
						// subProd.setSub_pro_desc("");
						// subProd.setSub_prod_type(EcsOrderConsts.ZB_CARD_TYPE_ZHU);
						// subProd.setSource_from(source_from);
						// subProd.setDb_action(ConstsCore.DB_ACTION_INSERT);
						// subProd.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						// subProd.store();

						Map params = new HashMap();
						params.put("sub_prod_inst_id", sub_pro_inst_id);
						params.put("order_id", order_id);
						params.put("prod_inst_id", "0");
						params.put("sub_pro_code", cbss_product_code);
						params.put("sub_pro_name", "");
						params.put("sub_pro_desc", "");
						params.put("sub_pro_type", EcsOrderConsts.ZB_CARD_TYPE_ZHU);
						params.put("source_from", source_from);
						this.baseDaoSupport.insert("ES_ORDER_SUB_PRODCUT", params);
						// 标识附加产品已处理，并保存ES_ORDER_SUB_PRODCUT表中的Sub_pro_inst_id
						subProdInsertList.put(cbss_product_code, sub_pro_inst_id);
					} else {
						sub_pro_inst_id = subProdInsertList.get(cbss_product_code);
					}

					// 附加产品可选包
					String package_inst_id = baseDaoSupport.getSequences("ES_ATTR_PACKAGE_SubProd_SEQ");
					Map params = new HashMap();
					params.put("package_inst_id", package_inst_id);
					params.put("order_id", order_id);
					params.put("subProd_inst_id", sub_pro_inst_id);
					params.put("package_code", spec.get("package_code"));
					params.put("package_name", spec.get("package_name"));
					params.put("product_code", spec.get("cbss_product_code"));
					params.put("element_code", spec.get("package_element_code"));
					params.put("element_type", "");
					params.put("element_name", spec.get("element_name"));
					params.put("oper_type", "");
					params.put("change_type", "");
					params.put("biz_type", "");
					params.put("sku", product.getSku());
					params.put("status", "0");
					params.put("source_from", source_from);
					this.baseDaoSupport.insert("ES_ATTR_PACKAGE_SubProd", params);
				} else if (EcsOrderConsts.PRODUCT_TYPE_SP_PRODUCT.equals(type_id)) {// SP业务
					String sp_inst_id = baseDaoSupport.getSequences("S_ES_ORDER_SP_PRODUCT");
					Map params = new HashMap();
					params.put("sp_inst_id", sp_inst_id);
					params.put("order_id", order_id);
					params.put("sp_id", spec.get("sp_id"));
					params.put("sp_code", spec.get("sp_service_code"));
					params.put("sp_provider", spec.get("sp_service_applyer"));
					params.put("sp_name", spec.get("sp_product_name"));
					params.put("accept_platform", spec.get("accept_platform"));
					params.put("sku", product.getSku());
					params.put("status", "0");
					params.put("source_from", source_from);
					this.baseDaoSupport.insert("es_order_sp_product", params);
				} else if (EcsOrderConsts.PRODUCT_TYPE_KEXUANBAO.equals(type_id)
						&& product.getCat_path().contains("|" + EcsOrderConsts.PRODUCT_CAT_TCYWB + "|")) {// 套餐业务包
					Map params = new HashMap();
					params.put("order_id", order_id);
					params.put("inst_id", inst_id);
					params.put("package_inst_id", this.baseDaoSupport.getSequences("seq_attr_zb_package"));
					params.put("packageName", spec.get("package_name"));
					params.put("productCode", spec.get("package_code"));
					params.put("elementCode", spec.get("package_element_code"));
					params.put("elementType", EcsOrderConsts.ELEMENT_TYPE_D);
					params.put("elementName", spec.get("element_name"));
					params.put("operType", EcsOrderConsts.BSS_OPER_TYPE_E);
					params.put("packageCode", spec.get("package_code"));
					params.put("chageType", EcsOrderConsts.CHANGE_TYPE);
					params.put("bizType", EcsOrderConsts.BIZ_TYPE_FWYH);
					params.put("sku", product.getSku());
					this.baseDaoSupport.insert("es_attr_package", params);
				}
			}
		}

	}

}

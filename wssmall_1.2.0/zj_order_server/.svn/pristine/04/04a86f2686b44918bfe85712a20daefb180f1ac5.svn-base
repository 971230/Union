package com.ztesoft.net.attr.hander;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import zte.net.ecsord.params.busi.req.AttrGiftInfoBusiRequest;
import zte.net.ecsord.params.busi.req.AttrGiftParamBusiRequest;
import zte.net.ecsord.params.busi.req.AttrPackageBusiRequest;
import zte.net.ecsord.params.busi.req.AttrPackageSubProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderSpProductBusiRequest;
import zte.net.ecsord.params.busi.req.OrderSubProductBusiRequest;
import zte.net.ecsord.utils.SpecUtils;
import zte.params.goods.req.SkuQueryReq;
import zte.params.goods.resp.SkuQueryResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsParam;
import com.ztesoft.net.mall.core.model.support.ParamGroup;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.AttrDef;

import consts.ConstsCore;
/**
 * 赠品信息
 * @author Administrator
 *
 */
public class GiftInfosHandler extends BaseSupport implements IAttrHandler {
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
		
		//****這個要加上總部商城,淘寶商城(RESERVE9)的判斷,處理總部商城傳的禮品,還有商品系統配置活動的禮品
		String plat_type = (String) orderAttrValues.get(AttrConsts.PLAT_TYPE);
		boolean isTbOrder = CommonDataFactory.getInstance().isTbOrder(order_from);//是否淘宝订单,默认否
		if(CommonDataFactory.getInstance().isZbOrder(plat_type)){//总部赠品
			String giftInfos = (String) orderAttrValues.get(AttrConsts.GIFT_INFOS);
			if(!StringUtils.isEmpty(giftInfos) && !"-1".equals(giftInfos)){
				saveZBGiftInfo(order_id, inst_id, giftInfos);
			}
			if(StringUtils.isNotEmpty((String)orderAttrValues.get(AttrConsts.RESERVE9))){
				//公共方法,ES_ATTR_GIFT_INFO
				saveECSGift(order_id, inst_id, (String)orderAttrValues.get(AttrConsts.RESERVE9));
			}
		}
		else if(isTbOrder/*EcsOrderConsts.ORDER_FROM_10001.equals(order_from)*/){//淘宝赠品
			String service_remarks = (String) orderAttrValues.get("service_remarks");
			saveTbGiftInfo(order_id, inst_id, service_remarks);
			//公共方法ES_ATTR_GIFT_INFO
			saveECSGift(order_id, inst_id, (String)orderAttrValues.get(AttrConsts.RESERVE9));
		}
		else if(EcsOrderConsts.ORDER_FROM_10002.equals(order_from)){
			String service_remarks = (String) orderAttrValues.get("service_remarks");
			saveLocalMallGiftInfo(order_id, inst_id, service_remarks);
		}else{//商城赠品
			String activity_list = (String) orderAttrValues.get(AttrConsts.ACTIVITY_LIST);
			saveGiftInfo(order_id, inst_id, activity_list);
		}
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(null);
		return resp;
	}
	
	public void saveTbGiftInfo(String order_id, String inst_id, String service_remarks){
		String[] remarks = service_remarks.replaceAll("^##", "").split(",");
		if(remarks == null || remarks.length==0) return ;
		for(String remark : remarks){
			if(!StringUtils.isEmpty(remark)){
				String[] remarkInfo = remark.split("=");
				if(remarkInfo == null && remarkInfo.length!=2) continue;
				String name = remarkInfo[0];
				String giftStr = remarkInfo[1];
				if(EcsOrderConsts.TB_GIFT_REMARK_PREFIX.equals(name) && StringUtils.isNotEmpty(giftStr)){
					String[] giftInfo = giftStr.split("\\|");
					for (int i = 0 ; i < giftInfo.length ; i++) {
						String giftName = giftInfo[i];
						//2015-07-01去掉"總部-"前綴和"淘寶-"前綴
						Map lpMap = CommonDataFactory.getInstance().getVProductByName(/*"总部-"+*/giftName);
						if(lpMap == null){
							lpMap = CommonDataFactory.getInstance().getVProductByName(/*"淘宝-"+*/giftName);
						}
						if(lpMap == null || lpMap.size() == 0) continue;
						Map giftMap = new HashMap();
						String gift_id = this.baseDaoSupport.getSequences("seq_attr_gift_info");
						String type_id = lpMap.get("type_id").toString();
						String cat_id = lpMap.get("cat_name").toString();
						String params = lpMap.get("params").toString();
						String is_yewubao = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.ISYEWUBAO, cat_id);
						if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_yewubao)){
							type_id = EcsOrderConsts.PRODUCT_TYPE_KEXUANBAO;
						}
						giftMap.put("gift_inst_id", gift_id);
						giftMap.put("order_id", order_id);
						giftMap.put("inst_id", inst_id);
						giftMap.put("sku_id", lpMap.get("skua"));
						giftMap.put("goods_name", giftName);
						giftMap.put("goods_type", type_id);
						giftMap.put("goods_category", lpMap.get("cat_name"));
						giftMap.put("goods_desc", giftName);
						
						String isvirtualgift = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.IS_VIRTUAL_GIFT, lpMap.get("cat_name").toString());
						if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(isvirtualgift)) {
							giftMap.put("is_virtual", "1");
						}
						else{
							giftMap.put("is_virtual", "0");
						}
						
						String giftType = EcsOrderConsts.PRODUCT_CAT_ID_1.equals(cat_id)?EcsOrderConsts.CARD_SPECIES_01:EcsOrderConsts.PRODUCT_CAT_ID_5.equals(cat_id)?EcsOrderConsts.CARD_SPECIES_03:EcsOrderConsts.CARD_SPECIES_02;
						giftMap.put("is_gift", "1");
						giftMap.put("sku_num", "1");
						giftMap.put("sku_fee", "[]");
						giftMap.put("gift_id", gift_id);
						giftMap.put("gift_value", "0");
						giftMap.put("gift_unit", "01");
						giftMap.put("gift_type", giftType);
						giftMap.put("gift_sku", lpMap.get("giftTypeId"));
						giftMap.put("gift_brand", lpMap.get("giftbrand"));
						giftMap.put("gift_color", lpMap.get("giftColor"));
						giftMap.put("gift_model", lpMap.get("giftmodel"));
						giftMap.put("is_process", "0");
						giftMap.put("process_type", "");
						giftMap.put("process_desc", "");
						giftMap.put("source_from", ManagerUtils.getSourceFrom());
						
						try {
							ParamGroup[] paramAr = JsonStrHandler.converFormString(params);
							if (null != paramAr) {
								for (int k = 0; k < paramAr.length; k++) {
									ParamGroup group = paramAr[k];
									List<GoodsParam> paramList = group.getParamList();
									for(GoodsParam param : paramList){
										if("is_cbss".equals(param.getEname())){//如果需要BSS业务办理，设置赠品BSS状态为1【待办理】
											if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(param.getValue())){
												giftMap.put("bss_status", "1");
											}
										}
									}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.info(e.getMessage(), e);
						}
						AttrGiftInfoBusiRequest attrGiftInfoBusiRequest = new AttrGiftInfoBusiRequest();
						try {
							BeanUtils.copyProperties(attrGiftInfoBusiRequest, giftMap);
							attrGiftInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
							attrGiftInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							attrGiftInfoBusiRequest.store();
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				}
			}
		}
	}
	
	public void saveLocalMallGiftInfo(String order_id, String inst_id, String service_remarks){
		String[] remarks = service_remarks.toLowerCase().split("x");
		if(remarks == null || remarks.length==0) return ;
		String giftName = remarks[0];
		String giftNum = remarks[1];
		Map lpMap = CommonDataFactory.getInstance().getVProductByName(/*"总部-"+*/giftName);
		if(lpMap == null){
			lpMap = CommonDataFactory.getInstance().getVProductByName(/*"淘宝-"+*/giftName);
		}
		if(lpMap == null || lpMap.size() == 0) return;
		Map giftMap = new HashMap();
		String gift_id = this.baseDaoSupport.getSequences("seq_attr_gift_info");
		String type_id = lpMap.get("type_id").toString();
		String cat_id = lpMap.get("cat_name").toString();
		String params = lpMap.get("params").toString();
		String is_yewubao = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.ISYEWUBAO, cat_id);
		if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_yewubao)){
			type_id = EcsOrderConsts.PRODUCT_TYPE_KEXUANBAO;
		}
		giftMap.put("gift_inst_id", gift_id);
		giftMap.put("order_id", order_id);
		giftMap.put("inst_id", inst_id);
		giftMap.put("sku_id", lpMap.get("skua"));
		giftMap.put("goods_name", giftName);
		giftMap.put("goods_type", type_id);
		giftMap.put("goods_category", lpMap.get("cat_name"));
		giftMap.put("goods_desc", giftName);
		
		String isvirtualgift = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.IS_VIRTUAL_GIFT, lpMap.get("cat_name").toString());
		if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(isvirtualgift)) {
			giftMap.put("is_virtual", "1");
		}
		else{
			giftMap.put("is_virtual", "0");
		}
		
		String giftType = EcsOrderConsts.PRODUCT_CAT_ID_1.equals(cat_id)?EcsOrderConsts.CARD_SPECIES_01:EcsOrderConsts.PRODUCT_CAT_ID_5.equals(cat_id)?EcsOrderConsts.CARD_SPECIES_03:EcsOrderConsts.CARD_SPECIES_02;
		giftMap.put("is_gift", "1");
		giftMap.put("sku_num", giftNum);
		giftMap.put("sku_fee", "[]");
		giftMap.put("gift_id", gift_id);
		giftMap.put("gift_value", "0");
		giftMap.put("gift_unit", "01");
		giftMap.put("gift_type", giftType);
		giftMap.put("gift_sku", lpMap.get("giftTypeId"));
		giftMap.put("gift_brand", lpMap.get("giftbrand"));
		giftMap.put("gift_color", lpMap.get("giftColor"));
		giftMap.put("gift_model", lpMap.get("giftmodel"));
		giftMap.put("is_process", "0");
		giftMap.put("process_type", "");
		giftMap.put("process_desc", "");
		giftMap.put("source_from", ManagerUtils.getSourceFrom());
		
		try {
			ParamGroup[] paramAr = JsonStrHandler.converFormString(params);
			if (null != paramAr) {
				for (int k = 0; k < paramAr.length; k++) {
					ParamGroup group = paramAr[k];
					List<GoodsParam> paramList = group.getParamList();
					for(GoodsParam param : paramList){
						if("is_cbss".equals(param.getEname())){//如果需要BSS业务办理，设置赠品BSS状态为1【待办理】
							if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(param.getValue())){
								giftMap.put("bss_status", "1");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage(), e);
		}
		AttrGiftInfoBusiRequest attrGiftInfoBusiRequest = new AttrGiftInfoBusiRequest();
		try {
			BeanUtils.copyProperties(attrGiftInfoBusiRequest, giftMap);
			attrGiftInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
			attrGiftInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			attrGiftInfoBusiRequest.store();
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}
	
	/**
	 * 获取总部赠品信息
	 * @return
	 */
	private void saveZBGiftInfo(String order_id, String inst_id, String giftInfos){
		try {
		if(!StringUtils.isEmpty(giftInfos) && !"[]".equals(giftInfos)){
			try{
				JSONArray jsonArray = JSONArray.fromObject(giftInfos);
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
					String giftID = jsonObject.get("giftID")==null?"":jsonObject.get("giftID").toString();
					String giftType = jsonObject.get("giftType")==null?"":jsonObject.get("giftType").toString();
					String giftValue = jsonObject.get("giftValue")==null?"":jsonObject.get("giftValue").toString();
					String giftName = jsonObject.get("giftName")==null?"":jsonObject.get("giftName").toString();
					String giftDesc = jsonObject.get("giftDesc")==null?"":jsonObject.get("giftDesc").toString();
					String giftNum = jsonObject.get("giftNum")==null?"":jsonObject.get("giftNum").toString();
					String giftBrand = jsonObject.get("giftBrand")==null?"":jsonObject.get("giftBrand").toString();
					String params = null;
					String goodsCategory = null;
					String goodsType = SpecConsts.TYPE_ID_10007; //礼品
					if(StringUtils.isEmpty(giftValue))
						giftValue = EcsOrderConsts.DEFAULT_STR_SERO;
					if(StringUtils.isEmpty(giftID))
						giftID = EcsOrderConsts.DEFAULT_GIFT_ID;
					if (EcsOrderConsts.CARD_SPECIES_01.equals(giftType)) {
						goodsCategory = EcsOrderConsts.PRODUCT_CAT_ID_1;
					}else {
						goodsCategory = EcsOrderConsts.PRODUCT_CAT_ID_2;
					}
					//总部订单下发时，对其中的礼品将礼品名称截取掉前缀“总部-”后，找到商品系统中同名的礼品货品sku，传送给订单系统。
					//2016-07-01取消掉對"總部-"的添加,直接使用總部訂單傳過來的贈品名稱在商品系統中查找
					//如果匹配不到货品，则透传总部的sku
					String sku_id = null;
					if (null != giftName && !"".equals(giftName)) {
						//根据货品名查找sku
						//giftName = "总部-" + giftName;
						Map lpMap = CommonDataFactory.getInstance().getVProductByName(giftName);
						if (null != lpMap && lpMap.size() > 0) {
							sku_id = lpMap.get("skua")==null?"":lpMap.get("skua").toString();	//sku
							goodsType = lpMap.get("type_id")==null?"":lpMap.get("type_id").toString();	//货品大类
							goodsCategory = lpMap.get("cat_name")==null?"":lpMap.get("cat_name").toString();	//货品小类
							if(StringUtils.isEmpty(giftBrand)){
								giftBrand = lpMap.get("brand_name")==null?"":lpMap.get("brand_name").toString();
							}
							params = lpMap.get("params").toString();
						}
					}
					giftType = EcsOrderConsts.PRODUCT_CAT_ID_1.equals(goodsCategory)?EcsOrderConsts.CARD_SPECIES_01:EcsOrderConsts.PRODUCT_CAT_ID_5.equals(goodsCategory)?EcsOrderConsts.CARD_SPECIES_03:EcsOrderConsts.CARD_SPECIES_02;
					Map giftMap = new HashMap();
					String gift_id = this.baseDaoSupport.getSequences("seq_attr_gift_info");
					giftMap.put("gift_inst_id", gift_id);
					giftMap.put("order_id", order_id);
					giftMap.put("inst_id", inst_id);
					giftMap.put("sku_id", sku_id);
					giftMap.put("goods_name", giftName);
					giftMap.put("goods_type", goodsType);
					giftMap.put("goods_category", goodsCategory);
					giftMap.put("goods_desc", giftName);
					String isvirtualgift = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.IS_VIRTUAL_GIFT, goodsCategory);
					if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(isvirtualgift)) {
						giftMap.put("is_virtual", "1");
					}
					else{
						giftMap.put("is_virtual", "0");
					}
					giftMap.put("is_gift", "1");
					giftMap.put("sku_num", giftNum);
					giftMap.put("sku_fee", "[]");
					giftMap.put("gift_id", giftID);
					giftMap.put("gift_value", giftValue);
					giftMap.put("gift_unit", "01");
					giftMap.put("gift_type", giftType);
					giftMap.put("gift_desc", giftDesc);
					giftMap.put("gift_sku", jsonObject.get("giftTypeId"));
					giftMap.put("gift_brand", giftBrand);
					giftMap.put("gift_color", jsonObject.get("giftColor"));
					giftMap.put("gift_model", jsonObject.get("giftModel"));
					giftMap.put("is_process", "0");
					giftMap.put("process_type", "");
					giftMap.put("process_desc", "");
					giftMap.put("source_from", ManagerUtils.getSourceFrom());
					
					try {
						ParamGroup[] paramAr = JsonStrHandler.converFormString(params);
						if (null != paramAr) {
							for (int k = 0; k < paramAr.length; k++) {
								ParamGroup group = paramAr[k];
								List<GoodsParam> paramList = group.getParamList();
								for(GoodsParam param : paramList){
									if("is_cbss".equals(param.getEname())){//如果需要BSS业务办理，设置赠品BSS状态为1【待办理】
										if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(param.getValue())){
											giftMap.put("bss_status", "1");
										}
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.info(e.getMessage(), e);
					}
					
					AttrGiftInfoBusiRequest attrGiftInfoBusiRequest = new AttrGiftInfoBusiRequest();
					BeanUtils.copyProperties(attrGiftInfoBusiRequest, giftMap);
					attrGiftInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					attrGiftInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					attrGiftInfoBusiRequest.store();
					
//					this.baseDaoSupport.insert("es_attr_gift_info", giftMap);
					
					Map paramMap = new HashMap();
					paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
					paramMap.put("gift_inst_id", gift_id);
					paramMap.put("param_code", "gift_type");
					paramMap.put("param_name", "赠品类型");
					paramMap.put("has_value_code", "0");
					paramMap.put("param_value_code", "");
					paramMap.put("param_value", goodsType);
					paramMap.put("order_id", order_id);
					paramMap.put("source_from", ManagerUtils.getSourceFrom());
					AttrGiftParamBusiRequest attrGiftParamBusiRequest = new AttrGiftParamBusiRequest();
					BeanUtils.copyProperties(attrGiftParamBusiRequest, paramMap);
					attrGiftParamBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					attrGiftParamBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					attrGiftParamBusiRequest.store();
//					this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
					
					//赠品编码
					paramMap = new HashMap();
					paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
					paramMap.put("gift_inst_id", gift_id);
					paramMap.put("param_code", "gift_id");
					paramMap.put("param_name", "赠品编码");
					paramMap.put("has_value_code", "0");
					paramMap.put("param_value_code", "");
					paramMap.put("param_value", giftID);
					paramMap.put("order_id", order_id);
					paramMap.put("source_from", ManagerUtils.getSourceFrom());
					
					attrGiftParamBusiRequest = new AttrGiftParamBusiRequest();
					BeanUtils.copyProperties(attrGiftParamBusiRequest, paramMap);
					attrGiftParamBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					attrGiftParamBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					attrGiftParamBusiRequest.store();
					
//					this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
					//赠品名称
					paramMap = new HashMap();
					paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
					paramMap.put("gift_inst_id", gift_id);
					paramMap.put("param_code", "gift_name");
					paramMap.put("param_name", "赠品名称");
					paramMap.put("has_value_code", "0");
					paramMap.put("param_value_code", "");
					paramMap.put("param_value", giftName);
					paramMap.put("order_id", order_id);
					paramMap.put("source_from", ManagerUtils.getSourceFrom());
					
					attrGiftParamBusiRequest = new AttrGiftParamBusiRequest();
					BeanUtils.copyProperties(attrGiftParamBusiRequest, paramMap);
					attrGiftParamBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					attrGiftParamBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					attrGiftParamBusiRequest.store();
					
//					this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
					//赠品面值
					paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
					paramMap.put("gift_inst_id", gift_id);
					paramMap.put("param_code", "gift_value");
					paramMap.put("param_name", "赠品面值");
					paramMap.put("has_value_code", "0");
					paramMap.put("param_value_code", "");
					paramMap.put("param_value", giftValue);
					paramMap.put("order_id", order_id);
					paramMap.put("source_from", ManagerUtils.getSourceFrom());
					
					attrGiftParamBusiRequest = new AttrGiftParamBusiRequest();
					BeanUtils.copyProperties(attrGiftParamBusiRequest, paramMap);
					attrGiftParamBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					attrGiftParamBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					attrGiftParamBusiRequest.store();
						
//					this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
					//赠品面值单位
					paramMap = new HashMap();
					paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
					paramMap.put("gift_inst_id", gift_id);
					paramMap.put("param_code", "gift_unit");
					paramMap.put("param_name", "赠品面值单位");
					paramMap.put("has_value_code", "0");
					paramMap.put("param_value_code", "");
					paramMap.put("param_value", "01");
					paramMap.put("order_id", order_id);
					paramMap.put("source_from", ManagerUtils.getSourceFrom());
//					this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
					
					attrGiftParamBusiRequest = new AttrGiftParamBusiRequest();
					BeanUtils.copyProperties(attrGiftParamBusiRequest, paramMap);
					attrGiftParamBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					attrGiftParamBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					attrGiftParamBusiRequest.store();
					
					
					//赠品数量
					paramMap = new HashMap();
					paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
					paramMap.put("gift_inst_id", gift_id);
					paramMap.put("param_code", "gift_num");
					paramMap.put("param_name", "赠品数量");
					paramMap.put("has_value_code", "0");
					paramMap.put("param_value_code", "");
					paramMap.put("param_value", giftNum);
					paramMap.put("order_id", order_id);
					paramMap.put("source_from", ManagerUtils.getSourceFrom());
					
					attrGiftParamBusiRequest = new AttrGiftParamBusiRequest();
					BeanUtils.copyProperties(attrGiftParamBusiRequest, paramMap);
					attrGiftParamBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					attrGiftParamBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					attrGiftParamBusiRequest.store();
					
					
//					this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
					//赠品描述
					paramMap = new HashMap();
					paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
					paramMap.put("gift_inst_id", gift_id);
					paramMap.put("param_code", "gift_desc");
					paramMap.put("param_name", "赠品描述");
					paramMap.put("has_value_code", "0");
					paramMap.put("param_value_code", "");
					paramMap.put("param_value", giftDesc);
					paramMap.put("order_id", order_id);
					paramMap.put("source_from", ManagerUtils.getSourceFrom());
					
					
					attrGiftParamBusiRequest = new AttrGiftParamBusiRequest();
					BeanUtils.copyProperties(attrGiftParamBusiRequest, paramMap);
					attrGiftParamBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					attrGiftParamBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					attrGiftParamBusiRequest.store();
					
//					this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
					//赠品品牌
					paramMap = new HashMap();
					paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
					paramMap.put("gift_inst_id", gift_id);
					paramMap.put("param_code", "gift_brand");
					paramMap.put("param_name", "赠品品牌");
					paramMap.put("has_value_code", "0");
					paramMap.put("param_value_code", "");
					paramMap.put("param_value", giftBrand);
					paramMap.put("order_id", order_id);
					paramMap.put("source_from", ManagerUtils.getSourceFrom());
					
					attrGiftParamBusiRequest = new AttrGiftParamBusiRequest();
					BeanUtils.copyProperties(attrGiftParamBusiRequest, paramMap);
					attrGiftParamBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					attrGiftParamBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					attrGiftParamBusiRequest.store();
					
					
//					this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
					//赠品型号
					paramMap = new HashMap();
					paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
					paramMap.put("gift_inst_id", gift_id);
					paramMap.put("param_code", "gift_model");
					paramMap.put("param_name", "赠品型号");
					paramMap.put("has_value_code", "0");
					paramMap.put("param_value_code", "");
					paramMap.put("param_value", jsonObject.get("giftModel"));
					paramMap.put("order_id", order_id);
					paramMap.put("source_from", ManagerUtils.getSourceFrom());
					
					attrGiftParamBusiRequest = new AttrGiftParamBusiRequest();
					BeanUtils.copyProperties(attrGiftParamBusiRequest, paramMap);
					attrGiftParamBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					attrGiftParamBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					attrGiftParamBusiRequest.store();
					
					
//					this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
					//赠品颜色
					paramMap = new HashMap();
					paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
					paramMap.put("gift_inst_id", gift_id);
					paramMap.put("param_code", "gift_color");
					paramMap.put("param_name", "赠品颜色");
					paramMap.put("has_value_code", "0");
					paramMap.put("param_value_code", "");
					paramMap.put("param_value", jsonObject.get("giftColor"));
					paramMap.put("order_id", order_id);
					paramMap.put("source_from", ManagerUtils.getSourceFrom());
					
					attrGiftParamBusiRequest = new AttrGiftParamBusiRequest();
					BeanUtils.copyProperties(attrGiftParamBusiRequest, paramMap);
					attrGiftParamBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					attrGiftParamBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					attrGiftParamBusiRequest.store();
					
					
//					this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
					//赠品机型
					paramMap = new HashMap();
					paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
					paramMap.put("gift_inst_id", gift_id);
					paramMap.put("param_code", "gift_sku");
					paramMap.put("param_name", "赠品机型");
					paramMap.put("has_value_code", "0");
					paramMap.put("param_value_code", "");
					paramMap.put("order_id", order_id);
					paramMap.put("param_value", jsonObject.get("giftTypeId"));
					paramMap.put("source_from", ManagerUtils.getSourceFrom());
					
					attrGiftParamBusiRequest = new AttrGiftParamBusiRequest();
					BeanUtils.copyProperties(attrGiftParamBusiRequest, paramMap);
					attrGiftParamBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					attrGiftParamBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					attrGiftParamBusiRequest.store();
					
					
//					this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
					//是否需要加工
					paramMap = new HashMap();
					paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
					paramMap.put("gift_inst_id", gift_id);
					paramMap.put("param_code", "is_process");
					paramMap.put("param_name", "是否需要加工");
					paramMap.put("has_value_code", "0");
					paramMap.put("param_value_code", "");
					paramMap.put("param_value", "0");
					paramMap.put("order_id", order_id);
					paramMap.put("source_from", ManagerUtils.getSourceFrom());
					
					attrGiftParamBusiRequest = new AttrGiftParamBusiRequest();
					BeanUtils.copyProperties(attrGiftParamBusiRequest, paramMap);
					attrGiftParamBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					attrGiftParamBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					attrGiftParamBusiRequest.store();
					
					
//					this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
					//加工类型
					paramMap = new HashMap();
					paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
					paramMap.put("gift_inst_id", gift_id);
					paramMap.put("param_code", "process_type");
					paramMap.put("param_name", "加工类型");
					paramMap.put("has_value_code", "0");
					paramMap.put("param_value_code", "");
					paramMap.put("param_value", "");
					paramMap.put("order_id", order_id);
					paramMap.put("source_from", ManagerUtils.getSourceFrom());
					
					 attrGiftParamBusiRequest = new AttrGiftParamBusiRequest();
					BeanUtils.copyProperties(attrGiftParamBusiRequest, paramMap);
					attrGiftParamBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					attrGiftParamBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					attrGiftParamBusiRequest.store();
					
					
//					this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
					//加工内容
					paramMap = new HashMap();
					paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
					paramMap.put("gift_inst_id", gift_id);
					paramMap.put("param_code", "process_desc");
					paramMap.put("param_name", "加工内容");
					paramMap.put("has_value_code", "0");
					paramMap.put("param_value_code", "");
					paramMap.put("param_value", "");
					paramMap.put("order_id", order_id);
					paramMap.put("source_from", ManagerUtils.getSourceFrom());
					
					attrGiftParamBusiRequest = new AttrGiftParamBusiRequest();
					BeanUtils.copyProperties(attrGiftParamBusiRequest, paramMap);
					attrGiftParamBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					attrGiftParamBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					attrGiftParamBusiRequest.store();
					
					
//					this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
				}
			}catch(Exception e){
				
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void saveGiftInfo(String order_id,String inst_id,String activity_list){
		//{"activity_list":[{"activity_code":"201501205660002817","activity_id":3223}],
		//[{"activity_code":"2014072300006","activity_id":"1497","gift_list":[{"gift_id":"5869090300020140723000534","gift_num":"1","is_process":"0","process_type":""},{"gift_id":"5869100000020140815001262","gift_num":"1","is_process":"0","process_type":""}]}]
		if(StringUtils.isEmpty(activity_list) || "-1".equals(activity_list) || "[]".equals(activity_list))return ;
		try {
			JSONArray jsonArray = JSONArray.fromObject(activity_list);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
				String gift_list = jsonObject.get("gift_list")==null?"[]":jsonObject.get("gift_list").toString();
				if("[]".equals(gift_list)){
					String pmt_code = jsonObject.get("activity_code").toString();
					ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
					SkuQueryReq req = new SkuQueryReq();
					req.setSourceFrom(ManagerUtils.getSourceFrom());
					req.setActivityCode(pmt_code);
					SkuQueryResp resp = client.execute(req,SkuQueryResp.class);
					gift_list = resp.getBody();
				}
				JSONArray giftArray = JSONArray.fromObject(gift_list);
				boolean isGift = false;
				//判断附加产品是否已添加过
				Map<String,String> subProducutList = new HashMap<String,String>();
				for(int j=0;j<giftArray.size();j++){
					JSONObject giftObject = JSONObject.fromObject(giftArray.get(j));
					String gift_id = giftObject.get("gift_id")==null?"":giftObject.get("gift_id").toString();
					String gift_num = giftObject.get("gift_num")==null?"":giftObject.get("gift_num").toString();
					String is_process = giftObject.get("is_process")==null?"":giftObject.get("is_process").toString();
					String process_type = giftObject.get("process_type")==null?"":giftObject.get("process_type").toString();
					
					Goods goods = CommonDataFactory.getInstance().getGoodsBySku(gift_id);
					Map giftMap = new HashMap();
					AttrGiftInfoBusiRequest giftInfo = new AttrGiftInfoBusiRequest();
					Map paramMap = null;
					
					if (null != goods ) {
						String type_id = goods.getType_id();
						String cat_id = goods.getCat_id();
						//活动中的附加产品与SP服务存放在es_order_sp_product表中  begin
						//非附加产品（10050）、非SP服务（10051）存放逻辑不动
						//2016-4-19
						if(EcsOrderConsts.PRODUCT_TYPE_SP_PRODUCT.equals(type_id)){//SP产品
							saveSPGift(order_id, goods);
							continue;
						}else if(EcsOrderConsts.PRODUCT_TYPE_SUB_PRODUCT.equals(type_id)){//附加产品
							saveSubProductGift(order_id,goods,subProducutList);
							continue;
						}else if(EcsOrderConsts.PRODUCT_TYPE_KEXUANBAO.equals(type_id)
								&& goods.getCat_path().contains("|"+EcsOrderConsts.PRODUCT_CAT_TCYWB+"|")){//套餐业务包
							saveProductPackageGift(order_id,goods,inst_id);
							continue;
						}
						//2016-4-19
						//活动中的附加产品与SP服务存放在es_order_sp_product表中  end
						if (EcsOrderConsts.PRODUCT_CAT_ID_4.equals(cat_id)) {
							type_id = EcsOrderConsts.PRODUCT_TYPE_KEXUANBAO;
						}
						String id = this.baseDaoSupport.getSequences("seq_attr_gift_info");
						giftMap.put("gift_inst_id", id);
						giftMap.put("order_id", order_id);
						giftMap.put("inst_id", inst_id);
						giftMap.put("sku_id", gift_id);
						giftMap.put("goods_name", goods.getName());
						giftMap.put("goods_type", type_id);
						giftMap.put("goods_category", cat_id);
						giftMap.put("goods_desc", goods.getName());
						giftMap.put("is_gift", "1");
						giftMap.put("sku_num", giftObject.get("gift_num"));
						giftMap.put("sku_fee", "[]");
						giftMap.put("gift_id", gift_id);
						giftMap.put("gift_unit", "01");
						giftMap.put("gift_color", jsonObject.get("giftColor"));
						giftMap.put("gift_model", jsonObject.get("giftModel"));
						giftMap.put("is_process", is_process);
						giftMap.put("process_type", process_type);
						giftMap.put("source_from", ManagerUtils.getSourceFrom());
						
						String isvirtualgift = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.IS_VIRTUAL_GIFT, cat_id);
						if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(isvirtualgift)) {
							giftMap.put("is_virtual", "1");
						}
						else{
							giftMap.put("is_virtual", "0");
						}
						String gift_type = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.GIFT_TYPE, cat_id);
						if(StringUtils.isEmpty(gift_type)){//如果赠品类型不是
							gift_type = EcsOrderConsts.CARD_SPECIES_02;
						}
						giftMap.put("gift_type", gift_type);
						//690901000
						String isgift = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.IS_GIFT, cat_id);
						if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(isgift)) {
							isGift = true;
							paramMap = new HashMap();
							paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
							paramMap.put("gift_inst_id", id);
							paramMap.put("param_code", "gift_type");
							paramMap.put("param_name", "赠品类型");
							paramMap.put("has_value_code", "0");
							paramMap.put("param_value_code", "");
							paramMap.put("param_value", gift_type);
							paramMap.put("order_id", order_id);
							paramMap.put("source_from", ManagerUtils.getSourceFrom());
							this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
							
							//赠品编码
							paramMap = new HashMap();
							paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
							paramMap.put("gift_inst_id", id);
							paramMap.put("param_code", "gift_id");
							paramMap.put("param_name", "赠品编码");
							paramMap.put("has_value_code", "0");
							paramMap.put("param_value_code", "");
							paramMap.put("param_value", gift_id);
							paramMap.put("order_id", order_id);
							paramMap.put("source_from", ManagerUtils.getSourceFrom());
							this.baseDaoSupport.insert("es_attr_gift_param", paramMap);

							//赠品名称
							paramMap = new HashMap();
							paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
							paramMap.put("gift_inst_id", id);
							paramMap.put("param_code", "gift_name");
							paramMap.put("param_name", "赠品名称");
							paramMap.put("has_value_code", "0");
							paramMap.put("param_value_code", "");
							paramMap.put("param_value", goods.getName());
							paramMap.put("order_id", order_id);
							paramMap.put("source_from", ManagerUtils.getSourceFrom());
							this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
							
							//赠品数量
							paramMap = new HashMap();
							paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
							paramMap.put("gift_inst_id", id);
							paramMap.put("param_code", "gift_num");
							paramMap.put("param_name", "赠品数量");
							paramMap.put("has_value_code", "0");
							paramMap.put("param_value_code", "");
							paramMap.put("param_value", gift_num);
							paramMap.put("order_id", order_id);
							paramMap.put("source_from", ManagerUtils.getSourceFrom());
							this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
							
							//赠品面值单位
							paramMap = new HashMap();
							paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
							paramMap.put("gift_inst_id", id);
							paramMap.put("param_code", "gift_unit");
							paramMap.put("param_name", "赠品面值单位");
							paramMap.put("has_value_code", "0");
							paramMap.put("param_value_code", "");
							paramMap.put("param_value", "01");
							paramMap.put("order_id", order_id);
							paramMap.put("source_from", ManagerUtils.getSourceFrom());
							this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
							
							//是否需要加工
							paramMap = new HashMap();
							paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
							paramMap.put("gift_inst_id", id);
							paramMap.put("param_code", "is_process");
							paramMap.put("param_name", "是否需要加工");
							paramMap.put("has_value_code", "0");
							paramMap.put("param_value_code", "");
							paramMap.put("param_value", is_process);
							paramMap.put("order_id", order_id);
							paramMap.put("source_from", ManagerUtils.getSourceFrom());
							this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
							
							//加工类型
							paramMap = new HashMap();
							paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
							paramMap.put("gift_inst_id", id);
							paramMap.put("param_code", "process_type");
							paramMap.put("param_name", "加工类型");
							paramMap.put("has_value_code", "0");
							paramMap.put("param_value_code", "");
							paramMap.put("param_value", process_type);
							paramMap.put("order_id", order_id);
							paramMap.put("source_from", ManagerUtils.getSourceFrom());
							this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
							
						}
						
						String params_list = goods.getParams();
						ParamGroup[] paramAr = null;
						try {
							paramAr = JsonStrHandler.converFormString(params_list);
						} catch (Exception e) {
							logger.info(e.getMessage(), e);
						}
						if (null != paramAr && isGift) {
							for (int k = 0; k < paramAr.length; k++) {
								ParamGroup group = paramAr[k];
								List<GoodsParam> params = group.getParamList();
								for(GoodsParam param : params){
									
									paramMap = new HashMap();
									paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
									paramMap.put("gift_inst_id", id);
									paramMap.put("param_code", param.getName());
									paramMap.put("param_name", param.getName());
									paramMap.put("has_value_code", "0");
									paramMap.put("param_value_code", "");
									paramMap.put("param_value", param.getValue());
									paramMap.put("order_id", order_id);
									paramMap.put("source_from", ManagerUtils.getSourceFrom());
									this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
									
									if("is_cbss".equals(param.getEname())){//如果需要BSS业务办理，设置赠品BSS状态为1【待办理】
										if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(param.getValue())){
											giftMap.put("bss_status", "1");
										}
									}
								}

							}
						}
						if (isGift) {
							//赠品面值
							giftMap.put("gift_value", "0");
							
							paramMap = new HashMap();
							paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
							paramMap.put("gift_inst_id", id);
							paramMap.put("param_code", "gift_value");
							paramMap.put("param_name", "赠品面值");
							paramMap.put("has_value_code", "0");
							paramMap.put("param_value_code", "");
							paramMap.put("param_value", "0");
							paramMap.put("order_id", order_id);
							paramMap.put("source_from", ManagerUtils.getSourceFrom());
							this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
						}
						BeanUtils.copyProperties(giftInfo, giftMap);
						giftInfo.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						giftInfo.setDb_action(ConstsCore.DB_ACTION_INSERT);
						giftInfo.store();
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * save gift info which is in the local ECS system
	 * @param order_id
	 * @param inst_id
	 * @param pmt_codes
	 */
	private void saveECSGift(String order_id, String inst_id,String pmt_codes)
	{
		if(null==order_id || "".equalsIgnoreCase(order_id)
				|| null==pmt_codes || "".equalsIgnoreCase(pmt_codes))
			return;
		
		String[] pmt_code_items=pmt_codes.split(",");
		for(String pmt_code : pmt_code_items)
		{
			try
			{
				ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
				SkuQueryReq req = new SkuQueryReq();
				req.setSourceFrom(ManagerUtils.getSourceFrom());
				req.setActivityCode(pmt_code);
				SkuQueryResp resp = client.execute(req,SkuQueryResp.class);
				String gift_list = resp.getBody();
				
				JSONArray giftArray = JSONArray.fromObject(gift_list);
				boolean isGift = false;
				//判断附加产品是否已添加过
				Map<String,String> subProducutList = new HashMap<String,String>();
				for(int j=0;j<giftArray.size();j++){
					JSONObject giftObject = JSONObject.fromObject(giftArray.get(j));
					String gift_id = giftObject.get("gift_id")==null?"":giftObject.get("gift_id").toString();
					String gift_num = giftObject.get("gift_num")==null?"":giftObject.get("gift_num").toString();
					String is_process = giftObject.get("is_process")==null?"":giftObject.get("is_process").toString();
					String process_type = giftObject.get("process_type")==null?"":giftObject.get("process_type").toString();
					
					Goods goods = CommonDataFactory.getInstance().getGoodsBySku(gift_id);
					Map giftMap = new HashMap();
					AttrGiftInfoBusiRequest giftInfo = new AttrGiftInfoBusiRequest();
					Map paramMap = null;
					
					if (null != goods ) {
						String type_id = goods.getType_id();					
						String cat_id = goods.getCat_id();
						//活动中的附加产品与SP服务存放在es_order_sp_product表中  begin
						//非附加产品（10050）、非SP服务（10051）存放逻辑不动
						//2016-4-19
						if(EcsOrderConsts.PRODUCT_TYPE_SP_PRODUCT.equals(type_id)){//SP产品
							saveSPGift(order_id, goods);
							continue;
						}else if(EcsOrderConsts.PRODUCT_TYPE_SUB_PRODUCT.equals(type_id)){//附加产品
							saveSubProductGift(order_id,goods,subProducutList);
							continue;
						}else if(EcsOrderConsts.PRODUCT_TYPE_KEXUANBAO.equals(type_id) 
								&& goods.getCat_path().contains("|"+EcsOrderConsts.PRODUCT_CAT_TCYWB+"|")){//套餐业务包
							saveProductPackageGift(order_id,goods,inst_id);
							continue;
						}
						//2016-4-19
						//活动中的附加产品与SP服务存放在es_order_sp_product表中  end	
						if (EcsOrderConsts.PRODUCT_CAT_ID_4.equals(cat_id)) {
							type_id = EcsOrderConsts.PRODUCT_TYPE_KEXUANBAO;
						}
						String id = this.baseDaoSupport.getSequences("seq_attr_gift_info");
						giftMap.put("gift_inst_id", id);
						giftMap.put("order_id", order_id);
						giftMap.put("inst_id", inst_id);
						giftMap.put("sku_id", gift_id);
						giftMap.put("goods_name", goods.getName());
						giftMap.put("goods_type", type_id);
						giftMap.put("goods_category", cat_id);
						giftMap.put("goods_desc", goods.getName());
						giftMap.put("is_gift", "1");
						giftMap.put("sku_num", giftObject.get("gift_num"));
						giftMap.put("sku_fee", "[]");
						giftMap.put("gift_id", gift_id);
						giftMap.put("gift_unit", "01");
						//giftMap.put("gift_color", jsonObject.get("giftColor"));
						giftMap.put("gift_color", goods.getColor());
						//giftMap.put("gift_model", jsonObject.get("giftModel"));
						giftMap.put("gift_model", goods.getModel());
						giftMap.put("is_process", is_process);
						giftMap.put("process_type", process_type);
						giftMap.put("source_from", ManagerUtils.getSourceFrom());
						
						String isvirtualgift = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.IS_VIRTUAL_GIFT, cat_id);
						if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(isvirtualgift)) {
							giftMap.put("is_virtual", "1");
						}
						else{
							giftMap.put("is_virtual", "0");
						}
						String gift_type = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.GIFT_TYPE, cat_id);
						if(StringUtils.isEmpty(gift_type)){//如果赠品类型不是
							gift_type = EcsOrderConsts.CARD_SPECIES_02;
						}
						giftMap.put("gift_type", gift_type);
						//690901000
						String isgift = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.IS_GIFT, cat_id);
						if (EcsOrderConsts.IS_DEFAULT_VALUE.equals(isgift)) {
							isGift = true;
							paramMap = new HashMap();
							paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
							paramMap.put("gift_inst_id", id);
							paramMap.put("param_code", "gift_type");
							paramMap.put("param_name", "赠品类型");
							paramMap.put("has_value_code", "0");
							paramMap.put("param_value_code", "");
							paramMap.put("param_value", gift_type);
							paramMap.put("order_id", order_id);
							paramMap.put("source_from", ManagerUtils.getSourceFrom());
							this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
							
							//赠品编码
							paramMap = new HashMap();
							paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
							paramMap.put("gift_inst_id", id);
							paramMap.put("param_code", "gift_id");
							paramMap.put("param_name", "赠品编码");
							paramMap.put("has_value_code", "0");
							paramMap.put("param_value_code", "");
							paramMap.put("param_value", gift_id);
							paramMap.put("order_id", order_id);
							paramMap.put("source_from", ManagerUtils.getSourceFrom());
							this.baseDaoSupport.insert("es_attr_gift_param", paramMap);

							//赠品名称
							paramMap = new HashMap();
							paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
							paramMap.put("gift_inst_id", id);
							paramMap.put("param_code", "gift_name");
							paramMap.put("param_name", "赠品名称");
							paramMap.put("has_value_code", "0");
							paramMap.put("param_value_code", "");
							paramMap.put("param_value", goods.getName());
							paramMap.put("order_id", order_id);
							paramMap.put("source_from", ManagerUtils.getSourceFrom());
							this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
							
							//赠品数量
							paramMap = new HashMap();
							paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
							paramMap.put("gift_inst_id", id);
							paramMap.put("param_code", "gift_num");
							paramMap.put("param_name", "赠品数量");
							paramMap.put("has_value_code", "0");
							paramMap.put("param_value_code", "");
							paramMap.put("param_value", gift_num);
							paramMap.put("order_id", order_id);
							paramMap.put("source_from", ManagerUtils.getSourceFrom());
							this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
							
							//赠品面值单位
							paramMap = new HashMap();
							paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
							paramMap.put("gift_inst_id", id);
							paramMap.put("param_code", "gift_unit");
							paramMap.put("param_name", "赠品面值单位");
							paramMap.put("has_value_code", "0");
							paramMap.put("param_value_code", "");
							paramMap.put("param_value", "01");
							paramMap.put("order_id", order_id);
							paramMap.put("source_from", ManagerUtils.getSourceFrom());
							this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
							
							//是否需要加工
							paramMap = new HashMap();
							paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
							paramMap.put("gift_inst_id", id);
							paramMap.put("param_code", "is_process");
							paramMap.put("param_name", "是否需要加工");
							paramMap.put("has_value_code", "0");
							paramMap.put("param_value_code", "");
							paramMap.put("param_value", is_process);
							paramMap.put("order_id", order_id);
							paramMap.put("source_from", ManagerUtils.getSourceFrom());
							this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
							
							//加工类型
							paramMap = new HashMap();
							paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
							paramMap.put("gift_inst_id", id);
							paramMap.put("param_code", "process_type");
							paramMap.put("param_name", "加工类型");
							paramMap.put("has_value_code", "0");
							paramMap.put("param_value_code", "");
							paramMap.put("param_value", process_type);
							paramMap.put("order_id", order_id);
							paramMap.put("source_from", ManagerUtils.getSourceFrom());
							this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
							
						}
						
						String params_list = goods.getParams();
						ParamGroup[] paramAr = null;
						try {
							paramAr = JsonStrHandler.converFormString(params_list);
						} catch (Exception e) {
							logger.info(e.getMessage(), e);
						}
						if (null != paramAr && isGift) {
							for (int k = 0; k < paramAr.length; k++) {
								ParamGroup group = paramAr[k];
								List<GoodsParam> params = group.getParamList();
								for(GoodsParam param : params){
									
									paramMap = new HashMap();
									paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
									paramMap.put("gift_inst_id", id);
									paramMap.put("param_code", param.getName());
									paramMap.put("param_name", param.getName());
									paramMap.put("has_value_code", "0");
									paramMap.put("param_value_code", "");
									paramMap.put("param_value", param.getValue());
									paramMap.put("order_id", order_id);
									paramMap.put("source_from", ManagerUtils.getSourceFrom());
									this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
									
									if("is_cbss".equals(param.getEname())){//如果需要BSS业务办理，设置赠品BSS状态为1【待办理】
										if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(param.getValue())){
											giftMap.put("bss_status", "1");
										}
									}
								}
							}
						}
						if (isGift) {
							//赠品面值
							giftMap.put("gift_value", "0");
							
							paramMap = new HashMap();
							paramMap.put("param_inst_id", this.baseDaoSupport.getSequences("seq_attr_gift_param"));
							paramMap.put("gift_inst_id", id);
							paramMap.put("param_code", "gift_value");
							paramMap.put("param_name", "赠品面值");
							paramMap.put("has_value_code", "0");
							paramMap.put("param_value_code", "");
							paramMap.put("param_value", "0");
							paramMap.put("order_id", order_id);
							paramMap.put("source_from", ManagerUtils.getSourceFrom());
							this.baseDaoSupport.insert("es_attr_gift_param", paramMap);
						}
						BeanUtils.copyProperties(giftInfo, giftMap);
						giftInfo.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						giftInfo.setDb_action(ConstsCore.DB_ACTION_INSERT);
						giftInfo.store();
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
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
	
	/**
	 * 保存SP产品信息
	 * @param order_id
	 * @param goods
	 */
	public void saveSPGift(String order_id , Goods goods){
		try {
			Map<String,String> spec = SpecUtils.getProductSpecMap(goods);
			OrderSpProductBusiRequest spProduct = new OrderSpProductBusiRequest();
			String sp_inst_id = baseDaoSupport.getSequences("S_ES_ORDER_SP_PRODUCT");
			spProduct.setSp_inst_id(sp_inst_id);
			spProduct.setOrder_id(order_id);
			spProduct.setSp_id(spec.get("sp_id"));
			spProduct.setSp_code(spec.get("sp_service_code"));
			spProduct.setSp_provider(spec.get("sp_service_applyer"));
			spProduct.setSp_name(spec.get("sp_product_name"));
			spProduct.setAccept_platform(spec.get("accept_platform"));
			spProduct.setStatus("0");
			spProduct.setSku(goods.getSku());
			spProduct.setSource_from(ManagerUtils.getSourceFrom());
			spProduct.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			spProduct.setDb_action(ConstsCore.DB_ACTION_INSERT);
			spProduct.store();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage(), e);
		}
	}

	
	/**
	 * 保存附加产品信息
	 * @param order_id
	 * @param goods
	 */
	public void saveSubProductGift(String order_id , Goods goods , Map<String,String> subProducutList){
		try {
			Map<String,String> spec = SpecUtils.getProductSpecMap(goods);
			String cbss_product_code = spec.get("cbss_product_code");
			String sub_product_seq = "";
			//判断附加产品是否已保存（存在一个附加产品对应多个可选包的情况，可选包要入库多次，但附加产品只能入库一次）
			if(!subProducutList.containsKey(cbss_product_code)){
				//附加产品
				sub_product_seq = baseDaoSupport.getSequences("ES_ORDER_SUB_PRODCUT_SEQ");
				OrderSubProductBusiRequest orderSubProductBusiRequest = new OrderSubProductBusiRequest();
				orderSubProductBusiRequest.setSub_pro_inst_id(sub_product_seq);
				orderSubProductBusiRequest.setOrder_id(order_id);
				orderSubProductBusiRequest.setProd_inst_id("0");
				orderSubProductBusiRequest.setSub_pro_code(cbss_product_code);
				orderSubProductBusiRequest.setSub_pro_name("");
				orderSubProductBusiRequest.setSub_pro_desc("");
				orderSubProductBusiRequest.setSub_prod_type("1");
				orderSubProductBusiRequest.setSource_from(ManagerUtils.getSourceFrom());
				orderSubProductBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
				orderSubProductBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderSubProductBusiRequest.store();
				//标识附加产品已处理，并保存ES_ORDER_SUB_PRODCUT表中的Sub_pro_inst_id
				subProducutList.put(cbss_product_code, sub_product_seq);
			}else{
				//获取Sub_pro_inst_id值
				sub_product_seq = subProducutList.get(cbss_product_code);
			}
			
			//附加产品可选包
			String package_subProd_seq = baseDaoSupport.getSequences("ES_ATTR_PACKAGE_SubProd_SEQ");
			AttrPackageSubProdBusiRequest attrPackageSubProdBusiRequest = new AttrPackageSubProdBusiRequest();
			attrPackageSubProdBusiRequest.setPackage_inst_id(package_subProd_seq);
			attrPackageSubProdBusiRequest.setOrder_id(order_id);
			attrPackageSubProdBusiRequest.setSubProd_inst_id(sub_product_seq);
			attrPackageSubProdBusiRequest.setPackage_code(spec.get("package_code"));
			attrPackageSubProdBusiRequest.setPackage_name(spec.get("package_name"));
			attrPackageSubProdBusiRequest.setProduct_code(cbss_product_code);
			attrPackageSubProdBusiRequest.setElement_code(spec.get("element_code"));
			attrPackageSubProdBusiRequest.setElement_type("");
			attrPackageSubProdBusiRequest.setElement_name(spec.get("element_name"));
			attrPackageSubProdBusiRequest.setOper_type("");
			attrPackageSubProdBusiRequest.setChange_type("");
			attrPackageSubProdBusiRequest.setBiz_type("");
			attrPackageSubProdBusiRequest.setStatus("0");	//办理状态默认为0（未办理）
			attrPackageSubProdBusiRequest.setSku(goods.getSku());
			attrPackageSubProdBusiRequest.setSource_from(ManagerUtils.getSourceFrom());
			attrPackageSubProdBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
			attrPackageSubProdBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			attrPackageSubProdBusiRequest.store();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage(), e);
		}
	}
	
	/**
	 * 保存套餐业务包
	 * @param order_id
	 * @param goods
	 */
	public void saveProductPackageGift(String order_id , Goods goods , String inst_id){
		String type_id = goods.getType_id();
		Map<String, String> spec = SpecUtils.getProductSpecMap(goods);
		AttrPackageBusiRequest attrPackageBusiRequest = new AttrPackageBusiRequest();
		try {
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
			attrPackageBusiRequest.setSku(goods.getSku());
			attrPackageBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
			attrPackageBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			attrPackageBusiRequest.store();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

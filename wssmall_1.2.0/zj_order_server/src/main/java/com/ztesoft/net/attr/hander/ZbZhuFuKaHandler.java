/**
 * 
 */
package com.ztesoft.net.attr.hander;

import java.util.HashMap;
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
import zte.net.ecsord.params.busi.req.AttrPackageFukaBusiRequest;
import zte.net.ecsord.params.busi.req.AttrPackageSubProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderActivityBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoFukaBusiRequest;
import zte.net.ecsord.params.busi.req.OrderSubProductBusiRequest;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.BeanUtils;
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
public class ZbZhuFuKaHandler extends BaseSupport implements IAttrHandler {

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

//		CenterMallGoodsAttInfo goodsAttInfo = new CenterMallGoodsAttInfo();
		String goodsAttInfo = (String) orderAttrValues.get("zb_fuka_info");
		
		if (!StringUtils.isEmpty(goodsAttInfo) && !"null".equals(goodsAttInfo)
				&& !EcsOrderConsts.ATTR_DEFAULT_VALUE.equals(goodsAttInfo) && !"[]".equals(goodsAttInfo)) {
			if (CommonDataFactory.getInstance().isZbOrder(plat_type)) {
				JSONArray jsonArray = JSONArray.fromObject(goodsAttInfo);
				for (int i = 0; i < jsonArray.size(); i++) {
					Map fuKaMap = new HashMap();
					JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
					String faKu_inst_id = baseDaoSupport.getSequences("ES_ORDER_PHONE_INFO_FUKASEQ");
					fuKaMap.put("inst_id", faKu_inst_id);
					fuKaMap.put("order_id", order_id); //订单来源
					fuKaMap.put("order_from", jsonObject.get("order_from")!=null?jsonObject.get("order_from"):""); //订单来源
					fuKaMap.put("phoneNum", jsonObject.get("phone_num")!=null?jsonObject.get("phone_num"):""); //手机号码
					String user_type = jsonObject.get("user_type")!=null?jsonObject.get("user_type").toString():"";
					if (user_type.equals("NEW")) {
						user_type = EcsOrderConsts.IS_OLD_0;
					} else if (user_type.equals("OLD")) {
						user_type = EcsOrderConsts.IS_OLD_1;
					}
					fuKaMap.put("is_old", user_type);
					fuKaMap.put("cardType", jsonObject.get("card_type")!=null?jsonObject.get("card_type"):""); //卡类型
					fuKaMap.put("userType", jsonObject.get("net_type")!=null?jsonObject.get("net_type"):""); //入网类型
					fuKaMap.put("numType", jsonObject.get("num_type")!=null?jsonObject.get("num_type"):""); //主副卡标记
					fuKaMap.put("productCode", jsonObject.get("prod_id")!=null?jsonObject.get("prod_id"):""); //产品编码
					fuKaMap.put("productName", jsonObject.get("prod_name")!=null?jsonObject.get("prod_name"):""); //产品名称
					fuKaMap.put("productType", jsonObject.get("prod_type")!=null?jsonObject.get("prod_type"):""); //产品类型
					fuKaMap.put("productNet", jsonObject.get("prod_net_type")!=null?jsonObject.get("prod_net_type"):""); //产品网别
					fuKaMap.put("productBrand", jsonObject.get("prod_bran_code")!=null?jsonObject.get("prod_bran_code"):""); //产品品牌编码
					fuKaMap.put("firstMonbillMode", jsonObject.get("first_fee_type")!=null?jsonObject.get("first_fee_type"):""); //首月资费类型
					fuKaMap.put("activityType", jsonObject.get("ActivityType")!=null?jsonObject.get("ActivityType"):""); //合约类型
					fuKaMap.put("activityCode", jsonObject.get("ActivityCode")!=null?jsonObject.get("ActivityCode"):""); //合约编码
					fuKaMap.put("activityName", jsonObject.get("ActivityName")!=null?jsonObject.get("ActivityCode"):""); //合约名称
					fuKaMap.put("actProtPer", jsonObject.get("ActProtPer")!=null?jsonObject.get("ActProtPer"):""); //合约期限
					fuKaMap.put("prokey", jsonObject.get("prokey")!=null?jsonObject.get("prokey"):""); //预占关键字
					fuKaMap.put("occupiedflag", jsonObject.get("occupied_flag")!=null?jsonObject.get("occupied_flag"):""); //操作类型
					fuKaMap.put("operatorstate", jsonObject.get("result_state")!=null?jsonObject.get("result_state"):""); //操作结果状态
					fuKaMap.put("result_msg", jsonObject.get("result_msg")!=null?jsonObject.get("result_msg"):""); //操作结果描述
					fuKaMap.put("source_from", jsonObject.get("source_from")!=null?jsonObject.get("source_from"):"");
					fuKaMap.put("update_time", jsonObject.get("update_time")!=null?jsonObject.get("update_time"):""); //最后更新时间
					fuKaMap.put("bss_occupied_flag", jsonObject.get("bss_occupied_flag")!=null?jsonObject.get("bss_occupied_flag"):""); //BSS操作类型
					fuKaMap.put("oper_id", jsonObject.get("oper_id")!=null?jsonObject.get("oper_id"):""); //预占工号
					fuKaMap.put("certType", jsonObject.get("cert_type")!=null?jsonObject.get("cert_type"):""); // 副卡证件类型
					fuKaMap.put("customerName", jsonObject.get("cert_name")!=null?jsonObject.get("cert_name"):""); // 副卡名称
					fuKaMap.put("certNum", jsonObject.get("cert_num")!=null?jsonObject.get("cert_num"):""); // 副卡证件号码
					
					fuKaMap.put("advancePay", jsonObject.get("advancepay")!=null?jsonObject.get("advancepay"):""); // 预存话费金额
					fuKaMap.put("advanceCom", jsonObject.get("advancecom")!=null?jsonObject.get("advancecom"):""); // 普通预存
					fuKaMap.put("advanceSpe", jsonObject.get("advancespe")!=null?jsonObject.get("advancespe"):""); // 专项预存
					fuKaMap.put("numThawTim", jsonObject.get("numthawtim")!=null?jsonObject.get("numthawtim"):""); // 返还时长
					fuKaMap.put("numThawPro", jsonObject.get("numthawpro")!=null?jsonObject.get("numthawpro"):""); // 分月返还金额
					fuKaMap.put("classId", jsonObject.get("classid")!=null?jsonObject.get("classid"):""); // 号码等级
					fuKaMap.put("lowCostChe", jsonObject.get("lowcostche")!=null?jsonObject.get("lowcostche"):""); // 考核期最低消费
					fuKaMap.put("timeDurChe", jsonObject.get("timedurche")!=null?jsonObject.get("timedurche"):""); // 考核时长
					fuKaMap.put("changeTagChe", jsonObject.get("changetagche")!=null?jsonObject.get("changetagche"):""); // 考核期是否过户
					fuKaMap.put("cancelTagChe", jsonObject.get("canceltagche")!=null?jsonObject.get("canceltagche"):""); // 考核期是否销户
					fuKaMap.put("breMonChe", jsonObject.get("bremonche")!=null?jsonObject.get("bremonche"):""); // 考核期违约金月份
					fuKaMap.put("lowCostPro", jsonObject.get("lowcostpro")!=null?jsonObject.get("lowcostpro"):""); // 协议期最低消费
					fuKaMap.put("timeDurPro", jsonObject.get("timedurpro")!=null?jsonObject.get("timedurpro"):""); // 协议时长
					fuKaMap.put("changeTagPro", jsonObject.get("changetagpro")!=null?jsonObject.get("changetagpro"):""); // 协议期是否过户
					fuKaMap.put("cancelTagPro", jsonObject.get("canceltagpro")!=null?jsonObject.get("canceltagpro"):""); // 协议期是否销户
					fuKaMap.put("broMonPro", jsonObject.get("bromonpro")!=null?jsonObject.get("bromonpro"):""); // 协议期违约金月份
					fuKaMap.put("proKeyMode", jsonObject.get("prokeymode")!=null?jsonObject.get("prokeymode"):""); // 号码资源预占关键字类型 0：随机码 1：客户标识（或登录名） 2：订单标识 3：淘宝序列号
					fuKaMap.put("occupiedTime", jsonObject.get("occupiedtime")!=null?jsonObject.get("occupiedtime"):""); // 操作时间
					fuKaMap.put("CertLoseTime", jsonObject.get("certlosetime")!=null?jsonObject.get("certlosetime"):""); // 证件失效时间YYYY-MM-DD HH24:MI:SS
					fuKaMap.put("certAddr", jsonObject.get("certaddr")!=null?jsonObject.get("certaddr"):""); // 证件地址
					fuKaMap.put("sex", jsonObject.get("sex")!=null?jsonObject.get("sex"):""); // 性别，固定长度1位，M：男， F：女
					fuKaMap.put("reliefPresFlag", jsonObject.get("reliefpresflag")!=null?jsonObject.get("reliefpresflag"):""); // 减免预存标记
					fuKaMap.put("saleMode", jsonObject.get("salemode")!=null?jsonObject.get("salemode"):""); // 销售方式
					fuKaMap.put("simId", jsonObject.get("simid")!=null?jsonObject.get("simid"):""); // SIM卡号
					fuKaMap.put("goodsType", jsonObject.get("goodstype")!=null?jsonObject.get("goodstype"):""); // 商品类型
					fuKaMap.put("serType", jsonObject.get("sertype")!=null?jsonObject.get("sertype"):""); // 付费类型
					fuKaMap.put("checkType", jsonObject.get("checktype")!=null?jsonObject.get("checktype"):""); // 认证类型：cbss系统订单必传01：本地认证02：公安认证03：二代证读卡器
					fuKaMap.put("realNameType", jsonObject.get("realnametype")!=null?jsonObject.get("realnametype"):""); // 实名认证类型：1：未实名认证2：已实名认证
					
					JSONArray packages = (JSONArray) jsonObject.get("packages");
					if (packages!=null && packages.size()>0) {
						for (int j = 0; j < packages.size(); j ++) {
							JSONObject attrPackage = JSONObject.fromObject(packages.get(j));
							AttrPackageFukaBusiRequest attrPackageFukaBusiRequest = new AttrPackageFukaBusiRequest();
							String pack_inst_id = baseDaoSupport.getSequences("ES_ATTR_PACKAGE_FUKA_SEQ");
							attrPackageFukaBusiRequest.setPackage_inst_id(pack_inst_id);
							attrPackageFukaBusiRequest.setOrder_id(order_id);
							attrPackageFukaBusiRequest.setInst_id(faKu_inst_id);
							attrPackageFukaBusiRequest.setPackage_code(attrPackage.getString("packageCode")!=null?attrPackage.getString("packageCode"):"");
							attrPackageFukaBusiRequest.setPackage_name(attrPackage.getString("packageName")!=null?attrPackage.getString("packageName"):"");
							attrPackageFukaBusiRequest.setProduct_code(jsonObject.getString("prod_id")!=null?jsonObject.getString("prod_id"):"");
							attrPackageFukaBusiRequest.setElement_code(attrPackage.getString("elementCode")!=null?attrPackage.getString("elementCode"):"");			
							attrPackageFukaBusiRequest.setElement_type(attrPackage.getString("elementType")!=null?attrPackage.getString("elementType"):"");			
							attrPackageFukaBusiRequest.setElement_name(attrPackage.getString("elementName")!=null?attrPackage.getString("elementName"):"");			
							attrPackageFukaBusiRequest.setOper_type("");
							attrPackageFukaBusiRequest.setChange_type("");
							attrPackageFukaBusiRequest.setBiz_type("");
							attrPackageFukaBusiRequest.setSource_from(ManagerUtils.getSourceFrom());
							attrPackageFukaBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
							attrPackageFukaBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							attrPackageFukaBusiRequest.store();
						}
					}
					
					JSONArray zb_sub_prods = (JSONArray)jsonObject.get("subProdInfo");
					if (zb_sub_prods != null && zb_sub_prods.size() > 0) {
						for (int j = 0; j < zb_sub_prods.size(); j ++) {
							String ES_ORDER_SUB_PRODCUT_SEQ = baseDaoSupport.getSequences("ES_ORDER_SUB_PRODCUT_SEQ");
							JSONObject zb_sub_prod = JSONObject.fromObject(zb_sub_prods.get(j));
							String sub_pro_code = zb_sub_prod.getString("sub_pro_code")!=null?zb_sub_prod.getString("sub_pro_code"):"";
							String sub_pro_name = zb_sub_prod.getString("sub_pro_name")!=null?zb_sub_prod.getString("sub_pro_name"):"";
							String sub_pro_desc = zb_sub_prod.getString("sub_pro_desc")!=null?zb_sub_prod.getString("sub_pro_desc"):"";
							String sub_prod_type = zb_sub_prod.getString("sub_prod_type")!=null?zb_sub_prod.getString("sub_prod_type"):"";
							OrderSubProductBusiRequest orderSubProductBusiRequest = new OrderSubProductBusiRequest();
							orderSubProductBusiRequest.setSub_pro_inst_id(ES_ORDER_SUB_PRODCUT_SEQ);
							orderSubProductBusiRequest.setOrder_id(order_id);
							orderSubProductBusiRequest.setProd_inst_id(faKu_inst_id); // 副卡次字段填副卡表主键ID
							orderSubProductBusiRequest.setSub_pro_code(sub_pro_code);
							orderSubProductBusiRequest.setSub_pro_name(sub_pro_name);
							orderSubProductBusiRequest.setSub_pro_desc(sub_pro_desc);
							orderSubProductBusiRequest.setSub_prod_type(sub_prod_type);
							orderSubProductBusiRequest.setSource_from(ManagerUtils.getSourceFrom());
							orderSubProductBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
							orderSubProductBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							orderSubProductBusiRequest.store();
							String subPackage = zb_sub_prod.getString("subPackage")!=null?zb_sub_prod.getString("subPackage"):"";
							JSONArray subPackages = JSONArray.fromObject(subPackage);
							if (subPackages != null && subPackages.size() > 0) {
								for (int k = 0; k < subPackages.size(); k ++) {
									JSONObject subProd = JSONObject.fromObject(subPackages.get(k));
									String ES_ATTR_PACKAGE_SubProd_SEQ = baseDaoSupport.getSequences("ES_ATTR_PACKAGE_SubProd_SEQ");
									String package_code = subProd.getString("package_code")!=null?subProd.getString("package_code"):"";
									String package_name = subProd.getString("package_name")!=null?subProd.getString("package_name"):"";
									String element_code = subProd.getString("element_code")!=null?subProd.getString("element_code"):"";
									String element_type = subProd.getString("element_type")!=null?subProd.getString("element_type"):"";
									String element_name = subProd.getString("element_name")!=null?subProd.getString("element_name"):"";
									AttrPackageSubProdBusiRequest attrPackageSubProdBusiRequest = new AttrPackageSubProdBusiRequest();
									attrPackageSubProdBusiRequest.setPackage_inst_id(ES_ATTR_PACKAGE_SubProd_SEQ);
									attrPackageSubProdBusiRequest.setOrder_id(order_id);
									attrPackageSubProdBusiRequest.setSubProd_inst_id(ES_ORDER_SUB_PRODCUT_SEQ);
									attrPackageSubProdBusiRequest.setPackage_code(package_code);
									attrPackageSubProdBusiRequest.setPackage_name(package_name);
									attrPackageSubProdBusiRequest.setProduct_code(sub_pro_code);
									attrPackageSubProdBusiRequest.setElement_code(element_code);
									attrPackageSubProdBusiRequest.setElement_type(element_type);
									attrPackageSubProdBusiRequest.setElement_name(element_name);
									attrPackageSubProdBusiRequest.setOper_type("");
									attrPackageSubProdBusiRequest.setChange_type("");
									attrPackageSubProdBusiRequest.setBiz_type("");
									attrPackageSubProdBusiRequest.setStatus("0");	//办理状态默认为0（未办理）
									attrPackageSubProdBusiRequest.setSource_from(ManagerUtils.getSourceFrom());
									attrPackageSubProdBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
									attrPackageSubProdBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
									attrPackageSubProdBusiRequest.store();
								}
							}
						}
					}
					/*
					 * start
					 */
					JSONArray activityInfos = (JSONArray)jsonObject.get("activityInfo");
					if (activityInfos != null && activityInfos.size() > 0) {
						for (int j = 0; j < activityInfos.size(); j ++) {
							String S_ES_ORDER_ACTIVITY_SEQ = baseDaoSupport.getSequences("S_ES_ORDER_ACTIVITY");
							JSONObject activityInfo = JSONObject.fromObject(activityInfos.get(j));
							String activityType = activityInfo.getString("activityType")!=null?activityInfo.getString("activityType"):"";
							String activityCode = activityInfo.getString("activityCode")!=null?activityInfo.getString("activityCode"):"";
							String activityName = activityInfo.getString("activityName")!=null?activityInfo.getString("activityName"):"";
							String actProtPer = activityInfo.getString("actProtPer")!=null?activityInfo.getString("actProtPer"):"";
							OrderActivityBusiRequest orderActivityBusiRequest = new OrderActivityBusiRequest();
							orderActivityBusiRequest.setInst_id(S_ES_ORDER_ACTIVITY_SEQ);
							orderActivityBusiRequest.setFuka_inst_id(faKu_inst_id); // 副卡表主键ID
							orderActivityBusiRequest.setOrder_id(order_id);
							orderActivityBusiRequest.setActivity_type(activityType);
							orderActivityBusiRequest.setActivity_code(activityCode);
							orderActivityBusiRequest.setActivity_name(activityName);
							orderActivityBusiRequest.setAct_prot_per(actProtPer);
							orderActivityBusiRequest.setActivity_type_zhufu(EcsOrderConsts.ZB_CARD_TYPE_FU);//副卡
							orderActivityBusiRequest.setSource_from(ManagerUtils.getSourceFrom());
							orderActivityBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
							orderActivityBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							orderActivityBusiRequest.store();
							String subPackage = activityInfo.getString("package")!=null?activityInfo.getString("package"):"";
							JSONArray activityPackages = JSONArray.fromObject(subPackage);
							if (activityPackages != null && activityPackages.size() > 0) {
								for (int k = 0; k < activityPackages.size(); k ++) {
									JSONObject activityPackage = JSONObject.fromObject(activityPackages.get(k));
									String S_ES_ATTR_PACKAGE_ACTIVITY = baseDaoSupport.getSequences("S_ES_ATTR_PACKAGE_ACTIVITY");
									String packageCode = activityPackage.getString("packageCode")!=null?activityPackage.getString("packageCode"):"";
									String packageName = activityPackage.getString("packageName")!=null?activityPackage.getString("packageName"):"";
									String elementCode = activityPackage.getString("elementCode")!=null?activityPackage.getString("elementCode"):"";
									String elementType = activityPackage.getString("elementType")!=null?activityPackage.getString("elementType"):"";
									String elementName = activityPackage.getString("elementName")!=null?activityPackage.getString("elementName"):"";
									AttrPackageActivityBusiRequest attrPackageActivityBusiRequest = new AttrPackageActivityBusiRequest();
									attrPackageActivityBusiRequest.setInst_id(S_ES_ATTR_PACKAGE_ACTIVITY);
									attrPackageActivityBusiRequest.setActivity_inst_id(S_ES_ORDER_ACTIVITY_SEQ);
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
					/*
					 * end
					 */
					
					OrderPhoneInfoFukaBusiRequest orderPhoneInfoFukaBusiRequest = new OrderPhoneInfoFukaBusiRequest();
					try {
						BeanUtils.copyProperties(orderPhoneInfoFukaBusiRequest, fuKaMap);
						orderPhoneInfoFukaBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
						orderPhoneInfoFukaBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						orderPhoneInfoFukaBusiRequest.store();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				JSONObject jsonObject = JSONObject.fromObject(goodsAttInfo);
				if (jsonObject != null) {
					String sub_no = jsonObject.getString("sub_no")!=null?jsonObject.getString("sub_no"):"";
					String vicecard_no = jsonObject.getString("vicecard_no")!=null?jsonObject.getString("vicecard_no"):"";
					String is_old = jsonObject.getString("is_old")!=null?jsonObject.getString("is_old"):EcsOrderConsts.IS_OLD_0;
					if (StringUtils.isNotEmpty(is_old) && StringUtils.isNotEmpty(vicecard_no)) {
						OrderPhoneInfoFukaBusiRequest orderPhoneInfoFukaBusiRequest = new OrderPhoneInfoFukaBusiRequest();
						orderPhoneInfoFukaBusiRequest.setPhoneNum(vicecard_no);
						orderPhoneInfoFukaBusiRequest.setOrder_id(order_id);
						orderPhoneInfoFukaBusiRequest.setIs_old(is_old);
						orderPhoneInfoFukaBusiRequest.setNumType(EcsOrderConsts.ZB_CARD_TYPE_FU);
						orderPhoneInfoFukaBusiRequest.setInst_id(baseDaoSupport.getSequences("ES_ORDER_PHONE_INFO_FUKASEQ"));
						orderPhoneInfoFukaBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
						orderPhoneInfoFukaBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						orderPhoneInfoFukaBusiRequest.store();
					}
					if (StringUtils.isNotEmpty(is_old) && StringUtils.isNotEmpty(sub_no)) {
						String[] sub_nos = sub_no.split("||"); // 共享子号（格式：“子号1|子号1的卡类型||子号2|子号2的卡类型”）
						if (sub_nos != null && sub_nos.length > 0) {
							for (String sn : sub_nos) {
								String[] sub_n = sn.split("|");
								OrderPhoneInfoFukaBusiRequest orderPhoneInfoFukaBusiRequest = new OrderPhoneInfoFukaBusiRequest();
								orderPhoneInfoFukaBusiRequest.setPhoneNum(sub_n[0]);
								orderPhoneInfoFukaBusiRequest.setCardType(sub_n[1]);
								orderPhoneInfoFukaBusiRequest.setOrder_id(order_id);
								orderPhoneInfoFukaBusiRequest.setIs_old(is_old);
								orderPhoneInfoFukaBusiRequest.setNumType(EcsOrderConsts.ZB_CARD_TYPE_FU);
								orderPhoneInfoFukaBusiRequest.setInst_id(baseDaoSupport.getSequences("ES_ORDER_PHONE_INFO_FUKASEQ"));
								orderPhoneInfoFukaBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
								orderPhoneInfoFukaBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
								orderPhoneInfoFukaBusiRequest.store();
							}
						}
					}
				}
			}
		}
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(goodsAttInfo);
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

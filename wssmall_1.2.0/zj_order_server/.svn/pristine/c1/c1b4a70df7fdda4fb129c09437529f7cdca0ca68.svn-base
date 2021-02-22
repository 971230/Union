package com.ztesoft.net.ecsord.params.ecaop.req;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.util.ZjCommonUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

public class FuKaPreOpenReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId = "";
	@ZteSoftCommentAnnotationParam(name = "主卡号码", type = "String", isNecessary = "Y", desc = "主卡号码")
	private String service_num1 = "";
	@ZteSoftCommentAnnotationParam(name = "副卡号码", type = "String", isNecessary = "Y", desc = "副卡号码")
	private String service_num2 = "";
	@ZteSoftCommentAnnotationParam(name = "绑定类型", type = "String", isNecessary = "Y", desc = "绑定类型，非空【1】1：主副卡（默认）2：一号多卡")
	private String rela_type = "";
	@ZteSoftCommentAnnotationParam(name = "是否为号卡资源中心数据", type = "String", isNecessary = "Y", desc = "是否为号卡资源中心数据 非空【1】1：是  0：否")
	private String source_flag = "";
	@ZteSoftCommentAnnotationParam(name = "业务类型", type = "String", isNecessary = "Y", desc = "业务类型")
	private String service_type = "";
	@ZteSoftCommentAnnotationParam(name = "SIM卡号", type = "String", isNecessary = "Y", desc = "SIM卡号")
	private String sim_card = "";
	@ZteSoftCommentAnnotationParam(name = "主号码子产品ID", type = "String", isNecessary = "Y", desc = "主号码子产品ID")
	private String product_id = "";
	@ZteSoftCommentAnnotationParam(name = "活动ID", type = "String", isNecessary = "Y", desc = "活动ID,副卡不需要,可空【8】")
	private String scheme_id = "";
	@ZteSoftCommentAnnotationParam(name = "客户姓名", type = "String", isNecessary = "Y", desc = "客户姓名")
	private String customer_name = "";
	@ZteSoftCommentAnnotationParam(name = "证件类型", type = "String", isNecessary = "Y", desc = "证件类型")
	private String cert_type = "";
	@ZteSoftCommentAnnotationParam(name = "证件号码", type = "String", isNecessary = "Y", desc = "证件号码")
	private String cert_num = "";
	@ZteSoftCommentAnnotationParam(name = "证件地址", type = "String", isNecessary = "Y", desc = "证件地址")
	private String cert_addr = "";
	@ZteSoftCommentAnnotationParam(name = "民族", type = "String", isNecessary = "Y", desc = "民族")
	private String cert_nation = "";
	@ZteSoftCommentAnnotationParam(name = "性别", type = "String", isNecessary = "Y", desc = "性别，可空【1】0男性，1女性")
	private String cert_sex = "";
	@ZteSoftCommentAnnotationParam(name = "签证机关", type = "String", isNecessary = "Y", desc = "签证机关")
	private String cert_issuedat = "";
	@ZteSoftCommentAnnotationParam(name = "证件失效时间", type = "String", isNecessary = "Y", desc = "证件失效时间")
	private String cert_expire_date = "";
	@ZteSoftCommentAnnotationParam(name = "证件生效时间", type = "String", isNecessary = "Y", desc = "证件生效时间")
	private String cert_effected_date = "";
	@ZteSoftCommentAnnotationParam(name = "照片", type = "String", isNecessary = "Y", desc = "照片")
	private String cert_photo = "";
	@ZteSoftCommentAnnotationParam(name = "联系人", type = "String", isNecessary = "Y", desc = "联系人")
	private String contact_name = "";
	@ZteSoftCommentAnnotationParam(name = "联系电话", type = "String", isNecessary = "Y", desc = "联系电话")
	private String contact_phone = "";
	@ZteSoftCommentAnnotationParam(name = "通信地址", type = "String", isNecessary = "Y", desc = "通信地址")
	private String customer_adder = "";
	@ZteSoftCommentAnnotationParam(name = "办理操作员", type = "String", isNecessary = "Y", desc = "办理操作员")
	private String deal_operator = "";
	@ZteSoftCommentAnnotationParam(name = "办理操作点", type = "String", isNecessary = "Y", desc = "办理操作点")
	private String deal_office_id = "";
	@ZteSoftCommentAnnotationParam(name = "是否为白卡", type = "String", isNecessary = "Y", desc = "是否为白卡，1:是0:否")
	private String param11 = "";
	@ZteSoftCommentAnnotationParam(name = "USE_DOMAIN", type = "String", isNecessary = "Y", desc = "USE_DOMAIN")
	private String param12 = "";
	@ZteSoftCommentAnnotationParam(name = "发展点", type = "String", isNecessary = "Y", desc = "发展点")
	private String param13 = "";
	@ZteSoftCommentAnnotationParam(name = "发展人", type = "String", isNecessary = "Y", desc = "发展人")
	private String param14 = "";
	@ZteSoftCommentAnnotationParam(name = "生日", type = "String", isNecessary = "Y", desc = "生日")
	private String param15 = "";
	@ZteSoftCommentAnnotationParam(name = "拍照流水号", type = "String", isNecessary = "Y", desc = "拍照流水号")
	private String req_swift_num = "";
	@ZteSoftCommentAnnotationParam(name = "预留字段", type = "String", isNecessary = "Y", desc = "预留字段")
	private String param32 = "";
	@ZteSoftCommentAnnotationParam(name = "预留字段", type = "String", isNecessary = "Y", desc = "预留字段")
	private String param33 = "";
	@ZteSoftCommentAnnotationParam(name = "资源中心卡大类", type = "String", isNecessary = "Y", desc = "资源中心卡大类")
	private String rzb_big_card_type = "";
	@ZteSoftCommentAnnotationParam(name = "资源中心卡小类", type = "String", isNecessary = "Y", desc = "资源中心卡小类")
	private String rzb_card_type = "";
	@ZteSoftCommentAnnotationParam(name = "物料编码", type = "String", isNecessary = "Y", desc = "物料编码")
	private String material_code = "";
	@ZteSoftCommentAnnotationParam(name = "KI值", type = "String", isNecessary = "Y", desc = "KI值")
	private String ki = "";
	@ZteSoftCommentAnnotationParam(name = "副卡IMSI", type = "String", isNecessary = "Y", desc = "副卡IMSI")
	private String imsi = "";
	@ZteSoftCommentAnnotationParam(name = "是否为ESIM卡", type = "String", isNecessary = "Y", desc = "是否为ESIM卡，可空【1】0：非ESIM1：是ESIM")
	private String is_esim = "";
	@ZteSoftCommentAnnotationParam(name = "一号多卡副卡的EID", type = "String", isNecessary = "Y", desc = "一号多卡副卡的EID")
	private String eid = "";
	@ZteSoftCommentAnnotationParam(name = "一号多卡副卡的IMEI", type = "String", isNecessary = "Y", desc = "一号多卡副卡的IMEI")
	private String imei = "";
	@ZteSoftCommentAnnotationParam(name = "一号多卡副卡的终端类型", type = "String", isNecessary = "Y", desc = "一号多卡副卡的终端类型")
	private String machine_type = "";
	@ZteSoftCommentAnnotationParam(name = "一号多卡副卡的别名", type = "String", isNecessary = "Y", desc = "一号多卡副卡的别名")
	private String attach_name = "";
	@ZteSoftCommentAnnotationParam(name = "一号多卡副卡的确认码", type = "String", isNecessary = "Y", desc = "一号多卡副卡的确认码")
	private String confirm_code = "";
	@ZteSoftCommentAnnotationParam(name = "一号多卡副卡的激活码", type = "String", isNecessary = "Y", desc = "一号多卡副卡的激活码")
	private String activationcode = "";
	@ZteSoftCommentAnnotationParam(name = "一一号多卡副卡的命令", type = "String", isNecessary = "Y", desc = "一号多卡副卡的命令：ADD/DSTR/DOWN/DEL")
	private String command = "";
	@ZteSoftCommentAnnotationParam(name = "一号多卡副卡的MATCHING_ID", type = "String", isNecessary = "Y", desc = "一号多卡副卡的MATCHING_ID")
	private String matching_id = "";
	@ZteSoftCommentAnnotationParam(name = "是否压单,1:是;0:否", type = "String", isNecessary = "Y", desc = "是否压单,1:是;0:否")
	private String isbacklogorder = "";
	@ZteSoftCommentAnnotationParam(name = "订单树", type = "OrderTreeBusiRequest", isNecessary = "Y", desc = "订单树")
	private OrderTreeBusiRequest orderTree;
	@ZteSoftCommentAnnotationParam(name = "证件号码", type = "String", isNecessary = "Y", desc = "证件号码")
    private String cert_num2 = "";
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getService_num1() {
		service_num1 = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "mainNumber");
		return service_num1;
	}

	public void setService_num1(String service_num1) {
		this.service_num1 = service_num1;
	}

	public String getService_num2() {
		service_num2 = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getPhone_num();
		return service_num2;
	}

	public void setService_num2(String service_num2) {
		this.service_num2 = service_num2;
	}

	public String getRela_type() {
		rela_type = "1";// 目前只有主副卡，暂时不考虑一号多卡
		return rela_type;
	}

	public void setRela_type(String rela_type) {
		this.rela_type = rela_type;
	}

	public String getSource_flag() {
		source_flag = "1";
		return source_flag;
	}

	public void setSource_flag(String source_flag) {
		this.source_flag = source_flag;
	}

	public String getService_type() {
		service_type = "1001";
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public String getSim_card() {
		sim_card = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ICCID);
		return sim_card;
	}

	public void setSim_card(String sim_card) {
		this.sim_card = sim_card;
	}

	public String getProduct_id() {
//		product_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "viceProductId");
		product_id = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, "", "bss_code");
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getScheme_id() {
		return scheme_id;
	}

	public void setScheme_id(String scheme_id) {
		this.scheme_id = scheme_id;
	}

	public String getCustomer_name() {
		customer_name = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.PHONE_OWNER_NAME);
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCert_type() {
		String certType = orderTree.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0)
				.getOrderItemProdExtBusiRequest().getCerti_type();
		IEcsOrdManager ecsOrdManager = SpringContextHolder.getBean("ecsOrdManager");
		List dc_list = ecsOrdManager.getDcSqlByDcName("DIC_BSS_CERT_TYPE");
		for (int j = 0; j < dc_list.size(); j++) {
			String value = Const.getStrValue((Map) dc_list.get(j), "value");
			// String value_desc = Const.getStrValue((Map) dc_list.get(j),
			// "value_desc");
			if (StringUtil.equals(value, certType)) {
				cert_type = Const.getStrValue((Map) dc_list.get(j), "value_desc");
				break;
			}
		}
		return cert_type;
	}

	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}

	public String getCert_num() {
		cert_num = orderTree.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0)
				.getOrderItemProdExtBusiRequest().getCert_card_num();
		return cert_num;
	}

	public void setCert_num(String cert_num) {
		this.cert_num = cert_num;
	}

	public String getCert_addr() {
		cert_addr = orderTree.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0)
				.getOrderItemProdExtBusiRequest().getCert_address();
		return cert_addr;
	}

	public void setCert_addr(String cert_addr) {
		this.cert_addr = cert_addr;
	}

	public String getCert_nation() {
		String cert_card_nation = orderTree.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0)
				.getOrderItemProdExtBusiRequest().getCert_card_nation();
		if (!StringUtils.isEmpty(cert_card_nation)) {
			cert_nation = cert_card_nation.substring(0, 1);
		}
		return cert_nation;
	}

	public void setCert_nation(String cert_nation) {
		this.cert_nation = cert_nation;
	}

	public String getCert_sex() {
		String num = getCert_num();
		if (Integer.valueOf(num.substring(16, 17)) % 2 == 0) {
			cert_sex = "1";
		} else {
			cert_sex = "0";
		}
		return cert_sex;
	}

	public void setCert_sex(String cert_sex) {
		this.cert_sex = cert_sex;
	}

	public String getCert_issuedat() {
		cert_issuedat = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_ISSUER);
		return cert_issuedat;
	}

	public void setCert_issuedat(String cert_issuedat) {
		this.cert_issuedat = cert_issuedat;
	}

	public String getCert_expire_date() {
		String expire_date = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId)
				.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0)
				.getOrderItemProdExtBusiRequest().getCert_failure_time();
		if (!StringUtils.isEmpty(expire_date)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date;

				date = sdf.parse(expire_date);

				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
				String str = sdf2.format(date);
				cert_expire_date = str;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				cert_expire_date = "";
			}
		} else {
			cert_expire_date = "";
		}
		return cert_expire_date;
	}

	public void setCert_expire_date(String cert_expire_date) {
		this.cert_expire_date = cert_expire_date;
	}

	public String getCert_effected_date() {
		String effected_date = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.CERT_EFF_TIME);
		if (!StringUtils.isEmpty(effected_date)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date;

				date = sdf.parse(effected_date);

				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
				String str = sdf2.format(date);
				cert_effected_date = str;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				cert_effected_date = "";
			}
		} else {
			cert_effected_date = "";
		}
		return cert_effected_date;
	}

	public void setCert_effected_date(String cert_effected_date) {
		this.cert_effected_date = cert_effected_date;
	}

	public String getCert_photo() {
		return cert_photo;
	}

	public void setCert_photo(String cert_photo) {
		this.cert_photo = cert_photo;
	}

	public String getContact_name() {
		contact_name = orderTree.getOrderDeliveryBusiRequests().get(0).getShip_name();
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public String getContact_phone() {
		contact_phone = orderTree.getOrderDeliveryBusiRequests().get(0).getShip_mobile();
		return contact_phone;
	}

	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	public String getCustomer_adder() {
		String ship_addr = orderTree.getOrderDeliveryBusiRequests().get(0).getShip_addr();
		// String user_addr =
		// orderTree.getOrderAdslBusiRequest().get(0).getUser_address();
		// if(!StringUtil.isEmpty(ship_addr)){
		customer_adder = ship_addr;
		/*
		 * }else if (!StringUtil.isEmpty(user_addr)){ customer_adder =
		 * user_addr; }
		 */
		return customer_adder;
	}

	public void setCustomer_adder(String customer_adder) {
		this.customer_adder = customer_adder;
	}

	public String getDeal_operator() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");

		String flag = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_FLAG");

		if (orderTree != null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
				&& "1".equals(flag)) {
			String deal_method = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
					"order_deal_method");

			// 自定义流程
			if ("2".equals(deal_method)) {
				// 线下方式受理业务，先取传入的操作员
				this.deal_operator = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.OUT_OPERATOR);

				if (StringUtil.isEmpty(this.deal_operator)) {
					// 操作员没传的取配置的操作员
					this.deal_operator = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
				}
			} else {
				// 线上方式直接取配置的操作员
				this.deal_operator = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
			}
		} else {

			if (cacheUtil.getConfigInfo("FUKA_ORDER_FROM").contains(
					CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM))) {
				deal_operator = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
			} else {
				String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.OUT_OPERATOR);
				if (!StringUtil.isEmpty(OUT_OPERATOR)) {
					deal_operator = OUT_OPERATOR;
				} else {
					deal_operator = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
				}
			}
		}
		return deal_operator;
	}

	public void setDeal_operator(String deal_operator) {
		this.deal_operator = deal_operator;
	}

	public String getDeal_office_id() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");

		String flag = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_FLAG");

		if (orderTree != null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
				&& "1".equals(flag)) {
			String deal_method = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
					"order_deal_method");

			// 自定义流程
			if ("2".equals(deal_method)) {
				// 线下方式受理业务，先取传入的操作点
				this.deal_office_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.OUT_OPERATOR);

				if (StringUtil.isEmpty(this.deal_office_id)) {
					// 操作员没传的取配置的操作点
					this.deal_office_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
				}
			} else {
				// 线上方式直接取配置的操作点
				this.deal_office_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
			}
		} else {

			if (cacheUtil.getConfigInfo("FUKA_ORDER_FROM").contains(
					CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM))) {
				deal_office_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
			} else {
				String OUT_OFFICE = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.OUT_OFFICE);
				if (!StringUtil.isEmpty(OUT_OFFICE)) {
					deal_office_id = OUT_OFFICE;
				} else {
					deal_office_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
				}
			}
		}
		return deal_office_id;
	}

	public void setDeal_office_id(String deal_office_id) {
		this.deal_office_id = deal_office_id;
	}

	public String getParam11() {
		param11 = "1";
		return param11;
	}

	public void setParam11(String param11) {
		this.param11 = param11;
	}

	public String getParam12() {
		return param12;
	}

	public void setParam12(String param12) {
		this.param12 = param12;
	}

	public String getParam13() {
		param13 = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,"develop_point_code_new");// 发展人部门-注意收单
		return param13;
	}

	public void setParam13(String param13) {
		this.param13 = param13;
	}

	public String getParam14() {
	    param14 = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "develop_code_new");//新入库的发展人和发展点
		return param14;
	}

	public void setParam14(String param14) {
		this.param14 = param14;
	}

	public String getParam15() {
		param15 = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_BIRTH);// 生日
		return param15;
	}

	public void setParam15(String param15) {
		this.param15 = param15;
	}

	public String getReq_swift_num() {
		req_swift_num = "";
		String new_req_swift_num = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.COUPON_BATCH_ID);
		if (!StringUtil.isEmpty(new_req_swift_num)) {
			req_swift_num = new_req_swift_num;
		}
		return req_swift_num;
	}

	public void setReq_swift_num(String req_swift_num) {
		this.req_swift_num = req_swift_num;
	}

	public String getParam32() {
		return param32;
	}

	public void setParam32(String param32) {
		this.param32 = param32;
	}

	public String getParam33() {
		return param33;
	}

	public void setParam33(String param33) {
		this.param33 = param33;
	}

	public String getRzb_big_card_type() {
		return rzb_big_card_type;
	}

	public void setRzb_big_card_type(String rzb_big_card_type) {
		this.rzb_big_card_type = rzb_big_card_type;
	}

	public String getRzb_card_type() {
		return rzb_card_type;
	}

	public void setRzb_card_type(String rzb_card_type) {
		this.rzb_card_type = rzb_card_type;
	}

	public String getMaterial_code() {
		return material_code;
	}

	public void setMaterial_code(String material_code) {
		this.material_code = material_code;
	}

	public String getKi() {
		return ki;
	}

	public void setKi(String ki) {
		this.ki = ki;
	}

	public String getImsi() {
		imsi = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SIMID);
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getIs_esim() {
		return is_esim;
	}

	public void setIs_esim(String is_esim) {
		this.is_esim = is_esim;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getMachine_type() {
		return machine_type;
	}

	public void setMachine_type(String machine_type) {
		this.machine_type = machine_type;
	}

	public String getAttach_name() {
		return attach_name;
	}

	public void setAttach_name(String attach_name) {
		this.attach_name = attach_name;
	}

	public String getConfirm_code() {
		return confirm_code;
	}

	public void setConfirm_code(String confirm_code) {
		this.confirm_code = confirm_code;
	}

	public String getActivationcode() {
		return activationcode;
	}

	public void setActivationcode(String activationcode) {
		this.activationcode = activationcode;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getMatching_id() {
		return matching_id;
	}

	public void setMatching_id(String matching_id) {
		this.matching_id = matching_id;
	}

	public String getIsbacklogorder() {
		ICacheUtil cacheUtil = (ICacheUtil) SpringContextHolder.getBean("cacheUtil");
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(this.notNeedReqStrOrderId);
		String flag = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_FLAG");
		
		if(orderTree!=null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
				&& "1".equals(flag)){
			String deal_method = CommonDataFactory.getInstance().getAttrFieldValue(this.notNeedReqStrOrderId,
					"order_deal_method");
			
			//自定义流程
			if("2".equals(deal_method)){
				//线下：现场激活
				this.isbacklogorder = "0";
			}else{
				//线上：压单开户
				this.isbacklogorder = "1";
			}
		}else{
			String order_from = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest()
					.getOrder_from();

			String DELAY_TAG_ORDER_FROM = cacheUtil.getConfigInfo("DELAY_TAG_ORDER_FROM");
			if (DELAY_TAG_ORDER_FROM.contains(order_from)) {
				this.isbacklogorder = "1";
			} else {
				this.isbacklogorder = "0";
			}
		}
		
		return isbacklogorder;
	}

	public void setIsbacklogorder(String isbacklogorder) {
		this.isbacklogorder = isbacklogorder;
	}

	public OrderTreeBusiRequest getOrderTree() {
		return orderTree;
	}

	public void setOrderTree(OrderTreeBusiRequest orderTree) {
		this.orderTree = orderTree;
	}

	@Override
	public void check() throws ApiRuleException {
	}

	@Override
	public String getApiMethodName() {
		return "com.ztesoft.net.ecsord.params.ecaop.req.fuKaPreOpenReq";
	}

    public String getCert_num2() {
       return cert_num2 = orderTree.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0)
                .getOrderItemProdExtBusiRequest().getCert_num2();
    }

    public void setCert_num2(String cert_num2) {
        this.cert_num2 = cert_num2;
    }

}

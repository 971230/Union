package zte.net.ecsord.rule.tache;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.ztesoft.net.annotation.DroolsFact;
import com.ztesoft.net.annotation.DroolsFactField;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.auto.rule.vo.RuleConsts;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderRealNameInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;



/**
 * @author 王昌磊
 * 
 */

@DroolsFact(name = "流程环节", code = "tacheFact")
public class TacheFact extends AutoFact {

    
	private OrderTreeBusiRequest orderTreeBusiRequest;
	private String order_id;

	@DroolsFactField(name = "环节编码", ele_type = "checkbox", stype_code = "DC_ORDER_TACHE_NODE")
	private String tache_code;

	@DroolsFactField(name = "订单来源", ele_type = "checkbox", stype_code = "DC_MODE_SHOP_CODE")
	private String order_from;

	@DroolsFactField(name = "是否总部交互", ele_type = "radio", stype_code = "DC_IS_OR_NO")
	private String is_send_zb;

	// @DroolsFactField(name="是否WMS交互", ele_type="radio",
	// stype_code="DC_IS_OR_NO")
	// private String is_send_wms;

	@DroolsFactField(name = "商品类型", ele_type = "checkbox", stype_code = "DC_MODE_GOODS_TYPE")
	private String goods_type;

	// @DroolsFactField(name="货品类型", ele_type="checkbox",
	// stype_code="DC_PRODUCT_TYPE")
	// private String product_type;

	@DroolsFactField(name = "是否新用户", ele_type = "radio", stype_code = "DC_IS_OR_NO")
	private String is_new_user;

	// @DroolsFactField(name="是否实物单", ele_type="radio",
	// stype_code="DC_IS_OR_NO")
	// private String is_physics;

	/*
	 * @DroolsFactField(name="是否需要开户", ele_type="radio",
	 * stype_code="DC_IS_OR_NO") private String is_open_account;
	 */

	@DroolsFactField(name = "是否需要写卡", ele_type = "radio", stype_code = "DC_IS_OR_NO")
	private String is_write_card;

	// @DroolsFactField(name="是否支持一键开户", ele_type="radio",
	// stype_code="DC_IS_OR_NO")
	// private String is_easy_account;

	// @DroolsFactField(name="是否闪电送", ele_type="radio",
	// stype_code="DIC_SHIPPING_QUICK")
	// private String is_shipping_quick;

	/*
	 * @DroolsFactField(name="网别", ele_type="checkbox",
	 * stype_code="DC_MODE_NET_TYPE") private String net_type;
	 */

	/*
	 * @DroolsFactField(name="订单类型", ele_type="checkbox",
	 * stype_code="DC_ORDER_NEW_TYPE") private String order_type;
	 */

	@DroolsFactField(name = "生产模式", ele_type = "checkbox", stype_code = "DC_MODE_OPER_MODE")
	private String order_model;
	
	@DroolsFactField(name = "产品订购类型", ele_type = "checkbox", stype_code = "DC_OPT_TYPE")
    private String optType;
	/*
	 * @DroolsFactField(name="平台类型", ele_type="checkbox", stype_code="") private
	 * String plat_type;
	 */

	// @DroolsFactField(name="业务办理状态", ele_type="checkbox",
	// stype_code="DIC_BSS_STATUS")
	// private String bss_status;

	@DroolsFactField(name = "配送方式", ele_type = "checkbox", stype_code = "DC_MODE_SHIP_TYPE")
	private String sending_type;

	@DroolsFactField(name = "是否需要发货", ele_type = "radio", stype_code = "DC_IS_OR_NO")
	private String need_shipping;

	@DroolsFactField(name = "异常类型", ele_type = "checkbox", stype_code = "ORDER_ABNORMAL_TYPE")
	private String abnormal_type;

	@DroolsFactField(name = "号卡类型", ele_type = "checkbox", stype_code = "DC_MODE_CARD_TYPE")
	private String card_type;

	// @DroolsFactField(name="支付方式", ele_type="checkbox",
	// stype_code="DIC_PAY_METHOD")
	// private String pay_method;

	// @DroolsFactField(name="社会机定制机", ele_type="radio",
	// stype_code="DIC_CUSTOMIZATION")
	// private String is_society;

	/*
	 * @DroolsFactField(name="是否预约单", ele_type="radio",
	 * stype_code="DIC999_BESPOKE_FLAG") private String wm_isreservation_from;
	 */

	/*
	 * @DroolsFactField(name="参与活动", ele_type="checkbox",
	 * stype_code="DIC_999_PROD_CAT_ID") private String prod_cat_id;
	 * 
	 * @DroolsFactField(name="是否4G合约机", ele_type="radio",
	 * stype_code="DC_IS_OR_NO") private String is_heyue;
	 */

	// @DroolsFactField(name="供应商", ele_type="checkbox",
	// stype_code="DIC_SUPPLIER_ID")
	// private String supply_id;

	// @DroolsFactField(name="订单是否已送总部", ele_type="radio",
	// stype_code="DC_IS_OR_NO")
	// private String syn_ord_zb;

	/*
	 * @DroolsFactField(name="备注", ele_type="input", stype_code="") private
	 * String comments;
	 */

	// @DroolsFactField(name="收获人姓名", ele_type="input", stype_code="")
	// private String ship_name;

	// @DroolsFactField(name="是否新系统处理", ele_type="radio",
	// stype_code="DC_IS_OR_NO")
	// private String is_new_sys;

	/*
	 * @DroolsFactField(name="老生产模式", ele_type="checkbox",
	 * stype_code="DC_MODE_OPER_MODE") private String old_order_model;
	 */

	// @DroolsFactField(name="是否由商城发起退单申请", ele_type="radio",
	// stype_code="DC_IS_OR_NO")
	// private String is_zb_refund;

	/*
	 * @DroolsFactField(name="业务类型", ele_type="checkbox",
	 * stype_code="DC_BUSINESS_TYPE") private String busi_type;
	 */

	@DroolsFactField(name = "是否走AOP开户", ele_type = "radio", stype_code = "DIC_OPEN_SYSTEM")
	private String is_aop;

	@DroolsFactField(name = "号码状态标识", ele_type = "checkbox", stype_code = "DC_OCCUPIED_FLAG")
	private String occupiedFlag;

	// zengxianlian
	@DroolsFactField(name = "商品小类", ele_type = "checkbox", stype_code = "DC_GOODS_CAT_ID")
	private String goods_cat_id;

	// zengxianlian
	/*
	 * @DroolsFactField(name="客户类型", ele_type="checkbox",
	 * stype_code="DC_CUSTOMER_TYPE") private String customer_type;
	 */

	// zengxianlian
	@DroolsFactField(name = "地市", ele_type = "checkbox", stype_code = "DC_MODE_REGION")
	private String region;

	/*
	 * @DroolsFactField(name="终端操作状态", ele_type="checkbox",
	 * stype_code="DC_TERMI_OCCUPIED_FLAG") private String operation_status;
	 */

	// @DroolsFactField(name="bss号码操作状态", ele_type="checkbox",
	// stype_code="DC_BSS_OCCUPIED_FLAG")
	// private String bssOccupiedFlag;

	@DroolsFactField(name = "写卡结果状态", ele_type = "checkbox", stype_code = "WRITE_CARD_STATUS")
	private String write_card_status;

	// @DroolsFactField(name="买家留言", ele_type="input", stype_code="")
	// private String buyer_message;

	// zengxianlian
	@DroolsFactField(name = "物流公司", ele_type = "checkbox", stype_code = "DIC_LOGI_COMPANY")
	private String shipping_company;

	// Rapon
	// @DroolsFactField(name="库存状态", ele_type="checkbox",
	// stype_code="DIC_STOCK_STATE")
	// private String stock_state;

	// Rapon
	// @DroolsFactField(name="生产中心", ele_type="checkbox",
	// stype_code="DIC_PRODUCE_CENTER")
	// private String produce_center;

	// Rapon
	// @DroolsFactField(name="订单是否已发送WMS", ele_type="checkbox",
	// stype_code="DC_SYN_ORD_WMS")
	// private String syn_ord_wms;

	// Rapon
	// @DroolsFactField(name="代金券状态", ele_type="checkbox",
	// stype_code="DC_COUPONS_STATUS")
	// private String coupons_locked;

	// @DroolsFactField(name="是否续约类业务", ele_type="radio",
	// stype_code="DC_IS_OR_NO")
	// private String is_renew_contract;
	// @DroolsFactField(name="是否需要审核证件照", ele_type="radio",
	// stype_code="DC_IS_OR_NO")
	// private String is_examine_card;

	@DroolsFactField(name = "是否后向激活", ele_type = "radio", stype_code = "DC_IS_OR_NO")
	private String later_active_flag;

	@DroolsFactField(name = "后向激活激活状态", ele_type = "checkbox", stype_code = "DC_LATER_ACTIVE_STATUS")
	private String later_active_status;

	/*
	 * @DroolsFactField(name="激活系统", ele_type="radio",
	 * stype_code="DC_ACTIVE_SYSTEM") private String active_system;
	 */

	@DroolsFactField(name = "是否已支付", ele_type = "radio", stype_code = "DC_IS_OR_NO")
	private String is_pay;
	@DroolsFactField(name = "是否已开户", ele_type = "radio", stype_code = "DC_IS_OR_NO")
	private String is_open;
	@DroolsFactField(name = "BSS退款状态", ele_type = "checkbox", stype_code = "DC_BSS_REFUND_STAUS")
	private String bss_refund_status;

	/** ================================ */
	@DroolsFactField(name = "计算结果(是-否)", ele_type = "radio", stype_code = "DC_IS_OR_NO")
	private String calc_yn;
	// zengxianlian
	@DroolsFactField(name = "计算结果(文本)", ele_type = "input")
	private String calc_input;
	@DroolsFactField(name = "开户通道计算结果(总部-AOP-BSS)", ele_type = "radio", stype_code = "DIC_OPEN_SYSTEM")
	private String calc_open_channel;
	/*
	 * @DroolsFactField(name="号段计算结果", ele_type="checkbox",
	 * stype_code="DC_PHONE_PARA") private String phone_para;
	 */
	@DroolsFactField(name = "证件地址是否为空", ele_type = "radio", stype_code = "DC_IS_OR_NO")
	private String cert_addr;
	@DroolsFactField(name = "种子号码是否为空", ele_type = "radio", stype_code = "DC_IS_OR_NO")
    private String share_svc_num;
	@DroolsFactField(name = "订购主卡标识是否为空", ele_type = "radio", stype_code = "DC_IS_OR_NO")
    private String is_order_master;
	@DroolsFactField(name = "是否白卡", ele_type = "radio", stype_code = "DC_IS_OR_NO")
    private String is_blankcard;
	@DroolsFactField(name = "是否收费", ele_type = "radio", stype_code = "DC_IS_OR_NO")
	private String is_pay_shoufei;
	@DroolsFactField(name = "业务受理业务类型", ele_type = "checkbox", stype_code = "DC_BIZ_TYPE")
	private String biz_type;
	@DroolsFactField(name = "是否走BSS返销校验", ele_type = "radio", stype_code = "DC_IS_OR_NO")
	private String is_bss;
	/*
	 * @DroolsFactField(name="工号池编码", ele_type="radio",
	 * stype_code="DC_WORKER_POOL") private String worker_pool_id;
	 */
	@DroolsFactField(name = "是否退款", ele_type = "radio", stype_code = "DC_IS_OR_NO")
	private String is_refund;
	@DroolsFactField(name = "总商开户类型是否是手动开户", ele_type = "radio", stype_code = "DC_IS_OR_NO")
	private String zb_open_type;
	@DroolsFactField(name = "实收", ele_type = "input", stype_code = "")
	private String pay_money;

	/** ================================ */
	@DroolsFactField(name = "是否预判", ele_type = "radio", stype_code = "DC_IS_OR_NO")
	private String is_judge_address;
	@DroolsFactField(name = "是否实名", ele_type = "radio", stype_code = "DC_IS_OR_NO")
	private String is_real_name;
	@DroolsFactField(name = "宽带账号是否为空", ele_type = "radio", stype_code = "DC_IS_OR_NO")
	private String adsl_account;
	@DroolsFactField(name = "宽带接入方式", ele_type = "checkbox", stype_code = "DIC_SERVICE_TYPE")
	private String service_type;
	@DroolsFactField(name = "光猫ID是否为空", ele_type = "radio", stype_code = "DC_IS_OR_NO")
	private String moderm_id;
	@DroolsFactField(name = "支付类型", ele_type = "checkbox", stype_code = "DIC_PAY_TYPE")
	private String pay_type;
	@DroolsFactField(name = "意向单订单来源", ele_type = "checkbox", stype_code = "DC_MODE_SHOP_CODE")
	private String intent_order_from;

	private String src_tache_code;
	private String tar_tache_code;
	// 业务组件入参，不作为规则因子参与计算
	private String process_type;
	// 业务组件入参，不作为规则因子参与计算
	private String exception_from;
	// 业务组件入参，不作为规则因子参与计算
	private String exception_type;
	// 业务组件入参，不作为规则因子参与计算
	private String exception_desc;

	@Override
	public void exeAction(AutoFact fact, List<RuleConsts> consts) throws RuntimeException {
		// TODO Auto-generated method stub
	}

	@Override
	public String getObj_id() {
		// TODO Auto-generated method stub
		return order_id;
	}

	@Override
	public String getTrace_flow_id() {
		trace_flow_id = getOrderTree().getOrderExtBusiRequest().getFlow_trace_id();
		return trace_flow_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		// orderTreeBusiRequest =
		// CommonDataFactory.getInstance().getOrderTree(order_id);
		this.order_id = order_id;
	}

	public String getTache_code() {
		this.tache_code = getOrderTree().getOrderExtBusiRequest().getFlow_trace_id();
		return tache_code;
	}

	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}

	public String getOrder_from() {
		this.order_from = getOrderTree().getOrderExtBusiRequest().getOrder_from();
		return order_from;
	}

	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}

	public String getIs_send_zb() {
		is_send_zb = getOrderTree().getOrderExtBusiRequest().getSend_zb();
		return is_send_zb;
	}

	public void setIs_send_zb(String is_send_zb) {
		this.is_send_zb = is_send_zb;
	}

	// public String getIs_send_wms() {
	// is_send_wms = getOrderTree().getOrderExtBusiRequest().getIs_send_wms();
	// return is_send_wms;
	// }
	//
	// public void setIs_send_wms(String is_send_wms) {
	// this.is_send_wms = is_send_wms;
	// }

	public String getGoods_type() {
		this.goods_type = getOrderTree().getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getGoods_type();
		return goods_type;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	// public String getProduct_type() {
	// this.product_type =
	// CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id,
	// AttrConsts.PRODUCT_TYPE);
	// return product_type;
	// }
	//
	// public void setProduct_type(String product_type) {
	// this.product_type = product_type;
	// }

	public String getIs_new_user() {
		String is_old = getOrderTree().getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0)
				.getOrderItemProdExtBusiRequest().getIs_old();
		// String is_old =
		// CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id,
		// AttrConsts.IS_OLD);
		if (EcsOrderConsts.IS_OLD_1.equals(is_old)) {
			is_new_user = EcsOrderConsts.NO_DEFAULT_VALUE;
		} else {
			is_new_user = EcsOrderConsts.IS_DEFAULT_VALUE;
		}
		return is_new_user;
	}

	public void setIs_new_user(String is_new_user) {
		this.is_new_user = is_new_user;
	}

	// public String getIs_physics() {
	// is_physics =
	// CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id,
	// AttrConsts.IS_PHISICS);
	// return is_physics;
	// }
	//
	// public void setIs_physics(String is_physics) {
	// this.is_physics = is_physics;
	// }

	/*
	 * public String getIs_open_account() { is_open_account =
	 * getOrderTree().getOrderExtBusiRequest().getIs_account();; return
	 * is_open_account; }
	 * 
	 * public void setIs_open_account(String is_open_account) {
	 * this.is_open_account = is_open_account; }
	 */
	public String getIs_write_card() {
		is_write_card = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(), order_id,
				AttrConsts.IS_WRITE_CARD);
		return is_write_card;
	}

	public void setIs_write_card(String is_write_card) {
		this.is_write_card = is_write_card;
	}

	// public String getIs_easy_account() {
	// is_easy_account =
	// CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id,
	// AttrConsts.IS_EASY_ACCOUNT);
	// return is_easy_account;
	// }
	//
	// public void setIs_easy_account(String is_easy_account) {
	// this.is_easy_account = is_easy_account;
	// }
	//
	// public String getIs_shipping_quick() {
	// OrderExtBusiRequest orderExtBusiReq =
	// getOrderTree().getOrderExtBusiRequest();
	// this.is_shipping_quick = orderExtBusiReq.getShipping_quick();
	// return is_shipping_quick;
	// }
	//
	// public void setIs_shipping_quick(String is_shipping_quick) {
	// this.is_shipping_quick = is_shipping_quick;
	// }
	//
	/*
	 * public String getNet_type() { this.net_type =
	 * CommonDataFactory.getInstance(). getProductSpec(order_id,
	 * SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE); return net_type; }
	 * 
	 * public void setNet_type(String net_type) { this.net_type = net_type; }
	 */

	public String getOrder_model() {
		this.order_model = getOrderTree().getOrderExtBusiRequest().getOrder_model();
		return order_model;
	}

	public void setOrder_model(String order_model) {
		this.order_model = order_model;
	}

	
	/*
	 * public String getOrder_type() { this.order_type =
	 * getOrderTree().getOrderBusiRequest().getOrder_type(); return order_type;
	 * }
	 * 
	 * public void setOrder_type(String order_type) { this.order_type =
	 * order_type; }
	 * 
	 * public String getPlat_type() { this.plat_type =
	 * getOrderTree().getOrderExtBusiRequest().getPlat_type(); return plat_type;
	 * }
	 * 
	 * public void setPlat_type(String plat_type) { this.plat_type = plat_type;
	 * }
	 */

	// public String getBss_status() {
	// this.bss_status =
	// CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id,
	// AttrConsts.BSS_STATUS);
	// return bss_status;
	// }
	//
	// public void setBss_status(String bss_status) {
	// this.bss_status = bss_status;
	// }

	public String getOptType() {
	    IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
        String sql = "select t.opttype from  es_order_extvtl t  where t.source_from = '"
                + ManagerUtils.getSourceFrom() + "' and t.order_id = '" + order_id + "'";
        optType = baseDaoSupport.queryForString(sql);
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public String getSending_type() {
		this.sending_type = getOrderTree().getOrderBusiRequest().getShipping_type();
		return sending_type;
	}

	public void setSending_type(String sending_type) {
		this.sending_type = sending_type;
	}

	public String getAbnormal_type() {
		this.abnormal_type = getOrderTree().getOrderExtBusiRequest().getAbnormal_type();
		return abnormal_type;
	}

	public void setAbnormal_type(String abnormal_type) {
		this.abnormal_type = abnormal_type;
	}

	public String getCard_type() {
		this.card_type = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(), order_id,
				AttrConsts.SIM_TYPE);
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	// public String getPay_method() {
	// pay_method =
	// CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id,
	// AttrConsts.PAY_METHOD);
	// return pay_method;
	// }
	//
	// public void setPay_method(String pay_method) {
	// this.pay_method = pay_method;
	// }

	// public String getIs_society() {
	// String is_customized =
	// CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id,
	// AttrConsts.IS_CUSTOMIZED);
	// if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_customized)){
	// is_society = EcsOrderConsts.NO_DEFAULT_VALUE;
	// }
	// else if(EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_customized)){
	// is_society = EcsOrderConsts.IS_DEFAULT_VALUE;
	// }
	// return is_society;
	// }
	//
	// public void setIs_society(String is_society) {
	// this.is_society = is_society;
	// }

	/*
	 * public String getWm_isreservation_from() { wm_isreservation_from =
	 * getOrderTree().getOrderExtBusiRequest().getWm_isreservation_from();
	 * return wm_isreservation_from; }
	 * 
	 * public void setWm_isreservation_from(String wm_isreservation_from) {
	 * this.wm_isreservation_from = wm_isreservation_from; }
	 * 
	 * public String getProd_cat_id() { prod_cat_id =
	 * getOrderTree().getOrderExtBusiRequest().getProd_cat_id(); return
	 * prod_cat_id; }
	 * 
	 * public void setProd_cat_id(String prod_cat_id) { this.prod_cat_id =
	 * prod_cat_id; }
	 */

	@JSONField(deserialize = true, serialize = false)
	public OrderTreeBusiRequest getOrderTree() {
		if (orderTreeBusiRequest == null)
			orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id);
		return orderTreeBusiRequest;
	}

	public void removeOrderTree() {
		orderTreeBusiRequest = null;
	}

	public void toStringN() {
		// logger.info("================================================================>");
		// logger.info("zte.net.ecsord.rule.tache.TacheFact日志信息打印："+"订单编号："+this.order_id+"环节编码:"+this.tache_code+":"+"订单来源:"+this.order_from+"是否总部交互:"+this.is_send_zb
		// +"是否发送WMS："+this.is_send_wms+"商品类型："+this.goods_type+"是否新用户："+this.is_new_user
		// +"是否实物单:"+this.is_physics+"是否需要开户:"+this.is_open_account+"是否需要写卡:"+this.is_write_card+"是否支持一键开户："+this.is_easy_account+"是否闪电送:"+this.is_shipping_quick+"网别:"+this.net_type+"订单类型:"+this.order_type+":生产模式"+this.order_model
		// +"平台类型:"+this.plat_type+"配送方式:"+this.sending_type+"异常类型:"+this.abnormal_type+"号卡类型:"+this.card_type+"终端状态:"+this.operation_status);
		// logger.info("================================================================>");
	}

	/*
	 * public String getIs_heyue() { is_heyue = EcsOrderConsts.NO_DEFAULT_VALUE;
	 * String goods_type = getOrderTree().getOrderItemBusiRequests().get(0).
	 * getOrderItemExtBusiRequest().getGoods_type();
	 * if(SpecConsts.TYPE_ID_20002.equals(goods_type) &&
	 * EcsOrderConsts.NET_TYPE_4G.equals(this.getNet_type())){ is_heyue =
	 * EcsOrderConsts.IS_DEFAULT_VALUE; } return is_heyue; }
	 * 
	 * public void setIs_heyue(String is_heyue) { this.is_heyue = is_heyue; }
	 */

	// public String getSupply_id() {
	// this.supply_id =
	// CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id,
	// AttrConsts.SUPPLY_ID);
	// return supply_id;
	// }
	//
	// public void setSupply_id(String supply_id) {
	// this.supply_id = supply_id;
	// }
	//
	//
	//
	// public String getSyn_ord_zb() {
	// String order_from =
	// CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id,
	// AttrConsts.ORDER_FROM);
	// if(EcsOrderConsts.ORDER_FROM_10003.equals(order_from)){
	// return EcsOrderConsts.SYN_ORD_ZB_1;
	// }else{
	// syn_ord_zb = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
	// AttrConsts.SYN_ORD_ZB);
	// return syn_ord_zb;
	// }
	// }
	//
	// public void setSyn_ord_zb(String syn_ord_zb) {
	// this.syn_ord_zb = syn_ord_zb;
	// }

	public String getNeed_shipping() {
		need_shipping = getOrderTree().getOrderBusiRequest().getNeed_shipping();
		return need_shipping;
	}

	public void setNeed_shipping(String need_shipping) {
		this.need_shipping = need_shipping;
	}

	/*
	 * public String getComments() { comments =
	 * getOrderTree().getOrderBusiRequest().getRemark(); return comments; }
	 * 
	 * public void setComments(String comments) { this.comments = comments; }
	 */

	// public String getShip_name() {
	// ship_name = getOrderTree().getOrderBusiRequest().getShip_name();
	// return ship_name;
	// }
	//
	// public void setShip_name(String ship_name) {
	// this.ship_name = ship_name;
	// }
	//
	// public String getIs_new_sys(){
	// String sys_code =
	// CommonDataFactory.getInstance().getAttrFieldValue(order_id,
	// AttrConsts.SYS_CODE);
	// if(EcsOrderConsts.ORDER_SFLOW_NEW.equals(sys_code) ||
	// StringUtil.isEmpty(sys_code)){ //为空，也缺省为新系统处理
	// return EcsOrderConsts.IS_DEFAULT_VALUE;
	// }else{
	// return EcsOrderConsts.NO_DEFAULT_VALUE;
	// }
	// }
	// public void setIs_new_sys(String is_new_sys){
	// this.is_new_sys = is_new_sys;
	// }

	/*
	 * public String getOld_order_model() { this.old_order_model =
	 * getOrderTree().getOrderExtBusiRequest().getOld_order_model(); return
	 * old_order_model; }
	 * 
	 * public void setOld_order_model(String old_order_model) {
	 * this.old_order_model = old_order_model; }
	 */

	public String getSrc_tache_code() {
		return src_tache_code;
	}

	public void setSrc_tache_code(String src_tache_code) {
		this.src_tache_code = src_tache_code;
	}

	public String getTar_tache_code() {
		return tar_tache_code;
	}

	public void setTar_tache_code(String tar_tache_code) {
		this.tar_tache_code = tar_tache_code;
	}

	public String getProcess_type() {
		return process_type;
	}

	public void setProcess_type(String process_type) {
		this.process_type = process_type;
	}

	public String getException_from() {
		return exception_from;
	}

	public void setException_from(String exception_from) {
		this.exception_from = exception_from;
	}

	public String getException_type() {
		return exception_type;
	}

	public void setException_type(String exception_type) {
		this.exception_type = exception_type;
	}

	public String getException_desc() {
		return exception_desc;
	}

	public void setException_desc(String exception_desc) {
		this.exception_desc = exception_desc;
	}

	// public String getIs_zb_refund(){
	// String zb_refund_status =
	// CommonDataFactory.getInstance().getAttrFieldValue(order_id,
	// AttrConsts.ZB_REFUND_STATUS);
	// if(EcsOrderConsts.ZB_REFUND_STATUS_1.equals(zb_refund_status)){
	// return EcsOrderConsts.IS_DEFAULT_VALUE;
	// }else{
	// return EcsOrderConsts.NO_DEFAULT_VALUE;
	// }
	// }
	// public void setIs_zb_refund(String is_zb_refund){
	// this.is_zb_refund = is_zb_refund;
	// }

	/*
	 * public String getBusi_type() { busi_type =
	 * CommonDataFactory.getInstance()
	 * .getAttrFieldValue(getOrderTree(),order_id,
	 * AttrConsts.SPECIAL_BUSI_TYPE); return busi_type; }
	 * 
	 * public void setBusi_type(String busi_type) { this.busi_type = busi_type;
	 * }
	 */

	public String getIs_aop() {
		is_aop = getOrderTree().getOrderExtBusiRequest().getIs_aop();
		return is_aop;
	}

	public void setIs_aop(String is_aop) {
		this.is_aop = is_aop;
	}

	public String getOccupiedFlag() {
		OrderPhoneInfoBusiRequest phoneInfo = getOrderTree().getPhoneInfoBusiRequest();
		if (phoneInfo != null) {
			occupiedFlag = phoneInfo.getOccupiedFlag();
		}
		return occupiedFlag;
	}

	public void setOccupiedFlag(String occupiedFlag) {
		this.occupiedFlag = occupiedFlag;
	}

	public String getCalc_yn() {
		return calc_yn;
	}

	public void setCalc_yn(String calc_yn) {
		this.calc_yn = calc_yn;
	}

	public String getCalc_input() {
		return calc_input;
	}

	public void setCalc_input(String calc_input) {
		this.calc_input = calc_input;
	}

	/*
	 * public String getCustomer_type() { customer_type =
	 * CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),
	 * order_id, AttrConsts.CUSTOMER_TYPE); return customer_type; }
	 * 
	 * public void setCustomer_type(String customer_type) { this.customer_type =
	 * customer_type; }
	 */

	public String getGoods_cat_id() {
		goods_cat_id = getOrderTree().getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getGoods_cat_id();
		return goods_cat_id;
	}

	public void setGoods_cat_id(String goods_cat_id) {
		this.goods_cat_id = goods_cat_id;
	}

	public String getRegion() {
		region = getOrderTree().getOrderExtBusiRequest().getOrder_city_code();
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	/*
	 * public String getOperation_status() { List<AttrTmResourceInfoBusiRequest>
	 * list = getOrderTree().getTmResourceInfoBusiRequests(); if(list!=null &&
	 * list.size()>0){ operation_status = list.get(0).getOperation_status();
	 * }else{ operation_status = EcsOrderConsts.OCCUPIEDFLAG_0; } return
	 * operation_status; }
	 * 
	 * public void setOperation_status(String operation_status) {
	 * this.operation_status = operation_status; }
	 */

	// public String getBssOccupiedFlag() {
	// OrderPhoneInfoBusiRequest phoneInfo =
	// CommonDataFactory.getInstance().getOrderTree(order_id).getPhoneInfoBusiRequest();
	// if (phoneInfo != null) {
	// bssOccupiedFlag = phoneInfo.getBssOccupiedFlag();
	// }
	// return bssOccupiedFlag;
	// }
	//
	// public void setBssOccupiedFlag(String bssOccupiedFlag) {
	// this.bssOccupiedFlag = bssOccupiedFlag;
	// }

	public String getWrite_card_status() {
		write_card_status = getOrderTree().getOrderExtBusiRequest().getWrite_card_status();
		return write_card_status;
	}

	public void setWrite_card_status(String write_card_status) {
		this.write_card_status = write_card_status;
	}

	// public String getBuyer_message() {
	// buyer_message =
	// CommonDataFactory.getInstance().getAttrFieldValue(order_id,
	// AttrConsts.BUYER_MESSAGE);
	// return buyer_message;
	// }
	//
	// public void setBuyer_message(String buyer_message) {
	// this.buyer_message = buyer_message;
	// }

	public String getShipping_company() {
		List<OrderDeliveryBusiRequest> deliveryList = getOrderTree().getOrderDeliveryBusiRequests();
		shipping_company = "";
		if (null != deliveryList && deliveryList.size() > 0) {
			for (OrderDeliveryBusiRequest delivery : deliveryList) {
				if (StringUtil.equals("0", delivery.getDelivery_type())) {
					shipping_company = delivery.getShipping_company();
				}
			}
		}
		return shipping_company;
	}

	public void setShipping_company(String shipping_company) {
		this.shipping_company = shipping_company;
	}

	// public String getStock_state() {
	// stock_state = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
	// AttrConsts.STOCK_STATE);
	// return stock_state;
	// }
	//
	// public void setStock_state(String stock_state) {
	// this.stock_state = stock_state;
	// }
	//
	// public String getProduce_center() {
	// produce_center =
	// CommonDataFactory.getInstance().getAttrFieldValue(order_id,
	// AttrConsts.PRODUCE_CENTER);
	// return produce_center;
	// }
	//
	// public void setProduce_center(String produce_center) {
	// this.produce_center = produce_center;
	// }
	//
	// public String getSyn_ord_wms() {
	// syn_ord_wms = CommonDataFactory.getInstance().getAttrFieldValue(order_id,
	// AttrConsts.SYN_ORD_WMS);
	// return syn_ord_wms;
	// }
	//
	// public void setSyn_ord_wms(String syn_ord_wms) {
	// this.syn_ord_wms = syn_ord_wms;
	// }
	//
	// public String getCoupons_locked() {
	// coupons_locked =
	// CommonDataFactory.getInstance().getAttrFieldValue(order_id,
	// AttrConsts.COUPONS_LOCKED);
	// return coupons_locked;
	// }
	//
	// public void setCoupons_locked(String coupons_locked) {
	// this.coupons_locked = coupons_locked;
	// }

	public String getCalc_open_channel() {
		return calc_open_channel;
	}

	public void setCalc_open_channel(String calc_open_channel) {
		this.calc_open_channel = calc_open_channel;
	}

	// public String getIs_renew_contract() {
	// is_renew_contract =
	// CommonDataFactory.getInstance().getAttrFieldValue(order_id,
	// AttrConsts.EXTENSION_TAG);
	// return is_renew_contract;
	// }
	//
	// public void setIs_renew_contract(String is_renew_contract) {
	// this.is_renew_contract = is_renew_contract;
	// }

	// public String getIs_examine_card() {
	// is_examine_card =
	// CommonDataFactory.getInstance().getAttrFieldValue(order_id,
	// AttrConsts.IS_EXAMINE_CARD);
	// return is_examine_card;
	// }
	//
	// public void setIs_examine_card(String is_examine_card) {
	// this.is_examine_card = is_examine_card;
	// }
	/*
	 * public String getPhone_para() { return phone_para; }
	 * 
	 * public void setPhone_para(String phone_para) { this.phone_para =
	 * phone_para; }
	 */
	public String getLater_active_flag() {
		this.later_active_flag = EcsOrderConsts.NO_DEFAULT_VALUE;
		OrderRealNameInfoBusiRequest realNameBus = getOrderTree().getOrderRealNameInfoBusiRequest();
		if (null != realNameBus) {
			later_active_flag = realNameBus.getLater_active_flag();
		}
		return later_active_flag;
	}

	public void setLater_active_flag(String later_active_flag) {
		this.later_active_flag = later_active_flag;
	}

	public String getLater_active_status() {
		OrderRealNameInfoBusiRequest realNameBus = getOrderTree().getOrderRealNameInfoBusiRequest();
		if (null != realNameBus) {
			this.later_active_status = realNameBus.getActive_flag();
		}
		return later_active_status;
	}

	public void setLater_active_status(String later_active_status) {
		this.later_active_status = later_active_status;
	}

	/*
	 * public String getActive_system() { OrderRealNameInfoBusiRequest
	 * realNameBus = getOrderTree().getOrderRealNameInfoBusiRequest(); if(null
	 * != realNameBus){ this.active_system = realNameBus.getActive_system(); }
	 * return active_system; }
	 * 
	 * public void setActive_system(String active_system) { this.active_system =
	 * active_system; }
	 */

	public String getCert_addr() {
		String cert_addrress = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_ADDRESS);
		if (StringUtils.isEmpty(cert_addrress)) {
			return EcsOrderConsts.IS_DEFAULT_VALUE;
		} else {
			return EcsOrderConsts.NO_DEFAULT_VALUE;
		}
	}
	
	/**
	 * sgf 添加用户种子号码
	 */
	public String getShare_svc_num() {
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		String sql = "select decode(v.share_svc_num, '', i.share_svc_num, v.share_svc_num) as share_svc_num,decode(v.seed_user_id, '', i.seed_user_id, v.seed_user_id) as seed_user_id from es_order_extvtl v left join es_order_intent i on v.order_id = i.order_id where v.source_from = '"
				+ ManagerUtils.getSourceFrom() + "' and v.order_id = '" + order_id + "'";
		List<Map<String, String>> list = baseDaoSupport.queryForList(sql);
		if (!StringUtils.isEmpty(list.get(0).get("share_svc_num"))
				|| !StringUtils.isEmpty(list.get(0).get("seed_user_id"))) {
			return EcsOrderConsts.NO_DEFAULT_VALUE;
		} else {
			return EcsOrderConsts.IS_DEFAULT_VALUE;
		}
	}
    public void setShare_svc_num(String share_svc_num) {//0 都 1 是
        this.share_svc_num = share_svc_num;
    }
    
    public String getIs_order_master() {
        IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
        String sql = "select t.is_order_master from  es_order_extvtl t  where t.source_from = '"
                + ManagerUtils.getSourceFrom() + "' and t.order_id = '" + order_id + "'";
        String is_order_master = baseDaoSupport.queryForString(sql);
        if (StringUtils.isEmpty(is_order_master) || "0".equals(is_order_master)) {
            return EcsOrderConsts.IS_DEFAULT_VALUE;
        } else {
            return EcsOrderConsts.NO_DEFAULT_VALUE;
        }
    }

    public void setIs_order_master(String is_order_master) {
        this.is_order_master = is_order_master;
    }
    
    public String getIs_blankcard() {
        IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
        String sql = "select t.is_blankcard from  es_order_extvtl t  where t.source_from = '"
                + ManagerUtils.getSourceFrom() + "' and t.order_id = '" + order_id + "'";
        String is_order_master = baseDaoSupport.queryForString(sql);
        if (StringUtils.isEmpty(is_order_master) || "1".equals(is_order_master)) {//默认白卡    是否白卡字段判断是否同步卡数据
            return EcsOrderConsts.IS_DEFAULT_VALUE;
        } else {
            return EcsOrderConsts.NO_DEFAULT_VALUE;
        }
    }

    public void setIs_blankcard(String is_blankcard) {
        this.is_blankcard = is_blankcard;
    }

    public void setCert_addr(String cert_addr) {
		this.cert_addr = cert_addr;
	}

	public String getIs_pay() {
		is_pay = getOrderTree().getOrderBusiRequest().getPay_status() == null ? ""
				: getOrderTree().getOrderBusiRequest().getPay_status().toString();// 1：已支付，0：未支付
		return is_pay;
	}

	public void setIs_pay(String is_pay) {
		this.is_pay = is_pay;
	}

	public String getIs_open() {
		is_open = getOrderTree().getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getAccount_status();// 1：开户成功，0：开户失败
		return is_open;
	}

	public void setIs_open(String is_open) {
		this.is_open = is_open;
	}

	public String getIs_pay_shoufei() {
		is_pay_shoufei = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_PAY);// 是否收费,0否;1是
		return is_pay_shoufei;
	}

	public void setIs_pay_shoufei(String is_pay_shoufei) {
		this.is_pay_shoufei = is_pay_shoufei;
	}

	public String getBiz_type() {
		biz_type = CommonDataFactory.getInstance().getGoodSpec(order_id, null, AttrConsts.BIZ_TYPE);
		return biz_type;
	}

	public void setBiz_type(String biz_type) {
		this.biz_type = biz_type;
	}

	public String getIs_bss() {
		String new_is_bss = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "is_bss");
		if (StringUtil.isEmpty(new_is_bss)) {
			is_bss = "1";
		} else {
			is_bss = new_is_bss;
		}
		return is_bss;
	}

	public void setIs_bss(String is_bss) {
		this.is_bss = is_bss;
	}

	public String getBss_refund_status() {
		bss_refund_status = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS);
		return bss_refund_status;
	}

	public void setBss_refund_status(String bss_refund_status) {
		this.bss_refund_status = bss_refund_status;
	}

	/*
	 * public String getWorker_pool_id() { return worker_pool_id; }
	 * 
	 * public void setWorker_pool_id(String worker_pool_id) {
	 * this.worker_pool_id = worker_pool_id; }
	 */
	public String getIs_refund() {
		is_refund = getOrderTree().getOrderExtBusiRequest().getIs_refund();
		return is_refund;
	}

	public void setIs_refund(String is_refund) {
		this.is_refund = is_refund;
	}

	public String getZb_open_type() {
		zb_open_type = getOrderTree().getOrderExtBusiRequest().getZb_open_type();
		return zb_open_type;
	}

	public void setZb_open_type(String zb_open_type) {
		this.zb_open_type = zb_open_type;
	}

	public String getPay_money() {
		pay_money = (int) (getOrderTree().getOrderBusiRequest().getPaymoney() * 1000) + "";
		return pay_money;
	}

	public void setPay_money(String pay_money) {
		this.pay_money = pay_money;
	}

	public String getIs_judge_address() {
		is_judge_address = getOrderTree().getOrderAdslBusiRequest().get(0).getIs_judge_address();// 0-未预判
																									// 1-已预判
		return is_judge_address;
	}

	public void setIs_judge_address(String is_judge_address) {
		this.is_judge_address = is_judge_address;
	}

	public String getIs_real_name() {
		is_real_name = getOrderTree().getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0)
				.getOrderItemProdExtBusiRequest().getIs_real_name();// 0-未实名
																	// 1-已实名
		return is_real_name;
	}

	public void setIs_real_name(String is_real_name) {
		this.is_real_name = is_real_name;
	}

	public String getPay_type() {
		this.pay_type = getOrderTree().getOrderExtBusiRequest().getPay_type();
		return this.pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getAdsl_account() {
		return adsl_account;
	}

	public void setAdsl_account(String adsl_account) {
		this.adsl_account = adsl_account;
	}

	public String getModerm_id() {
		return moderm_id;
	}

	public void setModerm_id(String moderm_id) {
		this.moderm_id = moderm_id;
	}

	public String getService_type() {
		return service_type;
	}

	
	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public String getIntent_order_from() {
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		String sql = " select t.source_system_type from es_order_intent t where t.source_from = '"
				+ ManagerUtils.getSourceFrom() + "' and t.order_id = '" + order_id + "'";
		List<Map<String, String>> list = baseDaoSupport.queryForList(sql);
		if(list.size()>0){
			Map map = list.get(0);
			String a = map.get("source_system_type").toString();
			intent_order_from = a;
		}else{
			intent_order_from = "-1";
		}
		return intent_order_from;
	}

	public void setIntent_order_from(String intent_order_from) {
		this.intent_order_from = intent_order_from;
	}
}

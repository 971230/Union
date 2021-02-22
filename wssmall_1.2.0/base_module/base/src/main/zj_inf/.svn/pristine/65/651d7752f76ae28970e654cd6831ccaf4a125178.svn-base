package zte.net.ecsord.rule.orderexp;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.AttrTmResourceInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ord.params.busi.req.OrderQueueBusiRequest;

import com.alibaba.fastjson.annotation.JSONField;
import com.ztesoft.net.annotation.DroolsFact;
import com.ztesoft.net.annotation.DroolsFactField;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.auto.rule.vo.RuleConsts;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

@DroolsFact(name="异常单过滤因子",code="orderExpFilterFact")
public class OrderExpFilterFact extends AutoFact {
	
	private static final long serialVersionUID = 1L;
	
	//支持根据条件配制过滤器，条件至少包括：接口ID、商城、地市、生产模式、商品类型、商品小类、关键字
	
	private OrderTreeBusiRequest orderTreeBusiRequest;
	private String order_id;
	private String obj_type;
	private String order_source;
	
	@DroolsFactField(name="接口编码", ele_type="checkbox", stype_code="DC_ORDER_EXP_SEARCH_ID")
	private String search_id;
	
	@DroolsFactField(name="关键字", ele_type="checkbox", stype_code="DC_ORDER_EXP_KEY_ID")
	private String key_id;
	
	@DroolsFactField(name="环节编码", ele_type="checkbox", stype_code="DC_ORDER_TACHE_NODE")
	private String tache_code;
	
	@DroolsFactField(name="订单来源", ele_type="checkbox", stype_code="DC_MODE_SHOP_CODE")
	private String order_from;
	
	@DroolsFactField(name="是否总部交互", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String is_send_zb;
	
	@DroolsFactField(name="是否WMS交互", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String is_send_wms;
	
	@DroolsFactField(name="商品类型", ele_type="checkbox", stype_code="DC_MODE_GOODS_TYPE")
	private String goods_type;
	
	@DroolsFactField(name="货品类型", ele_type="checkbox", stype_code="DC_PRODUCT_TYPE")
	private String product_type;
	
	@DroolsFactField(name="是否新用户", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String is_new_user;
	
	@DroolsFactField(name="是否实物单", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String is_physics;
	
	@DroolsFactField(name="是否需要开户", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String is_open_account;
	
	@DroolsFactField(name="是否需要写卡", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String is_write_card;
	
	@DroolsFactField(name="是否支持一键开户", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String is_easy_account;
	
	@DroolsFactField(name="是否闪电送", ele_type="radio", stype_code="DIC_SHIPPING_QUICK")
	private String is_shipping_quick;
	
	@DroolsFactField(name="网别", ele_type="checkbox", stype_code="DC_MODE_NET_TYPE")
	private String net_type;
	
	@DroolsFactField(name="生产模式", ele_type="checkbox", stype_code="DC_MODE_OPER_MODE")
	private String order_model;
	
	@DroolsFactField(name="订单类型", ele_type="checkbox", stype_code="DC_ORDER_NEW_TYPE")
	private String order_type;
	
	@DroolsFactField(name="平台类型", ele_type="checkbox", stype_code="")
	private String plat_type;
	
	@DroolsFactField(name="配送方式", ele_type="checkbox", stype_code="DC_MODE_SHIP_TYPE")
	private String sending_type;
	
	@DroolsFactField(name="是否需要发货", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String need_shipping;
	
	@DroolsFactField(name="异常类型", ele_type="checkbox", stype_code="ORDER_ABNORMAL_TYPE")
	private String abnormal_type;
	
	@DroolsFactField(name="号卡类型", ele_type="checkbox", stype_code="DC_MODE_CARD_TYPE")
	private String card_type;
	
	@DroolsFactField(name="支付方式", ele_type="checkbox", stype_code="DIC_PAY_METHOD")
	private String pay_method;
	
	@DroolsFactField(name="社会机定制机", ele_type="radio", stype_code="DIC_CUSTOMIZATION")
	private String is_society;
	
	@DroolsFactField(name="是否预约单", ele_type="radio", stype_code="DIC999_BESPOKE_FLAG")
	private String wm_isreservation_from;
	
	@DroolsFactField(name="参与活动", ele_type="checkbox", stype_code="DIC_999_PROD_CAT_ID")
	private String prod_cat_id;
	
	@DroolsFactField(name="是否4G合约机", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String is_heyue;
	
	@DroolsFactField(name="供应商", ele_type="checkbox", stype_code="DIC_SUPPLIER_ID")
	private String supply_id;

	@DroolsFactField(name="订单是否已送总部", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String syn_ord_zb;
	
	@DroolsFactField(name="备注", ele_type="input", stype_code="")
	private String comments;
	
	@DroolsFactField(name="收获人姓名", ele_type="input", stype_code="")
	private String ship_name;
	
	@DroolsFactField(name="是否新系统处理", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String is_new_sys;
	
	@DroolsFactField(name="老生产模式", ele_type="checkbox", stype_code="DC_MODE_OPER_MODE")
	private String old_order_model;
	
	@DroolsFactField(name="是否由商城发起退单申请", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String is_zb_refund;
	
	@DroolsFactField(name="业务类型", ele_type="checkbox", stype_code="DC_BUSINESS_TYPE")
	private String busi_type;
	
	@DroolsFactField(name="是否走AOP开户", ele_type="radio", stype_code="DC_IS_OR_NO")
	private String is_aop;
	
	@DroolsFactField(name="号码状态标识", ele_type="checkbox", stype_code="DC_OCCUPIED_FLAG")
	private String occupiedFlag;
	
	@DroolsFactField(name="商品小类", ele_type="checkbox", stype_code="DC_GOODS_CAT_ID")
	private String goods_cat_id;
	
	@DroolsFactField(name="客户类型", ele_type="checkbox", stype_code="DC_CUSTOMER_TYPE")
	private String customer_type;

	@DroolsFactField(name="地市", ele_type="checkbox", stype_code="DC_MODE_REGION")
	private String region;	

	@DroolsFactField(name="终端操作状态", ele_type="checkbox", stype_code="DC_TERMI_OCCUPIED_FLAG")
	private String operation_status;
	
	@DroolsFactField(name="bss号码操作状态", ele_type="checkbox", stype_code="DC_BSS_OCCUPIED_FLAG")
	private String bssOccupiedFlag;
	
	@DroolsFactField(name="写卡结果状态", ele_type="checkbox", stype_code="WRITE_CARD_STATUS")
	private String write_card_status;
	
	@DroolsFactField(name="买家留言", ele_type="input", stype_code="")
	private String buyer_message;
	
	@Override
	public void exeAction(AutoFact fact, List<RuleConsts> consts)
			throws RuntimeException {
		// TODO Auto-generated method stub
	}

	@Override
	public String getObj_id() {
		// TODO Auto-generated method stub
		return order_id;
	}

	@Override
	public String getTrace_flow_id() {
		return "";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	@JSONField(deserialize=true,serialize=false)
	public OrderTreeBusiRequest getOrderTree(){
		if(orderTreeBusiRequest ==null)
			orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id);
		return orderTreeBusiRequest;
	}
	
	public String getSearch_id() {
		return search_id;
	}

	public void setSearch_id(String search_id) {
		this.search_id = search_id;
	}

	public String getKey_id() {
		return key_id;
	}

	public void setKey_id(String key_id) {
		this.key_id = key_id;
	}
	
	public String getTache_code() {
		this.tache_code = getOrderTree().getOrderExtBusiRequest().getFlow_trace_id();
		return tache_code;
	}

	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}
	
	public String getOrder_from() {
		this.order_from = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.ORDER_FROM);
		return order_from;
	}

	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}
	
	public String getGoods_type() {
		this.goods_type = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.GOODS_TYPE);
		return goods_type;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	public String getProduct_type() {
		this.product_type = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.PRODUCT_TYPE);
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}
	
	public String getIs_send_zb() {
		is_send_zb = getOrderTree().getOrderExtBusiRequest().getSend_zb();
		return is_send_zb;
	}

	public void setIs_send_zb(String is_send_zb) {
		this.is_send_zb = is_send_zb;
	}
	
	public String getIs_send_wms() {
		is_send_wms = getOrderTree().getOrderExtBusiRequest().getIs_send_wms();
		return is_send_wms;
	}

	public void setIs_send_wms(String is_send_wms) {
		this.is_send_wms = is_send_wms;
	}
	
	public String getIs_new_user() {
		String is_old = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.IS_OLD);
		if(EcsOrderConsts.IS_OLD_1.equals(is_old)){
			is_new_user = EcsOrderConsts.NO_DEFAULT_VALUE;
		}else{
			is_new_user = EcsOrderConsts.IS_DEFAULT_VALUE;
		}
		return is_new_user;
	}

	public void setIs_new_user(String is_new_user) {
		this.is_new_user = is_new_user;
	}

	public String getIs_physics() {
		is_physics = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.IS_PHISICS);
		return is_physics;
	}

	public void setIs_physics(String is_physics) {
		this.is_physics = is_physics;
	}

	public String getIs_open_account() {
		is_open_account = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.IS_ACCOUNT);
		return is_open_account;
	}

	public void setIs_open_account(String is_open_account) {
		this.is_open_account = is_open_account;
	}

	public String getIs_write_card() {
		is_write_card = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.IS_WRITE_CARD);
		return is_write_card;
	}

	public void setIs_write_card(String is_write_card) {
		this.is_write_card = is_write_card;
	}

	public String getIs_easy_account() {
		is_easy_account = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.IS_EASY_ACCOUNT);
		return is_easy_account;
	}

	public void setIs_easy_account(String is_easy_account) {
		this.is_easy_account = is_easy_account;
	}

	public String getIs_shipping_quick() {
		OrderExtBusiRequest orderExtBusiReq = getOrderTree().getOrderExtBusiRequest();
		this.is_shipping_quick = orderExtBusiReq.getShipping_quick();
		return is_shipping_quick;
	}

	public void setIs_shipping_quick(String is_shipping_quick) {
		this.is_shipping_quick = is_shipping_quick;
	}
	
	public String getNet_type() {
		this.net_type = CommonDataFactory.getInstance().
				getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		return net_type;
	}

	public void setNet_type(String net_type) {
		this.net_type = net_type;
	}
	
	public String getOrder_model() {
		this.order_model = getOrderTree().getOrderExtBusiRequest().getOrder_model();
		return order_model;
	}

	public void setOrder_model(String order_model) {
		this.order_model = order_model;
	}
	
	public String getOrder_type() {
		this.order_type = CommonDataFactory.getInstance()
				.getAttrFieldValue(getOrderTree(),order_id, AttrConsts.ORDER_TYPE);
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	
	public String getPlat_type() {
		this.plat_type = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.PLAT_TYPE);
		return plat_type;
	}

	public void setPlat_type(String plat_type) {
		this.plat_type = plat_type;
	}

	public String getSending_type() {
		if(EcsOrderConsts.EXPINST_REL_OBJ_TYPE_ORDER.equals(obj_type)) {
			this.sending_type = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.SENDING_TYPE);
		}else if(EcsOrderConsts.EXPINST_REL_OBJ_TYPE_ORDER_QUEUE.equals(obj_type)){
			OrderQueueBusiRequest queue = CommonDataFactory.getInstance().getOrderQueueFor(null, obj_id);
			if(queue != null && StringUtils.isNotEmpty(queue.getContents())) {
				int idx = queue.getContents().indexOf("shipping_type");
				if(idx != -1) {
					String start = queue.getContents().substring(idx).replaceAll(" ", "");
					String temp = start.substring(start.indexOf("\"")+3);
					sending_type = temp.substring(0, temp.indexOf("\""));
				}
			}
		}
		return sending_type;
	}

	public void setSending_type(String sending_type) {
		this.sending_type = sending_type;
	}

	public String getAbnormal_type() {
		this.abnormal_type=getOrderTree().getOrderExtBusiRequest().getAbnormal_type();
		return abnormal_type;
	}

	public void setAbnormal_type(String abnormal_type) {
		this.abnormal_type = abnormal_type;
	}

	public String getCard_type() {
		this.card_type = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.SIM_TYPE);
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	
	public String getPay_method() {
		pay_method = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.PAY_METHOD);
		return pay_method;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}

	public String getIs_society() {
		String is_customized = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.IS_CUSTOMIZED);
		if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_customized)){
			is_society = EcsOrderConsts.NO_DEFAULT_VALUE;
		}
		else if(EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_customized)){
			is_society = EcsOrderConsts.IS_DEFAULT_VALUE;
		}
		return is_society;
	}

	public void setIs_society(String is_society) {
		this.is_society = is_society;
	}
	
	public String getWm_isreservation_from() {
		wm_isreservation_from = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.WM_ISRESERVATION_FROM);
		return wm_isreservation_from;
	}

	public void setWm_isreservation_from(String wm_isreservation_from) {
		this.wm_isreservation_from = wm_isreservation_from;
	}

	public String getProd_cat_id() {
		prod_cat_id = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.PROD_CAT_ID);
		return prod_cat_id;
	}

	public void setProd_cat_id(String prod_cat_id) {
		this.prod_cat_id = prod_cat_id;
	}
	
	public String getIs_heyue() {
		is_heyue = EcsOrderConsts.NO_DEFAULT_VALUE;
		String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		if(SpecConsts.TYPE_ID_20002.equals(goods_type) && EcsOrderConsts.NET_TYPE_4G.equals(this.getNet_type())){
			is_heyue = EcsOrderConsts.IS_DEFAULT_VALUE;
		}
		return is_heyue;
	}

	public void setIs_heyue(String is_heyue) {
		this.is_heyue = is_heyue;
	}


	public String getSupply_id() {
		this.supply_id = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.SUPPLY_ID);
		return supply_id;
	}

	public void setSupply_id(String supply_id) {
		this.supply_id = supply_id;
	}
	

	
	public String getSyn_ord_zb() {		
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.ORDER_FROM);
		if(EcsOrderConsts.ORDER_FROM_10003.equals(order_from)){
			return EcsOrderConsts.SYN_ORD_ZB_1;
		}else{
			syn_ord_zb = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SYN_ORD_ZB);
			return syn_ord_zb;
		}		
	}

	public void setSyn_ord_zb(String syn_ord_zb) {
		this.syn_ord_zb = syn_ord_zb;
	}

	public String getNeed_shipping() {
		need_shipping = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.NEED_SHIPPING);
		return need_shipping;
	}

	public void setNeed_shipping(String need_shipping) {
		this.need_shipping = need_shipping;
	}
	
	public String getComments() {
		comments = getOrderTree().getOrderBusiRequest().getRemark();
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getShip_name() {
		ship_name = getOrderTree().getOrderBusiRequest().getShip_name();
		return ship_name;
	}

	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}
	
	public String getIs_new_sys(){
		String sys_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SYS_CODE);
		if(EcsOrderConsts.ORDER_SFLOW_NEW.equals(sys_code) || StringUtil.isEmpty(sys_code)){ //为空，也缺省为新系统处理
			return EcsOrderConsts.IS_DEFAULT_VALUE;
		}else{
			return EcsOrderConsts.NO_DEFAULT_VALUE;
		}
	}
	public void setIs_new_sys(String is_new_sys){
		this.is_new_sys = is_new_sys;
	}
	
	public String getOld_order_model() {
		this.old_order_model = getOrderTree().getOrderExtBusiRequest().getOld_order_model();
		return old_order_model;
	}

	public void setOld_order_model(String old_order_model) {
		this.old_order_model = old_order_model;
	}

	public String getIs_zb_refund(){
		String zb_refund_status = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ZB_REFUND_STATUS);
		if(EcsOrderConsts.ZB_REFUND_STATUS_1.equals(zb_refund_status)){
			return EcsOrderConsts.IS_DEFAULT_VALUE;
		}else{
			return EcsOrderConsts.NO_DEFAULT_VALUE;
		}
	}
	public void setIs_zb_refund(String is_zb_refund){
		this.is_zb_refund = is_zb_refund;
	}
	
	public String getBusi_type() {
		busi_type = CommonDataFactory.getInstance()
				.getAttrFieldValue(getOrderTree(),order_id, AttrConsts.SPECIAL_BUSI_TYPE);
		return busi_type;
	}

	public void setBusi_type(String busi_type) {
		this.busi_type = busi_type;
	}

	public String getIs_aop() {
		is_aop = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_AOP);
		return is_aop;
	}

	public void setIs_aop(String is_aop) {
		this.is_aop = is_aop;
	}

	public String getOccupiedFlag() {
		OrderPhoneInfoBusiRequest phoneInfo = CommonDataFactory.getInstance().getOrderTree(order_id).getPhoneInfoBusiRequest();
		if (phoneInfo != null) {
			occupiedFlag = phoneInfo.getOccupiedFlag();
		}
		return occupiedFlag;
	}

	public void setOccupiedFlag(String occupiedFlag) {
		this.occupiedFlag = occupiedFlag;
	}

	public String getCustomer_type() {
		customer_type = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.CUSTOMER_TYPE);
		return customer_type;
	}

	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}

	public String getGoods_cat_id() {
		goods_cat_id = CommonDataFactory.getInstance().getAttrFieldValue(getOrderTree(),order_id, AttrConsts.GOODS_CAT_ID);
		return goods_cat_id;
	}

	public void setGoods_cat_id(String goods_cat_id) {
		this.goods_cat_id = goods_cat_id;
	}

	public String getRegion() {
		region = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE);
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
	public String getOperation_status() {	
		List<AttrTmResourceInfoBusiRequest> list = CommonDataFactory.getInstance().getOrderTree(order_id).getTmResourceInfoBusiRequests();
		if(list!=null && list.size()>0){
			operation_status = list.get(0).getOperation_status();
		}else{
			operation_status = EcsOrderConsts.OCCUPIEDFLAG_0;
		}
		return operation_status;
	}

	public void setOperation_status(String operation_status) {
		this.operation_status = operation_status;
	}

	public String getBssOccupiedFlag() {
		OrderPhoneInfoBusiRequest phoneInfo = CommonDataFactory.getInstance().getOrderTree(order_id).getPhoneInfoBusiRequest();
		if (phoneInfo != null) {
			bssOccupiedFlag = phoneInfo.getBssOccupiedFlag();
		}
		return bssOccupiedFlag;
	}

	public void setBssOccupiedFlag(String bssOccupiedFlag) {
		this.bssOccupiedFlag = bssOccupiedFlag;
	}

	public String getWrite_card_status() {
		write_card_status = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.WRITE_CARD_STATUS);
		return write_card_status;
	}

	public void setWrite_card_status(String write_card_status) {
		this.write_card_status = write_card_status;
	}

	public String getBuyer_message() {
		buyer_message = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BUYER_MESSAGE);
		return buyer_message;
	}

	public void setBuyer_message(String buyer_message) {
		this.buyer_message = buyer_message;
	}

	public String getObj_type() {
		return obj_type;
	}

	public void setObj_type(String obj_type) {
		this.obj_type = obj_type;
	}
	
	public String getOrder_source() {
		return order_source;
	}

	public void setOrder_source(String order_source) {
		this.order_source = order_source;
	}

	public static void main(String[] args) {
		String str = "{\"order_req\":{\"abnormal_status\":\"正常\",\"agent_area\":\"51a3pe\",\"agent_city\":\"530\",\"agent_code\":\"51b3hn9\",\"agent_name\":\"佛山市南海区金沙西联益万家商场（益万家电讯店）\",\"alipay_id\":\"无账号\",\"bss_operator\":\"HLWFS679\",\"bss_operator_name\":\"\",\"chanel_name\":\"测试自营厅\",\"channel_id\":\"newmall001\",\"channel_mark\":\"1\",\"channel_type\":\"2010300\",\"create_time\":\"20150819104040\",\"delivery_status\":\"未发货\",\"development_code\":\"5104571161\",\"is_deal\":\"0\",\"is_examine_card\":\"0\",\"is_to4g\":\"0\",\"n_shipping_amount\":0,\"name\":\"ZTE压力测试AOP\",\"order_amount\":200000,\"order_city\":\"440600\",\"order_city_code\":\"440600\",\"order_comment\":\"渠道标记：传统营业厅\",\"order_disacount\":0,\"order_heavy\":\"0\",\"order_id\":\"WCSV21508191040409195202831\",\"order_list\":[{\"card_type\":\"NM\",\"certi_address\":\"广东省广州市天河区黄埔大道西601号暨大2012级理工学院研\",\"certi_num\":\"441402198910160411\",\"certi_type\":\"SFZ18\",\"cust_name\":\"李毓钦\",\"cust_type\":\"GRKH\",\"good_no_deposit\":\"0\",\"invoice_print_type\":\"3\",\"is_change\":\"0\",\"offer_coupon_price\":200000,\"offer_disacount_price\":0,\"offer_eff_type\":\"COMM\",\"offer_price\":200000,\"package_sale\":\"NO\",\"prod_offer_code\":\"596901020720150530024552\",\"prod_offer_heavy\":\"0\",\"prod_offer_name\":\"2015年1月4G全国套餐存费送费(36个月)76元\",\"prod_offer_num\":1,\"prod_offer_type\":\"20000\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value\":\"18665403532\",\"param_value_code\":\"\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value\":\"0\",\"param_value_code\":\"\"},{\"param_code\":\"family_no\",\"param_name\":\"亲情号码\",\"param_value\":\"\",\"param_value_code\":\"\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value\":\"0\",\"param_value_code\":\"\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value\":\"4G\",\"param_value_code\":\"\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value\":\"1\",\"param_value_code\":\"\"},{\"param_code\":\"advance_com\",\"param_name\":\"普通预存\",\"param_value_code\":\"\"},{\"param_code\":\"advance_spe\",\"param_name\":\"专项预存\",\"param_value_code\":\"\"},{\"param_code\":\"num_thaw_tim\",\"param_name\":\"返还时长\",\"param_value_code\":\"\"},{\"param_code\":\"num_thaw_pro\",\"param_name\":\"分月返还金额\",\"param_value_code\":\"\"},{\"param_code\":\"class_id\",\"param_name\":\"号码等级\",\"param_value\":0,\"param_value_code\":\"\"},{\"param_code\":\"low_cost_che\",\"param_name\":\"考核期最低消费\",\"param_value_code\":\"\"},{\"param_code\":\"time_dur_che\",\"param_name\":\"考核时长\",\"param_value_code\":\"\"},{\"param_code\":\"change_tag_che\",\"param_name\":\"考核期是否过户\",\"param_value_code\":\"\"},{\"param_code\":\"cancel_tag_che\",\"param_name\":\"考核期是否销户\",\"param_value_code\":\"\"},{\"param_code\":\"bremon_che\",\"param_name\":\"考核期违约金月份\",\"param_value_code\":\"\"},{\"param_code\":\"low_cost_pro\",\"param_name\":\"协议期最低消费\",\"param_value_code\":\"\"},{\"param_code\":\"time_dur_pro\",\"param_name\":\"协议时长\",\"param_value_code\":\"\"},{\"param_code\":\"change_tag_pro\",\"param_name\":\"协议期是否过户\",\"param_value_code\":\"\"},{\"param_code\":\"cancel_tag_pro\",\"param_name\":\"协议期是否销户\",\"param_value_code\":\"\"},{\"param_code\":\"bro_mon_pro\",\"param_name\":\"协议期违约金月份\",\"param_value_code\":\"\"},{\"param_code\":\"operator_state\",\"param_name\":\"操作状态\",\"param_value\":\"2\",\"param_value_code\":\"\"},{\"param_code\":\"pro_key\",\"param_name\":\"资源预占关键字\",\"param_value\":\"20150819103933281713\",\"param_value_code\":\"\"},{\"param_code\":\"occupied_time\",\"param_name\":\"号码操作时间\",\"param_value\":\"20150819104050\",\"param_value_code\":\"\"},{\"param_code\":\"pro_key_mode\",\"param_name\":\"号码资源预占关键字类\",\"param_value\":0,\"param_value_code\":\"\"},{\"param_code\":\"sex\",\"param_name\":\"性别\",\"param_value\":\"M\",\"param_value_code\":\"\"},{\"param_code\":\"contact_addr\",\"param_name\":\"通讯地址\",\"param_value\":\"广东省梅州市梅江区梅县妇幼保健院宿舍                \",\"param_value_code\":\"\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value\":\"10\",\"param_value_code\":\"\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value\":\"NM\",\"param_value_code\":\"\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value\":\"无\",\"param_value_code\":\"\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value\":\"00\",\"param_value_code\":\"\"}]}],\"order_provinc_code\":\"440000\",\"order_type\":\"1\",\"oss_operator\":\"GD001838\",\"pay_money\":200000,\"pay_type\":\"XCZF\",\"payment_type\":\"XJZF\",\"platform_status\":\"WAIT_BUYER_CONFIRM_GOODS\",\"receive_system\":\"10011\",\"serial_no\":\"d1e43f11-d2ff-4186-baae-df1c80f8b64b\",\"ship_area\":\"\",\"ship_city\":\"442000\",\"ship_country\":\"440105\",\"ship_name\":\"ZTE1.0开发勿动\",\"ship_phone\":\"18600000000\",\"shipping_amount\":0,\"shipping_quick\":\"01\",\"shipping_time\":\"NOLIMIT\",\"shipping_type\":\"KD\",\"source_from\":\"100312\",\"source_from_system\":\"10008\",\"source_system\":\"10008\",\"ssyh\":\"newmall001\",\"status\":\"WAIT_BUYER_CONFIRM_GOODS\",\"time\":\"20150819104050\",\"uid\":\"510000\",\"uname\":\"ZTE压力测试AOP\"}}";
//		JSONObject jsonObj = JSONObject.parseObject(str);
//		System.out.println(jsonObj.get("order_req"));
//		System.out.println(jsonObj.get("shipping_type"));
		String aaa = str.valueOf("shipping_type");
		int idx = str.indexOf("shipping_type");
		String start = str.substring(idx);
		String temp = start.substring(start.indexOf("\"")+3);
		System.out.println(temp.substring(0, temp.indexOf("\"")));
	}
}

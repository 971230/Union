package com.ztesoft.net.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_CFG;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class Outer implements Serializable{

	@ZteSoftCommentAnnotationParam(name="促销政策地",type="String",isNecessary="Y",desc="促销政策地")
	private String promotion_area;//促销政策地
	@ZteSoftCommentAnnotationParam(name="异常状态",type="String",isNecessary="Y",desc="异常状态")
	private String abnormal_status;//异常状态
	@ZteSoftCommentAnnotationParam(name="发货状态",type="String",isNecessary="Y",desc="发货状态")
	private String delivery_status;//发货状态
	@ZteSoftCommentAnnotationParam(name="配送方式",type="String",isNecessary="Y",desc="配送方式")
	private String sending_type;//配送方式
	@ZteSoftCommentAnnotationParam(name="外部平台状态",type="String",isNecessary="Y",desc="外部平台状态")
	private String platform_status;//外部平台状态
	@ZteSoftCommentAnnotationParam(name="支付方式",type="String",isNecessary="Y",desc="支付方式")
	private String pay_method;//支付方式
	@ZteSoftCommentAnnotationParam(name="支付时间",type="String",isNecessary="N",desc="支付时间")
	private String pay_time;//支付时间
	@ZteSoftCommentAnnotationParam(name="客服备注",type="String",isNecessary="N",desc="客服备注")
	private String service_remarks;//客服备注
	
	@ZteSoftCommentAnnotationParam(name="订单归属省份编码",type="String",isNecessary="Y",desc="订单归属省份编码")
	private String order_provinc_code;//订单归属省份编码
	@ZteSoftCommentAnnotationParam(name="订单归属地市编码",type="String",isNecessary="Y",desc="订单归属地市编码")
	private String order_city_code;//订单归属地市编码
	@ZteSoftCommentAnnotationParam(name="整单优惠",type="String",isNecessary="N",desc="整单优惠")
	private String order_disfee;//整单优惠
	@ZteSoftCommentAnnotationParam(name="平台分销商编号",type="String",isNecessary="N",desc="平台分销商编号")
	private String plat_distributor_code;//平台分销商编号
	@ZteSoftCommentAnnotationParam(name="第三方支付账户",type="String",isNecessary="Y",desc="第三方支付账户")
	private String alipay_id;//第三方支付账户
	@ZteSoftCommentAnnotationParam(name="订单实收总价",type="String",isNecessary="Y",desc="订单实收总价")
	private String order_realfee;//订单实收总价
	@ZteSoftCommentAnnotationParam(name="买家昵称",type="String",isNecessary="Y",desc="买家昵称")
	private String buyer_id;//买家昵称 uname
	@ZteSoftCommentAnnotationParam(name="买家姓名",type="String",isNecessary="Y",desc="买家姓名")
	private String buyer_name;//买家姓名 name
	
	@ZteSoftCommentAnnotationParam(name="收货电话",type="String",isNecessary="Y",desc="收货电话")
	private String phone;//收货电话 tel
	@ZteSoftCommentAnnotationParam(name="收货人所在省",type="String",isNecessary="Y",desc="收货人所在省")
	private String province;//收货人所在省
	@ZteSoftCommentAnnotationParam(name="收货人所在市",type="String",isNecessary="Y",desc="收货人所在市")
	private String city;//收货人所在市
	@ZteSoftCommentAnnotationParam(name="收货人所在区",type="String",isNecessary="Y",desc="收货人所在区")
	private String district;//收货人所在区 region
	@ZteSoftCommentAnnotationParam(name="收货人邮编",type="String",isNecessary="Y",desc="收货人邮编")
	private String post;//收货人邮编 ship_zip
	@ZteSoftCommentAnnotationParam(name="收货省编码",type="String",isNecessary="Y",desc="收货省编码")
	private String provinc_code;//收货省编码
	@ZteSoftCommentAnnotationParam(name="收货市编码",type="String",isNecessary="Y",desc="收货市编码")
	private String city_code;//收货市编码
	@ZteSoftCommentAnnotationParam(name="收货区编码",type="String",isNecessary="Y",desc="收货区编码")
	private String area_code;//收货区编码
	@ZteSoftCommentAnnotationParam(name="会员收货地址id",type="String",isNecessary="Y",desc="会员收货地址id")
	private String address_id;//收货区编码
	@ZteSoftCommentAnnotationParam(name="是否开发票",type="String",isNecessary="Y",desc="是否开发票[0否，1是]")
	private String is_bill;//是否开发票
	
	//发票信息
	@ZteSoftCommentAnnotationParam(name="发票类型",type="Integer",isNecessary="N",desc="发票类型：1-普通发票；2-增值发票")
	private Integer invoice_type;
	@ZteSoftCommentAnnotationParam(name="发票抬头",type="Integer",isNecessary="N",desc="发票抬头类型：1-个人；2-单位")
	private String invoice_title;
	@ZteSoftCommentAnnotationParam(name="发票抬头描述",type="String",isNecessary="N",desc="发票抬头描述")
	private String invoice_title_desc;
	@ZteSoftCommentAnnotationParam(name="发票内容",type="Integer",isNecessary="N",desc="发票内容：1-明细；2-办公用品；3-电脑配件；4-耗材")
	private Integer invoice_content;
	@ZteSoftCommentAnnotationParam(name="发票打印方式",type="String",isNecessary="Y",desc="发票打印方式[1一次性打印,2分月打印,0不打印发票]")
	private String invoice_print_type;
	
	
	@ZteSoftCommentAnnotationParam(name="资料回收方式",type="String",isNecessary="N",desc="资料回收方式[回收资料、物流代收、客户经理代收]")
	private String file_return_type;//资料回收方式
	@ZteSoftCommentAnnotationParam(name="代收邮费",type="String",isNecessary="N",desc="代收邮费")
	private String collection_free;//代收邮费
	
	@ZteSoftCommentAnnotationParam(name="代金券面值",type="String",isNecessary="N",desc="代金券面值")
	private String vouchers_money;//代金券面值
	@ZteSoftCommentAnnotationParam(name="代金券编号",type="String",isNecessary="N",desc="代金券编号")
	private String vouchers_code;//代金券编号
	@ZteSoftCommentAnnotationParam(name="优惠类型",type="String",isNecessary="N",desc="优惠类型")
	private String disfee_type;//优惠类型
	@ZteSoftCommentAnnotationParam(name="一级代理商id",type="String",isNecessary="N",desc="一级代理商id")
	private String one_agents_id;//一级代理商id
	@ZteSoftCommentAnnotationParam(name="二级代理商id",type="String",isNecessary="N",desc="二级代理商id")
	private String two_agents_id;//二级代理商id
	@ZteSoftCommentAnnotationParam(name="网盟订单来源",type="String",isNecessary="N",desc="网盟订单来源")
	private String wm_order_from;//网盟订单来源
	@ZteSoftCommentAnnotationParam(name="拍拍网厅的订单号",type="String",isNecessary="N",desc="拍拍网厅的订单号")
	private String wt_paipai_order_id;//拍拍网厅的订单号
	@ZteSoftCommentAnnotationParam(name="代金券名称",type="String",isNecessary="N",desc="代金券名称")
	private String couponname;//代金券名称
	@ZteSoftCommentAnnotationParam(name="代金券批次号",type="String",isNecessary="N",desc="代金券批次号")
	private String couponbatchid;//代金券批次号
	@ZteSoftCommentAnnotationParam(name="订单接入类型",type="String",isNecessary="N",desc="订单接入类型")
	private String orderacctype;//订单接入类型
	@ZteSoftCommentAnnotationParam(name="订单接入编码",type="String",isNecessary="N",desc="订单接入编码")
	private String orderacccode;//订单接入编码
	@ZteSoftCommentAnnotationParam(name="支付类型",type="String",isNecessary="N",desc="支付类型")
	private String paytype;//支付类型
	@ZteSoftCommentAnnotationParam(name="支付时间",type="String",isNecessary="N",desc="支付时间[格式yyyy-MM-dd HH:mm:ss]")
	private String payfintime;//支付时间
	@ZteSoftCommentAnnotationParam(name="支付订单号",type="String",isNecessary="N",desc="支付订单号")
	private String payplatformorderid;//支付订单号
	@ZteSoftCommentAnnotationParam(name="支付机构编码",type="String",isNecessary="N",desc="支付机构编码")
	private String payproviderid;//支付机构编码
	@ZteSoftCommentAnnotationParam(name="支付机构名称",type="String",isNecessary="N",desc="支付机构名称")
	private String payprovidername;//支付机构名称
	
	@ZteSoftCommentAnnotationParam(name="支付渠道编码",type="String",isNecessary="N",desc="支付渠道编码")
	private String paychannelid;//支付渠道编码
	@ZteSoftCommentAnnotationParam(name="支付渠道名称",type="String",isNecessary="N",desc="支付渠道名称")
	private String paychannelname;//支付渠道名称
	@ZteSoftCommentAnnotationParam(name="优惠活动名称",type="String",isNecessary="N",desc="优惠活动名称")
	private String discountname;//优惠活动名称
	@ZteSoftCommentAnnotationParam(name="优惠活动编号",type="String",isNecessary="N",desc="优惠活动编号")
	private String discountid;//优惠活动编号
	@ZteSoftCommentAnnotationParam(name="优惠幅度",type="String",isNecessary="N",desc="优惠幅度")
	private String discountrange;//优惠幅度
	@ZteSoftCommentAnnotationParam(name="优惠金额",type="String",isNecessary="N",desc="优惠金额")
	private String discountValue;//优惠金额
	@ZteSoftCommentAnnotationParam(name="BSS产品类型",type="String",isNecessary="N",desc="BSS产品类型")
	private String bss_order_type;//BSS产品类型
	@ZteSoftCommentAnnotationParam(name="证件失效时间",type="String",isNecessary="N",desc="证件失效时间[格式yyyy-MM-dd HH:m:ss]")
	private String cert_failure_time;//证件失效时间
	
	@ZteSoftCommentAnnotationParam(name="证件地址",type="String",isNecessary="N",desc="证件地址")
	private String cert_address;//证件地址
	@ZteSoftCommentAnnotationParam(name="订单应收总价",type="String",isNecessary="Y",desc="订单应收总价")
	private String order_origfee;//订单应收总价
	@ZteSoftCommentAnnotationParam(name="外部平台单号",type="String",isNecessary="Y",desc="外部平台单号")
	private String out_tid; //外部平台单号
	@ZteSoftCommentAnnotationParam(name="总部订单ID",type="String",isNecessary="N",desc="总部订单ID")
	private String order_num; //外部平台单号
	@ZteSoftCommentAnnotationParam(name="最终来源商城单号",type="String",isNecessary="N",desc="最终来源商城单号")
	private String src_out_tid; //外部平台单号
	@ZteSoftCommentAnnotationParam(name="订单渠道",type="String",isNecessary="Y",desc="订单渠道")
	private String order_channel;//订单渠道
	@ZteSoftCommentAnnotationParam(name="订单来源",type="String",isNecessary="Y",desc="订单来源")
	private String order_from;//订单来源
	@ZteSoftCommentAnnotationParam(name="订单类型",type="String",isNecessary="Y",desc="订单类型")
	private String type;//订单类型
	@ZteSoftCommentAnnotationParam(name="处理状态",type="String",isNecessary="Y",desc="处理状态")
	private String status;//处理状态
	@ZteSoftCommentAnnotationParam(name="合并状态",type="String",isNecessary="Y",desc="合并状态")
	private String merge_status;//合并状态
	@ZteSoftCommentAnnotationParam(name="退单标识",type="String",isNecessary="N",desc="退单标识")
	private String return_flag;
	
	@ZteSoftCommentAnnotationParam(name="平台类型",type="String",isNecessary="Y",desc="平台类型")
	private String plat_type;//平台类型
	@ZteSoftCommentAnnotationParam(name="产品总金额",type="String",isNecessary="Y",desc="产品总金额")
	private String pro_totalfee;//产品总金额
	@ZteSoftCommentAnnotationParam(name="订单总金额",type="String",isNecessary="Y",desc="订单总金额")
	private String order_totalfee;//订单总金额
	@ZteSoftCommentAnnotationParam(name="订货时间",type="String",isNecessary="Y",desc="订货时间[格式yyyy-MM-dd HH:mm:ss]")
	private String tid_time;//订货时间
	@ZteSoftCommentAnnotationParam(name="获取时间",type="String",isNecessary="Y",desc="获取时间[格式yyyy-MM-dd HH:mm:ss]")
	private String get_time;//获取时间
	@ZteSoftCommentAnnotationParam(name="买家留言",type="String",isNecessary="Y",desc="买家留言")
	private String buyer_message;//买家留言
	@ZteSoftCommentAnnotationParam(name="是否预售",type="String",isNecessary="Y",desc="是否预售[0否，1是]")
	private String is_adv_sale;//是否预售
	@ZteSoftCommentAnnotationParam(name="付款状态",type="String",isNecessary="Y",desc="付款状态")
	private String pay_status;//付款状态
	
	@ZteSoftCommentAnnotationParam(name="收货人姓名",type="String",isNecessary="Y",desc="收货人姓名")
	private String receiver_name;//收货人姓名 ship_name
	@ZteSoftCommentAnnotationParam(name="收货手机",type="String",isNecessary="Y",desc="收货手机")
	private String receiver_mobile;//收货手机 ship_mobile
	@ZteSoftCommentAnnotationParam(name="收货人详细地址",type="String",isNecessary="Y",desc="收货人详细地址")
	private String address;//收货人详细地址 SHIP_ADDR
	@ZteSoftCommentAnnotationParam(name="推荐人名称",type="String",isNecessary="N",desc="推荐人名称")
	private String recommended_name;//推荐人名称 spread_name
	@ZteSoftCommentAnnotationParam(name="推荐人编码",type="String",isNecessary="N",desc="推荐人编码")
	private String recommended_code;//推荐人编码 Spread_code
	@ZteSoftCommentAnnotationParam(name="推荐人手机号码",type="String",isNecessary="N",desc="推荐人手机号码")
	private String recommended_phone;//推荐人手机号码 spread_phone
	@ZteSoftCommentAnnotationParam(name="发展人编码",type="String",isNecessary="N",desc="发展人编码")
	private String development_code;//发展人编码
	@ZteSoftCommentAnnotationParam(name="发展人名称",type="String",isNecessary="N",desc="发展人名称")
	private String development_name;//发展人名称
	
	@ZteSoftCommentAnnotationParam(name="是否预约单",type="String",isNecessary="N",desc="是否预约单[0否1是]")
	private String wm_isreservation_from;//是否预约单
	//private String goods_num;
	@ZteSoftCommentAnnotationParam(name="邮费",type="String",isNecessary="Y",desc="邮费")
	private String post_fee;//邮费
	@ZteSoftCommentAnnotationParam(name="订单类别",type="String",isNecessary="Y",desc="订单类别[Z0:联通合约机Z1:3G号卡Z2:定制机裸机Z3:社会机合约机Z4:社会机裸机Z5:2G号卡Z7:上网卡【不含卡体】Z8:上网卡【含卡体】Z9:增值业务]")
	private String tid_category;////订单类别
	@ZteSoftCommentAnnotationParam(name="订单来原中文",type="String",isNecessary="Y",desc="订单来原中文")
	private String c_order_from;//订单来原中文
	@ZteSoftCommentAnnotationParam(name="子订单列表",type="String",isNecessary="Y",desc="子订单列表",hasChild=true)
	private List<OuterItem> itemList;
	
	@ZteSoftCommentAnnotationParam(name="百度账号",type="String",isNecessary="N",desc="百度账号")
	private String baidu_id;
	@ZteSoftCommentAnnotationParam(name="冻结流水号",type="String",isNecessary="N",desc="百度冻结流水号")
	private String freeze_tran_no;
	@ZteSoftCommentAnnotationParam(name="冻结款",type="String",isNecessary="N",desc="冻结款")
	private String freeze_free;
	@ZteSoftCommentAnnotationParam(name="赠送金额",type="String",isNecessary="N",desc="赠送金额")
	private String gift_money ;
	@ZteSoftCommentAnnotationParam(name="预留字段",type="String",isNecessary="N",desc="预留字段")
	private String reserve0;
	@ZteSoftCommentAnnotationParam(name="预留字段",type="String",isNecessary="N",desc="预留字段")
	private String reserve1;
	@ZteSoftCommentAnnotationParam(name="预留字段",type="String",isNecessary="N",desc="预留字段")
	private String reserve2;
	@ZteSoftCommentAnnotationParam(name="预留字段",type="String",isNecessary="N",desc="预留字段")
	private String reserve3;
	@ZteSoftCommentAnnotationParam(name="预留字段",type="String",isNecessary="N",desc="预留字段")
	private String reserve4;
	@ZteSoftCommentAnnotationParam(name="预留字段",type="String",isNecessary="N",desc="预留字段")
	private String reserve5;
	@ZteSoftCommentAnnotationParam(name="预留字段",type="String",isNecessary="N",desc="预留字段")
	private String reserve6;
	@ZteSoftCommentAnnotationParam(name="预留字段",type="String",isNecessary="N",desc="预留字段")
	private String reserve7;
	
	@ZteSoftCommentAnnotationParam(name="预留字段",type="String",isNecessary="N",desc="预留字段")
	private String reserve8;
	@ZteSoftCommentAnnotationParam(name="预留字段",type="String",isNecessary="N",desc="预留字段")
	private String reserve9;
	@ZteSoftCommentAnnotationParam(name="扩展信息",type="String",isNecessary="N",desc="扩展信息[key为属性名、value为属性值]")
	private Map<String,String> extMap;
	
	private String coupon_code;//优惠券编码 内部使用
	private String member_id;//会员ID 内部使用
	private String order_if_cancel;//作废单判断 内部使用
	
	private Object localObject;
		
	@ZteSoftCommentAnnotationParam(name="仓库信息",type="String",isNecessary="N",desc="仓库信息")
	private WarehousePurorder warehousePurorder; //内部使用
	
	//自定义流程配置
	private ES_WORK_CUSTOM_CFG flowCfg;

	//属性处理器包含属性 ding.xiaotao 20180522
	private	String guarantor;//担保人 ES_ORDER_EXTVTL
	private String bill_type;//账户付费方式  ES_ORDER_EXTVTL
	private String bill_mail_type;//账单寄送方式 ES_ORDER_EXTVTL
	private String cert_card_nation;//开户人民族 es_order_items_prod_ext
	private String sim_type;//卡种类 号卡性质es_order_extvtl
	private String cert_card_num;//开户人证件号码 es_order_items_prod_ext
	private String reliefpres_flag;//减免预存标记 ES_ORDER_EXTVTL
	private String CustomerType;//客户类型 es_order_extvtl
	
	private String order_deal_method;//线上转线下订单处理方式。1--线上，2--线下
	
	// add by zhaochen 流程编码
	private String flow_code;
	
	private Map<String, Object> nice_info; //靓号信息

	public String getCustomerType() {
		return CustomerType;
	}

	public void setCustomerType(String customerType) {
		CustomerType = customerType;
	}

	public String getReliefpres_flag() {
		return reliefpres_flag;
	}

	public void setReliefpres_flag(String reliefpres_flag) {
		this.reliefpres_flag = reliefpres_flag;
	}

	public String getCert_card_num() {
		return cert_card_num;
	}

	public void setCert_card_num(String cert_card_num) {
		this.cert_card_num = cert_card_num;
	}

	public String getSim_type() {
		return sim_type;
	}

	public void setSim_type(String sim_type) {
		this.sim_type = sim_type;
	}

	public String getCert_card_nation() {
		return cert_card_nation;
	}

	public void setCert_card_nation(String cert_card_nation) {
		this.cert_card_nation = cert_card_nation;
	}

	public String getBill_mail_type() {
		return bill_mail_type;
	}

	public void setBill_mail_type(String bill_mail_type) {
		this.bill_mail_type = bill_mail_type;
	}

	public String getBill_type() {
		return bill_type;
	}

	public void setBill_type(String bill_type) {
		this.bill_type = bill_type;
	}

	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getCoupon_code() {
		return coupon_code;
	}
	public void setCoupon_code(String coupon_code) {
		this.coupon_code = coupon_code;
	}
	
	public Object getLocalObject() {
		return localObject;
	}
	public void setLocalObject(Object localObject) {
		this.localObject = localObject;
	}
	
	public String getBaidu_id() {
		return baidu_id;
	}
	public void setBaidu_id(String baidu_id) {
		this.baidu_id = baidu_id;
	}
	public String getFreeze_tran_no() {
		return freeze_tran_no;
	}
	public void setFreeze_tran_no(String freeze_tran_no) {
		this.freeze_tran_no = freeze_tran_no;
	}
	public String getFreeze_free() {
		return freeze_free;
	}
	public void setFreeze_free(String freeze_free) {
		this.freeze_free = freeze_free;
	}
	public String getGift_money() {
		return gift_money;
	}
	public void setGift_money(String gift_money) {
		this.gift_money = gift_money;
	}
	public String getReserve0() {
		return reserve0;
	}
	public void setReserve0(String reserve0) {
		this.reserve0 = reserve0;
	}
	public String getReserve1() {
		return reserve1;
	}
	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}
	public String getReserve2() {
		return reserve2;
	}
	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}
	public String getReserve3() {
		return reserve3;
	}
	public void setReserve3(String reserve3) {
		this.reserve3 = reserve3;
	}
	public String getReserve4() {
		return reserve4;
	}
	public void setReserve4(String reserve4) {
		this.reserve4 = reserve4;
	}
	public String getReserve5() {
		return reserve5;
	}
	public void setReserve5(String reserve5) {
		this.reserve5 = reserve5;
	}
	public String getReserve6() {
		return reserve6;
	}
	public void setReserve6(String reserve6) {
		this.reserve6 = reserve6;
	}
	public String getReserve7() {
		return reserve7;
	}
	public void setReserve7(String reserve7) {
		this.reserve7 = reserve7;
	}
	public String getReserve8() {
		return reserve8;
	}
	public void setReserve8(String reserve8) {
		this.reserve8 = reserve8;
	}
	public String getReserve9() {
		return reserve9;
	}
	public void setReserve9(String reserve9) {
		this.reserve9 = reserve9;
	}
	public String getPromotion_area() {
		return promotion_area;
	}
	public void setPromotion_area(String promotion_area) {
		this.promotion_area = promotion_area;
	}
	public String getAbnormal_status() {
		return abnormal_status;
	}
	public void setAbnormal_status(String abnormal_status) {
		this.abnormal_status = abnormal_status;
	}
	public String getDelivery_status() {
		return delivery_status;
	}
	public void setDelivery_status(String delivery_status) {
		this.delivery_status = delivery_status;
	}
	public String getSending_type() {
		return sending_type;
	}
	public void setSending_type(String sending_type) {
		this.sending_type = sending_type;
	}
	public String getPlatform_status() {
		return platform_status;
	}
	public void setPlatform_status(String platform_status) {
		this.platform_status = platform_status;
	}
	public String getPay_method() {
		return pay_method;
	}
	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}
	public String getPay_time() {
		return pay_time;
	}
	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}
	public String getService_remarks() {
		return service_remarks;
	}
	public void setService_remarks(String service_remarks) {
		this.service_remarks = service_remarks;
	}
	public String getOrder_provinc_code() {
		return order_provinc_code;
	}
	public void setOrder_provinc_code(String order_provinc_code) {
		this.order_provinc_code = order_provinc_code;
	}
	public String getOrder_city_code() {
		return order_city_code;
	}
	public void setOrder_city_code(String order_city_code) {
		this.order_city_code = order_city_code;
	}
	public String getOrder_disfee() {
		return order_disfee;
	}
	public void setOrder_disfee(String order_disfee) {
		this.order_disfee = order_disfee;
	}
	public String getPlat_distributor_code() {
		return plat_distributor_code;
	}
	public void setPlat_distributor_code(String plat_distributor_code) {
		this.plat_distributor_code = plat_distributor_code;
	}
	public String getAlipay_id() {
		return alipay_id;
	}
	public void setAlipay_id(String alipay_id) {
		this.alipay_id = alipay_id;
	}
	public String getOrder_realfee() {
		return order_realfee;
	}
	public void setOrder_realfee(String order_realfee) {
		this.order_realfee = order_realfee;
	}
	public String getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}
	public String getBuyer_name() {
		return buyer_name;
	}
	public void setBuyer_name(String buyer_name) {
		this.buyer_name = buyer_name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getProvinc_code() {
		return provinc_code;
	}
	public void setProvinc_code(String provinc_code) {
		this.provinc_code = provinc_code;
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getIs_bill() {
		return is_bill;
	}
	public void setIs_bill(String is_bill) {
		this.is_bill = is_bill;
	}
	public String getInvoice_print_type() {
		return invoice_print_type;
	}
	public void setInvoice_print_type(String invoice_print_type) {
		this.invoice_print_type = invoice_print_type;
	}
	public String getFile_return_type() {
		return file_return_type;
	}
	public void setFile_return_type(String file_return_type) {
		this.file_return_type = file_return_type;
	}
	
	public Integer getInvoice_type() {
		return invoice_type;
	}
	public void setInvoice_type(Integer invoice_type) {
		this.invoice_type = invoice_type;
	}

	public String getInvoice_title() {
		return invoice_title;
	}
	public void setInvoice_title(String invoice_title) {
		this.invoice_title = invoice_title;
	}
	public String getInvoice_title_desc() {
		return invoice_title_desc;
	}
	public void setInvoice_title_desc(String invoice_title_desc) {
		this.invoice_title_desc = invoice_title_desc;
	}
	public Integer getInvoice_content() {
		return invoice_content;
	}
	public void setInvoice_content(Integer invoice_content) {
		this.invoice_content = invoice_content;
	}
	public String getCollection_free() {
		return collection_free;
	}
	public void setCollection_free(String collection_free) {
		this.collection_free = collection_free;
	}
	public String getVouchers_money() {
		return vouchers_money;
	}
	public void setVouchers_money(String vouchers_money) {
		this.vouchers_money = vouchers_money;
	}
	public String getVouchers_code() {
		return vouchers_code;
	}
	public void setVouchers_code(String vouchers_code) {
		this.vouchers_code = vouchers_code;
	}
	public String getDisfee_type() {
		return disfee_type;
	}
	public void setDisfee_type(String disfee_type) {
		this.disfee_type = disfee_type;
	}
	public String getOne_agents_id() {
		return one_agents_id;
	}
	public void setOne_agents_id(String one_agents_id) {
		this.one_agents_id = one_agents_id;
	}
	public String getTwo_agents_id() {
		return two_agents_id;
	}
	public void setTwo_agents_id(String two_agents_id) {
		this.two_agents_id = two_agents_id;
	}
	public String getWm_order_from() {
		return wm_order_from;
	}
	public void setWm_order_from(String wm_order_from) {
		this.wm_order_from = wm_order_from;
	}
	public String getWt_paipai_order_id() {
		return wt_paipai_order_id;
	}
	public void setWt_paipai_order_id(String wt_paipai_order_id) {
		this.wt_paipai_order_id = wt_paipai_order_id;
	}
	public String getCouponname() {
		return couponname;
	}
	public void setCouponname(String couponname) {
		this.couponname = couponname;
	}
	public String getCouponbatchid() {
		return couponbatchid;
	}
	public void setCouponbatchid(String couponbatchid) {
		this.couponbatchid = couponbatchid;
	}
	public String getOrderacctype() {
		return orderacctype;
	}
	public void setOrderacctype(String orderacctype) {
		this.orderacctype = orderacctype;
	}
	public String getOrderacccode() {
		return orderacccode;
	}
	public void setOrderacccode(String orderacccode) {
		this.orderacccode = orderacccode;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public String getPayfintime() {
		return payfintime;
	}
	public void setPayfintime(String payfintime) {
		this.payfintime = payfintime;
	}
	public String getPayplatformorderid() {
		return payplatformorderid;
	}
	public void setPayplatformorderid(String payplatformorderid) {
		this.payplatformorderid = payplatformorderid;
	}
	public String getPayproviderid() {
		return payproviderid;
	}
	public void setPayproviderid(String payproviderid) {
		this.payproviderid = payproviderid;
	}
	public String getPayprovidername() {
		return payprovidername;
	}
	public void setPayprovidername(String payprovidername) {
		this.payprovidername = payprovidername;
	}
	public String getPaychannelid() {
		return paychannelid;
	}
	public void setPaychannelid(String paychannelid) {
		this.paychannelid = paychannelid;
	}
	public String getPaychannelname() {
		return paychannelname;
	}
	public void setPaychannelname(String paychannelname) {
		this.paychannelname = paychannelname;
	}
	public String getDiscountname() {
		return discountname;
	}
	public void setDiscountname(String discountname) {
		this.discountname = discountname;
	}
	public String getDiscountid() {
		return discountid;
	}
	public void setDiscountid(String discountid) {
		this.discountid = discountid;
	}
	public String getDiscountrange() {
		return discountrange;
	}
	public void setDiscountrange(String discountrange) {
		this.discountrange = discountrange;
	}
	public String getDiscountValue() {
		return discountValue;
	}
	public void setDiscountValue(String discountValue) {
		this.discountValue = discountValue;
	}
	public String getBss_order_type() {
		return bss_order_type;
	}
	public void setBss_order_type(String bss_order_type) {
		this.bss_order_type = bss_order_type;
	}
	public String getCert_failure_time() {
		return cert_failure_time;
	}
	public void setCert_failure_time(String cert_failure_time) {
		this.cert_failure_time = cert_failure_time;
	}
	public String getCert_address() {
		return cert_address;
	}
	public void setCert_address(String cert_address) {
		this.cert_address = cert_address;
	}
	public String getOrder_origfee() {
		return order_origfee;
	}
	public void setOrder_origfee(String order_origfee) {
		this.order_origfee = order_origfee;
	}
	public String getOut_tid() {
		return out_tid;
	}
	public void setOut_tid(String out_tid) {
		this.out_tid = out_tid;
	}
	public String getOrder_num() {
		return order_num;
	}
	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}
	public String getSrc_out_tid() {
		return src_out_tid;
	}
	public void setSrc_out_tid(String src_out_tid) {
		this.src_out_tid = src_out_tid;
	}
	public String getOrder_channel() {
		return order_channel;
	}
	public void setOrder_channel(String order_channel) {
		this.order_channel = order_channel;
	}
	public String getOrder_from() {
		return order_from;
	}
	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMerge_status() {
		return merge_status;
	}
	public void setMerge_status(String merge_status) {
		this.merge_status = merge_status;
	}
	public String getReturn_flag() {
		return return_flag;
	}
	public void setReturn_flag(String return_flag) {
		this.return_flag = return_flag;
	}
	public String getPlat_type() {
		return plat_type;
	}
	public void setPlat_type(String plat_type) {
		this.plat_type = plat_type;
	}
	public String getPro_totalfee() {
		return pro_totalfee;
	}
	public void setPro_totalfee(String pro_totalfee) {
		this.pro_totalfee = pro_totalfee;
	}
	public String getOrder_totalfee() {
		return order_totalfee;
	}
	public void setOrder_totalfee(String order_totalfee) {
		this.order_totalfee = order_totalfee;
	}
	public String getTid_time() {
		return tid_time;
	}
	public void setTid_time(String tid_time) {
		this.tid_time = tid_time;
	}
	public String getGet_time() {
		return get_time;
	}
	public void setGet_time(String get_time) {
		this.get_time = get_time;
	}
	public String getBuyer_message() {
		return buyer_message;
	}
	public void setBuyer_message(String buyer_message) {
		this.buyer_message = buyer_message;
	}
	public String getIs_adv_sale() {
		return is_adv_sale;
	}
	public void setIs_adv_sale(String is_adv_sale) {
		this.is_adv_sale = is_adv_sale;
	}
	public String getPay_status() {
		return pay_status;
	}
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
	public String getReceiver_name() {
		return receiver_name;
	}
	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}
	public String getReceiver_mobile() {
		return receiver_mobile;
	}
	public void setReceiver_mobile(String receiver_mobile) {
		this.receiver_mobile = receiver_mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRecommended_name() {
		return recommended_name;
	}
	public void setRecommended_name(String recommended_name) {
		this.recommended_name = recommended_name;
	}
	public String getRecommended_code() {
		return recommended_code;
	}
	public void setRecommended_code(String recommended_code) {
		this.recommended_code = recommended_code;
	}
	public String getRecommended_phone() {
		return recommended_phone;
	}
	public void setRecommended_phone(String recommended_phone) {
		this.recommended_phone = recommended_phone;
	}
	public String getDevelopment_code() {
		return development_code;
	}
	public void setDevelopment_code(String development_code) {
		this.development_code = development_code;
	}
	public String getDevelopment_name() {
		return development_name;
	}
	public void setDevelopment_name(String development_name) {
		this.development_name = development_name;
	}
	public String getWm_isreservation_from() {
		return wm_isreservation_from;
	}
	public void setWm_isreservation_from(String wm_isreservation_from) {
		this.wm_isreservation_from = wm_isreservation_from;
	}
	public String getPost_fee() {
		return post_fee;
	}
	public void setPost_fee(String post_fee) {
		this.post_fee = post_fee;
	}
	public List<OuterItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<OuterItem> itemList) {
		this.itemList = itemList;
	}
	public String getTid_category() {
		return tid_category;
	}
	public void setTid_category(String tid_category) {
		this.tid_category = tid_category;
	}
	public String getC_order_from() {
		return c_order_from;
	}
	public void setC_order_from(String c_order_from) {
		this.c_order_from = c_order_from;
	}
	public Map<String, String> getExtMap() {
		return extMap;
	}
	public void setExtMap(Map<String, String> extMap) {
		this.extMap = extMap;
	}
	public WarehousePurorder getWarehousePurorder() {
		return warehousePurorder;
	}
	public void setWarehousePurorder(WarehousePurorder warehousePurorder) {
		this.warehousePurorder = warehousePurorder;
	}
	public String getAddress_id() {
		return address_id;
	}
	public void setAddress_id(String address_id) {
		this.address_id = address_id;
	}
	public String getOrder_if_cancel() {
		return order_if_cancel;
	}
	public void setOrder_if_cancel(String order_if_cancel) {
		this.order_if_cancel = order_if_cancel;
	}

	public String getGuarantor() {
		return guarantor;
	}

	public void setGuarantor(String guarantor) {
		this.guarantor = guarantor;
	}

	public String getOrder_deal_method() {
		return order_deal_method;
	}

	public void setOrder_deal_method(String order_deal_method) {
		this.order_deal_method = order_deal_method;
	}

	public ES_WORK_CUSTOM_CFG getFlowCfg() {
		return flowCfg;
	}

	public void setFlowCfg(ES_WORK_CUSTOM_CFG flowCfg) {
		this.flowCfg = flowCfg;
	}

	public Map<String, Object> getNice_info() {
		return nice_info;
	}

	public void setNice_info(Map<String, Object> nice_info) {
		this.nice_info = nice_info;
	}

	public String getFlow_code() {
		return flow_code;
	}

	public void setFlow_code(String flow_code) {
		this.flow_code = flow_code;
	}


	
}

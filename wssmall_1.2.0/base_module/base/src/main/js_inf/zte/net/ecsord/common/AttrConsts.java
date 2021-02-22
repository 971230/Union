package zte.net.ecsord.common;

/**
 * 属性规格常量定义
 * @author Administrator
 *
 */
public class AttrConsts {
	public static final boolean ACTIVE_NO_ON_OFF = true;//属性访问流水号：ActiveNo 常量

	//订单属性信息
	public static final String GOODS_NUM = "goods_num";//商品数量
	public static final String PAY_TYPE = "paytype";//支付类型
	public static final String PAY_TIME = "pay_time";//支付时间
	public static final String PAY_METHOD = "pay_method";//支付方式
	public static final String PAY_FIN_TIME = "payfintime";//支付完成时间
	public static final String SENDING_TYPE = "sending_type";//配送方式
	public static final String OLD_SENDING_TYPE = "old_sending_type";//配送方式
	public static final String SELL_PRICE = "sell_price";//商品单价
	public static final String ORDER_TOTAL_FEE = "order_totalfee";//订单总价
	public static final String ORDER_REAL_FEE = "order_realfee";//订单实收总价
	public static final String ORDER_ORIGFEE = "order_origfee";//订单应收总价
	public static final String PRO_ORIG_FEE = "pro_origfee";//商品应收金额
	public static final String DELIVERY_STATUS = "delivery_status";//发货状态
	public static final String ORDER_TYPE = "order_type";//订单类型
	public static final String ORDER_CITY_CODE = "order_city_code";//归属地市
	public static final String ORDER_DEAL_TYPE = "order_deal_type";//订单处理类型
	public static final String PLAT_TYPE = "plat_type";//平台类型
	public static final String OUT_TID = "out_tid";//外部平台单号
	public static final String WM_ISRESERVATION_FROM = "wm_isreservation_from";//是否预约单
	public static final String TID_TIME = "tid_time";//订货时间
	public static final String IS_ADV_SALE = "is_adv_sale";//是否预售
	public static final String TID_CATEGORY  ="tid_category";//订单类别
	public static final String ORDER_FROM = "order_from";//订单来源
	public static final String ORDER_CHANNEL = "order_channel";//商城来源
	public static final String ABNORMAL_STATUS = "abnormal_status";//异常状态
	public static final String SHIPPING_QUICK = "shipping_quick";//是否闪电送
	public static final String WCF_PACKAGES = "wcfPackages";//新商城可选包
	public static final String DISCOUNT_INFOS = "discountInfos";//总部优惠信息
	public static final String GIFT_INFOS = "giftInfos";//总部赠品信息
	public static final String LEAGUE_INFO = "LeagueInfo";//联盟信息
	public static final String PLATFORM_STATUS = "platform_status";//外部平台状态
	public static final String FEE_INFOS = "feeinfo";//总部费用明细
	public static final String PACKAGE_SALE = "package_sale";//套包销售标记
	public static final String ZB_PACKAGES = "zbpackages";//总部可选包
	public static final String SELLER_MESSAGE = "seller_message";//卖家留言
	public static final String FUND_TYPE = "fund_type";//基金类型
	public static final String RECOMMENDED_NAME = "recommended_name";//推荐人姓名
	public static final String RECOMMENDED_PHONE = "recommended_phone";//推荐人手机
	public static final String RECOMMENDED_TYPE = "recommended_type";//推荐类型
	public static final String DEVELOPMENT_CODE = "development_code";//发展人编码
	public static final String DEVELOPMENT_NAME = "development_name";//发展人姓名
	public static final String FILE_RETURN_TYPE = "file_return_type";//资料回收方式
	public static final String SERVICE_REMARKS = "service_remarks";//服务备注
	public static final String MERGE_STATUS = "merge_status";//合并状态
	public static final String RESERVE0 = "reserve0";//接收方系统标识
	public static final String RESERVE1 = "reserve1";//是否2G、3G升4G
	public static final String RESERVE2 = "reserve2";//渠道类型、订单发展归属
	public static final String RESERVE3 = "reserve3";//渠道ID、所属用户（发展代理商）
	public static final String RESERVE4 = "reserve4";//渠道名称
	public static final String RESERVE5 = "reserve5";//百度冻结开始时间
	public static final String RESERVE6 = "reserve6";//百度冻结结束时间
	public static final String RESERVE7 = "reserve7";//渠道ID、所属用户（发展代理商）
	public static final String RESERVE8 = "reserve8";//发票抬头            
	public static final String RESERVE9 = "reserve9";//活动编码
	public static final String ORG_ID = "org_id";//组织ID
	public static final String FREEZE_TRAN_NO = "freeze_tran_no";//百度冻结流水号
	public static final String FREEZE_FEE = "freeze_free";//百度冻结款
	public static final String BAIDU_ID = "baidu_id";//百度账号
	public static final String GROUP_CODE = "group_code";//集团编码
	public static final String GROUP_NAME = "group_name";//集团名称
	public static final String LIANG_CODE = "liang_code";//靓号单编码
	public static final String ORDER_PROVINCE_CODE = "order_provinc_code";//归属省份
	public static final String PLATFORM_STATUS_NAME = "platform_status_name";//外部订单状态名称
	public static final String OTHER_CONTRACT_CODE = "other_contract_code";//第三方协议编码
	public static final String BUSI_CREDENCE_CODE = "busi_credence_code";//业务凭证号
	public static final String ORDER_JOIN_CATEGORY = "order_join_category";//接入类别【作废，改为regist_type】
	public static final String SOURCE_TYPE = "source_type";//总部订单来源
	public static final String REGIST_TYPE = "regist_type";//总部订单接入类别
	public static final String IS_UPLOAD = "is_upload";//资料是否上传
	public static final String RECYLE_DESC = "recyle_desc"    ;//回收内容            
	public static final String BU_PRICE = "bu_price"    ;//差价单              
	public static final String BU_PRICE_CODE = "bu_price_code"    ;//差价单编号          
	public static final String UID = "uid"    ;//买家标识            
	public static final String BUYER_NAME = "buyer_name"    ;//买家姓名            
	public static final String BUYER_ID = "buyer_id"    ;//买家昵称            
	public static final String BUYER_MESSAGE = "buyer_message";//买家留言
	public static final String PLAT_DISTRIBUTOR_CODE = "plat_distributor_code"    ;//平台分销商编号      
	public static final String ORDER_DISFEE = "order_disfee"    ;//整单优惠            
	public static final String SALES_MANAGER = "sales_manager"    ;//销售经理            
	public static final String CUST_SERVICE_NAME = "cust_service_name"    ;//客服名称            
	public static final String ORDER_MODEL = "order_model"    ;//订单生产模式        
	public static final String PENDING_REASON = "pending_reason"    ;//挂起原因            
	public static final String LOCK_TIME = "lock_time"    ;//锁定时间            
	public static final String LOCK_USER_ID = "lock_user_id"    ;//锁定人            
	public static final String LOCK_STATUS = "lock_status"    ;//锁定状态            
	public static final String FLOW_ID = "flow_id"    ;//订单流程id          
	public static final String FLOW_INST_ID = "flow_inst_id"    ;//流程实例Id          
	public static final String FLOW_TRACE_NAME = "flow_trace_name"    ;//工作流环节名称      
	public static final String FLOW_TRACE_INST_ID = "flow_trace_inst_id"    ;//工作流环节实例ID    
	public static final String WMS_STATUS = "wms_status"    ;//WMS状态             
	public static final String IS_SEND_WMS = "is_send_wms"    ;//是否WMS发送         
	public static final String BSS_CANCEL_STATUS = "bss_cancel_status"    ;//BSS反销状态         
	public static final String ESS_CANCEL_STATUS = "ess_cancel_status"    ;//ESS返销状态         
	public static final String SEND_ZB = "send_zb"    ;//是否发送总部        
	public static final String IS_SHOW = "is_show"    ;//订单是否显示        
	public static final String REFUND_TYPE = "refund_type"    ;//退款类型            
	public static final String REFUND_STATUS = "refund_status"    ;//退单状态            
	public static final String REFUND_DEAL_TYPE = "refund_deal_type"    ;//退单处理类型
	public static final String SUB_REFUND_DEAL_TYPE = "sub_refund_deal_type"; //子退单处理类型
	public static final String EXCEPTION_TYPE = "exception_type"    ;//异常类型            
	public static final String REFUND_TRACE_INST_ID = "refund_trace_inst_id"    ;//退单前环节实例id时间
	public static final String IS_ACCOUNT = "is_account"    ;//是否ESS开户 
	public static final String IS_SEND_GOODS = "is_send_goods"    ;//是否发货      
	public static final String GOODS_TYPE = "goods_type";//商品类型，跟商品系统的商品大类映射、赠品类型
	public static final String IS_WRITE_CARD = "is_write_card";//是否需要写卡
	public static final String IS_EASY_ACCOUNT = "is_easy_account";//是否一键开户
	public static final String IS_PHISICS = "is_physics";//是否实物单 是否有实物商品
	public static final String SYN_ORD_ZB = "syn_ord_zb";		//订单是否已送总部
	public static final String ZB_STATUS = "zb_status";			//总部状态
//	public static final String WYG_STATUS = "wyg_status";			//新商城状态
	public static final String NEW_SHOP_STATUS = "new_shop_status";	//新商城状态
	public static final String TB_STATUS = "tb_status";				//淘宝状态
	public static final String SF_STATUS = "sf_status";				//顺风状态（物流状态）
	public static final String ND_STATUS = "nd_status";				//南都状态(物流状态)
	public static final String SF_STATUS_DESC = "sf_status_desc";				//顺风物流状态描述
	public static final String ND_STATUS_DESC = "nd_status_desc";				//南都物流状态描述
	public static final String SR_STATUS = "sr_status";				//森锐状态(物流状态)
	public static final String SYN_ORD_WMS = "syn_ord_wms";			//订单是否已送WMS
	public static final String ZB_REFUND_STATUS = "zb_refund_status";				//总部退单状态
	public static final String WMS_REFUND_STATUS = "wms_refund_status";				//WMS退单状态
	public static final String BSS_REFUND_STATUS = "bss_refund_status";				//BSS退款状态
	public static final String ESS_STATUS = "ess_status";				//Ess办理状态
	public static final String WRITE_CARD_RESULT = "write_card_result";				//写卡结果
	public static final String SPECIAL_BUSI_TYPE = "special_busi_type";//特殊业务类型
	public static final String PROD_CAT_ID = "prod_cat_id";//合约货品小类
	public static final String LEAGUE_NAME = "league_name";//加盟商名称
	public static final String HIGHER_LEAGUE = "higher_league";//挂靠加盟商
	public static final String ZB_INF_ID = "zb_inf_id";				//总部交互订单号
	public static final String CHANNEL_MARK = "channel_mark";//渠道标记
	public static final String ORDER_ORIGIN = "order_origin";//商城来源
	public static final String ACTIVE_NO = "active_no";//总部开户访问流水
	public static final String AGENT_CODE = "agent_code_dls";			//代理商编码
	public static final String AGENT_NAME = "agent_name";				//代理商名称
	public static final String AGENT_CITY = "agent_city";				//代理商归属地市
	public static final String AGENT_DISTRICT = "agent_district";		//代理商归属县
	public static final String CHANNEL_TYPE = "channel_type";		    //渠道标识：渠道类型
	
	
	/**
	 * 商品类型goodsType说明
	 * ZHKL	总部号卡类
	   ZHYL	总部号卡合约类
	   ZZDL	总部终端合约类
	   ZSWK	总部上网卡商品
	   ZLZD	总部裸终端商品
	   ZPJL	总部配件商品
	   ZYWL	总部业务变更类
	 */
	
	//订单物流属性信息
	public static final String NEED_SHIPPING = "need_shipping";//是否需要发货
	public static final String COLLECTION_FEE = "collection_free";//代收邮费
	public static final String SHIPPING_COMPANY = "shipping_company";//物流公司编码
	public static final String N_SHIPPING_AMOUNT = "n_shipping_amount";//实收运费
	public static final String SHIPPING_TIME = "shipping_time";//配送时间
	public static final String OUT_HOUSE_ID = "out_house_id";//外系统发货仓库ID
	public static final String HOUSE_ID = "house_id";//发货仓库ID
	public static final String AREA_CODE = "area_code";//收货区编码
	public static final String CITY = "city";//收货人所在市
	public static final String CITY_CODE = "city_code";//收货市编码
	public static final String PROVINCE = "province";//收货人所在省
	public static final String PROVINCE_CODE = "provinc_code";//收货省编码
	public static final String SHIP_EMAIL = "ship_email";//收货人电子邮件
	public static final String REVEIVER_MOBILE = "receiver_mobile";//收货人手机号码
	public static final String SHIP_NAME = "ship_name";//收货人姓名
	public static final String DISTRICT = "district";//收货人所县区
	public static final String REGION_ID = "region_id";//收货人所县区编号
	public static final String CUSTOMER_TYPE = "CustomerType";//客户类型
	//public static final String SHIP_TYPE = "ship_type";//配送类型
	public static final String REFERENCE_PHONE = "reference_phone";//收货人电话
	public static final String POST_FEE = "post_fee";//物流费用
	public static final String SHIP_AREA = "ship_area";//收货商圈
	public static final String SHIP_BATCH = "ship_batch";//发货批次号
	public static final String CARRY_PERSON_MOBILE = "carry_person_mobile";//取件人联系电话
	public static final String CARRY_PERSON = "carry_person";//取件人
	public static final String SHIP_ADDR = "ship_addr";//邮寄地址、收货地址
	public static final String SHIP_ZIP = "ship_zip";//收货邮编
	public static final String LOGI_NO = "logi_no";//物流单号
	public static final String SHIPPING_COMPANY_NAME = "shipping_company_name";//物流公司名称
	
	//物流员工信息
	public static final String POST_LINKMAN = "post_linkman";//物流员工
	public static final String POST_TEL = "post_tel";//物流员工联系电话
	
	//订单支付属性信息
	public static final String PAY_PLATFORM_ORDER_ID = "payplatformorderid";//支付订单号
	public static final String PAY_CHANNEL_NAME = "paychannelname";//支付渠道名称
	public static final String ALIPAY_ID = "alipay_id";//第三方支付账户
	public static final String PAY_CHANNEL_ID = "paychannelid";//支付渠道编码
	public static final String PAY_PROVIDER_ID = "payproviderid";//支付机构编码
	public static final String PAY_PROVIDER_NAME = "payprovidername";//支付机构名称
	public static final String VOUCHERS_MONEY = "vouchers_money";//代金券面值
	public static final String DISFEE_TYPE = "disfee_type";//优惠类型
	public static final String VOUCHERS_CODE = "vouchers_code";//代金券编号
	public static final String COUPON_BATCH_ID = "couponbatchid";//代金券批次号
	public static final String COUPON_NAME = "couponname";//代金券名称
	public static final String DISCOUNT_VALUE = "discountValue"    ;//减免值              
	
	//订单商品单属性信息
	public static final String SOCIETY_PRICE = "society_price";//代理商终端结算价格
	public static final String SOCIETY_NAME = "society_name";//社会代理商名称
	public static final String PRO_PAC_DESC = "ProPacDesc";//产品包说明
	public static final String BANK_CODE = "bank_code";//银行编码
	public static final String BANK_USER = "bank_user";//银行开户名
	public static final String BANK_ACCOUNT = "bank_account";//银行支付账号
	public static final String COLLECTION_ACCOUNT = "collection_account";//托收帐号
	public static final String BSS_ORDER_TYPE = "bss_order_type";//BSS产品类型
	public static final String IS_CHANGE = "is_change";//是否变更套餐
	public static final String SUPERIORS_BANK_CODE = "superiors_bankcode";//上级银行编码
	public static final String PHONE_DEPOSIT = "phone_deposit";//活动预存款
	public static final String DISCOUNT_ID = "discountid";//优惠活动编号
	public static final String INVOICE_PRINT_TYPE = "invoice_print_type";//发票打印方式
	public static final String IS_BILL = "is_bill";//是否开发票
	public static final String COLLECTION = "collection";//是否托收
	public static final String BSS_ACCOUNT_TIME = "bss_account_time";//开户时间
	public static final String ACCOUNT_VALI = "account_vali";//开户人身份验证
	public static final String ACCOUNT_STATUS = "account_status";//开户状态
	public static final String BSS_STATUS = "bss_status";//BSS业务办理状态
	public static final String ESS_VALI_STATUS = "ess_vali_status";//BSS预校验状态
	public static final String ESS_VALI_RESULT = "ess_vali_result";//ESS预校验结果
	public static final String WRITE_CARD_STATUS = "write_card_status";//写卡状态
	public static final String ESS_ORDER_ID = "ess_order_id";//ESS一键开户订单编号
	public static final String PRINT_FLAG = "print_flag";//发票打印标志
	public static final String PRINT_TIME = "print_time";//发票打印时间
	public static final String PRINT_USER = "print_user";//发票打印工号
	public static final String INVOICE_NO = "invoice_no";//发票号码
	public static final String INVOICE_CODE = "invoice_code";//发票代码
	public static final String INVOICE_GROUP_CONTENT = "invoice_group_content";//发票内容
	public static final String INVENTORY_NAME = "inventory_name";//仓库名称
	public static final String INVOICE_TYPE = "invoice_type";//发票类型
	public static final String PROD_BRAND = "pro_brand";//产品品牌
	public static final String IS_CUSTOMIZED = "is_customized";//是否定制机
	public static final String GOODS_CAT_ID = "goods_cat_id";//商品小类
	public static final String SCRIPT_SEQ = "script_seq";//写卡脚本
	
	//订单货品单属性信息
	public static final String SIMID = "simid";//SIM卡号
	public static final String LIANG_PRICE = "liang_price";//靓号金额
	public static final String PHONE_NUM = "phone_num";//开户号码
	public static final String LOVES_PHONE_NUM = "loves_phone_num";//情侣号码
	public static final String SUB_NO = "sub_no";//共享子号
	public static final String FAMILY_NUM = "family_num";//亲情号码
	public static final String RELIEF_PRES_FLAG = "reliefpres_flag";//减免预存标记
	public static final String IS_LOVES_PHONE = "is_loves_phone";//是否情侣号码
	public static final String OLD_REMD_USER = "old_remd_user";//老用户推荐人
	public static final String IS_OLD = "is_old";//是否老用户  用户类型
	public static final String TERMINAL_NUM = "terminal_num";//终端串号
	public static final String FIRST_PAYMENT = "first_payment";//首月资费方式
	public static final String WHITE_CART_TYPE = "white_cart_type";//卡类型
	public static final String VICECARD_NO = "vicecard_no";//副卡号码
	public static final String CERT_CARD_NAME = "phone_owner_name";//开户人姓名（机主姓名） add by wui调整
	public static final String CERT_CARD_NUM = "cert_card_num";//开户人证件号码
	public static final String CERT_CARD_SEX = "cert_card_sex";//开户人性别
	public static final String CERT_CARD_BIRTH = "birthday";//开户人生日
	public static final String CERT_CARD_NATION = "cert_card_nation";//开户人民族
	public static final String CERT_ADDRESS = "cert_address";//开户人身份证发证机关
	public static final String ADJUSTMENT_CREDIT = "adjustment_credit";//信用度调整
	public static final String CERT_FAILURE_TIME = "cert_failure_time";//证件失效时间
	public static final String CERTI_TYPE = "certi_type";//证件类型
	public static final String GIFT_UNIT = "gift_unit";//赠品面值单位、赠品单位
	public static final String IS_PROCESS = "is_process";//是否需要加工
	public static final String PROCESS_TYPE = "process_type";//加工类型
	public static final String PROCESS_DESC = "process_desc";//加工内容
	public static final String CARD_SPECIES = "card_species";//礼品种类
	public static final String FACE_VALUE = "face_value";//卡面值
	public static final String SIM_TYPE = "sim_type";//卡种类 号卡性质
	public static final String GUARANTOR = "guarantor";//担保人
	public static final String GUARANTEE_INFO = "guarantee_info";//担保信息参数
	public static final String GUARANTOR_CERTI_TYPE = "guarantor_certi_type";//担保人证件类型
	public static final String GUARANTOR_CERTI_NO = "guarantor_certi_no";//担保人证件号码
	public static final String BILL_MAIL_TYPE = "bill_mail_type";//账单寄送方式
	public static final String BILL_MAIL_CONTENT = "bill_mail_content";//账单寄送内容
	public static final String BILL_MAIL_REC = "bill_mail_rec";//账单收件人
	public static final String BILL_MAIL_ADDR = "bill_mail_addr";//账单寄送地址
	public static final String BILL_MAIL_POST_CODE = "bill_mail_post_code";//账单寄送邮编
	public static final String CREDIT_CLASS = "creadit_class";//信用等级
	public static final String NET_REGION = "net_region";//入网地区
	public static final String BILL_TYPE = "bill_type";//账户付费方式
	public static final String FIRST_FEE_TYPE = "first_fee_type";//首月资费类型
	public static final String FIRST_FEE_TYPE_NAME = "first_fee_type_name";//首月资费类型名称
	public static final String ACTIVE_SORT = "active_sort";//生效方式
	public static final String ACTIVE_SORT_NAME = "active_sort_name";//生效方式名称
	public static final String BUY_MODE = "buy_mode";//购机方式
	public static final String BUY_MODE_NAME = "buy_mode_name";//购机方式名称
	public static final String PASSWORD = "password";//老用户密码
	public static final String TOTE_ID = "toteId";//周转箱编号
	
	public static final String CHOOSE_ACTIVE = "choose_active";//选择活动
	public static final String PACKAGE_CATEGORY = "package_category";//商品包子类
	public static final String PRODUCT_TYPE = "product_type";//货品类型
	public static final String PROD_AUDIT_STATUS = "prod_audit_status";//质检稽核确认状态
	public static final String IS_IPHONE_PLAN = "is_iphone_plan";//是否iphone套餐
	public static final String CARD_TYPE = "card_type";//号卡类型
	public static final String PHONE_OWNER_NAME = "phone_owner_name";//开户人姓名（机主姓名）
	public static final String IS_DIY_PLAN = "is_diy_plan";//4G套餐类型
	public static final String SIM_CARD_SKU = "sim_card_sku";
	
	//商品规格属性信息
	public static final String IS_LIANG = "is_liang";//是否靓号
	public static final String ACTIVE_TYPE = "ative_type";//活动类型
	public static final String DISFEE_SELECT = "disfee_select";//优惠选项
	public static final String DISCOUNTRANGE = "discountrange";//优惠幅度（优惠金额）
	public static final String DISCOUNTNAME = "discountname";//优惠活动名称
	public static final String ACTIVITY_LIST = "activity_list";//优惠信息
	public static final String ISGIFTS = "isgifts";//是否赠品
	public static final String OUT_PACKAGE_ID = "out_package_id";//合约编码/商品编码/活动编码
	public static final String GOODSNAME = "GoodsName";//商品名称
	public static final String BRAND_NUMBER = "brand_number";//品牌编码
	public static final String BRAND_NAME = "brand_name";//品牌名称
	public static final String COLOR_CODE = "color_code";//颜色编码
	public static final String MODEL_NAME = "model_name";//机型名称
	public static final String MODEL_CODE = "model_code";//机型编码
	public static final String SPECIFICATION_NAME = "specificatio_nname";//型号名称
	public static final String SPECIFICATION_CODE = "specification_code";//型号编码
	public static final String PLAN_TITLE = "plan_title";//套餐名称
	public static final String OUT_PLAN_ID_ESS = "out_plan_id_ess";//总部ESS套餐编码/产品编码
	public static final String OUT_PLAN_ID_BSS = "out_plan_id_bss";//总部BSS套餐编码
	public static final String SER_TYPE = "ser_type";//付费类型
	public static final String IS_GROUP_CONTRACT = "is_group_contract";//是否总部合约
	
	//赠品参数
	public static final String GIFT_TYPE = "gift_type";//赠品类型
	public static final String GIFT_ID = "gift_id";//赠品编码
	public static final String GIFT_NAME = "gift_name";//赠品名称
	public static final String GIFT_NUM = "gift_num";//赠品数量
	public static final String GIFT_VALUE = "gift_value";//赠品面值
	public static final String GIFT_BRAND = "gift_brand";//赠品品牌
	public static final String GIFT_MODEL = "gift_model";//赠品型号
	public static final String GIFT_COLOR = "gift_color";//赠品颜色
	public static final String GIFT_SKU = "gift_sku";//赠品机型
	public static final String GIFT_DESC = "gift_desc";//赠品说明
	
	public static final String HAS_VALUE_CODE_0 = "0";
	public static final String HAS_VALUE_CODE_1 = "1";
	
	public static final String ICCID = "ICCID";//大卡卡号
	public static final String READID = "readId";//写卡机编码
	public static final String STATIONNO = "stationNo";//稽核工位号
	
	public static final String PACK_TYPE = "pack_type";//商品包类型
	public static final String ERRORCOMMENTS = "ErrorComments";//写卡错误说明
	
	public static final String BSS_OPERATOR = "bss_operator";//BSS工号(2.0)
	public static final String BSS_OPERATOR_NAME = "bss_operator_name";//BSS员工名称
	public static final String SUPPLY_ID = "supply_id";//供应商id
	
	public static final String SYS_CODE="sys_code"; //订单由新系统还是旧系统处理
	
}

package zte.net.ecsord.params.busi.req;

import java.util.ArrayList;
import java.util.List;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteBusiRequest;
import params.ZteResponse;
import rule.IZteBusiRequestHander;
import zte.net.common.annontion.context.request.RequestBeanAnnontion;
import zte.net.common.annontion.context.request.RequestFieldAnnontion;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.common.params.req.ZteInstUpdateRequest;
import zte.net.common.params.resp.QueryResponse;
import zte.net.treeInst.RequestStoreExector;

@RequestBeanAnnontion(primary_keys = "order_id", table_name = "es_order_extvtl", service_desc = "订单属性纵表打横表")
public class OrderExtvlBusiRequest extends ZteBusiRequest<ZteResponse>implements IZteBusiRequestHander {

	@RequestFieldAnnontion()
	private String active_sort;
	@RequestFieldAnnontion()
	private String bill_mail_type;  
	@RequestFieldAnnontion()
	private String is_upload;
	@RequestFieldAnnontion()
	private String freeze_tran_no;
	@RequestFieldAnnontion()
	private String bill_mail_post_code;
	@RequestFieldAnnontion()
	private String buy_mode;
	@RequestFieldAnnontion()
	private String liang_code;
	@RequestFieldAnnontion()
	private String is_physics;
	@RequestFieldAnnontion()
	private String orderacccode;
	@RequestFieldAnnontion()
	private String bss_order_type;
	@RequestFieldAnnontion()
	private String bss_operator;
	@RequestFieldAnnontion()
	private String pro_brand;
	@RequestFieldAnnontion()
	private String buyer_name;
	@RequestFieldAnnontion()
	private String busi_credence_code;
	@RequestFieldAnnontion()
	private String guarantor_certi_no;
	@RequestFieldAnnontion()
	private String birthday;
	@RequestFieldAnnontion()
	private String ship_batch;
	@RequestFieldAnnontion()
	private String reserve2;
	@RequestFieldAnnontion()
	private String vouchers_code;
	@RequestFieldAnnontion()
	private String bu_price;
	@RequestFieldAnnontion()
	private String liang_price;
	@RequestFieldAnnontion()
	private String sex;
	@RequestFieldAnnontion()
	private String pro_realfee;
	@RequestFieldAnnontion()
	private String guarantor_info;
	@RequestFieldAnnontion()
	private String disfee_type;
	@RequestFieldAnnontion()
	private String group_code;
	@RequestFieldAnnontion()
	private String platform_status_name;
	@RequestFieldAnnontion()
	private String seller_message;
	@RequestFieldAnnontion()
	private String special_busi_type;
	@RequestFieldAnnontion()
	private String first_fee_type_name;
	@RequestFieldAnnontion()
	private String platform_status;
	@RequestFieldAnnontion()
	private String goodsname;
	@RequestFieldAnnontion()
	private String brand_name;
	@RequestFieldAnnontion()
	private String discountrange;
	@RequestFieldAnnontion()
	private String service_remarks;
	@RequestFieldAnnontion()
	private String merge_status;
	@RequestFieldAnnontion()
	private String bill_type;
	@RequestFieldAnnontion()
	private String discountname;
	@RequestFieldAnnontion()
	private String payplatformorderid;
	@RequestFieldAnnontion()
	private String reserve4;
	@RequestFieldAnnontion()
	private String zb_refund_status;
	@RequestFieldAnnontion()
	private String society_name;
	@RequestFieldAnnontion()
	private String bill_mail_rec;
	@RequestFieldAnnontion()
	private String zbpackages;
	@RequestFieldAnnontion()
	private String loves_phone_num;
	@RequestFieldAnnontion()
	private String readid;
	@RequestFieldAnnontion()
	private String plat_distributor_code;
	@RequestFieldAnnontion()
	private String bank_user;
	@RequestFieldAnnontion()
	private String payproviderid;
	@RequestFieldAnnontion()
	private String reserve0;
	@RequestFieldAnnontion()
	private String guarantor_certi_type;
	@RequestFieldAnnontion()
	private String file_return_type;
	@RequestFieldAnnontion()
	private String prod_audit_status;
	@RequestFieldAnnontion()
	private String ship_area;
	@RequestFieldAnnontion()
	private String couponbatchid;
	@RequestFieldAnnontion()
	private String choose_active;
	@RequestFieldAnnontion()
	private String adjustment_credit;
	@RequestFieldAnnontion()
	private String simid;
	@RequestFieldAnnontion()
	private String is_write_card;
	@RequestFieldAnnontion()
	private String paychannelid;
	@RequestFieldAnnontion()
	private String sr_status;
	@RequestFieldAnnontion()
	private String higher_league;
	@RequestFieldAnnontion()
	private String sim_type;
	@RequestFieldAnnontion()
	private String sell_price;
	@RequestFieldAnnontion()
	private String iccid;
	@RequestFieldAnnontion()
	private String paychannelname;
	@RequestFieldAnnontion()
	private String famliy_num;
	@RequestFieldAnnontion()
	private String order_provinc_code;
	@RequestFieldAnnontion()
	private String isgifts;
	@RequestFieldAnnontion()
	private String discountinfos;
	@RequestFieldAnnontion()
	private String giftinfos;
	@RequestFieldAnnontion()
	private String activity_list;
	@RequestFieldAnnontion()
	private String oss_operator;
	@RequestFieldAnnontion()
	private String fund_type;
	@RequestFieldAnnontion()
	private String orderacctype;
	@RequestFieldAnnontion()
	private String customertype;
	@RequestFieldAnnontion()
	private String reserve1;
	@RequestFieldAnnontion()
	private String recommended_code;
	@RequestFieldAnnontion()
	private String bss_refund_status;
	@RequestFieldAnnontion()
	private String net_type;
	@RequestFieldAnnontion()
	private String reserve6;
	@RequestFieldAnnontion()
	private String group_name;
	@RequestFieldAnnontion()
	private String reliefpres_flag;
	@RequestFieldAnnontion()
	private String card_type;
	@RequestFieldAnnontion()
	private String couponname;
	@RequestFieldAnnontion()
	private String specificatio_nname;
	@RequestFieldAnnontion()
	private String active_sort_name;
	@RequestFieldAnnontion()
	private String propacdesc;
	@RequestFieldAnnontion()
	private String regist_type;
	@RequestFieldAnnontion()
	private String order_disfee;
	@RequestFieldAnnontion()
	private String shipping_company_name;
	@RequestFieldAnnontion()
	private String stationno;
	@RequestFieldAnnontion()
	private String guarantor;
	@RequestFieldAnnontion()
	private String development_code;
	@RequestFieldAnnontion()
	private String syn_ord_zb;
	@RequestFieldAnnontion()
	private String buyer_message;
	@RequestFieldAnnontion()
	private String org_id;
	@RequestFieldAnnontion()
	private String disfee_select;
	@RequestFieldAnnontion()
	private String cust_service_name;
	@RequestFieldAnnontion()
	private String collection_free;
	@RequestFieldAnnontion()
	private String package_sale;
	@RequestFieldAnnontion()
	private String recyle_desc;
	@RequestFieldAnnontion()
	private String payfintime;
	@RequestFieldAnnontion()
	private String discountvalue;
	@RequestFieldAnnontion()
	private String out_plan_id_bss;
	@RequestFieldAnnontion()
	private String password;
	@RequestFieldAnnontion()
	private String specification_code;
	@RequestFieldAnnontion()
	private String feeinfo;
	@RequestFieldAnnontion()
	private String recommended_name;
	@RequestFieldAnnontion()
	private String is_group_contract;
	@RequestFieldAnnontion()
	private String face_value;
	@RequestFieldAnnontion()
	private String league_name;
	@RequestFieldAnnontion()
	private String buyer_id;
	@RequestFieldAnnontion()
	private String bank_code;
	@RequestFieldAnnontion()
	private String net_region;
	@RequestFieldAnnontion()
	private String is_liang;
	@RequestFieldAnnontion()
	private String wcfpackages;
	@RequestFieldAnnontion()
	private String toteid;
	@RequestFieldAnnontion()
	private String baidu_id;
	@RequestFieldAnnontion()
	private String sales_manager;
	@RequestFieldAnnontion()
	private String out_package_id;
	@RequestFieldAnnontion()
	private String order_origfee;
	@RequestFieldAnnontion()
	private String development_name;
	@RequestFieldAnnontion()
	private String syn_ord_wms;
	@RequestFieldAnnontion()
	private String order_join_category;
	@RequestFieldAnnontion()
	private String phone_owner_name;
	@RequestFieldAnnontion()
	private String vicecard_no;
	@RequestFieldAnnontion()
	private String reserve5;
	@RequestFieldAnnontion()
	private String is_send_goods;
	@RequestFieldAnnontion()
	private String bill_mail_addr;
	@RequestFieldAnnontion()
	private String tb_status;
	@RequestFieldAnnontion()
	private String vouchers_money;
	@RequestFieldAnnontion()
	private String recommended_phone;
	@RequestFieldAnnontion()
	private String wms_refund_status;
	@RequestFieldAnnontion()
	private String freeze_free;
	@RequestFieldAnnontion()
	private String sf_status;
	@RequestFieldAnnontion()
	private String bank_account;
	@RequestFieldAnnontion()
	private String credit_class;
	@RequestFieldAnnontion()
	private String source_type;
	@RequestFieldAnnontion()
	private String alipay_id;
	@RequestFieldAnnontion()
	private String ess_status;
	@RequestFieldAnnontion()
	private String reserve7;
	@RequestFieldAnnontion()
	private String order_deal_type;
	@RequestFieldAnnontion()
	private String out_plan_id_ess;
	@RequestFieldAnnontion()
	private String is_easy_account;
	@RequestFieldAnnontion()
	private String society_price;
	@RequestFieldAnnontion()
	private String ative_type;
	@RequestFieldAnnontion()
	private String nd_status;
	@RequestFieldAnnontion()
	private String payprovidername;
	@RequestFieldAnnontion()
	private String card_species;
	@RequestFieldAnnontion()
	private String bill_mail_content;
	@RequestFieldAnnontion()
	private String buy_mode_name;
	@RequestFieldAnnontion()
	private String new_shop_status;
	@RequestFieldAnnontion()
	private String is_diy_plan;
	@RequestFieldAnnontion()
	private String plan_title;
	@RequestFieldAnnontion()
	private String script_seq;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String create_time;
	@RequestFieldAnnontion()
	private String source_from;
	@RequestFieldAnnontion()
	private String item_id;
	@RequestFieldAnnontion()
	private String item_prod_inst_id;
	@RequestFieldAnnontion()
	private String payment_id;
	@RequestFieldAnnontion()
	private String member_id;
	@RequestFieldAnnontion()
	private String acceptanceform;
	@RequestFieldAnnontion()
	private String bss_operator_name;
	@RequestFieldAnnontion()
	private String invoice_type;
	@RequestFieldAnnontion()
	private String col1;
	@RequestFieldAnnontion()
	private String col2;
	@RequestFieldAnnontion()
	private String col3;
	@RequestFieldAnnontion()
	private String col4;
	@RequestFieldAnnontion()
	private String col5;
	@RequestFieldAnnontion()
	private String col6;
	@RequestFieldAnnontion()
	private String col7;
	@RequestFieldAnnontion()
	private String col8;
	@RequestFieldAnnontion()
	private String col9;
	@RequestFieldAnnontion()
	private String col10;
	@RequestFieldAnnontion()
	private String col11;
	@RequestFieldAnnontion()
	private String col12;
	@RequestFieldAnnontion()
	private String col13;
	@RequestFieldAnnontion()
	private String col14;
	@RequestFieldAnnontion()
	private String col15;
	@RequestFieldAnnontion()
	private String col16;
	@RequestFieldAnnontion()
	private String col17;
	@RequestFieldAnnontion()
	private String col18;
	@RequestFieldAnnontion()
	private String col19;
	@RequestFieldAnnontion()
	private String col20;
	@RequestFieldAnnontion()
	private String agent_name;
	@RequestFieldAnnontion()
	private String agent_city;
	@RequestFieldAnnontion()
	private String agent_district;
	@RequestFieldAnnontion()
	private String channel_type;
	@RequestFieldAnnontion()
	private String agent_code_dls;
	@RequestFieldAnnontion()
	private String supply_id;
	@RequestFieldAnnontion()
	private String sub_no;
	@RequestFieldAnnontion()
	private String sys_code;
	@RequestFieldAnnontion()
	private String delivery_id;
	@RequestFieldAnnontion()
	private String pre_lock_user_id;
	@RequestFieldAnnontion()
	private String is_examine_card;
	@RequestFieldAnnontion()
	private String chnlname;
	@RequestFieldAnnontion()
	private String channelid;
	@RequestFieldAnnontion()
	private String developernumber;
	@RequestFieldAnnontion()
	private String old_iccid;
	@RequestFieldAnnontion()
	private String proc_id;
	@RequestFieldAnnontion()
	private String active_id;
	@RequestFieldAnnontion()
	private String capacity_type_code;
	@RequestFieldAnnontion()
	private String res_kind_code;
	@RequestFieldAnnontion()
	private String card_real_fee;
	@RequestFieldAnnontion()
	private String cust_id;
	@RequestFieldAnnontion()
	private String is_refresh;
	@RequestFieldAnnontion()
	private String is_match_warehouse;
	@RequestFieldAnnontion()
	private String oldproductfee;
	@RequestFieldAnnontion()
	private String old_out_plan_name_ess;
	@RequestFieldAnnontion()
	private String oldactivitytype;
	@RequestFieldAnnontion()
	private String oldplanstarttime;
	@RequestFieldAnnontion()
	private String oldplanendtime;
	@RequestFieldAnnontion()
	private String proactivity;
	@RequestFieldAnnontion()
	private String serviceclasscode;
	@RequestFieldAnnontion()
	private String old_out_plan_id_ess;
	@RequestFieldAnnontion()
	private String extensiontag;
	@RequestFieldAnnontion()
	private String old_out_package_id;
	@RequestFieldAnnontion()
	private String old_out_package_name;
	@RequestFieldAnnontion()
	private String bipcode;
	@RequestFieldAnnontion()
	private String old_net_type;
	@RequestFieldAnnontion()
	private String virtual_pro_num;
	@RequestFieldAnnontion()
	private String usecustname;
	@RequestFieldAnnontion()
	private String usecustpspttype;
	@RequestFieldAnnontion()
	private String usecustpsptcode;
	@RequestFieldAnnontion()
	private String usecustpsptaddress;
	@RequestFieldAnnontion()
	private String itmprdgrouptype;
	@RequestFieldAnnontion()
	private String itmprdrespobsible;
	@RequestFieldAnnontion()
	private String app_type;
	@RequestFieldAnnontion()
	private String sub_app_type;
	@RequestFieldAnnontion()
	private String developdep_id;
	@RequestFieldAnnontion()
	private String couponslist;
	@RequestFieldAnnontion()
	private String business_package;
	@RequestFieldAnnontion()
	private String out_office;
	@RequestFieldAnnontion()
	private String out_operator;
	@RequestFieldAnnontion()
	private String industry_source;
	@RequestFieldAnnontion()
	private String service_type;
	@RequestFieldAnnontion()
	private Double gift_money;
	@RequestFieldAnnontion()
	private String broadinfo;
	@RequestFieldAnnontion()
	private String is_pay;
	@RequestFieldAnnontion()
	private String rhbroadinfo;
	@RequestFieldAnnontion()
	private String refundplatformorderid;
	@RequestFieldAnnontion()
	private String subs_id;
	@RequestFieldAnnontion()
	private String service_num;
	@RequestFieldAnnontion()
	private String old_out_operator;
	@RequestFieldAnnontion()
	private String old_out_office;
	@RequestFieldAnnontion()
	private String sale_mod_type;
	@RequestFieldAnnontion()
	private String marking_tag;
	@RequestFieldAnnontion()
	private String district_code;
	@RequestFieldAnnontion()
	private String cbss_out_operator;
	@RequestFieldAnnontion()
	private String cbss_out_office;
	@RequestFieldAnnontion()
	private String is_bss;
	@RequestFieldAnnontion()
	private String subprodinfo;
	@RequestFieldAnnontion()
	private String subotherinfo;
	@RequestFieldAnnontion()
	private String development_point_code;
	@RequestFieldAnnontion()
	private String development_point_name;
	@RequestFieldAnnontion()
	private String line_type;
	@RequestFieldAnnontion()
	private String order_charge_id;
	@RequestFieldAnnontion()
	private String result_url;
	@RequestFieldAnnontion()
	private String sys_option;
	@RequestFieldAnnontion()
	private String bus_order_id;
	@RequestFieldAnnontion()
	private String develop_point_code_new;
	@RequestFieldAnnontion()
	private String develop_code_new;
	@RequestFieldAnnontion()
	private String develop_name_new;
	@RequestFieldAnnontion()
	private String deal_office_type;
	@RequestFieldAnnontion()
	private String deal_operator_name;
	@RequestFieldAnnontion()
	private String deal_operator_num;
	@RequestFieldAnnontion()
	private String qy_seq_no;
	@RequestFieldAnnontion()
	private String qy_user_id;
	@RequestFieldAnnontion()
	private String oldgoodsname;
	@RequestFieldAnnontion()
	private String checktype;
	@RequestFieldAnnontion()
	private String intent_order_id;
	@RequestFieldAnnontion()
	private String bind_type;
	@RequestFieldAnnontion()
	private String allot_status;
	@RequestFieldAnnontion()
	private String is_realname;
	@RequestFieldAnnontion()
	private String opttype;
	@RequestFieldAnnontion()
	private String modtype;
	@RequestFieldAnnontion()
	private String mainnumber;
	@RequestFieldAnnontion()
	private String viceproductid;
	@RequestFieldAnnontion()
	private String ordertype;
	@RequestFieldAnnontion()
	private String seccardroletype;
	@RequestFieldAnnontion()
	private String productmode;
	@RequestFieldAnnontion()
	private String chg_type;
	@RequestFieldAnnontion()
	private String card_data;
	@RequestFieldAnnontion()
	private String sertype;
	@RequestFieldAnnontion()
	private String debutysn;
	@RequestFieldAnnontion()    
	private String  syn_mode;
	@RequestFieldAnnontion()
	private String evdo_num;
	@RequestFieldAnnontion()
	private String user_kind;
	
	@RequestFieldAnnontion()
    private String market_user_id;
    @RequestFieldAnnontion()
    private String seed_user_id;
    @RequestFieldAnnontion()
    private String share_svc_num;
    @RequestFieldAnnontion()
    private String activity_name;
    @RequestFieldAnnontion()
    private String object_esn;
    @RequestFieldAnnontion()
    private String sale_mode;
    @RequestFieldAnnontion()
    private String object_id;
    @RequestFieldAnnontion()
    private String user_to_account ;
    @RequestFieldAnnontion()
    private String is_order_master;
    @RequestFieldAnnontion()
    private String check_card_number;//校验成卡卡号
    @RequestFieldAnnontion()
    private String check_terminal_series;//校验终端串号
    @RequestFieldAnnontion()
    private String top_share_userid;//校验终端串号
    @RequestFieldAnnontion()
    private String top_share_num;//校验终端串号
    @RequestFieldAnnontion()
    private String is_new;//是否新装
    @RequestFieldAnnontion()
    private String is_blankcard;//是否白卡
    @RequestFieldAnnontion()
    private String is_no_modify;//该字段判断不更新订单字段
    @RequestFieldAnnontion()
    private String object_name;//该字段判断不更新订单字段

    @RequestFieldAnnontion()
    private String top_seed_professional_line;//顶级种子专业线
    @RequestFieldAnnontion()
    private String top_seed_type;//顶级种子类型
    @RequestFieldAnnontion()
    private String top_seed_group_id;//顶级种子分组
    @RequestFieldAnnontion()
    private String element_id;//泛智能终端元素编码
    @RequestFieldAnnontion()
    private String mobile_type;//泛智能终端终端类型
    
    @RequestFieldAnnontion()
    private String open_account_userid;// 开户人员工号
	@RequestFieldAnnontion()
    private String refund_userid;// 退单人员工号
	
	@RequestFieldAnnontion()
    private String cbss_develop_code;//cbss发展人
	@RequestFieldAnnontion()
    private String cbss_development_point_code;// cbss发展点
    public String getOpen_account_userid() {
		return open_account_userid;
	}

	public void setOpen_account_userid(String open_account_userid) {
		this.open_account_userid = open_account_userid;
	}

	public String getRefund_userid() {
		return refund_userid;
	}

	public void setRefund_userid(String refund_userid) {
		this.refund_userid = refund_userid;
	}
	
	public String getElement_id() {
		return element_id;
	}

	public void setElement_id(String element_id) {
		this.element_id = element_id;
	}

	public String getMobile_type() {
		return mobile_type;
	}

	public void setMobile_type(String mobile_type) {
		this.mobile_type = mobile_type;
	}
    public String getTop_seed_group_id() {
		return top_seed_group_id;
	}
	
	public void setTop_seed_group_id(String top_seed_group_id) {
		this.top_seed_group_id = top_seed_group_id;
	}
    
    public String getTop_seed_professional_line() {
    	return top_seed_professional_line;
    }
    
    public void setTop_seed_professional_line(String top_seed_professional_line) {
    	this.top_seed_professional_line = top_seed_professional_line;
    }
    
    public String getTop_seed_type() {
    	return top_seed_type;
    }
    
    public void setTop_seed_type(String top_seed_type) {
    	this.top_seed_type = top_seed_type;
    }
    
	public String getIs_no_modify() {
		return is_no_modify;
	}

	public void setIs_no_modify(String is_no_modify) {
		this.is_no_modify = is_no_modify;
	}

	public String getEvdo_num() {
		return evdo_num;
	}

	public void setEvdo_num(String evdo_num) {
		this.evdo_num = evdo_num;
	}

	public String getUser_kind() {
		return user_kind;
	}

	public void setUser_kind(String user_kind) {
		this.user_kind = user_kind;
	}

	public String getActive_sort() {
		return active_sort;
	}

	public void setActive_sort(String active_sort) {
		this.active_sort = active_sort;
	}

	public String getBill_mail_type() {
		return bill_mail_type;
	}

	public void setBill_mail_type(String bill_mail_type) {
		this.bill_mail_type = bill_mail_type;
	}

	public String getIs_upload() {
		return is_upload;
	}

	public void setIs_upload(String is_upload) {
		this.is_upload = is_upload;
	}

	public String getFreeze_tran_no() {
		return freeze_tran_no;
	}

	public void setFreeze_tran_no(String freeze_tran_no) {
		this.freeze_tran_no = freeze_tran_no;
	}

	public String getBill_mail_post_code() {
		return bill_mail_post_code;
	}

	public void setBill_mail_post_code(String bill_mail_post_code) {
		this.bill_mail_post_code = bill_mail_post_code;
	}

	public String getBuy_mode() {
		return buy_mode;
	}

	public void setBuy_mode(String buy_mode) {
		this.buy_mode = buy_mode;
	}

	public String getLiang_code() {
		return liang_code;
	}

	public String getObject_name() {
        return object_name;
    }

    public void setObject_name(String object_name) {
        this.object_name = object_name;
    }

    public void setLiang_code(String liang_code) {
		this.liang_code = liang_code;
	}

	public String getIs_physics() {
		return is_physics;
	}

	public void setIs_physics(String is_physics) {
		this.is_physics = is_physics;
	}

	public String getOrderacccode() {
		return orderacccode;
	}

	public void setOrderacccode(String orderacccode) {
		this.orderacccode = orderacccode;
	}

	public String getBss_order_type() {
		return bss_order_type;
	}

	public void setBss_order_type(String bss_order_type) {
		this.bss_order_type = bss_order_type;
	}

	public String getBss_operator() {
		return bss_operator;
	}

	public void setBss_operator(String bss_operator) {
		this.bss_operator = bss_operator;
	}

	public String getPro_brand() {
		return pro_brand;
	}

	public void setPro_brand(String pro_brand) {
		this.pro_brand = pro_brand;
	}

	public String getBuyer_name() {
		return buyer_name;
	}

	public void setBuyer_name(String buyer_name) {
		this.buyer_name = buyer_name;
	}

	public String getBusi_credence_code() {
		return busi_credence_code;
	}

	public void setBusi_credence_code(String busi_credence_code) {
		this.busi_credence_code = busi_credence_code;
	}

	public String getGuarantor_certi_no() {
		return guarantor_certi_no;
	}

	public void setGuarantor_certi_no(String guarantor_certi_no) {
		this.guarantor_certi_no = guarantor_certi_no;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getShip_batch() {
		return ship_batch;
	}

	public void setShip_batch(String ship_batch) {
		this.ship_batch = ship_batch;
	}

	public String getReserve2() {
		return reserve2;
	}

	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	public String getVouchers_code() {
		return vouchers_code;
	}

	public void setVouchers_code(String vouchers_code) {
		this.vouchers_code = vouchers_code;
	}

	public String getBu_price() {
		return bu_price;
	}

	public void setBu_price(String bu_price) {
		this.bu_price = bu_price;
	}

	public String getLiang_price() {
		return liang_price;
	}

	public void setLiang_price(String liang_price) {
		this.liang_price = liang_price;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPro_realfee() {
		return pro_realfee;
	}

	public void setPro_realfee(String pro_realfee) {
		this.pro_realfee = pro_realfee;
	}

	public String getGuarantor_info() {
		return guarantor_info;
	}

	public void setGuarantor_info(String guarantor_info) {
		this.guarantor_info = guarantor_info;
	}

	public String getDisfee_type() {
		return disfee_type;
	}

	public void setDisfee_type(String disfee_type) {
		this.disfee_type = disfee_type;
	}

	public String getGroup_code() {
		return group_code;
	}

	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}

	public String getPlatform_status_name() {
		return platform_status_name;
	}

	public void setPlatform_status_name(String platform_status_name) {
		this.platform_status_name = platform_status_name;
	}

	public String getSeller_message() {
		return seller_message;
	}

	public void setSeller_message(String seller_message) {
		this.seller_message = seller_message;
	}

	public String getSpecial_busi_type() {
		return special_busi_type;
	}

	public void setSpecial_busi_type(String special_busi_type) {
		this.special_busi_type = special_busi_type;
	}

	public String getFirst_fee_type_name() {
		return first_fee_type_name;
	}

	public void setFirst_fee_type_name(String first_fee_type_name) {
		this.first_fee_type_name = first_fee_type_name;
	}

	public String getPlatform_status() {
		return platform_status;
	}

	public void setPlatform_status(String platform_status) {
		this.platform_status = platform_status;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getDiscountrange() {
		return discountrange;
	}

	public void setDiscountrange(String discountrange) {
		this.discountrange = discountrange;
	}

	public String getService_remarks() {
		return service_remarks;
	}

	public void setService_remarks(String service_remarks) {
		this.service_remarks = service_remarks;
	}

	public String getMerge_status() {
		return merge_status;
	}

	public void setMerge_status(String merge_status) {
		this.merge_status = merge_status;
	}

	public String getBill_type() {
		return bill_type;
	}

	public void setBill_type(String bill_type) {
		this.bill_type = bill_type;
	}

	public String getDiscountname() {
		return discountname;
	}

	public void setDiscountname(String discountname) {
		this.discountname = discountname;
	}

	public String getPayplatformorderid() {
		return payplatformorderid;
	}

	public void setPayplatformorderid(String payplatformorderid) {
		this.payplatformorderid = payplatformorderid;
	}

	public String getReserve4() {
		return reserve4;
	}

	public void setReserve4(String reserve4) {
		this.reserve4 = reserve4;
	}

	public String getZb_refund_status() {
		return zb_refund_status;
	}

	public void setZb_refund_status(String zb_refund_status) {
		this.zb_refund_status = zb_refund_status;
	}

	public String getSociety_name() {
		return society_name;
	}

	public void setSociety_name(String society_name) {
		this.society_name = society_name;
	}

	public String getBill_mail_rec() {
		return bill_mail_rec;
	}

	public void setBill_mail_rec(String bill_mail_rec) {
		this.bill_mail_rec = bill_mail_rec;
	}

	public String getZbpackages() {
		return zbpackages;
	}

	public void setZbpackages(String zbpackages) {
		this.zbpackages = zbpackages;
	}

	public String getLoves_phone_num() {
		return loves_phone_num;
	}

	public void setLoves_phone_num(String loves_phone_num) {
		this.loves_phone_num = loves_phone_num;
	}

	public String getReadid() {
		return readid;
	}

	public void setReadid(String readid) {
		this.readid = readid;
	}

	public String getPlat_distributor_code() {
		return plat_distributor_code;
	}

	public void setPlat_distributor_code(String plat_distributor_code) {
		this.plat_distributor_code = plat_distributor_code;
	}

	public String getBank_user() {
		return bank_user;
	}

	public void setBank_user(String bank_user) {
		this.bank_user = bank_user;
	}

	public String getPayproviderid() {
		return payproviderid;
	}

	public void setPayproviderid(String payproviderid) {
		this.payproviderid = payproviderid;
	}

	public String getReserve0() {
		return reserve0;
	}

	public void setReserve0(String reserve0) {
		this.reserve0 = reserve0;
	}

	public String getGuarantor_certi_type() {
		return guarantor_certi_type;
	}

	public void setGuarantor_certi_type(String guarantor_certi_type) {
		this.guarantor_certi_type = guarantor_certi_type;
	}

	public String getFile_return_type() {
		return file_return_type;
	}

	public void setFile_return_type(String file_return_type) {
		this.file_return_type = file_return_type;
	}

	public String getProd_audit_status() {
		return prod_audit_status;
	}

	public void setProd_audit_status(String prod_audit_status) {
		this.prod_audit_status = prod_audit_status;
	}

	public String getShip_area() {
		return ship_area;
	}

	public void setShip_area(String ship_area) {
		this.ship_area = ship_area;
	}

	public String getCouponbatchid() {
		return couponbatchid;
	}

	public void setCouponbatchid(String couponbatchid) {
		this.couponbatchid = couponbatchid;
	}

	public String getChoose_active() {
		return choose_active;
	}

	public void setChoose_active(String choose_active) {
		this.choose_active = choose_active;
	}

	public String getAdjustment_credit() {
		return adjustment_credit;
	}

	public void setAdjustment_credit(String adjustment_credit) {
		this.adjustment_credit = adjustment_credit;
	}

	public String getSimid() {
		return simid;
	}

	public void setSimid(String simid) {
		this.simid = simid;
	}

	public String getIs_write_card() {
		return is_write_card;
	}

	public void setIs_write_card(String is_write_card) {
		this.is_write_card = is_write_card;
	}

	public String getPaychannelid() {
		return paychannelid;
	}

	public void setPaychannelid(String paychannelid) {
		this.paychannelid = paychannelid;
	}

	public String getSr_status() {
		return sr_status;
	}

	public void setSr_status(String sr_status) {
		this.sr_status = sr_status;
	}

	public String getHigher_league() {
		return higher_league;
	}

	public void setHigher_league(String higher_league) {
		this.higher_league = higher_league;
	}

	public String getSim_type() {
		return sim_type;
	}

	public void setSim_type(String sim_type) {
		this.sim_type = sim_type;
	}

	public String getSell_price() {
		return sell_price;
	}

	public void setSell_price(String sell_price) {
		this.sell_price = sell_price;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getPaychannelname() {
		return paychannelname;
	}

	public void setPaychannelname(String paychannelname) {
		this.paychannelname = paychannelname;
	}

	public String getFamliy_num() {
		return famliy_num;
	}

	public void setFamliy_num(String famliy_num) {
		this.famliy_num = famliy_num;
	}

	public String getOrder_provinc_code() {
		return order_provinc_code;
	}

	public void setOrder_provinc_code(String order_provinc_code) {
		this.order_provinc_code = order_provinc_code;
	}

	public String getIsgifts() {
		return isgifts;
	}

	public void setIsgifts(String isgifts) {
		this.isgifts = isgifts;
	}

	public String getDiscountinfos() {
		return discountinfos;
	}

	public void setDiscountinfos(String discountinfos) {
		this.discountinfos = discountinfos;
	}

	public String getGiftinfos() {
		return giftinfos;
	}

	public void setGiftinfos(String giftinfos) {
		this.giftinfos = giftinfos;
	}

	public String getActivity_list() {
		return activity_list;
	}

	public void setActivity_list(String activity_list) {
		this.activity_list = activity_list;
	}

	public String getOss_operator() {
		return oss_operator;
	}

	public void setOss_operator(String oss_operator) {
		this.oss_operator = oss_operator;
	}

	public String getFund_type() {
		return fund_type;
	}

	public void setFund_type(String fund_type) {
		this.fund_type = fund_type;
	}

	public String getOrderacctype() {
		return orderacctype;
	}

	public void setOrderacctype(String orderacctype) {
		this.orderacctype = orderacctype;
	}

	public String getCustomertype() {
		return customertype;
	}

	public void setCustomertype(String customertype) {
		this.customertype = customertype;
	}

	public String getReserve1() {
		return reserve1;
	}

	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}

	public String getRecommended_code() {
		return recommended_code;
	}

	public void setRecommended_code(String recommended_code) {
		this.recommended_code = recommended_code;
	}

	public String getBss_refund_status() {
		return bss_refund_status;
	}

	public void setBss_refund_status(String bss_refund_status) {
		this.bss_refund_status = bss_refund_status;
	}

	public String getNet_type() {
		return net_type;
	}

	public void setNet_type(String net_type) {
		this.net_type = net_type;
	}

	public String getReserve6() {
		return reserve6;
	}

	public void setReserve6(String reserve6) {
		this.reserve6 = reserve6;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getReliefpres_flag() {
		return reliefpres_flag;
	}

	public void setReliefpres_flag(String reliefpres_flag) {
		this.reliefpres_flag = reliefpres_flag;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public String getCouponname() {
		return couponname;
	}

	public void setCouponname(String couponname) {
		this.couponname = couponname;
	}

	public String getSpecificatio_nname() {
		return specificatio_nname;
	}

	public void setSpecificatio_nname(String specificatio_nname) {
		this.specificatio_nname = specificatio_nname;
	}

	public String getActive_sort_name() {
		return active_sort_name;
	}

	public void setActive_sort_name(String active_sort_name) {
		this.active_sort_name = active_sort_name;
	}

	public String getPropacdesc() {
		return propacdesc;
	}

	public void setPropacdesc(String propacdesc) {
		this.propacdesc = propacdesc;
	}

	public String getRegist_type() {
		return regist_type;
	}

	public void setRegist_type(String regist_type) {
		this.regist_type = regist_type;
	}

	public String getOrder_disfee() {
		return order_disfee;
	}

	public void setOrder_disfee(String order_disfee) {
		this.order_disfee = order_disfee;
	}

	public String getShipping_company_name() {
		return shipping_company_name;
	}

	public void setShipping_company_name(String shipping_company_name) {
		this.shipping_company_name = shipping_company_name;
	}

	public String getStationno() {
		return stationno;
	}

	public void setStationno(String stationno) {
		this.stationno = stationno;
	}

	public String getGuarantor() {
		return guarantor;
	}

	public void setGuarantor(String guarantor) {
		this.guarantor = guarantor;
	}

	public String getDevelopment_code() {
		return development_code;
	}

	public void setDevelopment_code(String development_code) {
		this.development_code = development_code;
	}

	public String getSyn_ord_zb() {
		return syn_ord_zb;
	}

	public void setSyn_ord_zb(String syn_ord_zb) {
		this.syn_ord_zb = syn_ord_zb;
	}

	public String getBuyer_message() {
		return buyer_message;
	}

	public void setBuyer_message(String buyer_message) {
		this.buyer_message = buyer_message;
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	public String getDisfee_select() {
		return disfee_select;
	}

	public void setDisfee_select(String disfee_select) {
		this.disfee_select = disfee_select;
	}

	public String getCust_service_name() {
		return cust_service_name;
	}

	public void setCust_service_name(String cust_service_name) {
		this.cust_service_name = cust_service_name;
	}

	public String getCollection_free() {
		return collection_free;
	}

	public void setCollection_free(String collection_free) {
		this.collection_free = collection_free;
	}

	public String getPackage_sale() {
		return package_sale;
	}

	public void setPackage_sale(String package_sale) {
		this.package_sale = package_sale;
	}

	public String getRecyle_desc() {
		return recyle_desc;
	}

	public void setRecyle_desc(String recyle_desc) {
		this.recyle_desc = recyle_desc;
	}

	public String getPayfintime() {
		return payfintime;
	}

	public void setPayfintime(String payfintime) {
		this.payfintime = payfintime;
	}

	public String getDiscountvalue() {
		return discountvalue;
	}

	public void setDiscountvalue(String discountvalue) {
		this.discountvalue = discountvalue;
	}

	public String getOut_plan_id_bss() {
		return out_plan_id_bss;
	}

	public void setOut_plan_id_bss(String out_plan_id_bss) {
		this.out_plan_id_bss = out_plan_id_bss;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSpecification_code() {
		return specification_code;
	}

	public void setSpecification_code(String specification_code) {
		this.specification_code = specification_code;
	}

	public String getFeeinfo() {
		return feeinfo;
	}

	public void setFeeinfo(String feeinfo) {
		this.feeinfo = feeinfo;
	}

	public String getRecommended_name() {
		return recommended_name;
	}

	public void setRecommended_name(String recommended_name) {
		this.recommended_name = recommended_name;
	}

	public String getIs_group_contract() {
		return is_group_contract;
	}

	public void setIs_group_contract(String is_group_contract) {
		this.is_group_contract = is_group_contract;
	}

	public String getFace_value() {
		return face_value;
	}

	public void setFace_value(String face_value) {
		this.face_value = face_value;
	}

	public String getLeague_name() {
		return league_name;
	}

	public void setLeague_name(String league_name) {
		this.league_name = league_name;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getNet_region() {
		return net_region;
	}

	public void setNet_region(String net_region) {
		this.net_region = net_region;
	}

	public String getIs_liang() {
		return is_liang;
	}

	public void setIs_liang(String is_liang) {
		this.is_liang = is_liang;
	}

	public String getWcfpackages() {
		return wcfpackages;
	}

	public void setWcfpackages(String wcfpackages) {
		this.wcfpackages = wcfpackages;
	}

	public String getToteid() {
		return toteid;
	}

	public void setToteid(String toteid) {
		this.toteid = toteid;
	}

	public String getBaidu_id() {
		return baidu_id;
	}

	public void setBaidu_id(String baidu_id) {
		this.baidu_id = baidu_id;
	}

	public String getSales_manager() {
		return sales_manager;
	}

	public void setSales_manager(String sales_manager) {
		this.sales_manager = sales_manager;
	}

	public String getOut_package_id() {
		return out_package_id;
	}

	public void setOut_package_id(String out_package_id) {
		this.out_package_id = out_package_id;
	}

	public String getOrder_origfee() {
		return order_origfee;
	}

	public void setOrder_origfee(String order_origfee) {
		this.order_origfee = order_origfee;
	}

	public String getDevelopment_name() {
		return development_name;
	}

	public void setDevelopment_name(String development_name) {
		this.development_name = development_name;
	}

	public String getSyn_ord_wms() {
		return syn_ord_wms;
	}

	public void setSyn_ord_wms(String syn_ord_wms) {
		this.syn_ord_wms = syn_ord_wms;
	}

	public String getOrder_join_category() {
		return order_join_category;
	}

	public void setOrder_join_category(String order_join_category) {
		this.order_join_category = order_join_category;
	}

	public String getPhone_owner_name() {
		return phone_owner_name;
	}

	public void setPhone_owner_name(String phone_owner_name) {
		this.phone_owner_name = phone_owner_name;
	}

	public String getVicecard_no() {
		return vicecard_no;
	}

	public void setVicecard_no(String vicecard_no) {
		this.vicecard_no = vicecard_no;
	}

	public String getReserve5() {
		return reserve5;
	}

	public void setReserve5(String reserve5) {
		this.reserve5 = reserve5;
	}

	public String getIs_send_goods() {
		return is_send_goods;
	}

	public void setIs_send_goods(String is_send_goods) {
		this.is_send_goods = is_send_goods;
	}

	public String getBill_mail_addr() {
		return bill_mail_addr;
	}

	public void setBill_mail_addr(String bill_mail_addr) {
		this.bill_mail_addr = bill_mail_addr;
	}

	public String getTb_status() {
		return tb_status;
	}

	public void setTb_status(String tb_status) {
		this.tb_status = tb_status;
	}

	public String getVouchers_money() {
		return vouchers_money;
	}

	public void setVouchers_money(String vouchers_money) {
		this.vouchers_money = vouchers_money;
	}

	public String getRecommended_phone() {
		return recommended_phone;
	}

	public void setRecommended_phone(String recommended_phone) {
		this.recommended_phone = recommended_phone;
	}

	public String getWms_refund_status() {
		return wms_refund_status;
	}

	public void setWms_refund_status(String wms_refund_status) {
		this.wms_refund_status = wms_refund_status;
	}

	public String getFreeze_free() {
		return freeze_free;
	}

	public void setFreeze_free(String freeze_free) {
		this.freeze_free = freeze_free;
	}

	public String getSf_status() {
		return sf_status;
	}

	public void setSf_status(String sf_status) {
		this.sf_status = sf_status;
	}

	public String getBank_account() {
		return bank_account;
	}

	public void setBank_account(String bank_account) {
		this.bank_account = bank_account;
	}

	public String getCredit_class() {
		return credit_class;
	}

	public void setCredit_class(String credit_class) {
		this.credit_class = credit_class;
	}

	public String getSource_type() {
		return source_type;
	}

	public void setSource_type(String source_type) {
		this.source_type = source_type;
	}

	public String getAlipay_id() {
		return alipay_id;
	}

	public void setAlipay_id(String alipay_id) {
		this.alipay_id = alipay_id;
	}

	public String getEss_status() {
		return ess_status;
	}

	public void setEss_status(String ess_status) {
		this.ess_status = ess_status;
	}

	public String getReserve7() {
		return reserve7;
	}

	public void setReserve7(String reserve7) {
		this.reserve7 = reserve7;
	}

	public String getOrder_deal_type() {
		return order_deal_type;
	}

	public void setOrder_deal_type(String order_deal_type) {
		this.order_deal_type = order_deal_type;
	}

	public String getOut_plan_id_ess() {
		return out_plan_id_ess;
	}

	public void setOut_plan_id_ess(String out_plan_id_ess) {
		this.out_plan_id_ess = out_plan_id_ess;
	}

	public String getIs_easy_account() {
		return is_easy_account;
	}

	public void setIs_easy_account(String is_easy_account) {
		this.is_easy_account = is_easy_account;
	}

	public String getSociety_price() {
		return society_price;
	}

	public void setSociety_price(String society_price) {
		this.society_price = society_price;
	}

	public String getAtive_type() {
		return ative_type;
	}

	public void setAtive_type(String ative_type) {
		this.ative_type = ative_type;
	}

	public String getNd_status() {
		return nd_status;
	}

	public void setNd_status(String nd_status) {
		this.nd_status = nd_status;
	}

	public String getPayprovidername() {
		return payprovidername;
	}

	public void setPayprovidername(String payprovidername) {
		this.payprovidername = payprovidername;
	}

	public String getCard_species() {
		return card_species;
	}

	public void setCard_species(String card_species) {
		this.card_species = card_species;
	}

	public String getBill_mail_content() {
		return bill_mail_content;
	}

	public void setBill_mail_content(String bill_mail_content) {
		this.bill_mail_content = bill_mail_content;
	}

	public String getBuy_mode_name() {
		return buy_mode_name;
	}

	public void setBuy_mode_name(String buy_mode_name) {
		this.buy_mode_name = buy_mode_name;
	}

	public String getNew_shop_status() {
		return new_shop_status;
	}

	public void setNew_shop_status(String new_shop_status) {
		this.new_shop_status = new_shop_status;
	}

	public String getIs_diy_plan() {
		return is_diy_plan;
	}

	public void setIs_diy_plan(String is_diy_plan) {
		this.is_diy_plan = is_diy_plan;
	}

	public String getPlan_title() {
		return plan_title;
	}

	public void setPlan_title(String plan_title) {
		this.plan_title = plan_title;
	}

	public String getScript_seq() {
		return script_seq;
	}

	public void setScript_seq(String script_seq) {
		this.script_seq = script_seq;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getItem_prod_inst_id() {
		return item_prod_inst_id;
	}

	public void setItem_prod_inst_id(String item_prod_inst_id) {
		this.item_prod_inst_id = item_prod_inst_id;
	}

	public String getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getAcceptanceform() {
		return acceptanceform;
	}

	public void setAcceptanceform(String acceptanceform) {
		this.acceptanceform = acceptanceform;
	}

	public String getBss_operator_name() {
		return bss_operator_name;
	}

	public void setBss_operator_name(String bss_operator_name) {
		this.bss_operator_name = bss_operator_name;
	}

	public String getInvoice_type() {
		return invoice_type;
	}

	public void setInvoice_type(String invoice_type) {
		this.invoice_type = invoice_type;
	}

	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}

	public String getCol2() {
		return col2;
	}

	public void setCol2(String col2) {
		this.col2 = col2;
	}

	public String getCol3() {
		return col3;
	}

	public void setCol3(String col3) {
		this.col3 = col3;
	}

	public String getCol4() {
		return col4;
	}

	public void setCol4(String col4) {
		this.col4 = col4;
	}

	public String getCol5() {
		return col5;
	}

	public void setCol5(String col5) {
		this.col5 = col5;
	}

	public String getCol6() {
		return col6;
	}

	public void setCol6(String col6) {
		this.col6 = col6;
	}

	public String getCol7() {
		return col7;
	}

	public void setCol7(String col7) {
		this.col7 = col7;
	}

	public String getCol8() {
		return col8;
	}

	public void setCol8(String col8) {
		this.col8 = col8;
	}

	public String getCol9() {
		return col9;
	}

	public void setCol9(String col9) {
		this.col9 = col9;
	}

	public String getCol10() {
		return col10;
	}

	public void setCol10(String col10) {
		this.col10 = col10;
	}

	public String getCol11() {
		return col11;
	}

	public void setCol11(String col11) {
		this.col11 = col11;
	}

	public String getCol12() {
		return col12;
	}

	public void setCol12(String col12) {
		this.col12 = col12;
	}

	public String getCol13() {
		return col13;
	}

	public void setCol13(String col13) {
		this.col13 = col13;
	}

	public String getCol14() {
		return col14;
	}

	public void setCol14(String col14) {
		this.col14 = col14;
	}

	public String getCol15() {
		return col15;
	}

	public void setCol15(String col15) {
		this.col15 = col15;
	}

	public String getCol16() {
		return col16;
	}

	public void setCol16(String col16) {
		this.col16 = col16;
	}

	public String getCol17() {
		return col17;
	}

	public void setCol17(String col17) {
		this.col17 = col17;
	}

	public String getCol18() {
		return col18;
	}

	public void setCol18(String col18) {
		this.col18 = col18;
	}

	public String getCol19() {
		return col19;
	}

	public void setCol19(String col19) {
		this.col19 = col19;
	}

	public String getCol20() {
		return col20;
	}

	public void setCol20(String col20) {
		this.col20 = col20;
	}

	public String getAgent_name() {
		return agent_name;
	}

	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}

	public String getAgent_city() {
		return agent_city;
	}

	public void setAgent_city(String agent_city) {
		this.agent_city = agent_city;
	}

	public String getAgent_district() {
		return agent_district;
	}

	public void setAgent_district(String agent_district) {
		this.agent_district = agent_district;
	}

	public String getChannel_type() {
		return channel_type;
	}

	public void setChannel_type(String channel_type) {
		this.channel_type = channel_type;
	}

	public String getAgent_code_dls() {
		return agent_code_dls;
	}

	public void setAgent_code_dls(String agent_code_dls) {
		this.agent_code_dls = agent_code_dls;
	}

	public String getSupply_id() {
		return supply_id;
	}

	public void setSupply_id(String supply_id) {
		this.supply_id = supply_id;
	}

	public String getSub_no() {
		return sub_no;
	}

	public void setSub_no(String sub_no) {
		this.sub_no = sub_no;
	}

	public String getSys_code() {
		return sys_code;
	}

	public void setSys_code(String sys_code) {
		this.sys_code = sys_code;
	}

	public String getDelivery_id() {
		return delivery_id;
	}

	public void setDelivery_id(String delivery_id) {
		this.delivery_id = delivery_id;
	}

	public String getPre_lock_user_id() {
		return pre_lock_user_id;
	}

	public void setPre_lock_user_id(String pre_lock_user_id) {
		this.pre_lock_user_id = pre_lock_user_id;
	}

	public String getIs_examine_card() {
		return is_examine_card;
	}

	public void setIs_examine_card(String is_examine_card) {
		this.is_examine_card = is_examine_card;
	}

	public String getChnlname() {
		return chnlname;
	}

	public void setChnlname(String chnlname) {
		this.chnlname = chnlname;
	}

	public String getChannelid() {
		return channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}

	public String getDevelopernumber() {
		return developernumber;
	}

	public void setDevelopernumber(String developernumber) {
		this.developernumber = developernumber;
	}

	public String getOld_iccid() {
		return old_iccid;
	}

	public void setOld_iccid(String old_iccid) {
		this.old_iccid = old_iccid;
	}

	public String getProc_id() {
		return proc_id;
	}

	public void setProc_id(String proc_id) {
		this.proc_id = proc_id;
	}

	public String getActive_id() {
		return active_id;
	}

	public void setActive_id(String active_id) {
		this.active_id = active_id;
	}

	public String getCapacity_type_code() {
		return capacity_type_code;
	}

	public void setCapacity_type_code(String capacity_type_code) {
		this.capacity_type_code = capacity_type_code;
	}

	public String getRes_kind_code() {
		return res_kind_code;
	}

	public void setRes_kind_code(String res_kind_code) {
		this.res_kind_code = res_kind_code;
	}

	public String getCard_real_fee() {
		return card_real_fee;
	}

	public void setCard_real_fee(String card_real_fee) {
		this.card_real_fee = card_real_fee;
	}

	public String getCust_id() {
		return cust_id;
	}

	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	public String getIs_refresh() {
		return is_refresh;
	}

	public void setIs_refresh(String is_refresh) {
		this.is_refresh = is_refresh;
	}

	public String getIs_match_warehouse() {
		return is_match_warehouse;
	}

	public void setIs_match_warehouse(String is_match_warehouse) {
		this.is_match_warehouse = is_match_warehouse;
	}

	public String getOldproductfee() {
		return oldproductfee;
	}

	public void setOldproductfee(String oldproductfee) {
		this.oldproductfee = oldproductfee;
	}

	public String getOld_out_plan_name_ess() {
		return old_out_plan_name_ess;
	}

	public void setOld_out_plan_name_ess(String old_out_plan_name_ess) {
		this.old_out_plan_name_ess = old_out_plan_name_ess;
	}

	public String getOldactivitytype() {
		return oldactivitytype;
	}

	public void setOldactivitytype(String oldactivitytype) {
		this.oldactivitytype = oldactivitytype;
	}

	public String getOldplanstarttime() {
		return oldplanstarttime;
	}

	public void setOldplanstarttime(String oldplanstarttime) {
		this.oldplanstarttime = oldplanstarttime;
	}

	public String getOldplanendtime() {
		return oldplanendtime;
	}

	public void setOldplanendtime(String oldplanendtime) {
		this.oldplanendtime = oldplanendtime;
	}

	public String getProactivity() {
		return proactivity;
	}

	public void setProactivity(String proactivity) {
		this.proactivity = proactivity;
	}

	public String getServiceclasscode() {
		return serviceclasscode;
	}

	public void setServiceclasscode(String serviceclasscode) {
		this.serviceclasscode = serviceclasscode;
	}

	public String getOld_out_plan_id_ess() {
		return old_out_plan_id_ess;
	}

	public void setOld_out_plan_id_ess(String old_out_plan_id_ess) {
		this.old_out_plan_id_ess = old_out_plan_id_ess;
	}

	public String getExtensiontag() {
		return extensiontag;
	}

	public void setExtensiontag(String extensiontag) {
		this.extensiontag = extensiontag;
	}

	public String getOld_out_package_id() {
		return old_out_package_id;
	}

	public void setOld_out_package_id(String old_out_package_id) {
		this.old_out_package_id = old_out_package_id;
	}

	public String getOld_out_package_name() {
		return old_out_package_name;
	}

	public void setOld_out_package_name(String old_out_package_name) {
		this.old_out_package_name = old_out_package_name;
	}

	public String getBipcode() {
		return bipcode;
	}

	public void setBipcode(String bipcode) {
		this.bipcode = bipcode;
	}

	public String getOld_net_type() {
		return old_net_type;
	}

	public void setOld_net_type(String old_net_type) {
		this.old_net_type = old_net_type;
	}

	public String getVirtual_pro_num() {
		return virtual_pro_num;
	}

	public void setVirtual_pro_num(String virtual_pro_num) {
		this.virtual_pro_num = virtual_pro_num;
	}

	public String getUsecustname() {
		return usecustname;
	}

	public void setUsecustname(String usecustname) {
		this.usecustname = usecustname;
	}

	public String getUsecustpspttype() {
		return usecustpspttype;
	}

	public void setUsecustpspttype(String usecustpspttype) {
		this.usecustpspttype = usecustpspttype;
	}

	public String getUsecustpsptcode() {
		return usecustpsptcode;
	}

	public void setUsecustpsptcode(String usecustpsptcode) {
		this.usecustpsptcode = usecustpsptcode;
	}

	public String getUsecustpsptaddress() {
		return usecustpsptaddress;
	}

	public void setUsecustpsptaddress(String usecustpsptaddress) {
		this.usecustpsptaddress = usecustpsptaddress;
	}

	public String getItmprdgrouptype() {
		return itmprdgrouptype;
	}

	public void setItmprdgrouptype(String itmprdgrouptype) {
		this.itmprdgrouptype = itmprdgrouptype;
	}

	public String getItmprdrespobsible() {
		return itmprdrespobsible;
	}

	public void setItmprdrespobsible(String itmprdrespobsible) {
		this.itmprdrespobsible = itmprdrespobsible;
	}

	public String getApp_type() {
		return app_type;
	}

	public void setApp_type(String app_type) {
		this.app_type = app_type;
	}

	public String getSub_app_type() {
		return sub_app_type;
	}

	public void setSub_app_type(String sub_app_type) {
		this.sub_app_type = sub_app_type;
	}

	public String getDevelopdep_id() {
		return developdep_id;
	}

	public void setDevelopdep_id(String developdep_id) {
		this.developdep_id = developdep_id;
	}

	public String getCouponslist() {
		return couponslist;
	}

	public void setCouponslist(String couponslist) {
		this.couponslist = couponslist;
	}

	public String getBusiness_package() {
		return business_package;
	}

	public void setBusiness_package(String business_package) {
		this.business_package = business_package;
	}

	public String getOut_office() {
		return out_office;
	}

	public void setOut_office(String out_office) {
		this.out_office = out_office;
	}

	public String getOut_operator() {
		return out_operator;
	}

	public void setOut_operator(String out_operator) {
		this.out_operator = out_operator;
	}

	public String getIndustry_source() {
		return industry_source;
	}

	public void setIndustry_source(String industry_source) {
		this.industry_source = industry_source;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public Double getGift_money() {
		return gift_money;
	}

	public void setGift_money(Double gift_money) {
		this.gift_money = gift_money;
	}

	public String getBroadinfo() {
		return broadinfo;
	}

	public void setBroadinfo(String broadinfo) {
		this.broadinfo = broadinfo;
	}

	public String getIs_pay() {
		return is_pay;
	}

	public void setIs_pay(String is_pay) {
		this.is_pay = is_pay;
	}

	public String getRhbroadinfo() {
		return rhbroadinfo;
	}

	public void setRhbroadinfo(String rhbroadinfo) {
		this.rhbroadinfo = rhbroadinfo;
	}

	public String getRefundplatformorderid() {
		return refundplatformorderid;
	}

	public void setRefundplatformorderid(String refundplatformorderid) {
		this.refundplatformorderid = refundplatformorderid;
	}

	public String getSubs_id() {
		return subs_id;
	}

	public void setSubs_id(String subs_id) {
		this.subs_id = subs_id;
	}

	public String getService_num() {
		return service_num;
	}

	public void setService_num(String service_num) {
		this.service_num = service_num;
	}

	public String getOld_out_operator() {
		return old_out_operator;
	}

	public void setOld_out_operator(String old_out_operator) {
		this.old_out_operator = old_out_operator;
	}

	public String getOld_out_office() {
		return old_out_office;
	}

	public void setOld_out_office(String old_out_office) {
		this.old_out_office = old_out_office;
	}

	public String getSale_mod_type() {
		return sale_mod_type;
	}

	public void setSale_mod_type(String sale_mod_type) {
		this.sale_mod_type = sale_mod_type;
	}

	public String getMarking_tag() {
		return marking_tag;
	}

	public void setMarking_tag(String marking_tag) {
		this.marking_tag = marking_tag;
	}

	public String getDistrict_code() {
		return district_code;
	}

	public void setDistrict_code(String district_code) {
		this.district_code = district_code;
	}

	public String getCbss_out_operator() {
		return cbss_out_operator;
	}

	public void setCbss_out_operator(String cbss_out_operator) {
		this.cbss_out_operator = cbss_out_operator;
	}

	public String getCbss_out_office() {
		return cbss_out_office;
	}

	public void setCbss_out_office(String cbss_out_office) {
		this.cbss_out_office = cbss_out_office;
	}

	public String getIs_bss() {
		return is_bss;
	}

	public void setIs_bss(String is_bss) {
		this.is_bss = is_bss;
	}

	public String getSubprodinfo() {
		return subprodinfo;
	}

	public void setSubprodinfo(String subprodinfo) {
		this.subprodinfo = subprodinfo;
	}

	public String getSubotherinfo() {
		return subotherinfo;
	}

	public void setSubotherinfo(String subotherinfo) {
		this.subotherinfo = subotherinfo;
	}

	public String getDevelopment_point_code() {
		return development_point_code;
	}

	public void setDevelopment_point_code(String development_point_code) {
		this.development_point_code = development_point_code;
	}

	public String getDevelopment_point_name() {
		return development_point_name;
	}

	public void setDevelopment_point_name(String development_point_name) {
		this.development_point_name = development_point_name;
	}

	public String getLine_type() {
		return line_type;
	}

	public void setLine_type(String line_type) {
		this.line_type = line_type;
	}

	public String getOrder_charge_id() {
		return order_charge_id;
	}

	public void setOrder_charge_id(String order_charge_id) {
		this.order_charge_id = order_charge_id;
	}

	public String getResult_url() {
		return result_url;
	}

	public void setResult_url(String result_url) {
		this.result_url = result_url;
	}

	public String getSys_option() {
		return sys_option;
	}

	public void setSys_option(String sys_option) {
		this.sys_option = sys_option;
	}

	public String getBus_order_id() {
		return bus_order_id;
	}

	public void setBus_order_id(String bus_order_id) {
		this.bus_order_id = bus_order_id;
	}

	public String getDevelop_point_code_new() {
		return develop_point_code_new;
	}

	public void setDevelop_point_code_new(String develop_point_code_new) {
		this.develop_point_code_new = develop_point_code_new;
	}

	public String getDevelop_code_new() {
		return develop_code_new;
	}

	public void setDevelop_code_new(String develop_code_new) {
		this.develop_code_new = develop_code_new;
	}

	public String getDevelop_name_new() {
		return develop_name_new;
	}

	public void setDevelop_name_new(String develop_name_new) {
		this.develop_name_new = develop_name_new;
	}

	public String getDeal_office_type() {
		return deal_office_type;
	}

	public void setDeal_office_type(String deal_office_type) {
		this.deal_office_type = deal_office_type;
	}

	public String getDeal_operator_name() {
		return deal_operator_name;
	}

	public void setDeal_operator_name(String deal_operator_name) {
		this.deal_operator_name = deal_operator_name;
	}

	public String getDeal_operator_num() {
		return deal_operator_num;
	}

	public void setDeal_operator_num(String deal_operator_num) {
		this.deal_operator_num = deal_operator_num;
	}

	public String getQy_seq_no() {
		return qy_seq_no;
	}

	public void setQy_seq_no(String qy_seq_no) {
		this.qy_seq_no = qy_seq_no;
	}

	public String getQy_user_id() {
		return qy_user_id;
	}

	public void setQy_user_id(String qy_user_id) {
		this.qy_user_id = qy_user_id;
	}

	public String getOldgoodsname() {
		return oldgoodsname;
	}

	public void setOldgoodsname(String oldgoodsname) {
		this.oldgoodsname = oldgoodsname;
	}

	public String getChecktype() {
		return checktype;
	}

	public void setChecktype(String checktype) {
		this.checktype = checktype;
	}

	public String getIntent_order_id() {
		return intent_order_id;
	}

	public void setIntent_order_id(String intent_order_id) {
		this.intent_order_id = intent_order_id;
	}

	public String getBind_type() {
		return bind_type;
	}

	public void setBind_type(String bind_type) {
		this.bind_type = bind_type;
	}

	public String getAllot_status() {
		return allot_status;
	}

	public void setAllot_status(String allot_status) {
		this.allot_status = allot_status;
	}

    public String getIs_realname() {
		return is_realname;
	}

	public void setIs_realname(String is_realname) {
		this.is_realname = is_realname;
	}

	public String getOpttype() {
		return opttype;
	}

	public void setOpttype(String opttype) {
		this.opttype = opttype;
	}

	public String getModtype() {
		return modtype;
	}

	public void setModtype(String modtype) {
		this.modtype = modtype;
	}

	public String getMainnumber() {
		return mainnumber;
	}

	public void setMainnumber(String mainnumber) {
		this.mainnumber = mainnumber;
	}

	public String getViceproductid() {
		return viceproductid;
	}

	public void setViceproductid(String viceproductid) {
		this.viceproductid = viceproductid;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public String getSeccardroletype() {
		return seccardroletype;
	}

	public void setSeccardroletype(String seccardroletype) {
		this.seccardroletype = seccardroletype;
	}

	public String getProductmode() {
		return productmode;
	}

	public void setProductmode(String productmode) {
		this.productmode = productmode;
	}

	public String getChg_type() {
		return chg_type;
	}

	public void setChg_type(String chg_type) {
		this.chg_type = chg_type;
	}

	public String getCard_data() {
		return card_data;
	}

	public void setCard_data(String card_data) {
		this.card_data = card_data;
	}

	public String getSertype() {
		return sertype;
	}

	public void setSertype(String sertype) {
		this.sertype = sertype;
	}

	public String getDebutysn() {
		return debutysn;
	}

	public void setDebutysn(String debutysn) {
		this.debutysn = debutysn;
	}

	public String getSyn_mode() {
		return syn_mode;
	}

	public void setSyn_mode(String syn_mode) {
		this.syn_mode = syn_mode;
	}


    public String getMarket_user_id() {
        return market_user_id;
    }

    public void setMarket_user_id(String market_user_id) {
        this.market_user_id = market_user_id;
    }

    public String getSeed_user_id() {
        return seed_user_id;
    }

    public void setSeed_user_id(String seed_user_id) {
        this.seed_user_id = seed_user_id;
    }

    public String getShare_svc_num() {
        return share_svc_num;
    }

    public void setShare_svc_num(String share_svc_num) {
        this.share_svc_num = share_svc_num;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }
    
    public String getObject_esn() {
        return object_esn;
    }

    public void setObject_esn(String object_esn) {
        this.object_esn = object_esn;
    }

    public String getSale_mode() {
        return sale_mode;
    }

    public void setSale_mode(String sale_mode) {
        this.sale_mode = sale_mode;
    }

    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }

    public String getUser_to_account() {
        return user_to_account;
    }

    public void setUser_to_account(String user_to_account) {
        this.user_to_account = user_to_account;
    }
    
    public String getIs_order_master() {
        return is_order_master;
    }

    public void setIs_order_master(String is_order_master) {
        this.is_order_master = is_order_master;
    }

    public String getCheck_card_number() {
        return check_card_number;
    }

    public void setCheck_card_number(String check_card_number) {
        this.check_card_number = check_card_number;
    }

    public String getCheck_terminal_series() {
        return check_terminal_series;
    }

    public void setCheck_terminal_series(String check_terminal_series) {
        this.check_terminal_series = check_terminal_series;
    }
    public String getTop_share_userid() {
		return top_share_userid;
	}

	public void setTop_share_userid(String top_share_userid) {
		this.top_share_userid = top_share_userid;
	}

	public String getTop_share_num() {
		return top_share_num;
	}

	public void setTop_share_num(String top_share_num) {
		this.top_share_num = top_share_num;
	}
    public String getIs_new() {
        return is_new;
    }

    public void setIs_new(String is_new) {
        this.is_new = is_new;
    }

    public String getIs_blankcard() {
        return is_blankcard;
    }

    public void setIs_blankcard(String is_blankcard) {
        this.is_blankcard = is_blankcard;
    }
    
    public String getCbss_develop_code() {
		return cbss_develop_code;
	}

	public void setCbss_develop_code(String cbss_develop_code) {
		this.cbss_develop_code = cbss_develop_code;
	}

	public String getCbss_development_point_code() {
		return cbss_development_point_code;
	}

	public void setCbss_development_point_code(String cbss_development_point_code) {
		this.cbss_development_point_code = cbss_development_point_code;
	}

	@NotDbField
    public <T> T store() {
        ZteInstUpdateRequest<OrderItemExtvtlBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderItemExtvtlBusiRequest>();
        return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
    }

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<OrderItemExtvtlBusiRequest>> resp = new QueryResponse<List<OrderItemExtvtlBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery, resp, new ArrayList<OrderItemExtvtlBusiRequest>());
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return null;
	}

 

}

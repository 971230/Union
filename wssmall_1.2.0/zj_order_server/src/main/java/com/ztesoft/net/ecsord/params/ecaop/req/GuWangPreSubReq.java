package com.ztesoft.net.ecsord.params.ecaop.req;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderAdslBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam; 
import com.ztesoft.net.ecsord.params.ecaop.vo.BroadbandFeeVO;
import com.ztesoft.net.util.ZjCommonUtils;
 
public class GuWangPreSubReq extends ZteRequest{

	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "固网号码", type = "String", isNecessary = "Y", desc = "固网号码")
	private String service_num;
	@ZteSoftCommentAnnotationParam(name = "受理类型", type = "String", isNecessary = "Y", desc = "受理类型")
	private String oper_type="0";
	@ZteSoftCommentAnnotationParam(name = "业务类型", type = "String", isNecessary = "Y", desc = "业务类型")
	private String service_type;
	@ZteSoftCommentAnnotationParam(name = "宽带装机标准地址", type = "String", isNecessary = "Y", desc = "宽带装机标准地址")
	private String standard_address;
	@ZteSoftCommentAnnotationParam(name = "宽带用户地址", type = "String", isNecessary = "Y", desc = "宽带用户地址")
	private String user_address;
	/*@ZteSoftCommentAnnotationParam(name = "宽带速率", type = "String", isNecessary = "Y", desc = "宽带速率")
	private String speed_level;*/
	@ZteSoftCommentAnnotationParam(name = "用户种类", type = "String", isNecessary = "Y", desc = "用户种类")
	private String user_kind;
	@ZteSoftCommentAnnotationParam(name = "固网网格", type = "String", isNecessary = "N", desc = "固网网格")
	private String grid;
	@ZteSoftCommentAnnotationParam(name = "产品ID", type = "String", isNecessary = "Y", desc = "产品ID")
	private String product_id;
	@ZteSoftCommentAnnotationParam(name = "活动ID", type = "String", isNecessary = "Y", desc = "活动ID")
	private String scheme_id;
	@ZteSoftCommentAnnotationParam(name = "客户姓名", type = "String", isNecessary = "Y", desc = "客户姓名")
	private String customer_name;
	@ZteSoftCommentAnnotationParam(name = "证件类型", type = "String", isNecessary = "Y", desc = "证件类型")
	private String cert_type;
	@ZteSoftCommentAnnotationParam(name = "证件号码", type = "String", isNecessary = "Y", desc = "证件号码")
	private String cert_num;
	@ZteSoftCommentAnnotationParam(name = "证件地址", type = "String", isNecessary = "Y", desc = "证件地址")
	private String cert_addr;
	@ZteSoftCommentAnnotationParam(name = "民族", type = "String", isNecessary = "N", desc = "民族")
	private String cert_nation;
	@ZteSoftCommentAnnotationParam(name = "性别", type = "String", isNecessary = "N", desc = "性别 0男性，1女性")
	private String cert_sex;
	@ZteSoftCommentAnnotationParam(name = "签证机关", type = "String", isNecessary = "N", desc = "签证机关")
	private String cert_issuedat;
	@ZteSoftCommentAnnotationParam(name = "证件失效时间", type = "Date", isNecessary = "N", desc = "证件失效时间")
	private String cert_expire_date;
	@ZteSoftCommentAnnotationParam(name = "证件生效时间", type = "Date", isNecessary = "N", desc = "证件生效时间")
	private String cert_effected_date;
	@ZteSoftCommentAnnotationParam(name = "照片", type = "String", isNecessary = "N", desc = "照片")
	private String cert_photo;
	@ZteSoftCommentAnnotationParam(name = "联系人", type = "String", isNecessary = "Y", desc = "联系人")
	private String contact_name;
	@ZteSoftCommentAnnotationParam(name = "联系电话", type = "String", isNecessary = "Y", desc = "联系电话")
	private String contact_phone;
	@ZteSoftCommentAnnotationParam(name = "通信地址", type = "String", isNecessary = "Y", desc = "通信地址")
	private String customer_adder;
	@ZteSoftCommentAnnotationParam(name = "客户标识，Y实名-二代", type = "String", isNecessary = "Y", desc = "客户标识，Y实名-二代")
	private String cert_verified;
	@ZteSoftCommentAnnotationParam(name = "办理操作员", type = "String", isNecessary = "Y", desc = "办理操作员")
	private String deal_operator;
	@ZteSoftCommentAnnotationParam(name = "办理操作点", type = "String", isNecessary = "Y", desc = "办理操作点")
	private String deal_office_id;
	@ZteSoftCommentAnnotationParam(name = "费用清单", type = "List", isNecessary = "Y", desc = "费用清单")
	private String fee_list;
	@ZteSoftCommentAnnotationParam(name = "费用备注", type = "String", isNecessary = "N", desc = "费用备注")
	private String fee_remark;
	@ZteSoftCommentAnnotationParam(name = "局向编码", type = "String", isNecessary = "Y", desc = "局向编码")
	private String exch_code;
	@ZteSoftCommentAnnotationParam(name = "客户生日", type = "Date", isNecessary = "N", desc = "客户生日")
	private String cust_birthday;
	@ZteSoftCommentAnnotationParam(name = "发展点", type = "String", isNecessary = "N", desc = "发展点")
	private String param1;
	@ZteSoftCommentAnnotationParam(name = "备用字段", type = "String", isNecessary = "N", desc = "备用字段")
	private String param2;
	@ZteSoftCommentAnnotationParam(name = "备用字段", type = "String", isNecessary = "N", desc = "备用字段")
	private String param3;
	@ZteSoftCommentAnnotationParam(name = "备用字段", type = "String", isNecessary = "N", desc = "备用字段")
	private String param4;
	@ZteSoftCommentAnnotationParam(name = "拍照请求流水号（本次新增）", type = "String", isNecessary = "N", desc = "拍照请求流水号（本次新增）")
	private String req_swift_num;
	@ZteSoftCommentAnnotationParam(name = "是否收费,0否;1是", type = "String", isNecessary = "N", desc = "是否收费,0否;1是")
	private String is_pay;
	@ZteSoftCommentAnnotationParam(name = "光猫物品状态", type = "String", isNecessary = "N", desc = "光猫物品状态：1已领取；0未领取")
	private String object_status;
	
	/**
	 * Add by Wcl
	 * 新增参数
	 * sale_mode 销售模式：01：免费租用 06：用户自购 07：用户自备用户自备
	   devic_gear 新设备档位 00：标准版(光猫) 01：加强版(光猫)
       is_done_active 是否竣工生效 1：是 0：不是
	 */
	@ZteSoftCommentAnnotationParam(name = "销售模式", type = "String", isNecessary = "N", desc = "01：免费租用 06：用户自购 07：用户自备用户自备")
	private String sale_mode ;
	
	@ZteSoftCommentAnnotationParam(name = "新设备档位", type = "String", isNecessary = "N", desc = "00：标准版(光猫) 01：加强版(光猫)")
	private String devic_gear ;
	
	@ZteSoftCommentAnnotationParam(name = "是否竣工生效", type = "String", isNecessary = "N", desc = "是否竣工生效 1：是 0：不是")
	private String is_done_active ;
	
	@ZteSoftCommentAnnotationParam(name = "继承号码", type = "String", isNecessary = "N", desc = "继承号码")
	private String account_number;
	
	private OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
	@ZteSoftCommentAnnotationParam(name = "证件号码出入境号", type = "String", isNecessary = "N", desc = "证件号码")
    private String cert_num2;
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getService_num() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getAdsl_number();
	}

	public void setService_num(String service_num) {
		this.service_num = service_num;
	}

	public String getOper_type() {
		return oper_type;
	}

	public void setOper_type(String oper_type) {
		this.oper_type = oper_type;
	}

	public String getService_type() {
		String goods_id=CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getGoods_id();
		String service_type1 = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, goods_id, "service_type");
		service_type = service_type1;
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public String getStandard_address() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getAdsl_addr();
	}

	public void setStandard_address(String standard_address) {
		this.standard_address = standard_address;
	}

	public String getUser_address() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getUser_address();
	}

	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}

	/*public String getSpeed_level() {
		String goods_id=CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getGoods_id();
		String bss_brand_speed = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, goods_id, "bss_brand_speed");
		speed_level = bss_brand_speed;
		return speed_level;
	}

	public void setSpeed_level(String speed_level) {
		this.speed_level = speed_level;
	}*/

	public String getUser_kind() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getUser_kind();
	}

	public void setUser_kind(String user_kind) {
		this.user_kind = user_kind;
	}

	public String getGrid() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getAdsl_grid();
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public String getProduct_id() {
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
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NAME);
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCert_type() {
	    String cert_types =  CommonDataFactory.getInstance().getDcPublicDataByPkey("DIC_BSS_CERT_TYPE", CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERTI_TYPE));
        if(!StringUtil.isEmpty(cert_types)){
            cert_type = cert_types;
        }else {
            cert_type="11";
        }
        return cert_type;
	}

	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}

	public String getCert_num() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCert_card_num();
	}

	public void setCert_num(String cert_num) {
		this.cert_num = cert_num;
	}

	public String getCert_addr() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCert_address();
	}

	public void setCert_addr(String cert_addr) {
		this.cert_addr = cert_addr;
	}

	public String getCert_nation() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCert_card_nation();
	}
	
	public void setCert_nation(String cert_nation) {
		this.cert_nation = cert_nation;
	}

	public String getCert_sex() {
		String sex =CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCert_card_sex();
		if(!StringUtils.isEmpty(sex)&&StringUtil.equals(sex, "F")){
			cert_issuedat="1";
		}else if (!StringUtils.isEmpty(sex)&&StringUtil.equals(sex, "M")){
			cert_issuedat="0";
		}else{
			cert_issuedat="";
		}
		return cert_issuedat;
	}

	public void setCert_sex(String cert_sex) {
		this.cert_sex = cert_sex;
	}

	public String getCert_issuedat() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_ISSUER);
	}
	
	public void setCert_issuedat(String cert_issuedat) {
		this.cert_issuedat = cert_issuedat;
	}

	public String getCert_expire_date() {
		String expire_date = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCert_failure_time();
		if(!StringUtils.isEmpty(expire_date)){
			try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
    	    Date date;
			
				date = sdf.parse(expire_date);
			
    	    SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMMdd");
    	    String str=sdf2.format(date);
    	    cert_expire_date =  str;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				cert_expire_date="";
			} 
		}else{
			cert_expire_date="";
		}
		return cert_expire_date;
	}

	public void setCert_expire_date(String cert_expire_date) {
		this.cert_expire_date = cert_expire_date;
	}

	public String getCert_effected_date() {
		String effected_date = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_EFF_TIME);
		if(!StringUtils.isEmpty(effected_date)){
			try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
    	    Date date;
			
				date = sdf.parse(effected_date);
			
    	    SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMMdd");
    	    String str=sdf2.format(date);
    	    cert_effected_date =  str;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				cert_effected_date="";
			} 
		}else{
			cert_effected_date="";
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
		/**
		 * Add by wcl
		 * 修改原来取值问题
		 */
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderDeliveryBusiRequests().get(0).getShip_name();
//		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCert_address();
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public String getContact_phone() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderDeliveryBusiRequests().get(0).getShip_mobile();
	}

	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	public String getCustomer_adder() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderDeliveryBusiRequests().get(0).getShip_addr();
	}

	public void setCustomer_adder(String customer_adder) {
		this.customer_adder = customer_adder;
	}

	public String getCert_verified() {
		return "Y";
	}

	public void setCert_verified(String cert_verified) {
		this.cert_verified = cert_verified;
	}

	public String getDeal_operator() {
		String source_from = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getOrder_from();
		String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
		if(!StringUtil.isEmpty(OUT_OPERATOR)){
			deal_operator = OUT_OPERATOR;
		}else{
			deal_operator = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
		}
		return deal_operator;
	}

	public void setDeal_operator(String deal_operator) {
		this.deal_operator = deal_operator;
	}

	public String getDeal_office_id() {
		String source_from = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getOrder_from();
		String OUT_OFFICE = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OFFICE);
		if(!StringUtil.isEmpty(OUT_OFFICE)){
			deal_office_id = OUT_OFFICE;
		}else{
			deal_office_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
		}
		return deal_office_id;
	}

	public void setDeal_office_id(String deal_office_id) {
		this.deal_office_id = deal_office_id;
	}

	public String getFee_list() {
		List<BroadbandFeeVO> list = new ArrayList();
		String Fee_list  = "";
		List<AttrFeeInfoBusiRequest> feeInfoBusiRequests = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getFeeInfoBusiRequests();
		for(int i=0;i<feeInfoBusiRequests.size();i++){
			if(!StringUtils.isEmpty(feeInfoBusiRequests.get(i).getIs_aop_fee())
					&&StringUtil.equals("2", feeInfoBusiRequests.get(i).getIs_aop_fee())){
				
				Fee_list += feeInfoBusiRequests.get(i).getFee_item_code()+"&"+feeInfoBusiRequests.get(i).getO_fee_num()+"&"+feeInfoBusiRequests.get(i).getDiscount_fee()+"&"+feeInfoBusiRequests.get(i).getN_fee_num()+"&"+feeInfoBusiRequests.get(i).getFee_category()+";";
			}
		}
		if(!StringUtil.isEmpty(Fee_list)){
			Fee_list = Fee_list.substring(0, Fee_list.lastIndexOf(";"));
		}
		fee_list = Fee_list;
		return fee_list;
	}

	public void setFee_list(String fee_list) {
		this.fee_list = fee_list;
	}

	public String getFee_remark() {
		return fee_remark;
	}

	public void setFee_remark(String fee_remark) {
		this.fee_remark = fee_remark;
	}

	public String getExch_code() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getExch_code();
	}

	public void setExch_code(String exch_code) {
		this.exch_code = exch_code;
	}

	public String getCust_birthday() {
		return cust_birthday;
	}

	public void setCust_birthday(String cust_birthday) {
		this.cust_birthday = cust_birthday;
	}

	public String getParam1() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getDevelop_point_code();
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getDevelop_point_name();
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getModerm_id();
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	public String getParam4() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getAccess_type();
	}

	public void setParam4(String param4) {
		this.param4 = param4;
	}
	
	public String getReq_swift_num() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getReq_swift_num();
	}

	public void setReq_swift_num(String req_swift_num) {
		this.req_swift_num = req_swift_num;
	}

	

	public OrderTreeBusiRequest getOrderTree() {
		return orderTree;
	}

	public void setOrderTree(OrderTreeBusiRequest orderTree) {
		this.orderTree = orderTree;
	}


	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.zj.broadband.guWangPreSubReq";
	}

	public String getIs_pay() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "is_pay");
	}

	public void setIs_pay(String is_pay) {
		this.is_pay = is_pay;
	}

	public String getObject_status() {
		String newobject_status = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getObject_status();
		if(!StringUtil.isEmpty(newobject_status)){
			object_status = newobject_status;
		}else{
			object_status = "0";
		}
		return object_status;
	}

	public void setObject_status(String object_status) {
		this.object_status = object_status;
	}

	/**
	 * 收单透传
	 */
	public String getSale_mode() {
		List<OrderAdslBusiRequest> adslList = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest();
		if(adslList != null && adslList.size() > 0) {
			sale_mode = adslList.get(0).getSale_mode();
		}
		return sale_mode;
	}

	public void setSale_mode(String sale_mode) {
		this.sale_mode = sale_mode;
	}

	public String getDevic_gear() {
		List<OrderAdslBusiRequest> adslList = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest();
		if(adslList != null && adslList.size() > 0) {
			devic_gear = adslList.get(0).getDevic_gear();
		}
		return devic_gear;
	}

	public void setDevic_gear(String devic_gear) {
		this.devic_gear = devic_gear;
	}
	
	public String getIs_done_active() {
		List<OrderAdslBusiRequest> adslList = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest();
		if(adslList != null && adslList.size() > 0) {
			is_done_active = adslList.get(0).getIs_done_active();
		}
		return is_done_active;
	}

	public void setIs_done_active(String is_done_active) {
		this.is_done_active = is_done_active;
	}

	public String getAccount_number() {
		List<OrderAdslBusiRequest> adslList = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest();
		if(adslList != null && adslList.size() > 0) {
			account_number = adslList.get(0).getAccount_number() ;
		}
		return account_number;
	}

	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}

    public String getCert_num2() {
        return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCert_num2();

    }

    public void setCert_num2(String cert_num2) {
        this.cert_num2 = cert_num2;
    }
	
	
}

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

public class WotvUserSubReq extends ZteRequest{ 
	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "对端操作点", type = "String", isNecessary = "Y", desc = "对端操作点")
	private String office_id;
	@ZteSoftCommentAnnotationParam(name = "对端操作员", type = "String", isNecessary = "Y", desc = "对端操作员")
	private String operator_id;
	@ZteSoftCommentAnnotationParam(name = "业务号码", type = "String", isNecessary = "N", desc = "业务号码")
	private String service_num;
	@ZteSoftCommentAnnotationParam(name = "业务类型", type = "String", isNecessary = "N", desc = "业务类型")
	private String service_type;
	@ZteSoftCommentAnnotationParam(name = "沃TV号码", type = "String", isNecessary = "N", desc = "沃TV号码")
	private String wotv_num;
	@ZteSoftCommentAnnotationParam(name = "用户地址", type = "String", isNecessary = "N", desc = "用户地址")
	private String user_address;
	@ZteSoftCommentAnnotationParam(name = "证件类型", type = "String", isNecessary = "N", desc = "11:18位身份证")
	private String cert_type;
	@ZteSoftCommentAnnotationParam(name = "证件号码", type = "String", isNecessary = "N", desc = "证件号码")
	private String cert_num;
	@ZteSoftCommentAnnotationParam(name = "证件地址", type = "String", isNecessary = "N", desc = "证件地址")
	private String cert_address;
	@ZteSoftCommentAnnotationParam(name = "证件实名标识", type = "String", isNecessary = "N", desc = "Y实名-二代")
	private String cert_verified;
	@ZteSoftCommentAnnotationParam(name = "证件生效时间", type = "String", isNecessary = "N", desc = "格式yyyymmdd")
	private String cert_effected_date;
	@ZteSoftCommentAnnotationParam(name = "证件失效时间", type = "String", isNecessary = "N", desc = "格式yyyymmdd")
	private String cert_expire_date;
	@ZteSoftCommentAnnotationParam(name = "邮政编码", type = "String", isNecessary = "N", desc = "邮政编码")
	private String cust_zip;
	@ZteSoftCommentAnnotationParam(name = "客户名称", type = "String", isNecessary = "N", desc = "客户名称")
	private String cust_name;
	@ZteSoftCommentAnnotationParam(name = "客户生日", type = "String", isNecessary = "N", desc = "格式yyyymmdd")
	private String cust_birthday;
	@ZteSoftCommentAnnotationParam(name = "性别", type = "String", isNecessary = "N", desc = "0男性，1女性")
	private String cust_sex;
	@ZteSoftCommentAnnotationParam(name = "联系人", type = "String", isNecessary = "Y", desc = "联系人")
	private String contact_name;
	@ZteSoftCommentAnnotationParam(name = "联系电话", type = "String", isNecessary = "Y", desc = "联系电话")
	private String contact_phone;
	@ZteSoftCommentAnnotationParam(name = "费用清单", type = "String", isNecessary = "N", desc = "格式：科目1|应收费用|减免费用|实收费用|fee_rule_id;科目2|应收费用|减免费用|实收费用|fee_rule_id;……")
	private String fee_list;
	@ZteSoftCommentAnnotationParam(name = "局向编码", type = "String", isNecessary = "N", desc = "局向编码")
	private String exch_code;
	@ZteSoftCommentAnnotationParam(name = "产品ID", type = "String", isNecessary = "N", desc = "产品ID")
	private String product_id;
	@ZteSoftCommentAnnotationParam(name = "活动ID", type = "String", isNecessary = "N", desc = "活动ID")
	private String scheme_id;
	@ZteSoftCommentAnnotationParam(name = "标准地址ID", type = "String", isNecessary = "N", desc = "标准地址ID")
	private String standar_addr_code;
	@ZteSoftCommentAnnotationParam(name = "发展点编码", type = "String", isNecessary = "N", desc = "发展点编码")
	private String develop_id;
	@ZteSoftCommentAnnotationParam(name = "发展人编码", type = "String", isNecessary = "N", desc = "发展人编码")
	private String develop_man;
	@ZteSoftCommentAnnotationParam(name = "是否收费", type = "String", isNecessary = "N", desc = "0否;1是, 默认1")
	private String is_pay;
	@ZteSoftCommentAnnotationParam(name = "赠品ID", type = "String", isNecessary = "N", desc = "赠品ID")
	private String object_id;
	@ZteSoftCommentAnnotationParam(name = "赠品状态", type = "String", isNecessary = "N", desc = " 赠品状态,1:已领取;0未领取")
	private String object_status;
	
	/**
	 * Add by wcl
	 * 沃TV新装，新增参数
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
	
	public OrderTreeBusiRequest getOrderTree() {
		return orderTree;
	}
	public void setOrderTree(OrderTreeBusiRequest orderTree) {
		this.orderTree = orderTree;
	}
	
	public String getIs_pay() {
		is_pay = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "is_pay");
		if(StringUtils.isEmpty(is_pay)){
			is_pay = "1";
		}
		return is_pay;
	}
	public void setIs_pay(String is_pay) {
		this.is_pay = is_pay;
	}
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}
	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	public String getOffice_id() {
		String source_from = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getOrder_from();
		String OUT_OFFICE = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OFFICE);
		if(!StringUtil.isEmpty(OUT_OFFICE)){
			office_id = OUT_OFFICE;
		}else{
			office_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
		}
		return office_id;
	}
	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}
	public String getOperator_id() {
		String source_from = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getOrder_from();
		String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
		if(!StringUtil.isEmpty(OUT_OPERATOR)){
			operator_id = OUT_OPERATOR;
		}else{
			operator_id = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
		}
		return operator_id;
	}
	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}
	public String getService_num() {
		service_num = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getAdsl_number();
		return service_num;
	}
	public void setService_num(String service_num) {
		this.service_num = service_num;
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
	public String getWotv_num() {
		wotv_num = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getWotv_num();
		return wotv_num;
	}
	public void setWotv_num(String wotv_num) {
		this.wotv_num = wotv_num;
	}
	public String getUser_address() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getUser_address();
	}
	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}
	public String getCert_type() {
		return "11";
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
	public String getCert_address() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCert_address();
	}
	public void setCert_address(String cert_address) {
		this.cert_address = cert_address;
	}
	public String getCert_verified() {
		return "Y";
	}
	public void setCert_verified(String cert_verified) {
		this.cert_verified = cert_verified;
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
	public String getCust_zip() {
		return cust_zip;
	}
	public void setCust_zip(String cust_zip) {
		this.cust_zip = cust_zip;
	}
	public String getCust_name() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NAME);
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getCust_birthday() {
		String num = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCert_card_num();
		cust_birthday = num.substring(6, 14);
		return cust_birthday;
	}
	public void setCust_birthday(String cust_birthday) {
		this.cust_birthday = cust_birthday;
	}
	public String getCust_sex() {
		/*cust_sex =CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCert_card_sex();
		if(!StringUtils.isEmpty(cust_sex)&&StringUtil.equals(cust_sex, "F")){
			cust_sex="1";
		}else if (!StringUtils.isEmpty(cust_sex)&&StringUtil.equals(cust_sex, "M")){
			cust_sex="0";
		}else{
			cust_sex="";
		}*/
		String num = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCert_card_num();
		if(!StringUtil.isEmpty(num)){
			if(Integer.valueOf(num.substring(16, 17))%2==0){
				cust_sex="1";
			}else{
				cust_sex="0";
			}
		}
		return cust_sex;
	}
	public void setCust_sex(String cust_sex) {
		this.cust_sex = cust_sex;
	}
	public String getContact_name() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderDeliveryBusiRequests().get(0).getShip_name();
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
	public String getFee_list() {
		List<BroadbandFeeVO> list = new ArrayList();
		String Fee_list  = "";
		List<AttrFeeInfoBusiRequest> feeInfoBusiRequests = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getFeeInfoBusiRequests();
		for(int i=0;i<feeInfoBusiRequests.size();i++){
			if(!StringUtils.isEmpty(feeInfoBusiRequests.get(i).getIs_aop_fee())
					&&StringUtil.equals("2", feeInfoBusiRequests.get(i).getIs_aop_fee())){
				
				Fee_list += feeInfoBusiRequests.get(i).getFee_item_code()+"|"+feeInfoBusiRequests.get(i).getO_fee_num()+"|"+feeInfoBusiRequests.get(i).getDiscount_fee()+"|"+feeInfoBusiRequests.get(i).getN_fee_num()+"|"+feeInfoBusiRequests.get(i).getFee_category()+";";
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
	public String getExch_code() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getExch_code();
	}
	public void setExch_code(String exch_code) {
		this.exch_code = exch_code;
	}
	public String getScheme_id() {
		return scheme_id;
	}
	public void setScheme_id(String scheme_id) {
		this.scheme_id = scheme_id;
	}
	public String getStandar_addr_code() {
		standar_addr_code = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getAdsl_addr();
		return standar_addr_code;
	}
	public void setStandar_addr_code(String standar_addr_code) {
		this.standar_addr_code = standar_addr_code;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getDevelop_id() {
		return CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getDevelop_point_code();
	}
	public void setDevelop_id(String develop_id) {
		this.develop_id = develop_id;
	}
	public String getDevelop_man() {
		develop_man = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getDevelop_point_name();
		return develop_man;
	}
	public void setDevelop_man(String develop_man) {
		this.develop_man = develop_man;
	}
	
	public String getObject_id() {
		object_id = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderAdslBusiRequest().get(0).getModerm_id();
		return object_id;
	}
	public void setObject_id(String object_id) {
		this.object_id = object_id;
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
	
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.zj.broadband.wotvUserSubReq";
	}

	
}

package com.ztesoft.net.ecsord.params.ecaop.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
 
public class QueryGoodsListReq  extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name="序列号",type="String",isNecessary="Y",desc="serial_no:序列号")
	private String serial_no;

	@ZteSoftCommentAnnotationParam(name="时间",type="String",isNecessary="Y",desc="时间")
	private String time;
	
	@ZteSoftCommentAnnotationParam(name="发起方系统标识",type="String",isNecessary="Y",desc="发起方系统标识")
	private String source_system;
	
	@ZteSoftCommentAnnotationParam(name="业务号码",type="String",isNecessary="N",desc="业务号码")
	private String phone_num ;
	
	@ZteSoftCommentAnnotationParam(name="业务号码归属系统",type="String",isNecessary="N",desc="业务号码归属系统")
	private String number_attribution ;

	@ZteSoftCommentAnnotationParam(name="地市",type="String",isNecessary="N",desc="地市")
	private String city_code ;
	
	@ZteSoftCommentAnnotationParam(name="县分",type="String",isNecessary="N",desc="县分")
	private String county_id ;
	
	@ZteSoftCommentAnnotationParam(name="小区编码",type="String",isNecessary="N",desc="小区编码")
	private String community_code ;
	
	@ZteSoftCommentAnnotationParam(name="工号",type="String",isNecessary="N",desc="工号")
	private String staff_id ;
	
	@ZteSoftCommentAnnotationParam(name="客户编号",type="String",isNecessary="N",desc="客户编号")
	private String cust_id ;
	
	@ZteSoftCommentAnnotationParam(name="发展渠道",type="String",isNecessary="N",desc="发展渠道")
	private String develop_id;
	
	@ZteSoftCommentAnnotationParam(name="受理渠道",type="String",isNecessary="N",desc="受理渠道")
	private String office_id;
	
	@ZteSoftCommentAnnotationParam(name="商品编码",type="String",isNecessary="N",desc="商品编码")
	private String goods_id;
	
	@ZteSoftCommentAnnotationParam(name="商品类型",type="String",isNecessary="N",desc="商品类型")
	private String type_id ;
	
	@ZteSoftCommentAnnotationParam(name="商品分类",type="String",isNecessary="N",desc="商品分类")
	private String cat_id ;
	
	
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSource_system() {
		return source_system;
	}
	public void setSource_system(String source_system) {
		this.source_system = source_system;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public String getNumber_attribution() {
		return number_attribution;
	}
	public void setNumber_attribution(String number_attribution) {
		this.number_attribution = number_attribution;
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	public String getCounty_id() {
		return county_id;
	}
	public void setCounty_id(String county_id) {
		this.county_id = county_id;
	}
	public String getCommunity_code() {
		return community_code;
	}
	public void setCommunity_code(String community_code) {
		this.community_code = community_code;
	}
	public String getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getDevelop_id() {
		return develop_id;
	}
	public void setDevelop_id(String develop_id) {
		this.develop_id = develop_id;
	}
	public String getOffice_id() {
		return office_id;
	}
	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getCat_id() {
		return cat_id;
	}
	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
	 
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZJInfServices.queryGoodsListByRequ";
	}

}

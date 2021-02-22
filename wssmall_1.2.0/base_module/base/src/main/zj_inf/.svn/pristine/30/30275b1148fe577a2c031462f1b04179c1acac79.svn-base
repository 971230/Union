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

/**
 * @author 
 * @version 2017-07-25
 * @see 温大子产品
 * 
 */
@RequestBeanAnnontion(primary_keys = "order_id", table_name = "ES_ORDER_SUB_OTHER_INFO", service_desc = "集客业务专属信息节点表")
public class OrderSubOtherInfoBusiRequest extends ZteBusiRequest<ZteResponse>implements IZteBusiRequestHander{

	@RequestFieldAnnontion()
	private String sub_other_info_inst_id;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String user_group;
	@RequestFieldAnnontion()
	private String lan_school;
	@RequestFieldAnnontion()
	private String complex_grid_id;
	@RequestFieldAnnontion()
	private String cust_building_id;
	@RequestFieldAnnontion()
	private String grp_grid_id;
	@RequestFieldAnnontion()
	private String grpuser_ope_model;
	@RequestFieldAnnontion()
	private String source_from;
	@RequestFieldAnnontion(dname = "group_code", desc = "集团客户编号", need_query = "yes")
	private String group_code;
	@RequestFieldAnnontion(dname = "cust_id", desc = "集团客户编号", need_query = "yes")
	private String cust_id;
	@RequestFieldAnnontion(dname = "certify_flag", desc = "责任人/使用人标识", need_query = "yes")
	private String certify_flag;
	@RequestFieldAnnontion(dname = "certify_cert_type", desc = "责任人/使用人证件类型", need_query = "yes")
	private String certify_cert_type;
	@RequestFieldAnnontion(dname = "certify_cert_num", desc = "责任人/使用人证件号码", need_query = "yes")
	private String certify_cert_num;
	@RequestFieldAnnontion(dname = "certify_cust_name", desc = "责任人/使用人客户姓名", need_query = "yes")
	private String certify_cust_name;
	@RequestFieldAnnontion(dname = "certify_cert_addr", desc = "责任人/使用人客户证件地址", need_query = "yes")
	private String certify_cert_addr;
	

	public String getSub_other_info_inst_id() {
		return sub_other_info_inst_id;
	}

	public void setSub_other_info_inst_id(String sub_other_info_inst_id) {
		this.sub_other_info_inst_id = sub_other_info_inst_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getUser_group() {
		return user_group;
	}

	public void setUser_group(String user_group) {
		this.user_group = user_group;
	}

	public String getLan_school() {
		return lan_school;
	}

	public void setLan_school(String lan_school) {
		this.lan_school = lan_school;
	}

	public String getComplex_grid_id() {
		return complex_grid_id;
	}

	public void setComplex_grid_id(String complex_grid_id) {
		this.complex_grid_id = complex_grid_id;
	}

	public String getCust_building_id() {
		return cust_building_id;
	}

	public void setCust_building_id(String cust_building_id) {
		this.cust_building_id = cust_building_id;
	}

	public String getGrp_grid_id() {
		return grp_grid_id;
	}

	public void setGrp_grid_id(String grp_grid_id) {
		this.grp_grid_id = grp_grid_id;
	}

	public String getGrpuser_ope_model() {
		return grpuser_ope_model;
	}

	public void setGrpuser_ope_model(String grpuser_ope_model) {
		this.grpuser_ope_model = grpuser_ope_model;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	
	public String getGroup_code() {
		return group_code;
	}

	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}

	public String getCust_id() {
		return cust_id;
	}

	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	public String getCertify_flag() {
		return certify_flag;
	}

	public void setCertify_flag(String certify_flag) {
		this.certify_flag = certify_flag;
	}

	public String getCertify_cert_type() {
		return certify_cert_type;
	}

	public void setCertify_cert_type(String certify_cert_type) {
		this.certify_cert_type = certify_cert_type;
	}

	public String getCertify_cert_num() {
		return certify_cert_num;
	}

	public void setCertify_cert_num(String certify_cert_num) {
		this.certify_cert_num = certify_cert_num;
	}

	public String getCertify_cust_name() {
		return certify_cust_name;
	}

	public void setCertify_cust_name(String certify_cust_name) {
		this.certify_cust_name = certify_cust_name;
	}
	
	
	public String getCertify_cert_addr() {
		return certify_cert_addr;
	}

	public void setCertify_cert_addr(String certify_cert_addr) {
		this.certify_cert_addr = certify_cert_addr;
	}

	@Override
	@NotDbField
	public <T> T store() {
		ZteInstUpdateRequest<OrderSubProductBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderSubProductBusiRequest>();
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<OrderSubOtherInfoBusiRequest> resp = new QueryResponse<OrderSubOtherInfoBusiRequest>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery, resp, this);
	}
	/**
	 * 只插入数据库，不更新缓存，在标准化入库时执行
	 */
	@NotDbField
	public ZteResponse  insertNoCache(){
		return RequestStoreExector.getInstance().insertZteRequestInst(this);
	}
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}
}

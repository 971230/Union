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

@RequestBeanAnnontion(primary_keys = "adsl_inst_id", table_name = "es_order_zhwq_adsl", depency_keys = "order_id", service_desc = "宽带信息表")
public class OrderAdslBusiRequest extends ZteBusiRequest implements IZteBusiRequestHander {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4847544360443417683L;
	@RequestFieldAnnontion()
	private String adsl_inst_id;
	@RequestFieldAnnontion()
	private String inst_id;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String product_code; // 产品编码
	@RequestFieldAnnontion()
	private String adsl_account;// 宽带账号
	@RequestFieldAnnontion()
	private String adsl_number;// 宽带号码
	@RequestFieldAnnontion()
	private String adsl_addr;// 标准地址
	@RequestFieldAnnontion()
	private String adsl_addr_desc;// 标准地址  
	@RequestFieldAnnontion()
	private String adsl_speed;// 速率
	@RequestFieldAnnontion()
	private String adsl_grid;// 固网网格
	@RequestFieldAnnontion()
	private String user_kind;// 用户种类
	@RequestFieldAnnontion()
	private String service_type;// 业务类型
	@RequestFieldAnnontion()
	private String exch_code;// 局向编码
	@RequestFieldAnnontion()
	private String area_exch_id;// 区局
	@RequestFieldAnnontion()
	private String point_exch_id;// 端局
	@RequestFieldAnnontion()
	private String module_exch_id;// 模块局
	@RequestFieldAnnontion()
	private String cust_building_id;// 备注
	@RequestFieldAnnontion()
	private String total_fee;// 宽带总费用
	@RequestFieldAnnontion()
	private String user_address;// 用户地址
	@RequestFieldAnnontion()
	private String appt_date;// 预约装机时间
	@RequestFieldAnnontion()
	private String county_id;// bss县份
	@RequestFieldAnnontion()
	private String develop_point_code;// 发展点编码
	@RequestFieldAnnontion()
	private String develop_point_name;// 发展点名称
	@RequestFieldAnnontion()
	private String develop_code;// 发展人编码
	@RequestFieldAnnontion()
	private String develop_name;// 发展人名称
	@RequestFieldAnnontion()
	private String moderm_id;// 光猫ID
	@RequestFieldAnnontion()
	private String moderm_name;// 光猫ID名称
	@RequestFieldAnnontion()
	private String req_swift_num;// 拍照流水
	@RequestFieldAnnontion()
	private String access_type;// 接入方式
	@RequestFieldAnnontion()
	private String object_status;// 光猫物品状态
	@RequestFieldAnnontion()
	private String business_county;// 营业县分
	@RequestFieldAnnontion()
	private String product_type;// 产品分类 
	@RequestFieldAnnontion()
	private String wotv_num;// 沃tv号码
	@RequestFieldAnnontion()
	private String old_service_type;// 老业务类型
	@RequestFieldAnnontion()
	private String is_judge_address;// 是否预判
	
	@RequestFieldAnnontion()
	private String sale_mode;// 销售模式
	@RequestFieldAnnontion()
	private String devic_gear;// 新设备档位
	@RequestFieldAnnontion()
	private String is_done_active;// 是否竣工生效
	@RequestFieldAnnontion()
	private String account_number;// 继承号码
	@RequestFieldAnnontion()
	private String adsl_password;//密码

	@RequestFieldAnnontion()
	private String old_obj_esn ;
	@RequestFieldAnnontion()
	private String old_obj_owner;
	
	@Override
	public <T> T store() {
		ZteInstUpdateRequest<OrderAdslBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderAdslBusiRequest>();
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<OrderAdslBusiRequest>> resp = new QueryResponse<List<OrderAdslBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery, resp, new ArrayList<OrderFileBusiRequest>());
	}

	/**
	 * 只插入数据库，不更新缓存，在标准化入库时执行
	 */
	@NotDbField
	public ZteResponse  insertNoCache(){
		return RequestStoreExector.getInstance().insertZteRequestInst(this);
	}
	
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getInst_id() {
		return inst_id;
	}

	public void setInst_id(String inst_id) {
		this.inst_id = inst_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getAdsl_account() {
		return adsl_account;
	}

	public void setAdsl_account(String adsl_account) {
		this.adsl_account = adsl_account;
	}

	public String getAdsl_number() {
		return adsl_number;
	}

	public void setAdsl_number(String adsl_number) {
		this.adsl_number = adsl_number;
	}

	public String getAdsl_addr() {
		return adsl_addr;
	}

	public void setAdsl_addr(String adsl_addr) {
		this.adsl_addr = adsl_addr;
	}

	public String getAdsl_speed() {
		return adsl_speed;
	}

	public void setAdsl_speed(String adsl_speed) {
		this.adsl_speed = adsl_speed;
	}

	public String getAdsl_grid() {
		return adsl_grid;
	}

	public void setAdsl_grid(String adsl_grid) {
		this.adsl_grid = adsl_grid;
	}

	public String getUser_kind() {
		return user_kind;
	}

	public void setUser_kind(String user_kind) {
		this.user_kind = user_kind;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public String getExch_code() {
		return exch_code;
	}

	public void setExch_code(String exch_code) {
		this.exch_code = exch_code;
	}
	
	public String getArea_exch_id() {
		return area_exch_id;
	}

	public void setArea_exch_id(String area_exch_id) {
		this.area_exch_id = area_exch_id;
	}

	public String getPoint_exch_id() {
		return point_exch_id;
	}

	public void setPoint_exch_id(String point_exch_id) {
		this.point_exch_id = point_exch_id;
	}

	public String getModule_exch_id() {
		return module_exch_id;
	}

	public void setModule_exch_id(String module_exch_id) {
		this.module_exch_id = module_exch_id;
	}

	public String getCust_building_id() {
		return cust_building_id;
	}

	public void setCust_building_id(String cust_building_id) {
		this.cust_building_id = cust_building_id;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getUser_address() {
		return user_address;
	}

	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}

	public String getAppt_date() {
		return appt_date;
	}

	public void setAppt_date(String appt_date) {
		this.appt_date = appt_date;
	}

	public String getCounty_id() {
		return county_id;
	}

	public void setCounty_id(String county_id) {
		this.county_id = county_id;
	}

	public String getDevelop_point_code() {
		return develop_point_code;
	}

	public void setDevelop_point_code(String develop_point_code) {
		this.develop_point_code = develop_point_code;
	}

	public String getDevelop_point_name() {
		return develop_point_name;
	}

	public void setDevelop_point_name(String develop_point_name) {
		this.develop_point_name = develop_point_name;
	}

	public String getModerm_id() {
		return moderm_id;
	}

	public void setModerm_id(String moderm_id) {
		this.moderm_id = moderm_id;
	}

	public String getReq_swift_num() {
		return req_swift_num;
	}

	public void setReq_swift_num(String req_swift_num) {
		this.req_swift_num = req_swift_num;
	}

	public String getAccess_type() {
		return access_type;
	}

	public void setAccess_type(String access_type) {
		this.access_type = access_type;
	}

	public String getObject_status() {
		return object_status;
	}

	public void setObject_status(String object_status) {
		this.object_status = object_status;
	}

	public String getBusiness_county() {
		return business_county;
	}

	public void setBusiness_county(String business_county) {
		this.business_county = business_county;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public String getWotv_num() {
		return wotv_num;
	}

	public void setWotv_num(String wotv_num) {
		this.wotv_num = wotv_num;
	}

	public String getOld_service_type() {
		return old_service_type;
	}

	public void setOld_service_type(String old_service_type) {
		this.old_service_type = old_service_type;
	}

	public String getAdsl_inst_id() {
		return adsl_inst_id;
	}

	public void setAdsl_inst_id(String adsl_inst_id) {
		this.adsl_inst_id = adsl_inst_id;
	}

	public String getDevelop_code() {
		return develop_code;
	}

	public void setDevelop_code(String develop_code) {
		this.develop_code = develop_code;
	}

	public String getDevelop_name() {
		return develop_name;
	}

	public void setDevelop_name(String develop_name) {
		this.develop_name = develop_name;
	}

	public String getIs_judge_address() {
		return is_judge_address;
	}

	public void setIs_judge_address(String is_judge_address) {
		this.is_judge_address = is_judge_address;
	}

	public String getAdsl_addr_desc() {
		return adsl_addr_desc;
	}

	public void setAdsl_addr_desc(String adsl_addr_desc) {
		this.adsl_addr_desc = adsl_addr_desc;
	}

	public String getModerm_name() {
		return moderm_name;
	}

	public void setModerm_name(String moderm_name) {
		this.moderm_name = moderm_name;
	}

	public String getAdsl_password() {
		return adsl_password;
	}

	public void setAdsl_password(String adsl_password) {
		this.adsl_password = adsl_password;
	}

	public String getSale_mode() {
		return sale_mode;
	}
 
	public void setSale_mode(String sale_mode) {
		this.sale_mode = sale_mode;
	}

	public String getDevic_gear() {
		return devic_gear;
	}

	public void setDevic_gear(String devic_gear) {
		this.devic_gear = devic_gear;
	}

	public String getIs_done_active() {
		return is_done_active;
	}

	public void setIs_done_active(String is_done_active) {
		this.is_done_active = is_done_active;
	}

	public String getAccount_number() {
		return account_number;
	}

	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}

	public String getOld_obj_esn() {
		return old_obj_esn;
	}

	public void setOld_obj_esn(String old_obj_esn) {
		this.old_obj_esn = old_obj_esn;
	}

	public String getOld_obj_owner() {
		return old_obj_owner;
	}

	public void setOld_obj_owner(String old_obj_owner) {
		this.old_obj_owner = old_obj_owner;
	}
}

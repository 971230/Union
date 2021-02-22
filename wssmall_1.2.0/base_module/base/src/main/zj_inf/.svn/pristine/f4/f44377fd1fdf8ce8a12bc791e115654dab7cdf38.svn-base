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
 * 没有加入订单树属性中，仅作实体类使用
 * 
 * @author song.qi
 *
 */
@RequestBeanAnnontion(primary_keys = "work_order_id", table_name = "es_work_order", depency_keys = "order_id", service_desc = "工单信息表")
public class OrderWorksBusiRequest extends ZteBusiRequest implements IZteBusiRequestHander {

	private static final long serialVersionUID = -4847544360443417683L;
	@RequestFieldAnnontion()
	private String work_order_id;
	
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String type;// 工单类型
	@RequestFieldAnnontion()
	private String status;// 工单处理状态 0 — 未处理 1 — 处理成功2-处理失败
	@RequestFieldAnnontion()
	private String operator_id;// 操作员工号
	@RequestFieldAnnontion()
	private String operator_office_id;// 操作点id
	@RequestFieldAnnontion()
	private String operator_num;// 操作员联系电话
	@RequestFieldAnnontion()
	private String operator_name;// 操作员姓名
	@RequestFieldAnnontion()
	private String order_contact_name;// 联系人姓名
	@RequestFieldAnnontion()
	private String order_contact_phone;// 联系人电话
	@RequestFieldAnnontion()
	private String order_contact_addr;// 联系人地址/宽带标准地址
	@RequestFieldAnnontion()
	private String order_product_name;// 订单商品名称
	@RequestFieldAnnontion()
	private String order_amount;// 订单金额
	@RequestFieldAnnontion()
	private String update_time;// 更新时间
	@RequestFieldAnnontion()
	private String remark;// 备注
	@RequestFieldAnnontion()
	private String col1;
	@RequestFieldAnnontion()
	private String col2;
	@RequestFieldAnnontion()
	private String col3;

	@RequestFieldAnnontion()
	private String order_send_usercode;
	@RequestFieldAnnontion()
	private String order_send_username;
	@RequestFieldAnnontion()
	private String order_send_userphone;
	@RequestFieldAnnontion()
	private String order_priority;
	@RequestFieldAnnontion()
	private String order_time_limit;
	@RequestFieldAnnontion()
	private String order_product_id;
	@RequestFieldAnnontion()
	private String customer_name;
	@RequestFieldAnnontion()
	private String cert_type;
	@RequestFieldAnnontion()
	private String cert_num;
	@RequestFieldAnnontion()
	private String staff_id;
	@RequestFieldAnnontion()
	private String trade_type_code;

	@RequestFieldAnnontion()
	private String order_time;// 订单创建时间
	@RequestFieldAnnontion()
	private String node_ins_id;
	
	@Override
	public <T> T store() {
		ZteInstUpdateRequest<OrderWorksBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderWorksBusiRequest>();
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<OrderWorksBusiRequest>> resp = new QueryResponse<List<OrderWorksBusiRequest>>();
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
	}

	@Override
	public String getApiMethodName() {
		return null;
	}

	public String getWork_order_id() {
		return work_order_id;
	}

	public void setWork_order_id(String work_order_id) {
		this.work_order_id = work_order_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
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

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getOperator_office_id() {
		return operator_office_id;
	}

	public void setOperator_office_id(String operator_office_id) {
		this.operator_office_id = operator_office_id;
	}

	public String getOperator_num() {
		return operator_num;
	}

	public void setOperator_num(String operator_num) {
		this.operator_num = operator_num;
	}

	public String getOperator_name() {
		return operator_name;
	}

	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}

	public String getOrder_contact_name() {
		return order_contact_name;
	}

	public void setOrder_contact_name(String order_contact_name) {
		this.order_contact_name = order_contact_name;
	}

	public String getOrder_contact_phone() {
		return order_contact_phone;
	}

	public void setOrder_contact_phone(String order_contact_phone) {
		this.order_contact_phone = order_contact_phone;
	}

	public String getOrder_contact_addr() {
		return order_contact_addr;
	}

	public void setOrder_contact_addr(String order_contact_addr) {
		this.order_contact_addr = order_contact_addr;
	}

	public String getOrder_product_name() {
		return order_product_name;
	}

	public void setOrder_product_name(String order_product_name) {
		this.order_product_name = order_product_name;
	}

	public String getOrder_amount() {
		return order_amount;
	}

	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getOrder_send_usercode() {
		return order_send_usercode;
	}

	public void setOrder_send_usercode(String order_send_usercode) {
		this.order_send_usercode = order_send_usercode;
	}

	public String getOrder_send_username() {
		return order_send_username;
	}

	public void setOrder_send_username(String order_send_username) {
		this.order_send_username = order_send_username;
	}

	public String getOrder_send_userphone() {
		return order_send_userphone;
	}

	public void setOrder_send_userphone(String order_send_userphone) {
		this.order_send_userphone = order_send_userphone;
	}

	public String getOrder_priority() {
		return order_priority;
	}

	public void setOrder_priority(String order_priority) {
		this.order_priority = order_priority;
	}

	public String getOrder_time_limit() {
		return order_time_limit;
	}

	public void setOrder_time_limit(String order_time_limit) {
		this.order_time_limit = order_time_limit;
	}

	public String getOrder_product_id() {
		return order_product_id;
	}

	public void setOrder_product_id(String order_product_id) {
		this.order_product_id = order_product_id;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCert_type() {
		return cert_type;
	}

	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}

	public String getCert_num() {
		return cert_num;
	}

	public void setCert_num(String cert_num) {
		this.cert_num = cert_num;
	}

	public String getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}

	public String getTrade_type_code() {
		return trade_type_code;
	}

	public void setTrade_type_code(String trade_type_code) {
		this.trade_type_code = trade_type_code;
	}

	public String getOrder_time() {
		return order_time;
	}

	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}

    public String getNode_ins_id() {
        return node_ins_id;
    }

    public void setNode_ins_id(String node_ins_id) {
        this.node_ins_id = node_ins_id;
    }
	
}

package zte.net.ecsord.params.busi.req;

import java.util.ArrayList;
import java.util.List;

import params.ZteBusiRequest;
import params.ZteResponse;
import rule.IZteBusiRequestHander;
import zte.net.common.annontion.context.request.RequestBeanAnnontion;
import zte.net.common.annontion.context.request.RequestFieldAnnontion;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.common.params.req.ZteInstUpdateRequest;
import zte.net.common.params.resp.QueryResponse;
import zte.net.treeInst.RequestStoreExector;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 
 * @author wu.i 
 * 订单数对象
 * 
 */
@RequestBeanAnnontion(primary_keys="payment_id",table_name="es_payment_logs",service_desc="订单支付信息")
public class OrderPayBusiRequest extends ZteBusiRequest<ZteResponse> implements IZteBusiRequestHander{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String payment_id;
	@RequestFieldAnnontion()
	private String member_id;
	@RequestFieldAnnontion()
	private String account;
	
	@RequestFieldAnnontion()
	private String pay_user;
	@RequestFieldAnnontion()
	private Double money;
	@RequestFieldAnnontion()
	private Double pay_cost;
	@RequestFieldAnnontion()
	private String pay_type;
	@RequestFieldAnnontion()
	private String pay_method;
	
	@RequestFieldAnnontion()
	private String remark;
	@RequestFieldAnnontion()
	private String op_id;
	@RequestFieldAnnontion()
	private Integer type;
	@RequestFieldAnnontion()
	private Integer status;
	@RequestFieldAnnontion()
	private String create_time;
	

	@RequestFieldAnnontion()
	private String bank_id;
	@RequestFieldAnnontion()
	private String userid;
	@RequestFieldAnnontion()
	private String status_time;
	@RequestFieldAnnontion()
	private String transaction_id;
	@RequestFieldAnnontion()
	private Integer paytype = 0;
	
	@RequestFieldAnnontion()
	private String pay_time;
	@RequestFieldAnnontion()
	private String returned_account;
	@RequestFieldAnnontion()
	private Integer returned_type;
	@RequestFieldAnnontion()
	private Integer returned_kind;
	
	
	
	//支付、退款会员名
	private String member_name;
	private String user_name;
	private String payMethod;
	private String o_order_id;
	private String sn;
	private String bank;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}


	@Override
	@NotDbField
	public <T>T store() {
		ZteInstUpdateRequest<OrderPayBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderPayBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		//构造参数
		QueryResponse<List<OrderPayBusiRequest>> resp = new QueryResponse<List<OrderPayBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery,resp,new ArrayList<OrderPayBusiRequest>());
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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	@NotDbField
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getPay_user() {
		return pay_user;
	}

	public void setPay_user(String pay_user) {
		this.pay_user = pay_user;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getPay_cost() {
		return pay_cost;
	}

	public void setPay_cost(Double pay_cost) {
		this.pay_cost = pay_cost;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getPay_method() {
		return pay_method;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOp_id() {
		return op_id;
	}

	public void setOp_id(String op_id) {
		this.op_id = op_id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getBank_id() {
		return bank_id;
	}

	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getStatus_time() {
		return status_time;
	}

	public void setStatus_time(String status_time) {
		this.status_time = status_time;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public Integer getPaytype() {
		return paytype;
	}

	public void setPaytype(Integer paytype) {
		this.paytype = paytype;
	}

	public String getPay_time() {
		return pay_time;
	}

	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}

	public String getReturned_account() {
		return returned_account;
	}

	public void setReturned_account(String returned_account) {
		this.returned_account = returned_account;
	}

	public Integer getReturned_type() {
		return returned_type;
	}

	public void setReturned_type(Integer returned_type) {
		this.returned_type = returned_type;
	}

	public Integer getReturned_kind() {
		return returned_kind;
	}

	public void setReturned_kind(Integer returned_kind) {
		this.returned_kind = returned_kind;
	}
	
	
	
	@NotDbField
	public String getMember_name() {
		return member_name;
	}
	@NotDbField
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	@NotDbField
	public String getUser_name() {
		return user_name;
	}
	@NotDbField
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	@NotDbField
	public String getPayMethod() {
		return payMethod;
	}
	@NotDbField
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	@NotDbField
	public String getO_order_id() {
		return o_order_id;
	}
	@NotDbField
	public void setO_order_id(String o_order_id) {
		this.o_order_id = o_order_id;
	}
	@NotDbField
	public String getSn() {
		return sn;
	}
	@NotDbField
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
     * 只插入数据库，不更新缓存，在标准化入库时执行
     */
    @NotDbField
    public ZteResponse  insertNoCache(){
        return RequestStoreExector.getInstance().insertZteRequestInst(this);
    }
}

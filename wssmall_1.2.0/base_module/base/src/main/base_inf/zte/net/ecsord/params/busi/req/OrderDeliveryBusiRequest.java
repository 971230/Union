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
@RequestBeanAnnontion(primary_keys="delivery_id",table_name="es_delivery",service_desc="订单配送物流信息")
public class OrderDeliveryBusiRequest extends ZteBusiRequest<ZteResponse> implements IZteBusiRequestHander{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String delivery_id;

	@RequestFieldAnnontion()
	private Integer type;
	@RequestFieldAnnontion()
	private String member_id;
	
	//会员用户
	@RequestFieldAnnontion()
	private Double money;
	@RequestFieldAnnontion()
	private String ship_type;
	@RequestFieldAnnontion()
	private Integer is_protect;
	@RequestFieldAnnontion()
	private Double protect_price;
	@RequestFieldAnnontion()
	private String logi_id;
	@RequestFieldAnnontion()
	private String logi_name;
	@RequestFieldAnnontion()
	private String logi_no;
	@RequestFieldAnnontion()
	private String ship_name;
	@RequestFieldAnnontion()
	private Long province_id;
	@RequestFieldAnnontion()
	private Long city_id;
	@RequestFieldAnnontion()
	private Long region_id;
	@RequestFieldAnnontion()
	private String province;
	@RequestFieldAnnontion()
	private String city;
	@RequestFieldAnnontion()
	private String userid;
	@RequestFieldAnnontion()
	private String region;
	@RequestFieldAnnontion()
	private String ship_addr;
	@RequestFieldAnnontion()
	private String ship_zip;
	@RequestFieldAnnontion()
	private String ship_tel;
	@RequestFieldAnnontion()
	private String ship_mobile;
	@RequestFieldAnnontion()
	private String ship_email;
	@RequestFieldAnnontion()
	private String op_name;
	@RequestFieldAnnontion()
	private String remark;
	@RequestFieldAnnontion()
	private String create_time;
	@RequestFieldAnnontion()
	private String reason;
	@RequestFieldAnnontion()
	private Integer ship_num;
	@RequestFieldAnnontion()
	private Integer print_status;
	@RequestFieldAnnontion()
	private Long weight;
	@RequestFieldAnnontion()
	private Integer ship_status;
	@RequestFieldAnnontion()
	private String batch_id;
	
	@RequestFieldAnnontion()
	private String house_id;
	
	
	
	private Double shipping_amount;
	private String audit_status;
	private Double goods_amount;
	private String member_name;
	private String create_type;
	private String sn;
	private Double order_amount;
	private String pru_order_name;
	
	@RequestFieldAnnontion()
	private String logi_receiver;
	@RequestFieldAnnontion()
	private String logi_receiver_phone;
	@RequestFieldAnnontion()
	private String post_fee;
	@RequestFieldAnnontion()
	private String out_house_id;
	@RequestFieldAnnontion()
	private String shipping_time;
	@RequestFieldAnnontion()
	private String n_shipping_amount;
	@RequestFieldAnnontion()
	private String shipping_company;
	@RequestFieldAnnontion()
	private String user_recieve_time;
	@RequestFieldAnnontion()
	private String delivery_type;
	@RequestFieldAnnontion()
	private String sign_status;
	@RequestFieldAnnontion()
	private String receipt_status;
	@RequestFieldAnnontion()
	private String need_receipt;
	@RequestFieldAnnontion()
	private String receipt_no;
	@RequestFieldAnnontion()
	private String origin_code;
	@RequestFieldAnnontion()
	private String dest_code;
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
		ZteInstUpdateRequest<OrderDeliveryBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderDeliveryBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		//构造参数
		QueryResponse<List<OrderDeliveryBusiRequest>> resp = new QueryResponse<List<OrderDeliveryBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery,resp,new ArrayList<OrderDeliveryBusiRequest>());
	}

	public String getDelivery_id() {
		return delivery_id;
	}

	public void setDelivery_id(String delivery_id) {
		this.delivery_id = delivery_id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getShip_type() {
		return ship_type;
	}

	public void setShip_type(String ship_type) {
		this.ship_type = ship_type;
	}

	public Integer getIs_protect() {
		return is_protect;
	}

	public void setIs_protect(Integer is_protect) {
		this.is_protect = is_protect;
	}

	public Double getProtect_price() {
		return protect_price;
	}

	public void setProtect_price(Double protect_price) {
		this.protect_price = protect_price;
	}

	public String getLogi_id() {
		return logi_id;
	}

	public void setLogi_id(String logi_id) {
		this.logi_id = logi_id;
	}

	public String getLogi_name() {
		return logi_name;
	}

	public void setLogi_name(String logi_name) {
		this.logi_name = logi_name;
	}

	public String getLogi_no() {
		return logi_no;
	}

	public void setLogi_no(String logi_no) {
		this.logi_no = logi_no;
	}

	public String getShip_name() {
		return ship_name;
	}

	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}

	public Long getProvince_id() {
		return province_id;
	}

	public void setProvince_id(Long province_id) {
		this.province_id = province_id;
	}

	public Long getCity_id() {
		return city_id;
	}

	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}

	public Long getRegion_id() {
		return region_id;
	}

	public void setRegion_id(Long region_id) {
		this.region_id = region_id;
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

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getShip_addr() {
		return ship_addr;
	}

	public void setShip_addr(String ship_addr) {
		this.ship_addr = ship_addr;
	}

	public String getShip_zip() {
		return ship_zip;
	}

	public void setShip_zip(String ship_zip) {
		this.ship_zip = ship_zip;
	}

	public String getShip_tel() {
		return ship_tel;
	}

	public void setShip_tel(String ship_tel) {
		this.ship_tel = ship_tel;
	}

	public String getShip_mobile() {
		return ship_mobile;
	}

	public void setShip_mobile(String ship_mobile) {
		this.ship_mobile = ship_mobile;
	}

	public String getShip_email() {
		return ship_email;
	}

	public void setShip_email(String ship_email) {
		this.ship_email = ship_email;
	}

	public String getOp_name() {
		return op_name;
	}

	public void setOp_name(String op_name) {
		this.op_name = op_name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getShip_num() {
		return ship_num;
	}

	public void setShip_num(Integer ship_num) {
		this.ship_num = ship_num;
	}

	public Integer getPrint_status() {
		return print_status;
	}

	public void setPrint_status(Integer print_status) {
		this.print_status = print_status;
	}

	public Long getWeight() {
		return weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	public Integer getShip_status() {
		return ship_status;
	}

	public void setShip_status(Integer ship_status) {
		this.ship_status = ship_status;
	}

	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	@NotDbField
	public Double getOrder_amount() {
		return order_amount;
	}
	@NotDbField
	public void setOrder_amount(Double order_amount) {
		this.order_amount = order_amount;
	}
	@NotDbField
	public String getPru_order_name() {
		return pru_order_name;
	}
	@NotDbField
	public void setPru_order_name(String pru_order_name) {
		this.pru_order_name = pru_order_name;
	}

	public String getHouse_id() {
		return house_id;
	}

	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}
	
	
	@NotDbField
	public Double getShipping_amount() {
		return shipping_amount;
	}
	@NotDbField
	public void setShipping_amount(Double shipping_amount) {
		this.shipping_amount = shipping_amount;
	}
	@NotDbField
	public String getAudit_status() {
		return audit_status;
	}
	@NotDbField
	public void setAudit_status(String audit_status) {
		this.audit_status = audit_status;
	}
	@NotDbField
	public Double getGoods_amount() {
		return goods_amount;
	}
	@NotDbField
	public void setGoods_amount(Double goods_amount) {
		this.goods_amount = goods_amount;
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
	public String getCreate_type() {
		return create_type;
	}
	@NotDbField
	public void setCreate_type(String create_type) {
		this.create_type = create_type;
	}
	@NotDbField
	public String getSn() {
		return sn;
	}
	@NotDbField
	public void setSn(String sn) {
		this.sn = sn;
	}
	@NotDbField
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLogi_receiver() {
		return logi_receiver;
	}

	public void setLogi_receiver(String logi_receiver) {
		this.logi_receiver = logi_receiver;
	}

	public String getLogi_receiver_phone() {
		return logi_receiver_phone;
	}

	public void setLogi_receiver_phone(String logi_receiver_phone) {
		this.logi_receiver_phone = logi_receiver_phone;
	}

	public String getPost_fee() {
		return post_fee;
	}

	public void setPost_fee(String post_fee) {
		this.post_fee = post_fee;
	}

	public String getOut_house_id() {
		return out_house_id;
	}

	public void setOut_house_id(String out_house_id) {
		this.out_house_id = out_house_id;
	}

	public String getShipping_time() {
		return shipping_time;
	}

	public void setShipping_time(String shipping_time) {
		this.shipping_time = shipping_time;
	}

	public String getN_shipping_amount() {
		return n_shipping_amount;
	}

	public void setN_shipping_amount(String n_shipping_amount) {
		this.n_shipping_amount = n_shipping_amount;
	}

	public String getShipping_company() {
		return shipping_company;
	}

	public void setShipping_company(String shipping_company) {
		this.shipping_company = shipping_company;
	}

	public String getUser_recieve_time() {
		return user_recieve_time;
	}

	public void setUser_recieve_time(String user_recieve_time) {
		this.user_recieve_time = user_recieve_time;
	}

	public String getDelivery_type() {
		return delivery_type;
	}

	public void setDelivery_type(String delivery_type) {
		this.delivery_type = delivery_type;
	}

	public String getSign_status() {
		return sign_status;
	}

	public void setSign_status(String sign_status) {
		this.sign_status = sign_status;
	}

	public String getReceipt_status() {
		return receipt_status;
	}

	public void setReceipt_status(String receipt_status) {
		this.receipt_status = receipt_status;
	}

	public String getNeed_receipt() {
		return need_receipt;
	}

	public void setNeed_receipt(String need_receipt) {
		this.need_receipt = need_receipt;
	}

	public String getReceipt_no() {
		return receipt_no;
	}

	public void setReceipt_no(String receipt_no) {
		this.receipt_no = receipt_no;
	}

	public String getOrigin_code() {
		return origin_code;
	}

	public void setOrigin_code(String origin_code) {
		this.origin_code = origin_code;
	}

	public String getDest_code() {
		return dest_code;
	}

	public void setDest_code(String dest_code) {
		this.dest_code = dest_code;
	}
	/**
     * 只插入数据库，不更新缓存，在标准化入库时执行
     */
    @NotDbField
    public ZteResponse  insertNoCache(){
        return RequestStoreExector.getInstance().insertZteRequestInst(this);
    }
}

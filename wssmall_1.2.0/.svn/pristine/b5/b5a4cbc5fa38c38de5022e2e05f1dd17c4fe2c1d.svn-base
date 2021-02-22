package zte.net.ecsord.params.busi.req;

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
 * @author wu.i 订单数对象
 * 
 */
@RequestBeanAnnontion(primary_keys = "item_prod_inst_id", depency_keys="item_prod_inst_id",table_name = "es_order_items_prod_ext", service_desc = "订单货品扩展实体信息")
public class OrderItemProdExtBusiRequest extends ZteBusiRequest<ZteResponse>
		implements IZteBusiRequestHander {

	private static final long serialVersionUID = 1L;

	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String is_loves_phone;
	@RequestFieldAnnontion()
	private String old_remd_user;
	@RequestFieldAnnontion()
	private String is_old;
	@RequestFieldAnnontion()
	private String terminal_num;
	@RequestFieldAnnontion()
	private String first_payment;
	@RequestFieldAnnontion()
	private String white_cart_type;
	@RequestFieldAnnontion()
	private String vice_acc_nbr;
	@RequestFieldAnnontion()
	private String cert_card_name;
	@RequestFieldAnnontion()
	private String cert_card_num;
	@RequestFieldAnnontion()
	private String cert_card_sex;
	@RequestFieldAnnontion()
	private String cert_card_birth;
	@RequestFieldAnnontion()
	private String cert_card_nation;
	@RequestFieldAnnontion()
	private String cert_address;
	@RequestFieldAnnontion()
	private String cert_failure_time;
	@RequestFieldAnnontion()
	private String certi_type;
	@RequestFieldAnnontion()
	private String item_id;
	@RequestFieldAnnontion()
	private String item_prod_inst_id;
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
	private String create_time;
	@RequestFieldAnnontion()
	private String update_time;
	@RequestFieldAnnontion()
	private String phone_num;
	@RequestFieldAnnontion()
	private String readId;
	@RequestFieldAnnontion()
	private String ICCID;
	@RequestFieldAnnontion()
	private String sim_card_sku;
	
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getIs_loves_phone() {
		return is_loves_phone;
	}
	public void setIs_loves_phone(String is_loves_phone) {
		this.is_loves_phone = is_loves_phone;
	}
	public String getOld_remd_user() {
		return old_remd_user;
	}
	public void setOld_remd_user(String old_remd_user) {
		this.old_remd_user = old_remd_user;
	}
	public String getIs_old() {
		return is_old;
	}
	public void setIs_old(String is_old) {
		this.is_old = is_old;
	}
	public String getTerminal_num() {
		return terminal_num;
	}
	public void setTerminal_num(String terminal_num) {
		this.terminal_num = terminal_num;
	}
	public String getFirst_payment() {
		return first_payment;
	}
	public void setFirst_payment(String first_payment) {
		this.first_payment = first_payment;
	}
	public String getWhite_cart_type() {
		return white_cart_type;
	}
	public void setWhite_cart_type(String white_cart_type) {
		this.white_cart_type = white_cart_type;
	}
	public String getVice_acc_nbr() {
		return vice_acc_nbr;
	}
	public void setVice_acc_nbr(String vice_acc_nbr) {
		this.vice_acc_nbr = vice_acc_nbr;
	}
	public String getCert_card_name() {
		return cert_card_name;
	}
	public void setCert_card_name(String cert_card_name) {
		this.cert_card_name = cert_card_name;
	}
	public String getCert_card_num() {
		return cert_card_num;
	}
	public void setCert_card_num(String cert_card_num) {
		this.cert_card_num = cert_card_num;
	}
	public String getCert_card_sex() {
		return cert_card_sex;
	}
	public void setCert_card_sex(String cert_card_sex) {
		this.cert_card_sex = cert_card_sex;
	}
	public String getCert_card_birth() {
		return cert_card_birth;
	}
	public void setCert_card_birth(String cert_card_birth) {
		this.cert_card_birth = cert_card_birth;
	}
	public String getCert_card_nation() {
		return cert_card_nation;
	}
	public void setCert_card_nation(String cert_card_nation) {
		this.cert_card_nation = cert_card_nation;
	}
	public String getCert_address() {
		return cert_address;
	}
	public void setCert_address(String cert_address) {
		this.cert_address = cert_address;
	}
	public String getCert_failure_time() {
		return cert_failure_time;
	}
	public void setCert_failure_time(String cert_failure_time) {
		this.cert_failure_time = cert_failure_time;
	}
	public String getCerti_type() {
		return certi_type;
	}
	public void setCerti_type(String certi_type) {
		this.certi_type = certi_type;
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
	
	@Override
	@NotDbField
	public <T>T store() {
		ZteInstUpdateRequest<OrderItemProdExtBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderItemProdExtBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		//构造参数
		QueryResponse<OrderItemProdExtBusiRequest> resp = new QueryResponse<OrderItemProdExtBusiRequest>();
		return RequestStoreExector.orderProdExtBusiLoadAssemble(instQuery,resp,new OrderItemProdExtBusiRequest());
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
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getReadId() {
		return readId;
	}
	public void setReadId(String readId) {
		this.readId = readId;
	}
	public String getICCID() {
		return ICCID;
	}
	public void setICCID(String iCCID) {
		ICCID = iCCID;
	}
	public String getSim_card_sku() {
		return sim_card_sku;
	}
	public void setSim_card_sku(String sim_card_sku) {
		this.sim_card_sku = sim_card_sku;
	}
	
}

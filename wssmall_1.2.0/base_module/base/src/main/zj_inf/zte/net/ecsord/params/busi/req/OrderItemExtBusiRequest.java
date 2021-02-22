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
@RequestBeanAnnontion(primary_keys = "item_id", depency_keys="item_id", table_name = "es_order_items_ext", service_desc = "订单扩展信息横表")
public class OrderItemExtBusiRequest extends ZteBusiRequest<ZteResponse> implements IZteBusiRequestHander {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion()
	private String is_change;
	@RequestFieldAnnontion()
	private String superiors_bankcode;
	@RequestFieldAnnontion()
	private String phone_deposit;
	@RequestFieldAnnontion()
	private String discountid;
	@RequestFieldAnnontion()
	private String is_bill;
	@RequestFieldAnnontion()
	private String collection;
	@RequestFieldAnnontion()
	private String account_time;
	@RequestFieldAnnontion()
	private String account_vali;
	@RequestFieldAnnontion()
	private String account_status;
	@RequestFieldAnnontion()
	private String bss_status;
	@RequestFieldAnnontion()
	private String bss_pre_status;
	@RequestFieldAnnontion()
	private String ess_pre_status;
	@RequestFieldAnnontion()
	private String ess_pre_desc;
	@RequestFieldAnnontion()
	private String bss_pre_desc;
	@RequestFieldAnnontion()
	private String print_flag;
	@RequestFieldAnnontion()
	private String print_time;
	@RequestFieldAnnontion()
	private String print_user;
	@RequestFieldAnnontion()
	private String invoice_no;
	@RequestFieldAnnontion()
	private String invoice_code;	
	@RequestFieldAnnontion()
	private String ess_order_id;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String item_id;
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
	private String inventory_name;
	@RequestFieldAnnontion()
	private String invoice_group_content;
	@RequestFieldAnnontion()
	private String invoice_print_type;
	@RequestFieldAnnontion()
	private String reserve8;
	@RequestFieldAnnontion()
	private String source_from;
	@RequestFieldAnnontion()
	private String goods_type;
	@RequestFieldAnnontion()
	private String is_customized;
	@RequestFieldAnnontion()
	private String brand_number;
	@RequestFieldAnnontion()
	private String active_no;
	@RequestFieldAnnontion()
	private String bss_account_time;
	@RequestFieldAnnontion()
	private String phone_num;
	//AOP新增字段
	@RequestFieldAnnontion()
	private String zb_ess_order_id;
	@RequestFieldAnnontion()
	private String tax_no;
	@RequestFieldAnnontion()
	private String bssOrderId;
	@RequestFieldAnnontion()
	private String totalFee;
	@RequestFieldAnnontion()
	private String card_data;
	@RequestFieldAnnontion()
	private Integer bss_time_type; 
	@RequestFieldAnnontion()
	private String goods_cat_id;
	@RequestFieldAnnontion()
	private String goods_num;
	@RequestFieldAnnontion()
    private String CardCBssOrderId ;
	/*@RequestFieldAnnontion()
    private String CardCBssAcceptId ;*/
	public String getBrand_number() {
		return brand_number;
	}

	public void setBrand_number(String brand_number) { 
		this.brand_number = brand_number;
	}

	public String getIs_customized() {
		return is_customized;
	}

	public void setIs_customized(String is_customized) {
		this.is_customized = is_customized;
	}

	public String getGoods_type() {
		return goods_type;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	@Override
	@NotDbField
	public <T> T store() {
		ZteInstUpdateRequest<OrderItemExtBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderItemExtBusiRequest>();
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		// 构造参数
		QueryResponse<OrderItemExtBusiRequest> resp = new QueryResponse<OrderItemExtBusiRequest>();
		return RequestStoreExector.orderProdBusiLoadAssemble(instQuery, resp,new OrderItemExtBusiRequest());
	}
	/**
	 * 只插入数据库，不更新缓存，在标准化入库时执行
	 */
	@NotDbField
	public ZteResponse  insertNoCache(){
		return RequestStoreExector.getInstance().insertZteRequestInst(this);
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

	public String getIs_change() {
		return is_change;
	}

	public void setIs_change(String is_change) {
		this.is_change = is_change;
	}

	public String getSuperiors_bankcode() {
		return superiors_bankcode;
	}

	public void setSuperiors_bankcode(String superiors_bankcode) {
		this.superiors_bankcode = superiors_bankcode;
	}

	public String getPhone_deposit() {
		return phone_deposit;
	}

	public void setPhone_deposit(String phone_deposit) {
		this.phone_deposit = phone_deposit;
	}

	public String getDiscountid() {
		return discountid;
	}

	public void setDiscountid(String discountid) {
		this.discountid = discountid;
	}

	public String getIs_bill() {
		return is_bill;
	}

	public void setIs_bill(String is_bill) {
		this.is_bill = is_bill;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getAccount_time() {
		return account_time;
	}

	public void setAccount_time(String account_time) {
		this.account_time = account_time;
	}

	public String getAccount_vali() {
		return account_vali;
	}

	public void setAccount_vali(String account_vali) {
		this.account_vali = account_vali;
	}

	public String getAccount_status() {
		return account_status;
	}

	public void setAccount_status(String account_status) {
		this.account_status = account_status;
	}

	public String getBss_status() {
		return bss_status;
	}

	public void setBss_status(String bss_status) {
		this.bss_status = bss_status;
	}

	public String getBss_pre_status() {
		return bss_pre_status;
	}

	public void setBss_pre_status(String bss_pre_status) {
		this.bss_pre_status = bss_pre_status;
	}

	public String getEss_pre_status() {
		return ess_pre_status;
	}

	public void setEss_pre_status(String ess_pre_status) {
		this.ess_pre_status = ess_pre_status;
	}

	public String getEss_pre_desc() {
		return ess_pre_desc;
	}

	public void setEss_pre_desc(String ess_pre_desc) {
		this.ess_pre_desc = ess_pre_desc;
	}

	public String getBss_pre_desc() {
		return bss_pre_desc;
	}

	public void setBss_pre_desc(String bss_pre_desc) {
		this.bss_pre_desc = bss_pre_desc;
	}

	public String getPrint_flag() {
		return print_flag;
	}

	public void setPrint_flag(String print_flag) {
		this.print_flag = print_flag;
	}

	public String getPrint_time() {
		return print_time;
	}

	public void setPrint_time(String print_time) {
		this.print_time = print_time;
	}

	public String getPrint_user() {
		return print_user;
	}

	public void setPrint_user(String print_user) {
		this.print_user = print_user;
	}

	public String getInvoice_no() {
		return invoice_no;
	}

	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}

	public String getEss_order_id() {
		return ess_order_id;
	}

	public void setEss_order_id(String ess_order_id) {
		this.ess_order_id = ess_order_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
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

	public String getInventory_name() {
		return inventory_name;
	}

	public void setInventory_name(String inventory_name) {
		this.inventory_name = inventory_name;
	}

	public String getInvoice_group_content() {
		return invoice_group_content;
	}

	public void setInvoice_group_content(String invoice_group_content) {
		this.invoice_group_content = invoice_group_content;
	}

	public String getInvoice_print_type() {
		return invoice_print_type;
	}

	public void setInvoice_print_type(String invoice_print_type) {
		this.invoice_print_type = invoice_print_type;
	}

	public String getReserve8() {
		return reserve8;
	}

	public void setReserve8(String reserve8) {
		this.reserve8 = reserve8;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getActive_no() {
		return active_no;
	}

	public void setActive_no(String active_no) {
		this.active_no = active_no;
	}

	public String getBss_account_time() {
		return bss_account_time;
	}

	public void setBss_account_time(String bss_account_time) {
		this.bss_account_time = bss_account_time;
	}
	
	public String getPhone_num(){
		return phone_num;
	}
	
	public void setPhone_num(String phone_num){
		this.phone_num = phone_num;
	}

	public String getZb_ess_order_id() {
		return zb_ess_order_id;
	}

	public void setZb_ess_order_id(String zb_ess_order_id) {
		this.zb_ess_order_id = zb_ess_order_id;
	}

	public String getTax_no() {
		return tax_no;
	}

	public void setTax_no(String tax_no) {
		this.tax_no = tax_no;
	}

	public String getBssOrderId() {
		return bssOrderId;
	}

	public void setBssOrderId(String bssOrderId) {
		this.bssOrderId = bssOrderId;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getCard_data() {
		return card_data;
	}

	public void setCard_data(String card_data) {
		this.card_data = card_data;
	}

	public String getInvoice_code() {
		return invoice_code;
	}

	public void setInvoice_code(String invoice_code) {
		this.invoice_code = invoice_code;
	}


	public Integer getBss_time_type() {
		return bss_time_type;
	}

	public void setBss_time_type(Integer bss_time_type) {
		this.bss_time_type = bss_time_type;
	}

	public String getGoods_cat_id() {
		return goods_cat_id;
	}

	public void setGoods_cat_id(String goods_cat_id) {
		this.goods_cat_id = goods_cat_id;
	}

	public String getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(String goods_num) {
		this.goods_num = goods_num;
	}

    public String getCardCBssOrderId() {
        return CardCBssOrderId;
    }

    public void setCardCBssOrderId(String cardCBssOrderId) {
        CardCBssOrderId = cardCBssOrderId;
    }

    /*public String getCardCBssAcceptId() {
        return CardCBssAcceptId;
    }

    public void setCardCBssAcceptId(String cardCBssAcceptId) {
        CardCBssAcceptId = cardCBssAcceptId;
    }*/
	
}

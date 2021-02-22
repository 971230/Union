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

@RequestBeanAnnontion(primary_keys="inv_inst_id",table_name="es_order_items_inv_prints",depency_keys="order_id",service_desc="订单发票打印表")
public class OrderItemsInvPrintsBusiRequest extends ZteBusiRequest<ZteResponse>  implements IZteBusiRequestHander {
	@RequestFieldAnnontion()
	private String order_id; // 订单编号
	@RequestFieldAnnontion()
	private String item_id; // 商品单编号
	@RequestFieldAnnontion()
	private String inv_inst_id; // 发票打印实例id
	@RequestFieldAnnontion()
	private Integer seq; // 序号
	@RequestFieldAnnontion()
	private String create_time; // 创建时间
	@RequestFieldAnnontion()
	private String uname; // 发票姓名（ 买家姓名）
	@RequestFieldAnnontion()
	private String utel; // 发票电话（买家电话）
	@RequestFieldAnnontion()
	private float order_amount; // 订单金额
	@RequestFieldAnnontion()
	private float paymoney; // 支付金额
	@RequestFieldAnnontion()
	private String terminal_code; // 终端串号
	@RequestFieldAnnontion()
	private String source_from;
	@RequestFieldAnnontion()
	private String col1;
	@RequestFieldAnnontion()
	private String col2;
	@RequestFieldAnnontion()
	private String col3;
	@RequestFieldAnnontion()
	private String print_flag; // 打印标志（0未打印、1 已打印）
	@RequestFieldAnnontion()
	private String print_time; // 打印时间
	@RequestFieldAnnontion()
	private String print_user; // 打印工号
	@RequestFieldAnnontion()
	private String invoice_num; // 发票号
	@RequestFieldAnnontion()
	private String memo; // 备注
	
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

	public String getInv_inst_id() {
		return inv_inst_id;
	}

	public void setInv_inst_id(String inv_inst_id) {
		this.inv_inst_id = inv_inst_id;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUtel() {
		return utel;
	}

	public void setUtel(String utel) {
		this.utel = utel;
	}

	public float getOrder_amount() {
		return order_amount;
	}

	public void setOrder_amount(float order_amount) {
		this.order_amount = order_amount;
	}

	public float getPaymoney() {
		return paymoney;
	}

	public void setPaymoney(float paymoney) {
		this.paymoney = paymoney;
	}

	public String getTerminal_code() {
		return terminal_code;
	}

	public void setTerminal_code(String terminal_code) {
		this.terminal_code = terminal_code;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
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

	public String getInvoice_num() {
		return invoice_num;
	}

	public void setInvoice_num(String invoice_num) {
		this.invoice_num = invoice_num;
	}

	@Override
	public <T> T store() {
		ZteInstUpdateRequest<OrderItemsInvPrintsBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderItemsInvPrintsBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<OrderItemsInvPrintsBusiRequest>> resp = new QueryResponse<List<OrderItemsInvPrintsBusiRequest>>();
		return RequestStoreExector.orderProdBusiLoadAssemble(instQuery,resp,new ArrayList<OrderItemsInvPrintsBusiRequest>());
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}

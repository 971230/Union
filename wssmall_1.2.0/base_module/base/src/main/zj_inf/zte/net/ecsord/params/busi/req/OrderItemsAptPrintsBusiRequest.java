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
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.framework.database.NotDbField;

@RequestBeanAnnontion(primary_keys="acceptance_id",table_name="es_order_items_apt_prints",depency_keys="item_id",service_desc="订单商品单受理单打印表")
public class OrderItemsAptPrintsBusiRequest extends ZteBusiRequest<ZteResponse>  implements IZteBusiRequestHander {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String item_id;
	@RequestFieldAnnontion()
	private String acceptance_id;
	@RequestFieldAnnontion()
	private String mobile_tel;
	@RequestFieldAnnontion()
	private String create_time;
	@RequestFieldAnnontion(need_query="no")
	private String acceptance_html;
	@RequestFieldAnnontion(need_query="no")
	private String acceptance_html_2;
	@RequestFieldAnnontion(need_query="no")
	private String acceptance_html_3;
	@RequestFieldAnnontion()
	private String status;
	@RequestFieldAnnontion()
	private String receipt_code;
	@RequestFieldAnnontion()
	private String receipt_mode;
	@RequestFieldAnnontion()
	private String goods_id;
	@RequestFieldAnnontion()
	private String source_from;
	@RequestFieldAnnontion()
	private String col1;
	@RequestFieldAnnontion()
	private String col2;
	@RequestFieldAnnontion()
	private String col3;
	
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

	public String getAcceptance_id() {
		return acceptance_id;
	}

	public void setAcceptance_id(String acceptance_id) {
		this.acceptance_id = acceptance_id;
	}
	
	public String getMobile_tel() {
		return mobile_tel;
	}

	public void setMobile_tel(String mobile_tel) {
		this.mobile_tel = mobile_tel;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getAcceptance_html() {
		return acceptance_html;
	}

	public void setAcceptance_html(String acceptance_html) {
		List<String> acceptanceForms = parseStr(acceptance_html);
		if (acceptanceForms.size() > 0) {
			this.acceptance_html = acceptanceForms.get(0);
		}
		if (acceptanceForms.size() > 1) {
			this.setAcceptance_html_2(acceptanceForms.get(1));
		}
		if (acceptanceForms.size() > 2) {
			this.setAcceptance_html_3(acceptanceForms.get(2));
		}
	}
	
	private List<String> parseStr(String errorStr) {
		List<String> errorsList = new ArrayList<String>();
		parseCicleStr(errorsList, errorStr);
		return errorsList;
	}

	private void parseCicleStr(List<String> errorsList, String errorStr) {
		int splitPoint = 3500;
		while (!StringUtil.isEmpty(errorStr) && (errorStr.length() >splitPoint)) {
			String error_info = errorStr.substring(0, splitPoint).toString();
			errorsList.add(error_info);
			errorStr = errorStr.substring(splitPoint);
			parseCicleStr(errorsList, errorStr);
		}
		errorsList.add(errorStr);
	}
	
	public String getAcceptance_html_2() {
		return acceptance_html_2;
	}

	public void setAcceptance_html_2(String acceptance_html_2) {
		this.acceptance_html_2 = acceptance_html_2;
	}

	public String getAcceptance_html_3() {
		return acceptance_html_3;
	}

	public void setAcceptance_html_3(String acceptance_html_3) {
		this.acceptance_html_3 = acceptance_html_3;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public String getReceipt_code() {
		return receipt_code;
	}

	public void setReceipt_code(String receipt_code) {
		this.receipt_code = receipt_code;
	}

	public String getReceipt_mode() {
		return receipt_mode;
	}

	public void setReceipt_mode(String receipt_mode) {
		this.receipt_mode = receipt_mode;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
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

	@Override
	public <T> T store() {
		ZteInstUpdateRequest<OrderItemsAptPrintsBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderItemsAptPrintsBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<OrderItemsAptPrintsBusiRequest>> resp = new QueryResponse<List<OrderItemsAptPrintsBusiRequest>>();
		return RequestStoreExector.orderProdBusiLoadAssemble(instQuery,resp,new ArrayList<OrderItemsAptPrintsBusiRequest>());
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

}

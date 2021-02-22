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
@RequestBeanAnnontion(primary_keys = "SUB_PROD_INFO_ID", depency_keys = "order_id", table_name = "ES_ORDER_SUB_PROD_INFO", service_desc = "温大子产品表")
public class OrderSubProdInfoBusiRequest extends ZteBusiRequest<ZteResponse>implements IZteBusiRequestHander{

	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion(dname = "sub_prod_info_id", desc = "附加产品实例ID", need_query = "yes")
	private String sub_prod_info_id;
	@RequestFieldAnnontion(dname = "order_id", desc = "内部单号", need_query = "yes")
	private String order_id;
	@RequestFieldAnnontion(dname = "sub_prod_name", desc = "子产品名称", need_query = "yes")
	private String sub_prod_name;
	@RequestFieldAnnontion(dname = "sub_prod_code", desc = "子产品编码", need_query = "yes")
	private String sub_prod_code;
	@RequestFieldAnnontion(dname = "source_from", desc = "", need_query = "yes")
	private String source_from;
	
	
	public String getSub_prod_info_id() {
		return sub_prod_info_id;
	}

	public void setSub_prod_info_id(String sub_prod_info_id) {
		this.sub_prod_info_id = sub_prod_info_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getSub_prod_name() {
		return sub_prod_name;
	}

	public void setSub_prod_name(String sub_prod_name) {
		this.sub_prod_name = sub_prod_name;
	}

	public String getSub_prod_code() {
		return sub_prod_code;
	}

	public void setSub_prod_code(String sub_prod_code) {
		this.sub_prod_code = sub_prod_code;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
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
		QueryResponse<List<OrderSubProductBusiRequest>> resp = new QueryResponse<List<OrderSubProductBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery, resp,
				new ArrayList<OrderSubProductBusiRequest>());
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

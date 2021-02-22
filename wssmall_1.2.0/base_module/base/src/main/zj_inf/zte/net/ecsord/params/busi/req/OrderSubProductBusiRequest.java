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
 * @author ZX
 * @version 2015-12-23
 * @see 附加产品表
 * 
 */
@RequestBeanAnnontion(primary_keys = "sub_pro_inst_id", depency_keys = "order_id", table_name = "ES_ORDER_SUB_PRODCUT", service_desc = "附加产品表")
public class OrderSubProductBusiRequest extends ZteBusiRequest<ZteResponse>implements IZteBusiRequestHander {
	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion(dname = "sub_pro_inst_id", desc = "附加产品实例ID", need_query = "yes")
	private String sub_pro_inst_id;
	@RequestFieldAnnontion(dname = "order_id", desc = "订单ID", need_query = "no")
	private String order_id;
	@RequestFieldAnnontion(dname = "prod_inst_id", desc = "对应卡号实例ID", need_query = "no")
	private String prod_inst_id	;
	@RequestFieldAnnontion(dname = "sub_pro_code", desc = "附加产品编码", need_query = "no")
	private String sub_pro_code;
	@RequestFieldAnnontion(dname = "sub_pro_name", desc = "附加产品名称", need_query = "no")
	private String sub_pro_name;
	@RequestFieldAnnontion(dname = "sub_pro_desc", desc = "附加产品说明", need_query = "no")
	private String sub_pro_desc;
	@RequestFieldAnnontion(dname = "sub_prod_type", desc = "产品类型(主副卡)", need_query = "no")
	private String sub_prod_type;
	@RequestFieldAnnontion(dname = "source_from", desc = "", need_query = "no")
	private String source_from;

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

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getSub_pro_inst_id() {
		return sub_pro_inst_id;
	}

	public void setSub_pro_inst_id(String sub_pro_inst_id) {
		this.sub_pro_inst_id = sub_pro_inst_id;
	}

	public String getProd_inst_id() {
		return prod_inst_id;
	}

	public void setProd_inst_id(String prod_inst_id) {
		this.prod_inst_id = prod_inst_id;
	}

	public String getSub_pro_code() {
		return sub_pro_code;
	}

	public void setSub_pro_code(String sub_pro_code) {
		this.sub_pro_code = sub_pro_code;
	}

	public String getSub_pro_name() {
		return sub_pro_name;
	}

	public void setSub_pro_name(String sub_pro_name) {
		this.sub_pro_name = sub_pro_name;
	}

	public String getSub_pro_desc() {
		return sub_pro_desc;
	}

	public void setSub_pro_desc(String sub_pro_desc) {
		this.sub_pro_desc = sub_pro_desc;
	}

	public String getSub_prod_type() {
		return sub_prod_type;
	}

	public void setSub_prod_type(String sub_prod_type) {
		this.sub_prod_type = sub_prod_type;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

}

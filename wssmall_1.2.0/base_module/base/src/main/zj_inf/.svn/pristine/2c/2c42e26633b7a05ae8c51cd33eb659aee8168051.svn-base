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
 * @author Rapon
 * @version 2016-04-08
 * @see 合约活动下自选包表
 * 
 */
@RequestBeanAnnontion(primary_keys = "inst_id", depency_keys = "order_id", table_name = "es_attr_package_activity", service_desc = "合约活动下自选包表")
public class AttrPackageActivityBusiRequest extends ZteBusiRequest<ZteResponse>implements IZteBusiRequestHander {
	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion(dname = "inst_id", desc = "主键", need_query = "yes")
	private String inst_id;
	@RequestFieldAnnontion(dname = "activity_inst_id", desc = "es_order_activity表主键", need_query = "no")
	private String activity_inst_id;
	@RequestFieldAnnontion(dname = "order_id", desc = "订单号", need_query = "no")
	private String order_id;
	@RequestFieldAnnontion(dname = "package_code", desc = "包编号", need_query = "no")
	private String package_code;
	@RequestFieldAnnontion(dname = "package_name", desc = "包名称", need_query = "no")
	private String package_name;
	@RequestFieldAnnontion(dname = "element_code", desc = "元素编码", need_query = "no")
	private String element_code;
	@RequestFieldAnnontion(dname = "element_type", desc = "元素类型", need_query = "no")
	private String element_type;
	@RequestFieldAnnontion(dname = "element_name", desc = "元素名称", need_query = "no")
	private String element_name;
	@RequestFieldAnnontion(dname = "source_from", desc = "ECS", need_query = "no")
	private String source_from;

	@Override
	@NotDbField
	public <T> T store() {
		ZteInstUpdateRequest<AttrPackageActivityBusiRequest> updateRequest = new ZteInstUpdateRequest<AttrPackageActivityBusiRequest>();
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<AttrPackageActivityBusiRequest>> resp = new QueryResponse<List<AttrPackageActivityBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery, resp,
				new ArrayList<AttrPackageActivityBusiRequest>());
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

	public String getInst_id() {
		return inst_id;
	}

	public void setInst_id(String inst_id) {
		this.inst_id = inst_id;
	}

	public String getActivity_inst_id() {
		return activity_inst_id;
	}

	public void setActivity_inst_id(String activity_inst_id) {
		this.activity_inst_id = activity_inst_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getPackage_code() {
		return package_code;
	}

	public void setPackage_code(String package_code) {
		this.package_code = package_code;
	}

	public String getPackage_name() {
		return package_name;
	}

	public void setPackage_name(String package_name) {
		this.package_name = package_name;
	}

	public String getElement_code() {
		return element_code;
	}

	public void setElement_code(String element_code) {
		this.element_code = element_code;
	}

	public String getElement_type() {
		return element_type;
	}

	public void setElement_type(String element_type) {
		this.element_type = element_type;
	}

	public String getElement_name() {
		return element_name;
	}

	public void setElement_name(String element_name) {
		this.element_name = element_name;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
}

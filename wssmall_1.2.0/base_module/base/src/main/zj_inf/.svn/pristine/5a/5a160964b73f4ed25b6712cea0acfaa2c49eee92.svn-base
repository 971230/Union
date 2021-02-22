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
 * @see 附加产品可选包表
 * 
 */
@RequestBeanAnnontion(primary_keys = "package_inst_id", depency_keys = "order_id", table_name = "ES_ATTR_PACKAGE_SubProd", service_desc = "附加产品可选包表")
public class AttrPackageSubProdBusiRequest extends ZteBusiRequest<ZteResponse>implements IZteBusiRequestHander {
	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion(dname = "package_inst_id", desc = "可选包实例ID", need_query = "yes")
	private String package_inst_id;
	@RequestFieldAnnontion(dname = "order_id", desc = "订单ID", need_query = "no")
	private String order_id;
	@RequestFieldAnnontion(dname = "subProd_inst_id", desc = "附加产品实例ID", need_query = "no")
	private String subProd_inst_id;
	@RequestFieldAnnontion(dname = "package_code", desc = "可选包编号", need_query = "no")
	private String package_code;
	@RequestFieldAnnontion(dname = "package_name", desc = "可选包名称", need_query = "no")
	private String package_name;
	@RequestFieldAnnontion(dname = "product_code", desc = "产品编号", need_query = "no")
	private String product_code;
	@RequestFieldAnnontion(dname = "element_code", desc = "元素编码", need_query = "no")
	private String element_code;
	@RequestFieldAnnontion(dname = "element_type", desc = "元素类型", need_query = "no")
	private String element_type;
	@RequestFieldAnnontion(dname = "element_name", desc = "元素名称", need_query = "no")
	private String element_name;
	@RequestFieldAnnontion(dname = "oper_type", desc = "操作类型", need_query = "no")
	private String oper_type;
	@RequestFieldAnnontion(dname = "change_type", desc = "", need_query = "no")
	private String change_type;
	@RequestFieldAnnontion(dname = "biz_type", desc = "", need_query = "no")
	private String biz_type;
	@RequestFieldAnnontion(dname = "source_from", desc = "", need_query = "no")
	private String source_from;
	@RequestFieldAnnontion(dname = "status", desc = "办理状态", need_query = "no")
	private String status;
	@RequestFieldAnnontion(dname = "sub_bss_order_id", desc = "开户流水", need_query = "no")
	private String sub_bss_order_id;
	@RequestFieldAnnontion(dname = "sku", desc = "sku", need_query = "no")
	private String sku;

	@Override
	@NotDbField
	public <T> T store() {
		ZteInstUpdateRequest<AttrPackageSubProdBusiRequest> updateRequest = new ZteInstUpdateRequest<AttrPackageSubProdBusiRequest>();
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<AttrPackageSubProdBusiRequest>> resp = new QueryResponse<List<AttrPackageSubProdBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery, resp,
				new ArrayList<AttrPackageSubProdBusiRequest>());
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

	public String getPackage_inst_id() {
		return package_inst_id;
	}

	public void setPackage_inst_id(String package_inst_id) {
		this.package_inst_id = package_inst_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}



	public String getSubProd_inst_id() {
		return subProd_inst_id;
	}

	public void setSubProd_inst_id(String subProd_inst_id) {
		this.subProd_inst_id = subProd_inst_id;
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

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
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

	public String getOper_type() {
		return oper_type;
	}

	public void setOper_type(String oper_type) {
		this.oper_type = oper_type;
	}

	public String getChange_type() {
		return change_type;
	}

	public void setChange_type(String change_type) {
		this.change_type = change_type;
	}

	public String getBiz_type() {
		return biz_type;
	}

	public void setBiz_type(String biz_type) {
		this.biz_type = biz_type;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSub_bss_order_id() {
		return sub_bss_order_id;
	}

	public void setSub_bss_order_id(String sub_bss_order_id) {
		this.sub_bss_order_id = sub_bss_order_id;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

}

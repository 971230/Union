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

@RequestBeanAnnontion(primary_keys="tm_resource_id",table_name="es_attr_Terminal_ext",depency_keys="order_id",service_desc="终端资源信息")
public class AttrTmResourceInfoBusiRequest extends ZteBusiRequest<ZteResponse>  implements IZteBusiRequestHander{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@RequestFieldAnnontion(dname="resources_type")	
	private String resources_type;
	@RequestFieldAnnontion(dname="resources_code")
	private String resources_code;
	@RequestFieldAnnontion(dname="rscstate_code")
	private String rscstate_code;
	@RequestFieldAnnontion(dname="rscstate_desc")
	private String rscstate_desc;	
	@RequestFieldAnnontion(dname="sale_price")
	private String sale_price;
	@RequestFieldAnnontion(dname="c_cost")
	private String c_cost;
	@RequestFieldAnnontion(dname="card_price")
	private String card_price;
	@RequestFieldAnnontion(dname="reserva_price")
	private String reserva_price;	
	@RequestFieldAnnontion(dname="product_activity_info")
	private String product_activity_info;
	@RequestFieldAnnontion(dname="resources_brand_code")
	private String resources_brand_code;
	@RequestFieldAnnontion(dname="org_device_brand_code")
	private String org_device_brand_code;
	@RequestFieldAnnontion(dname="resources_brand_name")
	private String resources_brand_name;
	@RequestFieldAnnontion(dname="resources_model_code")
	private String resources_model_code;
	@RequestFieldAnnontion(dname="resources_model_name")
	private String resources_model_name;
	@RequestFieldAnnontion(dname="resources_src_code")
	private String resources_src_code;
	@RequestFieldAnnontion(dname="resources_src_name")
	private String resources_src_name;
	@RequestFieldAnnontion(dname="resources_supply_corp")
	private String resources_supply_corp;
	@RequestFieldAnnontion(dname="resources_service_corp")
	private String resources_service_corp;
	@RequestFieldAnnontion(dname="resources_color")
	private String resources_color;
	@RequestFieldAnnontion(dname="machine_type_code")
	private String machine_type_code;
	@RequestFieldAnnontion(dname="machine_type_name")
	private String machine_type_name;
	@RequestFieldAnnontion(dname="distribution_tag")
	private String distribution_tag;
	@RequestFieldAnnontion(dname="res_rele")
	private String res_rele;
	@RequestFieldAnnontion(dname="terminal_type")
	private String terminal_type;
	@RequestFieldAnnontion(dname="terminal_t_sub_type")
	private String terminal_t_sub_type;
	@RequestFieldAnnontion(dname="service_number")
	private String service_number;
	@RequestFieldAnnontion(dname="para")
	private String para;
	@RequestFieldAnnontion(dname="tm_resource_id")
	private String tm_resource_id;
	@RequestFieldAnnontion(dname="order_id")
	private String order_id;
	@RequestFieldAnnontion()
    private String source_from;
	@RequestFieldAnnontion(dname="occupied_essId")
    private String occupied_essId;
	@RequestFieldAnnontion(dname="operation_status")
    private String operation_status;
	
		
	public String getOccupied_essId() {
		return occupied_essId;
	}

	public void setOccupied_essId(String occupied_essId) {
		this.occupied_essId = occupied_essId;
	}

	public String getOperation_status() {
		return operation_status;
	}

	public void setOperation_status(String operation_status) {
		this.operation_status = operation_status;
	}

	@Override
	public <T> T store() {
		ZteInstUpdateRequest<AttrTmResourceInfoBusiRequest> updateRequest = new ZteInstUpdateRequest<AttrTmResourceInfoBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<AttrTmResourceInfoBusiRequest>> resp = new QueryResponse<List<AttrTmResourceInfoBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery,resp,new ArrayList<AttrTmResourceInfoBusiRequest>());
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

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public String getTm_resource_id() {
		return tm_resource_id;
	}

	public void setTm_resource_id(String tm_resource_id) {
		this.tm_resource_id = tm_resource_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getResources_type() {
		return resources_type;
	}

	public void setResources_type(String resources_type) {
		this.resources_type = resources_type;
	}

	public String getResources_code() {
		return resources_code;
	}

	public void setResources_code(String resources_code) {
		this.resources_code = resources_code;
	}

	public String getRscstate_code() {
		return rscstate_code;
	}

	public void setRscstate_code(String rscstate_code) {
		this.rscstate_code = rscstate_code;
	}

	public String getRscstate_desc() {
		return rscstate_desc;
	}

	public void setRscstate_desc(String rscstate_desc) {
		this.rscstate_desc = rscstate_desc;
	}

	public String getSale_price() {
		return sale_price;
	}

	public void setSale_price(String sale_price) {
		this.sale_price = sale_price;
	}

	public String getC_cost() {
		return c_cost;
	}

	public void setC_cost(String c_cost) {
		this.c_cost = c_cost;
	}

	public String getCard_price() {
		return card_price;
	}

	public void setCard_price(String card_price) {
		this.card_price = card_price;
	}

	public String getReserva_price() {
		return reserva_price;
	}

	public void setReserva_price(String reserva_price) {
		this.reserva_price = reserva_price;
	}

	public String getProduct_activity_info() {
		return product_activity_info;
	}

	public void setProduct_activity_info(String product_activity_info) {
		this.product_activity_info = product_activity_info;
	}

	public String getResources_brand_code() {
		return resources_brand_code;
	}

	public void setResources_brand_code(String resources_brand_code) {
		this.resources_brand_code = resources_brand_code;
	}

	public String getOrg_device_brand_code() {
		return org_device_brand_code;
	}

	public void setOrg_device_brand_code(String org_device_brand_code) {
		this.org_device_brand_code = org_device_brand_code;
	}

	public String getResources_brand_name() {
		return resources_brand_name;
	}

	public void setResources_brand_name(String resources_brand_name) {
		this.resources_brand_name = resources_brand_name;
	}

	public String getResources_model_code() {
		return resources_model_code;
	}

	public void setResources_model_code(String resources_model_code) {
		this.resources_model_code = resources_model_code;
	}

	public String getResources_model_name() {
		return resources_model_name;
	}

	public void setResources_model_name(String resources_model_name) {
		this.resources_model_name = resources_model_name;
	}

	public String getResources_src_code() {
		return resources_src_code;
	}

	public void setResources_src_code(String resources_src_code) {
		this.resources_src_code = resources_src_code;
	}

	public String getResources_src_name() {
		return resources_src_name;
	}

	public void setResources_src_name(String resources_src_name) {
		this.resources_src_name = resources_src_name;
	}

	public String getResources_supply_corp() {
		return resources_supply_corp;
	}

	public void setResources_supply_corp(String resources_supply_corp) {
		this.resources_supply_corp = resources_supply_corp;
	}

	public String getResources_service_corp() {
		return resources_service_corp;
	}

	public void setResources_service_corp(String resources_service_corp) {
		this.resources_service_corp = resources_service_corp;
	}

	public String getResources_color() {
		return resources_color;
	}

	public void setResources_color(String resources_color) {
		this.resources_color = resources_color;
	}

	public String getMachine_type_code() {
		return machine_type_code;
	}

	public void setMachine_type_code(String machine_type_code) {
		this.machine_type_code = machine_type_code;
	}

	public String getMachine_type_name() {
		return machine_type_name;
	}

	public void setMachine_type_name(String machine_type_name) {
		this.machine_type_name = machine_type_name;
	}

	public String getDistribution_tag() {
		return distribution_tag;
	}

	public void setDistribution_tag(String distribution_tag) {
		this.distribution_tag = distribution_tag;
	}

	public String getRes_rele() {
		return res_rele;
	}

	public void setRes_rele(String res_rele) {
		this.res_rele = res_rele;
	}

	public String getTerminal_type() {
		return terminal_type;
	}

	public void setTerminal_type(String terminal_type) {
		this.terminal_type = terminal_type;
	}

	public String getTerminal_t_sub_type() {
		return terminal_t_sub_type;
	}

	public void setTerminal_t_sub_type(String terminal_t_sub_type) {
		this.terminal_t_sub_type = terminal_t_sub_type;
	}

	public String getService_number() {
		return service_number;
	}

	public void setService_number(String service_number) {
		this.service_number = service_number;
	}
}

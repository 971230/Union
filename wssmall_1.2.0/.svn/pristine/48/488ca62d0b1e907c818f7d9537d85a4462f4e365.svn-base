package zte.net.ecsord.params.busi.req;

import java.util.ArrayList;
import java.util.List;

import com.ztesoft.api.ApiRuleException;

import params.ZteBusiRequest;
import params.ZteResponse;
import rule.IZteBusiRequestHander;
import zte.net.common.annontion.context.request.RequestBeanAnnontion;
import zte.net.common.annontion.context.request.RequestFieldAnnontion;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.common.params.req.ZteInstUpdateRequest;
import zte.net.common.params.resp.QueryResponse;
import zte.net.treeInst.RequestStoreExector;

@RequestBeanAnnontion(primary_keys="fee_inst_id",table_name="es_attr_fee_info",depency_keys="order_id",service_desc="费用明细信息")
public class AttrFeeInfoBusiRequest extends ZteBusiRequest<ZteResponse>  implements IZteBusiRequestHander {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion()
	private String fee_inst_id;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String inst_id;
	@RequestFieldAnnontion()
	private String fee_item_code;
	@RequestFieldAnnontion()
	private String fee_item_name;
	@RequestFieldAnnontion()
	private Double o_fee_num;
	@RequestFieldAnnontion()
	private Double discount_fee;
	@RequestFieldAnnontion()
	private String discount_reason;
	@RequestFieldAnnontion()
	private Double n_fee_num;
	@RequestFieldAnnontion()
	private String origin_mall;
	
	@Override
	public <T> T store() {
		ZteInstUpdateRequest<AttrFeeInfoBusiRequest> updateRequest = new ZteInstUpdateRequest<AttrFeeInfoBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<AttrFeeInfoBusiRequest>> resp = new QueryResponse<List<AttrFeeInfoBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery,resp,new ArrayList<AttrFeeInfoBusiRequest>());
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

	public String getFee_inst_id() {
		return fee_inst_id;
	}

	public void setFee_inst_id(String fee_inst_id) {
		this.fee_inst_id = fee_inst_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getInst_id() {
		return inst_id;
	}

	public void setInst_id(String inst_id) {
		this.inst_id = inst_id;
	}

	public String getFee_item_code() {
		return fee_item_code;
	}

	public void setFee_item_code(String fee_item_code) {
		this.fee_item_code = fee_item_code;
	}

	public String getFee_item_name() {
		return fee_item_name;
	}

	public void setFee_item_name(String fee_item_name) {
		this.fee_item_name = fee_item_name;
	}

	public Double getO_fee_num() {
		return o_fee_num;
	}

	public void setO_fee_num(Double o_fee_num) {
		this.o_fee_num = o_fee_num;
	}

	public Double getDiscount_fee() {
		return discount_fee;
	}

	public void setDiscount_fee(Double discount_fee) {
		this.discount_fee = discount_fee;
	}

	public String getDiscount_reason() {
		return discount_reason;
	}

	public void setDiscount_reason(String discount_reason) {
		this.discount_reason = discount_reason;
	}

	public Double getN_fee_num() {
		return n_fee_num;
	}

	public void setN_fee_num(Double n_fee_num) {
		this.n_fee_num = n_fee_num;
	}

	public String getOrigin_mall() {
		return origin_mall;
	}

	public void setOrigin_mall(String origin_mall) {
		this.origin_mall = origin_mall;
	}

}

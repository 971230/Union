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

@RequestBeanAnnontion(primary_keys="discount_inst_id",table_name="es_attr_discount_info",depency_keys="order_id",service_desc="总部优惠信息")
public class AttrDiscountInfoBusiRequest extends ZteBusiRequest<ZteResponse>  implements IZteBusiRequestHander {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion()
	private String discount_inst_id;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String inst_id;
	@RequestFieldAnnontion()
	private String activity_code;
	@RequestFieldAnnontion()
	private String activity_name;
	@RequestFieldAnnontion()
	private String activity_desc;
	@RequestFieldAnnontion()
	private String activity_type;
	@RequestFieldAnnontion()
	private Double discount_range;
	@RequestFieldAnnontion()
	private Double discount_num	;
	@RequestFieldAnnontion()
	private String discount_unit;
	
	@Override
	public <T> T store() {
		ZteInstUpdateRequest<AttrDiscountInfoBusiRequest> updateRequest = new ZteInstUpdateRequest<AttrDiscountInfoBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<AttrDiscountInfoBusiRequest>> resp = new QueryResponse<List<AttrDiscountInfoBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery,resp,new ArrayList<AttrDiscountInfoBusiRequest>());
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

	public String getDiscount_inst_id() {
		return discount_inst_id;
	}

	public void setDiscount_inst_id(String discount_inst_id) {
		this.discount_inst_id = discount_inst_id;
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

	public String getActivity_code() {
		return activity_code;
	}

	public void setActivity_code(String activity_code) {
		this.activity_code = activity_code;
	}

	public String getActivity_name() {
		return activity_name;
	}

	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}

	public String getActivity_desc() {
		return activity_desc;
	}

	public void setActivity_desc(String activity_desc) {
		this.activity_desc = activity_desc;
	}

	public String getActivity_type() {
		return activity_type;
	}

	public void setActivity_type(String activity_type) {
		this.activity_type = activity_type;
	}

	public Double getDiscount_range() {
		return discount_range;
	}

	public void setDiscount_range(Double discount_range) {
		this.discount_range = discount_range;
	}

	public Double getDiscount_num() {
		return discount_num;
	}

	public void setDiscount_num(Double discount_num) {
		this.discount_num = discount_num;
	}

	public String getDiscount_unit() {
		return discount_unit;
	}

	public void setDiscount_unit(String discount_unit) {
		this.discount_unit = discount_unit;
	}

}

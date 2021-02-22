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

@RequestBeanAnnontion(primary_keys="param_inst_id",table_name="es_attr_gift_param",depency_keys="order_id",service_desc="赠品参数信息")
public class AttrGiftParamBusiRequest extends ZteBusiRequest<ZteResponse>  implements IZteBusiRequestHander {
	@RequestFieldAnnontion()
	private String param_inst_id;
	@RequestFieldAnnontion()
	private String gift_inst_id;
	@RequestFieldAnnontion()
	private String param_code;
	@RequestFieldAnnontion()
	private String param_name;
	@RequestFieldAnnontion()
	private String has_value_code;
	@RequestFieldAnnontion()
	private String param_value_code;
	@RequestFieldAnnontion()
	private String param_value;
	@RequestFieldAnnontion()
	private String order_id;
	
	@Override
	public <T> T store() {
		ZteInstUpdateRequest<AttrGiftParamBusiRequest> updateRequest = new ZteInstUpdateRequest<AttrGiftParamBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<AttrGiftParamBusiRequest>> resp = new QueryResponse<List<AttrGiftParamBusiRequest>>();
		return RequestStoreExector.giftParamBusiLoadAssemble(instQuery,resp,new ArrayList<AttrGiftParamBusiRequest>());
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

	public String getParam_inst_id() {
		return param_inst_id;
	}

	public void setParam_inst_id(String param_inst_id) {
		this.param_inst_id = param_inst_id;
	}

	public String getGift_inst_id() {
		return gift_inst_id;
	}

	public void setGift_inst_id(String gift_inst_id) {
		this.gift_inst_id = gift_inst_id;
	}

	public String getParam_code() {
		return param_code;
	}

	public void setParam_code(String param_code) {
		this.param_code = param_code;
	}

	public String getParam_name() {
		return param_name;
	}

	public void setParam_name(String param_name) {
		this.param_name = param_name;
	}

	public String getHas_value_code() {
		return has_value_code;
	}

	public void setHas_value_code(String has_value_code) {
		this.has_value_code = has_value_code;
	}

	public String getParam_value_code() {
		return param_value_code;
	}

	public void setParam_value_code(String param_value_code) {
		this.param_value_code = param_value_code;
	}

	public String getParam_value() {
		return param_value;
	}

	public void setParam_value(String param_value) {
		this.param_value = param_value;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

}

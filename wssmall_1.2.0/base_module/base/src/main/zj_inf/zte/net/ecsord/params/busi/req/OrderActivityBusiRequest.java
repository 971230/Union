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
 * @see 合约信息表
 * 
 */
@RequestBeanAnnontion(primary_keys = "inst_id", depency_keys = "order_id", table_name = "es_order_activity", service_desc = "合约信息表")
public class OrderActivityBusiRequest extends ZteBusiRequest<ZteResponse>implements IZteBusiRequestHander {
	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion(dname = "inst_id", desc = "主键", need_query = "yes")
	private String inst_id;
	@RequestFieldAnnontion(dname = "fuka_inst_id", desc = "es_order_phone_info_fuka表主键", need_query = "no")
	private String fuka_inst_id;
	@RequestFieldAnnontion(dname = "order_id", desc = "订单号", need_query = "no")
	private String order_id	;
	@RequestFieldAnnontion(dname = "activity_type", desc = "合约类型", need_query = "no")
	private String activity_type;
	@RequestFieldAnnontion(dname = "activity_code", desc = "合约编码", need_query = "no")
	private String activity_code;
	@RequestFieldAnnontion(dname = "activity_name", desc = "合约名称", need_query = "no")
	private String activity_name;
	@RequestFieldAnnontion(dname = "act_prot_per", desc = "合约期限", need_query = "no")
	private String act_prot_per;
	@RequestFieldAnnontion(dname = "activity_type_zhufu", desc = "活动类型(区分主副卡)", need_query = "no")
	private String activity_type_zhufu;
	@RequestFieldAnnontion(dname = "source_from", desc = "", need_query = "no")
	private String source_from;

	@Override
	@NotDbField
	public <T> T store() {
		ZteInstUpdateRequest<OrderActivityBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderActivityBusiRequest>();
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<OrderActivityBusiRequest>> resp = new QueryResponse<List<OrderActivityBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery, resp,
				new ArrayList<OrderActivityBusiRequest>());
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

	public String getFuka_inst_id() {
		return fuka_inst_id;
	}

	public void setFuka_inst_id(String fuka_inst_id) {
		this.fuka_inst_id = fuka_inst_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getActivity_type() {
		return activity_type;
	}

	public void setActivity_type(String activity_type) {
		this.activity_type = activity_type;
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

	public String getAct_prot_per() {
		return act_prot_per;
	}

	public void setAct_prot_per(String act_prot_per) {
		this.act_prot_per = act_prot_per;
	}

	public String getActivity_type_zhufu() {
		return activity_type_zhufu;
	}

	public void setActivity_type_zhufu(String activity_type_zhufu) {
		this.activity_type_zhufu = activity_type_zhufu;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
}

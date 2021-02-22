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
 * 
 * @author wu.i 
 * 订单数对象
 * 
 */
@RequestBeanAnnontion(primary_keys="id",depency_keys="order_id", table_name="es_order_route_log",service_desc="订单物流路由明细")
public class OrderRouteLogBusiRequest extends ZteBusiRequest<ZteResponse> implements IZteBusiRequestHander{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2694465777213734707L;
	@RequestFieldAnnontion()
	private String id;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String route_id;
	@RequestFieldAnnontion()
	private String mail_no;
	@RequestFieldAnnontion()
	private String op_code;
	@RequestFieldAnnontion()
	private String route_acceptime;
	@RequestFieldAnnontion()
	private String route_acceptadress;
	@RequestFieldAnnontion()
	private String route_remark;
	@RequestFieldAnnontion()
	private String create_time;
	@RequestFieldAnnontion()
	private String create_user;
	@RequestFieldAnnontion()
	private String source_from;
	@RequestFieldAnnontion()
	private String effect;
	
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	@NotDbField
	public <T>T store() {
		ZteInstUpdateRequest<OrderRouteLogBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderRouteLogBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		//构造参数
		QueryResponse<List<OrderRouteLogBusiRequest>> resp = new QueryResponse<List<OrderRouteLogBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery,resp,new ArrayList<OrderRouteLogBusiRequest>());
	}
	/**
	 * 只插入数据库，不更新缓存，在标准化入库时执行
	 */
	@NotDbField
	public ZteResponse  insertNoCache(){
		return RequestStoreExector.getInstance().insertZteRequestInst(this);
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getRoute_id() {
		return route_id;
	}

	public void setRoute_id(String route_id) {
		this.route_id = route_id;
	}

	public String getMail_no() {
		return mail_no;
	}

	public void setMail_no(String mail_no) {
		this.mail_no = mail_no;
	}

	public String getOp_code() {
		return op_code;
	}

	public void setOp_code(String op_code) {
		this.op_code = op_code;
	}

	public String getRoute_acceptime() {
		return route_acceptime;
	}

	public void setRoute_acceptime(String route_acceptime) {
		this.route_acceptime = route_acceptime;
	}

	public String getRoute_acceptadress() {
		return route_acceptadress;
	}

	public void setRoute_acceptadress(String route_acceptadress) {
		this.route_acceptadress = route_acceptadress;
	}

	public String getRoute_remark() {
		return route_remark;
	}

	public void setRoute_remark(String route_remark) {
		this.route_remark = route_remark;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

		
}

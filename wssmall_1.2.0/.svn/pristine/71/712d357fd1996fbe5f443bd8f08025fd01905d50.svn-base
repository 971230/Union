package zte.net.ecsord.params.busi.req;

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
 * 实名制信息表
 * @author duan.shaochu
 *
 */
@RequestBeanAnnontion(primary_keys = "order_id", table_name = "es_order_realname_info", service_desc = "实名制信息表")
public class OrderRealNameInfoBusiRequest extends ZteBusiRequest<ZteResponse> implements IZteBusiRequestHander {
	
	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String later_active_flag;
	@RequestFieldAnnontion()
	private String active_system;
	@RequestFieldAnnontion()
	private String active_type;
	@RequestFieldAnnontion()
	private String active_flag;
	@RequestFieldAnnontion()
	private String cancel_type;
	@RequestFieldAnnontion()
	private String cancel_flag;
	@RequestFieldAnnontion()
	private String front_pic_flag;
	@RequestFieldAnnontion()
	private String front_pic_desc;
	@RequestFieldAnnontion()
	private String back_pic_flag;
	@RequestFieldAnnontion()
	private String back_pic_desc;
	@RequestFieldAnnontion()
	private String hand_pic_flag;
	@RequestFieldAnnontion()
	private String hand_pic_desc;
	@RequestFieldAnnontion()
	private String cert_pic_flag;
	@RequestFieldAnnontion()
	private String cert_pic_desc;	
	@RequestFieldAnnontion()
	private String active_fail_type;//激活失败原因类型
	@RequestFieldAnnontion()
	private String source_from;

	@Override
	@NotDbField
	public <T> T store() {
		ZteInstUpdateRequest<OrderRealNameInfoBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderRealNameInfoBusiRequest>();
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		// 构造参数
		QueryResponse<OrderRealNameInfoBusiRequest> resp = new QueryResponse<OrderRealNameInfoBusiRequest>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery, resp, this);
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

	public String getLater_active_flag() {
		return later_active_flag;
	}

	public void setLater_active_flag(String later_active_flag) {
		this.later_active_flag = later_active_flag;
	}

	public String getActive_system() {
		return active_system;
	}

	public void setActive_system(String active_system) {
		this.active_system = active_system;
	}

	public String getActive_type() {
		return active_type;
	}

	public void setActive_type(String active_type) {
		this.active_type = active_type;
	}

	public String getActive_flag() {
		return active_flag;
	}

	public void setActive_flag(String active_flag) {
		this.active_flag = active_flag;
	}

	public String getCancel_type() {
		return cancel_type;
	}

	public void setCancel_type(String cancel_type) {
		this.cancel_type = cancel_type;
	}

	public String getCancel_flag() {
		return cancel_flag;
	}

	public void setCancel_flag(String cancel_flag) {
		this.cancel_flag = cancel_flag;
	}

	public String getFront_pic_flag() {
		return front_pic_flag;
	}

	public void setFront_pic_flag(String front_pic_flag) {
		this.front_pic_flag = front_pic_flag;
	}

	public String getFront_pic_desc() {
		return front_pic_desc;
	}

	public void setFront_pic_desc(String front_pic_desc) {
		this.front_pic_desc = front_pic_desc;
	}

	public String getBack_pic_flag() {
		return back_pic_flag;
	}

	public void setBack_pic_flag(String back_pic_flag) {
		this.back_pic_flag = back_pic_flag;
	}

	public String getBack_pic_desc() {
		return back_pic_desc;
	}

	public void setBack_pic_desc(String back_pic_desc) {
		this.back_pic_desc = back_pic_desc;
	}

	public String getHand_pic_flag() {
		return hand_pic_flag;
	}

	public void setHand_pic_flag(String hand_pic_flag) {
		this.hand_pic_flag = hand_pic_flag;
	}

	public String getHand_pic_desc() {
		return hand_pic_desc;
	}

	public void setHand_pic_desc(String hand_pic_desc) {
		this.hand_pic_desc = hand_pic_desc;
	}

	public String getCert_pic_flag() {
		return cert_pic_flag;
	}

	public void setCert_pic_flag(String cert_pic_flag) {
		this.cert_pic_flag = cert_pic_flag;
	}

	public String getCert_pic_desc() {
		return cert_pic_desc;
	}

	public void setCert_pic_desc(String cert_pic_desc) {
		this.cert_pic_desc = cert_pic_desc;
	}

	public String getActive_fail_type() {
		return active_fail_type;
	}

	public void setActive_fail_type(String active_fail_type) {
		this.active_fail_type = active_fail_type;
	}
	
}

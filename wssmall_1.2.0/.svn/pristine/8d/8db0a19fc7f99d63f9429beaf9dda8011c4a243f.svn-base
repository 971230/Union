package rule.params.coqueue.req;

import java.util.Map;

import params.ZteRequest;
import params.ZteResponse;
import zte.params.order.req.OrderCollect;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.model.CoQueue;

import commons.CommonTools;

/**
 * 消息队列入参
 * 
 * @author wu.i
 * 
 */
public class CoQueueRuleReq extends ZteRequest<ZteResponse> {

	private String co_id = ""; //消息队列标识
	private String batch_id = "";  //批次号
	private String service_code = "";  //服务编码
	private String action_code = "";  //操作类型编码,A:新增类；M：修改类；D：删除停用类；
	private String source_from = "";  //数据来源
	private String object_id = "";  //对象标识
	private CoQueue coQueue;  //消息队列对象
	private Map params;  //传入的map参数（冗余）
	
	
	OrderCollect oc;

	public String getCo_id() {
		return co_id;
	}

	public void setCo_id(String co_id) {
		this.co_id = co_id;
	}
	
	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	
	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}
	
	public String getAction_code() {
		return action_code;
	}

	public void setAction_code(String action_code) {
		this.action_code = action_code;
	}
	
	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	
	public String getObject_id() {
		return object_id;
	}

	public void setObject_id(String object_id) {
		this.object_id = object_id;
	}
	
	public CoQueue getCoQueue() {
		return coQueue;
	}

	public void setCoQueue(CoQueue coQueue) {
		this.coQueue = coQueue;
	}
	
	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {

		if (null == coQueue) {
			CommonTools.addFailError("消息队列对象为null!");
		}
		
		if (ApiUtils.isBlank(co_id)) {
			CommonTools.addFailError("消息队列标识【co_id】不能为空!");
        }
		
		if (ApiUtils.isBlank(service_code)) {
			CommonTools.addFailError("服务编码【service_code】不能为空!");
        }
		
		if (ApiUtils.isBlank(source_from)) {
			CommonTools.addFailError("数据来源【source_from】不能为空!");
        }
		
		if (ApiUtils.isBlank(object_id)) {
			CommonTools.addFailError("对象标识【object_id】不能为空!");
        }
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "rule.coqueue.scan";
	}

	public OrderCollect getOc() {
		return oc;
	}

	public void setOc(OrderCollect oc) {
		this.oc = oc;
	}

}

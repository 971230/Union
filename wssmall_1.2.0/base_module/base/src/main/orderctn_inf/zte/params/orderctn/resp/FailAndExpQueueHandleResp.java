package zte.params.orderctn.resp;

import java.util.Map;

import params.ZteResponse;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class FailAndExpQueueHandleResp extends ZteResponse {

	private static final long serialVersionUID = -2666482501625376190L;

	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "N", desc = "0000：成功/其他编码：失败")
	private String resp_code;

	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "N", desc = "结果描述信息")
	private String resp_msg;

	@ZteSoftCommentAnnotationParam(name = "消费类型", type = "String", isNecessary = "N", desc = "d:dubbo调用/m:mq")
	private String rpc_type;

	@ZteSoftCommentAnnotationParam(name = "队列id", type = "String", isNecessary = "Y", desc = "队列id")
	private String base_co_id;

	@ZteSoftCommentAnnotationParam(name = "订单单号", type = "String", isNecessary = "Y", desc = "订单单号")
	private String base_order_id;
	
	@ZteSoftCommentAnnotationParam(name = "返回报文", type = "String", isNecessary = "Y", desc = "返回报文")
	private String respStr;

	@ZteSoftCommentAnnotationParam(name = "返回报文", type = "Map", isNecessary = "Y", desc = "返回报文")
	private Map<String, Object> infOutMap;


	public String getRespStr() {
		return respStr;
	}

	public void setRespStr(String respStr) {
		this.respStr = respStr;
	}

	public Map<String, Object> getInfOutMap() {
		return infOutMap;
	}

	public void setInfOutMap(Map<String, Object> infOutMap) {
		this.infOutMap = infOutMap;
	}

	public String getBase_co_id() {
		return base_co_id;
	}

	public void setBase_co_id(String base_co_id) {
		this.base_co_id = base_co_id;
	}

	public String getBase_order_id() {
		return base_order_id;
	}

	public void setBase_order_id(String base_order_id) {
		this.base_order_id = base_order_id;
	}

	public String getResp_code() {
		return resp_code;
	}

	public void setResp_code(String resp_code) {
		this.resp_code = resp_code;
	}

	public String getResp_msg() {
		return resp_msg;
	}

	public void setResp_msg(String resp_msg) {
		this.resp_msg = resp_msg;
	}

	public String getRpc_type() {
		return rpc_type;
	}

	public void setRpc_type(String rpc_type) {
		this.rpc_type = rpc_type;
	}

}

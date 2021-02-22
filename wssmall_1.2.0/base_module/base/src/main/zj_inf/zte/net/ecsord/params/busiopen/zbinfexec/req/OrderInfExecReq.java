package zte.net.ecsord.params.busiopen.zbinfexec.req;

import java.util.Map;

import params.ZteRequest;
import zte.net.ecsord.params.busiopen.separteflow.resp.OrderSeparteResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.framework.util.StringUtil;

/**
 *  订单接口调用入参
 * @作者 wui
 * @创建日期 2014-11-04
 * @版本 V 1.0
 */
public class OrderInfExecReq extends ZteRequest<OrderSeparteResp> {
	@ZteSoftCommentAnnotationParam(name="外部订单编号",type="String",isNecessary="N",desc="外部订单编号")
	private String 	outer_id;
	
	@ZteSoftCommentAnnotationParam(name="接口编码",type="String",isNecessary="N",desc="接口编码,必填")
	private String op_code;
	
	@ZteSoftCommentAnnotationParam(name="接口名称",type="String",isNecessary="N",desc="接口名称，非必填")
	private String op_name;
	
	@ZteSoftCommentAnnotationParam(name="接口名称",type="String",isNecessary="N",desc="接口名称，非必填")
	private String op_req;
	
	@ZteSoftCommentAnnotationParam(name="接口名称",type="String",isNecessary="N",desc="接口名称，非必填")
	private String op_rsp;
	
	@ZteSoftCommentAnnotationParam(name="接口平台",type="String",isNecessary="N",desc="接口平台，总部：ZB")
	private String plan_type;
	
	@ZteSoftCommentAnnotationParam(name="接口调用地址",type="String",isNecessary="N",desc="接口调用地址")
	private String ip_address;
	
	@ZteSoftCommentAnnotationParam(name="接口报文内容",type="String",isNecessary="N",desc="接口报文内容,按JSON格式")
	private String param_desc;
	
	private String apiMethodName;
	
	@ZteSoftCommentAnnotationParam(name="扩展参数",type="String",isNecessary="N",desc="扩展参数")
	private Map extParams;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
	
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		if(StringUtil.isEmpty(apiMethodName) || "zte.net.infservice.order.busi.zbinfexec.orderinfexec".equals(apiMethodName)){
			return "zte.net.infservice.order.busi.zbinfexec.orderinfexec";
		}else{
			return "zte.net.infservice.order.busi.zbinfexec.orderinfexec2";
		}
	}

	public String getOuter_id() {
		return outer_id;
	}

	public void setOuter_id(String outer_id) {
		this.outer_id = outer_id;
	}

	public String getOp_code() {
		return op_code;
	}

	public void setOp_code(String op_code) {
		this.op_code = op_code;
	}

	public String getOp_name() {
		return op_name;
	}

	public void setOp_name(String op_name) {
		this.op_name = op_name;
	}

	public String getOp_req() {
		return op_req;
	}

	public void setOp_req(String op_req) {
		this.op_req = op_req;
	}

	public String getOp_rsp() {
		return op_rsp;
	}

	public void setOp_rsp(String op_rsp) {
		this.op_rsp = op_rsp;
	}

	public String getPlan_type() {
		return plan_type;
	}

	public void setPlan_type(String plan_type) {
		this.plan_type = plan_type;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public String getParam_desc() {
		return param_desc;
	}

	public void setParam_desc(String param_desc) {
		this.param_desc = param_desc;
	}

	public Map getExtParams() {
		return extParams;
	}

	public void setExtParams(Map extParams) {
		this.extParams = extParams;
	}

	public void setApiMethodName(String apiMethodName) {
		this.apiMethodName = apiMethodName;
	}
}

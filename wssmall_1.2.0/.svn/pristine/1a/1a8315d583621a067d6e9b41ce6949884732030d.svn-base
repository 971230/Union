package zte.net.ecsord.params.busiopen.separteflow.req;

import java.util.Map;

import params.ZteRequest;
import zte.net.ecsord.params.busiopen.separteflow.resp.OrderSeparteResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.framework.util.StringUtil;

/**
 *  订单分流
 * @作者 wui
 * @创建日期 2014-11-04
 * @版本 V 1.0
 */
public class OrderSeparteReq extends ZteRequest<OrderSeparteResp> {
	@ZteSoftCommentAnnotationParam(name="外部订单编号",type="String",isNecessary="N",desc="外部订单编号")
	private String 	outer_id;
	@ZteSoftCommentAnnotationParam(name="接口编码",type="String",isNecessary="N",desc="接口编码)")
	private String op_code;
	@ZteSoftCommentAnnotationParam(name="接口名称",type="String",isNecessary="N",desc="接口名称")
	private String op_name;
	
	@ZteSoftCommentAnnotationParam(name="扩展参数",type="String",isNecessary="N",desc="扩展参数")
	private Map extParams;
	
	private String apiMethodName; 

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
	
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		if(StringUtil.isEmpty(apiMethodName) || "zte.net.infservice.order.busi.separteflow.ordersepartereq".equals(apiMethodName)){
			return "zte.net.infservice.order.busi.separteflow.ordersepartereq";
		}else{
			return "zte.net.infservice.order.busi.separteflow.ordersepartereq2";
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

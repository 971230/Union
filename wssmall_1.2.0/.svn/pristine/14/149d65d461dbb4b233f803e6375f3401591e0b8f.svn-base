package zte.net.ord.params.busi.req;

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
 * @Description 接口请求头部业务对象
 * @author  zhangJun
 * @date    2015-4-10
 * @version 1.0
 */
@RequestBeanAnnontion(primary_keys="inf_head_id", cache_keys="order_id,co_id",table_name="es_inf_head",table_archive = "yes", cache_store = "yes",service_desc="接口请求头部")
public class InfHeadBusiRequest extends ZteBusiRequest<ZteResponse>  implements IZteBusiRequestHander {
	
	private static final long serialVersionUID = 1L;

	@RequestFieldAnnontion(desc="id")
	String inf_head_id;
	@RequestFieldAnnontion(desc="接口服务名")
	String process_code;
	@RequestFieldAnnontion(desc="验证码")
	String verify_code;
	@RequestFieldAnnontion(desc="请求类型")
	String request_type;
	@RequestFieldAnnontion(desc="功能代码")
	String sysfunc_id;
	@RequestFieldAnnontion(desc="操作员工号")
	String operator_id;
	@RequestFieldAnnontion(desc="工号所属组织")
	String organ_id;
	@RequestFieldAnnontion(desc="类型")
	String route_type;
	@RequestFieldAnnontion(desc="路由地市或手机号")
	String route_value;
	@RequestFieldAnnontion(desc="请求时间")
	String request_time;
	@RequestFieldAnnontion(desc="请求流水号")
	String request_seq;
	@RequestFieldAnnontion(desc="请求来源")
	String request_source;
	@RequestFieldAnnontion(desc="请求目标")
	String request_target;
	@RequestFieldAnnontion(desc="版本")
	String msg_version;
	@RequestFieldAnnontion(desc="版本")
	String cont_version;
	@RequestFieldAnnontion(desc="用户密码")
	String user_passwd;
	@RequestFieldAnnontion(desc="ip")
	String operator_ip;
	@RequestFieldAnnontion(desc="手机号码")
	String login_msisdn;
	@RequestFieldAnnontion(desc="应用id")
	String app_id;
	@RequestFieldAnnontion(desc="令牌")
	String access_token;
	@RequestFieldAnnontion(desc="数字签名")
	String sign;
	
	
	@RequestFieldAnnontion(desc="模板编码")
	String template_code;
	@RequestFieldAnnontion(desc="模板版本号")
	String template_version;
	
	
	@RequestFieldAnnontion(desc="订单id")
	String order_id;
	@RequestFieldAnnontion(desc="订单来源")
	String source_from;
	
	@RequestFieldAnnontion(desc="队列id")
	String co_id;
	

	@Override
	@NotDbField
	public <T> T store() {
		ZteInstUpdateRequest<InfHeadBusiRequest> updateRequest = new ZteInstUpdateRequest<InfHeadBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		//构造参数
		QueryResponse<InfHeadBusiRequest> resp = new QueryResponse<InfHeadBusiRequest>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery,resp,this);
	}
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProcess_code() {
		return process_code;
	}

	public void setProcess_code(String process_code) {
		this.process_code = process_code;
	}

	public String getVerify_code() {
		return verify_code;
	}

	public void setVerify_code(String verify_code) {
		this.verify_code = verify_code;
	}

	public String getRequest_type() {
		return request_type;
	}

	public void setRequest_type(String request_type) {
		this.request_type = request_type;
	}

	public String getSysfunc_id() {
		return sysfunc_id;
	}

	public void setSysfunc_id(String sysfunc_id) {
		this.sysfunc_id = sysfunc_id;
	}

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getOrgan_id() {
		return organ_id;
	}

	public void setOrgan_id(String organ_id) {
		this.organ_id = organ_id;
	}

	public String getRoute_type() {
		return route_type;
	}

	public void setRoute_type(String route_type) {
		this.route_type = route_type;
	}

	public String getRoute_value() {
		return route_value;
	}

	public void setRoute_value(String route_value) {
		this.route_value = route_value;
	}

	public String getRequest_time() {
		return request_time;
	}

	public void setRequest_time(String request_time) {
		this.request_time = request_time;
	}

	public String getRequest_seq() {
		return request_seq;
	}

	public void setRequest_seq(String request_seq) {
		this.request_seq = request_seq;
	}

	public String getRequest_source() {
		return request_source;
	}

	public void setRequest_source(String request_source) {
		this.request_source = request_source;
	}

	public String getRequest_target() {
		return request_target;
	}

	public void setRequest_target(String request_target) {
		this.request_target = request_target;
	}

	public String getMsg_version() {
		return msg_version;
	}

	public void setMsg_version(String msg_version) {
		this.msg_version = msg_version;
	}

	public String getCont_version() {
		return cont_version;
	}

	public void setCont_version(String cont_version) {
		this.cont_version = cont_version;
	}

	public String getUser_passwd() {
		return user_passwd;
	}

	public void setUser_passwd(String user_passwd) {
		this.user_passwd = user_passwd;
	}

	public String getOperator_ip() {
		return operator_ip;
	}

	public void setOperator_ip(String operator_ip) {
		this.operator_ip = operator_ip;
	}

	public String getLogin_msisdn() {
		return login_msisdn;
	}

	public void setLogin_msisdn(String login_msisdn) {
		this.login_msisdn = login_msisdn;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}


	public String getTemplate_code() {
		return template_code;
	}

	public void setTemplate_code(String template_code) {
		this.template_code = template_code;
	}

	public String getTemplate_version() {
		return template_version;
	}

	public void setTemplate_version(String template_version) {
		this.template_version = template_version;
	}

	public String getInf_head_id() {
		return inf_head_id;
	}

	public void setInf_head_id(String inf_head_id) {
		this.inf_head_id = inf_head_id;
	}
	

	public String getCo_id() {
		return co_id;
	}

	public void setCo_id(String co_id) {
		this.co_id = co_id;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InfHeadBusiRequest [inf_head_id=");
		builder.append(inf_head_id);
		builder.append(", process_code=");
		builder.append(process_code);
		builder.append(", verify_code=");
		builder.append(verify_code);
		builder.append(", request_type=");
		builder.append(request_type);
		builder.append(", sysfunc_id=");
		builder.append(sysfunc_id);
		builder.append(", operator_id=");
		builder.append(operator_id);
		builder.append(", organ_id=");
		builder.append(organ_id);
		builder.append(", route_type=");
		builder.append(route_type);
		builder.append(", route_value=");
		builder.append(route_value);
		builder.append(", request_time=");
		builder.append(request_time);
		builder.append(", request_seq=");
		builder.append(request_seq);
		builder.append(", request_source=");
		builder.append(request_source);
		builder.append(", request_target=");
		builder.append(request_target);
		builder.append(", msg_version=");
		builder.append(msg_version);
		builder.append(", cont_version=");
		builder.append(cont_version);
		builder.append(", user_passwd=");
		builder.append(user_passwd);
		builder.append(", operator_ip=");
		builder.append(operator_ip);
		builder.append(", login_msisdn=");
		builder.append(login_msisdn);
		builder.append(", app_id=");
		builder.append(app_id);
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append(", sign=");
		builder.append(sign);
		builder.append(", template_code=");
		builder.append(template_code);
		builder.append(", template_version=");
		builder.append(template_version);
		builder.append(", order_id=");
		builder.append(order_id);
		builder.append(", source_from=");
		builder.append(source_from);
		builder.append(", co_id=");
		builder.append(co_id);
		builder.append("]");
		return builder.toString();
	}

}

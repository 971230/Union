package zte.net.ecsord.params.busiopen.ordinfo.req;

import params.ZteError;
import params.ZteRequest;
import zte.net.ecsord.params.busiopen.ordinfo.resp.OrderInfoResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

/**
 * 订单信息查询请求对象
 * 
 * @作者 zhou.qiangang
 * @创建日期 2014-12-11
 * @版本 V 1.0
 */
public class OrderInfoReq extends ZteRequest<OrderInfoResp> {

	private static final long serialVersionUID = 1398858174797767490L;

	/*
	 * @ZteSoftCommentAnnotationParam(name = "访问权限key", type = "String",
	 * isNecessary = "Y", desc = "访问权限key,默认wssmall_ecsord") private String key;
	 * 
	 * @ZteSoftCommentAnnotationParam(name = "访问密码，默认123456", type = "String",
	 * isNecessary = "Y", desc = "访问密码，默认123456") private String sec;
	 * 
	 * @ZteSoftCommentAnnotationParam(name = "请求服务编码：", type = "String",
	 * isNecessary = "Y", desc =
	 * "请求服务编码：zte.net.infservice.order.busi.queryOrderInfo") private String
	 * serv;
	 * 
	 * @ZteSoftCommentAnnotationParam(name = "版本号，默认1.0", type = "String",
	 * isNecessary = "Y", desc = "版本号，默认1.0") private String version;
	 */
	@ZteSoftCommentAnnotationParam(name = "参数类型，默认json", type = "String", isNecessary = "Y", desc = "参数类型，默认json")
	private String type;

	@ZteSoftCommentAnnotationParam(name = "请求参数包体,包体内参数为json字符串", type = "String", isNecessary = "Y", desc = "请求参数包体,包体内参数为json字符串")
	private String req;

	@ZteSoftCommentAnnotationParam(name = "访问流水", type = "String", isNecessary = "Y", desc = "访问流水")
	private String active_no;

	@ZteSoftCommentAnnotationParam(name = "订单来源", type = "String", isNecessary = "N", desc = "订单来源--")
	private String order_origin;

	@ZteSoftCommentAnnotationParam(name = "订单开始时间", type = "String", isNecessary = "N", desc = "用户下单时间,格式YYYY-MM-DD HH:mm:ss")
	private String s_time;

	@ZteSoftCommentAnnotationParam(name = "订单结束时间", type = "String", isNecessary = "N", desc = "用户下单时间,格式YYYY-MM-DD HH:mm:ss")
	private String e_time;

	@ZteSoftCommentAnnotationParam(name = "外部订单号", type = "String", isNecessary = "N", desc = "外部订单号")
	private String out_order_id;

	@ZteSoftCommentAnnotationParam(name = "商品SKU串", type = "String", isNecessary = "N", desc = "多于1个用半角逗号“,”隔开，如：1001,1002,1003")
	private String g_sku_str;

	@ZteSoftCommentAnnotationParam(name = "用户手机号码", type = "String", isNecessary = "N", desc = "用户手机号码")
	private String mobile_tel;

	@ZteSoftCommentAnnotationParam(name = "用户证件号码", type = "String", isNecessary = "N", desc = "用户证件号码")
	private String certi_no;

	@ZteSoftCommentAnnotationParam(name = "第三方系统编码", type = "String", isNecessary = "N", desc = "编码待定")
	private String third_sys_code;

	/**
	 * 指定查询结果,逗号隔开，例如1000,1001,1002…… 约定结果类型： 1000订单基础信息 1001订单支付信息 1002 收获地址信息
	 * 1003 订单配送信息 1004 订单商品包信息 2001 货品信息 2002 赠品信息 2003 开户信息
	 */
	@ZteSoftCommentAnnotationParam(name = "指定查询结果", type = "String", isNecessary = "Y", desc = "指定查询结果")
	private String result_type;

	@ZteSoftCommentAnnotationParam(name = "查询起始页", type = "Integer", isNecessary = "Y", desc = "查询起始页")
	private Integer page_no;

	@ZteSoftCommentAnnotationParam(name = "每页数量", type = "Integer", isNecessary = "Y", desc = "每页数量")
	private Integer page_size;

	@ZteSoftCommentAnnotationParam(name = "api执行方法设置", type = "String", isNecessary = "Y", desc = "api执行方法设置")
	private String api_method;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		StringBuffer isNullInfo = new StringBuffer();
		// StringBuffer verifyInfo = new StringBuffer();

		if (StringUtils.isEmpty(this.getActive_no())) {
			isNullInfo.append("访问流水号【activeNo】,");
		}

		if (0 < isNullInfo.length()) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, isNullInfo.append("不能为空！").toString()));
		}
	}

	public String getActive_no() {
		return active_no;
	}

	public String getApi_method() {
		return api_method;
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return (null == this.api_method || this.api_method.equals("")) ? "zte.net.infservice.order.busi.queryOrderInfo" : this.api_method;
		// return "zte.net.infservice.order.busi.queryOrderInfo";
	}

	public String getCerti_no() {
		return certi_no;
	}

	public String getE_time() {
		return e_time;
	}

	public String getG_sku_str() {
		return g_sku_str;
	}

	public String getMobile_tel() {
		return mobile_tel;
	}

	public String getOrder_origin() {
		return order_origin;
	}

	public String getOut_order_id() {
		return out_order_id;
	}

	public Integer getPage_no() {
		return page_no;
	}

	public Integer getPage_size() {
		return page_size;
	}

	public String getReq() {
		return req;
	}

	public String getResult_type() {
		return result_type;
	}

	public String getS_time() {
		return s_time;
	}

	public String getThird_sys_code() {
		return third_sys_code;
	}

	public String getType() {
		return type;
	}

	public void setActive_no(String active_no) {
		this.active_no = active_no;
	}

	public void setApi_method(String api_method) {
		this.api_method = api_method;
	}

	public void setCerti_no(String certi_no) {
		this.certi_no = certi_no;
	}

	public void setE_time(String e_time) {
		this.e_time = e_time;
	}

	public void setG_sku_str(String g_sku_str) {
		this.g_sku_str = g_sku_str;
	}

	public void setMobile_tel(String mobile_tel) {
		this.mobile_tel = mobile_tel;
	}

	public void setOrder_origin(String order_origin) {
		this.order_origin = order_origin;
	}

	public void setOut_order_id(String out_order_id) {
		this.out_order_id = out_order_id;
	}

	public void setPage_no(Integer page_no) {
		this.page_no = page_no;
	}

	public void setPage_size(Integer page_size) {
		this.page_size = page_size;
	}

	public void setReq(String req) {
		this.req = req;
	}

	public void setResult_type(String result_type) {
		this.result_type = result_type;
	}

	public void setS_time(String s_time) {
		this.s_time = s_time;
	}

	public void setThird_sys_code(String third_sys_code) {
		this.third_sys_code = third_sys_code;
	}

	public void setType(String type) {
		this.type = type;
	}

}

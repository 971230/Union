package zte.net.ecsord.params.busiopen.account.req;

import params.ZteError;
import params.ZteRequest;
import zte.net.ecsord.params.busiopen.account.resp.OrderAKeyActResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

/**
 *  订单一键开户
 * @作者 wui
 * @创建日期 2014-11-04
 * @版本 V 1.0
 */
public class OrderAKeyActReq extends ZteRequest<OrderAKeyActResp> {
	@ZteSoftCommentAnnotationParam(name="外部订单号",type="String",isNecessary="N",desc="外部系统下单生成的订单号(必填)")
	private String 	order_id;
	@ZteSoftCommentAnnotationParam(name="终端串号",type="String",isNecessary="N",desc="终端串号(如果是现场交付的单子为必填项)")
	private String imei;
	@ZteSoftCommentAnnotationParam(name="sim卡iccid号	iccid",type="String",isNecessary="N",desc="SIM卡ICCID号(如果是现场交付的单子为必填项)")
	private String iccid;
	@ZteSoftCommentAnnotationParam(name="action_code",type="String",isNecessary="N",desc="动作")
	private String action_code;
	
	@ZteSoftCommentAnnotationParam(name="change_fields",type="String",isNecessary="N",desc="变更字段")
	private String change_fields; //变更信息，按json数组格式传入 //[{\"field_name\":'phone_num',\"field_desc\":\"开户号码\",field_value:\"\"},{\"field_name\":'development_name',\"field_desc\":\"发展人名称\",field_value:\"\"},{\"field_name\":'development_code',\"field_desc\":\"发展人编码\",field_value:\"\"},{\"field_name\":'bss_operator',\"field_desc\":\"BSS工号\",field_value:\"\"},{\"field_name\":'oss_operator',\"field_desc\":\"订单支撑系统工号\",field_value:\"\"},{\"field_name\":'bss_operator_name',\"field_desc\":\"BSS工号名称\",field_value:\"\"},{\"field_name\":'phone_num',\"field_desc\":\"开户号码\",field_value:\"\"},{\"field_name\":'imei',\"field_desc\":\"串号\",field_value:\"\"}]
	
	@ZteSoftCommentAnnotationParam(name="change_fields",type="String",isNecessary="N",desc="物流信息")
	private String logi_fields; //物流变更信息，按json数组格式传入 //[{\"field_name\":'phone_num',\"field_desc\":\"开户号码\",field_value:\"\"},{\"field_name\":'development_name',\"field_desc\":\"发展人名称\",field_value:\"\"},{\"field_name\":'development_code',\"field_desc\":\"发展人编码\",field_value:\"\"},{\"field_name\":'bss_operator',\"field_desc\":\"BSS工号\",field_value:\"\"},{\"field_name\":'oss_operator',\"field_desc\":\"订单支撑系统工号\",field_value:\"\"},{\"field_name\":'bss_operator_name',\"field_desc\":\"BSS工号名称\",field_value:\"\"},{\"field_name\":'phone_num',\"field_desc\":\"开户号码\",field_value:\"\"},{\"field_name\":'imei',\"field_desc\":\"串号\",field_value:\"\"}]
	/**
	 * [{"field_desc":"应收运费(厘)","field_name":"post_fee","field_value":""}, 
		{"field_desc":"实收运费(厘)","field_name":"n_shipping_amount","field_value":""},
		{"field_desc":"物流公司编码","field_name":"shipping_company","field_value":""},
		{"field_desc":"物流公司名称","field_name":"logi_company","field_value":""},
		{"field_desc":"是否闪电送","field_name":"shipping_quick","field_value":""},
		{"field_desc":"配送方式","field_name":"sending_type","field_value":""},
		{"field_desc":"配送时间","field_name":"shipping_time","field_value":""},
		{"field_desc":"收货人姓名","field_name":"ship_name","field_value":""},
		{"field_desc":"收货省份","field_name":"provinc_code","field_value":""},
		{"field_desc":"收货地市","field_name":"city_code","field_value":""},
		{"field_desc":"收货区县","field_name":"district_id","field_value":""},
		{"field_desc":"收货商圈","field_name":"ship_area","field_value":""},
		{"field_desc":"详细地址","field_name":"ship_addr","field_value":""},
		{"field_desc":"邮政编码","field_name":"ship_zip","field_value":""},
		{"field_desc":"固定电话","field_name":"reference_phone","field_value":""},
		{"field_desc":"手机号码","field_name":"receiver_mobile","field_value":""},
		{"field_desc":"电子邮件","field_name":"ship_email","field_value":""}]
	 * @return
	 */
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public String getAction_code() {
		return action_code;
	}
	public void setAction_code(String action_code) {
		this.action_code = action_code;
	}
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if (StringUtils.isEmpty(order_id)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "订单号【order_id】不能为空！"));
        }
//		if (StringUtils.isEmpty(imei)) {
//			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "终端串号【imei】不能为空！"));
//        }
//		if (StringUtils.isEmpty(iccid)) {
//			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "【iccid】不能为空！"));
//        }
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.infservice.order.busi.akeyact";
	}
	
	public static void main(String[] args) {
//		OrderAKeyActReq req = new OrderAKeyActReq();
//		DefaultZteRopClient client = new DefaultZteRopClient("http://订单系统开放ip:端口/router","wssmall_ecsord","123456","1.0");
//		OrderAKeyActResp resp= client.execute(req, OrderAKeyActResp.class);
		
	}
	public String getChange_fields() {
		return change_fields;
	}
	public void setChange_fields(String change_fields) {
		this.change_fields = change_fields;
	}
	public String getLogi_fields() {
		return logi_fields;
	}
	public void setLogi_fields(String logi_fields) {
		this.logi_fields = logi_fields;
	}
}

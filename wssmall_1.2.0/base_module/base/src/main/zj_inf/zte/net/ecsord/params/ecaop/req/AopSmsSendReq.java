package zte.net.ecsord.params.ecaop.req;

import java.util.List;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

import params.ZteRequest;
import zte.net.ecsord.params.workCustom.po.ES_WORK_SMS_SEND;

public class AopSmsSendReq extends ZteRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1392895428378086578L;
	@ZteSoftCommentAnnotationParam(name="服务号码",type="String",isNecessary="Y",desc="服务号码")
	private String service_num;
	@ZteSoftCommentAnnotationParam(name="短信内容",type="String",isNecessary="Y",desc="短信内容")
	private String sms_data;
	@ZteSoftCommentAnnotationParam(name="短信发送号",type="String",isNecessary="Y",desc="短信发送号")
	private String bill_num;
	@ZteSoftCommentAnnotationParam(name="0：短内容 1：长内容",type="String",isNecessary="Y",desc="0：短内容 1：长内容")
	private String sms_flag;
	
	@ZteSoftCommentAnnotationParam(name="发送类型",type="String",isNecessary="Y",desc="发送类型")
	private String send_type;
	

	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="订单编号")
	private String order_id;
	
	
	@ZteSoftCommentAnnotationParam(name="批量发送",type="String",isNecessary="Y",desc="发送类型")
	private List<ES_WORK_SMS_SEND> listpojo;
	

	public List<ES_WORK_SMS_SEND> getListpojo() {
		return listpojo;
	}

	public void setListpojo(List<ES_WORK_SMS_SEND> listpojo) {
		this.listpojo = listpojo;
	}

	public String getSend_type() {
		return send_type;
	}

	public void setSend_type(String send_type) {
		this.send_type = send_type;
	}

	
	@Override
	public void check() throws ApiRuleException {

	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.zb.sendSmsZb";
	}

	public String getService_num() {
		return service_num;
	}

	public void setService_num(String service_num) {
		this.service_num = service_num;
	}

	public String getSms_data() {
		return sms_data;
	}

	public void setSms_data(String sms_data) {
		this.sms_data = sms_data;
	}

	public String getBill_num() {
		return bill_num;
	}

	public void setBill_num(String bill_num) {
		this.bill_num = bill_num;
	}

	public String getSms_flag() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String smsflag = cacheUtil.getConfigInfo("sms_flag");
		if(!StringUtil.isEmpty(smsflag)){
			sms_flag = smsflag;
		}
		return sms_flag;
	}

	public void setSms_flag(String sms_flag) {
		this.sms_flag = sms_flag;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	
}

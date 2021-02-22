package zte.net.card.params.req;

import params.ZteError;
import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import commons.CommonTools;
import consts.ConstsCore;

/**
 * @Description写卡
 * @author  zhangJun
 * @date    2017年3月7日
 * @version 1.0
 */
public class WriteCardReq extends ZteRequest<ZteResponse> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	@ZteSoftCommentAnnotationParam(name = "订单id", type = "String", isNecessary = "Y", desc = "订单id")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name = "写卡机编码", type = "String", isNecessary = "Y", desc = "写卡机编码")
	private String machine_no;
	@ZteSoftCommentAnnotationParam(name = "iccid", type = "String", isNecessary = "Y", desc = "iccid")
	private String iccid;
	@ZteSoftCommentAnnotationParam(name = "imsi", type = "String", isNecessary = "Y", desc = "imsi")
	private String imsi;
	@ZteSoftCommentAnnotationParam(name = "写卡脚本", type = "String", isNecessary = "Y", desc = "写卡脚本")
	private String option;
	@ZteSoftCommentAnnotationParam(name = "手机号码", type = "String", isNecessary = "Y", desc = "手机号码")
	private String phone_num;
	

	
	
	

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if (StringUtils.isEmpty(order_id)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "订单id【order_id】不能为空！"));
        }
		if (StringUtils.isEmpty(machine_no)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "写卡机编码【machine_no】不能为空！"));
        }
		if (StringUtils.isEmpty(iccid)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "【iccid】不能为空！"));
        }
		if (StringUtils.isEmpty(imsi)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "【imsi】不能为空！"));
        }
		if (StringUtils.isEmpty(option)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "写卡脚本【option】不能为空！"));
        }
		if (StringUtils.isEmpty(phone_num)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "手机号码【phone_num】不能为空！"));
        }
		if(phone_num.length()!=11){
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "手机号码【phone_num】必须是11位！"));
		}
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.card.writeCard";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getMachine_no() {
		return machine_no;
	}

	public void setMachine_no(String machine_no) {
		this.machine_no = machine_no;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	
	
}

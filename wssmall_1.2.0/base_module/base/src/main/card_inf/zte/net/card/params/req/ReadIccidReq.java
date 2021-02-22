package zte.net.card.params.req;

import params.ZteError;
import params.ZteRequest;
import zte.net.card.params.resp.ReadIccidResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import commons.CommonTools;
import consts.ConstsCore;

/**
 * 订单模块请求写卡机dubbo服务读取ICCID值。
 * @Description
 * @author  zhangJun
 * @date    2017年3月2日
 * @version 1.0
 */
public class ReadIccidReq extends ZteRequest<ReadIccidResp> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "订单id", type = "String", isNecessary = "Y", desc = "订单id")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name = "写卡机编码", type = "String", isNecessary = "N", desc = "写卡机编码")
	private String machine_no;
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if (StringUtils.isEmpty(order_id)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "订单id【order_id】不能为空！"));
        }
		if (StringUtils.isEmpty(machine_no)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "写卡机编码【machine_no】不能为空！"));
        }
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.card.readIccid";
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
	

}

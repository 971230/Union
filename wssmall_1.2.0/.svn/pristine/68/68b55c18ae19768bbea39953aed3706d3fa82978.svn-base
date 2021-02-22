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
 * @Description 同步队列数据到写卡调度中心
 * @author  zhangJun
 * @date    2017年3月17日
 * @version 1.0
 */
public class WriteQueueReq extends ZteRequest<ZteResponse> {

	private static final long serialVersionUID = 1L;
	
	@ZteSoftCommentAnnotationParam(name = "订单id", type = "String", isNecessary = "Y", desc = "订单id")
	private String order_id;

	@ZteSoftCommentAnnotationParam(name = "写卡机组编码", type = "String", isNecessary = "N", desc = "写卡机组编码")
	private String group_no;
	
	
	
	
	

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if (StringUtils.isEmpty(order_id)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "订单id【order_id】不能为空！"));
        }
		if (StringUtils.isEmpty(group_no)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "写卡机组编码【group_no】不能为空！"));
        }
		
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.card.writeQueue";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getGroup_no() {
		return group_no;
	}

	public void setGroup_no(String group_no) {
		this.group_no = group_no;
	}

	
}

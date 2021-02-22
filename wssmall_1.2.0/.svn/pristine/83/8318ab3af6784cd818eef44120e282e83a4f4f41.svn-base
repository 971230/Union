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
 * 吐卡、回收卡
 * @Description
 * @author  zhangJun
 * @date    2017年3月2日
 * @version 1.0
 */
public class OutCardReq extends ZteRequest<ZteResponse> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String ACTION_TYPE_OUT="out";//吐卡
	public static String ACTION_TYPE_REVERT="revert";//回收卡
	
	
	@ZteSoftCommentAnnotationParam(name = "订单id", type = "String", isNecessary = "Y", desc = "订单id")
	private String order_id;

	@ZteSoftCommentAnnotationParam(name = "写卡机编码", type = "String", isNecessary = "N", desc = "写卡机编码")
	private String machine_no;
	
	@ZteSoftCommentAnnotationParam(name = "出卡类型", type = "String", isNecessary = "N", desc = "out:吐，revert：回收卡")
	private String action_type;

	
	
	

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if (StringUtils.isEmpty(order_id)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "订单id【order_id】不能为空！"));
        }
		if (StringUtils.isEmpty(machine_no)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "写卡机编码【machine_no】不能为空！"));
        }
		if (StringUtils.isEmpty(action_type)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "出卡类型【action_type】不能为空！"));
        }else{
        	if (!action_type.equals(OutCardReq.ACTION_TYPE_OUT)&&!action_type.equals(OutCardReq.ACTION_TYPE_REVERT)) {
    			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "出卡类型【action_type】不正确！"));
            }
        }
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.card.outCard";
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

	public String getAction_type() {
		return action_type;
	}

	public void setAction_type(String action_type) {
		this.action_type = action_type;
	}

	
	
}

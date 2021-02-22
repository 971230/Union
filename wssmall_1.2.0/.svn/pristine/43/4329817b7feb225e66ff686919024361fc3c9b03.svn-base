package params.orderqueue.req;

import params.ZteError;
import params.ZteRequest;
import params.orderqueue.resp.OrderQueueFailSaveResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;


/**
 * @Description 转移mq发送失败的队列消息到失败表
 * @author  zhangJun
 * @date    2015-3-12
 * @version 1.0
 */
public class OrderQueueFailSaveReq extends ZteRequest<OrderQueueFailSaveResp> {
	
	@ZteSoftCommentAnnotationParam(name="队列id",type="String",isNecessary="Y",desc="队列id")
	private String co_id;
	
	@ZteSoftCommentAnnotationParam(name="订单id",type="String",isNecessary="Y",desc="订单id")
	private String order_id;
	
	@ZteSoftCommentAnnotationParam(name="描述",type="String",isNecessary="Y",desc="描述")
	private String deal_desc;
	

	

	@Override
	public void check() throws ApiRuleException {
		if (StringUtils.isEmpty(co_id)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "队列id【co_id】不能为空！"));
        }
		
	}
	
	public String getCo_id() {
		return co_id;
	}

	public void setCo_id(String co_id) {
		this.co_id = co_id;
	}

	@Override
	public String getApiMethodName() {
		return "zte.service.orderqueue.orderQueueFailSave";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getDeal_desc() {
		return deal_desc;
	}

	public void setDeal_desc(String deal_desc) {
		this.deal_desc = deal_desc;
	}

	
	
}

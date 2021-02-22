/**
 * 
 */
package rule.impl;

import java.util.Map;

import params.order.req.OrderExceptionCollectReq;
import params.order.req.OrderHandleLogsReq;
import params.order.resp.OrderExceptionCollectResp;
import rule.IOrdExceptionHandle;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.mq.client.common.DateUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import consts.ConstsCore;

/**
 * @author ZX OrdExceptionHandleImpl.java 2014-10-15
 */
public class OrdExceptionHandleImpl extends BaseSupport implements
		IOrdExceptionHandle {

	/*
	 * (non-Javadoc)
	 * 
	 * @see rule.IOrdExceptionHandle#exceptionHandle(params.order.req.
	 * OrderExceptionCollectReq, params.order.req.OrderHandleLogsReq)
	 */
	@Override
	public OrderExceptionCollectResp exceptionHandle(
			OrderExceptionCollectReq orderExceptionCollectReq,
			OrderHandleLogsReq orderHandleLogsReq) {

		OrderExceptionCollectResp resp = new OrderExceptionCollectResp();

		try {
			baseDaoSupport.insert("es_order_exception_collect",
					orderExceptionCollectReq);
			baseDaoSupport.insert("es_order_handle_logs",
					orderHandleLogsReq);

			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_code("-1");
			resp.setError_msg("失败");
		}
		return resp;
	}

	public OrderExceptionCollectResp exceptionHandleAct(Map params) {
		
		String order_id = (String)params.get("order_id");
		String exception_id = (String)params.get("exception_id");
		String exception_remark = (String)params.get("exception_remark");
		String abnormal_type = (String)params.get("abnormal_type");
		String need_customer_visit = (String)params.get("need_customer_visit");

		OrderExceptionCollectReq orderExceptionCollectReq = new OrderExceptionCollectReq();
		OrderHandleLogsReq orderHandleLogsReq = new OrderHandleLogsReq();

		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance()
				.getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree
				.getOrderExtBusiRequest();
		orderExtBusiRequest.setAbnormal_status("1"); //0正常单，1异常单
		orderExtBusiRequest.setAbnormal_type(abnormal_type);
		orderExtBusiRequest.setException_type(exception_id); // 异常类型
		orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExtBusiRequest.store();
		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest back_order_tree = orderTree.getOrderExtBusiRequest();
		if (back_order_tree != null) {
			orderExceptionCollectReq.setComments(exception_remark);
			orderExceptionCollectReq.setCreate_date(DateUtil
					.current("yyyy-MM-dd HH:mm:ss"));
			orderExceptionCollectReq.setException_id(exception_id);
			orderExceptionCollectReq.setFlow_id(orderExtBusiRequest
					.getFlow_id());
			orderExceptionCollectReq.setFlow_trace_id(orderExtBusiRequest
					.getFlow_trace_id());
			orderExceptionCollectReq.setNeed_customer_visit(need_customer_visit);
			orderExceptionCollectReq.setOrder_id(order_id);

			orderHandleLogsReq.setComments(exception_remark);
			orderHandleLogsReq.setCreate_time(DateUtil
					.current("yyyy-MM-dd HH:mm:ss"));
			orderHandleLogsReq.setFlow_id(orderExtBusiRequest.getFlow_id());
			orderHandleLogsReq.setFlow_trace_id(orderExtBusiRequest
					.getFlow_trace_id());
			orderHandleLogsReq
					.setOp_id(ManagerUtils.getAdminUser().getUserid());
			orderHandleLogsReq.setOp_name(ManagerUtils.getAdminUser()
					.getUsername());
			orderHandleLogsReq.setType_code("1");// 0正常单，1异常单
			orderHandleLogsReq.setHandler_type("exception");
			orderHandleLogsReq.setException_id(exception_id);
			orderHandleLogsReq.setOrder_id(order_id);

		}

		return exceptionHandle(orderExceptionCollectReq, orderHandleLogsReq);
	}
}

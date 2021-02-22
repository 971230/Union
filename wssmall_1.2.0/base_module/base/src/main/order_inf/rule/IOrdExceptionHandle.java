/**
 * 
 */
package rule;

import params.order.req.OrderExceptionCollectReq;
import params.order.req.OrderHandleLogsReq;
import params.order.resp.OrderExceptionCollectResp;

/**
 * @author ZX IOrdExceptionHandle.java 2014-10-15
 */
public interface IOrdExceptionHandle {

	/**
	 * 标记异常
	 * @param orderExceptionCollectReq
	 * @param orderHandleLogsReq
	 * @return
	 */
	OrderExceptionCollectResp exceptionHandle(
			OrderExceptionCollectReq orderExceptionCollectReq,
			OrderHandleLogsReq orderHandleLogsReq);

}

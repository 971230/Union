package zte.net.iservice;

import java.util.List;
import java.util.Map;

import params.ZteResponse;
import params.orderqueue.req.AsynExecuteMsgWriteMqReq;
import params.resp.EsearchAddResp;
import zte.net.ord.params.busi.req.InfHeadBusiRequest;
import zte.net.ord.params.busi.req.InfHeadHisBusiRequest;
import zte.net.ord.params.busi.req.OrderQueueBusiRequest;
import zte.net.ord.params.busi.req.OrderQueueFailBusiRequest;
import zte.net.ord.params.busi.req.OrderQueueHisBusiRequest;
import zte.params.orderctn.req.FailAndExpQueueHandleReq;
import zte.params.orderctn.resp.FailAndExpQueueHandleResp;

/**
 * 
 * @author wanpeng 2015-12-11
 *
 */
public interface IOrderQueueBaseManager {

	/**
	 * 把数据从待发送表转移到失败表->ES_ORDER_QUEUE_FAIL
	 * @param orderQueue 队列信息
	 * @throws Exception
	 */
	public void saveOrderQueueToFail(OrderQueueBusiRequest orderQueue) throws Exception;
	
	/**
	 * 归档报文队列表的数据到历史表->ES_ORDER_QUEUE_HIS
	 * @param orderQueue 队列信息
	 * @throws Exception
	 */
	public void saveOrderQueueToHis(OrderQueueBusiRequest orderQueue) throws Exception;
	
	/**
	 * 获取接口请求头部业务对象
	 * @param order_id 
	 * @param co_id
	 * @return
	 */
	public InfHeadBusiRequest getInfHeadBusiRequest(String order_id, String co_id);
	
	/**
	 * 保存订单队列业务对象
	 * @param orderQueue
	 * @return
	 */
	public OrderQueueBusiRequest saveOrderQueue(OrderQueueBusiRequest orderQueue) throws Exception;
	
	/**
	 * 保存订单队列失败
	 * @param orderQueueFail
	 * @return
	 */
	public OrderQueueFailBusiRequest saveOrderQueuefail(OrderQueueFailBusiRequest orderQueueFail) throws Exception;
	
	/**
	 * 保存订单队列列表
	 * @param orderQueueHis
	 * @return
	 */
	public OrderQueueHisBusiRequest saveOrderQueueHis(OrderQueueHisBusiRequest orderQueueHis) throws Exception;
	
	/**
	 * 保存接口请求头部
	 * @param infHis
	 */
	public InfHeadHisBusiRequest saveOrderInfHeadHis(InfHeadHisBusiRequest infHis);
	
	/**
	 * 删除订单队列业务对象
	 * @param req
	 */
	public void deleteOrderQueueBusiRequest(OrderQueueBusiRequest req);
	
	/**
	 * 删除接口请求头部业务对象
	 * @param req
	 */
	public void deleteInfHeadBusiRequest(InfHeadBusiRequest req);
	
	/**
	 * 删除队列失败记录
	 * @param req
	 */
	public void deleteOrderQueueFailBusiRequest(OrderQueueFailBusiRequest req);
	
	/**
	 * 获取订单队列失败记录
	 * @param max_num 扫描条数
	 * @param handle_sys 主机环境
	 * @return
	 */
	public List<OrderQueueFailBusiRequest> listOrderFailQueue(int max_num, String handle_sys,String queue_type);
	
	/**
	 * 根据队列id，把数据从ES_ORDER_QUEUE_FAIL转移到历史表->ES_ORDER_QUEUE_HIS
	 * @param order_id
	 * @param co_id
	 * @throws Exception
	 */
	public void orderQueueFailMoveToHis(String order_id,String co_id) throws Exception;
	
	/**
	 * 把失败表ES_ORDER_QUEUE_FAIL的数据迁移到在表处理
	 * @param order_id
	 * @param co_id
	 * @throws Exception
	 */
	public void orderQueueFailMoveToQueue(String order_id,String co_id) throws Exception;
	
	/**
	 * 获取失败队列
	 * @param order_id
	 * @param co_id
	 * @return
	 */
	public OrderQueueFailBusiRequest getOrderQueueFail(String order_id, String co_id);
	
	/**
	 * 获取消息队列
	 * @param order_id
	 * @param co_id
	 * @return
	 */
	public OrderQueueBusiRequest getOrderQueue(String order_id, String co_id);
	
	/**
	 * 获取队列历史
	 * @param order_id
	 * @param co_id
	 * @return
	 */
	public OrderQueueHisBusiRequest getOrderQueueHis(String order_id, String co_id);
	
	/**
	 * 获取队列信息，先查询队列失败记录，查询不到获取队列记录
	 * @param order_id
	 * @param co_id
	 * @return
	 */
	public OrderQueueBusiRequest getOrderQueueInfo(String order_id, String co_id);
	
	/**
	 * 获取报文头部信息
	 * @param order_id
	 * @param co_id
	 * @return
	 */
	public InfHeadHisBusiRequest getInfHeadHisBusiRequest(String order_id, String co_id);
	
	/**
	 * 获取报文头部，先查询历史，查询不到，则查询报文头部
	 * @param order_id
	 * @param co_id
	 * @return
	 */
	public InfHeadBusiRequest getInfHeadInfo(String order_id, String co_id);
	
	/**
	 * 更新队列失败表
	 * @param orderQueueFail
	 */
	public void updateOrderQueueFail(Map fields, Map where);
	
	/**
	 * 失败订单重新标准化
	 * @param req
	 * @return
	 */
	public FailAndExpQueueHandleResp failAndExpQueueHandle(FailAndExpQueueHandleReq req) ;
	
	/**
	 * 写文件系统
	 * @param co_id 队列ID
	 * @param in_param 报文入参
	 * @param out_param 返回报文
	 * @return
	 */
	public EsearchAddResp writeEsearch(String co_id, String in_param, String out_param,String out_order_Id);
	
	/**
	 * 
	 * @Description: mq写消息方法 
	 * @param req
	 * @return
	 * @throws Exception   
	 * @author zhouqiangang
	 * @date 2016年1月5日 下午3:46:40
	 */
	public ZteResponse asynExecuteMsgWriteMq(AsynExecuteMsgWriteMqReq req);


}

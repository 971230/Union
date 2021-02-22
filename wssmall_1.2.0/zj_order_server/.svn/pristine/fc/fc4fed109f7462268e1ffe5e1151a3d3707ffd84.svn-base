package com.ztesoft.net.service;

import java.util.List;
import java.util.Map;

import params.req.ZbAuditStatusUpdateReq;
import params.req.ZbCrawlerStatusUpdateReq;
import zte.net.ecsord.params.base.req.HuaShengGoodsReq;
import zte.net.ecsord.params.base.resp.HuaShengGoodsResp;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.busiopen.ordinfo.req.OrderInfoReq;
import zte.net.ecsord.params.busiopen.ordinfo.vo.OrderInfo;
import zte.params.orderctn.resp.OrderCtnResp;

public interface IOrderExtManager {
	/**
	 * 由out_tid获取order_id
	 * @return
	 */
	public String getOrderIdByOutTid(String out_tid);
	
	/**
	 * 由bss_id获取order_id
	 * @return
	 */
	public String getOrderIdByBssId(String out_tid);
	
	/**
	 * 由out_tid获取order_id(历史表)
	 * @return
	 */
	public String getHisOrderIdByOutTid(String out_tid);
	
	/**
	 * 由zb_inf_id获取order_id
	 * @return
	 */
	public String getOrderIdByInfId(String inf_id);
	
	/**
	 * 由vbeln获取order_id
	 * @return
	 */
	public String getOrderIdByVBELN(String vbeln);
	
	/**
	 * 由order_id获取out_order_id
	 * @param order_id
	 * @return
	 */
	public String getOutOrderIdByInfId(String order_id) ;

	/**
	 * 由外部订单号查询配送方式
	 * @param order_id
	 * @param inf_id
	 * @return
	 */
	public String getShippingTypeById(String order_id, String inf_id);
	
	/**
	 * 根据物流单号获取订单数量
	 * @param logi_no
	 * @param order_id
	 * @return
	 */
	public String getOrderCountByLogino(String logi_no,String order_id) ;
	
	/**
	 * 根据条件获取订单信息
	 * @param orderInfoReq
	 * @return
	 */	
	public List<OrderInfo> getOrderInfo(OrderInfoReq orderInfoReq);	
	
	/**
	 * 由out_tid获取order_id、archive_type(历史表)
	 * @return
	 */
	public Map getMapByOutTid(String out_tid);
	
	/**
	 * 由order_id获取archive_type(历史表)
	 * @return
	 */
	public Map getMapByOrderid(String order_id);
	
	/**
	 * 获取华盛商品信息
	 * @param req
	 * @return
	 */
	public HuaShengGoodsResp getHuaShengGoods(HuaShengGoodsReq req);


	/**
	 * 手工录入订单
	 * @param manualOrder
	 * @return
	 */
	public OrderCtnResp saveManualOrder(Map manualOrder,String rpc_type);
	
	/**
	 * 查询审核订单
	 * @return
	 */
	public List queryZbAuditOrders();
	/**
	 * 查询审核成功订单
	 * @return
	 */
	public List queryZbAuditSuccOrders();
	/**
	 * 更新审核状态
	 * @param req
	 */
	public void updateZbAuditStatus(ZbAuditStatusUpdateReq req);
	/**
	 * 更新审核状态
	 * @param req
	 */
	public void updateZbCrawlerStatus(ZbCrawlerStatusUpdateReq req);
	
	public String updateStatus(String order_id,String service_code);
	/**
	 * 添加工单
	 * @param map
	 */
	public String saveWork(Map map);
	/**
	 * <p>Title: changeDevelopmetInfo</p>
	 * <p>Description: 通用接口是否是bss  信息转换</p>
	 * @author songgaofeng
	 * @time 2018年7月18日 上午10:47:10
	 * @version 1.0
	 * @param order_id
	 * @param esOrderQueue 
	 * @param orderTree 
	 * @return
	 */
    public Map<String, String> changeDevelopmetInfo(String order_id, OrderTreeBusiRequest orderTree, List<Map<String, String>> esOrderQueue);

    /**
	 * 押金业务收单
	 * @author wjq
	 * @param map
	 * @param rpc_type 
	 */
    public OrderCtnResp insertManualOrder(Map manualOrder, String rpc_type);
    
    public String getActiveNo(String order_id);
}

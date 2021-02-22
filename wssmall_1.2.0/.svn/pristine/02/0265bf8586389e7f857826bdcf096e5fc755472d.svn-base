package com.ztesoft.net.mall.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import params.order.req.OrderExceptionCollectReq;
import params.order.req.OrderExceptionLogsReq;
import params.order.req.OrderHandleLogsReq;
import params.order.req.OutcallLogsReq;
import zte.params.order.req.OrderExceptionLogsListReq;
import zte.params.order.resp.OrderAttrHanderResp;

import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Comments;
import com.ztesoft.net.mall.core.model.ExtAttr;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsApply;
import com.ztesoft.net.mall.core.model.ItemCard;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderChange;
import com.ztesoft.net.mall.core.model.OrderExcepCollect;
import com.ztesoft.net.mall.core.model.OrderException;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.mall.core.model.OrderLog;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.mall.core.model.OrderQueryParam;
import com.ztesoft.net.mall.core.model.PaymentLog;
import com.ztesoft.net.mall.core.model.ReturnsOrder;
import com.ztesoft.net.mall.core.model.TestRule;
import com.ztesoft.net.model.AttrDef;
import com.ztesoft.net.model.AttrInst;
import com.ztesoft.net.model.AttrTable;

/**
 * 订单管理
 * 
 * @author kingapex 2010-4-6上午11:09:53
 */
public interface IOrderManager {
	
	/**
	 * 修改订单价格
	 * @param price
	 * @param orderid
	 */
	public void savePrice(double price,String orderid);
	
	public void cancel(String orderId);
	
	public void chargebackStackChg(String flag, String zb_order_id);
	
	//根据字段名获取实例数据
	public ItemCard getItemCardByFieldName(String order_id,String field_name,List<ItemCard> itemCards);
	
	//根据字段名获取实例数据
	public ItemCard getItemCardByFieldName(String order_id,String field_name);
	
	public List<ItemCard> getItemCardByOrderId(String order_id);
	
	public void log(String order_id,String message,String op_id,String op_name);
	
	/** 
	 * 保存售后服务订单
	 */
	public void saveAfterServiceOrder(Goods goods,Map<String,String> params);
	
	/**
	 * 创建订单 计算如下业务逻辑：</br> <li>为订单创建唯一的sn(订单号)</li> <li>
	 * 根据sessionid读取购物车计算订商品价格及商品重量，填充以下信息:</br> goods_amount
	 * 商品总额,shipping_amount 配送费用,order_amount 订单总额,weight商品重量,商品数量：goods_num</li>
	 * <li>根据shipping_id(配送方式id)、regionid(收货地区id)及is_protect(是否保价) 计算
	 * protect_price</li> <li>根据payment_id(支付方式id)计算paymoney(支付费用)</li> <li>
	 * 读取当前买家是否为会员或匿名购买并填充member_id字段</li> <li>计算获得积分和消费积分</li>
	 * 
	 * @param order
	 *            订单实体:<br/>
	 *            <li>shipping_id(配送方式id):需要填充用户选择的配送方式id</li> <li>
	 *            regionid(收货地区id)</li> <li>是否保价is_protect</li>
	 *            shipping_area(配送地区):需要填充以下格式数据：北京-北京市-昌平区 </li>
	 * 
	 *            <li>
	 *            payment_id(支付方式id):需要填充为用户选择的支付方式</li>
	 * 
	 *            <li>填充以下收货信息：</br> ship_name(收货人姓名)</br> ship_addr(收货地址)</br>
	 *            ship_zip(收货人邮编)</br> ship_email(收货人邮箱 ) ship_mobile( 收货人手机)
	 *            ship_tel (收货人电话 ) ship_day (送货日期 ) ship_time ( 送货时间 )
	 * 
	 *            </li> <li>remark为买家附言</li>
	 * 
	 * @param sessionid
	 *            会员的sessionid
	 * @throws IllegalStateException 会员尚未登录,不能兑换赠品!           
	 * @return 创建的新订单实体，已经赋予order id
	 */
	public Order add(Order order, String sessionid);
	
	/**
	 * 按供货商提交订单
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-15 
	 * @param order
	 * @param sessionid
	 * @param staff_no
	 * @return
	 */
	public Order add(OrderOuter cp,Member member,Order order, String sessionid,String staff_no);
	
	public List<Map<String,String>> qryStaffNoBySessionID(String sessionID);
	
	public Order addOrder(Order order, GoodsApply goodsApply,OrderOuter orderOuter,String sessionid);
	public void addLog(OrderLog log);
	
	
	//根据字段名获取实例数据
	public List<ItemCard> getItemCardsByFieldName(String field_name,List<ItemCard> itemCards);
	
	
	public void insertCardItem(Order order, OrderItem orderItem,List<ExtAttr> jsonLists,String mapping_type);
	
	
	/**
	 * 修改订单信息
	 * @param order
	 */
	public void edit(Order order);

	/**
	 * 分页读取订单列表
	 * 
	 * @param page
	 *            页数
	 * @param pageSize
	 *            每页显示数量
	 * @param disabled
	 *            是否读回收站列表(1为读取回收站列表,0为读取正常订单列表)
	 * @param searchkey
	 *            搜索项
	 * @param searchValue
	 *            搜索值
	 * @param order
	 *            排序值
	 * @return 订单分页列表对象
	 */
	public Page list(int page, int pageSize, int disabled, String searchkey,
			String searchValue, String order);

	/**
	 * 福建：售后服务申请
	 * @param pageNO
	 * @param pageSize
	 * @param disabled
	 * @param ordParam
	 * @param order
	 * @return
	 */
	public Page listAfterService(int pageNO, int pageSize,String order);
	
	//新列表
	public Page listn(int pageNO, int pageSize,int disabled, OrderQueryParam ordParam, String order);
	
	/**
	 * 大众版
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-12 
	 * @param pageNO
	 * @param pageSize
	 * @param disabled
	 * @param ordParam
	 * @param order
	 * @return
	 */
	public Page listc(int pageNO, int pageSize,int disabled, OrderQueryParam ordParam, String order);
	
	//统计订单数量
	public Map listc(OrderQueryParam ordParam);
	/**
	 * 根据订单id获取订单详细
	 * 
	 * @param orderId
	 *            订单id
	 * @return 订单实体 对象
	 *             当订单不存在时
	 */
	public Order get(String orderId);

	
	
	/**
	 * 根据订单号获取订单
	 * @param ordersn
	 * @return
	 */
	public Order getSn(String ordersn);
	
	
	
	/**
	 * 读取某个订单的商品货物列表
	 * 
	 * @param orderId
	 *            订单id
	 * @return list中为map，对应order_items表
	 */
	public List<Map> listGoodsItems(String orderId);
	/**
	 * 读取某个订单的商品货物列表-历史表
	 * 
	 * @param orderId
	 *            订单id
	 * @return list中为map，对应order_items表
	 */
	public List<Map> listGoodsItemsHis(String orderId);

	public List<Comments> qryCommentByOrderId(String orderId, String object_id);
	
	/**
	 * 读取赠品货物列表
	 * @param orderId
	 * @return 订单赠品对应表order_gift
	 */
	public List<Map> listGiftItems(String orderId);
	
	
	
	/**
	 * 读取某订单的订单日志
	 * 
	 * @param orderId
	 * @return lisgt中为map ，对应order_log表
	 */
	public List listLogs(String orderId);

	/**
	 * 批量将某些订单放入回收站<br>
	 * 
	 * @param orderId
	 *            要删除的订单Id数组
	 */
	public void delete(Integer[] orderId);

	/**
	 * 彻底删除某些订单 <br>
	 * 同时删除以下信息： <li>订单购物项</li> <li>订单日志</li> <li>订单支付、退款数据</li> <li>订单发货、退货数据</li>
	 * 
	 * @param orderId
	 *            要删除的订单Id数组
	 */
	public void clean(Integer[] orderId);

	/**
	 * 批量还原某些订单
	 * 
	 * @param orderId
	 */
	public void revert(Integer[] orderId);

	/**
	 * 列表某会员的订单<br/>
	 * lzf add
	 * 
	 * @param member_id
	 * @return
	 */
	public List listOrderByMemberId(String member_id);
	
	/**
	 * 取某一会员的关于订单的统计信息
	 * @param member_id
	 * @return
	 */
	public Map mapOrderByMemberId(String member_id);

	
	
	/**
	 * 读取某订单的配件发货项
	 * @param orderid
	 * @return
	 */
	public List<Map> listAdjItem(String orderid);
	
	//已废弃，使用CartManager.countPrice
	//public OrderPrice countPrice(String sessionid,Integer shippingid,String regionid,boolean isProtected );
	
	public Order getOrderByTransId(String transactionId);
	
	/**
	 * 统计订单状态
	 * @return key为状态值 ，value为此状态订单的数量
	 */
	public Map  censusState();

	/**
	 * 导出订单为excel
	 * @param start 下单日期范围开始
	 * @param end   下单日期范围结束 
	 * @return 返回导出的excel下载路径
	 */
	public String export(Date start,Date end);
	
	/**
	 * 查询订单状态
	 * @return
	 */
	public List listOrderOption();
	
	/**
	 * 查询订单分销商
	 * @return
	 */
	public List listOrderSource();
	/**
	 * 保存变动日志表
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-13 
	 * @param orderChange
	 */
	public void saveChange(OrderChange orderChange);
	/**
	 * 按batchID查询订单
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-26 
	 * @param batchID
	 * @return
	 */
	public List<Order> getByBatchID(String batchID);
	
	/**
	 * 统计昨日新增订单
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-28 
	 * @return
	 */
	public int countYestodayOrders();
	/**
	 * 统计令日新增订单
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-28 
	 * @return
	 */
	public int countTodayOrders();
	
	public int countOrders(OrderQueryParam ordParam);
	
	public OrderItem getOrderItemByItemId(String itemId);
	
	public void exchangeDeliveryPrice(String order_id,double price);
	
	/**
	 * 商品销售数量
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-9 
	 * @return
	 */
	public int goodsSalseCount(String goods_id,String land_code);
	/**
	 * 商品销售金额
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-9 
	 * @return
	 */
	public double goodsSalseMoney(String goods_id,String land_code);
	
	
	
	/**
	 * 查询N天前没有支付的订单
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-22 
	 * @param beforeDays
	 * @return
	 */
	public List<Order> qryTimeOutOrders(int beforeDays);
	
	public void updateConfirmStaus(String order_id,String confirm_status);
	
	/**
	 * 查询订单组可以查询的订单
	 * @作者 MoChunrun
	 * @创建日期 2013-10-17 
	 * @param pageNO
	 * @param pageSize
	 * @param disabled
	 * @param ordParam
	 * @param order
	 * @return
	 */
	public Page listGroupOrder(int pageNO, int pageSize,int disabled, OrderQueryParam ordParam, String order);
	
	/**
	 * 按流程查看订单
	 * @作者 MoChunrun
	 * @创建日期 2014-6-13 
	 * @param pageNO
	 * @param pageSize
	 * @param disabled
	 * @param ordParam
	 * @param event 0订单处理 1订单监控
	 * @return
	 */
	public Page queryFlowOrder(int pageNO, int pageSize,int disabled, OrderQueryParam ordParam, String order,int event);
	
	public List<PaymentLog> listOrderPayMentLog(String order_id);
	
	/**
	 * 查询购物车的所有商家ID
	 * @author mochunrun
	 */
	
	public List<Map<String,String>> qryGoodsByStaffNo(String staff_no);
	
	/**
	 * 查询未支付的订单支付日志
	 * @作者 MoChunrun
	 * @创建日期 2013-12-2 
	 * @param payType
	 * @param objectId
	 * @return
	 */
	public PaymentLog qryNotPayPaymentLog(String payType,String objectId);
	/**
	 * 按transactionid查询支付日志
	 * @作者 MoChunrun
	 * @创建日期 2013-12-3 
	 * @param transaction_id
	 * @return
	 */
	public PaymentLog qryPaymentLogByTransactionId(String transaction_id);
	
	/**
	 * 查询用户是否在权限组
	 * @作者 MoChunrun
	 * @创建日期 2013-12-20 
	 * @param user_id
	 * @param group_id
	 * @return
	 */
	public List qryGroupByOrder(String user_id,String group_id);
	
	/**
	 * 设置订单操作权限
	 * @作者 MoChunrun
	 * @创建日期 2013-12-20 
	 * @param order
	 */
	public void setOrderSecurity(Order order);
	
	public void updatePaymentMoney(String order_id,String transactionId,double pay_money,String payment_id);
	
	/**
	 * 是否创建订单
	 * @作者 MoChunrun
	 * @创建日期 2014-1-24 
	 * @param buy_type
	 * @param goods_ids
	 * @return
	 */
	public boolean isCreateOrder(String buy_type,String goods_ids);
	
	
	public List<AttrInst> getOuterAttrInst(OrderOuter orderOuter);
	
	public void saveOrderOuter(OrderOuter orderOuter);
	
	public void saveOuterAttrInst(OrderOuter orderOuter);
	
	public List listException();
	
	public void saveOrderFail(OrderExcepCollect orderExcepCollect);
	
	public Page listOrderException(OrderException orderException,int page,int pageSize);
	
	public OrderException qryExceptionById(String exception_id);
	
	public void saveOrderException(OrderException orderException);
	
	public void delOrderException(String exception_id);
	//预约单
	public List<TestRule> qryYuyueList();
	
	public int qryExceptionCount(OrderException orderException);
	
	public List<ReturnsOrder> memberReturnedOrders(String member_id);
	
	/***********
	 * 查询个人订单
	 * @param pageNo
	 * @param pageSize
	 * @param status
	 * @return
	 */
	public Page pageMemberOrders(String member_id,String pageNo, String pageSize, String status);
	
	public OrderOuter getOrderOuterByGoodsId(String product_id ,List<OrderOuter> orderOuters);
	
	public long countOrders(int disabled,OrderQueryParam ordParam,int event);
	
	/**
	 * 根据订单id查询订单关联外系统订单id
	 * @param order_id
	 * @return
	 */
	public OrderOuter qryOrderOuter(String order_id);
	
	public void updateOrderStatus(int status,String order_id);
	
	public void updateOrderAcceptStatus(int accept_status,String order_id);
	
	public int countHasShipOrderItems(String order_id);
	
	/**
	 * 修改异常单状态
	 * @作者 MoChunrun
	 * @创建日期 2014-7-11 
	 * @param order_id
	 * @param exception_status
	 */
	public void updateExceptionStatus(String order_id,int exception_status);
	
	public void updateOrderDisabledStatus(String order_id,int disabled);
	/**
	 * 获取商品的type_code
	 * @作者 MoChunrun
	 * @创建日期 2014-9-25 
	 * @param goods_id
	 * @return
	 */
	public String getGoodsTypeCode(String goods_id);
	
	public void addOrderItem(OrderItem item);
	
	public void delOrderItem(String item_id);
	
	public void updateOrderItemShipNum(String item_id,int ship_num);
	
	public void updateShipStatus(String order_id,int status);
	
	public List<OutcallLogsReq> queryOutcallLogs(String order_id);
	
	public List<OrderHandleLogsReq> queryOrderHandlerLogs(String order_id,String handler_type,String is_his_order);
	
	List<OrderExceptionCollectReq> queryOrderExceptionLogs(OrderExceptionLogsListReq req);
	
	public String getOrderSysCode(String order_id);
	
	//根据外部订单号获取内部订单号---zengxianlian
	public String getOrderIdByOrderOutId(String outId);
	
	/**
	 * 查询订单属性配置信息
	 * @作者 MoChunrun
	 * @创建日期 2014-9-22 
	 * @param space_type 属性类型
	 * @param attr_space_id -999表示通用的属性数据 
	 * @return
	 */
	public List<AttrDef> queryAttrDef(String space_type,String sub_space_type,String attr_space_id);
	
	public List<AttrTable> queryAttrTable(String space_type,String sub_space_type,String attr_space_id);
	
	OrderAttrHanderResp groupAndInsertAttrByTable(int index,List<AttrDef> attrs,Map values,String order_id,String inst_id,String goods_id,String pro_goods_id,String order_from,List<AttrTable> tbList,String goods_pro_id,String product_pro_id);
	

	/**
	 * 获取订单异常信息
	 * @param queryParams
	 * @return
	 */
	public List<OrderExceptionLogsReq> queryOrderExcLogs(Map queryParams);
	
	/**
	 * 根据物流单号获取内部订单ID
	 * @param logi_no
	 * @return
	 */
	public String getOrderIdByLogiNo(String logi_no);
}

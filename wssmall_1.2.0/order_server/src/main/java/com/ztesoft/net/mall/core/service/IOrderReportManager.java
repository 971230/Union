package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.framework.database.ObjectNotFoundException;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Delivery;
import com.ztesoft.net.mall.core.model.DeliveryItem;
import com.ztesoft.net.mall.core.model.PaymentLog;

/**
 * 订单单据管理接口
 * @author kingapex
 *2010-4-11下午01:01:29
 */
public interface IOrderReportManager {
	
	/**
	 * 供应商订单报表表
	 * */
	public Page orderCountList(int pageNo,int pageSize,String startDate,String endDate);
	
	/**
	 * 通过staffId查询此供货商详情
	 * */
	public Page ListOrderWithStaffId(String staffId,int pageNo,int pageSize,String startDate,String endDate);
	
	
	/**
	 * 分页读取收款单<br>
	 * <li>对应表payment_logs</li>
	 * <li>读取字段type值为<b>1</b>的记录</li>
	 * <li>其中字段order_id和order表关联</li>
	 * <li>其中字段member_id和member表关联</li>
	 * 
	 * @param pageNo 当前页码 
	 * @param pageSize 每页分页数量
	 * 
	 * @param order 排序项<br>
	 * 接受如下格式参数：
	 * order_id desc 或 pay_user asc
	 * 默认为 payment_id desc
	 * @return  收款单分页列表，列表中的实体为： PaymentLog实体,<br> 
	 * 其member_name为非数据库字段，用于显示会员用户名<br>
	 * 如果非会员购买此属性为空
	 */
	public Page listPayment(int pageNo,int pageSize,String order,String payment_id,String order_id,String member_name);
	
	
	
	
	
	
	/**
	 * 分页读取退款单<br>
	 * <li>对应表payment_logs</li>
	 * <li>读取字段type值为<b>2</b>的记录</li>
	 * <li>其中字段order_id和order表关联</li>
	 * <li>其中字段member_id和member表关联</li>
	 * @param pageNo 当前页码 
	 * @param pageSize 每页分页数量
	 *	
	 * @param order 排序项<br>
	 * 接受如下格式参数：
	 * order_id desc 或 pay_user asc
	 * 默认为 payment_id desc
	 * 
	 * @return  收款单列表，列表中的实体为：PaymentLog实体,<br> 
	 * 其member_name为非数据库字段，用于显示会员用户名<br>
	 * 如果非会员购买此属性为空
	 */
	public Page listRefund(int pageNo,int pageSize,String order,String payment_id,String order_id,String member_name);
	
	
	
	
	/**
	 * 读取发货单分页列表
	 * <li>对应表delivery</li>
	 * <li>读取字段type值为<b>1</b>的记录</li>
	 * <li>其中字段order_id和order表关联</li>
	 * <li>其中字段member_id和member表关联</li>
	 * 
	 * @param pageNo 当前页码 
	 * @param pageSize 每页分页数量
	 * 
	 *  @param order 排序项<br>
	 * 接受如下格式参数：
	 * order_id desc 或 ship_name asc
	 * 默认为 delivery_id desc
	 * 
	 * @return 发货单分页列表,列表中的实体为：Delivery<br> 
	 * 其member_name为非数据库字段，用于显示会员用户名<br>
	 * 如果非会员购买此属性为空
	 */
	public Page listShipping(int pageNo,int pageSize,String order,String create_type,String order_id, String delivery_id, String member_name);
	
	
	
	
	/**
	 * 读取退货单分页列表
	 * <li>对应表delivery</li>
	 * <li>读取字段type值为<b>2</b>的记录</li>
	 * <li>其中字段order_id和order表关联</li>
	 * <li>其中字段member_id和member表关联</li>
	 * 
	 * @param pageNo 当前页码 
	 * @param pageSize 每页分页数量
	 * 
	 *  @param order 排序项<br>
	 * 接受如下格式参数：
	 * order_id desc 或 ship_name asc
	 * 默认为 delivery_id desc
	 * 
	 * @return 发货单分页列表,列表中的实体为：Delivery<br> 
	 * 其member_name为非数据库字段，用于显示会员用户名<br>
	 * 如果非会员购买此属性为空
	 */
	public Page listReturned(int pageNo,int pageSize,String order,String create_type,String order_id, String delivery_id, String member_name);	
	
	
	
	
	
	/**
	 * 读取收款单/退款单详细
	 * @param paymentId 收款单/退款单id
	 * @return 收款单/退款单实体
	 * @throws ObjectNotFoundException 如果此单据不存在时
	 */
	public PaymentLog getPayment(String paymentId);
	
	
	
	
	
	/**
	 * 读取发货单/退货单详细
	 * @param deliveryId 发货单/退货单id 
	 * @return  发货单/退货单实体
	 * @throws ObjectNotFoundException 如果此单据不存在时
	 */
	public Delivery getDelivery(String deliveryId);
	
	
	
	
	
	/**
	 * 读取读取发货单/退货单明细
	 * @param deliveryId 发货单/退货单id 
	 * @return 发货/退货明细列表
	 */
	public List<DeliveryItem> listDeliveryItem(String deliveryId);
	
	/**
	 * 退付款日志
	 * @param order_id
	 * @param type, 1付款；2退款
	 * @return
	 */
	public List listPayLogs(String order_id, Integer type);
	
	/**
	 * 发退货日志
	 * @param order_id
	 * @param type, 1发货;2退货
	 * @return
	 */
	public List listDelivery(String order_id, Integer type);
	
}

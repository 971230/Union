package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.OrderApply;
import com.ztesoft.net.mall.core.model.OrderApplyItem;
import com.ztesoft.net.mall.core.model.OrderRel;

public interface IOrderApplyManage {
	
	@Deprecated
	public Page listOrderApply(String service_type,String order_apply_id,String a_order_item_id,int pageNo, int pageSize);
	
	/**
	 * @param item_id  子单id
	 * @return
	 */
	@Deprecated
	public OrderApply findOrderApplyByItemId(String item_id);
	@Deprecated
	public Page findOrderApply(int pageNo,int pageSize,String service_type,String user_id);
	@Deprecated
	public int countOrderApply(String service_type,String user_id);
	@Deprecated
	public int sumReturnedItems(String item_id);
	
	public String addOrderApply(OrderApply orderApply);
	public OrderApply getOrderApply(String order_apply_id);
	public void updateOrderApply(OrderApply orderApply);
	/**
	 * 保存子申请表商品项
	 * @作者 MoChunrun
	 * @创建日期 2014-2-18 
	 * @param item
	 */
	public void saveOrderApplyItem(OrderApplyItem item);
	/**
	 * 查询订单申请商品 
	 * @作者 MoChunrun
	 * @创建日期 2014-2-18 
	 * @param appli_id
	 * @param item_type 1订购关系、2退费关系、3换货关系、4退货关系  为空是查询所有类型
	 * @return
	 */
	public List<OrderApplyItem> queryApplyItems(String appli_id,String item_type);
	
	/**
	 * 按申请类型查询订单申请数据
	 * @作者 MoChunrun
	 * @创建日期 2014-2-18 
	 * @param serviceType
	 * @param apply_id
	 * @param order_id
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page queryOrderApplyByType(String serviceType,String apply_id,String order_id,String member_id,int pageNo,int pageSize);

	/**
	 * 添加订单申请关联表
	 * @作者 MoChunrun
	 * @创建日期 2014-2-18 
	 * @param rel
	 */
	public void insertOrderApplyRel(OrderRel rel);
	
	/**
	 * 查询订单申请关系
	 * @作者 MoChunrun
	 * @创建日期 2014-2-20 
	 * @param apply_id
	 * @param service_type
	 * @return 1订购关系、2退费关系、3换货关系、4退货关系  null是查询所有类型
	 */
	public List<OrderRel> queryOrderRelByApplyId(String apply_id,String service_type);
	
	/**
	 * 删除item表
	 * @作者 MoChunrun
	 * @创建日期 2014-2-20 
	 * @param apply_id
	 */
	public void delApplyItems(String apply_id);
	/**
	 * 删除关联表
	 * @作者 MoChunrun
	 * @创建日期 2014-2-20 
	 * @param apply_id
	 */
	public void delApplyOrderRel(String apply_id);
	/**
	 * 查看是否已经存在申请单
	 * @作者 MoChunrun
	 * @创建日期 2014-2-20 
	 * @param apply_id
	 * @param order_id
	 */
	public boolean orderIsOnApply(String order_id);
	
	/**
	 * 修改申请单状态
	 * @作者 MoChunrun
	 * @创建日期 2014-2-20 
	 * @param apply_id
	 * @param status
	 */
	public void updateOrderApplyStatus(String apply_id,String status);
	
	public long countOrderApplyByTypeAndStatus(String service_type,String apply_status);
}

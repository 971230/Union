package com.ztesoft.orderstd.plugin;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ztesoft.net.mall.core.model.Allocation;

/**
 * 订单配货事件
 * @author kingapex
 *
 */
public interface IOrderAllocationEvent {
	
	/**
	 * 激发订单配货事件
	 * 注意：非<b>订单项发货事件(IOrderAllocationItemEvent)</b>
	 * @param allocation 传递配货单
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void onAllocation(Allocation allocation);
	
	
}

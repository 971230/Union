
package com.ztesoft.net.mall.core.service;

import zte.net.common.params.req.ZteInstInsertRequest;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.common.params.req.ZteInstUpdateRequest;
import zte.net.common.params.resp.ZteInstQueryResponse;
import zte.net.common.params.resp.ZteInstUpdateResponse;
import zte.net.ecsord.params.base.resp.ZteBusiResponse;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

/**
 * 订单管理
 * @author wui
 */

public interface IRequestStoreManager   {

	
	
	/**
	 * 获取实例数据
	 * @param instParam
	 * @return
	 */
	public ZteInstQueryResponse getZteRequestInst(ZteInstQueryRequest instParam);
	
	/**
	 * 更新实例数据
	 * @param cache_key
	 * @param zteRequest
	 * @return
	 */
	public ZteInstUpdateResponse updateZteRequestInst(ZteInstUpdateRequest req);
	
	/**
	 * 更新订单树
	 * @param orderTreeBusiRequest
	 */
	public void cacheOrderTree(OrderTreeBusiRequest orderTreeBusiRequest);
	
	
	/**
	 * 更新实例数据
	 * @param cache_key
	 * @param zteRequest
	 * @return
	 */
	public ZteInstUpdateResponse updatePofZteRequestInst(ZteInstUpdateRequest req);
	
	public ZteBusiResponse insertZteRequestInst(ZteInstInsertRequest request);
}

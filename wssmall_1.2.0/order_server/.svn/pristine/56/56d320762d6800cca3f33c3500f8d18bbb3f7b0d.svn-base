package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.app.base.plugin.fileUpload.BatchResult;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.action.order.OrderResult;
import com.ztesoft.net.mall.core.model.CustReturned;


/**
 *  资料返档处理类
 * 
 * @author kingapex 2010-4-6上午11:09:53
 */
public interface ICustReturnedManager {
	
	public void date_returned(OrderRequst orderRequst,OrderResult orderResult,String type);
	
	public Page getCustReturnByBatchId(CustReturned custReturned, int page,int pageSize);
	
	public BatchResult batchReturned(List inList);
}

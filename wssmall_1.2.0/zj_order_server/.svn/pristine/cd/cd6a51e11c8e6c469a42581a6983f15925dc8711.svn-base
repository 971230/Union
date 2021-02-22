package com.ztesoft.net.service;

import javax.servlet.http.HttpServletRequest;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.model.AcceptPrintModel;
import com.ztesoft.net.model.OrderQueryParams;
import com.ztesoft.net.model.PrintOrderTree;

public interface IOrdPrtManager {

	/**
	 * 订单打印列表
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public Page qryPrtOrdList(int pageNo,int pageSize,OrderQueryParams params);
	
	/**
	 * 订单商品单受理单打印表
	 * @param order_id
	 * @return
	 */
	public PrintOrderTree getOrdItemsAptPrtBusiReq(String order_id,boolean isHis);
	
	/**
	 * 订单商品单受理单打印表修改
	 * @param order_id
	 * @param isHis -true 历史单
	 * @return
	 */
	public void updateOrdItemsAptPrtBusiReq(String order_id,boolean isHis);
	
	/**
	 * 获取[es_accept_print_model]打印模板
	 * @param receipt_code
	 * @return
	 */
	public AcceptPrintModel getActPrtMod(String receipt_code,String apply_type);
	
	/**
	 * 根据外部单号去内部单号
	 * @param out_order_id
	 * @return
	 */
	String getOrderId(String out_order_id);
	/**
	 *是新商城系统调用票据打印链接，并且根据outId查询现有表没有记录-则返回true
	 *否则--false
	 * @return
	 */
	public boolean checkIfNeedQueryHis(HttpServletRequest request,String outId);
}

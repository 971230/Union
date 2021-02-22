/**
 * 
 */
package com.ztesoft.net.service;

import java.util.List;
import java.util.Map;

import zte.net.ecsord.params.busi.req.OrderItemsInvPrintsBusiRequest;

import com.ztesoft.net.model.InvoiceDto;
import com.ztesoft.net.model.InvoicePrintInfo;


/**
 * @author ZX IOrderInvoiceManager.java 发票打印管理 2014-11-10
 */
public interface IOrderInvoiceManager {
	
	/**
	 * 打印信息
	 * @param order_id
	 * @return
	 */
	String doPrintInvoice(String order_id,String realname,String order_is_his, String ctx) throws Exception;
	
	/**
	 * 组装打印页面
	 * @param invoiceDtoList
	 * @return
	 */
	String invoicePrint(List<InvoiceDto> invoiceDtoList);
	
	/**
	 * 保存发票打印信息
	 * @param invoicePrintInfo
	 * @return
	 */
	boolean saveInvoicePrintDetail(InvoicePrintInfo invoicePrintInfo,boolean isHis,String realname);
	
	/**
	 * 根据订单ID获取打印信息
	 * @param order_id
	 * @return
	 */
	OrderItemsInvPrintsBusiRequest getPrintReq(String order_id,boolean isHis);
	/**
	 * 查询热免单各个属性的位置参数
	 * @return
	 */
	public Map<String,String> getHotFreeModelParams() ;
	
	/**
	 * 查询指定模板各个属性的位置参数
	 * @param model_id
	 * @return
	 */
	public Map<String,String> getModelParams(String model_id) ;
	
}

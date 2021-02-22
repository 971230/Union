package com.ztesoft.net.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.model.AuditBusinessVO;
import com.ztesoft.net.model.AuditQueryParame;
import com.ztesoft.net.model.MoneyAuditBaseDataVo;
import com.ztesoft.net.model.OrderWarning;


/**
 * @Description 财务稽核
 * @author  zhangJun
 * @date    2016年10月1日
 */
public interface IMoneyAuditManager {
	/**
	 * 订单系统稽核列表
	 * @param AuditQueryParame
	 * @param pageNo
	 * @param pageSize
	 */
	public Page queryList(AuditQueryParame parame,int pageNo, int pageSize) ;
	/**
	 * 订单系统稽核基础数据列表
	 * @param batch_number 根据日期批次号查询订单系统基础数据列表
	 */
	public List<MoneyAuditBaseDataVo> queryBaseDataList(String batch_number) ;
	
	/**
	 * 导入excel
	 */
	public String importacion(File file,String excel_from,String batch_number,String file_name) throws Exception;
	
	/**
	 * 稽核
	 * @param excel_from 稽核的报表类型
	 * @param batch_number 批次号
	 */
	public String auditData(String excel_from,String batch_number);
	/**
	 * 导出excel
	 */
	public String dataExport(AuditQueryParame parame);
	
	/**
	 * 删除导入的excel数据
	 */
	public void delExcle(String excel_from, String batch_number) ;
	

	
	public MoneyAuditBaseDataVo queryOrderListById(String order_id);
	
	public List queryFlowById(String order_id);
	
	public Map updateBusi(String order_id,String audit_bss_id);

	public Map updateOrder(String order_id, Map change);
	
	public List queryListForExp(AuditQueryParame parame,String excel);
	
	public List queryOfflineListForExp(String batch_number);
	public void export(List list, String fileTitle, String[] title,
			String[] content, HttpServletResponse response);
	public List queryMoneyForUDPById(String order_id);
	public List queryMoneyForOrderIdById(String order_id);
	public List queryMoneyForLogiNoById(String order_id);
	
	public Map addFlow(String order_id, Map change);
	
	
	public String auditBusiMoneyByOrderId(String order_id);
	public String  auditReceiveByOrderId(String order_id);
	
	//订单补录导入
	public String importacion_orderInput(File file,String file_name) throws Exception;
}

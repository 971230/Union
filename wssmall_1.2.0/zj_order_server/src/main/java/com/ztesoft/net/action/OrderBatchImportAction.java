package com.ztesoft.net.action;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.annotation.Resource;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.model.OrderBatchImport;
import com.ztesoft.net.model.OrderBatchLogiImport;
import com.ztesoft.net.service.IOrderBatchImportManager;

import consts.ConstsCore;
import zte.params.orderctn.resp.OrderCtnResp;
/**
 * 订单批量导入Action
 * @author MCC
 *
 */
public class OrderBatchImportAction extends WWAction {
	@Resource
	IOrderBatchImportManager orderBatchImportManager;
	
	private OrderBatchImport orderBatchImport;
	
	private OrderBatchLogiImport orderBatchLogiImport;
	
	public static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private String fileName;//文件名称
	private File file;//文件
	

	/**
	 * 批量导入列表
	 * @return
	 */
	public String batchImportList(){
		if(null == orderBatchImport){
			orderBatchImport = new OrderBatchImport();
			Calendar date = new GregorianCalendar(); 
	    	date.add(Calendar.HOUR, -3);
			String startTime = DF.format(date.getTime());
			String endTime = DF.format(new Date());
			orderBatchImport.setStart_time(startTime);
			orderBatchImport.setEnd_time(endTime);
		}
		this.webpage = orderBatchImportManager.queryBatchImportList(orderBatchImport, this.getPage(), this.getPageSize());
		return "batch_import_order_list";
	}
	
	/**
	 * 物流信息批量导入页面跳转
	 * @author GL
	 * @return
	 */
	public String importOrderLogiMsg() {
		if (null == orderBatchLogiImport) {
			orderBatchLogiImport = new OrderBatchLogiImport();
			Calendar date = new GregorianCalendar();
			date.add(Calendar.HOUR, -3);
			String startTime = DF.format(date.getTime());
			String endTime = DF.format(new Date());
			orderBatchLogiImport.setStart_time(startTime);
			orderBatchLogiImport.setEnd_time(endTime);
		}
		if (this.page <= 0) {
			this.page = 1;
		}
		this.webpage = orderBatchImportManager.queryBatchLogiList(orderBatchLogiImport, this.page, this.getPageSize());

		return "batch_import_order_logi_excel";
	}

	/**
	 * Excel
	 * @return
	 */
	public String toImportExcel(){
		return "batch_import_order_excel";
	}
	
	
	/**
	 * 导入excel表中的订单
	 * @return
	 */
	public String importExcel(){
		Map<String, String> result = orderBatchImportManager.saveBatchImportOrder(file, fileName);
		if("1".equals(result.get("result_code"))){
			super.json = "{result:1,message:'"+result.get("result_message")+"'}";
		}else if("0".equals(result.get("result_code"))){
			super.json = "{result:0,message:'"+result.get("result_message")+"'}";
		}else{
			super.json = "{result:-1,message:'"+result.get("result_message")+"'}";
		}
		return super.JSON_MESSAGE;
	}
	
	
	/**
	  * 批量更新物流信息 
	 * @author GL
	 * @return
	 * @throws Exception 
	 */
	public String importOrderLogUpdate() throws Exception {
		String resultJson = orderBatchImportManager.saveBatchImportOrderLogi(file,fileName);
		super.json=resultJson;
		return super.JSON_MESSAGE;
	}
	
	
	/**
	 * 批量导入修改页面
	 * @return
	 */
	public String toEditPage(){
		orderBatchImport = orderBatchImportManager.getOrderBatchImport(orderBatchImport.getImport_id());
		return "batch_import_order_edit";
	}
	/**
	 * 导入订单
	 * @return
	 */
	public String importOrder(){
		OrderCtnResp resp = orderBatchImportManager.importOrder(orderBatchImport);
		if(ConstsCore.ERROR_FAIL.equals(resp.getError_code())){
			super.json = "{result:0,message:'"+resp.getError_msg()+"'}";
		}else{
			super.json = "{result:1,message:'导入成功！'}";
		}
		return super.JSON_MESSAGE;
	} 
	
	public OrderBatchImport getOrderBatchImport() {
		return orderBatchImport;
	}

	public void setOrderBatchImport(OrderBatchImport orderBatchImport) {
		this.orderBatchImport = orderBatchImport;
	}

	public OrderBatchLogiImport getOrderBatchLogiImport() {
		return orderBatchLogiImport;
	}

	public void setOrderBatchLogiImport(OrderBatchLogiImport orderBatchLogiImport) {
		this.orderBatchLogiImport = orderBatchLogiImport;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}

package com.ztesoft.net.service;

import java.io.File;
import java.util.Map;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.model.OrderBatchImport;
import com.ztesoft.net.model.OrderBatchLogiImport;

import zte.params.orderctn.resp.OrderCtnResp;

public interface IOrderBatchImportManager {
	
	/**
	 * 订单批量导入
	 * @param file 需要的文件（excel）
	 * @param fileName 文件名称
	 * @return
	 */
	public Map<String, String> saveBatchImportOrder(File file, String fileName);
	
	/**
	 * 根据中文名称获取对应的编码
	 * @param fieldName
	 * @param fieldDesc
	 * @return
	 */
    public String getFieldValue(String fieldName, String fieldDesc);
    
    /**
     * 查询订单批量导入列表
     * @param params
     * @return
     */
    public Page queryBatchImportList(OrderBatchImport params, int pageNo, int pageSize);
    
    /**
     * 获取订单导入数据
     * @param importId
     * @return
     */
    public OrderBatchImport getOrderBatchImport(String importId);
    
    /**
     * 订单导入（单条）
     * @param orderBatchImport
     * @return
     */
    public OrderCtnResp importOrder(OrderBatchImport orderBatchImport);

    /**
     * 文件批量导入物流信息查询
     * @author GL
     * @param orderBatchLogiImport
     * @param page
     * @param pageSize
     * @return
     */
	public Page queryBatchLogiList(OrderBatchLogiImport orderBatchLogiImport, int page, int pageSize);

	/**
	 * 物流信息批量更新 
	 * @author GL
	 * @param file 文件Excel
	 * @param fileName 文件名称
	 * @return
	 */
	public String saveBatchImportOrderLogi(File file, String fileName)  throws Exception ;
}

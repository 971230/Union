package com.ztesoft.net.mall.core.service;


import java.io.File;
import java.util.List;
import java.util.Map;

import zte.params.number.resp.NumberSynInfo;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Numero;


/**
 * 号码分组管理接口
 * 
 * @author cc
 * 
 */
public interface INumeroManager {
	
	/**
	 * 根据号码查询号码基本信息
	 * @param no
	 * @return
	 */
	public Map queryNoInfoByNo(String no);
	
	public String publish(String org_ids,String numbers,String org_id_belong);
	Page getFormList(int page, int pageSize,Map<String,String> params);
	Page getBatchList(int page, int pageSize,Map<String,String> params);
	String listBatchPublish(Map<String,String> params);
	Numero get(String id);
	String importacion(File file, Map<String, String> params);
	List getEstatos();
	List getCiudads();
	List getGrupos();
	List getInternet();
	List getLiberacions();
	void modificar(String ids, String estado,String status,String orderId)  throws Exception;
	void modificar(String ids, Map<String, String> params)  throws Exception;
	
	/**
	 * 通过批次id查询号码同步信息
	 * @param batch_id
	 * @return
	 */
	public List<NumberSynInfo> queryNumberSynInfo(String batch_id);

	public String queryForEstado(String ids) ;
	public List getFirstList(String id);
	
	/**
	 * 查询号码同步日志
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page searchNumeroSynchLogs(Map params,int pageNum,int pageSize);
	
	/**
	 * 查询号码导入日志
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page searchNumeroImportLogsECS(Map params,int pageNum,int pageSize);
	
	/**
	 * 定时任务导入号码
	 */
	public void importNumero();
	
	/**
	 * 批量修改
	 * @param batch_id 导入批次号
	 */
	public void batchModify(String batch_id);
	
	/**
	 * 批量发布
	 * @param batch_id 导入批次号
	 */
	public void batchPublish(String batch_id,String org_ids);
	
	/**
	 * 批量回收-删除es_no_co
	 * @param dn_no
	 */
	public void deleteNoCoData(String dn_no);
	
	public int publishAgain(Map params);
	
	
	public Map getNumberSpec(String dn_no);
}

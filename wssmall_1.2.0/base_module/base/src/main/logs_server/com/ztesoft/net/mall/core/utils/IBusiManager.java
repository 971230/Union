package com.ztesoft.net.mall.core.utils;

import params.ZteBusiRequest;
import zte.net.common.params.req.ZteInstInsertRequest;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.common.params.resp.QueryResponse;
import zte.net.ecsord.params.base.resp.ZteBusiResponse;

public interface IBusiManager  {
	
	
	/**
	 * 实体对象异步查询处理
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public  ZteBusiRequest asyDbQuery(ZteBusiRequest request);
	
	
	/**
	 * 实体对象数据查询
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public  <T> T dbQuery(ZteInstQueryRequest<? extends ZteBusiRequest> instQuery);
	
	
	
	
	/**
	 * 实体对象异步入库处理
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public  ZteBusiRequest asyDbStore(ZteBusiRequest request);
	
	/**
	 * 异步数据库存储操作
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ZteBusiRequest dbStore(ZteBusiRequest request);
	
	
	/**
	 * 异步数据库存储操作
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ZteBusiRequest pofDbStore(ZteBusiRequest request);
	
	
	/**
	 * 实体对象异步入库处理
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public  ZteBusiRequest pofAsyDbStore(ZteBusiRequest request);
	
	/**
	 * 插入对象
	 * @param table_name
	 * @param insertMap
	 */
	public ZteBusiResponse insertZteRequestInst(ZteInstInsertRequest request);
	/**
	 * 从数据库中查询对象
	 * @param request
	 */
	public QueryResponse loadZteBusiRequestByDb(ZteBusiRequest request,Boolean query_his_table,String queryParam);
	/**
     * 插入对象
     * @param table_name
     * @param insertMap
     */
    public ZteBusiResponse insertZteRequestInst(ZteBusiRequest request);
    
}

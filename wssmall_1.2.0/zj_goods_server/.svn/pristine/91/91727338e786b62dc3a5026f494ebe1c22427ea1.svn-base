package com.ztesoft.net.outter.inf.iservice;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.outter.inf.model.OuterError;
import com.ztesoft.net.outter.inf.model.OutterTmpl;
import com.ztesoft.net.outter.inf.model.OutterTmplNew;
import com.ztesoft.net.outter.inf.model.PublicConst;

public interface IOutterECSTmplManager {

	/**
	 * 按订单来源查询订单模板
	 * @作者 MoChunrun
	 * @创建日期 2014-3-12 
	 * @param order_from
	 * @return
	 */
	public OutterTmpl getTmplByOrderFrom(String order_from);
	
	/**
	 * 执行任务完成后修改任务信息
	 * @作者 MoChunrun
	 * @创建日期 2014-3-13 
	 * @param sync_num
	 * @param status
	 * @param execute_min
	 * @param data_end_time
	 * @param error_msg
	 */
	public void updateTmplExecuteInfo(String exe_id,int sync_num,int status,int execute_min,String data_end_time,String error_msg);
	
	/**
	 * 查询所有订单定时任务列表
	 * @作者 MoChunrun
	 * @创建日期 2014-3-13 
	 * @param status -1查询全部 
	 * @return
	 */
	public List<OutterTmpl> listOuterTmpl(int status);
	
	/**
	 * 按外系统状态码查询公共常量
	 * @作者 MoChunrun
	 * @创建日期 2014-3-19 
	 * @param order_from
	 * @param object_type
	 * @param outer_code
	 * @return
	 */
	public PublicConst queryPublicConst(String order_from,String object_type,String outer_code);
	
	/**
	 * 修改运行状态
	 * @作者 MoChunrun
	 * @创建日期 2014-3-24 
	 * @param run_status
	 * @param tmpl_id
	 */
	public void updateRunStatus(String run_status,String tmpl_id);
	
	public void updateRunStatus(String run_status,String msg,int sync_num,String tmpl_id);
	
	public void insertOuterError(OuterError nogoods);
	
	public List<Map> listNotSyncError(String order_from);
	
	public void updateErrorDelFlag(String order_from,String tid,String deal_flag);
	
	public Map getPhoneInfo(String phoneNo);
	
	public void updateOutterOrderId(String updateOutId,String outId);
	
	/**
	 * 定点获取指定任务	es_outer_execute_tmpl_new
	 * @param temp_id
	 * @return
	 */
	public List<OutterTmplNew> listOuterTmplNew(String tmpl_id);
	/**
	 * 修改运行状态	es_outer_execute_tmpl_new
	 * @param run_status
	 * @param tmpl_id
	 */
	public void updateRunStatusNew(String run_status,String tmpl_id);
	
	/**
	 * 任务完成后更新任务信息
	 * @param tmpl
	 */
	public void updateTmplNewInfo(OutterTmplNew tmpl);
}

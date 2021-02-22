package com.ztesoft.net.service;

import java.util.List;
import java.util.Map;

import params.resp.CrawlerProcCondResp;
import zte.net.model.CrawlerConfig;
import zte.net.model.CrawlerProc;

import com.ztesoft.net.framework.database.Page;

public interface IOrderCrawlerManager {
	
	//查询配置的进程
	public List<CrawlerProc> queryCrawlerProc();
	
	//保持爬虫配置信息
	public String saveCrawlerInfo(CrawlerConfig crawlerConfig) throws Exception;
	
	//启用/禁用进程
	public String[] startOrForbidProc(String proc_id,String flag);
	
	//添加进程
	public String addProc(CrawlerProc crawlerProxy,String flag);
	
	//查询进程信息
	public CrawlerProc getProcInfo(String proc_id);
	
	//查询进程关联的条件
	public Page queryProcCond(String proc_id,int pageNo,int pageSize);
	
	//添加进程条件
	public String[] addProcCond(List<Map<String, String>> listParms,String proc_id);
	
	//禁用进程条件
	public String[] forbidCond(String cond_id);
	
	//根据进程IP和端口获取条件
	public CrawlerProcCondResp getProcCond(String ip,String port);
	
	//查询爬虫基础信息
	public CrawlerConfig queryCrawlerConfig();
	
	//查询操作配置信息
	public Page queryUrlInfo(int pageNo,int pageSize);
	
	//新增操作配置信息
	public String[] addUrlInfo(List<Map<String,String>> Urllist) throws Exception; 
	
	//删除操作配置信息
	public String[] delUrlConfig(String cuc_id);
	
	//界面刷新将已开启进程的爬虫信息传到爬虫侧
	public String[] refreshInfo();
	
	//根据条件类型获取条件编码
	public List<Map<String, String>>  getCondCode(String condType);
	
	//根据条件编码获取条件值
	public List<Map<String,String>> getCondValueInfo(String condValueCode);
	/**
	 * 审核环节修改总商商品信息
	 * @param order_id
	 * @return
	 */
	public String modifyZbOrderGoodsInfo(String order_id);
	/**
	 * 审核环节修改总商配送信息
	 * @param order_id
	 * @return
	 */
	public String modifyZbOrderPostInfo(String order_id);
	/**
	 * 开户环节修改总商商品信息
	 * @param order_id
	 * @return
	 */
	public String modifyGoodsInfoToZb(String order_id);
	/**
	 * 开户环节修改总商配送信息
	 * @param order_id
	 * @return
	 */
	public String modifyDeliveryInfoToZb(String order_id);
}

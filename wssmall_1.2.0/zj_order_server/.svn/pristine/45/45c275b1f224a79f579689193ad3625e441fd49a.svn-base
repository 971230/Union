package com.ztesoft.net.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.drools.core.util.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import params.ZteResponse;
import params.req.CrawlerDeliveryInfoReq;
import params.req.CrawlerProcCondSettingReq;
import params.req.CrawlerUpdateGoodsInfoReq;
import params.req.CrawlerUpdatePostInfoReq;
import params.req.ModifyOpenAccountGoodsReq;
import params.req.UpdateCrawlerSettingReq;
import params.resp.CrawlerProcCondResp;
import params.resp.CrawlerResp;
import util.Utils;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.utils.AttrUtils;
import zte.net.model.CrawlerConfig;
import zte.net.model.CrawlerProc;
import zte.net.model.CrawlerUrlConfig;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.SequenceTools;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IOrderCrawlerManager;

import commons.CommonTools;
import consts.ConstsCore;

public class OrderCrawlerManager extends BaseSupport implements IOrderCrawlerManager {
	
	/**
	 * 查询配置的进程
	 */
	@Override
	public List<CrawlerProc> queryCrawlerProc() {
		String sql = "select t.* from es_crawler_proc t ";
		List<CrawlerProc> list = this.baseDaoSupport.queryForList(sql, CrawlerProc.class, null);
		return list;
	}

	/**
	 * 保存爬虫配置基础信息
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String saveCrawlerInfo(CrawlerConfig crawlerConfig)throws Exception {
		String cfg_id = crawlerConfig.getCfg_id();
		List params = new ArrayList();
		//判断是否已存在基础数据，若存在则更新，否则新增
		if(StringUtils.isEmpty(cfg_id)){
			crawlerConfig.setCfg_id(SequenceTools.getShort18PrimaryKey());
			this.baseDaoSupport.insert("es_crawler_config", crawlerConfig);
		}else{
			StringBuffer sql_update = new StringBuffer();
			sql_update.append(" update es_crawler_config t set t.address=?,t.port=?,t.is_out_id_check=?,t.proxy_type=?");
			sql_update.append(" ,t.proxy_address=?,t.proxy_port=?,t.proxy_user=?,t.proxy_pwd=?");
			sql_update.append(" where t.cfg_id='"+cfg_id+"' and t.source_from='"+ManagerUtils.getSourceFrom()+"'");
			params.add(crawlerConfig.getAddress());
			params.add(crawlerConfig.getPort());
			params.add(crawlerConfig.getIs_out_id_check());
			params.add(crawlerConfig.getProxy_type());
			
			if(crawlerConfig.getProxy_type().equals("Y")){
				params.add(crawlerConfig.getProxy_address());	
				params.add(crawlerConfig.getProxy_port());
				params.add(crawlerConfig.getProxy_user());
				params.add(crawlerConfig.getProxy_pwd());
			}else{
				params.add("");
				params.add("");
				params.add("");
				params.add("");
			}
			
			this.baseDaoSupport.execute(sql_update.toString(), params.toArray());
			
		}
		
		//调用爬虫修改能力
		String[] result = optionAfterCondUpdate(null,null,"crawler_config",null,null);
		if(result!=null && result.length>=0){
			CommonTools.addFailError(result[1]);
		}
		return "0";
	}

	/**
	 * 启用/禁用进程
	 */
	@Override
	public String[] startOrForbidProc(String proc_id,String flag) {
		if(flag.equals("forbid")){
			String type = "forbid_proc";
			//获取操作配置信息
			String sql_url = "select t.* from es_crawler_url_config t where t.status='0'";
			List<CrawlerUrlConfig> urlList = this.baseDaoSupport.queryForList(sql_url, CrawlerUrlConfig.class, null);
			
			//调用爬虫修改能力，禁用线程
			String[] reqResult = optionAfterCondUpdate(null,proc_id,type,"",urlList);
			if (reqResult!=null && reqResult.length>0) {
				return reqResult;
			}
			//改变进程状态
			String sql = "update es_crawler_proc t set t.status='1' where t.proc_id='"+proc_id+"'";
			this.baseDaoSupport.execute(sql);
		}else if(flag.equals("restart")){
			//开启前先判断该进程下是否有配置条件，若没有则提示先配置条件
			String sql_cond = "select t.* from es_crawler_proc_cond t where t.proc_id=?";
			List list = this.baseDaoSupport.queryForList(sql_cond, proc_id);
			if(list==null || list.size()<=0){
				return new String[]{"1","该进程下没有配置条件，请先配置条件再开启进程!"};
			}
			
			//查询操作配置信息
			String sql_url = "select t.* from es_crawler_url_config t where t.status='0'";
			List<CrawlerUrlConfig> urlList = this.baseDaoSupport.queryForList(sql_url, CrawlerUrlConfig.class, null);
			
			//调用爬虫修改能力，开启线程
			String[] reqResult = optionAfterCondUpdate(null,proc_id,"","",urlList);
			if (reqResult!=null && reqResult.length>0) {
				return reqResult;
			}
			
			String sql = "update es_crawler_proc t set t.status='0' where t.proc_id='"+proc_id+"'";
			this.baseDaoSupport.execute(sql);
		}
		return new String[]{};
	}

	/**
	 * 添加进程
	 */
	@Override
	public String addProc(CrawlerProc crawlerProc,String flag) {
		if(crawlerProc!=null){
			if(flag.equals("add")){
				
				String sql = "select t.* from es_crawler_proc t where t.ip=? and t.port=? and thread_name=?";
				List list = this.baseDaoSupport.queryForList(sql, crawlerProc.getIp(),crawlerProc.getPort(),crawlerProc.getThread_name());
				if(list!=null && list.size()>0){
					return "此进程中已存在该线程名字，请重新命名!";
				}
				
				crawlerProc.setProc_id(SequenceTools.getShort18PrimaryKey());
				crawlerProc.setStatus("1");//0正常，1失效;新增进程默认是禁用的
				this.baseDaoSupport.insert("es_crawler_proc", crawlerProc);
			}else if(flag.equals("edit")){
				String proc_id = crawlerProc.getProc_id()!=null?crawlerProc.getProc_id():"";
				if(StringUtils.isEmpty(proc_id)){
					CommonTools.addFailError("修改进程信息失败！");
				}
				List<String> params = new ArrayList<String>();
				StringBuffer sql = new StringBuffer();
				/*sql.append(" update es_crawler_proc t");
				sql.append(" set t.proc_name='"+crawlerProc.getProc_name()+"',");
				sql.append(" t.proc_code='"+crawlerProc.getProc_code()+"',");
				sql.append(" t.ip='"+crawlerProc.getIp()+"',");
				sql.append(" t.port='"+crawlerProc.getPort()+"',");
				sql.append(" t.thread_name='"+crawlerProc.getThread_name()+"'");
				sql.append(" where proc_id='"+proc_id+"'");*/
				
				sql.append(" update es_crawler_proc t set t.proc_name=?,t.proc_code=?,t.ip=?,t.port=?,t.thread_name=?");
				sql.append(" where proc_id=?");
				
				params.add(crawlerProc.getProc_name());
				params.add(crawlerProc.getProc_code());
				params.add(crawlerProc.getIp());
				params.add(crawlerProc.getPort());
				params.add(crawlerProc.getThread_name());
				params.add(proc_id);
				this.baseDaoSupport.execute(sql.toString(),params);
			}
		}
		return "0";
	}

	/**
	 * 查询进程相关信息
	 */
	@Override
	public CrawlerProc getProcInfo(String proc_id) {
		if(StringUtils.isEmpty(proc_id)){
			proc_id = "";
		}
		String sql = "select t.* from es_crawler_proc t where t.proc_id=?";
		CrawlerProc crawlerProc = (CrawlerProc) this.baseDaoSupport.queryForObject(sql, CrawlerProc.class, proc_id);
		return crawlerProc;
	}

	/**
	 * 查询进程关联的条件
	 */
	@Override
	public Page queryProcCond(String proc_id,int pageNo,int pageSize) {
		String sql = "select t.* from es_crawler_proc_cond t where t.proc_id=? and t.status='0'";//0正常；1失效
		return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, proc_id);
	}

	/**
	 * 添加进程条件
	 */
	@Override
	public String[] addProcCond(List<Map<String, String>> listParams, String proc_id) {
		
		String sql_proc = "select t.status from es_crawler_proc t where t.proc_id=?";
		String status = this.baseDaoSupport.queryForString(sql_proc, proc_id);
		
		if(status.equals("1")){//该线程线程处于禁用状态
			//保存条件
			String[] result = saveCond(listParams,proc_id);
			return result;
			
		}else if(status.equals("0")){//该线程处于启用状态
			
			String sql_url = "select t.* from es_crawler_url_config t where t.status='0'";
			List<CrawlerUrlConfig> urlList = this.baseDaoSupport.queryForList(sql_url, CrawlerUrlConfig.class, null);
			
			//调用爬虫修改能力
			String[] reqResult = optionAfterCondUpdate(listParams,proc_id,"","",urlList);
			if (reqResult!=null && reqResult.length>0) {
				return reqResult;
			}
			
			//保存条件(写库表)
			String[] insertResult = saveCond(listParams,proc_id);
			return insertResult;
		}
		
		return new String[]{};
	}

	/**
	 * 禁用进程条件
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String[] forbidCond(String cond_id) {
		String type ="forbid_cond";
		
		String sql_qry = "select b.proc_id,b.status from es_crawler_proc_cond a,es_crawler_proc b where a.cond_id=? and b.proc_id=a.proc_id and a.source_from='ECS'";
		Map<String,String> map = this.baseDaoSupport.queryForMap(sql_qry, cond_id);
		String status = map.get("status"); 
		String proc_id = map.get("proc_id")!=null?map.get("proc_id").toString():"";
		if(!StringUtils.isEmpty(status)){
			if(status.equals("0")){//开启状态才需要调用爬虫能力
				//获取操作配置信息
				String sql_url = "select t.* from es_crawler_url_config t where t.status='0'";
				List<CrawlerUrlConfig> urlList = this.baseDaoSupport.queryForList(sql_url, CrawlerUrlConfig.class, null);
				
				//调用爬虫修改能力
				String[] result = optionAfterCondUpdate(null,proc_id,type,cond_id,urlList);
				if (result!=null && result.length>0) {
					return result;
				}
				
				//更新该条件状态
				String sql = "update es_crawler_proc_cond t set t.status='1' where t.cond_id='"+cond_id+"'";
				this.baseDaoSupport.execute(sql);
			}else{
				//更新该条件状态
				String sql = "update es_crawler_proc_cond t set t.status='1' where t.cond_id='"+cond_id+"'";
				this.baseDaoSupport.execute(sql);
			}
			
			//若该线程的条件全部被禁用，则改变该进程的状态为禁用
			String sql_cond = "select t.* from es_crawler_proc_cond t where t.proc_id=? and t.status='0'";
			List list = this.baseDaoSupport.queryForList(sql_cond, proc_id);
			if(list==null || list.size()<=0){
				String sql = "update es_crawler_proc t set t.status='1' where t.proc_id='"+proc_id+"'";
				this.baseDaoSupport.execute(sql); 
			}
			
			return new String[]{};
		}
		return new String[]{"1","获取该条件对应的线程状态失败!"};
	}

	/**
	 * 初始化爬虫端信息
	 * 根据进程ip和端口获取条件信息
	 */
	@Override
	public CrawlerProcCondResp getProcCond(String ip, String port) {
		CrawlerProcCondResp resp = new CrawlerProcCondResp();
		Map<String,List<CrawlerProcCondSettingReq>> respMap = new HashedMap();
		//获取该ip和端口配置的线程
		String sql_proc = "select t.* from es_crawler_proc t where t.ip=? and t.port=? and t.status='0'";
		List<CrawlerProc> cProcs = this.baseDaoSupport.queryForList(sql_proc, CrawlerProc.class,ip,port);
		
		if(cProcs!=null&&cProcs.size()>0){
			for(CrawlerProc cProc :cProcs){
				String proc_id = cProc.getProc_id();
				String thread_name = cProc.getThread_name();
				//获取该线程下配置的可用条件
				String sql_cond = "select t.cond_code,t.cond_name,t.cond_value from es_crawler_proc_cond t where t.proc_id=? and t.status='0'";
				List<CrawlerProcCondSettingReq> conds = this.baseDaoSupport.queryForList(sql_cond, CrawlerProcCondSettingReq.class, proc_id);
				
				if(conds!=null&&conds.size()>0){
					respMap.put(thread_name, conds);
					resp.setRespMap(respMap);
					resp.setError_code("0");
					resp.setError_msg("成功!");
				}else{
					resp.setError_code("-1");
					resp.setError_msg("IP为："+ip+"、端口为："+port+"、线程名称为："+thread_name+"的线程没有配置相关的条件!");
					return resp;
				}
			}
		}else{
			resp.setError_code("-1");
			resp.setError_msg("IP为："+ip+"、端口为："+port+";没有配置相应的进程");
			return resp;
		}
		
		//查询爬虫基础信息
		String sql_crawler = "select t.* from es_crawler_config t ";
		CrawlerConfig crawlerConfig = (CrawlerConfig) this.baseDaoSupport.queryForObject(sql_crawler, CrawlerConfig.class, null);
		
		if(crawlerConfig==null){
			resp.setError_code("-1");
			resp.setError_msg("IP为："+ip+"、端口为："+port+";没有查询到爬虫基础信息!");
			return resp;
		}
		resp.setCfg_id(crawlerConfig.getCfg_id()!=null?crawlerConfig.getCfg_id().toString():"");
		resp.setCrawlerAddress(crawlerConfig.getAddress()!=null?crawlerConfig.getAddress().toString():"");
		resp.setCrawlerPort(crawlerConfig.getPort()!=null?crawlerConfig.getPort().toString():"");
		resp.setCrawlerProxyType(crawlerConfig.getProxy_type()!=null?crawlerConfig.getProxy_type().toString():"");
		resp.setCrawlerProxyAddress(crawlerConfig.getProxy_address()!=null?crawlerConfig.getProxy_address().toString():"");
		resp.setCrawlerProxyPort(crawlerConfig.getProxy_port()!=null?crawlerConfig.getProxy_port().toString():"");
		resp.setCrawlerProxyUser(crawlerConfig.getProxy_user()!=null?crawlerConfig.getProxy_user().toString():"");
		resp.setCrawlerProxyPwd(crawlerConfig.getProxy_pwd()!=null?crawlerConfig.getProxy_pwd().toString():"");
		resp.setCrawlerIsOutIdCheck(crawlerConfig.getIs_out_id_check()!=null?crawlerConfig.getIs_out_id_check().toString():"");
		
		//查询操作配置信息
		String sql_url = "select t.* from es_crawler_url_config t where t.status='0'";
		List<CrawlerUrlConfig> urlList = this.baseDaoSupport.queryForList(sql_url, CrawlerUrlConfig.class, null);
		if(urlList==null || urlList.size()<=0){
			resp.setError_code("-1");
			resp.setError_msg("获取操作配置信息失败!");
		}
		
		resp.setUrlList(urlList);
		return resp;
	}
	
	/**
	 * 调用爬虫修改能力
	 * listParams : 新增条件的list
	 * type(操作的类型):forbid_cond-禁用条件,forbid_proc-禁用线程,opt_url:新增或修改操作配置信息
	 * cond_id : 用于禁用条件的时候
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String[] optionAfterCondUpdate(List<Map<String, String>> listParams,String proc_id,String type,String cond_id,List<CrawlerUrlConfig> urlList){
		UpdateCrawlerSettingReq req = new UpdateCrawlerSettingReq();
		
		if(type.equals("opt_url")){
			req.setUrlList(urlList);
		}else if(type.equals("crawler_config")){
			
		}else{
			String sql_proc = "select t.* from es_crawler_proc t where t.proc_id=?";  
			CrawlerProc crawlerProc = (CrawlerProc) this.baseDaoSupport.queryForObject(sql_proc, CrawlerProc.class, proc_id);
			if(crawlerProc==null){
				return new String[]{"1","调用爬虫修改能力前获取线程信息失败!"};
			}
			String thread_name = crawlerProc.getThread_name()!=null?crawlerProc.getThread_name().toString():"";
			
			//进程所在ip和端口
			req.setIp(crawlerProc.getIp()!=null?crawlerProc.getIp().toString():"");//进程ip
			req.setPort(crawlerProc.getPort()!=null?crawlerProc.getPort().toString():"");//进程端口
			req.setThreadName(thread_name);
			req.setUrlList(urlList);//操作配置信息
			
			if(type.equals("forbid_proc")){//禁用进程
				req.setThreadStatus("0");//返回对象里面线程状态返回禁用状态
			}else{
				List<CrawlerProcCondSettingReq> condList = new ArrayList<CrawlerProcCondSettingReq>();
				
				//获取原有的可用条件
				List<CrawlerProcCondSettingReq> conds = new ArrayList<CrawlerProcCondSettingReq>();
				if(type.equals("forbid_cond")){
					String sql_cond = "select t.cond_code,t.cond_name,t.cond_value from es_crawler_proc_cond t where t.proc_id=? and t.status='0' and t.cond_id <> ?";
					 conds = this.baseDaoSupport.queryForList(sql_cond, CrawlerProcCondSettingReq.class, proc_id,cond_id);
				}else{
					String sql_cond = "select t.cond_code,t.cond_name,t.cond_value from es_crawler_proc_cond t where t.proc_id=? and t.status='0'";
					 conds = this.baseDaoSupport.queryForList(sql_cond, CrawlerProcCondSettingReq.class, proc_id);
				}
				
				//原有可用条件
				if(conds!=null&&conds.size()>0){
					for(CrawlerProcCondSettingReq condSettingReq : conds){
						condList.add(condSettingReq);
					}
				}
				
				if(listParams!=null&&listParams.size()>0){
					//新增条件
					for(Map map : listParams){
						CrawlerProcCondSettingReq cSettingReq = new CrawlerProcCondSettingReq();
						cSettingReq.setCond_code(map.get("cond_code")!=null?map.get("cond_code").toString():"");
						cSettingReq.setCond_name(map.get("cond_name")!=null?map.get("cond_name").toString():"");
						cSettingReq.setCond_value(map.get("cond_value")!=null?map.get("cond_value").toString():"");
						condList.add(cSettingReq);
					}
				}
				
				req.setThreadName(thread_name);
				req.setCrawlerProcCond(condList);
				req.setThreadStatus("1");//线程状态为启用
			}

		}
		
		//查询爬虫基础信息
		String sql_crawler = "select t.* from es_crawler_config t ";
		CrawlerConfig crawlerConfig = (CrawlerConfig) this.baseDaoSupport.queryForObject(sql_crawler, CrawlerConfig.class, null);
		
		if(crawlerConfig==null){
			return new String[]{"1","获取爬虫基础数据失败!"};
		}
		
		//爬虫基础信息
//		req.setCfg_id(crawlerConfig.getCfg_id()!=null?crawlerConfig.getCfg_id().toString():"");
		req.setCrawlerAddress(crawlerConfig.getAddress()!=null?crawlerConfig.getAddress().toString():"");
		req.setCrawlerPort(crawlerConfig.getPort()!=null?crawlerConfig.getPort().toString():"");
		req.setCrawlerProxyType(crawlerConfig.getProxy_type()!=null?crawlerConfig.getProxy_type().toString():"");
		req.setCrawlerProxyAddress(crawlerConfig.getProxy_address()!=null?crawlerConfig.getProxy_address().toString():"");
		req.setCrawlerProxyPort(crawlerConfig.getProxy_port()!=null?crawlerConfig.getProxy_port().toString():"");
		req.setCrawlerProxyUser(crawlerConfig.getProxy_user()!=null?crawlerConfig.getProxy_user().toString():"");
		req.setCrawlerProxyPwd(crawlerConfig.getProxy_pwd()!=null?crawlerConfig.getProxy_pwd().toString():"");
		req.setCrawlerIsOutIdCheck(crawlerConfig.getIs_out_id_check()!=null?crawlerConfig.getIs_out_id_check().toString():"");
		
		//调用爬虫修改能力
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ZteResponse  resp = client.execute(req, ZteResponse.class);
		
		if(resp == null || !resp.getError_code().equals("0")){
			logger.info("=====OrderCrawlerManager optionAfterCondUpdate 更新爬虫配置信息出错【errorMsg："+resp.getError_msg()+"】");
			return new String[]{"1",resp.getError_msg()};
		}
		
		return new String[]{};
	}
	
	/*
	 * 保存条件
	 */
	public String[] saveCond(List<Map<String,String>> listParms,String proc_id){
		if(listParms!=null&&listParms.size()>0){
			for(Map map : listParms){
				String cond_code = map.get("cond_code").toString();
				
				map.put("cond_id", SequenceTools.getShort18PrimaryKey());
				map.put("proc_id", proc_id);
				map.put("status", "0");//0正常，1失效
				this.baseDaoSupport.insert("es_crawler_proc_cond", map, null);
				
			}
			
			return new String[]{};
		}
		return new String[]{"1","保存条件，传到后台的list为null"};
	}

	/**
	 * 查询爬虫基础信息
	 */
	@Override
	public CrawlerConfig queryCrawlerConfig() {
		String sql = "select t.* from es_crawler_config t";
		CrawlerConfig crawlerConfig = (CrawlerConfig) this.baseDaoSupport.queryForObject(sql, CrawlerConfig.class, null);
		return crawlerConfig;
	}

	/**
	 * 查询操作配置信息
	 */
	@Override
	public Page queryUrlInfo(int pageNo, int pageSize) {
		String sql = "select t.* from es_crawler_url_config t where t.status='0'";
		return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize);
	}

	/**
	 * 新增操作配置信息
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String[] addUrlInfo(List<Map<String, String>> Urllist) throws Exception{
		if(Urllist!=null&&Urllist.size()>0){
			for(Map map : Urllist){
				map.put("cuc_id", SequenceTools.getShort18PrimaryKey());
				map.put("status", "0");
				this.baseDaoSupport.insert("es_crawler_url_config", map, null);
			}
			
			//查询操作配置信息
			String sql_url = "select t.* from es_crawler_url_config t where t.status='0'";
			List<CrawlerUrlConfig> urlList = this.baseDaoSupport.queryForList(sql_url, CrawlerUrlConfig.class, null);
			
			//调用爬虫修改能力
			String[] result = optionAfterCondUpdate(null,null,"opt_url",null,urlList);
			if (result!=null && result.length>0) {
				return result;
			}
			
			return new String[]{};
		}
		return new String[]{"1","保存操作配置，传到后台的list为null"};
	}

	/**
	 * 删除操作配置信息
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String[] delUrlConfig(String cuc_id) {
		if(!StringUtils.isEmpty(cuc_id)){
			//调用爬虫修改能力
			String sql_url = "select t.* from es_crawler_url_config t where t.status='0' and t.cuc_id <> ? ";
			List<CrawlerUrlConfig> urlList = this.baseDaoSupport.queryForList(sql_url, CrawlerUrlConfig.class, cuc_id);
			String[] result = optionAfterCondUpdate(null, null, "opt_url", null,urlList);
			if(result!=null && result.length>0){
				return result;
			}
			
			String sql = "update es_crawler_url_config t set t.status='1' where t.cuc_id='"+cuc_id+"'";
			this.baseDaoSupport.execute(sql);
			return new String[]{};
		}
		
		return new String[]{"1","cuc_id为空!"};
	}
	
	/**
	 * 界面刷新将已开启进程的爬虫信息传到爬虫侧
	 */
	public String[] refreshInfo(){
		String sql = "select distinct t.ip,t.port from es_crawler_proc t where t.status='0'";
		List<CrawlerProc> list = this.baseDaoSupport.queryForList(sql, CrawlerProc.class, null);
		if(list==null || list.size()<=0){
			return new String[]{"1","没有查询到已开启的进程，无需刷新!"};
		}
		
		for(CrawlerProc cProc : list){
			String proc_id = cProc.getProc_id();
			
			//查询操作配置信息
			String sql_url = "select t.* from es_crawler_url_config t where t.status='0'";
			List<CrawlerUrlConfig> urlList = this.baseDaoSupport.queryForList(sql_url, CrawlerUrlConfig.class, null);
			
			String result[] =  optionAfterCondUpdate(null,proc_id,null,null,urlList);
			
			if(result!=null || result.length>0){
				return result;
			}
			
		}
		return new String[]{};
	}

	/**
	 * 根据条件类型获取条件编码
	 */
	@Override
	public List<Map<String, String>>  getCondCode(String condType) {
		List<Map<String, String>> list = new ArrayList();
		List<Map<String, String>> condCodeList = new ArrayList();
		String sql = "select t.dc_sql from es_dc_sql t where t.dc_name=?";
		String code_sql = this.baseDaoSupport.queryForString(sql, condType);
		if(!StringUtils.isEmpty(code_sql)){
			list = this.baseDaoSupport.queryForList(code_sql, null);
		}
		for (Map<String, String> map : list) {
			Map<String, String> map1 = new HashMap<String, String>();
			String selType = map.get("codea");
			String condValueCode = map.get("codeb");
			String pkey = map.get("pkey");
			String pname = map.get("pname");
			map1.put("selType", selType);
			map1.put("condValueCode", condValueCode);
			map1.put("pkey", pkey);
			map1.put("pname", pname);
			condCodeList.add(map1);
		}
		
		return condCodeList;
	}

	@Override
	public List<Map<String, String>> getCondValueInfo(String condValueCode) {
		List<Map<String, String>> list = new ArrayList();
		List<Map<String, String>> condValueList = new ArrayList();
		String sql = "select t.dc_sql from es_dc_sql t where t.dc_name=?";
		String code_sql = this.baseDaoSupport.queryForString(sql, condValueCode);
		if(!StringUtils.isEmpty(code_sql)){
			list = this.baseDaoSupport.queryForList(code_sql, null);
		}
		for (Map<String, String> map : list) {
			Map<String, String> map1 = new HashMap<String, String>();
			String pkey = map.get("key");
			String pname = map.get("value");
			map1.put("pkey", pkey);
			map1.put("pname", pname);
			condValueList.add(map1);
		}
		
		return condValueList;
	}
	public String modifyZbOrderGoodsInfo(String orderId){
		String json ="";
		OrderTreeBusiRequest newOrderTree = CommonDataFactory.getInstance().getOrderTree(orderId);
		CrawlerUpdateGoodsInfoReq goodsInfoReq = new CrawlerUpdateGoodsInfoReq();
		goodsInfoReq.setOrderNo(newOrderTree.getOrderExtBusiRequest().getOut_tid());
		goodsInfoReq.setCustName(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PHONE_OWNER_NAME));
		goodsInfoReq.setCertAddr(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CERT_ADDRESS));
		goodsInfoReq.setPsptNo(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CERT_CARD_NUM));
		List<OrderItemProdBusiRequest> itemProdList = newOrderTree.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests();
		if(null!=itemProdList){
			String certType =itemProdList.get(0).getOrderItemProdExtBusiRequest().getCerti_type();
			String firstMonthFree = itemProdList.get(0).getOrderItemProdExtBusiRequest().getFirst_payment();
			if(ZjEcsOrderConsts.CERTI_TYPE_SFZ15.equals(certType)){
				goodsInfoReq.setPsptType("01");
			}else if(ZjEcsOrderConsts.CERTI_TYPE_SFZ18.equals(certType)){
				goodsInfoReq.setPsptType("02");
			}
			
			if(ZjEcsOrderConsts.FIRST_FEE_TYPE_ALLM.equals(firstMonthFree)){
				goodsInfoReq.setFirstMonthFee("A000011V000001");
				goodsInfoReq.setFirstMonthFeeName("全月");
			}else if(ZjEcsOrderConsts.FIRST_FEE_TYPE_HALF.equals(firstMonthFree)){
				goodsInfoReq.setFirstMonthFee("A000011V000002");
				goodsInfoReq.setFirstMonthFeeName("半月");
			}else if(ZjEcsOrderConsts.FIRST_FEE_TYPE_OTHER.equals(firstMonthFree)){
				goodsInfoReq.setFirstMonthFee("A000011V000003");
				goodsInfoReq.setFirstMonthFeeName("套餐包外");
			}
		}
		ZteClient client =ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ZteResponse  goodsInfoResp= client.execute(goodsInfoReq, ZteResponse.class);
		if(!ConstsCore.ERROR_SUCC.equals(goodsInfoResp.getError_code())){
			json="{'result':1,'message':'修改总商商品信息出错"+goodsInfoResp.getError_msg()+"'}";
		}else{
			json="{'result':0,'message':'修改总商商品信息成功'}";
		}
		return json;
	}
	public String modifyZbOrderPostInfo(String order_id){
		String json="";
		OrderTreeBusiRequest newOrderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<OrderDeliveryBusiRequest> deliveryList = newOrderTree.getOrderDeliveryBusiRequests();
		if(null!=deliveryList){
			OrderDeliveryBusiRequest delivery = deliveryList.get(0);
			
			CrawlerUpdatePostInfoReq postInfoReq = new CrawlerUpdatePostInfoReq();
			postInfoReq.setOrderNo(newOrderTree.getOrderExtBusiRequest().getOut_tid());
			postInfoReq.setReceiverName(delivery.getShip_name());
			postInfoReq.setProviceCode(delivery.getProvince_id()==null?"":delivery.getProvince_id().toString());
			postInfoReq.setCityCode(delivery.getCity_id()==null?"":delivery.getCity_id().toString());
			postInfoReq.setDistrictCode(delivery.getRegion_id()==null?"":delivery.getRegion_id().toString());
			postInfoReq.setPostAddr(delivery.getShip_addr());
			postInfoReq.setWishLgtsCode(delivery.getShipping_company().equals(EcsOrderConsts.LOGI_COMPANY_SFFYZQYF)?"1002":"1001");
			String deliveryType =delivery.getShipping_time();
			if(ZjEcsOrderConsts.SHIPPING_TIME_NOLIMIT.equals(deliveryType)){
				postInfoReq.setDeliverDateType("01");//配送类型  04：延时配送，01： 不限时配送，02：只工作日送货，03只有双休日、节假日送货
			}else if(ZjEcsOrderConsts.SHIPPING_TIME_HOLIDAY.equals(deliveryType)){
				postInfoReq.setDeliverDateType("03");
			}else if(ZjEcsOrderConsts.SHIPPING_TIME_WORKDAY.equals(deliveryType)){
				postInfoReq.setDeliverDateType("02");
			}
			postInfoReq.setMobilePhone(delivery.getShip_tel());
			String shipType =newOrderTree.getOrderBusiRequest().getShipping_type();
			if(ZjEcsOrderConsts.SHIP_TYPE_KD.equals(shipType)){
				postInfoReq.setDeliverTypeCode("01");  // 01:第三方物流 04：现场提货 05：自行配送
				postInfoReq.setDispatchName("快递");//配送方式名称
			}else if(ZjEcsOrderConsts.SHIP_TYPE_XCTH.equals(shipType)) {
				postInfoReq.setDeliverTypeCode("04"); 
			}else if(ZjEcsOrderConsts.SHIP_TYPE_ZYPS.equals(shipType)){
				postInfoReq.setDeliverTypeCode("05"); 
			}
			
			ZteClient client =ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			ZteResponse  zteResp= client.execute(postInfoReq, ZteResponse.class);
			if(!ConstsCore.ERROR_SUCC.equals(zteResp.getError_code())){
				json="{'result':1,'message':'修改总商配送信息出错"+zteResp.getError_msg()+"'}";
			}else{
				json="{'result':0,'message':'修改总商配送信息成功'}";
			}
		}
		return json; 
	}
	/**
	 * 开户环节修改总商商品信息
	 * @param order_id
	 * @return
	 */
	public String modifyGoodsInfoToZb(String order_id){
		String json="";
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ModifyOpenAccountGoodsReq req = new ModifyOpenAccountGoodsReq();
		req.setOrderId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_NUM));
		req.setOrderNo(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID));
		req.setIdCard(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM));
		//证件类型01:15位身份证,02:18位身份证
		String cert_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERTI_TYPE);
		String certType = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ZB_CERT_TYPE_RELATION, cert_type);
		req.setCertType(certType);
		String certName = AttrUtils.getInstance().getOtherDictCodeValueDesc(StypeConsts.ZB_CERT_TYPE_RELATION, cert_type);
		req.setCertName(certName);
		req.setCertAddr(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_ADDRESS));
		req.setCustName(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_OWNER_NAME));
		req.setReferrerName(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.RECOMMENDED_NAME));
		req.setReferrerPhone(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.RECOMMENDED_PHONE));
		req.setDevelopmentName(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.DEVELOPMENT_NAME));
		req.setDevelopmentCode(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.DEVELOPMENT_CODE));
		req.setDevelopmentDepartId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.DEVELOPDEP_ID));
		req.setChannelName(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CHA_NAME));
		req.setRecommendNumber(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CHA_PHONENUM));
		req.setIsManual(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ZB_OPEN_TYPE));
		ZteResponse rsp = client.execute(req, ZteResponse.class);
		if(!ConstsCore.ERROR_SUCC.equals(rsp.getError_code())){
			json="{'result':1,'message':'修改总商商品信息出错"+rsp.getError_msg()+"'}";
		}else{
			json="{'result':0,'message':'修改总商商品信息成功'}";
		}
		return json;
	}
	/**
	 * 开户环节修改总商配送信息
	 * @param order_id
	 * @return
	 */
	public String modifyDeliveryInfoToZb(String order_id){
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		String json="";
		CrawlerDeliveryInfoReq req = new CrawlerDeliveryInfoReq();
		req.setOrderId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_NUM));
		req.setOldProvinceId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PROVINCE_CODE));
		req.setOldCityId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CITY_CODE));
		req.setProvinceId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PROVINCE_CODE));
		req.setCityId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CITY_CODE));
		req.setDistrictId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.AREA_CODE));
		req.setPostAddr(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_ADDR));
		String sending_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SENDING_TYPE);
		String dispachCode = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ZB_CARRY_MODE_CRAWLER, sending_type);
		if(StringUtils.isEmpty(dispachCode)){
			dispachCode = "01";
		}
		req.setDispachCode(dispachCode);
		String shipping_time = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIPPING_TIME);
		String dlvTypeCode = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ZB_SHIPPING_TIME, shipping_time);
		req.setDlvTypeCode(dlvTypeCode);
		String shipping_company = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIPPING_COMPANY);
		String logisticCode=CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ZB_SHIPPING_COMPANY, shipping_company);
		req.setLogisticCode(logisticCode);
		String delayTime = null;
		if(EcsOrderConsts.SHIPPING_TIME_HOLIDAY.equals(shipping_time)){
			delayTime = "只有双休日、节假日送货";
		}
		else if(EcsOrderConsts.SHIPPING_TIME_WORKDAY.equals(shipping_time)){
			delayTime = "周一到周五工作时间";
		}
		else if(EcsOrderConsts.SHIPPING_TIME_NOLIMIT.equals(shipping_time)){
			delayTime = "工作日、双休日、节假日均可送货";
		}
		req.setDelayTime(delayTime);
		req.setDeliverySelfName(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BUYER_NAME));
		req.setDeliverySelfPhone(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REVEIVER_MOBILE));
		req.setOrderNo(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID));
		req.setIsManual(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ZB_OPEN_TYPE));
		CrawlerResp rsp = client.execute(req, CrawlerResp.class);
		if(!ConstsCore.ERROR_SUCC.equals(rsp.getError_code())){
			json="{'result':1,'message':'修改总商商品信息出错"+rsp.getError_msg()+"'}";
		}else{
			json="{'result':0,'message':'修改总商商品信息成功'}";
		}
		return json;
	}
}

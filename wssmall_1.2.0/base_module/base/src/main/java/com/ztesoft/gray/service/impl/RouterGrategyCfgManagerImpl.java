package com.ztesoft.gray.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.taobao.tair.ResultCode;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.HttpUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.gray.model.RouterGrategy;
import com.ztesoft.gray.service.IRouterGrategyCfgManager;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.cache.redis.DefaultRedisClient;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.DateUtil;
import com.ztesoft.net.framework.util.HttpClientUtils;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.service.IGrayDataSyncManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import consts.ConstsCore;

public class RouterGrategyCfgManagerImpl  extends BaseSupport implements IRouterGrategyCfgManager{
	private IGrayDataSyncManager grayDataSyncManagerImpl;
	

	@Override
	public Page qryRouterGrategyCfg(HashMap param, int page, int pageSize) {
		String sql = "select * from es_abgray_grategy_cfg a where a.is_show = '"+ConstsCore.CONSTS_YES+"'  order by cfg_id ";
		String countSql = "select count(1) " +sql.substring(sql.indexOf("from es_abgray_grategy_cfg "));
		Page webpage = this.daoSupport.queryForCPage(sql, page, pageSize, RouterGrategy.class, countSql);
		return webpage;
	}
	@Override
	public String initPolicy() throws Exception {
		String result_str="";
		String sql = "select * from es_abgray_grategy_cfg a where a.is_show = '"+ConstsCore.CONSTS_YES+"'";
		List<Map> cfgList = this.daoSupport.queryForList(sql);
		for (Map map : cfgList) {
			result_str=result_str+this.setPolicyById((String)map.get("cfg_id"));
		}
		return result_str;
	}
	
	private String setPolicyById(String cfg_id) throws Exception{
		String  result_str="";
		String  succ_code = "200";//tengine成功编码
		HashMap params = new HashMap();
		//set
		Map cgfMap=this.qryRouterGrategyCfgById(cfg_id);
		String instruction=(String)cgfMap.get("instruction");
		String env_url=(String)cgfMap.get("env_url");
		
		
		env_url=env_url+"/admin/policy/set";
		String result = HttpClientUtils.getResult(env_url, instruction,null);
		Map map = new HashMap();
		JSONObject  joso = JSONObject.fromObject(result);
		map = (Map) JSONObject.toBean(joso,HashMap.class);
		if(map!=null){
			String errcode = ((java.lang.Integer)map.get("errcode")).toString();
			String errinfo = (String)map.get("errinfo");
			if(succ_code.equals(errcode)){//成功
				String policyid_rt=errinfo.replace("success  the id of new policy is ", "");//截取策略id
				if(!StringUtil.isEmpty(policyid_rt)){//到服务器上配置策略 返回修改初始状态为挂起
					//TODO：update es_abgray_grategy_cfg->policyid,run_status=00X where cfg_id=?
					params.put("policyid", policyid_rt);
					//params.put("run_status", "00X");
					this.updateAbGrayCfg(cfg_id, params);
				}else{
					result_str="[cfg_id="+cfg_id+"]截取返回的策略id失败;";
				}
			}else{
				result_str="[cfg_id="+cfg_id+"]设置策略失败"+errinfo+";";
			}
		}else{
			result_str="[cfg_id="+cfg_id+"]设置策略，http返回结果为空;";
		}
		return result_str;
	}
	@Override
	public String initRunPolicy() throws Exception {
		String result_str="";
		String  succ_code = "200";//tengine成功编码
		
		
		//删除运行时策略
		String urlSql = "select env_url from es_abgray_grategy_cfg a where a.is_show = 'yes' and a.env_url is not null  group by a.env_url  ";
		List<Map> urlList = this.daoSupport.queryForList(urlSql);
		for (Map urlMap : urlList) {
			//运行
			String env_url = (String)urlMap.get("env_url");
			String env_url_run=env_url+"/admin/runtime/del";
			String result = HttpUtils.getContentByUrl(env_url_run,5000);//{"errcode":200,"errinfo":"success "}
			JSONObject  joso = JSONObject.fromObject(result);
			Map map =  (Map) JSONObject.toBean(joso,HashMap.class);
			if(map!=null){
				String errcode = ((java.lang.Integer)map.get("errcode")).toString();
				String errinfo = (String)map.get("errinfo");
				 if(!succ_code.equals(errcode)){//失败
					 result_str=result_str+"[env_url="+env_url+"]删除运行时策略失败，"+errinfo;
				 }
			}
		}
		
		//设置
		String sql = "select * from es_abgray_grategy_cfg a where a.run_status = '00A' and a.is_show = '"+ConstsCore.CONSTS_YES+"' ";
		List<Map> cfgList = this.daoSupport.queryForList(sql);
		for (Map cfgMap : cfgList) {
			//运行
			String env_url = (String)cfgMap.get("env_url");
			String policyid = (String)cfgMap.get("policyid");
			String cfg_id = (String)cfgMap.get("cfg_id");
			String env_code = (String)cfgMap.get("env_code");
			String env_type = (String)cfgMap.get("env_type");
			String env_url_run=env_url+"/admin/runtime/set?policyid="+policyid;
			String result = HttpUtils.getContentByUrl(env_url_run,5000);//{"errcode":200,"errinfo":"success "}
			//TODO：成功修改策略为运行 ,同服务类型的需挂起 update es_abgray_grategy_cfg->run_status=00A where cfg_id=?
			//TODO: update run_status=00X where --同一环境类型只有一条策略运行 其他的需挂起
			JSONObject  joso = JSONObject.fromObject(result);
			Map map =  (Map) JSONObject.toBean(joso,HashMap.class);
			if(map!=null){
				String errcode = ((java.lang.Integer)map.get("errcode")).toString();
				String errinfo = (String)map.get("errinfo");
				 if(!succ_code.equals(errcode)){//失败
					 result_str=result_str+"[cfg_id="+cfg_id+"]设置运行时策略失败，"+errinfo;
				 }else if(!("ecsord_server_inf").equals(env_type)){//写入环境编码到redis
					 String env_set_url=env_url+"/admin/policy/setECS?ecs_code="+env_code;
					 String evnResult = HttpUtils.getContentByUrl(env_set_url,5000);
					 JSONObject  envJson = JSONObject.fromObject(evnResult);
						Map envMap =  (Map) JSONObject.toBean(envJson,HashMap.class);
						if(envMap!=null){
							 errcode = ((java.lang.Integer)envMap.get("errcode")).toString();
							 errinfo = (String)envMap.get("errinfo");
							 logger.info("写入环境编码到redis结果："+errinfo);
							 if(!succ_code.equals(errcode)){//失败
								 result_str=result_str+"[cfg_id="+cfg_id+"]设置运行时策略成功，但写入环境编码到redis失败，"+errinfo;
							 }else{
								 result_str=result_str+" [成功设置的cfg_id:"+cfg_id+"]";
							 }
						}else{
							result_str=result_str+ "[cfg_id="+cfg_id+"]写入环境编码到redis时，http返回结果为空";
						}
				 }else {
					 result_str=result_str+" [成功设置的cfg_id:"+cfg_id+"]";
				 }
			}else{
				result_str=result_str+"[cfg_id="+cfg_id+"]设置运行时策略，http返回结果为空";
			}
			
			//无论成功与否都需要更新策略,以便redis挂了时能快出切换环境，不用修改订单标准化job
			//this.updateAbGrayStatus(cfg_id, env_type);
		
		}
		return result_str;
	}
	
	
	/**
	 * 
	 1 在数据库插入数据 policyid和run_status必须为空
	 2 配置策略 得到policyid
	 3 启用策略 修改状态修改run_status
	 * @throws Exception 

	 */
	@Override
	public String operPolicy(HashMap param) throws Exception {
		
			String run_status = (String)param.get("run_status");
			String cfg_id = (String)param.get("cfg_id");
			String env_url = (String)param.get("env_url");
			String env_type = (String)param.get("env_type");
			String env_code = (String)param.get("env_code");
			String policyid = (String)param.get("policyid");
			String gray_moment =(String)param.get("gray_moment");
			Map params = new HashMap();
			String  result_code = "200";
			String result_str="";
			
			String  errcode = "";//返回的
			String  errinfo = "";
			if("set".equals(run_status)){//set
				Map cgfMap=this.qryRouterGrategyCfgById(cfg_id);
				String instruction=(String)cgfMap.get("instruction");
				env_url=env_url+"/admin/policy/set";
				String result = HttpClientUtils.getResult(env_url, instruction,null);
				Map map = new HashMap();
				JSONObject  joso = JSONObject.fromObject(result);
				map = (Map) JSONObject.toBean(joso,HashMap.class);
				if(map!=null){
					 errcode = ((java.lang.Integer)map.get("errcode")).toString();
					 errinfo = (String)map.get("errinfo");
					if(result_code.equals(errcode)){//成功
						String policyid_rt=errinfo.replace("success  the id of new policy is ", "");//截取策略id
						if(!StringUtil.isEmpty(policyid_rt)){//到服务器上配置策略 返回修改初始状态为挂起
							//TODO：update es_abgray_grategy_cfg->policyid,run_status=00X where cfg_id=?
							params.put("policyid", policyid_rt);
							params.put("run_status", "00X");
						}else{
							result_str="截取返回的策略id失败";
						}
					}else{
						result_str="设置策略失败"+errinfo;
					}
				}else{
					result_str="设置策略，http返回结果为空";
				}
			}else if("run".equals(run_status)){//运行
				String env_url_run=env_url+"/admin/runtime/set?policyid="+policyid;
				String result = HttpUtils.getContentByUrl(env_url_run,5000);//{"errcode":200,"errinfo":"success "}
				//TODO：成功修改策略为运行 ,同服务类型的需挂起 update es_abgray_grategy_cfg->run_status=00A where cfg_id=?
				//TODO: update run_status=00X where --同一环境类型只有一条策略运行 其他的需挂起
				JSONObject  joso = JSONObject.fromObject(result);
				Map map =  (Map) JSONObject.toBean(joso,HashMap.class);
				if(map!=null){
					 errcode = ((java.lang.Integer)map.get("errcode")).toString();
					 errinfo = (String)map.get("errinfo");
					 if(!result_code.equals(errcode)){//失败
						 result_str="设置运行时策略失败，"+errinfo;
					 }else if(!("ecsord_server_inf").equals(env_type)){//写入环境编码到redis
						 String env_set_url=env_url+"/admin/policy/setECS?ecs_code="+env_code;
						 String evnResult = HttpUtils.getContentByUrl(env_set_url,5000);
						 JSONObject  envJson = JSONObject.fromObject(evnResult);
							Map envMap =  (Map) JSONObject.toBean(envJson,HashMap.class);
							if(envMap!=null){
								 errcode = ((java.lang.Integer)envMap.get("errcode")).toString();
								 errinfo = (String)envMap.get("errinfo");
								 logger.info("写入环境编码到redis结果："+errinfo);
								 if(!result_code.equals(errcode)){//失败
									 result_str="设置运行时策略成功，但写入环境编码到redis失败，"+errinfo;
								 }
							}else{
								result_str ="写入环境编码到redis时，http返回结果为空";
							}
					 }
				}else{
					result_str ="设置运行时策略，http返回结果为空";
				}
				params.put("run_status", "00A");
				//无论成功与否都需要更新策略,以便redis挂了时能快出切换环境，不用修改订单标准化job
				this.updateAbGrayStatus(cfg_id, env_type);
				
			}else if("hang".equals(run_status)){//挂起
				String result = HttpUtils.getContentByUrl(env_url,5000);//{"errcode":200,"errinfo":"success "}
				params.put("run_status", "00X");
			}
			params.put("update_time", Const.SYSDATE);
			
			//无论成功与否都需要更新策略,以便redis挂了时能快出切换环境，不用修改订单标准化job
			this.updateAbGrayCfg(cfg_id, params);
			this.insertAbGrayCfgLog(cfg_id);
			String status = (String)params.get("run_status");
			this.updateAbgrayHostenv(status, env_code,env_type,gray_moment);
		return result_str;
	}
	
	public void updateAbGrayCfg(String cfg_id,Map params){
		Map whereMap = new HashMap();
		whereMap.put("cfg_id", cfg_id);
		this.baseDaoSupport.update("es_abgray_grategy_cfg", params, whereMap);
	}
	public void updateAbGrayStatus(String cfg_id,String env_type){
		String sql = "update  es_abgray_grategy_cfg set run_status ='00X' where cfg_id !=? and env_type=? ";
		this.baseDaoSupport.execute(sql, cfg_id,env_type);
	}
	public void insertAbGrayCfgLog(String cfg_id){
		
		String sql = "insert into  es_abgray_grategy_oper_log(id,cfg_id,policyid,oper_status,update_time,source_from)  (select S_ES_ABGRAY_GRATEGY_CFG.NEXTVAL,cfg_id,policyid,run_status,update_time,source_from from es_abgray_grategy_cfg where cfg_id=? and source_from ='"+ManagerUtils.getSourceFrom()+"' )";
		this.baseDaoSupport.execute(sql, cfg_id);
	}
	public void updateAbgrayHostenv(String run_status,String env_code,String env_type,String gray_moment){
	      if("00A".equals(run_status)){
		    	//查询正在运行的环境是否与修改的环境一致，如果不一致则需要清空订单主机表
		  		String order_env_sql ="select count(*) num from es_abgray_hostenv where env_code =? and env_status='00A' ";
		  		int count = this.baseDaoSupport.queryForInt(order_env_sql, env_code);
		  		//查询主机环境的业务版本，是1.0还是2.0		  			
	  			Map map= this.baseDaoSupport.queryForMap("select busi_version,env_group from es_abgray_hostenv where env_code=? and rownum=1",env_code);
	  			String busi_version =(String)map.get("busi_version");
	  			String env_group =(String)map.get("env_group");
		  		if(count==0){
		  			if(Consts.ZTE_CESH_ENV_TYPE_SERVER.equals(env_type)||Consts.ZTE_CESH_ENV_TYPE_SERVER_XJ.equals(env_type)){	
		  			   IDaoSupport baseDaoSupportGProd = SpringContextHolder.getBean("baseDaoSupportGProd");	
		  			 	baseDaoSupportGProd.execute("delete from es_abgray_ord_env_rel ");
		  			}else{
		  				this.baseDaoSupport.execute("delete from es_abgray_ord_env_rel ");
		  			}	
		  		}
		  		 //设置当前版本为运行状态
	  			 String sql_update ="update  es_abgray_hostenv set env_status='00A'  where busi_version =? and env_group=?";
	  			 this.baseDaoSupport.execute(sql_update,busi_version,env_group);
	  			 //设置busi_version对应环境为灰度
	  			sql_update ="update  es_abgray_hostenv set env_status='00X'  where busi_version =? and env_group<>?";
	  			this.baseDaoSupport.execute(sql_update,busi_version,env_group);
	  			
	      }else{
	    	  String sql_update = " update es_abgray_hostenv set env_status=? where env_code= ? ";
			  this.baseDaoSupport.execute(sql_update, run_status,env_code);
	      }
	      //更新缓存
	      this.grayDataSyncManagerImpl.refreshHostEnv();
	      
	}
	public Map qryRouterGrategyCfgById(String cfg_id){
		String sql = "select * from es_abgray_grategy_cfg  a where  a.cfg_id=?";
		Map map = this.daoSupport.queryForMap(sql,cfg_id);
		return map;
		
	}
	public IGrayDataSyncManager getGrayDataSyncManagerImpl() {
		return grayDataSyncManagerImpl;
	}

	public void setGrayDataSyncManagerImpl(
			IGrayDataSyncManager grayDataSyncManagerImpl) {
		this.grayDataSyncManagerImpl = grayDataSyncManagerImpl;
	}

	@Override
	public String qrySeparateOnOff() throws Exception {
		String val="";
		DefaultRedisClient client = new DefaultRedisClient();
		List separate_list = client.r_mget("separate_key");
		if(separate_list!=null && separate_list.size()>0){
			val =(String)separate_list.get(0);
		}
		return val;
	}
	@Override
	public  void separateSet(String separate_status) throws Exception {
		DefaultRedisClient client = new DefaultRedisClient();
		if(!StringUtil.isEmpty(separate_status)){
			ResultCode separate_resultCode = client.r_mset(Consts.REDIS_EXPIRETIME, "separate_key",separate_status);
			if(ResultCode.SUCCESS.getCode()!=separate_resultCode.getCode()){
				throw new Exception("保存失败"+separate_resultCode.getMessage());
			}
		}
	}

	@Override
	public List getOrderEnvByOrderId(String order_id) {
		// TODO Auto-generated method stub
		Map map = BeanUtils.getCurrHostEnv();
		String env_type =String.valueOf(map.get("env_type"));
		String sql ="select * from es_abgray_ord_env_rel where order_id = ?";
		List list =null;
		//如果是测试环境这需要链接到生成环境查询
		if(Consts.ZTE_CESH_ENV_TYPE_SERVER.equals(env_type)||Consts.ZTE_CESH_ENV_TYPE_SERVER_XJ.equals(env_type)){	
			   IDaoSupport baseDaoSupportGProd = SpringContextHolder.getBean("baseDaoSupportGProd");	
			   list =baseDaoSupportGProd.queryForList(sql, order_id);
			}else{
			   list = baseDaoSupport.queryForList(sql, order_id);
			}	
		return list;
	}
	@Override
	public void setLimitCount(String limitCount, String execedCount) throws Exception {
		Map<String,String> map=new HashMap<String,String>();
		DefaultRedisClient client = new DefaultRedisClient();
		if(!StringUtil.isEmpty(limitCount)){
			ResultCode limit_resultCode = client.r_mset(Consts.REDIS_EXPIRETIME, this.getLimitCountKey(),limitCount);
			if(ResultCode.SUCCESS.getCode()!=limit_resultCode.getCode()){
				throw new Exception("限流订单数保存失败，"+limit_resultCode.getMessage());
			}
		}
		if(!StringUtil.isEmpty(execedCount)){
			ResultCode execed_resultCode = client.r_mset(Consts.REDIS_EXPIRETIME, this.getExecedCountKey(),execedCount);
			if(ResultCode.SUCCESS.getCode()!=execed_resultCode.getCode()){
				throw new Exception("已分流订单数保存失败，"+execed_resultCode.getMessage());
			}
		}
	}
	@Override
	public int getLimitCount() throws Exception {
		int limit_count=-1;
		DefaultRedisClient client = new DefaultRedisClient();
		List limit_count_list = client.r_mget(this.getLimitCountKey());
		if(limit_count_list!=null && limit_count_list.size()>0){
			limit_count =Integer.valueOf((String)limit_count_list.get(0)) ;
		}
		return limit_count;
		
	}
	@Override
	public int getExecedCount() throws Exception {
		int execed_count=-1;
		DefaultRedisClient client = new DefaultRedisClient();
		List execed_count_list = client.r_mget(this.getExecedCountKey());
		if(execed_count_list!=null && execed_count_list.size()>0){
			execed_count =Integer.valueOf((String) execed_count_list.get(0));
		}
		return execed_count;
	}

	private String getExecedCountKey(){
		return  DateUtil.toString(new Date(), "yyyyMMdd")+"_execed_count";
	}
	private String getLimitCountKey(){
		return  DateUtil.toString(new Date(), "yyyyMMdd")+"_limit_count";
	}
}

package zte.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import services.ServiceBase;
import zte.net.iservice.IJobTaskService;
import zte.params.req.JobTaskCheckedReq;
import zte.params.req.RefreshJobTaskCacheReq;
import zte.params.resp.JobTaskCheckedResp;
import zte.params.resp.RefreshJobTaskCacheResp;

import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.cache.ITaskCache;
import com.ztesoft.net.mall.core.model.ConfigInfo;
import com.ztesoft.net.mall.core.model.JobRunLog;
import com.ztesoft.net.mall.core.model.JobTask;
import com.ztesoft.net.mall.core.service.IJobTaskManager;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

public class JobTaskService extends ServiceBase implements IJobTaskService {
	
	private ITaskCache taskCache;
	private IJobTaskManager jobTaskManager;

	/*@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "查询可执行的定时任务配置信息", summary = "查询可执行的定时任务配置信息")
	public JobTaskQueryResp queryJobTask(JobTaskQueryReq req) {
		JobTask task = jobTaskManager.getJobTask(req.getClassName(), req.getMethod(), req.getIp(), req.getPort());
		JobTaskQueryResp resp = new JobTaskQueryResp();
		resp.setJobTask(task);
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}*/

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "检查定时任务是否可执行", summary = "检查定时任务是否可执行，并记录日志")
	public JobTaskCheckedResp checkedJobTask(JobTaskCheckedReq req) {
		JobTaskCheckedResp resp = new JobTaskCheckedResp();
		List<JobTask> taskList = taskCache.getCacheJob(req.getClassName(), req.getMethod());
		
		if(taskList!=null){
			for(JobTask task:taskList){
				if(!StringUtil.isEmpty(task.getRun_ip()) && !StringUtil.isEmpty(task.getRun_port())){
					
					String server_ip ="";
					String server_port = "";
					synchronized(this){
						//logger.info("set端口"+task.getRun_port());
						System.setProperty("timer_port",task.getRun_port());
						server_ip = StringUtil.getLocalIpAddress();
						server_port = StringUtil.getContextPort();
					}
					 //logger.info("定时任务获取到的端口"+server_port);
					if(task.getRun_ip().equals(server_ip) && task.getRun_port().equals(server_port)){
						JobRunLog log = new JobRunLog(task.getJob_id(), task.getRun_ip(), task.getRun_port(), task.getClass_name(), task.getRun_method());
						log.setRun_time("sysdate");
						jobTaskManager.insertJobRunLog(log);
						resp.setCanRun(true);
						break ;
					}
				}
			}
		}else{
			resp.setCanRun(false);
		}
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "刷定时任务缓存", summary = "刷定时任务缓存")
	public RefreshJobTaskCacheResp refreshJobTaskCache(
			RefreshJobTaskCacheReq req) {
		taskCache.cacheAllJob();
		RefreshJobTaskCacheResp resp = new RefreshJobTaskCacheResp();
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}
	
	@Override
	public Map checkedTaobaoJob(String server_ip,String server_port){
		if(com.ztesoft.form.util.StringUtil.isEmpty(server_ip)&&com.ztesoft.form.util.StringUtil.isEmpty(server_port)){
		server_ip = StringUtil.getLocalIpAddress();
		server_port = StringUtil.getContextPort();
		}
		logger.info("-----当前服务器IP："+server_ip+"端口："+server_port+"--------");
		ICacheUtil cacheUtil1 = SpringContextHolder.getBean("cacheUtil");
		List tbList = cacheUtil1.getConfigList("TBP");
//		IDictManager dictManager = SpringContextHolder.getBean("dictManager");
//		DictManagerCacheProxy dc=new DictManagerCacheProxy(dictManager);
//		List tbList = dc.loadData("TAOBAO_IP");
		Map resultMap = new HashMap();
		if(null==tbList||tbList.size()==0){
			logger.info("-----未找到淘宝自动任务ip配置数据，无需限制-------");
			resultMap.put("isneedcheck", "false");
			resultMap.put("iscanrun", "true");
			return resultMap;
		}else{
			resultMap.put("isneedcheck", "true");
		}
		boolean flag = false;
		for(int i=0;i<tbList.size();i++){
//			Map tb = (Map)tbList.get(i);
//			String desc = tb.get("value_desc").toString();
			ConfigInfo config = (ConfigInfo)tbList.get(i);
			String desc = config.getCf_value();
			Map tMap = JsonUtil.fromJson(desc, Map.class);
			if(server_ip.equals(tMap.get("ip"))&&server_port.equals(tMap.get("port"))){
				if("1".equals(tMap.get("isrun"))){
					logger.info("-----获取淘宝ip配置数据："+desc+"-----");
					flag = true;
					resultMap.putAll(tMap);
					break;
				}
			}		
		}
		resultMap.put("iscanrun", Boolean.toString(flag));
		return resultMap;
	}

	public ITaskCache getTaskCache() {
		return taskCache;
	}

	public void setTaskCache(ITaskCache taskCache) {
		this.taskCache = taskCache;
	}

	public IJobTaskManager getJobTaskManager() {
		return jobTaskManager;
	}

	public void setJobTaskManager(IJobTaskManager jobTaskManager) {
		this.jobTaskManager = jobTaskManager;
	}


}

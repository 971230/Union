package com.ztesoft.net.mall.core.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.model.JobTask;
import com.ztesoft.net.mall.core.service.IJobTaskManager;

public class TaskCache implements ITaskCache {

	public static final int NAME_SPACE = 444;
	public static final String JOB_TASK_CACHE_KEY = "JOB_TASK_CACHE";
	
	private static Map<String, List<JobTask>> CACHE_JOB_MAP;

	private synchronized static void initCacheJobConfig(){
		IJobTaskManager jobTaskManager = SpringContextHolder.getBean("jobTaskManager");
		List<JobTask> list = jobTaskManager.listJobTask();
		if(list!=null & list.size()>0){
			Map<String, List<JobTask>> tempMap = new HashMap<String, List<JobTask>>();
			for(JobTask job:list) {
				List<JobTask> tempList = tempMap.get(job.getClass_name()+"_"+job.getRun_method());
				if(tempList == null) {
					tempList = new ArrayList<JobTask>();
				}
				tempList.add(job);
				tempMap.put(job.getClass_name()+"_"+job.getRun_method(), tempList);
			}
			CACHE_JOB_MAP = tempMap;
		}
	}
	
	@Override
	public CacheArrayList<JobTask> cacheAllJob() {
//		List<JobTask> list = jobTaskManager.listJobTask();
//		if(list!=null & list.size()>0){
//			CacheArrayList<JobTask> cacheList = new CacheArrayList<JobTask>();
//			for(JobTask job:list){
//				cacheList.add(job);
//			}
//			cache.set(NAME_SPACE, JOB_TASK_CACHE_KEY, cacheList);
//			return cacheList;
//		}else{
//			return new CacheArrayList<JobTask>();
//		}
		initCacheJobConfig();
		return null;
	}

	@Override
	public List<JobTask> getCacheJob(String className, String method) {
//		CacheArrayList<JobTask> cacheList = (CacheArrayList<JobTask>) cache.get(NAME_SPACE, JOB_TASK_CACHE_KEY);
//		if(cacheList==null){//cacheList==null
//			cacheList = cacheAllJob();
//		}
//		List<JobTask> list = new ArrayList<JobTask>();
//		for(JobTask job:cacheList){
//			if(className.equals(job.getClass_name()) && method.equals(job.getRun_method())){
//				list.add(job);
//			}
//		}
//		if(ListUtil.isEmpty(list)){
//			for(JobTask job:cacheList){
//				if(className.equals(job.getClass_name())){
//					list.add(job);
//				}
//			}
//		}
//		return list;
		
		if(CACHE_JOB_MAP != null && CACHE_JOB_MAP.size() > 0) {
			return CACHE_JOB_MAP.get(className+"_"+method);
		}else {
			initCacheJobConfig();
			return CACHE_JOB_MAP.get(className+"_"+method);
		}
	}
}